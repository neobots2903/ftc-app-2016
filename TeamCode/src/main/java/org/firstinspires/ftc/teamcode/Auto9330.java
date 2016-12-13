package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by NeoBOTS on 11/4/2016.
 */

@Autonomous(name= "Auto9330", group ="Opmode")
public class Auto9330 extends LinearOpMode {


    Hardware9330 robot9330 = new Hardware9330();
    private ElapsedTime runtime = new ElapsedTime();
    drive9330 ds = new drive9330();

    @Override
    public void runOpMode() throws InterruptedException {


        // not needed because we rewrote drive to just drive
        // until completed.
        // ds.reset();
        // ds.setTime(100);
        robot9330.init(hardwareMap);
        ds.init(robot9330, true);
        Brake9330 brake = new Brake9330(robot9330);
        brake.releaseBrake();

        wait(10000);
        ds.driveDistance(24, 0.8f);
        ds.turn(90, 0.8f, 3);
        ds.driveDistance(12, 0.8f);
        ds.driveDiagonalLeftDistance(12, 0.8f);

        // this is not needed -- drive(1500) should stop the motors when finished
        // ds.drive();
    }

  /*    @Override
    public void init(){
        hwMap = new Hardware9330();
        hwMap.init(hardwareMap);
        ds.init(hwMap);
        ds.reset();
        ds.setTime(100);
        robot9330.init(hardwareMap);
        Brake9330 brake = new Brake9330(hwMap);
        brake.releaseBrake();

    }

  @Override
    public void init_loop(){


    }
    @Override
    public void loop() {

        // something like this, but more like
        ds.drive(1000);
        // where you pass how long you want to drive
        // to the drive method on drive9330.  
        // now go over to drive9330.java and look at what
        // I was thinking there. 
        ds.drive();
    }
    @Override
    public void stop() {
        robot9330.leftFrontMotor.setPower(0);
        robot9330.rightFrontMotor.setPower(0);
        robot9330.rightRearMotor.setPower(0);
        robot9330.leftRearMotor.setPower(0);
    }


    // we tried :(
        // Okay, so what I was thinking was not here, but in drive9330...  and then in the loop code 
        // see my comments above.

    // WOOPS IGNORE THIS
    // public void driveForwardOneSecond() {
    //     setTime(long 1000);
    //     drive();
    // }*/


}