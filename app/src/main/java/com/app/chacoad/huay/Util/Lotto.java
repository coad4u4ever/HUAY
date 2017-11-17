package com.app.chacoad.huay.Util;

import android.util.Log;

import com.app.chacoad.huay.Model.FirstPrize;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private Long firstPrize;
    private DatabaseReference mLottoDatabase;
    private String key;

    public void start() {
        key = "t".concat(new DayDate().getCurrentCycle());
        checkLottoExist(key);
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
            Article article = response.body().getFirstArticle();
            setFirstPrize(article.getFirstPrize());
            writeLotto(key, firstPrize);
            Log.d("GUID", article.getGuid());
            Log.d("pubDate", article.getPubdate());
            Log.d("FormattedDate", article.getFormattedPubdate());
            Log.d("Description", article.getDescription());
            Log.d("FirstPrize", Long.toString(firstPrize));
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

    public void setFirstPrize(Long firstPrize) {
        this.firstPrize = firstPrize;
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
                    FirstPrize firstPrize = dataSnapshot.getValue(FirstPrize.class);
                    setFirstPrize(firstPrize.getNumber());
                    Log.d("FireBaseFirstPrize", getFirstPrize().toString());
                } else {
                    Log.d("Lotto", "key not found on database");
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