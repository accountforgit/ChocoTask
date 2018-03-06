package com.choco.android.choco.dagger2.components;

import com.choco.android.choco.dagger2.modules.CurrencyModule;
import com.choco.android.choco.dagger2.scopes.FragmentScope;
import com.choco.android.choco.fragments.CurrencyFragment;
import com.choco.android.choco.fragments.HistoryFragment;

import dagger.Subcomponent;

/**
 * Created by win_user on 05/03/2018.
 */

@Subcomponent(modules = {CurrencyModule.class})
@FragmentScope
public interface CurrencyComponent {
    void inject(CurrencyFragment fragment);
    void inject(HistoryFragment fragment);
}
