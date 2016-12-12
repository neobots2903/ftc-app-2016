package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * This OpMode scans a single servo back and forwards until Stop is pressed.
 * The code is structured as a LinearOpMode
 * INCREMENT sets how much to increase/decrease the servo position each cycle
 * CYCLE_MS sets the update period.
 * <p>
 * This code assumes a Servo configured with the name "left claw" as is found on a pushbot.
 * <p>
 * NOTE: When any servo position is set, ALL attached servos are activated, so ensure that any other
 * connected servos are able to move freely before running this test.
 * <p>
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */
@Autonomous(name = "autoBeaconTest", group = "Concept")
public class autoBeaconTest extends LinearOpMode {

    static final double INCREMENT = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final int CYCLE_MS = 50;     // period of each cycle
    static final double MAX_POS = 360;     // Maximum rotational position
    static final double MIN_POS = 0.0;     // Minimum rotational position

    // Define class members
    double position = (MAX_POS - MIN_POS) / 2; // Start at halfway position
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
        telemetry.addData(">", "Press Start to test gyro.");
        telemetry.update();
        waitForStart();

        /*
                                  1 1 1
              0 1 2 3 4 5 6 7 8 9 0 1 2
             0+-+-+-+-+-+-+-+-+-+-+-+-+
              |BBBBBB | R |   |   |  /|  Robot starts here and moves diagonally, while moving
             1+BBBBB  +R  +   +   + / +  downfield 48"
              |BBBB   R   |   |   |/  |
             2+BBB+-+R+-+-+-+-+-+-/-+-+
              |BB | R |   |   |  /|   |
             3+B  +R  +   +   + / +   +
              |   R   |   |   |/  |   |
             4+-+R+-+-+-+-+-+-/-+-+-+-+  When it reaches here, it begins to move forward
              |   |   |   |  /|   |   |  until it discovers the line.  Where it rotates
             5KLLLL   + +---+ +   +   +  90 degres to the right and moves forward
              |   |   | |***| |   |   |  18 inches.  Then detects the color and determines
             6+-+-+-+-+-|***|-+-+-+-+-+  if the beacon(K) needs to be activated.
              |   |   | |***| |   |   |
             7+   |   + +---+ +   +   +
              |   |   |/  |   |   |   | If we have time, the robot will reverse 12 inches,
             8+-+-+-+-/-+-+-+-+-+-+-+-+ rotate 90 degrees to the left and move forward
              |   |  /|   |   |   |   | until it finds the next line, where it will rotate
             9KLLLL / +   +   +   +  R+ 90 degrees to the right, move forward 12 inches or until
              |   |/  |   |   |   | RR| the touch sensor is activated.  Then detects the beacon
            10+-+-/-L-+---+-L-+-+-+RRR+ color and activates the beacon if necessary.
              |  /| L |   | L |   RRRR|
            11+ / + L +   + L +  RRRRR+
              |/  | L |   | L | RRRRRR|
            12+-+-+-K-+---+-K-+-+-+-+-+
        */

        // move forward until line detected.
        while (opModeIsActive()) {


            // move diagonally towards the red team beacons
            ds.driveDiagonalLeftDistance(48, 0.2f);
            while (hwMap.ODS.getLightDetected() < 0.0189) { //value continuously checked
                ds.moveForward(0.2f, 0.2f);
            }
            ds.moveForward(0, 0);
            ds.turn(90, .5f, 3);
            ds.driveDistance(18, .3f);


            // move to next beacon
            ds.driveDistance(12, -0.5f);
            ds.turn(-90, 0.5f, 3);
            ds.driveDistance(36, 0.5f);

            while (hwMap.ODS.getLightDetected() < 0.0189) { //value continuously checked
                ds.moveForward(0.2f, 0.2f);
            }
            ds.moveForward(0, 0);
            ds.turn(90, .5f, 3);
            ds.driveDistance(12, .3f);

            // beacon stuff here --
            //      set retries to 0
            //      while color not correct and retries less than 3
            //          detect color
            //          backup 1 inch
            //          move bboop to correct side
            //          move forward 1 inch
            //          increment retries
            //      end while

            // to middle pedestal
            ds.driveDistance(18, -0.5f);
            ds.turn(90, 0.5f, 3);
            ds.driveDiagonalRightDistance(48, 0.5f);


            ds.moveForward(0, 0);
        }

        // Signal done;
        telemetry.addData(">", "Done");
        telemetry.update();
    }
}
