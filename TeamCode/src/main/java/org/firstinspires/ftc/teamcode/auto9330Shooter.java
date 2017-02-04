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
    drive9330 ds = null;
    Brake9330 brake = null;
    Shoot9330 shooter ;

    @Override
    public void runOpMode() throws InterruptedException {
        robot9330.init(hardwareMap);
        shooter = new Shoot9330(robot9330);

        // instantiate and initialize drive subsystem
        ds = new drive9330(robot9330);
        ds.init(true);

        // instantiate and initialize brake subsystem
        brake = new Brake9330(robot9330);
        brake.releaseBrake();

        sleep(10000);
        ds.driveDiagonalLeft(1000, 1);

        ds.turn(90, .5f, 3, 1);
        ds.turn(-180, .5f, 3, 1);

        shooter.shoot();

        // drive straight 24 inches
        ds.driveDistance(24, 1);
    }



}
