package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by NeoBOTS on 10/17/2016.
 */

public class BBoop9330 {
    private Hardware9330 hwMap = null;
    static final double LEFT_POS = 1.0;
    static final double RIGHT_POS = 0.0;
    ColorSensor csensor;

    public BBoop9330(Hardware9330 robotMap) {
        hwMap = robotMap;
        hwMap.beBoop.setPosition(RIGHT_POS);
        csensor = hwMap.BBSensor;
        csensor.enableLed(true);
    }

    public void swivelRedLeft() {
        if (csensor.red() > 200) {
            hwMap.beBoop.setPosition(LEFT_POS);
        }
        if (csensor.blue() > 200) {
            hwMap.beBoop.setPosition(RIGHT_POS);
        }
    }

    public void swivelRedRight() {
        if (csensor.red() > 200) {
            hwMap.beBoop.setPosition(RIGHT_POS);
        }
        if (csensor.blue() > 200) {
            hwMap.beBoop.setPosition(LEFT_POS);
        }
    }

    public void swivelToRed() {
        if (csensor.red() < csensor.blue()) {
            hwMap.beBoop.setPosition(RIGHT_POS);
        }
        else {
            hwMap.beBoop.setPosition(LEFT_POS);
        }
    }

    public void swivelToBlue() {
        if (csensor.red() > csensor.blue()) {
            hwMap.beBoop.setPosition(RIGHT_POS);
        }
        else {
            hwMap.beBoop.setPosition(LEFT_POS);
        }
    }

}
