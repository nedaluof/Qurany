package com.nedaluof.qurany.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by nedaluof on 6/13/2020.
 */
public abstract class BaseFragment extends Fragment {
    //private Unbinder unbinder;

    /* if you need to handle more fragment like Pager or bottomNavigationView
    private int pageNumber =1;
    private boolean hasMore = false;*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    public abstract @LayoutRes
    int getLayoutId();

   /* @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }*/
}
