package com.nedaluof.qurany.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.nedaluof.qurany.R;
import com.nedaluof.qurany.databinding.ActivityMainBinding;
import com.nedaluof.qurany.ui.reciter.RecitersFragment;
import com.nedaluof.qurany.util.RxUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private Disposable networkDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        RxUtil.dispose(networkDisposable);
        networkDisposable = ReactiveNetwork.observeNetworkConnectivity(MainActivity.this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        available -> handleState(available.available()),
                        throwable -> Log.d(TAG, "Error : " + throwable.getMessage())
                );
    }

    private void handleState(boolean available) {
        if (available) {
            // TODO: 7/4/2020 inflate home fragment
            setContentView(binding.getRoot());
            binding.navigation.setItemSelected(R.id.nav_home,true);
            getSupportFragmentManager().beginTransaction().replace(binding.container.getId(), new RecitersFragment()).commit();
        } else {
            // TODO: 7/4/2020 inflate no connection layout
            setContentView(R.layout.dialog_sura_ready_layout);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        RxUtil.dispose(networkDisposable);
    }
}