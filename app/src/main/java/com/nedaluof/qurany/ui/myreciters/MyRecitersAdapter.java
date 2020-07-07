package com.nedaluof.qurany.ui.myreciters;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.nedaluof.qurany.R;
import com.nedaluof.qurany.data.model.Reciter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by nedaluof on 7/7/2020.
 */
public class MyRecitersAdapter extends BaseQuickAdapter<Reciter, BaseViewHolder> {
    private MyRecitersView handleClick;
    @Inject
    Context context;

    public MyRecitersAdapter(int layoutResId, @Nullable List<Reciter> data, MyRecitersView handleClick) {
        super(layoutResId, data);
        this.handleClick = handleClick;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, Reciter reciter) {
        holder.setText(R.id.tv_reciter_name, reciter.getName().toUpperCase())
                .setText(R.id.tv_reciter_rewaya, reciter.getRewaya().toUpperCase())
                .setText(R.id.tv_reciter_sura_count, "There " + reciter.getCount() + " Sura")
                .getView(R.id.reciter_data_layout).setOnClickListener(v -> handleClick.onClickGetReciterData(reciter));

        holder.setImageResource(R.id.img_add_favorite, R.drawable.ic_favorite_selected);
        holder.getView(R.id.img_add_favorite)
                .setOnClickListener(v -> {
                    holder.setImageResource(R.id.img_add_favorite, R.drawable.ic_favorite_navigation);
                    handleClick.onClickDeleteFromMyReciters(reciter);
                });

    }

}
