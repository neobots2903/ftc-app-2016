package org.firstinspires.ftc.teamcode;

/**
 * Created by Robot on 10/24/2016.
 */

public class Brake9330  {

    /* The brake function will be accessed by both the operator and the robot.
       The brake function will be achieved by extension from the robot powered by a motor.
       It will be rotated into a position similar to a kick stand to keep in place.
    */
private boolean brakeEngaged;

//   Declaring Set positions for servo
    static final Double ENGAGED_POS = 0.8;
    static final Double RELEASE_POS = 0.25;
//   accessing Hardware9330
    private Hardware9330 hwMap = null;
//Constructor for brake class
    public Brake9330 (Hardware9330 robotMap){

        hwMap = robotMap;
        hwMap.brake.setPosition(RELEASE_POS);
        brakeEngaged = false;
    }
 //  Engaging the brake
    public void engageBrake(){
        if(!brakeEngaged){
            brakeEngaged = true;
            hwMap.brake.setPosition(ENGAGED_POS);
        }
    }
// Releasing the brake
    public void releaseBrake(){
        if(brakeEngaged){
            brakeEngaged = false;
            hwMap.brake.setPosition(RELEASE_POS);
        }
    }

    // query brake state
    public boolean isBrakeEngaged() {
        return brakeEngaged;
    }

}


