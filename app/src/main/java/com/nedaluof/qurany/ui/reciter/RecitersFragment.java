package com.nedaluof.qurany.ui.reciter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.animation.ScaleInAnimation;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.nedaluof.qurany.R;
import com.nedaluof.qurany.data.model.Reciter;
import com.nedaluof.qurany.databinding.RecitersFragmentBinding;
import com.nedaluof.qurany.ui.component.NewAdapter;
import com.nedaluof.qurany.ui.component.RecitersAdapter;
import com.nedaluof.qurany.ui.sura.ReciterSurasActivity;
import com.nedaluof.qurany.util.RxUtil;
import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by nedaluof on 7/5/2020.
 */
@AndroidEntryPoint
public class RecitersFragment extends Fragment
        implements ReciterView {
    public RecitersFragment() {/**/}

    private static final String TAG = "RecitersFragment";
    private RecitersFragmentBinding binding;
    //private RecitersAdapter adapter;
    @Inject
    RecitersAdapter adapter;
    NewAdapter newAdapter;
    private Disposable networkDisposable;
    @Inject
    ReciterPresenter presenter;
    @ApplicationContext
    @Inject
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = RecitersFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.attachView(this);
        initComponents();
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
        /*binding.recitersRecyclerView.scheduleLayoutAnimation();
        if (adapter.getItemCount() > 0) adapter.clear();
        adapter.addAll(list);*/
        newAdapter.addData(list);
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
        presenter.addReciterToMyReciters(reciterData);
    }

    @Override
    public void onReciterAddedToMyRecitersSuccess() {
        Alerter.create(getActivity())
                .setText(R.string.alrt_add_success_title)
                .setText(R.string.alrt_add_success_msg)
                .enableSwipeToDismiss()
                .setBackgroundColorRes(R.color.green_200)
                .show();
    }


    private void initComponents() {
        networkDisposable = ReactiveNetwork
                .observeNetworkConnectivity(context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(available -> {
                    if (available.available()) {
                        if (binding.reciterListLayout.getVisibility() == View.GONE) {
                            binding.reciterListLayout.setVisibility(View.VISIBLE);
                        }
                        binding.recitersRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                        binding.recitersRecyclerView.setHasFixedSize(true);
                        binding.recitersRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        //adapter.setOnClickHandler(this);
                        newAdapter = new NewAdapter(R.layout.item_reciter, new ArrayList<>(), getActivity(), this);
                        newAdapter.setAnimationEnable(true);
                        newAdapter.setAdapterAnimation(new ScaleInAnimation());
                        //binding.recitersRecyclerView.setAdapter(adapter);
                        binding.recitersRecyclerView.setAdapter(newAdapter);
                        presenter.loadReciters();
                    } else {
                        presenter.loadNoInternetConnectionView();
                    }
                });
    }

    @Override
    public void onNoInternetConnectionProvided() {
        binding.reciterListLayout.setVisibility(View.GONE);
        binding.noInternetLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        presenter.detachView();
        RxUtil.dispose(networkDisposable);
    }
}
