package com.choco.android.choco.dagger2.modules;

import com.choco.android.choco.dagger2.scopes.FragmentScope;
import com.choco.android.choco.services.CoinDeskApi;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by win_user on 05/03/2018.
 */

@Module
public class CurrencyModule {

    @Provides
    @FragmentScope
    @Nonnull
    CoinDeskApi provideApi(Retrofit retrofit){
        return retrofit.create(CoinDeskApi.class);
    }
}
