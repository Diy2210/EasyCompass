package com.example.diy2210.easycompass;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private RotateAnimation rotateAnimation;
    private float currentDegree = 0f;
    private float degree;
    private TextView headingTV;
    private ImageView compassIV;
    private ToggleButton toggleButton;
    private ConstraintLayout mainCL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        headingTV = findViewById(R.id.headingTV);
        compassIV = findViewById(R.id.compassIV);
        compassIV.setScaleType(ImageView.ScaleType.FIT_CENTER);
        compassIV.setImageResource(R.drawable.ic_compass_black);
        mainCL = findViewById(R.id.mainCL);
        mainCL.setBackgroundColor(getResources().getColor(R.color.white));
        toggleButton = findViewById(R.id.toggleButton);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mainCL.setBackgroundColor(getResources().getColor(R.color.black));
                    headingTV.setTextColor(Color.WHITE);
                    compassIV.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    compassIV.setImageResource(R.drawable.ic_compass_white);
                } else {
                    mainCL.setBackgroundColor(getResources().getColor(R.color.white));
                    headingTV.setTextColor(Color.BLACK);
                    compassIV.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    compassIV.setImageResource(R.drawable.ic_compass_black);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        degree = Math.round(event.values[0]);
        headingTV.setText(Float.toString(degree) + "°");
        rotateAnimation = new RotateAnimation(currentDegree, -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setDuration(210);
        rotateAnimation.setFillAfter(true);
        compassIV.startAnimation(rotateAnimation);
        currentDegree = -degree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO
    }
}

