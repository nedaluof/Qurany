package com.nedaluof.qurany.ui.reciter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.animation.ScaleInAnimation;
import com.nedaluof.qurany.QuranyApplication;
import com.nedaluof.qurany.R;
import com.nedaluof.qurany.data.model.Reciters;
import com.nedaluof.qurany.databinding.ActivityReciterBinding;
import com.nedaluof.qurany.di.components.AppComponent;
import com.nedaluof.qurany.ui.sura.ReciterSurasActivity;
import com.nedaluof.qurany.util.SurasUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ReciterActivity extends AppCompatActivity implements ReciterView {
    private static final String TAG = "ReciterActivity";
    private ActivityReciterBinding binding;
    RecitersAdapter adapter;
    @Inject
    ReciterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReciterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppComponent appComponent = ((QuranyApplication) getApplication()).getComponent();
        appComponent.inject(this);


        //presenter = new ReciterPresenter();
        presenter.attachView(this);

        binding.recitersRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.recitersRecyclerView.setHasFixedSize(true);
        binding.recitersRecyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new RecitersAdapter(R.layout.item_reciter, new ArrayList<>(), this);
        adapter.setAdapterAnimation(new ScaleInAnimation());
        binding.recitersRecyclerView.setAdapter(adapter);
        presenter.loadReciters(SurasUtil.getLanguage());

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
    public void showReciters(List<Reciters.Reciter> list) {
        adapter.addData(list);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, "Error: " + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClickGetReciterData(Reciters.Reciter reciterData) {
        startActivity(
                new Intent(this, ReciterSurasActivity.class)
                        .putExtra("reciterData", reciterData)
        );
    }
}