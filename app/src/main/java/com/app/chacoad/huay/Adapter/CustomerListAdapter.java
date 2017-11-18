package com.app.chacoad.huay.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.chacoad.huay.Model.Customer;
import com.app.chacoad.huay.R;

import java.util.ArrayList;

public class CustomerListAdapter extends ArrayAdapter<Customer> {
    public static final String TAG = CustomerListAdapter.class.getSimpleName();


    public CustomerListAdapter(Context context, ArrayList<Customer> list) {
        super(context, 0, list);
        Log.d(TAG, "Constructor");
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View row;
        if (convertView == null) {
            row = LayoutInflater.from(getContext()).inflate(R.layout.list_customer, viewGroup, false);
        } else {
            row = convertView;
        }
        ViewHolder viewHolder = new ViewHolder(row);
        Customer cus = getItem(position);
        viewHolder.customerId.setText(Long.toString(cus.getCustomerId()));
        viewHolder.customerName.setText(cus.getCustomerName());

        return row;
    }

    public static class ViewHolder {
        TextView customerId;
        TextView customerName;

        public ViewHolder(View view) {
            customerId = view.findViewById(R.id.customer_id);
            customerName = view.findViewById(R.id.customer_name);
        }
    }
}
