package com.nedaluof.qurany.ui.myreciters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nedaluof.qurany.R;
import com.nedaluof.qurany.data.model.Reciter;
import com.nedaluof.qurany.databinding.MyRecitersFragmentBinding;
import com.nedaluof.qurany.ui.component.MyRecitersAdapter;
import com.nedaluof.qurany.ui.sura.ReciterSurasActivity;
import com.tapadoo.alerter.Alerter;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * Created by nedaluof on 7/5/2020.
 */
public class MyRecitersFragment extends DaggerFragment
        implements MyRecitersView {
    public MyRecitersFragment() {/**/}

    private MyRecitersFragmentBinding binding;
    @Inject
    MyRecitersPresenter presenter;
    @Inject
    Context context;
    @Inject
    MyRecitersAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MyRecitersFragmentBinding.inflate(inflater, container, false);
        initComponent();
        return binding.getRoot();
    }

    private void initComponent() {
        presenter.attachView(this);
        binding.recitersRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        binding.recitersRecyclerView.setHasFixedSize(true);
        binding.recitersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnClickHandlerMyReciters(this);
        binding.recitersRecyclerView.setAdapter(adapter);
        presenter.loadMyReciters();
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
    public void showMyReciters(List<Reciter> list) {
        if (list != null && !list.isEmpty()) {
            adapter.addAll(list);
            if (binding.reciterListLayout.getVisibility() == View.GONE) {
                binding.reciterListLayout.setVisibility(View.VISIBLE);
            }
        } else {
            binding.reciterListLayout.setVisibility(View.GONE);
            binding.noRecitersLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onError(String message) {
        Alerter.create(getActivity())
                .setTitle(R.string.alrt_err_occur_title)
                .setText(R.string.alrt_err_occur_msg)
                .hideIcon()
                .setBackgroundColorRes(R.color.red)
                .show();
    }

    @Override
    public void onClickGetReciterData(Reciter reciterData) {
        startActivity(
                new Intent(context, ReciterSurasActivity.class)
                        .putExtra("reciterData", reciterData)
        );
    }

    @Override
    public void onClickDeleteFromMyReciters(Reciter reciterData) {
        String msg1 = context.getString(R.string.alrt_delete_msg1);
        String msg2 = context.getString(R.string.alrt_delete_msg2);
        Alerter.create(getActivity())
                .setTitle(R.string.alrt_delete_title)
                .setText(msg1 + reciterData.getName() + msg2)
                .addButton(context.getString(R.string.alrt_delete_btn_ok), R.style.AlertButton, v -> {
                    presenter.deleteFromMyReciters(reciterData);
                    Alerter.hide();
                })
                .addButton(context.getString(R.string.alrt_delete_btn_cancel), R.style.AlertButton, v -> Alerter.hide())
                .enableSwipeToDismiss()
                .show();
    }

    @Override
    public void onReciterDeletedFromMyReciters() {
        adapter.clear();
        presenter.loadMyReciters();
        Alerter.create(getActivity())
                .setTitle(R.string.alrt_delete_success)
                .enableSwipeToDismiss()
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        presenter.detachView();
    }

}
