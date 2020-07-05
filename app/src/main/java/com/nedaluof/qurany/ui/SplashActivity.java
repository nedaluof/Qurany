package com.nedaluof.qurany.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.nedaluof.qurany.BuildConfig;
import com.nedaluof.qurany.ui.main.MainActivity;
import com.nedaluof.qurany.databinding.ActivitySplashBinding;

import yanzhikai.textpath.painter.FireworksPainter;

/**
 * Created by nedaluof on 6/13/2020.
 */
public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashBinding binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.stpvQurany.setPathPainter(new FireworksPainter());
        binding.stpvQurany.startAnimation(0, 1);
        binding.stpvQurany.setFillColor(true);

        binding.tvVersion.setText("v ".concat(BuildConfig.VERSION_NAME));
        // TODO: 6/13/2020 check the preferences to open languageActivity
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            this.finish();
        }, 2200);
    }

    @Override
    public void onBackPressed() {

    }
}