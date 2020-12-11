package com.nedaluof.qurany.ui.component;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.nedaluof.qurany.R;
import com.nedaluof.qurany.data.model.Reciter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by nedaluof on 7/15/2020.
 */
public class ReciterAdapter extends BaseQuickAdapter<Reciter, BaseViewHolder> {
    private ReciterAdapterListener listener;
    private final Context context;

    public ReciterAdapter(int layoutResId, @Nullable List<Reciter> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    public void setListener(ReciterAdapterListener listener) {
        this.listener = listener;
    }


    @Override
    protected void convert(@NotNull BaseViewHolder holder, Reciter reciter) {
        holder.setText(R.id.tv_reciter_name, reciter.getName().toUpperCase());
        holder.setText(R.id.tv_reciter_rewaya, reciter.getRewaya().toUpperCase());
        holder.setText(R.id.tv_reciter_sura_count, context.getString(R.string.sura_count) + reciter.getCount());
        holder.getView(R.id.reciter_data_layout).setOnClickListener(v -> listener.onClickGetReciterData(reciter));

        if (reciter.getInMyReciters()) {
            holder.getView(R.id.img_add_favorite).setVisibility(View.GONE);
        } else {
            holder.getView(R.id.img_add_favorite).setVisibility(View.VISIBLE);
        }

        holder.getView(R.id.img_add_favorite).setOnClickListener(v -> {
            listener.onClickAddToMyReciters(reciter);
            holder.getView(R.id.img_add_favorite).setVisibility(View.GONE);
        });
    }

    public interface ReciterAdapterListener {
        void onClickGetReciterData(Reciter reciter);

        void onClickAddToMyReciters(Reciter reciter);
    }
}
