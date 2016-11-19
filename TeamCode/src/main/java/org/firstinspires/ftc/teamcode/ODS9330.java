package org.firstinspires.ftc.teamcode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
        import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by NeoBOTS on 11/18/2016.
 */
@TeleOp(name="ODS9330", group="OpMode")
public class ODS9330 extends OpMode{

    OpticalDistanceSensor opticalDistanceSensor;

    @Override
    public void init() {
        opticalDistanceSensor = hardwareMap.opticalDistanceSensor.get("ODS");
    }

    @Override
    public void loop() {
        double value = opticalDistanceSensor.getLightDetected();

        telemetry.addData("Value", value);
    }
}










