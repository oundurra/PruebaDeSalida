package com.example.pruebadesalida;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CakeAdapter extends RecyclerView.Adapter<CakeAdapter.ViewHolder> {
    List<Cake> cakeList;
    Context context;

    public CakeAdapter(List<Cake> cakeList, Context context) {
        this.cakeList = cakeList;
        this.context = context;
    }

    @NonNull
    @Override
    public CakeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.cake_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Cake cake = cakeList.get(position);
        try {
            Picasso.get().load(cakeList.get(position).getImage()).into(holder.ivCake);
            holder.tvTitle.setText(cakeList.get(position).getTitle());
            holder.tvPrice.setText(Integer.toString(cakeList.get(position).getPrice()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, CakeDetailActivity.class)
                            .putExtra("id",cakeList.get(position).getId())
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    );
                }
            });
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return cakeList.size();
    }

    /*public void refreshMyList(List<Cake> list){
        this.cakeList.clear();
        this.cakeList.addAll(list);
        this.notifyDataSetChanged();
    }*/

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivCake;
        public TextView tvTitle;
        public TextView tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            this.ivCake = (ImageView) itemView.findViewById(R.id.ivCake);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            this.tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
        }
    }

}
