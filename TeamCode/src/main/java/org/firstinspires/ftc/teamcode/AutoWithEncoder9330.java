package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by NeoBOTS on 11/4/2016.
 */

@Autonomous(name= "AutoEncoder9330", group ="Opmode")
public class AutoWithEncoder9330 extends LinearOpMode {

    Hardware9330 robot9330 = new Hardware9330();
    private ElapsedTime runtime = new ElapsedTime();
    drive9330 ds = new drive9330();
    private Hardware9330 hwMap = null;

    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.14159);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;
    DcMotor encoderMotor = null;


    @Override
    public void runOpMode() throws InterruptedException {
        hwMap = new Hardware9330();
        hwMap.init(hardwareMap);
        ds.init(hwMap);
        ds.reset();
        ds.setTime(100);
        robot9330.init(hardwareMap);
        Brake9330 brake = new Brake9330(hwMap);
        brake.releaseBrake();


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

        encoderDrive(DRIVE_SPEED, 24, 5.0); // drive forward 24 inches with 5 second timeout

        telemetry.addData("Path", "Complete");
        telemetry.update();

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
            robot9330.leftFrontMotor.setPower(-speed);
            robot9330.rightFrontMotor.setPower(speed);
            robot9330.rightRearMotor.setPower(speed);
            robot9330.leftRearMotor.setPower(-speed);

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    encoderMotor.isBusy()) {

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