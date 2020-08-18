package com.example.akshi.e_commerce;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * Created by akshi on 02-08-2017.
 */

public class ProductDetail extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_product_category,container,false);

        return view;
    }

    public void setArguments(Bundle bundle) {
    }
}
