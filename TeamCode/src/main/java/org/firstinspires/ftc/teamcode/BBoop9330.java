package org.firstinspires.ftc.teamcode;

/**
 * Created by NeoBOTS on 10/17/2016.
 */

public class BBoop9330 {
    public Hardware9330 hwMap = null;
    static final double LEFT_POS = 1.0;
    static final double RIGHT_POS = 0.0;

    public BBoop9330(Hardware9330 robotMap) {
    hwMap = robotMap;
        hwMap.beBoop.setPosition(RIGHT_POS);
    }

}
