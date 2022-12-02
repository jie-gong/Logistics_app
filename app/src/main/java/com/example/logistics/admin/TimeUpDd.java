package com.example.logistics.admin;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.logistics.R;
import com.example.logistics.admin.adapter.DD_Adapter;
import com.example.logistics.admin.bean.DD_bean;
import com.example.logistics.http.OkHttpTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class TimeUpDd extends AppCompatActivity {

    private TextView title1;
    private TextView tvGrzx;
    private ListView listItem;
    private TextView t1;
    private SwipeRefreshLayout xiala;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_up_dd);
        initView();
        title1.setText("客户申请修改的订单");
        getOkHttp();
        xiala.setColorSchemeColors(Color.parseColor("#ff0000"),Color.parseColor("#00ff00"));
        xiala.setProgressBackgroundColorSchemeColor(Color.parseColor("#0000ff"));
        xiala.setOnRefreshListener(() -> {
            //判断是否在刷新
            getOkHttp();
            Toast.makeText(TimeUpDd.this, "刷新完成", Toast.LENGTH_SHORT).show();
            xiala.postDelayed(() -> {
                //关闭刷新
                xiala.setRefreshing(false);
            }, 100);
        });

    }

    private ArrayList<DD_bean> beans = new ArrayList<>();

    private DD_Adapter adapter;

    private void getOkHttp() {
        new OkHttpTo()
                .setType("GET")
                .setUrl("/admin/selectByDel")
                .setOkHttpLo(jsonObject -> {
                    if (jsonObject.optString("code").equals("200")) {
                        beans = new Gson()
                                .fromJson(jsonObject.optJSONArray("data").toString(),
                                        new TypeToken<List<DD_bean>>() {
                                        }.getType());
                        if (beans.size() == 0) {
                            t1.setVisibility(View.VISIBLE);
                        } else {
                            t1.setVisibility(View.GONE);
                        }
                        adapter = new DD_Adapter(beans);

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
        t1 = findViewById(R.id.t1);
        xiala = findViewById(R.id.xiala);
    }
}