package com.app.chacoad.huay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.chacoad.huay.R;

import java.util.ArrayList;

/**
 * Created by Admin on 18/11/2560.
 */

public class HuayAdapter extends ArrayAdapter<String[]> {
    public static final String TAG = CustomerListAdapter.class.getSimpleName();


    public HuayAdapter(Context context, ArrayList<String[]> list) {
        super(context, 0, list);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View row;
        if (convertView == null) {
            row = LayoutInflater.from(getContext()).inflate(R.layout.list_huay, viewGroup, false);
        } else {
            row = convertView;
        }

        String[] value = getItem(position);

        HuayAdapter.ViewHolder viewHolder = new HuayAdapter.ViewHolder(row);

        viewHolder.huayId.setText(value[0]);
        viewHolder.huayText.setText(value[1]);


        return row;
    }

    public static class ViewHolder {
        TextView huayId;
        TextView huayText;

        public ViewHolder(View view) {
            huayId = view.findViewById(R.id.huay_id);
            huayText = view.findViewById(R.id.huay_text);
        }
    }
}
