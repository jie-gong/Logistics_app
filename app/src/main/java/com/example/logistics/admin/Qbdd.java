package com.example.logistics.admin;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.logistics.R;
import com.example.logistics.admin.adapter.DD2_Adapter;
import com.example.logistics.admin.bean.DD_bean;
import com.example.logistics.http.OkHttpTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

//全部订单
public class Qbdd extends AppCompatActivity {

    private TextView title1;
    private TextView tvGrzx;
    private ListView listItem;
    private EditText sousuoedit;
    private TextView sousuobut;
    private DD2_Adapter adapter;
    private ArrayList<DD_bean> beans = new ArrayList<>();
    private EditText sousuoedit1;
    private TextView sousuobut1;
    private Button but2;
    private Button but1;
    private LinearLayout line1;
    private LinearLayout line2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qbdd);
        initView();
        line1.setVisibility(View.GONE);
        line2.setVisibility(View.GONE);
        but1.setOnClickListener(view -> {
            line1.setVisibility(View.VISIBLE);
            line2.setVisibility(View.GONE);
        });
        but2.setOnClickListener(view -> {
            line2.setVisibility(View.VISIBLE);
            line1.setVisibility(View.GONE);
        });
        title1.setText("全部快递订单");
        tvGrzx.setText("返回上一级");
        tvGrzx.setOnClickListener(view -> {
            finish();
        });
        getClient();
        sousuobut.setOnClickListener(view1 -> {
            String text = sousuoedit.getText().toString();
            new OkHttpTo()
                    .setUrl("/admin/selectByPhone")
                    .setType("POST")
                    .setJsonObject("phone", text)
                    .setOkHttpLo(jsonObject -> {
                        if (jsonObject.optString("code").equals("200")) {
                            beans = new Gson()
                                    .fromJson(jsonObject.optJSONArray("data").toString(),
                                            new TypeToken<List<DD_bean>>() {
                                            }.getType());

                            adapter = new DD2_Adapter(beans);

                            runOnUiThread(() -> {
                                listItem.setAdapter(adapter);
                                ListAdapter listAdapter = listItem.getAdapter();
                                if (listAdapter == null) {
                                    return;
                                }
                                int h = 0;
                                for (int i = 0; i < beans.size(); i++) {
                                    View view = listAdapter.getView(i, null, listItem);
                                    view.measure(1, 1);
                                    h += view.getMeasuredHeight();
                                }
                                ViewGroup.LayoutParams params = listItem.getLayoutParams();
                                params.height = h + listItem.getDividerHeight() * (listAdapter.getCount() - 1);
                                listItem.setLayoutParams(params);
                            });
                        } else {
                            Toast.makeText(this, "当前没有订单", Toast.LENGTH_SHORT).show();
                            listItem.setAdapter(null);
                        }
                    }).start();
        });

        sousuobut1.setOnClickListener(view1 -> {
            String text = sousuoedit1.getText().toString();
            new OkHttpTo()
                    .setUrl("/admin/selectByUserPhone")
                    .setType("POST")
                    .setJsonObject("getPhone", text)
                    .setOkHttpLo(jsonObject -> {
                        if (jsonObject.optString("code").equals("200")) {
                            beans = new Gson()
                                    .fromJson(jsonObject.optJSONArray("data").toString(),
                                            new TypeToken<List<DD_bean>>() {
                                            }.getType());

                            adapter = new DD2_Adapter(beans);

                            runOnUiThread(() -> {
                                listItem.setAdapter(adapter);
                                ListAdapter listAdapter = listItem.getAdapter();
                                if (listAdapter == null) {
                                    return;
                                }
                                int h = 0;
                                for (int i = 0; i < beans.size(); i++) {
                                    View view = listAdapter.getView(i, null, listItem);
                                    view.measure(1, 1);
                                    h += view.getMeasuredHeight();
                                }
                                ViewGroup.LayoutParams params = listItem.getLayoutParams();
                                params.height = h + listItem.getDividerHeight() * (listAdapter.getCount() - 1);
                                listItem.setLayoutParams(params);
                            });
                        } else {
                            Toast.makeText(this, "当前没有订单", Toast.LENGTH_SHORT).show();
                            adapter = new DD2_Adapter(null);
                            listItem.setAdapter(null);
                        }
                    }).start();
        });
    }


    private void getClient() {
        new OkHttpTo()
                .setType("GET")
                .setUrl("/admin/selectAll")
                .setOkHttpLo(jsonObject -> {
                    if (jsonObject.optString("code").equals("200")) {
                        beans = new Gson()
                                .fromJson(jsonObject.optJSONArray("data").toString(),
                                        new TypeToken<List<DD_bean>>() {
                                        }.getType());

                        adapter = new DD2_Adapter(beans);

                        runOnUiThread(() -> {
                            listItem.setAdapter(adapter);
                            ListAdapter listAdapter = listItem.getAdapter();
                            if (listAdapter == null) {
                                return;
                            }
                            int h = 0;
                            for (int i = 0; i < beans.size(); i++) {
                                View view = listAdapter.getView(i, null, listItem);
                                view.measure(1, 1);
                                h += view.getMeasuredHeight();
                            }
                            ViewGroup.LayoutParams params = listItem.getLayoutParams();
                            params.height = h + listItem.getDividerHeight() * (listAdapter.getCount() - 1);
                            listItem.setLayoutParams(params);
                        });
                    }

                }).start();
    }

    private void initView() {
        title1 = findViewById(R.id.title1);
        tvGrzx = findViewById(R.id.tv_grzx);
        listItem = findViewById(R.id.list_item);
        sousuoedit = findViewById(R.id.sousuoedit);
        sousuobut = findViewById(R.id.sousuobut);
        sousuoedit1 = findViewById(R.id.sousuoedit1);
        sousuobut1 = findViewById(R.id.sousuobut1);
        but2 = findViewById(R.id.but2);
        but1 = findViewById(R.id.but1);
        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);
    }
}