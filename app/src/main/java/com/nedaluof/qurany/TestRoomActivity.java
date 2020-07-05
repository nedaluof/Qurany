package com.nedaluof.qurany;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nedaluof.qurany.data.DataManager;
import com.nedaluof.qurany.data.model.Reciter;
import com.nedaluof.qurany.databinding.ActivityTestroomBinding;
import com.nedaluof.qurany.util.RxUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TestRoomActivity extends AppCompatActivity {
    private static final String TAG = "TestRoomActivity";
    @Inject
    DataManager dataManager;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ActivityTestroomBinding binding;
    List<String> str = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestroomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ((QuranyApplication) getApplication())
                .getComponent()
                .inject(TestRoomActivity.this);

        binding.testBtnInsert.setOnClickListener(v -> {
            /*Log.d(TAG, "insert btn : enter insert state");

            Log.d(TAG, "insert btn : table is empty ");
            compositeDisposable.add(dataManager.getReciterRepository().insertReciters(getEntities())
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        Toast.makeText(getApplicationContext(), "completed", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "insert btn : onComplete: ");
                    }));*/

            binding.tvShowData.setText(String.valueOf(dataManager.getReciterRepository().recitersCountinTable()));

        });

        binding.testBtnGet.setOnClickListener(v -> compositeDisposable.add(dataManager.getReciterRepository().getReciters()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    Toast.makeText(getApplicationContext(), String.valueOf(list.size()), Toast.LENGTH_SHORT).show();

                }, throwable -> Log.d(TAG, "Error: " + throwable.getMessage()))));

        binding.testBtnDelete.setOnClickListener(v -> compositeDisposable.add(dataManager.getReciterRepository().deleteAllReciters()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    Toast.makeText(getApplicationContext(), "All are Deleted", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "delete btn : success ");
                })));


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