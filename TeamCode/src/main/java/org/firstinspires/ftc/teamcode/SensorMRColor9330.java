/* Copyright (c) 2015 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

/*
 *
 * This is an example LinearOpMode that shows how to use
 * a Modern Robotics Color Sensor.
 *
 * The op mode assumes that the color sensor
 * is configured with a name of "sensor_color".
 *
 * You can use the X button on gamepad1 to toggle the LED on and off.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */
@TeleOp(name = "Sensor: MR Color", group = "Sensor")
//@Disabled
public class SensorMRColor9330 extends LinearOpMode {

    Hardware9330 hwMap = new Hardware9330();
    ColorSensor bbcSensor;
    ColorSensor linecSensor;


    @Override
    public void runOpMode() {

        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F, 0F, 0F};
        float hsvValues2[] = {0F, 0F, 0F};

        // values is a reference to the hsvValues array.
        final float values[] = hsvValues;

        hwMap.init(hardwareMap);


        // get a reference to the RelativeLayout so we can change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(R.id.RelativeLayout);

        // bLedOn represents the state of the LED.
        boolean bLedOn = true;

        bbcSensor = hwMap.BBSensor;
        linecSensor = hwMap.lineCSensor;

        // Set the LED in the beginning
        bbcSensor.enableLed(bLedOn);
        linecSensor.enableLed(bLedOn);

        // wait for the start button to be pressed.
        waitForStart();

        // while the op mode is active, loop and read the RGB data.
        // Note we use opModeIsActive() as our loop condition because it is an interruptible method.
        while (opModeIsActive()) {

            // check for button state transitions.
            if (gamepad1.x) {
                // button is transitioning to a pressed state. So Toggle LED
                bLedOn = !bLedOn;
                bbcSensor.enableLed(bLedOn);
                linecSensor.enableLed(bLedOn);
            }

            // convert the RGB values to HSV values.
            Color.RGBToHSV(hwMap.BBSensor.red() * 8, hwMap.BBSensor.green() * 8, hwMap.BBSensor.blue() * 8, hsvValues);
            Color.RGBToHSV(hwMap.lineCSensor.red() * 8, hwMap.lineCSensor.green() * 8, hwMap.lineCSensor.blue() * 8, hsvValues2);

            // send the info back to driver station using telemetry function.
            telemetry.addData("LED", bLedOn ? "On" : "Off");
            telemetry.addLine("----- beBool Color Sensor ------");
            telemetry.addData("BB Address :", bbcSensor.getI2cAddress().get7Bit());
            telemetry.addData("Alpha", bbcSensor.alpha());
            telemetry.addData("Red  ", bbcSensor.red());
            telemetry.addData("Green", bbcSensor.green());
            telemetry.addData("Blue ", bbcSensor.blue());
            telemetry.addData("Hue", hsvValues[0]);

            telemetry.addLine("----- Other Color Sensor ------");
            telemetry.addData("C Address :", linecSensor.getI2cAddress().get7Bit());
            telemetry.addData("Clear", linecSensor.alpha());
            telemetry.addData("Red  ", linecSensor.red());
            telemetry.addData("Green", linecSensor.green());
            telemetry.addData("Blue ", linecSensor.blue());
            telemetry.addData("Hue", hsvValues2[0]);


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
}
