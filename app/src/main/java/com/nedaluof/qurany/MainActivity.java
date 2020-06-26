package com.nedaluof.qurany;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.nedaluof.qurany.ui.CustomDownload;

public class MainActivity extends AppCompatActivity {

    private CustomDownload customDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customDownload = findViewById(R.id.view_download);
        customDownload.setDownloadConfig(2000, 20 , CustomDownload.DownloadUnit.MB);
        customDownload.start();
        new Handler().postDelayed(() -> customDownload.reset(), 10000);
        new Handler().postDelayed(() -> customDownload.reset(), 100);
    }

    public void dddd(View view) {
        customDownload.start();
    }
}