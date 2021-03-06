package org.firstinspires.ftc.teamcode;
    import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
    import com.qualcomm.robotcore.hardware.DcMotor;
    import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Jakob and Conner on 10/17/2016.
 */

//PickUp class
public class PickUp9330 {

    //grab hardware map
    private Hardware9330 hwMap = null;

    //grab pickUpMotor from hwMap
    public PickUp9330(Hardware9330 robotMap) {
        hwMap = robotMap;
        //Set the direction of the pickup motor
        hwMap.pickUpMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void pickUp() {
        if (hwMap != null) {
            hwMap.pickUpMotor.setPower(1);
        }
    }

    public void pickUpEject() {
        if (hwMap != null) {
            hwMap.pickUpMotor.setPower(-1);
        }
    }

    public void stopPickUp() {
        if (hwMap != null) {
            hwMap.pickUpMotor.setPower(0);
        }
    }

    /** <-- delete this to uncomment

     This was going to be code for pickup, but design was changed. Keeping just in case.

     public void pickUpOpen() throws InterruptedException {
     if (hwMap != null) {
     //open "scissors" (motor power + and stop)
     pickUpMotor.setPower(1); //turn on motor
     sleep(1000); //time in milliseconds motor is running
     pickUpMotor.setPower(0); //stops motor
     }
     }

     public void pickUpClose() {
     if (hwMap != null) {
     //keep "scissors" closed (motor power - constantly)
     pickUpMotor.setPower(-1);
     }
     }

     //If you are reading this WHY because this code

     public void pickup() throws InterruptedException {
     if (pickUpButton == true) {
     pickUpOpen();
     while (pickUpButton == true) {
     pickUpMotor.setPower(0);
     }
     } else {
     pickUpClose();
     }
     }
     **/
}

//hello