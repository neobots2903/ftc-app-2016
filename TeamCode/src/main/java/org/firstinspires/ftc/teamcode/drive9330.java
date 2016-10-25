package org.firstinspires.ftc.teamcode;

/**
 * Created by NeoBOTS on 10/17/2016.
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


        robot9330.leftFrontMotor.setPower(-leftY1 - leftX1 - rightX1);
        robot9330.rightFrontMotor.setPower(leftY1 - leftX1 - rightX1);
        robot9330.rightRearMotor.setPower(leftY1 + leftX1 - rightX1);
        robot9330.leftRearMotor.setPower(-leftY1 + leftX1 - rightX1);
    }

    public void start() {

    }

    public void loop(){

    }

    public void stop(){

    }
 }
