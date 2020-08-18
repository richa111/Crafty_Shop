package com.example.akshi.e_commerce;

/**
 * Created by akshi on 02-08-2017.
 */

public class Lidata {
    private String image;
    private String name;
    private String price;

    public Lidata() {}

    public Lidata(String image,String name,String price) {
        this.image = image;
        this.name = name;
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}



