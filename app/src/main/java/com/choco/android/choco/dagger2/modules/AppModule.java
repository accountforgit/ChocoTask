package com.choco.android.choco.dagger2.modules;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.choco.android.choco.R;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by win_user on 05/03/2018.
 */

@Module
public class AppModule {

    private Context context;

    public AppModule(@NonNull Context context){
        this.context=context;
    }

    @Provides
    @Singleton
    Context provideContext(){
        return context;
    }

    @Provides
    @Singleton
    OkHttpClient provide(){
        return new OkHttpClient().newBuilder().build();
    }

    @Provides
    @Singleton
    Gson provideGson(){
        return new GsonBuilder().serializeNulls()
                .setFieldNamingStrategy(new FieldNamingStrategy() {
                    @Override
                    public String translateName(Field f) {
                        if(f.getName().startsWith("yesterday")){
                            Calendar calendar = Calendar.getInstance();
                            calendar.add(Calendar.DATE,-1);
                            int month = calendar.get(Calendar.MONTH) + 1;
                            int day = calendar.get(Calendar.DATE);
                            int year = calendar.get(Calendar.YEAR);
                            Log.d("tag", "translateName: "+year
                                    +"-"+month+"-"+day);
                            return year+"-"+f(month)+"-"+f(day);
                        }
                        return f.getName();
                    }
                    public String f(int a){
                        if(a<10)
                            return "0"+a;
                        return a+"";
                    }
                })
                .create();
    }


    @Provides
    @Singleton
    @Nonnull
    Retrofit provideRetrofit(OkHttpClient okHttpClient,Gson gson){
        return new Retrofit.Builder()
                .baseUrl("https://api.coindesk.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

}
