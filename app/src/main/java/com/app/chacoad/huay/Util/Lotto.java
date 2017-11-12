package com.app.chacoad.huay.Util;

import android.util.Log;

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

    static final String BASE_URL = "https://feeds.feedburner.com/";
    static final String regex = "\\d{6}";
    private int firstPrize;

    public void start() {
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
            Log.d("GUID", rss.getFirstArticle().getGuid());
            Log.d("pubDate", rss.getFirstArticle().getPubdate());
            Log.d("FormattedDate", rss.getFirstArticle().getFormattedPubdate());
            Log.d("Description", rss.getFirstArticle().getDescription());
            Log.d("FirstPrize", Integer.toString(this.getFirstPrize()));
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<RSSFeed> call, Throwable t) {
        t.printStackTrace();

    }

    public int getFirstPrize() {
        return firstPrize;
    }

    public void setFirstPrize(String description) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(description);
        if (m.find()) {
            this.firstPrize = Integer.parseInt(m.group(0));
        }
    }
}