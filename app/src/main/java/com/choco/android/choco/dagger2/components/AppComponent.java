package com.choco.android.choco.dagger2.components;

import com.choco.android.choco.App;
import com.choco.android.choco.dagger2.modules.AppModule;
import com.choco.android.choco.dagger2.modules.CurrencyModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by win_user on 05/03/2018.
 */

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {

    CurrencyComponent plus(CurrencyModule module);
}
