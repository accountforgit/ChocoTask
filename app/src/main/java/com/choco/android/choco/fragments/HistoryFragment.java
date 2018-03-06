package com.choco.android.choco.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.choco.android.choco.App;
import com.choco.android.choco.R;
import com.choco.android.choco.adapters.CurrencyAdapter;
import com.choco.android.choco.models.BitCoinPrice;
import com.choco.android.choco.models.Currency;
import com.choco.android.choco.services.CoinDeskApi;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    @Inject
    CoinDeskApi coinDeskApi;

    @Inject
    Context context;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    KProgressHUD dialog;

    List<Currency> currencies=new ArrayList<>();

    String[] request_curr=new String[]{"USD","GBP","EUR"};

    CompositeDisposable disposables = new CompositeDisposable();
    private CurrencyAdapter currencyAdapter;

    public HistoryFragment() {
        App.getApp().plusCurrencyComponent().inject(this);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_currency, container, false);
        ButterKnife.bind(this, view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        currencyAdapter = new CurrencyAdapter(currencies);
        recyclerView.setAdapter(currencyAdapter);
        initDialog();
        return view;

    }
    private void initDialog() {
        dialog=KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        dialog.show();
        disposables.add(coinDeskApi.getHistory(request_curr[0])
                .zipWith(coinDeskApi.getHistory(request_curr[1]), new BiFunction<BitCoinPrice, BitCoinPrice, List<BitCoinPrice>>() {
                    @Override
                    public List<BitCoinPrice> apply(BitCoinPrice bitCoinPrice, BitCoinPrice bitCoinPrice2) throws Exception {
                        List<BitCoinPrice> list=new ArrayList<>();
                        list.add(bitCoinPrice);
                        list.add(bitCoinPrice2);
                        return list;
                    }
                }).zipWith(coinDeskApi.getHistory(request_curr[2]), new BiFunction<List<BitCoinPrice>, BitCoinPrice, List<BitCoinPrice>>() {
                    @Override
                    public List<BitCoinPrice> apply(List<BitCoinPrice> bitCoinPrices, BitCoinPrice bitCoinPrice) throws Exception {
                        bitCoinPrices.add(bitCoinPrice);
                        return bitCoinPrices;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<BitCoinPrice>>(){

                    @Override
                    public void onSuccess(List<BitCoinPrice> bitCoinPrice) {
                        currencies.clear();
                        for (int i = 0; i < bitCoinPrice.size(); i++) {
                            Currency currency = new Currency();
                            currency.setCode(request_curr[i]);
                            currency.setRate(bitCoinPrice.get(i).getBpi().getYesterday()+"");
                            currencies.add(currency);
                        }
                        currencyAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(getActivity()!=null){
                            Toast.makeText(getActivity(), "Network error.", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.dispose();
        App.getApp().destroyCurrencyComponent();
    }

}
