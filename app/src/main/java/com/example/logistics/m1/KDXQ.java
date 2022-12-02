package com.example.logistics.m1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.logistics.R;
import com.example.logistics.adapter.F2_adapter;
import com.example.logistics.bean.F2_Bean;
import com.example.logistics.http.OkHttpLo;
import com.example.logistics.http.OkHttpTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KDXQ extends AppCompatActivity {

    private TextView title1;
    private LinearLayout line1;
    private TextView yundanh;
    private TextView site;
    private LinearLayout line2;
    private ListView dizhi;
    private TextView tvGrzx;
    private TextView qq;
    private Integer a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kdxq);
        initView();
        title1.setText("运单详情");
        a = (Integer) getIntent().getSerializableExtra("id");
        getDate();
        qq.setOnClickListener(view -> {
            new OkHttpTo()
                    .setUrl("/custumer/updateByDel")
                    .setType("POST")
                    .setJsonObject("orderNumber", a)
                    .setOkHttpLo(jsonObject -> {
                        if (jsonObject.optString("code").equals("200")) {
                            Toast.makeText(this, "申请成功", Toast.LENGTH_SHORT).show();
                        }
                    }).start();
        });
    }

    private ArrayList<F2_Bean.StepListDTO> beans = new ArrayList<>();

    private F2_adapter adapter;

    private void getDate() {
        new OkHttpTo()
                .setUrl("/admin/selectByClient")
                .setJsonObject("orderNumber", a)
                .setType("POST")
                .setOkHttpLo(jsonObject -> {
                    if (jsonObject.optString("code").equals("200")) {
                        line1.setVisibility(View.VISIBLE);
                        line2.setVisibility(View.VISIBLE);
                        try {
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            String site1 = jsonObject1.optString("site");
                            site.setText(site1);
                            String orderNumber = jsonObject1.optString("orderNumber");
                            yundanh.setText(orderNumber);
                            Log.d("TAG1111", "getDate: " + jsonObject1.optString("stepList"));
                            beans = new Gson().fromJson(jsonObject1.optJSONArray("stepList").toString(),
                                    new TypeToken<List<F2_Bean.StepListDTO>>() {
                                    }.getType());
                            adapter = new F2_adapter(beans);
                            runOnUiThread(() -> {
                                dizhi.setAdapter(adapter);
                                ListAdapter listAdapter = dizhi.getAdapter();
                                if (listAdapter == null) {
                                    return;
                                }
                                int h = 0;
                                for (int i = 0; i < beans.size(); i++) {
                                    View view = listAdapter.getView(i, null, dizhi);
                                    view.measure(1, 1);
                                    h += view.getMeasuredHeight();
                                }
                                ViewGroup.LayoutParams params = dizhi.getLayoutParams();
                                params.height = h + dizhi.getDividerHeight() * (listAdapter.getCount() - 1);
                                dizhi.setLayoutParams(params);
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
    }


    private void initView() {
        title1 = findViewById(R.id.title1);
        line1 = findViewById(R.id.line1);
        yundanh = findViewById(R.id.yundanh);
        site = findViewById(R.id.site);
        line2 = findViewById(R.id.line2);
        dizhi = findViewById(R.id.dizhi);
        tvGrzx = findViewById(R.id.tv_grzx);
        qq = findViewById(R.id.qq);
    }
}