package com.nedaluof.qurany.ui.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nedaluof.qurany.R;
import com.nedaluof.qurany.data.DataManager;
import com.nedaluof.qurany.data.model.Reciter;
import com.nedaluof.qurany.databinding.ItemReciterBinding;
import com.nedaluof.qurany.ui.myreciters.MyRecitersView;
import com.nedaluof.qurany.ui.reciter.ReciterView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nedaluof on 7/13/2020.
 */
public class MyRecitersAdapter extends RecyclerView.Adapter<MyRecitersAdapter.RecitersViewHolder> {
    private MyRecitersView onClickHandlerMy;
    private List<Reciter> list = new ArrayList<>();
    private Context context;
    private DataManager dataManager;

    @Inject
    public MyRecitersAdapter(DataManager dataManager, Context context) {
        this.dataManager = dataManager;
        this.context = context;
    }

    public void setOnClickHandlerMyReciters(MyRecitersView onClickHandlerMy) {
        this.onClickHandlerMy = onClickHandlerMy;
    }

    @NonNull
    @Override
    public MyRecitersAdapter.RecitersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyRecitersAdapter.RecitersViewHolder(ItemReciterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecitersAdapter.RecitersViewHolder holder, int position) {
        holder.binding.tvReciterName.setText(list.get(position).getName().toUpperCase());
        holder.binding.tvReciterRewaya.setText(list.get(position).getRewaya().toUpperCase());
        holder.binding.tvReciterSuraCount.setText(context.getString(R.string.sura_count).concat(list.get(position).getCount()));

        holder.binding.reciterDataLayout.setOnClickListener(v -> onClickHandlerMy.onClickGetReciterData(list.get(position)));
        holder.binding.imgAddFavorite.setImageResource(R.drawable.ic_favorite_selected);
        holder.binding.imgAddFavorite.setOnClickListener(v -> {
            onClickHandlerMy.onClickDeleteFromMyReciters(list.get(position));
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
