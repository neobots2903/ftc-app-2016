package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import static java.lang.System.currentTimeMillis;
import static java.lang.Thread.sleep;

/**
 * Created by NeoBOTS on 10/17/2016.
 *
 * This needs to contain the methods for use by autonomous mode or
 * any automatic functions that the drive team requests, i.e., press button 'A'
 * and the robot spins 90 degrees and shoots the ball.
 *
 * Some possible methods
 * public void init(Hardware9330 robot9330)
 * public void turn(float degrees)
 * public void forward(float speed)
 * public void stop()
 *
 * etc.
 *
 */



public class drive9330{

    int zAccumulated;  //Total rotation left/right
    int target = 0;  //Desired angle to turn to

    Hardware9330 robot9330 = null;
    long targetTime = 0;
    float motorSpeed;
    ModernRoboticsI2cGyro gyro;
    private boolean gyroInitialized;

    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 3.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.14159);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;
    double angleZ;
    DcMotor encoderMotor = null;

    public drive9330(Hardware9330 robotmap) {
        robot9330 = robotmap;
    }

    public void init () {
        init(false);
    }

    public void init(boolean initGyro) {

        // only initialize the gyro if requested to do
        gyroInitialized = initGyro;
        if (initGyro) {
            gyro = robot9330.gyro;

            gyro.calibrate();

            while (gyro.isCalibrating()) {
            }
        }

        encoderMotor = robot9330.bigBallPickup;
        encoderMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        encoderMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void driveForward(int time, double speed) {
        if(time > 0){
            targetTime = currentTimeMillis() + time;
            robot9330.leftFrontMotor.setPower(-speed);
            robot9330.rightFrontMotor.setPower(speed);
            robot9330.rightRearMotor.setPower(speed);
            robot9330.leftRearMotor.setPower(-speed);
            while(currentTimeMillis() <= targetTime){
                System.out.println   ("Driving straight: time left -->" + (targetTime - currentTimeMillis()));
            }
            robot9330.leftFrontMotor.setPower(0);
            robot9330.rightFrontMotor.setPower(0);
            robot9330.rightRearMotor.setPower(0);
            robot9330.leftRearMotor.setPower(0);
        }
    }

    public void drive() {
        if(currentTimeMillis() < targetTime) {
            moveForward(1.0f,1.0f);
        }
        else{
            moveForward(0,0);
        }
    }

    // set the drive train to a specific speed
    public void drive(float speed)
    {
        moveForward(speed, speed);
    }

    public void drive(int time)
    {
        driveTime(time, 1);

    }

    public void driveDistance(int distance, float newSpeed) {
        int newLeftTarget;

        int zAccumulated;  //Total rotation left/right
        int target = 0;  //Desired angle to turn to
        float leftSpeed = newSpeed;
        float rightSpeed = newSpeed;

        if(distance > 0){

            // Determine new target position, and pass to motor controller
            newLeftTarget = encoderMotor.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);

            if (gyroInitialized) {
                target = gyro.getIntegratedZValue();  //Starting direction
            }

            moveForward(leftSpeed, rightSpeed);
            while(encoderMotor.getTargetPosition() < newLeftTarget){
                if (gyroInitialized) {
                    zAccumulated = gyro.getIntegratedZValue();
                    leftSpeed = newSpeed + ((zAccumulated - target) / 100);
                    rightSpeed = newSpeed - ((zAccumulated - target) / 100);

                    leftSpeed = Range.clip(leftSpeed, -1, 1);
                    rightSpeed = Range.clip(rightSpeed, -1, 1);
                    moveForward(leftSpeed, rightSpeed);
                }
            }
            moveForward(0,0);
        }

    }

    // now we could have a different method that species the time and speed. 
    // (this is called method overloading) 
    public void driveTime(int time, float newSpeed) {
        int zAccumulated;  //Total rotation left/right
        int target = 0;  //Desired angle to turn to
        float leftSpeed = newSpeed;
        float rightSpeed = -newSpeed;

        if(time > 0){
            if (gyroInitialized) {
                target = gyro.getIntegratedZValue();  //Starting direction
            }

            motorSpeed = newSpeed;
            targetTime = currentTimeMillis() + time;
            while(currentTimeMillis() <= targetTime){
                if (gyroInitialized) {
                    zAccumulated = gyro.getIntegratedZValue();
                    leftSpeed = newSpeed + ((zAccumulated - target) / 100);
                    rightSpeed = newSpeed - ((zAccumulated - target) / 100);

                    leftSpeed = Range.clip(leftSpeed, -1, 1);
                    rightSpeed = Range.clip(rightSpeed, -1, 1);
                }
                moveForward(leftSpeed, rightSpeed);
                System.out.println   ("autodrive %d" + (targetTime - currentTimeMillis()));
            }
            moveForward(0,0);
        }
    }

    // set the drive train to a specific speed
    public void driveDiagonalLeft(float speed)
    {
        moveDiagonalLeft(speed, speed);
    }

    // set the drive train to a specific speed
    public void driveDiagonalRight(float speed)
    {
        moveDiagonalRight(speed, speed);
    }


    public void driveDiagonalLeft(int time, float newSpeed) {

        if(time > 0){
            motorSpeed = newSpeed;
            targetTime = currentTimeMillis() + time;
            moveDiagonalLeft(motorSpeed, motorSpeed);
            while(currentTimeMillis() <= targetTime){
                System.out.println   ("autodrive %d" + (targetTime - currentTimeMillis()));
            }
            moveDiagonalLeft(0, 0);
        }
    }

    public void driveDiagonalRight(int time, float newSpeed) {

        if(time > 0){
            motorSpeed = newSpeed;
            targetTime = currentTimeMillis() + time;
            moveDiagonalRight(motorSpeed, motorSpeed);
            while(currentTimeMillis() <= targetTime){
                System.out.println   ("autodrive %d" + (targetTime - currentTimeMillis()));
            }
            moveDiagonalRight(0, 0);
        }
    }

    public void driveDiagonalLeftDistance(int distanceInches, float newSpeed) {

        int newLeftTarget;

        int zAccumulated;  //Total rotation left/right
        int target = 0;  //Desired angle to turn to
        float leftSpeed = newSpeed;
        float rightSpeed = -newSpeed;

        if (distanceInches > 0) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = encoderMotor.getCurrentPosition() + (int) (distanceInches * COUNTS_PER_INCH);

            if (gyroInitialized) {
                target = gyro.getIntegratedZValue();  //Starting direction
            }

            motorSpeed = newSpeed;
            while (encoderMotor.getTargetPosition() < newLeftTarget) {
                if (gyroInitialized) {
                    zAccumulated = gyro.getIntegratedZValue();
                    leftSpeed = newSpeed + ((zAccumulated - target) / 100);
                    rightSpeed = newSpeed - ((zAccumulated - target) / 100);

                    leftSpeed = Range.clip(leftSpeed, -1, 1);
                    rightSpeed = Range.clip(rightSpeed, -1, 1);
                }
                moveDiagonalLeft(leftSpeed, rightSpeed);
            }
            moveDiagonalLeft(0, 0);
        }

    }

    public void driveDiagonalRightDistance(int distanceInches, float newSpeed) {

        int newLeftTarget;

        int zAccumulated;  //Total rotation left/right
        int target = 0;  //Desired angle to turn to
        float leftSpeed = newSpeed;
        float rightSpeed = -newSpeed;

        if (distanceInches > 0) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = encoderMotor.getCurrentPosition() + (int) (distanceInches * COUNTS_PER_INCH);

            if (gyroInitialized) {
                target = gyro.getIntegratedZValue();  //Starting direction
            }

            motorSpeed = newSpeed;
            while (encoderMotor.getTargetPosition() < newLeftTarget) {
                if (gyroInitialized) {
                    zAccumulated = gyro.getIntegratedZValue();
                    leftSpeed = newSpeed + ((zAccumulated - target) / 100);
                    rightSpeed = newSpeed - ((zAccumulated - target) / 100);

                    leftSpeed = Range.clip(leftSpeed, -1, 1);
                    rightSpeed = Range.clip(rightSpeed, -1, 1);
                }
                moveDiagonalRight(leftSpeed, rightSpeed);
            }
            moveDiagonalRight(0, 0);
        }

    }

    public void driveLeft(int time, double newSpeed) {

        if(time > 0){
            targetTime = currentTimeMillis() + time;
            moveLeft(newSpeed, newSpeed);
            while(currentTimeMillis() <= targetTime){
                System.out.println   ("autodrive %d" + (targetTime - currentTimeMillis()));
            }
            moveLeft(0, 0);
        }
    }
    
    public void driveRight(int time, double newSpeed) {

        if(time > 0){
            targetTime = currentTimeMillis() + time;
            moveRight(newSpeed, newSpeed);
            while(currentTimeMillis() <= targetTime){
                System.out.println   ("autodrive %d" + (targetTime - currentTimeMillis()));
            }
            moveRight(0, 0);
        }
    }

    public void moveForward(float leftSpeed, float rightSpeed){

        robot9330.leftFrontMotor.setPower(leftSpeed);
        robot9330.rightFrontMotor.setPower(-rightSpeed);
        robot9330.rightRearMotor.setPower(-rightSpeed);
        robot9330.leftRearMotor.setPower(leftSpeed);
    }

    public void moveLeft(double leftSpeed, double rightSpeed){

        robot9330.leftFrontMotor.setPower(-leftSpeed);
        robot9330.rightFrontMotor.setPower(-rightSpeed);
        robot9330.rightRearMotor.setPower(rightSpeed);
        robot9330.leftRearMotor.setPower(leftSpeed);
    }

    public void moveRight(double leftSpeed, double rightSpeed){

        robot9330.leftFrontMotor.setPower(leftSpeed);
        robot9330.rightFrontMotor.setPower(rightSpeed);
        robot9330.rightRearMotor.setPower(-rightSpeed);
        robot9330.leftRearMotor.setPower(-leftSpeed);
    }


    public void moveDiagonalRight(float leftSpeed, float rightSpeed) {

        robot9330.leftFrontMotor.setPower(leftSpeed);
        robot9330.rightRearMotor.setPower(-rightSpeed);

    }

    public void moveDiagonalLeft(float leftSpeed, float rightSpeed) {

        robot9330.rightFrontMotor.setPower(-rightSpeed);
        robot9330.leftRearMotor.setPower(leftSpeed);

    }

    public void turnWithoutGyro(int time, double newSpeed, boolean left) {
        if(time > 0){
            targetTime = currentTimeMillis() + time;
            if (left){
                turnLeft(newSpeed, newSpeed);
            } else {
                turnRight(newSpeed, newSpeed);
            }
            while(currentTimeMillis() <= targetTime){
                System.out.println   ("autodrive %d" + (targetTime - currentTimeMillis()));
            }
            moveDiagonalLeft(0, 0);
        }
    }

    public void turnLeft(double leftSpeed, double rightSpeed){

        robot9330.leftFrontMotor.setPower(leftSpeed);
        robot9330.rightFrontMotor.setPower(rightSpeed);
        robot9330.rightRearMotor.setPower(rightSpeed);
        robot9330.leftRearMotor.setPower(leftSpeed);
    }

    public void turnRight(double leftSpeed, double rightSpeed){

        robot9330.leftFrontMotor.setPower(-leftSpeed);
        robot9330.rightFrontMotor.setPower(-rightSpeed);
        robot9330.rightRearMotor.setPower(-rightSpeed);
        robot9330.leftRearMotor.setPower(-leftSpeed);
    }
    // keep speed low for more accurate angles.
    // for turning left, make targetAngle negative and for right, make it positive
    public void turn(int targetAngle, double speed, int error, int angleZ) {

        // if (gyroInitialized) {
        //     zAccumulated = gyro.getIntegratedZValue();  //Set variables to gyro readings
        // }
        //Continue while the robot direction is further than three degrees from the target
        if ((Math.abs(angleZ - targetAngle) > error)) {
            //if gyro is positive, we will turn right
            if (angleZ > targetAngle) {
                turnTable(speed);
            }
            //if gyro is positive, we will turn left
            else if (angleZ < targetAngle) {
                turnTable(-speed);

                //Set variables to gyro readings
                //zAccumulated = gyro.getIntegratedZValue();
            } else {
                turnTable(0);
            }
        } else {
            turnTable(0);
        }
    }

    public void turnTable (double speed){

        robot9330.leftFrontMotor.setPower(speed);
        robot9330.rightFrontMotor.setPower(speed);
        robot9330.rightRearMotor.setPower(speed);
        robot9330.leftRearMotor.setPower(speed);
    }

    public void setRightSpeed(float speed) {
        robot9330.rightFrontMotor.setPower(speed);
        robot9330.rightRearMotor.setPower(speed);
    }

    public void setLeftSpeed(float speed) {
        robot9330.leftFrontMotor.setPower(speed);
        robot9330.leftRearMotor.setPower(speed);
    }


    public void setTime(long driveTime) {
        targetTime = driveTime + currentTimeMillis();
    }

    public void reset() {
        long targetTime = 0;
    }
 }
