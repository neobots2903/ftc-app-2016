package org.firstinspires.ftc.teamcode;

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
@Autonomous(name = "Concept: MRGyro", group = "Concept")
public class ConceptMRGyro9330 extends LinearOpMode {

    static final double INCREMENT   = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final int    CYCLE_MS    =   50;     // period of each cycle
    static final double MAX_POS     =  360;     // Maximum rotational position
    static final double MIN_POS     =  0.0;     // Minimum rotational position

    // Define class members
    double  position = (MAX_POS - MIN_POS) / 2; // Start at halfway position
    boolean rampUp = true;
    Hardware9330 hwMap = new Hardware9330();

    PID9330 gyroPID;

    double targetGyroPos;
    double currentGyroPos;
    double motorPower;

    static double aggressiveKP = 4;
    static double aggressiveKI = 0.2;
    static double aggressiveKD = 1;

    static double conservativeKP = 1;
    static double conservativeKI = 0.05;
    static double conservativeKD = 0.25;

    static boolean setConservative = false;
    static boolean setAggressive = false;



    @Override
    public void runOpMode() {

        int xVal, yVal, zVal = 0;     // Gyro rate Values
        int heading = 0;              // Gyro integrated heading
        int angleZ = 0;

        hwMap.init(hardwareMap);

        // setup
        targetGyroPos = 90;
        currentGyroPos = 0;
        motorPower = 0;

        hwMap.gyro.calibrate();

        while (!isStopRequested() && hwMap.gyro.isCalibrating())  {
            sleep(50);
            idle();
        }

        gyroPID = new PID9330(conservativeKP, conservativeKI, conservativeKD, PID9330.TUNING_DIRECTION.DIRECT);

        // set initial currentGyroPos and motorPower values
        gyroPID.setInput(currentGyroPos);

        // set target value (targetGyroPos)
        gyroPID.setSetpoint(targetGyroPos);

        // set minimum and maximum motorPower -- for example power to motors
        gyroPID.setOutputLimits(-100, 100);

        // enable the PID
        gyroPID.setMode(PID9330.OPERATING_MODE.AUTOMATIC);

        // Wait for the start button
        telemetry.addData(">", "Press Start to scan Servo." );
        telemetry.update();
        waitForStart();

        // Scan turn 90 till stop pressed.
        while(opModeIsActive()) {
            while (opModeIsActive() && currentGyroPos < targetGyroPos && motorPower > 2) {

                // current gyro settings
                currentGyroPos = hwMap.gyro.getHeading();
                if (currentGyroPos > 180) {
                    currentGyroPos -= 360;
                }

                // see if we are getting close
                double gap = targetGyroPos - currentGyroPos;
                if (abs(gap) < 10) {
                    if (!setConservative) {
                        // we're getting close to the goal
                        gyroPID.setTunings(conservativeKP, conservativeKI, conservativeKD);
                        setConservative = false;
                    }
                }
                else {
                    if (!setAggressive) {
                        // use aggressive values
                        gyroPID.setTunings(aggressiveKP, aggressiveKI, aggressiveKD);
                        setAggressive = false;
                    }
                }

                // compute the power to provide to motors
                motorPower = gyroPID.compute(currentGyroPos);
                if (motorPower > 0)
                    motorPower /= 100.0;

                // set motor speeds
                hwMap.leftFrontMotor.setPower(motorPower);
                hwMap.rightFrontMotor.setPower(motorPower);
                hwMap.leftRearMotor.setPower(motorPower);
                hwMap.rightRearMotor.setPower(motorPower);

                // get the x, y, and z values (rate of change of angle).
                xVal = hwMap.gyro.rawX();
                yVal = hwMap.gyro.rawY();
                zVal = hwMap.gyro.rawZ();

                // get the heading info.
                // the Modern Robotics' gyro sensor keeps
                // track of the current heading for the Z axis only.
                heading = hwMap.gyro.getHeading();
                // angleZ  = hwMap.gyro.getIntegratedZValue();

                telemetry.addData(">", "Press A & B to reset Heading.");
                telemetry.addData("0", "Heading %03d", heading);
                telemetry.addData("1", "Int. Ang. %03d", angleZ);
                telemetry.addData("2", "X av. %03d", xVal);
                telemetry.addData("3", "Y av. %03d", yVal);
                telemetry.addData("4", "Z av. %03d", zVal);
                telemetry.update();
            }
        }

        // Signal done;
        telemetry.addData(">", "Done");
        telemetry.update();
    }
}
