package com.arcsoft.arcfacedemo.NoteBook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.arcsoft.arcfacedemo.R;


import com.arcsoft.arcfacedemo.activity.FaceManageActivity;

public class SettingActivity extends com.example.notebook.BaseActivity {

    private Switch timeOrder;
    private SharedPreferences sharedPreferences;//存储变量
//    private Boolean order_change;//判断是否改变排序方式
    private static final String TAG = "USER";
    private TextView password;
    private TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference_layout);


        password = findViewById(R.id.password);
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, ChangePasswordActivity.class));
                finish();
            }
        });
        password = findViewById(R.id.control_register);
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, FaceManageActivity.class));
                finish();
            }
        });

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        initView();

    }

    @Override
    protected void needRefresh() {

    }

    public void initView(){
        timeOrder = findViewById(R.id.timeOrder);
        timeOrder.setChecked(sharedPreferences.getBoolean("updateOrder", false));
        timeOrder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setOrderPref(isChecked);
            }
        });
    }
    private void setOrderPref(boolean update){
        //通过order switch修改pref中的timeOrder
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("updateOrder", update);
        editor.commit();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            Intent intent = new Intent();
            intent.setAction("ORDER_SWITCH");
            sendBroadcast(intent);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


}