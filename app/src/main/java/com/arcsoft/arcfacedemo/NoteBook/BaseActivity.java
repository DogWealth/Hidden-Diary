package com.example.notebook;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.arcsoft.arcfacedemo.R;

public abstract class BaseActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    static final String TAG = "tag";
    public final String ACTION = "ORDER_SWITCH";
    protected BroadcastReceiver receiver;
    protected IntentFilter filter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        setNightMode();

        filter = new IntentFilter();
        filter.addAction(ACTION);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "onReceive: needRefresh");
                needRefresh();
            }

        };

        registerReceiver(receiver, filter);

    }

    public boolean isUpdateOrder() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        return sharedPreferences.getBoolean("updateOrder", false);
    }

    public void setNightMode() {
        setTheme(R.style.DayTheme);

    }
    @Override
    protected void onDestroy() {

        super.onDestroy();
        unregisterReceiver(receiver);
    }

    protected abstract void needRefresh();
}