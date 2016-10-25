package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by John on 10/18/2016.
 */

public class Hardware9330 {
    /* Public OpMode members. */
    public DcMotor leftFrontMotor   = null;
    public DcMotor  rightFrontMotor  = null;
    public DcMotor leftRearMotor   = null;
    public DcMotor  rightRearMotor  = null;

    public GyroSensor gyro = null;
    public Servo  brake    = null;
    public Servo beBoop    = null;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    public Hardware9330() {
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftFrontMotor   = hwMap.dcMotor.get("left_front_drive");
        rightFrontMotor  = hwMap.dcMotor.get("right_front_drive");
        leftRearMotor   = hwMap.dcMotor.get("left_rear_drive");
        rightRearMotor  = hwMap.dcMotor.get("right_rear_drive");

        gyro = hwMap.gyroSensor.get("gyro");

//        armMotor    = hwMap.dcMotor.get("left_arm");
//        leftMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
//        rightMotor.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
//
//        // Set all motors to zero power
//        leftMotor.setPower(0);
//        rightMotor.setPower(0);
//        armMotor.setPower(0);
//
//        // Set all motors to run without encoders.
//        // May want to use RUN_USING_ENCODERS if encoders are installed.
//        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

//        // Define and initialize ALL installed servos.
//        brake = hwMap.servo.get("brake");
//        beBoop = hwMap.servo.get("beBoop");
//        brake.setPosition(MID_SERVO);
//        beBoop.setPosition(MID_SERVO);

    }

}
