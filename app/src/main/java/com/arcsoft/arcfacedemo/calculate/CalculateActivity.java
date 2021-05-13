package com.arcsoft.arcfacedemo.calculate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arcsoft.arcfacedemo.NoteBook.MainActivity;
import com.arcsoft.arcfacedemo.R;

import org.w3c.dom.Text;


public class CalculateActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9;
    private TextView btn_add, btn_min, btn_m, btn_d, btn_p, btn_e, btn_c;
    private TextView output,fuhao;//显示输入数字和结果
    public int flag = 0; //判断加减乘除中哪一个标志位
    private String text1 = "0";
    private String pwd = "+2018211876+";
    private String password = "";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_calculate_layout);
        Toast.makeText(this, "这只是一个计算器", Toast.LENGTH_SHORT).show();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String p = sharedPreferences.getString("password", Context.ACCESSIBILITY_SERVICE);
        pwd = "+"+p+"+";


        btn_0 = findViewById(R.id.btn_0);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);
        btn_5 = findViewById(R.id.btn_5);
        btn_6 = findViewById(R.id.btn_6);
        btn_7 = findViewById(R.id.btn_7);
        btn_8 = findViewById(R.id.btn_8);
        btn_9 = findViewById(R.id.btn_9);
        btn_add = findViewById(R.id.btn_add);
        btn_min = findViewById(R.id.btn_min);
        btn_m = findViewById(R.id.btn_m);
        btn_d = findViewById(R.id.btn_d);
        btn_p = findViewById(R.id.btn_p);
        btn_e = findViewById(R.id.btn_e);
        btn_c = findViewById(R.id.btn_c);
        output = findViewById(R.id.output);
        fuhao = findViewById(R.id.history_text);

        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        btn_min.setOnClickListener(this);
        btn_m.setOnClickListener(this);
        btn_d.setOnClickListener(this);
        btn_p.setOnClickListener(this);
        btn_e.setOnClickListener(this);
        btn_c.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        String text2 = "0";
        switch (v.getId()){
            case R.id.btn_0:
                output.append("0");
                password = password+"0";
                break;
            case R.id.btn_1:
                output.append("1");
                password = password+"1";
                break;
            case R.id.btn_2:
                output.append("2");
                password = password+"2";
                break;
            case R.id.btn_3:
                output.append("3");
                password = password+"3";
                break;
            case R.id.btn_4:
                output.append("4");
                password = password+"4";
                break;
            case R.id.btn_5:
                output.append("5");
                password = password+"5";
                break;
            case R.id.btn_6:
                output.append("6");
                password = password+"6";
                break;
            case R.id.btn_7:
                output.append("7");
                password = password+"7";
                break;
            case R.id.btn_8:
                output.append("8");
                password = password+"8";
                break;
            case R.id.btn_9:
                output.append("9");
                password = password+"9";
                break;
            case R.id.btn_p:
                output.append(".");
                password = password+".";
                break;
            case R.id.btn_add:
                flag = 1;
                text1 = output.getText().toString();
                output.setText("");
                password = password+"+";
                fuhao.setText(password);
                break;
            case R.id.btn_min:
                flag = 2;
                text1 = output.getText().toString();
                output.setText("");
                password = password+"-";
                fuhao.setText(password);
                break;
            case R.id.btn_m:
                flag = 3;
                text1 = output.getText().toString();
                output.setText("");
                password = password+"x";
                fuhao.setText(password);
                break;
            case R.id.btn_d:
                flag = 4;
                text1 = output.getText().toString();
                output.setText("");
                password = password+"÷";
                fuhao.setText(password);
                break;
            case R.id.btn_e:
                fuhao.setText("=");
                if(password.equals("+0+")){
                    password="";
                }else if(password.equals(pwd)){
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                    password = "";
                }else {
                    try {
                        fuhao.setText(password+"=");
                        switch (flag) {
                            case 0:
                                // et_show.setText(text1);
                                output.setText("0");
                                break;
                            case 1:
                                text2 = output.getText().toString();
                                Double res = Double.parseDouble(text1)
                                        + Double.parseDouble(text2);
                                output.setText(res + "");
                                break;
                            case 2:
                                text2 = output.getText().toString();
                                Double res2 = (Double.parseDouble(text1) - Double
                                        .parseDouble(text2));
                                output.setText(res2 + "");
                                break;
                            case 3:
                                text2 = output.getText().toString();
                                Double res3 = Double.parseDouble(text1)
                                        * Double.parseDouble(text2);
                                output.setText(res3 + "");
                                break;
                            case 4:
                                text2 = output.getText().toString();
                                Double res4 = Double.parseDouble(text1)
                                        / Double.parseDouble(text2);
                                output.setText(res4 + "");
                                break;
                            default:
                                break;
                        }
                    } catch (Exception e) {
                        output.setText("Error");
                    }
                }
                break;
            case R.id.btn_c:
                password="";
                flag = 0;
                text1 = "0";
                text2 = "0";
                fuhao.setText("");
                output.setText("");
                break;
            default:
                break;
        }


    }
}