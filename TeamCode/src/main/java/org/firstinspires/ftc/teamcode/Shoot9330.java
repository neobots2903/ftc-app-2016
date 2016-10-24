//Import packages and whatnot
package org.firstinspires.ftc.teamcode;
        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.eventloop.opmode.OpMode;
        import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by robot on 10/17/2016.
 */

//The shot class
public class Shoot9330 extends LinearOpMode {

    //Predefine motor shotMotor, the motor used for shooting.
    DcMotor shotMotor;

    //Empty constructor
    public Shoot9330 () {
        //Set the motor
        shotMotor = hardwareMap.dcMotor.get("shotMotor");
        //Set the direction of the shootig motor
        shotMotor.setDirection(DcMotor.direction.REVERSE);
    }

    //Shoot function
    public void shoot() {
        //Wait for start
        waitForStart();

        //Set the power to max
        shotMotor.setPower(1);
        //Wait 1 second
        sleep(1000);
        //Set the power to reverse, max
        shotMotor.setPower(-1);
        //Wait 1 second
        sleep(1000);
        //Stop the motor
        shotMotor.setPower(0);

    }

}
github
