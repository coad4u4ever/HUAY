package com.app.chacoad.huay.Util;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by N on 11/12/2017.
 */

public interface LottoAPI {
    @GET("Huay")
    Call<RSSFeed> loadRSSFeed();
}
