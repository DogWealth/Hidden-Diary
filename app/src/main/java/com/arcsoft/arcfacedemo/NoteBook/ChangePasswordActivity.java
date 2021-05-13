package com.arcsoft.arcfacedemo.NoteBook;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arcsoft.arcfacedemo.R;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView text;
    private EditText edit;
    private Button btn;
    private String password;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_layout);

        text = findViewById(R.id.text);
        edit = findViewById(R.id.edit);
        btn = findViewById(R.id.buttonx);

        text.setOnClickListener(this);
        edit.setOnClickListener(this);
        btn.setOnClickListener(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        text.setText("当前密码："+sharedPreferences.getString("password", Context.ACCESSIBILITY_SERVICE));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonx:
                password = edit.getText().toString();
                text.setText("当前密码："+password);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("password",password);
                editor.commit();
                Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT).show();
        }
    }
}