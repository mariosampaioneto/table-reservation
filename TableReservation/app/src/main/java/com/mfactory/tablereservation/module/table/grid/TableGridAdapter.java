package com.mfactory.tablereservation.module.table.grid;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mfactory.tablereservation.R;
import com.mfactory.tablereservation.model.Table;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TableGridAdapter extends RecyclerView.Adapter<TableGridAdapter.ViewHolder> {

    private List<Table> mTables;
    private OnItemClickListener mOnItemCLickListener;

    public TableGridAdapter() {
        mTables = new ArrayList<>();
    }

    @Override
    public TableGridAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_table_grid, parent, false);
        return new TableGridAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TableGridAdapter.ViewHolder holder, final int position) {
        Table table = mTables.get(position);

        holder.table.setText(String.valueOf(table.getNumber()));

        Context context = holder.view.getContext();
        if (table.isAvailable()) {
            holder.image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_empty_table));
            holder.image.setColorFilter(ContextCompat.getColor(context, R.color.table_available));
            holder.status.setText(context.getString(R.string.table_grid_screen_available_label));
            holder.customerContainer.setVisibility(View.INVISIBLE);
        } else {
            holder.image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_table));
            holder.image.setColorFilter(ContextCompat.getColor(context, R.color.table_unavailable));

            if (table.getCustomer() != null && !TextUtils.isEmpty(table.getCustomer().getFullName())) {
                holder.customerContainer.setVisibility(View.VISIBLE);
                holder.status.setText(context.getString(R.string.table_grid_screen_reserved_label));
                holder.customerName.setText(table.getCustomer().getFullName());
            } else {
                holder.customerContainer.setVisibility(View.INVISIBLE);
                holder.status.setText(context.getString(R.string.table_grid_screen_unavailable_label));
                holder.customerName.setText(context.getString(R.string.table_grid_screen_available_no_customer));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mTables.size();
    }

    public void setTables(List<Table> tables) {
        this.mTables.clear();
        this.mTables.addAll(tables);
        notifyDataSetChanged();
    }

    public List<Table> getTables() {
        return mTables;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mOnItemCLickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Table table, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;

        @BindView(R.id.table_root)
        CardView rootLayout;

        @BindView(R.id.table)
        TextView table;

        @BindView(R.id.image)
        ImageView image;

        @BindView(R.id.status)
        TextView status;

        @BindView(R.id.customer_container)
        LinearLayout customerContainer;

        @BindView(R.id.customer_name)
        TextView customerName;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
            rootLayout.setOnClickListener(v -> {
                int position = getAdapterPosition();
                mOnItemCLickListener.onItemClick(v, mTables.get(position), position);
            });
        }
    }
}
