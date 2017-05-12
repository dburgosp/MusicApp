package com.example.david.musicapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by David on 11/05/2017.
 */
public class ColumnElement extends RecyclerView.Adapter<ColumnElement.PaletteViewHolder> {
    private List<Element> data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public ColumnElement(@NonNull List<Element> data, @NonNull RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
        this.data = data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    @Override
    public PaletteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.columns, parent, false);
        return new PaletteViewHolder(row);
    }

    @Override
    public void onBindViewHolder(PaletteViewHolder holder, int position) {
        Element element = data.get(position);
        holder.getTitleTextView().setText(element.getFirstLine());
        holder.getSubtitleTextView().setText(element.getSecondLine());
        holder.getImageView().setImageDrawable(element.getImage());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PaletteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView horizontalTitleTextView;
        private TextView horizontalSubtitleTextView;
        private ImageView horizontalImageView;

        public PaletteViewHolder(View itemView) {
            super(itemView);
            horizontalTitleTextView = (TextView) itemView.findViewById(R.id.horizontalTitleTextView);
            horizontalSubtitleTextView = (TextView) itemView.findViewById(R.id.horizontalSubtitleTextView);
            horizontalImageView = (ImageView) itemView.findViewById(R.id.horizontalImageView);
            itemView.setOnClickListener(this);
        }

        public TextView getTitleTextView() {
            return horizontalTitleTextView;
        }
        public TextView getSubtitleTextView() {
            return horizontalSubtitleTextView;
        }
        public ImageView getImageView() {
            return horizontalImageView;
        }

        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
        }
    }
}