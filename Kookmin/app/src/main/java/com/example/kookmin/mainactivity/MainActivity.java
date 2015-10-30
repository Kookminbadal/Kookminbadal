package com.example.kookmin.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.kookmin.R;
import com.example.kookmin.item.AddItem;
import com.example.kookmin.item.Item;
import com.example.kookmin.item.ItemArrayList;
import com.example.kookmin.item.ItemList;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {
    private ArrayList<Item> itemList;
    private ListView listView;
    private ItemList listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemList = ItemArrayList.getInstance();

        findViewById(R.id.addbtn).setOnClickListener(onClick);

        updateListView();
    }

    public void updateListView() {
        listView = (ListView) findViewById(R.id.itemlist);
        listAdapter = new ItemList(this, R.layout.list_item, itemList);
        listView.setAdapter(listAdapter);
//        listView.setOnItemClickListener(listener);
    }

    Button.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.addbtn:
                    Intent addItemIntent = new Intent(MainActivity.this, AddItem.class);
                    startActivityForResult(addItemIntent, 0);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                Log.i("ArraySize ", "" + itemList.size());
                updateListView();
                break;
            default:
                break;
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
