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

public class RecyclerViewElement extends RecyclerView.Adapter<RecyclerViewElement.PaletteViewHolder> {
    private List<Element> data;
    private int orientation;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public RecyclerViewElement(@NonNull List<Element> data, @NonNull int orientation, @NonNull RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
        this.data = data;
        this.orientation = orientation;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    @Override
    public PaletteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row;
        if (this.orientation == 0) {
            // Display elements vertically.
            row = LayoutInflater.from(parent.getContext()).inflate(R.layout.rows, parent, false);
        } else {
            // Display elements horizontally.
            row = LayoutInflater.from(parent.getContext()).inflate(R.layout.columns, parent, false);
        }
        return new PaletteViewHolder(row);
    }

    @Override
    public void onBindViewHolder(PaletteViewHolder holder, int position) {
        Element element = data.get(position);
        holder.getTitleTextView().setText(element.getFirstLine());
        holder.getSubtitleTextView().setText(element.getSecondLine());
        holder.getImageView().setImageDrawable(element.getImage());
    }

    public int getOrientation() { return orientation; }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PaletteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleTextView;
        private TextView subtitleTextView;
        private ImageView imageView;

        public PaletteViewHolder(View itemView) {
            super(itemView);
            if (getOrientation() == 0) {
                // Display elements vertically.
                titleTextView = (TextView) itemView.findViewById(R.id.row_list_element_title);
                subtitleTextView = (TextView) itemView.findViewById(R.id.row_list_element_subtitle);
                imageView = (ImageView) itemView.findViewById(R.id.row_list_element_image);
            } else {
                // Display elements horizontally.
                titleTextView = (TextView) itemView.findViewById(R.id.columns_title);
                subtitleTextView = (TextView) itemView.findViewById(R.id.columns_subtitle);
                imageView = (ImageView) itemView.findViewById(R.id.columns_image);
            }
            itemView.setOnClickListener(this);
        }

        public TextView getTitleTextView() {
            return titleTextView;
        }

        public TextView getSubtitleTextView() {
            return subtitleTextView;
        }

        public ImageView getImageView() {
            return imageView;
        }

        @Override
        public void onClick(View v) { recyclerViewOnItemClickListener.onClick(v, getAdapterPosition()); }
    }
}
