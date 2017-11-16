package com.app.chacoad.huay.Util;

import android.util.Log;

import com.app.chacoad.huay.Model.FirstPrize;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by N on 11/12/2017.
 */

public class Lotto implements Callback<RSSFeed> {

    private static final String BASE_URL = "https://feeds.feedburner.com/";
    private static final String regex = "\\d{6}";
    private Long firstPrize;
    private DatabaseReference mLottoDatabase;
    private boolean isInDatabase;
    private String key;

    public void start() {
        key = "t".concat(new DayDate().getCurrentCycle());
        checkLottoExist(key);
//        if (!isInDatabase) {
//            getRSSPrize();
//        }
    }

    private void getRSSPrize() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create()).build();

        LottoAPI lottoAPI = retrofit.create(LottoAPI.class);

        Call<RSSFeed> call = lottoAPI.loadRSSFeed();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<RSSFeed> call, Response<RSSFeed> response) {
        if (response.isSuccessful()) {
            RSSFeed rss = response.body();
            this.setFirstPrize(rss.getFirstArticle().getDescription());
            writeLotto(key, getFirstPrize());
            Log.d("GUID", rss.getFirstArticle().getGuid());
            Log.d("pubDate", rss.getFirstArticle().getPubdate());
            Log.d("FormattedDate", rss.getFirstArticle().getFormattedPubdate());
            Log.d("Description", rss.getFirstArticle().getDescription());
            Log.d("FirstPrize", Long.toString(this.getFirstPrize()));
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<RSSFeed> call, Throwable t) {
        t.printStackTrace();

    }

    public Long getFirstPrize() {
        return firstPrize;
    }

    public void setFirstPrize(String description) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(description);
        if (m.find()) {
            this.firstPrize = Long.parseLong(m.group(0));
        }
    }

    public void setFirstPrize(Long firstPrize) {
        this.firstPrize = firstPrize;
    }

    public void setInDatabase(boolean inDatabase) {
        isInDatabase = inDatabase;
    }

    private void writeLotto(String key, Long number) {
        mLottoDatabase = FirebaseDatabase.getInstance().getReference().child("firstPrize").child(key).child("number");
        mLottoDatabase.setValue(number);
    }

    private void checkLottoExist(String key) {
        mLottoDatabase = FirebaseDatabase.getInstance().getReference().child("firstPrize").child(key);
        mLottoDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    setInDatabase(true);
                    FirstPrize firstPrize = dataSnapshot.getValue(FirstPrize.class);
                    setFirstPrize(firstPrize.getNumber());
                    Log.d("FireBaseFirstPrize", getFirstPrize().toString());
                } else {
                    Log.d("Lotto", "key not found on database");
                    setInDatabase(false);
                    getRSSPrize();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Lotto", databaseError.getDetails());
            }
        });
    }
}