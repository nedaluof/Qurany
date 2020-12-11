package com.nedaluof.qurany.ui.component;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.nedaluof.qurany.R;
import com.nedaluof.qurany.data.model.Reciter;
import com.nedaluof.qurany.ui.reciter.ReciterView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by nedaluof on 7/15/2020.
 */
public class NewAdapter extends BaseQuickAdapter<Reciter, BaseViewHolder> {
    private ReciterView viewOnClick;
    private Context context;

    public NewAdapter(int layoutResId, @Nullable List<Reciter> data, Context context, ReciterView reciterView) {
        super(layoutResId, data);
        this.viewOnClick = reciterView;
        this.context = context;
    }


    @Override
    protected void convert(@NotNull BaseViewHolder holder, Reciter reciter) {
        holder.setText(R.id.tv_reciter_name, reciter.getName().toUpperCase());
        holder.setText(R.id.tv_reciter_rewaya, reciter.getRewaya().toUpperCase());
        holder.setText(R.id.tv_reciter_sura_count, context.getString(R.string.sura_count) + reciter.getCount());
        holder.getView(R.id.reciter_data_layout).setOnClickListener(v -> viewOnClick.onClickGetReciterData(reciter));

        if (reciter.getInMyReciters()) {
            holder.getView(R.id.img_add_favorite).setVisibility(View.GONE);
        } else {
            holder.getView(R.id.img_add_favorite).setVisibility(View.VISIBLE);
        }

        holder.getView(R.id.img_add_favorite).setOnClickListener(v -> {
            viewOnClick.onClickAddToMyReciters(reciter);
            holder.getView(R.id.img_add_favorite).setVisibility(View.GONE);
        });
    }
}
