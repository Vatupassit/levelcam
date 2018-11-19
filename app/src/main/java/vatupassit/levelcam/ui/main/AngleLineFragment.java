package vatupassit.levelcam.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vatupassit.levelcam.R;

public class AngleLineFragment extends Fragment implements SensorEventListener {

    private AngleLineViewModel mViewModel;

    private SensorManager sensorManager;
    private Sensor sensor;

    private float[] rotationMatrix;
    private float[] orientationValues;

    private TextView angleX;
    private TextView angleY;
    private TextView angleZ;

    public static AngleLineFragment newInstance() {
        return new AngleLineFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.angle_line_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AngleLineViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
        SensorManager.remapCoordinateSystem(rotationMatrix, SensorManager.AXIS_X, SensorManager.AXIS_Z, rotationMatrix);
        SensorManager.getOrientation(rotationMatrix, orientationValues);

        float x = (float) Math.toDegrees(orientationValues[0]);
        float y = (float) Math.toDegrees(orientationValues[1]);
        float z = (float) Math.toDegrees(orientationValues[2]);

        angleX.setText("X: " + String.valueOf(Math.floor(Math.abs(x))) + "°");
        angleY.setText("Y: " + String.valueOf(Math.floor(Math.abs(y))) + "°");
        angleZ.setText("Z: " + String.valueOf(Math.floor(Math.abs(z))) + "°");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onStart() {

        super.onStart();

        sensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        angleX = (TextView)getView().findViewById(R.id.angleX);
        angleY = (TextView)getView().findViewById(R.id.angleY);
        angleZ = (TextView)getView().findViewById(R.id.angleZ);


        if (sensor == null) {
            angleX.setText("sensor not found");
        }

        rotationMatrix = new float[16];
        orientationValues = new float[3];

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);

    }

    public void OnPause() {

        sensorManager.unregisterListener(this);
    }
}
