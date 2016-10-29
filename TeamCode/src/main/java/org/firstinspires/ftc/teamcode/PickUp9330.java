package org.firstinspires.ftc.teamcode;
    import com.qualcomm.robotcore.hardware.DcMotor;
    import com.qualcomm.robotcore.eventloop.opmode.OpMode;
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
    boolean pickUpButton = gamepad2.button.x; //gamepad button code;
    boolean pickUpEjectButton = gamepad2.button.y; //gamepad button code;

    //Empty constructor
    public PickUp9330(Hardware9330 robotMap) {
        //Set the motor
//        pickUpMotor = Hardware9330.dcMotor.get("pickUpMotor");
        //Set the direction of the pickup motor
        hwMap = robotMap;
        hwMap.pickUpMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void pickUp() {
        if (pickUpButton == true) {
            pickUpMotor.setPower(1);
        } else {
            pickUpMotor.setPower(0);
        }
    }

    public void pickUpEject() {
        if (pickUpEjectButton == true) {
            pickUpMotor.setPower(-1);
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