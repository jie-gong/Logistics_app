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

public class DDXG extends AppCompatActivity {
    private Integer a;
    private TextView title1;
    private TextView tvGrzx;
    private LinearLayout line1;
    private TextView yundanh;
    private EditText edit1;
    private EditText edit2;
    private Button but1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ddxg);
        initView();
        a = (Integer) getIntent().getSerializableExtra("id");
        tvGrzx.setText("返回上一级");
        tvGrzx.setOnClickListener(view -> {
            Intent intent = new Intent(this, TimeUpDd.class);
            finish();
            startActivity(intent);
        });
        title1.setText("订单修改");
        yundanh.setText(a.toString());
        but1.setOnClickListener(view -> {
            getOkHttp();
        });

    }

    private void getOkHttp() {
        new OkHttpTo()
                .setUrl("/admin/updateById")
                .setJsonObject("orderNumber", a)
                .setJsonObject("phone", edit2.getText().toString().trim())
                .setJsonObject("site", edit1.getText().toString().trim())
                .setType("POST")
                .setOkHttpLo(jsonObject -> {
                    if (jsonObject.optString("code").equals("200")) {
                        new OkHttpTo()
                                .setUrl("/admin/updateByDel")
                                .setType("POST")
                                .setJsonObject("orderNumber", a)
                                .setOkHttpLo(jsonObject1 -> {
                                    if (jsonObject1.optString("code").equals("200")) {
                                        Intent intent = new Intent(this, TimeUpDd.class);
                                        finish();
                                        startActivity(intent);
                                        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                                    }
                                }).start();

                    }
                }).start();
    }

    private void initView() {
        title1 = findViewById(R.id.title1);
        tvGrzx = findViewById(R.id.tv_grzx);
        line1 = findViewById(R.id.line1);
        yundanh = findViewById(R.id.yundanh);
        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        but1 = findViewById(R.id.but1);
    }
}