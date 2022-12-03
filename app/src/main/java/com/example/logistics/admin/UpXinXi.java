package com.example.logistics.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.logistics.R;
import com.example.logistics.http.OkHttpTo;

public class UpXinXi extends AppCompatActivity {
    private Integer a;
    private TextView title1;
    private TextView tvGrzx;
    private LinearLayout line1;
    private TextView yundanh;
    private EditText edit2;
    private Button but1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_xin_xi);
        initView();
        a = (Integer) getIntent().getSerializableExtra("id");
        tvGrzx.setText("返回上一级");
        yundanh.setText(a.toString());
        but1.setOnClickListener(view -> {
            getOkHttp();
        });
    }

    private void getOkHttp() {
        new OkHttpTo()
                .setUrl("/admin/stepList")
                .setJsonObject("orderNumber", a)
                .setJsonObject("AddressAt", edit2.getText().toString().trim())
                .setType("POST")
                .setOkHttpLo(jsonObject -> {
                    if (jsonObject.optString("code").equals("200")) {
                        finish();
                        Toast.makeText(this, "操作成功", Toast.LENGTH_SHORT).show();
                    }
                }).start();
    }

    private void initView() {
        title1 = findViewById(R.id.title1);
        tvGrzx = findViewById(R.id.tv_grzx);
        line1 = findViewById(R.id.line1);
        yundanh = findViewById(R.id.yundanh);
        edit2 = findViewById(R.id.edit2);
        but1 = findViewById(R.id.but1);
    }
}