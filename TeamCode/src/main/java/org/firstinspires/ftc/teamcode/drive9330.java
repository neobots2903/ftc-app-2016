package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import static java.lang.System.currentTimeMillis;

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
    float motorSpeed;

    public void init(Hardware9330 robotmap) {

        robot9330 = robotmap;

    }

    public void drive() {
        if(currentTimeMillis() < targetTime) {
            moveForward(1.0f);
        }
        else{
            moveForward(0);
        }
    }
    
    // this is what I was thinking about when I wanted you to 
    // add the ability to drive for a time.
    // right now, we have a drive method that will be useful for when
    // we have other things we want to do besides drive, but we 
    // also need to be able to just drive in a straight line for a 
    // certain period of time.  If we had a different drivetrain, we 
    // could add a drive for a specified distance, but, alas, we don't.
    public void drive(int time)
    {
        // pseudocode for what I want done here:
        /*
        if time is greater than 0
            set motor speed to 1
            set target time to current system time plus time
            while current system time is less than or equal to target time do
                set the robot motors speeds to motor speed
            end while
            set motor speed to 0
        end if
        */

        drive(time, 1);
//        if (time > 0){
//            motorSpeed = 1;
//            targetTime = currentTimeMillis() + time;
//            moveForward(motorSpeed);
//            while (currentTimeMillis() <= targetTime){
//                System.out.println   ("autodrive %d" + (targetTime - currentTimeMillis()));
//            }
//            moveForward(0);
//        }
        //Typed out your pseudocode

// Loop above should work -- need to update values.
//        if (time > 0) {
//           motorSpeed = 1;
//            targetTime = System.currentTimeMillis() + time;
//            while (System.currentTimeMillis() <= targetTime) {
//                robot9330.leftFrontMotor.setPower(motorSpeed);
//                robot9330.rightFrontMotor.setPower(-motorSpeed);
//                robot9330.rightRearMotor.setPower(-motorSpeed);
//                robot9330.leftRearMotor.setPower(motorSpeed);
//            }
//            motorSpeed = 0;
//        }
    }
    
    // now we could have a different method that species the time and speed. 
    // (this is called method overloading) 
    public void drive(int time, float newSpeed)
    {
        // pseudocode for what I want done here:
        /*
        if time is greater than 0
            set motor speed to newSpeed
            set target time to current system time plus time
            while current system time is less than or equal to target time do
                set the robot motors speeds to motor speed
            end while
            set motor speed to 0
        end if
        */
        if(time > 0){
            motorSpeed = newSpeed;
            targetTime = currentTimeMillis() + time;
            moveForward(motorSpeed);
            while(currentTimeMillis() <= targetTime){
                System.out.println   ("autodrive %d" + (targetTime - currentTimeMillis()));
            }
            moveForward(0);
        }
        //typed out your pseudocode for you

// above loop should work...
//        if (time > 0) {
//            motorSpeed = newSpeed;
//            double targetTime = System.currentTimeMillis() + time;
//            while (System.currentTimeMillis() <= targetTime) {
//                robot9330.leftFrontMotor.setPower(motorSpeed);
//                robot9330.rightFrontMotor.setPower(-motorSpeed);
//                robot9330.rightRearMotor.setPower(-motorSpeed);
//                robot9330.leftRearMotor.setPower(motorSpeed);
//            }
//            motorSpeed = 0;
//        }
    }

    public void driveDiagonalLeft(int time, float newSpeed) {

        if(time > 0){
            motorSpeed = newSpeed;
            targetTime = currentTimeMillis() + time;
            moveDiagonalLeft(motorSpeed);
            while(currentTimeMillis() <= targetTime){
                System.out.println   ("autodrive %d" + (targetTime - currentTimeMillis()));
            }
            moveDiagonalLeft(0);
        }
    }

    public void driveDiagonalRight(int time, float newSpeed) {

        if(time > 0){
            motorSpeed = newSpeed;
            targetTime = currentTimeMillis() + time;
            moveDiagonalRight(motorSpeed);
            while(currentTimeMillis() <= targetTime){
                System.out.println   ("autodrive %d" + (targetTime - currentTimeMillis()));
            }
            moveDiagonalRight(0);
        }
    }


    public void moveForward(float speed){

        robot9330.leftFrontMotor.setPower(speed);
        robot9330.rightFrontMotor.setPower(-speed);
        robot9330.rightRearMotor.setPower(-speed);
        robot9330.leftRearMotor.setPower(speed);
    }

    public void moveDiagonalRight(float speed) {

        robot9330.leftFrontMotor.setPower(speed);
        robot9330.rightRearMotor.setPower(-speed);

    }

    public void moveDiagonalLeft(float speed) {

        robot9330.rightFrontMotor.setPower(-speed);
        robot9330.leftRearMotor.setPower(speed);

    }

    public void turnTable (float speed){

        robot9330.leftFrontMotor.setPower(speed);
        robot9330.rightFrontMotor.setPower(speed);
        robot9330.rightRearMotor.setPower(speed);
        robot9330.leftRearMotor.setPower(speed);
    }

    public void setTime(long driveTime) {
        targetTime = driveTime + currentTimeMillis();
    }

    public void reset() {
        long targetTime = 0;
    }
 }
