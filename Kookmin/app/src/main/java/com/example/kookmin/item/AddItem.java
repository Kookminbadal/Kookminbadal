package com.example.kookmin.item;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.kookmin.R;

/**
 * Created by 보운 on 2015-10-30.
 */
public class AddItem extends Activity {
    private EditText titleEdt;
    private EditText locaEdt;
    private EditText peopleEdt;
    private int mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_view);

        titleEdt = (EditText) findViewById(R.id.titleedt);
        locaEdt = (EditText) findViewById(R.id.locationedt);
        peopleEdt = (EditText) findViewById(R.id.peopleedt);

        findViewById(R.id.timebtn).setOnClickListener(onClick);
        findViewById(R.id.registration).setOnClickListener(onClick);
    }

    Button.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.registration:
                    Item item = new Item();
                    item.setTitle(titleEdt.getText().toString());
                    item.setLocation(locaEdt.getText().toString());
                    item.setHour(String.valueOf(mHour));
                    item.setMinute(String.valueOf(mMinute));
                    item.setPeople(peopleEdt.getText().toString());
                    ItemArrayList.getInstance().add(item);
                    setResult(RESULT_OK);
                    finish();
                    break;
                case R.id.timebtn:
                    new TimePickerDialog(AddItem.this, mTimeSetListener, mHour, mMinute, false).show();
                    break;
                default:
                    break;
            }
        }
    };

    TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mHour = hourOfDay;
            mMinute = minute;
        }
    };
}
