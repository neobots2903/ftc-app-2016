package org.firstinspires.ftc.teamcode;

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

    Hardware9330 robot9330 = new Hardware9330();
    long targetTime = 0;

    public void init(Hardware9330 robotmap) {

        robot9330 = robotmap;

    }

    public void drive() {
        if(System.currentTimeMillis() < targetTime) {
            moveForward(setTime());
        }
        else{
            moveForward(0);
        }
    }

    public void moveForward(float speed){

        robot9330.leftFrontMotor.setPower(-speed);
        robot9330.rightFrontMotor.setPower(speed);
        robot9330.rightRearMotor.setPower(speed);
        robot9330.leftRearMotor.setPower(-speed);
    }

    public void turnTable (float speed){

        robot9330.leftFrontMotor.setPower(speed);
        robot9330.rightFrontMotor.setPower(speed);
        robot9330.rightRearMotor.setPower(speed);
        robot9330.leftRearMotor.setPower(speed);
    }

    public void setTime(long driveTime) {
        targetTime = driveTime + System.currentTimeMillis();
    }

    public void reset() {
        long targetTime = 0;
    }
 }
