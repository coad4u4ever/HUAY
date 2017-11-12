package com.app.chacoad.huay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.chacoad.huay.Model.Customer;
import com.app.chacoad.huay.Model.LotoNumber;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class NumberActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "NumberActivity";
    int numberCount = 1;
    int priceCount = 1;
    String keyCustomerName;
    String keyCustomerId;
    String keyHuayDate;
    Customer cus;
    private Button numberActivityAdd;
    private EditText numberActivityNumber;
    private EditText numberActivityPrice;
    private DatabaseReference mDatabase;
    private DatabaseReference ref;
    private long nextNumberId = 1;
    private HashMap<String, LotoNumber> numbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);
        numberActivityNumber = findViewById(R.id.number_activity_number);
        numberActivityAdd = findViewById(R.id.number_activity_add);
        numberActivityPrice = findViewById(R.id.number_activity_price);
        numberActivityAdd.setOnClickListener(this);
        numberActivityAdd.setEnabled(false);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                keyHuayDate = null;
                keyCustomerName = null;
                keyCustomerId = null;
            } else {
                keyHuayDate = extras.getString("key_huay_date");
                keyCustomerName = extras.getString("key_customer_name");
                keyCustomerId = extras.getString("key_customer_id");
            }
        } else {
            keyHuayDate = (String) savedInstanceState.getSerializable("key_huay_date");
            keyCustomerName = (String) savedInstanceState.getSerializable("key_customer_name");
            keyCustomerId = (String) savedInstanceState.getSerializable("key_customer_id");
        }
        cus = new Customer();
        cus.setCustomerName(keyCustomerName);
        cus.setCustomerId(Long.parseLong(keyCustomerId));

        setTitle(keyCustomerName);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ref = mDatabase.child(keyHuayDate).child("c" + keyCustomerId).child("numbers");
        numbers = new HashMap<String, LotoNumber>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                numberActivityAdd.setEnabled(true);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    LotoNumber ln = new LotoNumber(ds.getValue(LotoNumber.class).getNumber(), ds.getValue(LotoNumber.class).getPrice());
                    numbers.put(ds.getKey(), ln);
                }

                Log.d(TAG, "numbers size: " + numbers.size());
                nextNumberId = numbers.size() + 1;
                Log.d(TAG, "nextNumberId : " + nextNumberId);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.number_activity_add:
                String number = numberActivityNumber.getText().toString();
                String price = numberActivityPrice.getText().toString();
                if (number.length() == 2 || number.length() == 3) {
                    if (price.length() >= 1 && price.length() <= 4) {
                        insertDatabase(Long.parseLong(number), Long.parseLong(price));
                    } else {
                        Toast.makeText(this, "ใส่จำนวนเงิน 1 ถึง 4 ตัว", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "ใส่ตัวเลข 2 หรือ 3 ตัว", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void insertDatabase(long number, long price) {
        LotoNumber num = new LotoNumber(number, price);
        numbers.put("n" + nextNumberId, num);
        cus.setNumbers(numbers);
        mDatabase.child(keyHuayDate).child("c" + keyCustomerId).setValue(cus);
    }
}
