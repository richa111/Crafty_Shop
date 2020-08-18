package com.example.akshi.e_commerce;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.Vector;
/**
 * Created by akshi on 02-08-2017.
 */

public class Cart extends Fragment {

    Button btnPay;
    Fragment fragment;
static String pname;
static int cost,quantity;
        CartItems cartItems;
        Vector<CartItems> clist;
        ListView listView;
        DBHandler dbh;

        View view;

public Cart(){};

public Cart(String pname, int cost) {
        this.pname = pname;
        this.cost = cost;

        }

@Nullable
@Override
public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cart,null,false);

        Vector<CartItems> cartItemses = new Vector<>();
        listView = (ListView)view.findViewById(R.id.product_listView);
        clist = dbh.getCart();

final CartAdapter adapter = new CartAdapter(getActivity(),clist);
        listView.setAdapter(adapter);



        btnPay = (Button)view.findViewById(R.id.btnPay);


        btnPay.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {


        fragment = new payment();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.fragmentContainer,fragment);
        ft.addToBackStack("cc");
        ft.commit();


        }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
@Override
public boolean onKey(View v, int keyCode, KeyEvent event) {
        Log.i(getTag(), "keyCode: " + keyCode);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        Log.i(getTag(), "onKey Back listener is working!!!");
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        // String cameback="CameBack";
        Intent i = new Intent(getActivity(), MainActivity.class);
        // i.putExtra("Comingback", cameback);
        startActivity(i);
        return true;
        } else {
        return false;
        }
        }
        });

        return view;
        }
        }

