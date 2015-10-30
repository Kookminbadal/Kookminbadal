package com.example.kookmin.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kookmin.R;
import com.example.kookmin.item.Item;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by 보운 on 2015-10-30.
 */
public class ItemList extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<Item> itemList;
    private int layout;

    public ItemList() {
        this.layout = 0;
        this.itemList = null;
        this.inflater = null;
    }

    public ItemList(Context context, int layout, ArrayList<Item> itemList) {
        this.layout = layout;
        this.itemList = itemList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position).getTitle();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.itemname);
        name.setText(itemList.get(position).getTitle());

        TextView location = (TextView) convertView.findViewById(R.id.locatext);
        location.setText(itemList.get(position).getLocation());

        TextView time = (TextView) convertView.findViewById(R.id.timetext);
        String timestr = "";
        timestr += itemList.get(position).getHour() + " : ";
        timestr += itemList.get(position).getMinute();
        time.setText(timestr);

        TextView people = (TextView) convertView.findViewById(R.id.peopletext);
        people.setText(itemList.get(position).getPeople() + "명");

        TextView word = (TextView) convertView.findViewById(R.id.word);
        word.setText(itemList.get(position).getWord());

        return convertView;
    }
}
