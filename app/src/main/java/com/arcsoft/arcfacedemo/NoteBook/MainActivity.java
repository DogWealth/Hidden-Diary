package com.arcsoft.arcfacedemo.NoteBook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

//import androidx.appcompat.widget.Toolbar;
//
//import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.arcsoft.arcfacedemo.R;
import com.arcsoft.arcfacedemo.activity.InitRegisterAndRecognizeActivity;
import com.arcsoft.arcfacedemo.calculate.CalculateActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends com.example.notebook.BaseActivity implements AdapterView.OnItemClickListener {

    private NoteDatabase dbHelper;

    private Context context = this;
    final String TAG = "MAIN";
    FloatingActionButton btn;
    TextView tv;
    private ListView lv;

    private NoteAdapter adapter;
    private List<Note> noteList = new ArrayList<Note>();
    private Toolbar myToolbar;
    //弹出菜单
    private PopupWindow popupWindow;
    private PopupWindow popupCover;
    private ViewGroup customView;
    private ViewGroup coverView;
    private LayoutInflater layoutInflater;
    private RelativeLayout main;
    private WindowManager wm;
    private DisplayMetrics metrics;
    private TextView setting_text;
    private TextView computer;
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.fab);
        // tv = findViewById(R.id.tv);
        lv = findViewById(R.id.lv);
        myToolbar = findViewById(R.id.mToolbar);
        adapter = new NoteAdapter(getApplicationContext(), noteList);
        refreshListView();
        lv.setAdapter(adapter);

        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //设置toolbar取代actionBar

        initPopUpView();
        myToolbar.setNavigationIcon(R.drawable.base_menu);//set menu
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUpView();
            }
        });

        lv.setOnItemClickListener(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log.d(TAG, "onClick: click");
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("mode", 4);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void needRefresh() {
        refreshListView();

    }

    //接收startActivtyForResult的结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        int returnMode;
        long note_Id;
        returnMode = data.getExtras().getInt("mode", -1);
        note_Id = data.getExtras().getLong("id", 0);

        if (returnMode == 1) {  //update current note

            String content = data.getExtras().getString("content");
            String time = data.getExtras().getString("time");
            int tag = data.getExtras().getInt("tag", 1);

            Note newNote = new Note(content, time, tag);
            newNote.setId(note_Id);
            CRUD op = new CRUD(context);
            op.open();
            op.updateNote(newNote);
            op.close();
        } else if (returnMode == 0) {  // create new note
            String content = data.getExtras().getString("content");
            String time = data.getExtras().getString("time");
            int tag = data.getExtras().getInt("tag", 1);

            Note newNote = new Note(content, time, time,tag);//新笔记有创建时间
            CRUD op = new CRUD(context);
            op.open();
            op.addNote(newNote);
            op.close();
        }else if (returnMode == 2) { // delete
            Note curNote = new Note();
            curNote.setId(note_Id);
            CRUD op = new CRUD(context);
            op.open();
            op.removeNote(curNote);
            op.close();
        }else{

        }
        refreshListView();
        super.onActivityResult(requestCode, resultCode, data);
        /*String content = data.getStringExtra("content");
        String time = data.getStringExtra("time");
        Note note = new Note(content, time, 1);
        CRUD op = new CRUD(context);
        op.open();
        op.addNote(note);
        op.close();
        refreshListView();*/

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);

        //search
        MenuItem mSearch = menu.findItem(R.id.search_button);
        SearchView mSearchView = (SearchView) mSearch.getActionView();

        mSearchView.setQueryHint("搜索日记/便签");

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
//刷新笔记界面
    public void refreshListView(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean update = sharedPreferences.getBoolean("updateOrder",false);

        CRUD op = new CRUD(context);
        op.open();
        // set adapter
        if (noteList.size() > 0) noteList.clear();
        noteList.addAll(op.getAllNotes(update));
        op.close();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.lv:
                Note curNote = (Note) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("content", curNote.getContent());
                intent.putExtra("id", curNote.getId());
                intent.putExtra("time", curNote.getTime());
                intent.putExtra("mode", 3);     // MODE of 'click to edit'
                intent.putExtra("tag", curNote.getTag());
                startActivityForResult(intent, 1);      //collect data from edit
                Log.d(TAG, "onItemClick: " + position);
                break;
        }
    }

    public void  initPopUpView(){
        layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customView = (ViewGroup) layoutInflater.inflate(R.layout.setting_layout,null);
        coverView = (ViewGroup) layoutInflater.inflate(R.layout.setting_cover,null);

        main = findViewById(R.id.main_layout);
        wm = getWindowManager();
        metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
    }

    public void showPopUpView(){
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        popupCover = new PopupWindow(coverView,width,height,false);
        popupWindow = new PopupWindow(customView,(int)(width * 0.7), height);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        //在主界面加载成功之后显示弹出
        findViewById(R.id.main_layout).post(new Runnable() {
            @Override
            public void run() {
                popupCover.showAtLocation(main, Gravity.NO_GRAVITY,0,0);
                popupWindow.showAtLocation(main, Gravity.NO_GRAVITY,0,0);

                computer = customView.findViewById(R.id.computer);
                setting_text = customView.findViewById(R.id.setting_setting_text);
                register = customView.findViewById(R.id.register);

                computer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, CalculateActivity.class));
                        finish();
                    }
                });

                setting_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                    }
                });

                register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, InitRegisterAndRecognizeActivity.class));
                    }
                });

                coverView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });

                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        popupCover.dismiss();
                    }
                });
            }
        });
    }


}

