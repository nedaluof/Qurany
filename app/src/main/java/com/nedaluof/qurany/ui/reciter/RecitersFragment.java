package com.nedaluof.qurany.ui.reciter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.animation.ScaleInAnimation;
import com.nedaluof.qurany.QuranyApplication;
import com.nedaluof.qurany.R;
import com.nedaluof.qurany.data.model.Reciter;
import com.nedaluof.qurany.databinding.ItemReciterBinding;
import com.nedaluof.qurany.databinding.RecitersFragmentBinding;
import com.nedaluof.qurany.ui.sura.ReciterSurasActivity;
import com.nedaluof.qurany.util.Utility;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nedaluof on 7/5/2020.
 */
public class RecitersFragment extends Fragment implements ReciterView {
    public RecitersFragment() {/**/}

    private static final String TAG = "RecitersFragment";
    private RecitersFragmentBinding binding;
    private RecitersAdapter adapter;
    @Inject
    ReciterPresenter presenter;
    @Inject
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = RecitersFragmentBinding.inflate(inflater, container, false);
        ((QuranyApplication) getActivity().getApplication()).getComponent().inject(this);
        initComponents();
        return binding.getRoot();
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            binding.proReciters.setVisibility(View.VISIBLE);
        } else {
            binding.proReciters.setVisibility(View.GONE);
        }
    }

    @Override
    public void showReciters(List<Reciter> list) {
        adapter.addData(list);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClickGetReciterData(Reciter reciterData) {
        startActivity(
                new Intent(context, ReciterSurasActivity.class)
                        .putExtra("reciterData", reciterData)
        );
    }

    @Override
    public void onClickAddToMyReciters(Reciter reciterData) {
        Toast.makeText(context, "reciter name:" + reciterData.getName(), Toast.LENGTH_SHORT).show();
        presenter.addReciterToMyReciters(reciterData);
    }

    @Override
    public void onReciterAddedToMyRecitersSuccess() {
        Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReciterAlreadyAddedToMyReciters() {
        Toast.makeText(context, "Fail To Add Reciter", Toast.LENGTH_SHORT).show();
    }

    private void initComponents() {
        presenter.attachView(this);
        binding.recitersRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        binding.recitersRecyclerView.setHasFixedSize(true);
        binding.recitersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new RecitersAdapter(R.layout.item_reciter, new ArrayList<>(), this);
        adapter.setAdapterAnimation(new ScaleInAnimation());
        binding.recitersRecyclerView.setAdapter(adapter);
        presenter.loadReciters();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
