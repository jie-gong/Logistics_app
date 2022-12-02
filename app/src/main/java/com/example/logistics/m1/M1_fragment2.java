package com.example.logistics.m1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

/**
 * @user 公杰
 * @方法描述 。。。。
 * @Date 2022/11/29/下午 05:17
 */
public class M1_fragment2 extends Fragment {
    private EditText edit;
    private TextView but1;
    private LinearLayout line1;
    private TextView yundanh;
    private TextView site;
    private TextView t1;
    private LinearLayout line2;
    private ListView dizhi;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        line1.setVisibility(View.GONE);
        line2.setVisibility(View.GONE);
        site.setVisibility(View.GONE);
        t1.setVisibility(View.VISIBLE);
        but1.setOnClickListener(view1 -> {
            if (edit.getText().toString().equals("")) {
                line1.setVisibility(View.GONE);
                line2.setVisibility(View.GONE);
                site.setVisibility(View.GONE);
                t1.setVisibility(View.VISIBLE);
            } else {
                getDate();
            }
        });
    }

    private ArrayList<F2_Bean.StepListDTO> beans = new ArrayList<>();

    private F2_adapter adapter;

    private void getDate() {
        String s = edit.getText().toString().trim();
        new OkHttpTo()
                .setUrl("/admin/selectByClient")
                .setJsonObject("orderNumber", s)
                .setType("POST")
                .setOkHttpLo(jsonObject -> {
                    if (jsonObject.optString("code").equals("200")) {
                        line1.setVisibility(View.VISIBLE);
                        line2.setVisibility(View.VISIBLE);
                        site.setVisibility(View.VISIBLE);
                        t1.setVisibility(View.GONE);
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
                            getActivity().runOnUiThread(() -> {
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f2, null);

    }

    private void initView() {
        edit = getView().findViewById(R.id.edit);
        but1 = getView().findViewById(R.id.but1);
        line1 = getView().findViewById(R.id.line1);
        yundanh = getView().findViewById(R.id.yundanh);
        site = getView().findViewById(R.id.site);
        line2 = getView().findViewById(R.id.line2);
        dizhi = getView().findViewById(R.id.dizhi);
        t1 = getView().findViewById(R.id.te1);
    }
}
