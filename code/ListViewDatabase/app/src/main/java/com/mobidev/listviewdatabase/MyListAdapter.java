package com.mobidev.listviewdatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lawrence on 4/18/15.
 */
public class MyListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<CountyModel> listItems;

    public MyListAdapter(Context context, ArrayList<CountyModel> items) {
        this.mContext = context;
        this.listItems = items;

    }

    @Override
    public int getCount() {

        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return listItems.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.counties_list_item, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.txtCountyName = (TextView) view.findViewById(R.id.county_name);
            viewHolder.txtCountyGovernor = (TextView) view.findViewById(R.id.county_governor);
            viewHolder.txtCountyDesc = (TextView) view.findViewById(R.id.county_desc);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CountyModel model = listItems.get(position);
        viewHolder.txtCountyName.setText(model.getCountyName());
        viewHolder.txtCountyGovernor.setText(model.getCountyGovernor());
        viewHolder.txtCountyDesc.setText(model.getCountyDesc());


        return view;
    }

    private static class ViewHolder {
        TextView txtCountyName;
        TextView txtCountyGovernor;
        TextView txtCountyDesc;
    }
}
