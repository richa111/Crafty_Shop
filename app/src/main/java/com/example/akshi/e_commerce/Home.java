package com.example.akshi.e_commerce;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by akshi on 02-08-2017.
 */

public class Home extends Fragment {
//    StringBuilder s;

    String url = "http://clothesaccessories.in/webservice/img/";
    //        RecyclerView list;
//    RecyclerAdater adapter;
    //ArrayList<Lidata> arr;
    RecyclerView listView;
    ArrayList<Lidata> pList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_home,null,false);

        listView = (RecyclerView) view.findViewById(R.id.lstHome);

        pList = new ArrayList<>();
        pList.add(new Lidata(url+"formalshirts4.jpg", "Adidas", "4000"));
        pList.add(new Lidata(url+"formalshirts3.jpg", "Levis", "2000"));
        pList.add(new Lidata(url+"formalshirts2.jpg", "Raymonds", "4000"));
        pList.add(new Lidata(url+"formalshirts1.jpg", "Lee Cooper", "5000"));
        pList.add(new Lidata(url+"formalshirts5.jpg", "Peter England", "2500"));
        pList.add(new Lidata(url+"formalshirts6.jpg", "John  Players", "2800"));
        pList.add(new Lidata(url+"womensaree1.jpg"  , "Banaras Brocade", "4000"));
        pList.add(new Lidata(url+"womensaree3.jpg"  , "Banaras Brocade", "4000"));
        pList.add(new Lidata(url+"womensaree2.jpg", "Banaras Brocade", "4000"));
        pList.add(new Lidata(url+"womensaree4.jpg"  , "Banaras Brocade", "4000"));
        pList.add(new Lidata(url+"womentshirt1.jpg", "Adidas", "3000"));
        pList.add(new Lidata(url+"womentshirt2.jpg", "Levis", "3000"));
        pList.add(new Lidata(url+"womenwestern1.jpg", "Adidas", "3000"));
        pList.add(new Lidata(url+"womenwestern2.jpg", "Levis", "3000"));
        pList.add(new Lidata(url+"womenwestern3.jpg", "Peter England", "3000"));
        pList.add(new Lidata(url+"womenwestern4.jpg", "Adidas", "3000"));
        pList.add(new Lidata(url+"womenwestern5.jpg", "Adidas", "3000"));
        pList.add(new Lidata(url+"tr1.jpg", "Raymonds", "3000"));
        pList.add(new Lidata(url+"tr2.jpg", "Peter England", "3000"));
        pList.add(new Lidata(url+"tr3.jpg", "John Players", "3000"));
        pList.add(new Lidata(url+"tr4.jpg", "Peter England", "4000"));
        pList.add(new Lidata(url+"tr5.jpg", "John Players", "4000"));
        pList.add(new Lidata(url+"tr6.jpg", "Peter England", "4000"));
        pList.add(new Lidata(url+"tr7.jpg", "Lee Cooper", "4000"));
        pList.add(new Lidata(url+"tr8.jpg", "Levis", "4000"));
        pList.add(new Lidata(url+"tr9.jpg", "United Colors Of Benetton", "4400"));
        pList.add(new Lidata(url+"tr10.jpg", "Lee Cooper", "5000"));
        pList.add(new Lidata(url+"shirts.jpg", "Lee Cooper", "3500"));
        pList.add(new Lidata(url+"custom.jpg", "Lee Cooper", "3500"));
        pList.add(new Lidata(url+"collection.jpg", "Lee Cooper", "3500"));
        pList.add(new Lidata(url+"sh.jpg", "Lee Cooper", "3000"));
        pList.add(new Lidata(url+"formalshirts4.jpg", "Lee Cooper", "3000"));
        pList.add(new Lidata(url+"womensaree3.jpg", "Banaras Brocade", "3500"));


        CustomHome c = new CustomHome(getActivity().getApplicationContext(),pList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        listView.setLayoutManager(mLayoutManager);
        listView.setAdapter(c);


        return view;
    }
}
