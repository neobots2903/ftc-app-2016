package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by NeoBOTS on 11/21/2016.
 */
@Autonomous(name= "auto9330Shooter", group ="Opmode")
public class auto9330Shooter extends LinearOpMode {

    Hardware9330 robot9330 = new Hardware9330();
    private ElapsedTime runtime = new ElapsedTime();
    drive9330 ds = new drive9330();
    Shoot9330 shooter = new Shoot9330(robot9330);

    @Override
    public void runOpMode() throws InterruptedException {
        robot9330.init(hardwareMap);
        ds.init(robot9330);
        Brake9330 brake = new Brake9330(robot9330);
        brake.releaseBrake();

        sleep(10000);
        ds.drive(1000);

        shooter.shoot();

        ds.drive(750);
    }



}
