/**
 * Created by NeoBOTS on 10/17/2016.
 */

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class drive9330 {

    public void init() {
        DcMotor motorFL;
        DcMotor motorFR;
        DcMotor motorBL;
        DcMotor motorBR;

        double leftY1 = gamepad1.left_stick_y;
        double leftX1 = gamepad1.left_stick_x;
        double rightX1 = gamepad1.right_stick_x;

    }

    public void init_loop() {

        motorFL = hardwareMap.dcMotor.get("motorFL");
        motorFR = hardwareMap.dcMotor.get("motorFR");
        motorBL = hardwareMap.dcMotor.get("motorBL");
        motorBR = hardwareMap.dcMotor.get("motorBR");

        motorFL.setPower(-leftY1 - leftX1 - rightX1);
        motorFR.setPower(leftY1 - leftX1 - rightX1);
        motorBR.setPower(leftY1 + leftX1 - rightX1);
        motorBL.setPower(-leftY1 + leftX1 - rightX1);
    }

    public void start() {

    }

    public void loop(){

    }

    public void stop(){

    }
 }
