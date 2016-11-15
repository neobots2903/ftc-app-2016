package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by NeoBOTS on 11/14/2016.
 */
@TeleOp(name="PickupTest9330", group="Opmode")
public class bigBallPickupTest extends OpMode{
    Hardware9330 robot9330 = new Hardware9330();

    @Override
    public void init(){

    }
    @Override
    public void init_loop(){

    }
    @Override
    public void start(){

    }
    @Override
    public void loop(){
        if(gamepad2.right_bumper){
            robot9330.bigBallPickup.setPower(1.0);
        }
    }
    @Override
    public void stop(){
        robot9330.bigBallPickup.setPower(0.0);
    }
}
