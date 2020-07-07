package com.nedaluof.qurany.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.nedaluof.qurany.R;
import com.nedaluof.qurany.databinding.ActivityMainBinding;
import com.nedaluof.qurany.ui.myreciters.MyRecitersFragment;
import com.nedaluof.qurany.ui.reciter.RecitersFragment;
import com.nedaluof.qurany.util.RxUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String FRAGMENT_KEY_ID = "FRAGMENT_KEY_ID";
    private int fragmentId;
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
                        available -> {
                            setContentView(binding.getRoot());
                            handleState(available.available(), savedInstanceState);
                            binding.navigation.setOnItemSelectedListener(i -> {
                                switch (i) {
                                    case R.id.nav_home:
                                        binding.navigation.setItemSelected(R.id.nav_home, true);
                                        loadFragment(new RecitersFragment(), 1);
                                        break;
                                    case R.id.nav_favorite:
                                        binding.navigation.setItemSelected(R.id.nav_favorite, true);
                                        loadFragment(new MyRecitersFragment(), 2);
                                        break;
                                }
                            });
                        },
                        throwable -> Log.d(TAG, "Error : " + throwable.getMessage())
                );
    }

    private void handleState(boolean available, Bundle savedInstanceState) {
        if (available) {
            if (savedInstanceState != null) {
                int id = savedInstanceState.getInt(FRAGMENT_KEY_ID);
                switch (id) {
                    case 1:
                        binding.navigation.setItemEnabled(R.id.nav_home, true);
                        binding.navigation.setItemSelected(R.id.nav_home, true);
                        loadFragment(new RecitersFragment(), 1);
                        break;
                    case 2:
                        binding.navigation.setItemEnabled(R.id.nav_favorite, true);
                        binding.navigation.setItemSelected(R.id.nav_favorite, true);
                        loadFragment(new MyRecitersFragment(), 2);
                        break;
                }
            } else {
               handleItemEnabled(true);
                binding.navigation.setItemSelected(R.id.nav_home, true);
                loadFragment(new RecitersFragment(), 1);
            }
        } else {
            handleItemEnabled(false);
            loadFragment(new NoInternetFragment(), 0);
        }
    }

    private void loadFragment(Fragment fragment, int fragmentId) {
        if (fragment != null) {
            if (fragmentId != 0) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(binding.container.getId(), fragment)
                        .commit();
                this.fragmentId = fragmentId;
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(binding.container.getId(), fragment)
                        .commit();
            }
        }
    }

    private void handleItemEnabled(boolean enabled){
        if (enabled){
            binding.navigation.setItemEnabled(R.id.nav_home, true);
            binding.navigation.setItemEnabled(R.id.nav_favorite, true);
        }else {
            binding.navigation.setItemEnabled(R.id.nav_home, false);
            binding.navigation.setItemEnabled(R.id.nav_favorite, false);
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(FRAGMENT_KEY_ID, fragmentId);
    }


    @Override
    protected void onPause() {
        super.onPause();
        RxUtil.dispose(networkDisposable);
    }
}