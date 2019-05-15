package com.example.morri.messingaround;

import java.lang.reflect.Array;
import java.util.Arrays;

public class contact_struct extends Object{
    String name;
    String number;

    public contact_struct(String fname, String number) {
        name = fname;
        number = number;
    }

    public contact_struct contact_struct(String n, String num){
        String name = n;
        String number = num;
        return this;
    }

}
