package com.example.logistics.m1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.logistics.R;
import com.example.logistics.adapter.F1_adapter;
import com.example.logistics.bean.F1_Bean;
import com.example.logistics.http.OkHttpTo;
import com.example.logistics.util.SPUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @user 公杰
 * @方法描述 。。。。
 * @Date 2022/11/29/下午 05:16
 */
public class M1_fragment1 extends Fragment {
    private Button but;
    private SPUtil spUtil1;
    private Button but2;
    private ListView listItem;
    private TextView t1;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        spUtil1 = new SPUtil();
        String sp1 = spUtil1.getSp(requireContext(), "id");
        if (sp1.equals("")) {
            but.setVisibility(View.VISIBLE);
            but2.setVisibility(View.VISIBLE);
        } else {
            but.setVisibility(View.GONE);
            but2.setVisibility(View.GONE);
            getClient();
        }
        but.setOnClickListener(view1 -> {
            View inflate = View.inflate(requireContext(), R.layout.login, null);
            AlertDialog show = new AlertDialog.Builder(requireContext()).setView(inflate).show();
            EditText zh = inflate.findViewById(R.id.zh);
            EditText mm = inflate.findViewById(R.id.mm);
            Button clicked = inflate.findViewById(R.id.clicked);
            clicked.setOnClickListener(vie -> {
                String s = zh.getText().toString();
                String s1 = mm.getText().toString();
                spUtil1.setUserSp(requireContext(), s);
                new OkHttpTo()
                        .setUrl("/custumer/login")
                        .setJsonObject("custumerId", s)
                        .setJsonObject("custumerPassword", s1)
                        .setType("POST")
                        .setOkHttpLo(jsonObject -> {
                            if (jsonObject.optString("code").equals("200")) {
                                spUtil1.setSp(requireContext(), s);
                                Toast.makeText(getActivity(), "登陆成功", Toast.LENGTH_SHORT).show();
                                show.dismiss();
                                but.setVisibility(View.GONE);
                                but2.setVisibility(View.GONE);
                                getClient();
                            } else {
                                Toast.makeText(getActivity(), "账号或密码错误", Toast.LENGTH_SHORT).show();
                            }
                        }).start();
            });
        });
        but2.setOnClickListener(view1 -> {
            View inflate = View.inflate(requireContext(), R.layout.register, null);
            AlertDialog show = new AlertDialog.Builder(requireContext()).setView(inflate).show();
            EditText zh = inflate.findViewById(R.id.zh);
            EditText mm = inflate.findViewById(R.id.mm);
            EditText mm2 = inflate.findViewById(R.id.mm2);
            EditText tel = inflate.findViewById(R.id.tel);
            EditText home = inflate.findViewById(R.id.home);
            Button clicked = inflate.findViewById(R.id.clicked);
            clicked.setOnClickListener(view2 -> {
                String s = zh.getText().toString();
                String s1 = mm.getText().toString();
                String s2 = mm2.getText().toString();
                String s3 = tel.getText().toString();
                String s4 = home.getText().toString();
                if (s1.equals(s2)) {
                    new OkHttpTo()
                            .setUrl("/custumer/register")
                            .setJsonObject("custumerId", s)
                            .setJsonObject("custumerPassword", s2)
                            .setJsonObject("custumerTel", s3)
                            .setJsonObject("custumerAddress", s4)
                            .setType("POST")
                            .setOkHttpLo(jsonObject -> {
                                if (jsonObject.optString("code").equals("200")) {
                                    Toast.makeText(requireContext(), "请重新登录", Toast.LENGTH_SHORT).show();
                                    show.dismiss();
                                    but2.setVisibility(View.GONE);
                                }
                            }).start();
                } else {
                    Toast.makeText(requireContext(), "请确认两次密码相同", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private F1_adapter adapter;
    private ArrayList<F1_Bean> beans = new ArrayList<>();

    private void getClient() {
        new OkHttpTo()
                .setType("POST")
                .setUrl("/custumer/selectById")
                .setJsonObject("custumerId", spUtil1.getSp(requireContext(), "user"))
                .setOkHttpLo(jsonObject -> {
                    if (jsonObject.optString("code").equals("200")) {
                        beans = new Gson()
                                .fromJson(jsonObject.optJSONArray("data").toString(),
                                        new TypeToken<List<F1_Bean>>() {
                                        }.getType());
                        if (beans.size() == 0) {
                            t1.setVisibility(View.VISIBLE);
                        } else {
                            t1.setVisibility(View.GONE);
                        }
                        adapter = new F1_adapter(beans);

                        requireActivity().runOnUiThread(() -> {
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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f1, null);

    }

    private void initView() {
        but = getView().findViewById(R.id.but);
        but2 = getView().findViewById(R.id.but2);
        listItem = getView().findViewById(R.id.list_item);
        t1 = getView().findViewById(R.id.title1);
    }

}

