package com.nedaluof.qurany;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.nedaluof.qurany.data.DataManager;
import com.nedaluof.qurany.data.model.Reciter;
import com.nedaluof.qurany.data.model.ReciterSuraEntity;
import com.nedaluof.qurany.data.model.Reciters;
import com.nedaluof.qurany.databinding.ActivityMainBinding;
import com.nedaluof.qurany.util.RxUtil;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Inject
    DataManager dataManager;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ((QuranyApplication) getApplication())
                .getComponent()
                .inject(MainActivity.this);


        binding.testBtnInsert.setOnClickListener(v -> {
            Log.d(TAG, "insert btn : enter insert state");

            Log.d(TAG, "insert btn : table is empty ");
            compositeDisposable.add(dataManager.getReciterRepository().insertReciters(getEntities())
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        Toast.makeText(getApplicationContext(), "completed", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "insert btn : onComplete: ");
                    }));

        });

        binding.testBtnGet.setOnClickListener(v -> {

            compositeDisposable.add(dataManager.getReciterRepository().getReciters()
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(list -> {
                        Toast.makeText(getApplicationContext(), String.valueOf(list.size()), Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < list.size(); i++) {
                            binding.tvShowData.append(list.get(i).getName());
                        }
                    }, throwable -> Log.d(TAG, "Error: " + throwable.getMessage())));
        });

        binding.testBtnDelete.setOnClickListener(v -> {
            compositeDisposable.add(dataManager.getReciterRepository().deleteAllReciters()
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        Toast.makeText(getApplicationContext(), "All are Deleted", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "delete btn : success ");
                    }));
        });


    }

    List<Reciter> getEntities() {
        List<Reciter> entities = new ArrayList<>();
        entities.add(new Reciter(
                "50",
                "nidal1",
                "nidal2",
                "nidal3",
                "nidal4",
                "ddd",
                "ddd"
        ));

        entities.add(new Reciter(
                "100",
                "nidal",
                "nidal",
                "nidal",
                "read",
                "read",
                "read"
        ));

        entities.add(new Reciter(
                "90",
                "nidal",
                "nidal",
                "nidal",
                "max",
                "max",
                "max"
        ));
        return entities;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxUtil.disposeComposite(compositeDisposable);
    }
}