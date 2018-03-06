package com.choco.android.choco;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.choco.android.choco.fragments.CurrencyFragment;
import com.choco.android.choco.fragments.HistoryFragment;

public class NavActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.today:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content,new CurrencyFragment(),"currency")
                            .commit();
                    return true;
                case R.id.history:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content,new HistoryFragment(),"currency")
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if(getSupportFragmentManager().findFragmentByTag("currency")==null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content,new CurrencyFragment(),"currency")
                    .commit();
        }
    }

}
