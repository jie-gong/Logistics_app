package com.example.logistics.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.logistics.R;
import com.example.logistics.bean.F2_Bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @user 公杰
 * @方法描述 。。。。
 * @Date 2022/11/30/下午 04:19
 */
public class F2_adapter extends BaseAdapter {
    private ArrayList<F2_Bean.StepListDTO> beans;


    public F2_adapter(ArrayList<F2_Bean.StepListDTO> beans) {
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
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_sun_f2, viewGroup, false);
            viewHolder.address = view.findViewById(R.id.address);
            viewHolder.date = view.findViewById(R.id.date);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        F2_Bean.StepListDTO bean = beans.get(i);
        Date createTime = bean.getCreateTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String format = sdf.format(createTime);
        viewHolder.date.setText(format);
        viewHolder.address.setText(bean.getAddressAt());

        return view;
    }

    class ViewHolder {
        private TextView address;
        private TextView date;
    }
}
