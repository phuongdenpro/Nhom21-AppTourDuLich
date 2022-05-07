package com.example.travel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;


import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.TourViewHolder> {
    private final Context context;
    private final List<Tour> tourList;



    public TourAdapter( Context context,List<Tour> tourList) {
        this.tourList = tourList;
        this.context = context;
    }

    @NonNull
    @Override
    public TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View tourView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tour_item, parent, false);

        return new TourViewHolder(tourView);
    }

    @Override
    public void onBindViewHolder(@NonNull TourAdapter.TourViewHolder holder, int position) {
        Tour tour = tourList.get(position);
        holder.tvName.setText(tour.getNamePlace());
        Picasso.get().load(tour.getImageURL()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(holder.imgPlace);
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailActivity.class);
                Bundle bundle = new Bundle();
                i.putExtra("tour",tour);
                context.startActivity(i);
            }
        });
        holder.imgPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailActivity.class);
                Bundle bundle = new Bundle();
                i.putExtra("tour",tour);
                context.startActivity(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return tourList == null ? 0 : tourList.size();
    }

    public static class TourViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvName;
        private final ImageView imgPlace;
        public TourViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);

            imgPlace = itemView.findViewById(R.id.imgPlace);

        }


    }
}