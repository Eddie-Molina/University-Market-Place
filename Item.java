package com.example.morri.messingaround;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class Item {


    private String Name;
    private String Descritption;
    private int Thumbnail;
    private int Price;
   // Button message_button;
    //String seller = "Greg Oldman";
    //public Item() {
    //}

    public Item(String name, String descritption, int thumbnail, int price) {
        Name = name;
        Descritption = descritption;
        Thumbnail = thumbnail;
        Price = price;


    }

    public int getPrice() {
        return Price;
    }


    public String getName() {
        return Name;

    }

    public String getDescritption() {
        return Descritption;
    }

    public int getThumbnail() {
        return Thumbnail;
    }


    public void setName(String name) {
        Name = name;
    }

    public void setDescritption(String descritption) {
        Descritption = descritption;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }

    public void setPrice(int price) {
        Price = price;
    }
}
