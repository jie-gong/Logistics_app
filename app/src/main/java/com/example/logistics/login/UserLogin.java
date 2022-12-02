package com.example.logistics.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.logistics.R;
import com.example.logistics.http.OkHttpLo;
import com.example.logistics.http.OkHttpTo;
import com.example.logistics.util.SPUtil;

import org.json.JSONObject;

public class UserLogin extends AppCompatActivity {

    private TextView title1;
    private TextView tvGrzx;
    private EditText zh;
    private EditText mm;
    private Button clicked;
    private SPUtil spUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        initView();
        title1.setText("管理员登录");
        spUtil = new SPUtil();
        clicked.setOnClickListener(view -> {
            String trim = zh.getText().toString().trim();
            String trim1 = mm.getText().toString().trim();
            new OkHttpTo()
                    .setUrl("/admin/Login")
                    .setType("POST")
                    .setJsonObject("admin", trim)
                    .setJsonObject("password", trim1)
                    .setOkHttpLo(jsonObject -> {
                        if (jsonObject.optString("code").equals("200")) {
                            Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(this, Admin.class);
                            startActivity(intent);
                            spUtil.setSp(this, "admin");
                            spUtil.clear(this, "id");
                            finish();
                        } else {
                            Toast.makeText(this, "账号或密码输入错误", Toast.LENGTH_SHORT).show();
                            zh.setText("");
                            mm.setText("");
                        }
                    }).start();
        });
    }

    private void initView() {
        title1 = findViewById(R.id.title1);
        tvGrzx = findViewById(R.id.tv_grzx);
        zh = findViewById(R.id.zh);
        mm = findViewById(R.id.mm);
        clicked = findViewById(R.id.clicked);
    }
}