package com.example.logistics.m1;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.logistics.R;
import com.example.logistics.bean.F3_Bean;
import com.example.logistics.http.OkHttpLo;
import com.example.logistics.http.OkHttpTo;
import com.example.logistics.util.SPUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * @user 公杰
 * @方法描述 。。。。
 * @Date 2022/11/29/下午 05:17
 */
public class M1_fragment3 extends Fragment {
    private EditText site1;
    private EditText site2;
    private EditText name;
    private EditText phone2;
    private Button getUp;
    private SPUtil spUtil;
    private LinearLayout newsInflateItem;
    private TextView yundanhao;
    private TextView fuzhi;
    private TextView newsContentItem;
    private TextView newsNumberItem;
    private TextView newsDateItem;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        newsInflateItem.setVisibility(View.GONE);
        getUp.setOnClickListener(view1 -> {
            UpOkHttp();
        });
    }

    private void UpOkHttp() {
        spUtil = new SPUtil();
        String sp = spUtil.getSp(requireContext(), "user");
        String text01 = site1.getText().toString();
        String text02 = site2.getText().toString();
        String phone02 = phone2.getText().toString();
        String name01 = name.getText().toString();
        if (text01.equals("") || text02.equals("") || phone02.equals("") || name01.equals("")) {
            Toast.makeText(requireContext(), "请填写全部内容", Toast.LENGTH_SHORT).show();
        } else {
            String aa = text01 + "————" + text02;
            new OkHttpTo()
                    .setType("POST")
                    .setUrl("/custumer/create")
                    .setJsonObject("custumerId", sp)
                    .setJsonObject("site", aa)
                    .setJsonObject("getName", name01)
                    .setJsonObject("getPhone", phone02)
                    .setOkHttpLo(jsonObject -> {
                        if (jsonObject.optString("code").equals("200")) {
                            Toast.makeText(requireContext(), "发货成功", Toast.LENGTH_SHORT).show();
                            String data = jsonObject.optString("data");
                            new OkHttpTo()
                                    .setType("POST")
                                    .setUrl("/admin/selectByClient")
                                    .setJsonObject("orderNumber", data)
                                    .setOkHttpLo(jsonObject1 -> {
                                        if (jsonObject1.optString("code").equals("200")) {
                                            try {
                                                newsInflateItem.setVisibility(View.VISIBLE);
                                                JSONObject jsonObject2 = jsonObject1.getJSONObject("data");
                                                yundanhao.setText(jsonObject2.optString("orderNumber"));
                                                fuzhi.setOnClickListener(view -> {
                                                    ClipboardManager cm = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                                                    ClipData mClipData = ClipData.newPlainText("Label", jsonObject2.optString("orderNumber") + "");
                                                    cm.setPrimaryClip(mClipData);
                                                    Toast.makeText(requireContext(), "复制成功", Toast.LENGTH_SHORT).show();
                                                });
                                                newsInflateItem.setOnClickListener(view -> {
                                                    Toast.makeText(requireContext(), "请复制单号进行查询", Toast.LENGTH_SHORT).show();
                                                });
                                                newsContentItem.setText(jsonObject2.optString("site"));
                                                newsDateItem.setText("订单完成状态：未完成");
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).start();
                        }
                    }).start();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f3, null);

    }

    private void initView() {
        site1 = getView().findViewById(R.id.site1);
        site2 = getView().findViewById(R.id.site2);
        name = getView().findViewById(R.id.name);
        phone2 = getView().findViewById(R.id.phone2);
        getUp = getView().findViewById(R.id.getUp);
        newsInflateItem = getView().findViewById(R.id.news_inflate_item);
        yundanhao = getView().findViewById(R.id.yundanhao);
        fuzhi = getView().findViewById(R.id.fuzhi);
        newsContentItem = getView().findViewById(R.id.news_content_item);
        newsNumberItem = getView().findViewById(R.id.news_number_item);
        newsDateItem = getView().findViewById(R.id.news_date_item);
    }
}
