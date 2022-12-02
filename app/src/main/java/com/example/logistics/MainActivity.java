package com.example.logistics;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.logistics.login.Admin;
import com.example.logistics.login.UserLogin;
import com.example.logistics.m1.Adapter.Main1_fragment_Adapter;
import com.example.logistics.m1.M1_fragment1;
import com.example.logistics.m1.M1_fragment2;
import com.example.logistics.m1.M1_fragment3;
import com.example.logistics.m1.M1_fragment4;
import com.example.logistics.util.SPUtil;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private androidx.drawerlayout.widget.DrawerLayout drawerLayout;
    private ImageView ivFh;
    private TextView title1;
    private TextView tvGrzx;
    private ViewPager2 viewpager;
    private NavigationView mainNav;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private SPUtil spUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        CeB();
        spUtil = new SPUtil();
        String admin = spUtil.getSp(this, "admin");
        if (admin.equals("")) {
            fragments.add(new M1_fragment1());
            fragments.add(new M1_fragment2());
            fragments.add(new M1_fragment3());
            fragments.add(new M1_fragment4());
            Main1_fragment_Adapter main1_fragment_adapter = new Main1_fragment_Adapter(getSupportFragmentManager(), getLifecycle(), fragments);
            viewpager.setAdapter(main1_fragment_adapter);
            viewpager.setOffscreenPageLimit(4);
            viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }

                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    switch (position) {
                        case 0:
                            title1.setText("主页");
                            break;
                        case 1:
                            title1.setText("查快递");
                            break;
                        case 2:
                            title1.setText("寄快递");
                            break;
                        case 3:
                            title1.setText("个人中心");
                            break;
                    }

                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    super.onPageScrollStateChanged(state);
                }
            });
        } else {
            Intent intent = new Intent(this, Admin.class);
            startActivity(intent);
        }

    }

    public void CeB() {
        ivFh.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        mainNav.setNavigationItemSelectedListener(item -> {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.tc:
                    intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    spUtil.clear(this, "id");
                    spUtil.clear(this, "User");
                    break;
                case R.id.qiehuanzhiye:
                    intent = new Intent(MainActivity.this, UserLogin.class);
                    startActivity(intent);
                    finish();

            }
            return true;
        });
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
        drawerLayout = findViewById(R.id.DrawerLayout);
        ivFh = findViewById(R.id.iv_fh);
        title1 = findViewById(R.id.title1);
        tvGrzx = findViewById(R.id.tv_grzx);
        viewpager = findViewById(R.id.viewpager);
        mainNav = findViewById(R.id.mainNav);
    }
}