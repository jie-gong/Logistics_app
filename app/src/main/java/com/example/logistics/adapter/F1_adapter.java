package com.example.logistics.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logistics.R;
import com.example.logistics.bean.F1_Bean;
import com.example.logistics.m1.KDXQ;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @user 公杰
 * @方法描述 。。。。
 * @Date 2022/11/29/下午 10:39
 */
public class F1_adapter extends BaseAdapter {
    private ArrayList<F1_Bean> beans;
    private String b;
    private Context context;
    private static ClipboardManager manager;

    public F1_adapter(ArrayList<F1_Bean> beans) {
        this.beans = beans;
    }

    @Override

    public int getCount() {
        return beans.size();
    }

    @Override
    public Object getItem(int i) {
        return beans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = new ViewHolder();
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_sun_f1, viewGroup, false);
            viewHolder.fuzhi = view.findViewById(R.id.fuzhi);
            viewHolder.newsInflateItem = view.findViewById(R.id.news_inflate_item);
            viewHolder.yundanhao = view.findViewById(R.id.yundanhao);
            viewHolder.newsContentItem = view.findViewById(R.id.news_content_item);
            viewHolder.newsNumberItem = view.findViewById(R.id.news_number_item);
            viewHolder.newsDateItem = view.findViewById(R.id.news_date_item);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        F1_Bean f1_bean = beans.get(i);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String format = sdf.format(f1_bean.getCreateTime());
        viewHolder.yundanhao.setText(f1_bean.getOrderNumber() + "");
        viewHolder.fuzhi.setOnClickListener(view1 -> {
            ClipboardManager cm = (ClipboardManager) viewGroup.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData mClipData = ClipData.newPlainText("Label", f1_bean.getOrderNumber() + "");
            cm.setPrimaryClip(mClipData);
            Toast.makeText(viewGroup.getContext(), "复制成功", Toast.LENGTH_SHORT).show();
        });
        viewHolder.newsContentItem.setText(f1_bean.getSite());
        viewHolder.newsNumberItem.setText("发货时间:" + format);
        int sex = f1_bean.getSex();
        String a;
        if (sex == 1) {
            a = "已完成";
        } else {
            a = "未完成";
        }
        viewHolder.newsDateItem.setText("订房单完成状态:" + a);
        viewHolder.newsInflateItem.setOnClickListener(view1 -> {
            Intent intent = new Intent(viewGroup.getContext(), KDXQ.class);
            intent.putExtra("id",f1_bean.getOrderNumber());
            viewGroup.getContext().startActivity(intent);
        });
        return view;
    }


    class ViewHolder {
        private LinearLayout newsInflateItem;
        private TextView yundanhao;
        private TextView newsContentItem;
        private TextView newsNumberItem;
        private TextView newsDateItem;
        private TextView fuzhi;
    }
}
