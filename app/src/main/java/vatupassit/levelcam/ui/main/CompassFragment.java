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
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

import vatupassit.levelcam.R;

public class CompassFragment extends Fragment implements SensorEventListener {

    // define the display assembly compass picture
    private ImageView image;

    // record the compass picture angle turned
    private float currentDegree = 0f;
    private float[] rotationMatrix;
    private float[] orientationValues;


    private SensorManager mSensorManager;

    private Sensor sensor;
    //private Sensor sensorMag;

    private CompassViewModel mViewModel;

    TextView tvHeading;
    //TextView testAngleX;
    //TextView testAngleY;
    //TextView testAngleZ;
    TextView magX;
    TextView magY;
    TextView magZ;

    public static CompassFragment newInstance() {
        return new CompassFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.compass_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CompassViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
        SensorManager.remapCoordinateSystem(rotationMatrix, SensorManager.AXIS_X, SensorManager.AXIS_Z, rotationMatrix);
        SensorManager.getOrientation(rotationMatrix, orientationValues);

        //float y = (float) Math.toDegrees(orientationValues[0]);
        float z = (float) Math.toDegrees(orientationValues[2]);
        tvHeading.setText(new DecimalFormat("#").format((Math.abs(z))) + "°");

        image.setPivotX(image.getWidth()/2);
        image.setPivotY(image.getHeight()/2);
        image.setRotation(Math.abs(z));
    }

    public void onStart() {

        super.onStart();

        image = (ImageView) getActivity().findViewById(R.id.imageView2);
        image.setPivotX(image.getWidth()/2);
        image.setPivotY(image.getHeight()/2);
        image.setRotation(45);

        mSensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        //magY = (TextView)getView().findViewById(R.id.magY);
        //magZ = (TextView)getView().findViewById(R.id.magZ);
        tvHeading = (TextView)getView().findViewById(R.id.tvHeading);


        if(sensor == null) {
            tvHeading.setText("Sensor fail.");
        }

        rotationMatrix = new float[16];
        orientationValues = new float[3];

        mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
    }
    public void OnPause() {

        mSensorManager.unregisterListener(this);
    }
}
