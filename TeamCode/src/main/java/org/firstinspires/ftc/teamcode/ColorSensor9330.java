package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by Robot on 10/31/2016.
 */

public class ColorSensor9330 {

    ColorSensor csensor;

    public ColorSensor9330() {

        csensor = hardwareMap.colorSensor.get("sensor_color");
        csensor.enableLed(bLedOn);
    }

    public float r() {
        waitForStart();
        return csensor.red();
    }

    public float g() {
        waitForStart();
        return csensor.green();
    }

    public float b() {
        waitforStart();
        return csensor.blue();
    }

}
