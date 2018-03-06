package com.choco.android.choco.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.choco.android.choco.R;
import com.choco.android.choco.models.Currency;

import java.util.List;

/**
 * Created by win_user on 06/03/2018.
 */

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.MyViewHolder> {

    private final List<Currency> currencies;

    public CurrencyAdapter(List<Currency> currencies){
        this.currencies = currencies;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.currency.setText(currencies.get(position).getCode());
        holder.cost.setText(currencies.get(position).getRate());
    }

    @Override
    public int getItemCount() {
        return currencies.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView currency;
        TextView cost;

        public MyViewHolder(View view) {
            super(view);
            currency=view.findViewById(R.id.currency_name);
            cost=view.findViewById(R.id.currency_cost);
        }
    }
}
