package com.nedaluof.qurany.ui.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nedaluof.qurany.data.DataManager;
import com.nedaluof.qurany.data.model.Reciter;
import com.nedaluof.qurany.databinding.ItemReciterBinding;
import com.nedaluof.qurany.ui.reciter.ReciterView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

/**
 * Created by nedaluof on 7/12/2020.
 */
public class RecitersAdapter extends RecyclerView.Adapter<RecitersAdapter.RecitersViewHolder> {
    private ReciterView onClickHandler;
    private List<Reciter> list = new ArrayList<>();
    private Context context;
    private DataManager dataManager;

    @Inject
    public RecitersAdapter(DataManager dataManager, @ApplicationContext Context context) {
        this.dataManager = dataManager;
        this.context = context;
    }

    //must set before set data list to the adapter
    public void setOnClickHandler(ReciterView onClickHandler) {
        this.onClickHandler = onClickHandler;
    }

    @NonNull
    @Override
    public RecitersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecitersViewHolder(ItemReciterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull RecitersViewHolder holder, int position) {
        holder.binding.tvReciterName.setText(list.get(position).getName().toUpperCase());
        holder.binding.tvReciterRewaya.setText(list.get(position).getRewaya().toUpperCase());
        holder.binding.tvReciterSuraCount.setText("There ".concat(list.get(position).getCount()).concat(" Sura"));
        holder.binding.reciterDataLayout.setOnClickListener(v -> onClickHandler.onClickGetReciterData(list.get(position)));
        if (list.get(position).getInMyReciters()) {
            holder.binding.imgAddFavorite.setVisibility(View.GONE);
        } else {
            holder.binding.imgAddFavorite.setVisibility(View.VISIBLE);
        }

        holder.binding.imgAddFavorite.setOnClickListener(v -> {
            onClickHandler.onClickAddToMyReciters(list.get(position));
            holder.binding.imgAddFavorite.setVisibility(View.GONE);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(Reciter reciter) {
        list.add(reciter);
        notifyItemInserted(list.size() - 1);
    }

    public void addAll(List<Reciter> list) {
        for (Reciter reciter : list) {
            add(reciter);
        }
    }

    public void remove(Reciter reciter) {
        int position = list.indexOf(reciter);
        if (position > -1) {
            list.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public Reciter getItem(int position) {
        return list.get(position);
    }

    static class RecitersViewHolder extends RecyclerView.ViewHolder {
        private ItemReciterBinding binding;

        public RecitersViewHolder(@NonNull View view) {
            super(view);
            binding = ItemReciterBinding.bind(view);
        }
    }
}
