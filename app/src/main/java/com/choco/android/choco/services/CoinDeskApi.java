package com.choco.android.choco.services;

import com.choco.android.choco.models.BitCoinPrice;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by win_user on 05/03/2018.
 */

public interface CoinDeskApi {

    @GET("/v1/bpi/currentprice.json")
    Single<BitCoinPrice> getBitCoinPrice();

    @GET("/v1/bpi/historical/close.json?for=yesterday")
    Single<BitCoinPrice> getHistory(@Query("currency") String curr);
}
