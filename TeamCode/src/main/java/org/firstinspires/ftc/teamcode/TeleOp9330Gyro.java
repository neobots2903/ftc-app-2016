/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 * <p>
 * This particular OpMode just executes a basic Tank Drive Teleop for a PushBot
 * It includes all the skeletal structure that all iterative OpModes contain.
 * <p>
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name = "TeleOp9330Gyro", group = "Opmode")  // @Autonomous(...) is the other common choice
//@Disabled
public class TeleOp9330Gyro extends OpMode {
    Hardware9330 robot9330 = new Hardware9330();

    double xPower = 0;
    double yPower = 0;
    double spinPower = 0;

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();


    Brake9330 brake = null;
    private long lastBrakeChange;
    private boolean buttonBReleased = true;
    private boolean armSet = true;


    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        /* eg: Initialize the hardware variables. Note that the strings used here as parameters
         * to 'get' must correspond to the names assigned during the robot configuration
         * step (using the FTC Robot Controller app on the phone).
         */
        robot9330.init(hardwareMap);


        telemetry.addData("Status", "Calibrating gyro");
        telemetry.update();
        robot9330.gyro.calibrate();
        while (robot9330.gyro.isCalibrating()) {
        }

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        brake = new Brake9330(robot9330);
        brake.releaseBrake();
        lastBrakeChange = System.currentTimeMillis();

    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }


    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        telemetry.addData("Status", "Running: " + runtime.toString());

        // omniwheel X drive:
        //        X FRONT X
        //      X           X
        //    X  P1       P2  X
        //            X
        //           XXX
        //            X
        //    X  P4       P3  X
        //      X           X
        //        X       X

        xPower = gamepad1.left_stick_x;
        yPower = gamepad1.left_stick_y;
        spinPower = gamepad1.right_stick_x;

        robot9330.leftFrontMotor.setPower(-yPower + xPower - spinPower);
        robot9330.rightFrontMotor.setPower(yPower + xPower - spinPower);
        robot9330.rightRearMotor.setPower(yPower - xPower - spinPower);
        robot9330.leftRearMotor.setPower(-yPower - xPower - spinPower);

        int heading = robot9330.gyro.getHeading();
        telemetry.addData("Gyro Heading = ", heading);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        robot9330.leftFrontMotor.setPower(0);
        robot9330.rightFrontMotor.setPower(0);
        robot9330.rightRearMotor.setPower(0);
        robot9330.leftRearMotor.setPower(0);
    }

}
