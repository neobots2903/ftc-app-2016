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
        hwMap.shotMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hwMap.shotMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    //Shoot function
    public void shoot() throws InterruptedException {
        //Wait for start
       // waitForStart();
        if (hwMap != null) {
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
        }

    }

    public void shootEncoderReset() {
        hwMap.shotMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public boolean isShootBusy() {
        return hwMap.shotMotor.isBusy();
    }
    public void startShootWithEncoder() {

        hwMap.shotMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hwMap.shotMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hwMap.shotMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hwMap.shotMotor.setTargetPosition(1440);
        hwMap.shotMotor.setPower(.8);
    }

    public void stopShootWithEncoder() {
        hwMap.shotMotor.setPower(0);
        hwMap.shotMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hwMap.shotMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

}
