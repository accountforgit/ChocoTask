package com.choco.android.choco;

import android.app.Application;

import com.choco.android.choco.dagger2.components.AppComponent;
import com.choco.android.choco.dagger2.components.CurrencyComponent;
import com.choco.android.choco.dagger2.components.DaggerAppComponent;
import com.choco.android.choco.dagger2.modules.AppModule;
import com.choco.android.choco.dagger2.modules.CurrencyModule;

/**
 * Created by win_user on 05/03/2018.
 */

public class App extends Application {

    private static App app;

    private AppComponent appComponent;
    private CurrencyComponent currencyComponent;

    public static App getApp(){
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(app))
                .build();
    }

    public CurrencyComponent plusCurrencyComponent(){
        if(currencyComponent==null)
            currencyComponent=appComponent.plus(new CurrencyModule());
        return currencyComponent;
    }

    public void destroyCurrencyComponent(){
        currencyComponent=null;
    }
}
