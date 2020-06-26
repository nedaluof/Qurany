package com.nedaluof.qurany.ui.reciter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.nedaluof.qurany.R;
import com.nedaluof.qurany.data.model.Reciters;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by nedaluof on 6/15/2020.
 */
public class RecitersAdapter extends BaseQuickAdapter<Reciters.Reciter, BaseViewHolder> {
    private ReciterView onClick;

    public RecitersAdapter(int layoutResId, @Nullable List<Reciters.Reciter> data, ReciterView onClick) {
        super(layoutResId, data);
        this.onClick = onClick;
    }


    @Override
    protected void convert(@NotNull BaseViewHolder holder, Reciters.Reciter reciter) {
        holder.setText(R.id.tv_reciter_name, reciter.getName().toUpperCase())
                .setText(R.id.tv_reciter_rewaya, reciter.getRewaya().toUpperCase())
                .setText(R.id.tv_reciter_sura_count, "There " + reciter.getCount() + " Sura")
                .itemView.setOnClickListener(v -> onClick.onClickGetReciterData(reciter));
    }
}
