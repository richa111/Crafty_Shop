package com.example.akshi.e_commerce;

/**
 * Created by akshi on 02-08-2017.
 */

public class CartItems {
    private String pname;
    private int cost;
    private int quantity;

    public CartItems(String pname,int cost,int quantity)
    {
        this.pname = pname;
        this.cost = cost;
        this.quantity = quantity;
    }

    public CartItems() {

    }

    public void setPname(String pname)
    {
        this.pname = pname;
    }

    public String getPname()
    {
        return pname;
    }

    public void setCost(int cost)
    {
        this.cost = cost;
    }

    public int getCost()
    {
        return cost;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public int getQuantity()
    {
        return quantity;
    }
}

