package com.nedaluof.qurany.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nedaluof.qurany.databinding.NoInternetFragmentBinding;

/**
 * Created by nedaluof on 7/5/2020.
 */
public class NoInternetFragment extends Fragment {
    public NoInternetFragment() {/**/}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return NoInternetFragmentBinding.inflate(inflater, container, false).getRoot();
    }
}
