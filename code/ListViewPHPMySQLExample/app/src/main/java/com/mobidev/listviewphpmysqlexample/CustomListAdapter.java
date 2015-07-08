package com.mobidev.listviewphpmysqlexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lawrence on 4/19/15.
 */
public class CustomListAdapter extends BaseAdapter {
    private ArrayList<UniversitiesModel> listItems;
    private Context mContext;

    public CustomListAdapter(Context context, ArrayList<UniversitiesModel> items) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.university_list_item, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.txtUniName = (TextView) view.findViewById(R.id.university_name);
            viewHolder.txtUniDesc = (TextView) view.findViewById(R.id.university_desc);
            viewHolder.txtUniId = (TextView) view.findViewById(R.id.uniId);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        UniversitiesModel model = listItems.get(position);
        System.out.println(model.getUniversityName());
        viewHolder.txtUniName.setText(model.getUniversityName());
        viewHolder.txtUniDesc.setText(model.getDescription());
        viewHolder.txtUniId.setText(Integer.toString(model.getId()));


        return view;
    }

    private static class ViewHolder {
        TextView txtUniName;
        TextView txtUniDesc;
        TextView txtUniId;
    }
}
