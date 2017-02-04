package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static java.lang.Math.abs;

@Autonomous(name = "Simple Concept Gyro", group = "Concept")
public class simpleConceptGyro extends LinearOpMode {

    Hardware9330 robot9330 = new Hardware9330();

    double currentGyroPos;

    ModernRoboticsI2cGyro gyro;

    @Override
    public void runOpMode() {

        robot9330.init(hardwareMap);
        gyro = (ModernRoboticsI2cGyro)hardwareMap.get("gyro");

        currentGyroPos = 0;

        telemetry.addData(">", "Starting Calibration! Please wait!");
        telemetry.update();

        gyro.calibrate();

        while (!isStopRequested() && gyro.isCalibrating())  {
            sleep(50);
            idle();
        }
        gyro.resetZAxisIntegrator();

        // Wait for the start button
        telemetry.addData(">", "Finished Calibration! Press Start to test gyro." );
        telemetry.update();
        waitForStart();

        // Scan turn 90 till stop pressed.
        while(opModeIsActive()) {
                // current gyro settings
                currentGyroPos = gyro.getHeading();
                if (currentGyroPos > 180) {
                    currentGyroPos -= 360;
                }

                telemetry.addData("Current Gyro Position: ", currentGyroPos);
                telemetry.update();
        }

        // Signal done;
        telemetry.addData(">", "Done");
        telemetry.update();
    }
}
