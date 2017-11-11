package com.app.chacoad.huay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.app.chacoad.huay.Adapter.CustomerListAdapter;
import com.app.chacoad.huay.Model.Customer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "CustomerActivity";
    ListView listViewCustomer;
    EditText newCustomerName;
    Button addNewCustomer;
    String keyHuayDate;
    ArrayList<Customer> customerArrayList;
    CustomerListAdapter adapter;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private long currentCustomerId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        listViewCustomer = findViewById(R.id.listview_customer);
        newCustomerName = findViewById(R.id.new_customer_name);
        addNewCustomer = findViewById(R.id.add_new_customer);
        addNewCustomer.setEnabled(false);
        addNewCustomer.setOnClickListener(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        customerArrayList = new ArrayList<>();

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


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        adapter = new CustomerListAdapter(this, customerArrayList);
        listViewCustomer.setAdapter(adapter);
        listViewCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Customer item = (Customer) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getApplicationContext(), NumberActivity.class);
                Log.d(TAG, "put extra -------");
                intent.putExtra("key_huay_date", keyHuayDate);
                intent.putExtra("key_customer_id", Long.toString(item.getCustomerId()));
                intent.putExtra("key_customer_name", item.getCustomerName());
                Log.d(TAG, "put extra key_huay_date     -------" + keyHuayDate);
                Log.d(TAG, "put extra key_customer_id   -------" + item.getCustomerId());
                Log.d(TAG, "put extra key_customer_name -------" + item.getCustomerName());
                startActivity(intent);
            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                showData(dataSnapshot);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showData(DataSnapshot dataSnapshot) {
        customerArrayList.clear();
        addNewCustomer.setEnabled(true);
        int customerCount = 0;
        Log.d(TAG, "keyHuayDate: " + keyHuayDate);
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            for (DataSnapshot ds2 : ds.getChildren()) {
                Log.d(TAG, "keyHuayDate " + ds.getKey());
                Customer cus = ds2.getValue(Customer.class);
                Log.d(TAG, "keyHuayDate " + cus.getCustomerName());
                customerArrayList.add(cus);
                customerCount++;
            }
        }

        adapter.notifyDataSetChanged();
        currentCustomerId = customerCount;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_new_customer:
                if (newCustomerName.getText().toString().length() == 0) {

                } else {
                    Customer cus = new Customer();
                    cus.setCustomerName(newCustomerName.getText().toString());
                    cus.setCustomerId(currentCustomerId);
                    myRef.child(keyHuayDate).child("c" + currentCustomerId).setValue(cus);
                    customerArrayList.clear();
                    adapter.notifyDataSetChanged();
                    newCustomerName.setText("");
                }

                break;
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
