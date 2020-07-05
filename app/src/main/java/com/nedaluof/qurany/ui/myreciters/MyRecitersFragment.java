package com.nedaluof.qurany.ui.myreciters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nedaluof.qurany.databinding.MyRecitersFragmentBinding;

/**
 * Created by nedaluof on 7/5/2020.
 */
public class MyRecitersFragment extends Fragment {
    private MyRecitersFragmentBinding binding;

    /*
     * TODO 5/7/2020 : save the Favorite Reciters To Room then inflate
     * them to recycler view  , again same process with suras
     * */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MyRecitersFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
