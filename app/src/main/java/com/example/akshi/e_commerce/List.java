package com.example.akshi.e_commerce;

/**
 * Created by akshi on 02-08-2017.
 */

public class List {
    private String title;
    private String Quantity;
    private String price;


    public List(String title, String quantity, String price ){
        this.title = title;
        this.Quantity = quantity;
        this.price=price;

    }

    public void setName(String name){
        this.title = title;
    }

    public String getName(){
        return this.title;
    }

    public void setQuantity(String phone){
        this.Quantity = Quantity;
    }

    public String getQuantity(){
        return this.Quantity;
    }


    public void setPrice(String name){
        this.price= price;
    }
    public String getPrice(){
        return this.price;
    }





}

