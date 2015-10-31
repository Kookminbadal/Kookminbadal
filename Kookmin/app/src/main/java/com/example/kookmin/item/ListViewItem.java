package com.example.kookmin.item;

/**
 * Created by Hyung-Jun on 2015-10-31.
 */
public class ListViewItem {
    private int icon1;
    private int icon2;
    private String name;
    private String kind;
    private String num;

    public int getIcon1() {
        return icon1;
    }

    public int getIcon2() {
        return icon2;
    }

    public String getName() {
        return name;
    }

    public String getKind() {
        return kind;
    }

    public String getNum() {
        return num;
    }

    public ListViewItem(int icon1, int icon2, String name, String kind, String num) {
        this.icon1 = icon1;
        this.icon2 = icon2;
        this.name = name;
        this.kind = kind;
        this.num = num;
    }
}