package com.example.akshi.e_commerce;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
/**
 * Created by akshi on 02-08-2017.
 */

public class CustomWoman extends BaseAdapter {

    LayoutInflater layoutInflater;
    ArrayList<String> pName,pPrice;
    Context context;

    public CustomWoman(Context applicationContext, ArrayList<String> pName, ArrayList<String> pPrice) {

        this.pName = pName;
        this.pPrice = pPrice;
        this.context = applicationContext;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return pName.size();
    }

    @Override
    public Object getItem(int position) {
        return pName.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.activity_custom_woman,null);
        TextView txtProductName = (TextView)convertView.findViewById(R.id.txtProductName2);
        TextView txtProductPrice = (TextView)convertView.findViewById(R.id.txtProductPrice2);

        txtProductName.setText(pName.get(position));
        txtProductPrice.setText(pPrice.get(position));

        return convertView;
    }
}
