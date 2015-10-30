package com.example.kookmin.mainactivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TimePicker;

import com.example.kookmin.R;
import com.example.kookmin.item.AddItem;
import com.example.kookmin.item.Item;
import com.example.kookmin.item.ItemArrayList;
import com.example.kookmin.item.ItemInformation;
import com.example.kookmin.item.ItemList;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private ArrayList<Item> itemList;
    private ListView listView;
    private ItemList listAdapter;
    private ImageButton boardImg;
    private ImageButton menuImg;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        itemList = ItemArrayList.getInstance();
        fragmentManager = getFragmentManager();

        boardImg = (ImageButton) findViewById(R.id.boardbtn);
        menuImg = (ImageButton) findViewById(R.id.menubtn);

        boardImg.setBackground(getDrawable(R.drawable.boardpushed));
        FragmentTransaction tr = fragmentManager.beginTransaction();
        FragmentBoard fi = new FragmentBoard();
        tr.add(R.id.frame, fi, "Board");
        tr.commit();
    }

    public AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent info = new Intent(MainActivity.this, ItemInformation.class);
            Item item = itemList.get(position);
            info.putExtra("Item", item);
            startActivity(info);
        }
    };

    public void fragmentMenuClick(View v) {
        switch (v.getId()) {
            case R.id.boardbtn:
                boardImg.setBackground(getDrawable(R.drawable.boardpushed));
                menuImg.setBackground(getDrawable(R.drawable.menu));

                FragmentTransaction tr1 = fragmentManager.beginTransaction();
                FragmentBoard board = new FragmentBoard();
                tr1.replace(R.id.frame, board, "Board");
                tr1.commit();
                break;
            case R.id.menubtn:
                boardImg.setBackground(getDrawable(R.drawable.board));
                menuImg.setBackground(getDrawable(R.drawable.menupushed));

                FragmentTransaction tr2 = fragmentManager.beginTransaction();
                FragmentMenu menu = new FragmentMenu();
                tr2.replace(R.id.frame, menu, "Menu");
                tr2.commit();
                break;
            case R.id.addbtn:
                boardImg.setBackground(getDrawable(R.drawable.boardpushed));
                menuImg.setBackground(getDrawable(R.drawable.menu));

                FragmentTransaction tr3 = fragmentManager.beginTransaction();
                FragmentAddItem addItem = new FragmentAddItem();
                tr3.replace(R.id.frame, addItem, "AddItem");
                tr3.commit();
                break;
            default:
                break;
        }
    }

    @SuppressLint("ValidFragment")
    class FragmentAddItem extends Fragment {
        private EditText titleEdt;
        private EditText locaEdt;
        private EditText peopleEdt;
        private EditText wordEdt;
        private int mHour, mMinute;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.item_view, container, false);

            titleEdt = (EditText) root.findViewById(R.id.titleedt);
            locaEdt = (EditText) root.findViewById(R.id.locationedt);
            peopleEdt = (EditText) root.findViewById(R.id.peopleedt);
            wordEdt = (EditText) root.findViewById(R.id.wordedt);

            root.findViewById(R.id.timebtn).setOnClickListener(onClick);
            root.findViewById(R.id.registration).setOnClickListener(onClick);

            return root;
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
                        item.setWord(wordEdt.getText().toString());
                        ItemArrayList.getInstance().add(item);

                        FragmentTransaction tr1 = fragmentManager.beginTransaction();
                        FragmentBoard board = new FragmentBoard();
                        tr1.replace(R.id.frame, board, "Board");
                        tr1.commit();
                        break;
                    case R.id.timebtn:
                        new TimePickerDialog(MainActivity.this, mTimeSetListener, mHour, mMinute, false).show();
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

    @SuppressLint("ValidFragment")
    class FragmentBoard extends Fragment {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.board, container, false);

            listView = (ListView) root.findViewById(R.id.itemlist);
            listAdapter = new ItemList(MainActivity.this, R.layout.list_item, itemList);
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(listener);

            return root;
        }
    }

    @SuppressLint("ValidFragment")
    class FragmentMenu extends Fragment {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.menu, container, false);
            return root;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
