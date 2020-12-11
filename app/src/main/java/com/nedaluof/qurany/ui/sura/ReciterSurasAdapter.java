package com.nedaluof.qurany.ui.sura;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.nedaluof.qurany.R;
import com.nedaluof.qurany.data.model.Sura;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by nedaluof on 6/16/2020.
 */
public class ReciterSurasAdapter extends BaseQuickAdapter<Sura, BaseViewHolder> {

    private static final String TAG = "ReciterSurasAdapter";
    private SurasView surasView;

    public ReciterSurasAdapter(int layoutResId, @Nullable List<Sura> data, SurasView surasView) {
        super(layoutResId, data);
        this.surasView = surasView;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, Sura suras) {
        holder.setText(R.id.tv_sura_name, suras.getName())
                .setText(R.id.tv_sura_rewaya, suras.getRewaya())
                .setText(R.id.tv_sura_number, String.valueOf(suras.getId()))
                .getView(R.id.img_download_sura).setOnClickListener(v -> surasView.onDownloadClick(suras.getId()));

        holder.getView(R.id.img_play_sura)
                .setOnClickListener(v -> surasView.onClickPlay(suras.getId()));
    }
}
