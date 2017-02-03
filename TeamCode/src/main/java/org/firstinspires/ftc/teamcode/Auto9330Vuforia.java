package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;

/**
 * Created by NeoBOTS on 11/4/2016.
 */

@Autonomous(name= "Auto9330Vuforia", group ="Opmode")
public class Auto9330Vuforia extends LinearOpMode {

    Hardware9330 robot9330 = new Hardware9330();
    private ElapsedTime runtime = new ElapsedTime();
    drive9330 ds = null;
    Shoot9330 shooter ;
    Vuforia9330 vuforia = new Vuforia9330();
    ModernRoboticsI2cGyro gyro;

    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 3.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.14159);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.1;
    static final double     MAX_LEFT_TARGET         = 64;
    static final double     MAX_RIGHT_TARGET        = 70;
    static final double     CENTER_POSITION         = 67;
    static final double     CLOSEST_DISTANCE        = 10;
    static final double     MAX_LEFT_GYRO           = -3;
    static final double     MAX_RIGHT_GYRO          = 3;

    DcMotor encoderMotor = null;
    OpenGLMatrix latestLocation;
    double x;
    double y;
    int angleZ;
    boolean gyroInitialized = false;
    boolean foundTarget = false;
    boolean firstBeaconComplete = false;
    boolean missionComplete = false;

    @Override
    public void runOpMode() throws InterruptedException {

        telemetry.addData(">", "Initializing! Please wait!");
        telemetry.update();

        robot9330.init(hardwareMap);
        shooter = new Shoot9330(robot9330);
        vuforia.setupVuforia();
        gyro = (ModernRoboticsI2cGyro)hardwareMap.get("gyro");
        encoderMotor = robot9330.bigBallPickup;

        telemetry.addData(">", "Starting Calibration! Please wait!");
        telemetry.update();

        // Calibrate gyro
        gyro.calibrate();

        // Wait for gyro to finish calibrating
        while (!isStopRequested() && gyro.isCalibrating())  {
            sleep(50);
            idle();
        }
        gyro.resetZAxisIntegrator();
        gyroInitialized = true;

        // Instantiate and initialize drive subsystem
        ds = new drive9330(robot9330);
        ds.init(false);
        ds.reset();
        ds.setTime(100);

        // Wait for the start button
        telemetry.addData(">", "Finished Calibration! Press start when ready." );
        telemetry.update();
        waitForStart();

        vuforia.init();
        telemetry.addData("Waiting for 4.593 seconds",null);
        telemetry.update();
        sleep(4593);
        resetEncoder();
        encoderDrive(DRIVE_SPEED, -8, 5.0); // drive forward 8 inches with 5 second timeout
        telemetry.addData("Currently no-scoping center vortex",null);
        telemetry.update();
        shooter.shoot();
        telemetry.addData("Shots Fired.", "Now going to destroy cap ball");
        telemetry.update();
        resetEncoder();
        encoderDrive(DRIVE_SPEED, -28, 5.0); // drive forward 36 inches with 5 second timeout
                                             //(destination inches must be negative; I know, it's backwards :P)
        telemetry.addData("Cap ball has been rekt.", "Now trying to find beacons");
        telemetry.update();

        while(opModeIsActive()) {

            angleZ  = -gyro.getIntegratedZValue();

            telemetry.addData("Rotation from original angle ", angleZ);
            telemetry.update();

            if (!firstBeaconComplete) {
                // Ask the listener for the latest information on where the robot is
                if (vuforia.wheelsListener.isVisible()) {
                    latestLocation = vuforia.wheelsListener.getUpdatedRobotLocation();

                    // The listener will sometimes return null, so we check for that to prevent errors
                    if (latestLocation != null)
                        vuforia.lastKnownLocation = latestLocation;

                    // Send information about whether the target is visible, and where the robot is
                    telemetry.addData("Tracking " + vuforia.wheelsTarget.getName(), vuforia.wheelsListener.isVisible());
                    foundTarget = true;
                    drivetoTarget();
                    straighten();
                }
                else if (vuforia.toolsListener.isVisible())
                {
                    OpenGLMatrix latestLocation = vuforia.toolsListener.getUpdatedRobotLocation();

                    // The listener will sometimes return null, so we check for that to prevent errors
                    if (latestLocation != null)
                        vuforia.lastKnownLocation = latestLocation;

                    // Send information about whether the target is visible, and where the robot is
                    telemetry.addData("Tracking " + vuforia.toolsTarget.getName(), vuforia.toolsListener.isVisible());
                    foundTarget = true;
                    drivetoTarget();
                    straighten();
                }
                else if (vuforia.legosListener.isVisible())
                {
                    OpenGLMatrix latestLocation = vuforia.legosListener.getUpdatedRobotLocation();

                    // The listener will sometimes return null, so we check for that to prevent errors
                    if (latestLocation != null)
                        vuforia.lastKnownLocation = latestLocation;

                    // Send information about whether the target is visible, and where the robot is
                    telemetry.addData("Tracking " + vuforia.legosTarget.getName(), vuforia.legosListener.isVisible());
                    foundTarget = true;
                    drivetoTarget();
                    straighten();
                }
                else if (vuforia.gearsListener.isVisible())
                {
                    OpenGLMatrix latestLocation = vuforia.gearsListener.getUpdatedRobotLocation();

                    // The listener will sometimes return null, so we check for that to prevent errors
                    if (latestLocation != null)
                        vuforia.lastKnownLocation = latestLocation;

                    // Send information about whether the target is visible, and where the robot is
                    telemetry.addData("Tracking " + vuforia.gearsTarget.getName(), vuforia.gearsListener.isVisible());
                    foundTarget = true;
                    drivetoTarget();
                    straighten();
                }
                else {
                    OpenGLMatrix latestLocation = vuforia.wheelsListener.getUpdatedRobotLocation();

                    // The listener will sometimes return null, so we check for that to prevent errors
                    if (latestLocation != null)
                        vuforia.lastKnownLocation = latestLocation;

                    // Send information about whether the target is visible, and where the robot is
                    if (!foundTarget) {
                        //ds.driveForward(100, 1);
                        ds.driveDiagonalLeft(100, 1);
                        telemetry.addData("about to straighten", null);
                        straighten();
                        telemetry.addData("done straightening", null);
                    } else {
                        telemetry.addData("no targets in sight", null);
                        if (y > CLOSEST_DISTANCE) {
                            if (CENTER_POSITION < x) {
                                telemetry.addData("Haven't met goal, can't find, turning right", null);
                                ds.turnWithoutGyro(1, 0.2, false);
                            } else {
                                telemetry.addData("Haven't met goal, can't find, turning left", null);
                                ds.turnWithoutGyro(1, 0.2, true);
                            }
                        } else {
                            telemetry.addData("Can't find target, met distance goal", null);
                            ds.drive(0f);
                        }
                    }
                }
            } else {
                pressBeacon();
                //driveToSecondBeacon();
                //pressBeacon();
                telemetry.addData("We are successful!!", null);
            }
            telemetry.update();
            telemetry.addData( "X:"+ vuforia.convertInToMM(vuforia.getXLocation(vuforia.lastKnownLocation)) +"  Y:" +
                    vuforia.convertInToMM(vuforia.getYLocation(vuforia.lastKnownLocation)), 00);
            idle();

            x = vuforia.convertInToMM(vuforia.getXLocation(vuforia.lastKnownLocation));
            y = vuforia.convertInToMM(vuforia.getYLocation(vuforia.lastKnownLocation));

        }
    }
    
    //Below are most of the functions called in the code above

    public void drivetoTarget() {
        if (y > CLOSEST_DISTANCE) {
            if (MAX_LEFT_TARGET >= x) {
                telemetry.addData("have not yet met goal, turning left", null);
                ds.driveDiagonalLeft(100,1);
            } else if (MAX_RIGHT_TARGET <= x) {
                telemetry.addData("have not yet met goal, turning right", null);
                ds.driveDiagonalRight(100,1);
            } else {
                telemetry.addData("have not yet met goal, continue straight", null);
                ds.drive(0.4f);
            }
        } else {
            //firstBeaconComplete = true;
            telemetry.addData("The robot is trying to stop", null);
        }
    }

    public void straighten() {
        if (angleZ < MAX_LEFT_GYRO) {
            ds.turn(0, TURN_SPEED, 2, angleZ);
        } else if (angleZ > MAX_RIGHT_GYRO) {
            ds.turn(0, TURN_SPEED, 2, angleZ);
        } else {
        }
    }

    public void pressBeacon() {
        //Ready to put beacon-pressing code here.
        //This will require color sensor, gyro, beBoop, and driving.
        //Have fun :P
    }

    public void driveToSecondBeacon(){
    }

    public void resetEncoder(){
        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //

        encoderMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        encoderMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d",
                encoderMotor.getCurrentPosition());
    }

    /*
     *  Method to perfmorm a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */
    
    public void encoderDrive(double speed,
                             double leftInches,
                             double timeoutS) {
        int newLeftTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = encoderMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            encoderMotor.setTargetPosition(newLeftTarget);

            // Turn On RUN_TO_POSITION
            encoderMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot9330.leftFrontMotor.setPower(speed);
            robot9330.rightFrontMotor.setPower(-speed);
            robot9330.rightRearMotor.setPower(-speed);
            robot9330.leftRearMotor.setPower(speed);

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    encoderMotor.isBusy() &&
                    encoderMotor.getCurrentPosition() > newLeftTarget) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d", newLeftTarget);
                telemetry.addData("Path2",  "Running at %7d",
                        encoderMotor.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            robot9330.leftFrontMotor.setPower(0);
            robot9330.rightFrontMotor.setPower(0);
            robot9330.rightRearMotor.setPower(0);
            robot9330.leftRearMotor.setPower(0);

            // Turn off RUN_TO_POSITION
            encoderMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }

}
