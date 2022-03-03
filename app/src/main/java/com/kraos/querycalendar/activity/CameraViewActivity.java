package com.kraos.querycalendar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;

import com.kraos.querycalendar.R;
import com.kraos.querycalendar.view.CameraView;

public class CameraViewActivity extends BaseActivity {

    CameraView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_view);
        view = findViewById(R.id.view);

        ObjectAnimator topFlipAnimator = ObjectAnimator.ofFloat(view,"topFlip",45);
        topFlipAnimator.setDuration(1500);
        ObjectAnimator rotateFlipAnimator = ObjectAnimator.ofFloat(view,"rotate",360);
        rotateFlipAnimator.setDuration(1500);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(topFlipAnimator,rotateFlipAnimator);
        animatorSet.setStartDelay(1000);
        animatorSet.start();
    }


}