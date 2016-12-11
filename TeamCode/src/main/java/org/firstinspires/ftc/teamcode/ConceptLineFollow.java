package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

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
@Autonomous(name = "Concept: MRGyro", group = "Concept")
public class ConceptLineFollow extends LinearOpMode {

    static final double INCREMENT   = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final int    CYCLE_MS    =   50;     // period of each cycle
    static final double MAX_POS     =  360;     // Maximum rotational position
    static final double MIN_POS     =  0.0;     // Minimum rotational position

    // Define class members
    double  position = (MAX_POS - MIN_POS) / 2; // Start at halfway position
    boolean rampUp = true;
    Hardware9330 hwMap = new Hardware9330();

 //   PID9330 gyroPID;

    double gyroError;
    double targetGyroPos;
    double currentGyroPos;
    double motorPower;
    double driveSteering;
    double driveGain;
    double leftPower;
    double rightPower;

    drive9330 ds;

    @Override
    public void runOpMode() {

        hwMap.init(hardwareMap);

        // Wait for the start button
        telemetry.addData(">", "Press Start to test gyro." );
        telemetry.update();
        waitForStart();


        // move forward until line detected.
        while(opModeIsActive()) {

            waitForStart();
            while(hwMap.ODS.getLightDetected() < 0.0189) { //value continuously checked
                ds.moveForward(0.2f, 0.2f);
            }

            // need to turn
            ds.setRightSpeed(-0.3f);
            sleep(2000);
            ds.setLeftSpeed(0.3f);

            sleep(500);

            ds.moveForward(0, 0);
        }

        // Signal done;
        telemetry.addData(">", "Done");
        telemetry.update();
    }
}
