package com.app.chacoad.huay;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.app.chacoad.huay.Adapter.LotoNumberListAdapter;
import com.app.chacoad.huay.Model.Customer;
import com.app.chacoad.huay.Model.LotoNumber;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class NumberActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "NumberActivity";
    int numberCount = 1;
    int priceCount = 1;
    String keyCustomerName;
    String keyCustomerId;
    String keyHuayDate;
    Customer cus;
    ArrayList<LotoNumber> lotoNumberArrayList;
    LotoNumberListAdapter adapter;
    AlertDialog.Builder builder;
    String numberKey;
    int nextNumberKey;
    private Button numberActivityAdd;
    private EditText numberActivityNumber;
    private EditText numberActivityPrice;
    private ListView numberActivityListview;
    private DatabaseReference mDatabase;
    private DatabaseReference ref;
    private DatabaseReference refDel;
    private long nextNumberId = 1;
    private HashMap<String, LotoNumber> numbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);
        numberActivityNumber = findViewById(R.id.number_activity_number);
        numberActivityAdd = findViewById(R.id.number_activity_add);
        numberActivityPrice = findViewById(R.id.number_activity_price);
        numberActivityListview = findViewById(R.id.number_activity_listview);
        numberActivityAdd.setOnClickListener(this);
        numberActivityAdd.setEnabled(false);
        lotoNumberArrayList = new ArrayList<>();
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

        adapter = new LotoNumberListAdapter(this, lotoNumberArrayList);
        numberActivityListview.setAdapter(adapter);
        numberActivityListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemClick");
            }
        });
        setTitle(keyCustomerName);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ref = mDatabase.child(keyHuayDate).child("c" + keyCustomerId).child("numbers");
        numbers = new HashMap<String, LotoNumber>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                numberActivityAdd.setEnabled(true);
                lotoNumberArrayList.clear();
                numbers.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    LotoNumber ln = new LotoNumber(ds.getValue(LotoNumber.class).getNumber(), ds.getValue(LotoNumber.class).getPrice());
                    ln.setKeyname(ds.getKey());
                    numbers.put(ds.getKey(), ln);
                    lotoNumberArrayList.add(ln);
                    int currentNumberKey = Integer.parseInt(ds.getKey().substring(1));
                    if (currentNumberKey > nextNumberKey) {
                        nextNumberKey = currentNumberKey;
                    }
                }
                nextNumberId = nextNumberKey + 1;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        numberActivityListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View arg1, int pos, long id) {
                LotoNumber item = (LotoNumber) adapterView.getItemAtPosition(pos);
                numberKey = item.getKeyname();
                builder = new AlertDialog.Builder(NumberActivity.this);
                builder.setMessage(R.string.th_confirm_del_num);
                builder.setPositiveButton(R.string.th_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        refDel = mDatabase.child(keyHuayDate).child("c" + keyCustomerId).child("numbers").child(numberKey);
                        refDel.removeValue();
                    }
                });
                builder.setNegativeButton(R.string.th_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                return true;
            }
        });

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
            case R.id.number_activity_add:
                String number = numberActivityNumber.getText().toString();
                String price = numberActivityPrice.getText().toString();
                if (number.length() == 2 || number.length() == 3) {
                    if (price.length() >= 1 && price.length() <= 4) {
                        insertDatabase(Long.parseLong(number), Long.parseLong(price));
                        numberActivityNumber.setText("");
                        numberActivityPrice.setText("");
                        numberActivityNumber.requestFocus();
                        lotoNumberArrayList.clear();
                        adapter.notifyDataSetChanged();
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
