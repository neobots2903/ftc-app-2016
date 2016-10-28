package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import static java.lang.Thread.sleep;

/**
 * Created by robot on 10/17/2016.
 */

//PickUp class
public class PickUp9330 {

    //grab hardware map
    private Hardware9330 hwMap = null;

    //code to include motor goes here
    DcMotor pickUpMotor;
    boolean pickUpButton; //gamepad button code;


    boolean pickUpOpen = false;

    //Empty constructor
    public PickUp9330 (Hardware9330 robotMap) {
        //Set the motor
//        shotMotor = Hardware9330.dcMotor.get("shotMotor");
        //Set the direction of the shootig motor
        hwMap = robotMap;
        hwMap.pickUpMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void pickup() throws InterruptedException{
        if (pickUpButton == true){

            //open "scissors" (motor power + and stop)
            pickUpMotor.setPower(1); //turn on motor
            sleep(1000); //time in milliseconds motor is running
            pickUpOpen = true;

            while (pickUpButton == true && pickUpOpen) {
                pickUpMotor.setPower(0); //stop motor and keep it open
            }

        } else {
            //keep "scissors" closed (motor power - constantly)
            pickUpMotor.setPower(-1);
            pickUpOpen = false;
        }
    }
}

//:'(  I have so much more to do