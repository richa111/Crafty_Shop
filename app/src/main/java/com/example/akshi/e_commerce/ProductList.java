package com.example.akshi.e_commerce;

/**
 * Created by akshi on 02-08-2017.
 */

public class ProductList {

    private String productName,productCost;

    public ProductList(String productName, String productCost)
    {
        this.productName = productName;
        this.productCost = productCost;
    }

    public ProductList() {

    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getProductName()
    {
        return this.productName;
    }


    public void setProductCost(String productCost)
    {
        this.productCost = productCost;
    }

    public String getProductCost()
    {
        return this.productCost;
    }
}

