package com.mobidev.recyclerviewexample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by lawrence on 7/17/15.
 * <p/>
 * The adapter extends ecyclerView.Adapter class passing our internal class that implements ViewHolder pattern
 */
public class CodingLoveAdapter extends RecyclerView.Adapter<CodingLoveAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<CodingLoveItem> items;


    /**
     * Constructor
     *
     * @param context
     * @param items
     */
    public CodingLoveAdapter(Context context, ArrayList<CodingLoveItem> items) {
        this.mContext = context;
        this.items = items;
    }

    /**
     * creates a new instance of ViewHolder
     *
     * @param viewGroup
     * @param i
     * @return
     */
    @Override
    public CodingLoveAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_card_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    /**
     * This is where the data is shown in the UI (we bind our data to the views here)
     *
     * @param viewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(CodingLoveAdapter.ViewHolder viewHolder, int i) {
        CodingLoveItem codingLoveItem = items.get(i);
        viewHolder.tvDescription.setText(codingLoveItem.item_description);

        Glide.with(mContext)
                .load(codingLoveItem.item_image)
                .asGif()
                .placeholder(R.drawable.loading_spinner)
                .crossFade()
                .into(viewHolder.imgThumbnail);

    }

    /**
     * Returns the total number of items in the data set hold by the adapter
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgThumbnail;
        public TextView tvDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView) itemView.findViewById(R.id.img_thumbnail);
            tvDescription = (TextView) itemView.findViewById(R.id.tv_description);
        }
    }
}
