package com.nedaluof.qurany.ui.main;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.nedaluof.qurany.R;
import com.nedaluof.qurany.databinding.ActivityMainBinding;
import com.nedaluof.qurany.ui.myreciters.MyRecitersFragment;
import com.nedaluof.qurany.ui.reciter.RecitersFragment;

import org.jetbrains.annotations.NotNull;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String FRAGMENT_KEY_ID = "FRAGMENT_KEY_ID";
    private int fragmentId;
    private ActivityMainBinding binding;
    private boolean doubleBackToExitPressedOnce = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        handleState(savedInstanceState);

    }

    private void handleState(Bundle savedInstanceState) {
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
            binding.navigation.setItemSelected(R.id.nav_home, true);
            loadFragment(new RecitersFragment(), 1);
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

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(FRAGMENT_KEY_ID, fragmentId);
    }


    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(binding.container.getId());
        if (fragment instanceof RecitersFragment) {
            if (doubleBackToExitPressedOnce) {
                this.doubleBackToExitPressedOnce = false;
                Toast.makeText(this, "Please press back again to exit.", Toast.LENGTH_LONG).show();
                new Handler().postDelayed(() -> this.doubleBackToExitPressedOnce = true, 2000);
            } else {
                finish();
            }
        } else if (fragment instanceof MyRecitersFragment) {
            loadFragment(new RecitersFragment(), 1);
            binding.navigation.setItemSelected(R.id.nav_home, true);
        } else {
            Toast.makeText(this, "i don't know", Toast.LENGTH_SHORT).show();
        }

    }
}