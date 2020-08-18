package com.example.akshi.e_commerce;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Vector;

/**
 * Created by akshi on 02-08-2017.
 */

public class CartAdapter extends ArrayAdapter<CartItems> {

private final Context context;

        Vector<CartItems> values;

public CartAdapter(Context context,Vector<CartItems> values)
        {
        super(context,R.layout.cartlist,values);
        this.context = context;
        this.values = values;
        }

public View getView(int position, View convertView, ViewGroup parent)
        {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.cartlist,null,false);

        CartItems cartItems = values.get(position);

        TextView txtProdName = (TextView)view.findViewById(R.id.txtProdName);
        TextView txtProdCost = (TextView)view.findViewById(R.id.txtProdCost);
        TextView txtProdQuantity = (TextView)view.findViewById(R.id.txtProdQuantity);

        txtProdName.setText(cartItems.getPname());
        txtProdCost.setText(cartItems.getCost());
        txtProdQuantity.setText(cartItems.getQuantity());

        Log.d("1234",""+position);

        return view;
        }
        }
