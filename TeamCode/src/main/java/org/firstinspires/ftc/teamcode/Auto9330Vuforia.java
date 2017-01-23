package org.firstinspires.ftc.teamcode;

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
    Vuforia9330 vuforia = new Vuforia9330();

    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 3.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.14159);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;
    static final double     MAX_LEFT                = 72;
    static final double     MAX_RIGHT               = 80;
    static final double     CENTER_ANGLE            = 77;
    static final double     CLOSEST_DISTANCE        = 15;

    DcMotor encoderMotor = null;
    OpenGLMatrix latestLocation;
    double x;
    double y;
    boolean turnDirection;

    @Override
    public void runOpMode() throws InterruptedException {
        robot9330.init(hardwareMap);
        vuforia.setupVuforia();


        // instantiate and initialize drive subsystem
        ds = new drive9330(robot9330);
        ds.init(false);
        ds.reset();
        ds.setTime(100);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        encoderMotor = robot9330.bigBallPickup;
        encoderMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        encoderMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d",
                encoderMotor.getCurrentPosition());
        telemetry.update();

        waitForStart();

        vuforia.init();
        while(opModeIsActive())
        {
            // Ask the listener for the latest information on where the robot is
            if (vuforia.wheelsListener.isVisible()) {
                latestLocation = vuforia.wheelsListener.getUpdatedRobotLocation();

                // The listener will sometimes return null, so we check for that to prevent errors
                if (latestLocation != null)
                    vuforia.lastKnownLocation = latestLocation;

                // Send information about whether the target is visible, and where the robot is
                telemetry.addData("Tracking " + vuforia.wheelsTarget.getName(), vuforia.wheelsListener.isVisible());
                drivetoTarget();
            }
            else if (vuforia.toolsListener.isVisible())
            {
                OpenGLMatrix latestLocation = vuforia.toolsListener.getUpdatedRobotLocation();

                // The listener will sometimes return null, so we check for that to prevent errors
                if (latestLocation != null)
                    vuforia.lastKnownLocation = latestLocation;

                // Send information about whether the target is visible, and where the robot is
                telemetry.addData("Tracking " + vuforia.toolsTarget.getName(), vuforia.toolsListener.isVisible());
                drivetoTarget();
            }
            else if (vuforia.legosListener.isVisible())
            {
                OpenGLMatrix latestLocation = vuforia.legosListener.getUpdatedRobotLocation();

                // The listener will sometimes return null, so we check for that to prevent errors
                if (latestLocation != null)
                    vuforia.lastKnownLocation = latestLocation;

                // Send information about whether the target is visible, and where the robot is
                telemetry.addData("Tracking " + vuforia.legosTarget.getName(), vuforia.legosListener.isVisible());
                drivetoTarget();
            }
            else if (vuforia.gearsListener.isVisible())
            {
                OpenGLMatrix latestLocation = vuforia.gearsListener.getUpdatedRobotLocation();

                // The listener will sometimes return null, so we check for that to prevent errors
                if (latestLocation != null)
                    vuforia.lastKnownLocation = latestLocation;

                // Send information about whether the target is visible, and where the robot is
                telemetry.addData("Tracking " + vuforia.gearsTarget.getName(), vuforia.gearsListener.isVisible());
                drivetoTarget();
            }
            else
            {
                OpenGLMatrix latestLocation = vuforia.wheelsListener.getUpdatedRobotLocation();

                // The listener will sometimes return null, so we check for that to prevent errors
                if (latestLocation != null)
                    vuforia.lastKnownLocation = latestLocation;

                // Send information about whether the target is visible, and where the robot is
                telemetry.addData("no targets in sight", null);
                if (y > CLOSEST_DISTANCE) {
                    if (CENTER_ANGLE > x) {
                        ds.turn(2, 0.5f, 2);
                    } else if (CENTER_ANGLE < x) {
                        ds.turn(-2, 0.5f, 2);
                    } else {
                        ds.turn(-5, 0.5f, 2);
                        turnDirection = !turnDirection;
                    }
                }
                ds.drive(0f);
            }
            telemetry.update();
            telemetry.addData( "X:"+ vuforia.convertInToMM(vuforia.getXLocation(vuforia.lastKnownLocation)) +"  Y:" +
                    vuforia.convertInToMM(vuforia.getYLocation(vuforia.lastKnownLocation)), 00);
            idle();

            x = vuforia.convertInToMM(vuforia.getXLocation(vuforia.lastKnownLocation));
            y = vuforia.convertInToMM(vuforia.getYLocation(vuforia.lastKnownLocation));

        }
        //sleep(10000);
        //encoderDrive(DRIVE_SPEED, -36, 5.0); // drive forward 36 inches with 5 second timeout
        //(destination inches must be negative; I know, it's backwards :P)

        //telemetry.addData("Path", "Complete");
        //telemetry.update();
    }
    /*
     *  Method to perfmorm a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */
    public void drivetoTarget() {
        if (y > CLOSEST_DISTANCE) {
            if (MAX_LEFT >= x) {
                ds.turn(2, 0.5f, 2);
            } else if (MAX_RIGHT <= x) {
                ds.turn(-2, 0.5f, 2);
            } else {
                ds.drive(0.4f);
            }
        }
    }

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