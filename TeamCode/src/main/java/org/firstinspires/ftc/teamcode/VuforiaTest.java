package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by John on 11/1/2016.  Thank you to team 6150!
 */

public class VuforiaTest extends LinearOpMode {

    // Variables to be used for later
    VuforiaLocalizer vuforiaLocalizer;
    VuforiaLocalizer.Parameters parameters;
    VuforiaTrackables visionTargets;
    VuforiaTrackable wheelsTarget, toolsTarget, legosTarget, gearsTarget;
    VuforiaTrackableDefaultListener wheelsListener, toolsListener, legosListener, gearsListener;
    OpenGLMatrix lastKnownLocation;
    OpenGLMatrix phoneLocation;

    public static final String VUFORIA_KEY = "AX2kqHH/////AAAAGTBSjEsQrELpg6LxE4wo/DNOjsPTB7/AFGloN/Z9A9RdVJM4UsJ4czaCR0J/UHwB24f8qcJd+hUP486zHfXkiY2qc3KvNd9DcPmEZytopYZQ0tSLQXwnJJs3ntyqDYeZBv/cmZCLE9WNqRHslYMv3LRMJt7gmq2EsIKs5B8fW5Su9+2S4M7msEDAjEQygfuagPWXRbJHD098VfQujTp0BhG9M22INbcVnlfrZzfD8+/dstf4AfaYdKokkGyIEh3Cygw3jEhZau+X1gghaRSlzE+nzyfSkR1ARbxuf6jzKRdw5hg5e5IjQ4n+MJfl7uuRvnrvvUncG56OKcUm+WuUoJuetZtR4dKrn1Nr82ccT56H"; //Key used for Vuforia.

    public void runOpMode() throws InterruptedException
    {
        setupVuforia();

        // We don't know where the robot is, so set it to the origin
        // If we don't include this, it would be null, which would cause errors later on
        lastKnownLocation = createMatrix(0, 0, 0, 0, 0, 0);

        waitForStart();

        // Start tracking the targets
        visionTargets.activate();

        while(opModeIsActive())
        {
            // Ask the listener for the latest information on where the robot is
            if (wheelsListener.isVisible()) {
                OpenGLMatrix latestLocation = wheelsListener.getUpdatedRobotLocation();

                // The listener will sometimes return null, so we check for that to prevent errors
                if (latestLocation != null)
                    lastKnownLocation = latestLocation;

                // Send information about whether the target is visible, and where the robot is
                telemetry.addData("Tracking " + wheelsTarget.getName(), wheelsListener.isVisible());
            }
            else if (toolsListener.isVisible())
            {
                OpenGLMatrix latestLocation = toolsListener.getUpdatedRobotLocation();

                // The listener will sometimes return null, so we check for that to prevent errors
                if (latestLocation != null)
                    lastKnownLocation = latestLocation;

                // Send information about whether the target is visible, and where the robot is
                telemetry.addData("Tracking " + toolsTarget.getName(), toolsListener.isVisible());
            }
            else if (legosListener.isVisible())
            {
                OpenGLMatrix latestLocation = legosListener.getUpdatedRobotLocation();

                // The listener will sometimes return null, so we check for that to prevent errors
                if (latestLocation != null)
                    lastKnownLocation = latestLocation;

                // Send information about whether the target is visible, and where the robot is
                telemetry.addData("Tracking " + legosTarget.getName(), legosListener.isVisible());
            }
            else if (gearsListener.isVisible())
            {
                OpenGLMatrix latestLocation = gearsListener.getUpdatedRobotLocation();

                // The listener will sometimes return null, so we check for that to prevent errors
                if (latestLocation != null)
                    lastKnownLocation = latestLocation;

                // Send information about whether the target is visible, and where the robot is
                telemetry.addData("Tracking " + gearsTarget.getName(), gearsListener.isVisible());
            }
            else
            {
                OpenGLMatrix latestLocation = wheelsListener.getUpdatedRobotLocation();

                // The listener will sometimes return null, so we check for that to prevent errors
                if (latestLocation != null)
                    lastKnownLocation = latestLocation;

                // Send information about whether the target is visible, and where the robot is
                telemetry.addData("no targets in sight", null);
            }
            telemetry.update();
            telemetry.addData( "X:"+ convertInToMM(getXLocation(lastKnownLocation)) +"  Y:" +  convertInToMM(getYLocation(lastKnownLocation)), 00);
            idle();
        }
    }

    public void setupVuforia()
    {
        // Setup parameters to create localizer
        parameters = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        vuforiaLocalizer = ClassFactory.createVuforiaLocalizer(parameters);

        // These are the vision targets that we want to use
        // The string needs to be the name of the appropriate .xml file in the assets folder
        visionTargets = vuforiaLocalizer.loadTrackablesFromAsset("FTC_2016-17");

        // Setup the target to be tracked
        wheelsTarget = visionTargets.get(0); // 0 corresponds to the wheels target
        wheelsTarget.setName("Wheels Target");
        wheelsTarget.setLocation(createMatrix(0, 1981, 32, 90, 0, 90));

        toolsTarget = visionTargets.get(1);
        toolsTarget.setName("Tools Target");
        toolsTarget.setLocation(createMatrix(914, 0, 32, 90, 0, 180));

        legosTarget = visionTargets.get(2);
        legosTarget.setName("Legos Target");
        legosTarget.setLocation(createMatrix(0, 914, 32, 90, 0, 90));

        gearsTarget = visionTargets.get(3);
        gearsTarget.setName("Gears Target");
        gearsTarget.setLocation(createMatrix(1981, 0, 32, 90, 0, 180));
        // Set phone location on robot
        phoneLocation = createMatrix(0, 0, 0, 90, 0, 0);

        // Setup listener and inform it of phone information
        wheelsListener = (VuforiaTrackableDefaultListener) wheelsTarget.getListener();
        wheelsListener.setPhoneInformation(phoneLocation, parameters.cameraDirection);

        toolsListener = (VuforiaTrackableDefaultListener) toolsTarget.getListener();
        toolsListener.setPhoneInformation(phoneLocation, parameters.cameraDirection);

        legosListener = (VuforiaTrackableDefaultListener) legosTarget.getListener();
        legosListener.setPhoneInformation(phoneLocation, parameters.cameraDirection);

        gearsListener = (VuforiaTrackableDefaultListener) gearsTarget.getListener();
        gearsListener.setPhoneInformation(phoneLocation, parameters.cameraDirection);
    }

    // Creates a matrix for determining the locations and orientations of objects
    // Units are millimeters for x, y, and z, and degrees for u, v, and w
    public OpenGLMatrix createMatrix(float x, float y, float z, float u, float v, float w)
    {
        return OpenGLMatrix.translation(x, y, z).
                multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, u, v, w));
    }

    // Formats a matrix into a readable string
    public String formatMatrix(OpenGLMatrix matrix)
    {
        return matrix.formatAsTransform();
    }

    public double getXLocation(OpenGLMatrix matrix) //returns x value
    {
        float[] robotLocationArray = matrix.getData();
        return robotLocationArray[12];
    }

    public double getYLocation(OpenGLMatrix matrix) //Returns y value
    {
        float[] robotLocationArray = matrix.getData();
        return robotLocationArray[13];
    }

    public double convertInToMM (double mm)
    {
        return mm * 0.0393701;
    }

}
