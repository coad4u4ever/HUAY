package com.app.chacoad.huay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.chacoad.huay.Model.Customer;
import com.app.chacoad.huay.R;

import java.util.ArrayList;

public class CustomerListAdapter extends ArrayAdapter<Customer> {
    public static final String LOG_TAG = CustomerListAdapter.class.getSimpleName();


    public CustomerListAdapter(Context context, ArrayList<Customer> list) {
        super(context, 0, list);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Customer cus = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_customer, viewGroup, false);
        }
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.customerId.setText(cus.getCustomerId());
        viewHolder.customerName.setText(cus.getCustomerName());

        return convertView;
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
