package com.app.chacoad.huay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.chacoad.huay.Model.LotoNumber;
import com.app.chacoad.huay.R;

import java.util.ArrayList;

public class LotoNumberListAdapter extends ArrayAdapter<LotoNumber> {
    public static final String LOG_TAG = LotoNumberListAdapter.class.getSimpleName();


    public LotoNumberListAdapter(Context context, ArrayList<LotoNumber> list) {
        super(context, 0, list);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View row = null;

        if (convertView == null) {
            row = LayoutInflater.from(getContext()).inflate(R.layout.list_number, viewGroup, false);
        } else {
            row = convertView;
        }

        LotoNumber ln = getItem(position);
        ViewHolder viewHolder = new ViewHolder(row);
        viewHolder.numberId.setText(Long.toString(position));
        viewHolder.numberNumber.setText(Long.toString(ln.getNumber()));
        viewHolder.numberPrice.setText(Long.toString(ln.getPrice()));

        return row;
    }

    public static class ViewHolder {
        TextView numberId;
        TextView numberNumber;
        TextView numberPrice;

        public ViewHolder(View view) {
            numberId = view.findViewById(R.id.number_id);
            numberNumber = view.findViewById(R.id.number_number);
            numberPrice = view.findViewById(R.id.number_price);
        }
    }
}
