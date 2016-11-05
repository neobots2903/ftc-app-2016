package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by NeoBOTS on 11/4/2016.
 */

@Autonomous(name= "Auto9330", group ="Opmode")
public class Auto9330 extends OpMode {

    Hardware9330 robot9330 = new Hardware9330();
    private ElapsedTime runtime = new ElapsedTime();
    drive9330 ds = new drive9330();

    @Override
    public void init(){
        ds.reset();
        ds.setTime(100);
        robot9330.init(hardwareMap);


    }
    @Override
    public void init_loop(){


    }
    @Override
    public void loop() {
        ds.drive();
    }
    @Override
    public void stop() {
        robot9330.leftFrontMotor.setPower(0);
        robot9330.rightFrontMotor.setPower(0);
        robot9330.rightRearMotor.setPower(0);
        robot9330.leftRearMotor.setPower(0);
    }
}