//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.hardware.LightSensor;
//
//import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;
//
///**
// * Created by NeoBOTS on 10/29/2016.
// */
//@Autonomous
//
//public class Auto9330 extends OpMode
//{
//    /* Declare OpMode members. */
//    HardwarePushbot robot   = new HardwarePushbot();   // Use a Pushbot's hardware
//    // could also use HardwarePushbotMatrix class.
//    LightSensor lightSensor;      // Primary LEGO Light sensor,
//    // OpticalDistanceSensor   lightSensor;   // Alternative MR ODS sensor
//
//    static final double     WHITE_THRESHOLD = 0.2;  // spans between 0.1 - 0.5 from dark to light
//    static final double     APPROACH_SPEED  = 0.5;
//
//    @Override
//    public void runOpMode() {
//
//        /* Initialize the drive system variables.
//         * The init() method of the hardware class does all the work here
//         */
//        robot.init(hardwareMap);
//
//        // If there are encoders connected, switch to RUN_USING_ENCODER mode for greater accuracy
//        // robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        // robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        // get a reference to our Light Sensor object.
//        lightSensor = hardwareMap.lightSensor.get("sensor_light");                // Primary LEGO Light Sensor
//        //  lightSensor = hardwareMap.opticalDistanceSensor.get("sensor_ods");  // Alternative MR ODS sensor.
//
//        // turn on LED of light sensor.
//        lightSensor.enableLed(true);
//
//        // Send telemetry message to signify robot waiting;
//        telemetry.addData("Status", "Ready to run");    //
//        telemetry.update();
//
//        // Wait for the game to start (driver presses PLAY)
//        // Abort this loop is started or stopped.
//        while (!(isStarted() || isStopRequested())) {
//
//            // Display the light level while we are waiting to start
//            telemetry.addData("Light Level", lightSensor.getLightDetected());
//            telemetry.update();
//            idle();
//        }
//
//        // Start the robot moving forward, and then begin looking for a white line.
//        robot.leftMotor.setPower(APPROACH_SPEED);
//        robot.rightMotor.setPower(APPROACH_SPEED);
//
//        // run until the white line is seen OR the driver presses STOP;
//        while (opModeIsActive() && (lightSensor.getLightDetected() < WHITE_THRESHOLD)) {
//
//            // Display the light level while we are looking for the line
//            telemetry.addData("Light Level",  lightSensor.getLightDetected());
//            telemetry.update();
//        }
//
//        // Stop all motors
//        robot.leftMotor.setPower(0);
//        robot.rightMotor.setPower(0);
//    }
//}
//}
