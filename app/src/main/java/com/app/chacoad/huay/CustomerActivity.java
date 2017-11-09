package com.app.chacoad.huay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.chacoad.huay.Adapter.CustomerListAdapter;
import com.app.chacoad.huay.Model.Customer;

import java.util.ArrayList;

public class CustomerActivity extends AppCompatActivity implements View.OnClickListener {

    ListView listViewCustomer;
    String keyHuayDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        listViewCustomer = findViewById(R.id.listview_customer);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                keyHuayDate = null;
            } else {
                keyHuayDate = extras.getString("key_huay_date");
            }
        } else {
            keyHuayDate = (String) savedInstanceState.getSerializable("key_huay_date");
        }

        ArrayList<Customer> customerArrayList = new ArrayList<>();
        customerArrayList.add(new Customer("1", "name1"));
        customerArrayList.add(new Customer("2", "name2"));

        CustomerListAdapter adapter = new CustomerListAdapter(this, customerArrayList);

        listViewCustomer.setAdapter(adapter);

        listViewCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Customer item = (Customer) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getApplicationContext(), NumberActivity.class);
                intent.putExtra("key_huay_date", keyHuayDate);
                intent.putExtra("key_customer_name", item.getCustomerName());
                intent.putExtra("key_customer_id", item.getCustomerId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
