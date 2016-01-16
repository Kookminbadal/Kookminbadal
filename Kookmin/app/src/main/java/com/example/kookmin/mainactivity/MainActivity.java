package com.example.kookmin.mainactivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.kookmin.R;
import com.example.kookmin.item.Item;
import com.example.kookmin.item.ItemArrayList;
import com.example.kookmin.item.ItemList;
import com.example.kookmin.item.ListViewItem;
import com.example.kookmin.login.LoginForm;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private ArrayList<Item> itemList;
    private ListView listView;
    private ItemList listAdapter;
    private ImageButton boardImg;
    private ImageButton menuImg;
    private FragmentManager fragmentManager;

    //팝업관련
    View popupview;
    PopupWindow popup;
    FrameLayout li;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        itemList = ItemArrayList.getInstance();
        fragmentManager = getFragmentManager();

        boardImg = (ImageButton) findViewById(R.id.boardbtn);
        menuImg = (ImageButton) findViewById(R.id.menubtn);

        ImageButton logOut = (ImageButton) findViewById(R.id.logout);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginForm.class);
                startActivity(intent);
                finish();

            }
        });

        boardImg.setBackground(getDrawable(R.drawable.boardpushed));
        FragmentTransaction tr = fragmentManager.beginTransaction();
        FragmentBoard fi = new FragmentBoard();
        tr.add(R.id.frame, fi, "Board");
        tr.commit();

        popupview = View.inflate(MainActivity.this, R.layout.menupopup_layout, null);
        popup = new PopupWindow(popupview, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        popup.setOutsideTouchable(true);
        popup.setBackgroundDrawable(new BitmapDrawable());


    }

    public AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            FragmentTransaction tr = fragmentManager.beginTransaction();
            FragmentInformation information = new FragmentInformation(position);
            tr.replace(R.id.frame, information, "Information");
            tr.commit();
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
        private RelativeLayout relativeLayout;
        private ScrollView scrollView;
        private InputMethodManager imm;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.item_view, container, false);

            imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            scrollView = (ScrollView) root.findViewById(R.id.additemscroll);
            relativeLayout = (RelativeLayout) root.findViewById(R.id.additemview);
            titleEdt = (EditText) root.findViewById(R.id.titleedt);
            locaEdt = (EditText) root.findViewById(R.id.locationedt);
            peopleEdt = (EditText) root.findViewById(R.id.peopleedt);
            wordEdt = (EditText) root.findViewById(R.id.wordedt);

            root.findViewById(R.id.timebtn).setOnClickListener(onClick);
            root.findViewById(R.id.registration).setOnClickListener(onClick);

            scrollView.removeAllViews();
            scrollView.addView(relativeLayout);

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
                        imm.hideSoftInputFromWindow(peopleEdt.getWindowToken(), 0);

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
    class FragmentInformation extends Fragment {
        private TextView title;
        private TextView location;
        private TextView time;
        private TextView people;
        private TextView word;
        private int position;

        public FragmentInformation(int position) {
            this.position = position;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.information, container, false);

            title = (TextView) root.findViewById(R.id.nameinfo);
            location = (TextView) root.findViewById(R.id.locainfo);
            time = (TextView) root.findViewById(R.id.timeinfo);
            people = (TextView) root.findViewById(R.id.peopleinfo);
            word = (TextView) root.findViewById(R.id.wordinfo);

            root.findViewById(R.id.joinbtn).setOnClickListener(onClick);
            root.findViewById(R.id.commentbtn).setOnClickListener(onClick);

            title.setText(itemList.get(position).getTitle());
            location.setText(itemList.get(position).getLocation());
            String timestr = itemList.get(position).getHour() + " : ";
            timestr += itemList.get(position).getMinute();
            time.setText(timestr);
            String peoplestr = itemList.get(position).getJoin() + " / ";
            peoplestr += itemList.get(position).getPeople();
            people.setText(peoplestr);
            word.setText(itemList.get(position).getWord());

            return root;
        }

        Button.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.joinbtn:
                        if (itemList.get(position).getJoin() < Integer.valueOf(itemList.get(position).getPeople())) {
                            itemList.get(position).joinPlus();
                            String peoplestr = itemList.get(position).getJoin() + " / ";
                            peoplestr += itemList.get(position).getPeople();
                            people.setText(peoplestr);
                            word.setText(itemList.get(position).getWord());
                        } else {
                            Toast.makeText(MainActivity.this, "더 이상 참여할 수 없습니다.", Toast.LENGTH_SHORT).show();

                            FragmentTransaction tr1 = fragmentManager.beginTransaction();
                            FragmentBoard board = new FragmentBoard();
                            tr1.replace(R.id.frame, board, "Board");
                            tr1.commit();
                        }
                        break;
                    case R.id.commentbtn:
                        break;
                    default:
                        break;
                }
            }
        };
    }

    @SuppressLint("ValidFragment")
    class FragmentBoard extends Fragment {
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
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.main_layout, container, false);

            listView = (ListView) root.findViewById(R.id.menulistview);
            ArrayList<ListViewItem> data = new ArrayList<ListViewItem>();

            data.add(new ListViewItem(R.drawable.don1, R.drawable.don2, "DonCafe", "일식", "02-322-5160"));
            data.add(new ListViewItem(R.drawable.big1, R.drawable.big1, "The BIGBURGER", "양식", "02-919-6503"));
            data.add(new ListViewItem(R.drawable.kuk1, R.drawable.kuk1, "국수나무", "일식", "02-942-8884"));
            data.add(new ListViewItem(R.drawable.kim1, R.drawable.kim1, "김밥고을", "한식", "02-909-7171"));
            data.add(new ListViewItem(R.drawable.ma1, R.drawable.ma2, "마초떡볶이&치킨", "분식", "02-999-9910"));
            data.add(new ListViewItem(R.drawable.mat1, R.drawable.mat1, "맛사랑", "한식", "02-909-2303"));
            data.add(new ListViewItem(R.drawable.myung1, R.drawable.myung2, "명동돈까스", "일식", "02-917-3383"));
            data.add(new ListViewItem(R.drawable.mi1, R.drawable.mi1, "미쳐버린 파닭", "치킨", "02-942-8282"));
            data.add(new ListViewItem(R.drawable.bong1, R.drawable.bong1, "봉구스 밥버거", "한식", "02-941-7333"));
            data.add(new ListViewItem(R.drawable.sam1, R.drawable.sam1, "삼성원", "중식", "02-911-5020"));

            ListViewAdapter adapter = new ListViewAdapter(MainActivity.this, R.layout.item, data);
            listView.setAdapter(adapter);

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

    class ListViewAdapter extends BaseAdapter {


        private LayoutInflater inflater;
        private ArrayList<ListViewItem> data;
        private int layout;

        public ListViewAdapter(Context context, int layout, ArrayList<ListViewItem> data) {
            //  this.context = context;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.data = data;
            this.layout = layout;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public String getItem(int position) {
            return data.get(position).getName();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            if (convertView == null) {
                convertView = inflater.inflate(layout, parent, false);
            }
            ListViewItem listviewitem = data.get(position);
            ImageView icon1 = (ImageView) convertView.findViewById(R.id.imageview1);
            icon1.setImageResource(listviewitem.getIcon1());
            ImageView icon2 = (ImageView) convertView.findViewById(R.id.imageview2);
            icon2.setImageResource(listviewitem.getIcon2());

            TextView name = (TextView) convertView.findViewById(R.id.textview1);
            name.setText(listviewitem.getName());
            TextView kind = (TextView) convertView.findViewById(R.id.textview2);
            kind.setText(listviewitem.getKind());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    li = (FrameLayout) View.inflate(MainActivity.this, R.layout.menupopup_layout, null);
                    popup.showAtLocation(li, Gravity.CENTER, 0, 0);
                    ImageView bmImage = (ImageView) popupview.findViewById(R.id.img);
                    bmImage.setImageResource(data.get(pos).getIcon1());
                    TextView phone = (TextView) popupview.findViewById(R.id.phonetxt);
                    phone.setText(data.get(pos).getNum());
                }
            });

            return convertView;
        }

    }
}
