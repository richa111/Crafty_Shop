package com.example.akshi.e_commerce;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Vector;
/**
 * Created by akshi on 02-08-2017.
 */

public class ProductAdapter extends ArrayAdapter<ProductList> {
    private final Context context;
    Vector<ProductList> values;


    public ProductAdapter(Context context, Vector<ProductList> values) {
        super(context, R.layout.productlist, values);
        this.context = context;
        this.values = values;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.productlist, null, false);

        ProductList productList = values.get(position);

        ImageView imgView = (ImageView) rowView.findViewById(R.id.imageView);

        imgView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Toast.makeText(context, "image View clicked", Toast.LENGTH_SHORT).show();
            }
        });

        TextView ProductnameView = (TextView) rowView.findViewById(R.id.txtProductName);
        ProductnameView.setText(productList.getProductName());

        TextView ProductCostView = (TextView) rowView.findViewById(R.id.txtCost1);
        ProductCostView.setText(productList.getProductCost());

        Log.d("1234", "" + position);

        return rowView;
    }


}
