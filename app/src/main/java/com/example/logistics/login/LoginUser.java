package com.example.logistics.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.logistics.R;
import com.example.logistics.http.OkHttpLo;
import com.example.logistics.http.OkHttpTo;
import com.example.logistics.util.SPUtil;

import org.json.JSONObject;

/**
 * @user 公杰
 * @方法描述 。。。。
 * @Date 2022/11/29/下午 06:07
 */
public class LoginUser extends DialogFragment {
    private EditText zh;
    private EditText mm;
    private Button clicked;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login, null);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        String s = zh.getText().toString();
        String s1 = mm.getText().toString();
        clicked.setOnClickListener(view -> {
            new OkHttpTo()
                    .setUrl("/custumer/login")
                    .setJsonObject("custumer_id", s)
                    .setJsonObject("custumer_password", s1)
                    .setOkHttpLo(jsonObject -> {
                        if (jsonObject.optString("date").equals("200")) {
                            SPUtil spUtil = new SPUtil();
                            spUtil.setSp(requireContext(), s);
                            Toast.makeText(getActivity(), "登陆成功", Toast.LENGTH_SHORT).show();
                        }
                    }).start();
        });
    }

    private void initView() {
        zh = getView().findViewById(R.id.zh);
        mm = getView().findViewById(R.id.mm);
        clicked = getView().findViewById(R.id.clicked);
    }
}
