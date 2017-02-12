package com.wildeastcoders.pantroid.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wildeastcoders.pantroid.R;
import com.wildeastcoders.pantroid.model.PantryItemType;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Majfrendmartin on 2017-02-11.
 */

public class PantryItemTypesAdapter extends RecyclerView.Adapter<PantryItemTypesAdapter.ViewHolder> {

    private List<PantryItemType> items = new CopyOnWriteArrayList<>();

    public void updateItemsList(List<PantryItemType> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public PantryItemTypesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_type, parent, false);
        return new PantryItemTypesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PantryItemTypesAdapter.ViewHolder holder, int position) {
        holder.tvTypeName.setText(items.get(position).getName());
        holder.tvTypeId.setText(items.get(position).getId().toString());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_type_name)
        TextView tvTypeName;

        @BindView(R.id.tv_type_id)
        TextView tvTypeId;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
