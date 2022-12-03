package com.example.logistics.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.logistics.MainActivity;
import com.example.logistics.R;
import com.example.logistics.admin.Qbdd;
import com.example.logistics.admin.TimeUpDd;
import com.example.logistics.http.OkHttpLo;
import com.example.logistics.http.OkHttpTo;
import com.example.logistics.util.SPUtil;

import org.json.JSONObject;

public class Admin extends AppCompatActivity {

    private TextView title1;
    private TextView tvGrzx;
    private TextView math;
    private Button but1;
    private SPUtil spUtil;
    private SwipeRefreshLayout xiala;
    private TextView math2;
    private Button but2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        initView();
        //第二步，设置 下拉刷新时的颜色
        xiala.setColorSchemeColors(Color.parseColor("#ff0000"), Color.parseColor("#00ff00"));
        xiala.setProgressBackgroundColorSchemeColor(Color.parseColor("#0000ff"));
        xiala.setOnRefreshListener(() -> {
            getMath();
            getMath2();
            //判断是否在刷新
            Toast.makeText(Admin.this, "刷新完成", Toast.LENGTH_SHORT).show();
            xiala.postDelayed(() -> {
                //关闭刷新
                xiala.setRefreshing(false);
            }, 100);
        });

        spUtil = new SPUtil();
        title1.setText("管理员操作界面");
        tvGrzx.setText("返回用户登录");
        tvGrzx.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            spUtil.clear(this, "admin");
            startActivity(intent);
            finish();
        });
        getMath();
        getMath2();
        but1.setOnClickListener(view -> {
            Intent intent = new Intent(this, TimeUpDd.class);
            startActivity(intent);
        });
        but2.setOnClickListener(view -> {
            Intent intent = new Intent(Admin.this, Qbdd.class);
            startActivity(intent);
        });

    }

    private void getMath2() {
        new OkHttpTo()
                .setUrl("/admin/selectAll")
                .setType("GET")
                .setOkHttpLo(jsonObject -> {
                    if (jsonObject.optString("code").equals("200")) {
                        math2.setText("当前共有" + jsonObject.optString("total") + "个订单");
                    }
                }).start();
    }

    //显示订单数量
    private void getMath() {
        new OkHttpTo()
                .setUrl("/admin/selectByDel")
                .setType("GET")
                .setOkHttpLo(jsonObject -> {
                    if (jsonObject.optString("code").equals("200")) {
                        String total = jsonObject.optString("message");
                        math.setText(total);
                    }
                }).start();
    }

    private long time = 0;

    //双击退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long mNowTime = System.currentTimeMillis();/**  获取第一次按键时间*/
        if ((mNowTime - time) > 1000) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            time = mNowTime;
        } else {
            finish();
            System.exit(0);
        }
        return true;
    }

    private void initView() {
        title1 = findViewById(R.id.title1);
        tvGrzx = findViewById(R.id.tv_grzx);
        math = findViewById(R.id.math);
        but1 = findViewById(R.id.but1);
        xiala = findViewById(R.id.xiala);
        math2 = findViewById(R.id.math2);
        but2 = findViewById(R.id.but2);
    }
}