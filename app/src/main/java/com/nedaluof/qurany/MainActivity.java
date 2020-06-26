package com.nedaluof.qurany;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.nedaluof.qurany.data.room.ReciterSuraEntity;
import com.nedaluof.qurany.data.room.ReciterSuraRepository;
import com.nedaluof.qurany.databinding.ActivityMainBinding;
import com.nedaluof.qurany.ui.CustomDownload;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Inject
    ReciterSuraRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ((QuranyApplication) getApplication())
                .getComponent()
                .inject(MainActivity.this);

        List<ReciterSuraEntity> entities = new ArrayList<>();
        entities.add(new ReciterSuraEntity(
                50,
                "nidal",
                "nidal",
                "nidal",
                "nidal"
        ));

        entities.add(new ReciterSuraEntity(
                100,
                "nidal",
                "nidal",
                "nidal",
                "read"
        ));

        entities.add(new ReciterSuraEntity(
                90,
                "nidal",
                "nidal",
                "nidal",
                "max"
        ));


        binding.testBtnInsert.setOnClickListener(v -> {
            /*repository.insertToDb(new ReciterSuraEntity(
                    50,
                    "nidal",
                    "nidal",
                    "nidal",
                    "nidal"
            ));

            Log.d(TAG, "onCreate: insert sigle obj");*/
            repository.insertReciterSuras(entities);

        });

        binding.testBtnGet.setOnClickListener(v -> {
            for (ReciterSuraEntity entity : repository.getReciterSuras()){
                binding.tvShowData.append(
                        entity.getReciterServer()
                                + " HI :" +
                                entity.getReciterServer()
                );
            }
        });


    }

}