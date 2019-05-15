package com.example.morri.messingaround;

public class Item {


    private String Name;
    private String Descritption;
    private int Thumbnail;

    public Item() {
    }

    public Item(String name, String descritption, int thumbnail) {
        Name = name;
        Descritption = descritption;
        Thumbnail = thumbnail;
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
}
