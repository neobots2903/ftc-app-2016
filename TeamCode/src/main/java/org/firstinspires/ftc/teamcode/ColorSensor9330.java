package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by Robot on 10/31/2016.
 */

public class ColorSensor9330 {

    ColorSensor csensor;
    private Hardware9330 hwMap = null;
    boolean bLedOn = true;

    public ColorSensor9330(Hardware9330 robotMap) {
        hwMap = robotMap;
        csensor = hwMap.lineCSensor;
        csensor.enableLed(bLedOn);
    }

    public float r() {
        return csensor.red();
    }

    public float g() {
        return csensor.green();
    }

    public float b() {
        return csensor.blue();
    }

}
