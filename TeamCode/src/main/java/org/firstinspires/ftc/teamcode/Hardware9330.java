package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by John on 10/18/2016.
 */

public class Hardware9330 {
    /* Public OpMode members. */
    public DcMotor leftFrontMotor   = null;
    public DcMotor  rightFrontMotor  = null;
    public DcMotor leftRearMotor   = null;
    public DcMotor  rightRearMotor  = null;

    public DcMotor shotMotor = null;
    public DcMotor pickUpMotor = null;

    public ModernRoboticsI2cGyro gyro = null;
    public DcMotor bigBallPickup = null;

    public Servo  brake    = null;
    public Servo beBoop    = null;
    public Servo bigBallArmServo   = null;

    public ColorSensor lineCSensor = null;
    public ColorSensor BBSensor = null;

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
        leftFrontMotor   = hwMap.dcMotor.get("motorFL");
        rightFrontMotor  = hwMap.dcMotor.get("motorFR");
        leftRearMotor   = hwMap.dcMotor.get("motorBL");
        rightRearMotor  = hwMap.dcMotor.get("motorBR");
        pickUpMotor = hwMap.dcMotor.get("motorPickup");
        beBoop = hwMap.servo.get("beBoop");
        brake = hwMap.servo.get("brake");
        bigBallArmServo = hwMap.servo.get("bigBallArmServo");
        bigBallPickup = hwMap.dcMotor.get("bigBallPickup");

//        lineCSensor = hwMap.colorSensor.get("CSensor");
//        BBSensor = hwMap.colorSensor.get("BBSensor");
//        lineCSensor.setI2cAddress(I2cAddr.create7bit(0x26));
//        BBSensor.setI2cAddress(I2cAddr.create7bit(0x1E));
//        lineCSensor.enableLed(true);
//        BBSensor.enableLed(true);
//        gyro = (ModernRoboticsI2cGyro)hwMap.gyroSensor.get("gyro");


        brake.setDirection(Servo.Direction.REVERSE);

    }

}
