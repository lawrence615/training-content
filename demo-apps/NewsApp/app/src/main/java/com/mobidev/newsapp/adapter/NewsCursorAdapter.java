package com.mobidev.newsapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobidev.newsapp.R;
import com.mobidev.newsapp.database.AppContract.*;
import com.mobidev.newsapp.ui.widgets.RelativeTimeTextView;
import com.squareup.picasso.Picasso;


public class NewsCursorAdapter extends CursorAdapter {

    private static final String LOG_TAG = "NewsCursorAdapter Class";

    private Context mContext;

    public NewsCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.mContext = context;
        this.mCursor = c;
    }

    /**
     * Binds an existing view to the data pointed to by cursor
     *
     * @param view
     * @param context
     * @param cursor
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder;
        viewHolder = (ViewHolder) view.getTag();

        viewHolder.txtTitle.setText(cursor.getString(viewHolder.titleIndex));
        Log.d(LOG_TAG, cursor.getString(viewHolder.titleIndex));
        viewHolder.txtCreatedAt.setReferenceTime(java.sql.Timestamp
                .valueOf(cursor.getString(viewHolder.dateIndex)).getTime());

        if (cursor.getString(viewHolder.imageIndex).equals("null")) {
            viewHolder.imgImageView.setVisibility(View.GONE);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewHolder.txtTitle
                    .getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            viewHolder.txtTitle.setLayoutParams(params);
            viewHolder.txtTitle.setPadding(0, 0, 0, 2);

            RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) viewHolder.txtCreatedAt
                    .getLayoutParams();
            params2.addRule(RelativeLayout.BELOW, R.id.title);
            viewHolder.txtCreatedAt.setLayoutParams(params2);
            viewHolder.txtCreatedAt.setPadding(0, 0, 0, 5);
        }

        Picasso.with(context)
                .load(cursor.getString(viewHolder.imageIndex))
                .into(viewHolder.imgImageView);

    }

    /**
     * Makes a new view to hold the data pointed to bu cursor
     *
     * @param context
     * @param cursor
     * @param parent
     * @return
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.news_list_item, null);

        final ViewHolder holder = new ViewHolder();


        holder.txtTitle = (TextView) view.findViewById(R.id.title);
        holder.imgImageView = (ImageView) view.findViewById(R.id.image);
        holder.txtCreatedAt = (RelativeTimeTextView) view
                .findViewById(R.id.created_at);

        holder.titleIndex = cursor.getColumnIndexOrThrow(NewsColumns.TITLE);
        holder.dateIndex = cursor
                .getColumnIndexOrThrow(NewsColumns.CREATED_AT);

        holder.imageIndex = cursor
                .getColumnIndexOrThrow(NewsColumns.IMAGE_URL);

        view.setTag(holder);
        return view;
    }

    private static class ViewHolder {
        TextView txtTitle;
        ImageView imgImageView;
        RelativeTimeTextView txtCreatedAt;
        int titleIndex;
        int imageIndex;
        int dateIndex;
    }


}
