package com.mobidev.customlayoutlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lawrence on 4/18/15.
 */
public class MyListAdapter extends BaseAdapter implements Filterable {

    private Context mContext;
    private ArrayList<CountyModel> listItems;
    private ArrayList<CountyModel> mStringFilterList;

    public MyListAdapter(Context context, ArrayList<CountyModel> items) {
        this.mContext = context;
        this.listItems = items;
        this.mStringFilterList = items;

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

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listItems = (ArrayList<CountyModel>) results.values;
                notifyDataSetChanged();
            }

            //Invoked in a worker thread to filter the data according to the constraint.
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint != null && constraint.length() > 0) {
                    ArrayList<CountyModel> filterList = new ArrayList<CountyModel>();
                    for (int i = 0; i < mStringFilterList.size(); i++) {
                        if ((mStringFilterList.get(i).getCountyName().toUpperCase())
                                .contains(constraint.toString().toUpperCase())) {
                            CountyModel counties = new CountyModel(mStringFilterList.get(i).getCountyName(), mStringFilterList.get(i).getCountyGovernor(), mStringFilterList.get(i).getCountyDesc());
                            filterList.add(counties);
                        }
                    }
                    results.count = filterList.size();
                    results.values = filterList;
                } else {
                    results.count = mStringFilterList.size();
                    results.values = mStringFilterList;
                }
                return results;
            }
        };
        return filter;
    }

    private static class ViewHolder {
        TextView txtCountyName;
        TextView txtCountyGovernor;
        TextView txtCountyDesc;
    }
}
