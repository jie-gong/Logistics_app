package com.example.logistics.util;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @user 公杰
 * @方法描述 。。。。
 * @Date 2022/11/14/上午 11:43
 */
public class SPUtil {
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;

    //获取SharedPreferences的的工具类
    public String getSp(Context mContext, String id) {
        sharedPreferences = mContext.getSharedPreferences("userInfo", MODE_PRIVATE);//将内容存放到userinfo的文档内
        editor = sharedPreferences.edit();
        return sharedPreferences.getString(id, "");//获取账号
    }

    //存储数据信息
    public void setSp(Context context, String sp) {
        SharedPreferences pref = context.getSharedPreferences("userInfo", MODE_PRIVATE);//将内容存放到userinfo的文档内
        editor = pref.edit();
        editor.putString(sp, sp);
        editor.commit();
    }

    //储存客户账号
    public void setUserSp(Context context, String sp) {
        SharedPreferences pref = context.getSharedPreferences("userInfo", MODE_PRIVATE);//将内容存放到userinfo的文档内
        editor = pref.edit();
        editor.putString("user", sp);
        editor.commit();
    }

    //清除sp
    public void clear(Context mContext, String id) {
        SharedPreferences pref = mContext.getSharedPreferences("userInfo", MODE_PRIVATE);//将内容存放到userinfo的文档内
        editor = pref.edit();
        editor.remove(id);
        editor.commit();
    }

}
