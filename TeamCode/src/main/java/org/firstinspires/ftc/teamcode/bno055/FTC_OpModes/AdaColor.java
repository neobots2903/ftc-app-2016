package org.firstinspires.ftc.teamcode.bno055.FTC_OpModes;

/**
 * Created by Ollie_2 on 10/5/2015.
 */

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.bno055.FTC_Library.*;
import com.qualcomm.hardware.adafruit.AdafruitI2cColorSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;

public class AdaColor extends OpMode {
    private DataLogger              dl;
    private ColorSensor             cs;

    public void init() {
        dl          = new DataLogger("Ada CS Test");
        cs          = hardwareMap.colorSensor.get("AdaColor");

        dl.addField("Alpha");
        dl.addField("Red");
        dl.addField("Green");
        dl.addField("Blue");
        dl.newLine();
    }

    public void init_loop() {

    }

    public void start() {

    }

    @Override
    public void loop() {
        int clearValue,red,green,blue;
        clearValue  = cs.alpha();
        telemetry.addData("Clear = ", clearValue);
        red         = cs.red();
        green       = cs.green();
        blue        = cs.blue();
        dl.addField(clearValue);
        dl.addField(red);
        dl.addField(green);
        dl.addField(blue);
        dl.newLine();
    }

    public void stop() {
        dl.closeDataLogger();
        cs.close();
    }
}
