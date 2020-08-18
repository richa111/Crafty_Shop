package com.example.akshi.e_commerce;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by akshi on 02-08-2017.
 */

public class Man extends Fragment {
    ListView listView;
    ArrayList<String> pName,pPrice;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_man,null,false);
        listView = (ListView)view.findViewById(R.id.lstMan);

        pName = new ArrayList<String>();
        pPrice = new ArrayList<String>();

        pName.add("Levis");
        pPrice.add("1000");
        pName.add("Adidas");
        pPrice.add("1200");
        pName.add("Raymonds");
        pPrice.add("2000");

        pName.add("Lee Cooper");
        pPrice.add("2500");
        pName.add("Peter England");
        pPrice.add("3000");
        pName.add("John Players");
        pPrice.add("4000");

        pName.add("Van Huesen");
        pPrice.add("4000");
        pName.add("Allen Solley");
        pPrice.add("4200");
        pName.add("United Colors Of Benetton");
        pPrice.add("3500");

        pName.add("Van Huesen");
        pPrice.add("4000");
        pName.add("Allen Solley");
        pPrice.add("4200");
        pName.add("United Colors Of Benetton");
        pPrice.add("3500");


        pName.add("Van Huesen");
        pPrice.add("4000");
        pName.add("Allen Solley");
        pPrice.add("4200");
        pName.add("United Colors Of Benetton");
        pPrice.add("3500");

        pName.add("Van Huesen");
        pPrice.add("4000");
        pName.add("Allen Solley");
        pPrice.add("4200");
        pName.add("United Colors Of Benetton");
        pPrice.add("3500");
        pName.add("Van Huesen");
        pPrice.add("4000");
        pName.add("Allen Solley");
        pPrice.add("4200");
        pName.add("United Colors Of Benetton");
        pPrice.add("3500");

        CustomMan c = new CustomMan(getActivity().getApplicationContext(),pName,pPrice);
        listView.setAdapter((ListAdapter) c);


        return view;
    }
}


