package com.mobidev.httprequest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobidev.httprequest.R;
import com.mobidev.httprequest.model.University;

import java.util.ArrayList;

/**
 * Created by lawrence on 7/9/15.
 */
public class MyCustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<University> items = new ArrayList<University>();

    public MyCustomAdapter(Context context, ArrayList<University> items) {

        this.context = context;
        this.items = items;

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return items.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);


            viewHolder = new ViewHolder();
            viewHolder.txtUniId = (TextView) view.findViewById(R.id.univesity_id);
            viewHolder.txtUniName = (TextView) view.findViewById(R.id.university_name);
            viewHolder.txtUniDesc = (TextView) view.findViewById(R.id.desc);


            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        University university = items.get(position);
        System.err.println("de " + university.getId());

        viewHolder.txtUniId.setText(Integer.toString(university.getId()));
        viewHolder.txtUniName.setText(university.getUniversityName());
        viewHolder.txtUniDesc.setText(university.getDesc());
        return view;
    }

    private static class ViewHolder {
        TextView txtUniName;
        TextView txtUniDesc;
        TextView txtUniId;
    }
}
