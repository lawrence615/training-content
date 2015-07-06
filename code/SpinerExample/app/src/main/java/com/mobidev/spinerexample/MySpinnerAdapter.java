package com.mobidev.spinerexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lawrence on 5/6/15.
 */
public class MySpinnerAdapter extends ArrayAdapter {

    private Context mContext;
    private List<FilterSpinner> itemList;
    private int resource;

    public MySpinnerAdapter(Context context, int resource,
                            List<FilterSpinner> itemList) {
        super(context, resource, itemList);
        this.mContext = context;
        this.itemList = itemList;
        this.resource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View row = inflater.inflate(
                resource == 0 ? R.layout.android_os_spinner_item : resource,
                parent, false);
        TextView text1 = (TextView) row.findViewById(android.R.id.text1);
        text1.setText(itemList.get(position).getVersionName());
        return row;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View row = inflater.inflate(
                R.layout.android_os_spinner_item_dropdown, parent, false);
        TextView text1 = (TextView) row.findViewById(android.R.id.text1);
        text1.setText(itemList.get(position).getVersionName());
        return row;
    }
}
