package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static java.lang.Math.abs;

/**
 * This OpMode scans a single servo back and forwards until Stop is pressed.
 * The code is structured as a LinearOpMode
 * INCREMENT sets how much to increase/decrease the servo position each cycle
 * CYCLE_MS sets the update period.
 *
 * This code assumes a Servo configured with the name "left claw" as is found on a pushbot.
 *
 * NOTE: When any servo position is set, ALL attached servos are activated, so ensure that any other
 * connected servos are able to move freely before running this test.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */
@Autonomous(name = "Simple Concept Gyro TWO", group = "Concept")
public class simpleConceptGyroTWO extends LinearOpMode {

    // Define class members
    Hardware9330 hwMap = new Hardware9330();

    //   PID9330 gyroPID;

    ModernRoboticsI2cGyro gyro;


    @Override
    public void runOpMode() {

          // Gyro integrated heading
        int angleZ = 0;

        hwMap.init(hardwareMap);

        gyro = (ModernRoboticsI2cGyro)hardwareMap.get("gyro");

        telemetry.addData(">", "Starting Calibration! Please wait!");
        telemetry.update();

        gyro.calibrate();

        // Wait for gyro to finish calibrating
        while (!isStopRequested() && gyro.isCalibrating())  {
            sleep(50);
            idle();
        }
        gyro.resetZAxisIntegrator();

        // Wait for the start button
        telemetry.addData(">", "Press Start to test gyro." );
        telemetry.update();
        waitForStart();


        // Scan turn 90 till stop pressed.
        while(opModeIsActive()) {
            while (opModeIsActive()) {

                angleZ  = gyro.getIntegratedZValue();

                telemetry.addData("Int. Ang. ", angleZ);
                telemetry.update();
            }
        }

        // Signal done;
        telemetry.addData(">", "Done");
        telemetry.update();
    }
}
