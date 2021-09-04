package com.example.dherya_stock_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Stocks> {

    public ListAdapter(Context context, ArrayList<Stocks> stocksArrayList) {
        super(context, R.layout.custom_list,stocksArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View custom = layoutInflater.inflate(R.layout.custom_list, parent, false);

        TextView name = custom.findViewById(R.id.Name);
        TextView fullName = custom.findViewById(R.id.Full_Name);
        TextView stockPrice = custom.findViewById(R.id.Stock_Price);

        Stocks stocks = getItem(position);

        name.setText(stocks.Short_Name);
        fullName.setText(stocks.name);
        stockPrice.setText(stocks.price);



        return custom;
    }
}
