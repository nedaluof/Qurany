package com.nedaluof.qurany.ui.myreciters;

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
import com.nedaluof.qurany.QuranyApplication;
import com.nedaluof.qurany.R;
import com.nedaluof.qurany.data.model.Reciter;
import com.nedaluof.qurany.databinding.MyRecitersFragmentBinding;
import com.nedaluof.qurany.ui.reciter.RecitersAdapter;
import com.nedaluof.qurany.ui.sura.ReciterSurasActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nedaluof on 7/5/2020.
 */
public class MyRecitersFragment extends Fragment implements MyRecitersView {
    public MyRecitersFragment() {/**/}

    private MyRecitersFragmentBinding binding;
    @Inject
    MyRecitersPresenter presenter;
    @Inject
    Context context;

    private MyRecitersAdapter adapter;

    /*
     * TODO 5/7/2020 : save the Favorite Reciters To Room then inflate
     *  them to recycler view  , again same process with suras
     * */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MyRecitersFragmentBinding.inflate(inflater, container, false);
        ((QuranyApplication) getActivity().getApplication()).getComponent().inject(this);
        initComponent();
        return binding.getRoot();
    }

    private void initComponent() {
        presenter.attachView(this);
        binding.recitersRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        binding.recitersRecyclerView.setHasFixedSize(true);
        binding.recitersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new MyRecitersAdapter(R.layout.item_reciter, new ArrayList<>(), this);
        adapter.setAdapterAnimation(new ScaleInAnimation());
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
        adapter.addData(list);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(context, "Error : " + message, Toast.LENGTH_SHORT).show();
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
        /*Todo delete from My Reciters List*/
        presenter.deleteFromMyReciters(reciterData);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        presenter.detachView();
    }
}
