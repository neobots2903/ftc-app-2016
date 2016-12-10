//Import packages and whatnot
package org.firstinspires.ftc.teamcode;
        import com.qualcomm.robotcore.hardware.DcMotor;

        import static java.lang.Thread.sleep;

/**
 * Created by robot on 10/17/2016.
 */

//The shot class
public class Shoot9330 {

    //Predefine motor shotMotor, the motor used for shooting.
    //DcMotor shotMotor;

    private Hardware9330 hwMap = null;

    //Empty constructor
    public Shoot9330 (Hardware9330 robotMap) {
        //Set the motor
//        shotMotor = Hardware9330.dcMotor.get("shotMotor");
        //Set the direction of the shootig motor
        hwMap = robotMap;
        hwMap.shotMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    //Shoot function
    public void shoot() throws InterruptedException {
        //Wait for start
       // waitForStart();
        if (hwMap != null) {
            /*
            //Set the power to max
            hwMap.shotMotor.setPower(1);
            //Wait 1 second
            sleep(1000);
            //Set the power to reverse, max
            hwMap.shotMotor.setPower(-1);
            //Wait 1 second
            sleep(1000);
            //Stop the motor
            hwMap.shotMotor.setPower(0);
            */
        }

    }

}
