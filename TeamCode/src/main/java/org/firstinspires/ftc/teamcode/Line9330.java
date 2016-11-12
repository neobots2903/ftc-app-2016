package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.ColorSensor;


/**
 * Created by robot on 11/4/2016.
 */

public class Line9330 {
    public Hardware9330 hwMap = null;
    ColorSensor csensor;

    public Line9330(Hardware9330 robotMap) {
        hwMap = robotMap;
        csensor = hwMap.lineCSensor;
        csensor.enableLed(true);
    }

}
