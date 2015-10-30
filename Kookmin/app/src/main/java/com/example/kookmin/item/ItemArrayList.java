package com.example.kookmin.item;

import java.util.ArrayList;

/**
 * Created by 보운 on 2015-10-30.
 */
public class ItemArrayList {
    private static final ArrayList<Item> items = new ArrayList<Item>();

    private ItemArrayList() {

    }

    public static ArrayList<Item> getInstance() {
        return items;
    }
}
