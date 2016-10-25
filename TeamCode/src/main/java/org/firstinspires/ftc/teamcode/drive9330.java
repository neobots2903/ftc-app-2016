package org.firstinspires.ftc.teamcode;

/**
 * Created by NeoBOTS on 10/17/2016.
 *
 * This needs to contain the methods for use by autonomous mode or
 * any automatic functions that the drive team requests, i.e., press button 'A'
 * and the robot spins 90 degrees and shoots the ball.
 *
 * Some possible methods
 * public void init(Hardware9330 robot9330)
 * public void turn(float degrees)
 * public void forward(float speed)
 * public void stop()
 *
 * etc.
 *
 */

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Hardware;

public class drive9330 {

    Hardware9330 robot9330 = null;

    public drive9330(Hardware9330 robot9330) {
        this.robot9330 = robot9330;
    }

    public void init() {
    }

    public void init_loop() {
    }

    public void start() {

    }

    public void loop(){

    }

    public void stop(){

    }
 }
