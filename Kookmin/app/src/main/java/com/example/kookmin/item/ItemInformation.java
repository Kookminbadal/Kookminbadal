package com.example.kookmin.item;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.example.kookmin.R;

/**
 * Created by 보운 on 2015-10-31.
 */
public class ItemInformation extends Activity {
    private TextView title;
    private TextView location;
    private TextView time;
    private TextView people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.information);

        title = (TextView) findViewById(R.id.nameinfo);
        location = (TextView) findViewById(R.id.locainfo);
        time = (TextView) findViewById(R.id.timeinfo);
        people = (TextView) findViewById(R.id.peopleinfo);

        init();
    }

    public void init() {
        Intent intent = getIntent();
        Item item = (Item) intent.getSerializableExtra("Item");

        title.setText(item.getTitle());
        location.setText(item.getLocation());
        String timestr = "";
        timestr += item.getHour() + " : ";
        timestr += item.getMinute();
        time.setText(timestr);
        people.setText(item.getPeople());

    }
}
