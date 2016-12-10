package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;

/**
 * Created by Robot on 11/28/2016.
 */
@TeleOp(name="BeaconCS", group="OpMode``")
public class BeaconSensor9330 extends LinearOpMode {

    ColorSensor bbcSensor;
    boolean isRed;

    @Override
    public void runOpMode() {

        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F, 0F, 0F};
        float hsvValues2[] = {0F, 0F, 0F};

        // values is a reference to the hsvValues array.
        final float values[] = hsvValues;




        // get a reference to the RelativeLayout so we can change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(R.id.RelativeLayout);

        // bPrevState and bCurrState represent the previous and current state of the button.
        boolean bPrevState = false;
        boolean bCurrState = false;

        // bLedOn represents the state of the LED.
        boolean bLedOn = true;

        bbcSensor = hardwareMap.colorSensor.get("BBSensor");
        bbcSensor.setI2cAddress(I2cAddr.create7bit(0x1E));
        bbcSensor.enableLed(true);

        // Set the LED in the beginning
        bbcSensor.enableLed(bLedOn);

        // wait for the start button to be pressed.
        waitForStart();

        // while the op mode is active, loop and read the RGB data.
        // Note we use opModeIsActive() as our loop condition because it is an interruptible method.
        while (opModeIsActive()) {

            // check the status of the x button on either gamepad.
            bCurrState = gamepad1.x;

            // check for button state transitions.
            if ((bCurrState == true) && (bCurrState != bPrevState)) {

                // button is transitioning to a pressed state. So Toggle LED
                bLedOn = !bLedOn;
                bbcSensor.enableLed(bLedOn);
            }

            // update previous state variable.
            bPrevState = bCurrState;

            // convert the RGB values to HSV values.
            Color.RGBToHSV(bbcSensor.red() * 8, bbcSensor.green() * 8, bbcSensor.blue() * 8, hsvValues);

            // send the info back to driver station using telemetry function.
            telemetry.addData("LED", bLedOn ? "On" : "Off");
            telemetry.addLine("----- beBool Color Sensor ------");
            telemetry.addData("BB Address :", bbcSensor.getI2cAddress().get7Bit());
            telemetry.addData("Alpha", bbcSensor.alpha());
            telemetry.addData("Red  ", bbcSensor.red());
            telemetry.addData("Green", bbcSensor.green());
            telemetry.addData("Blue ", bbcSensor.blue());
            telemetry.addData("Hue", hsvValues[0]);


            // change the background color to match the color detected by the RGB sensor.
            // pass a reference to the hue, saturation, and value array as an argument
            // to the HSVToColor method.
            relativeLayout.post(new Runnable() {
                public void run() {
                    relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
                }
            });

            telemetry.update();
        }
    }

    public boolean DetectColor() {

        if (bbcSensor.red() > 1) {
            isRed = true;
        } else if (bbcSensor.blue() > 1) {
            isRed = false;
        }
        return isRed;

    }

}
