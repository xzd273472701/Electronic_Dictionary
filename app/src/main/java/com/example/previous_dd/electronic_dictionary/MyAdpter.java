package com.example.previous_dd.electronic_dictionary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by previous_DD on 2017/6/14.
 */

public class MyAdpter extends BaseAdapter {
    private List<Cidian> list;
    private ListView listview;

    public MyAdpter(List<Cidian> list) {
        super();
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (listview == null)
        {
            listview = (ListView) parent;
        }
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.items, null);
            holder = new ViewHolder();
            holder.yingwen = (TextView) convertView.findViewById(R.id.zongying);
            holder.zhongwen = (TextView) convertView.findViewById(R.id.zongzhong);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Cidian news = list.get(position);
        holder.yingwen.setText(news.getEnglish());
        holder.zhongwen.setText(news.getChinese());

        return convertView;
    }

    class ViewHolder {
        TextView yingwen, zhongwen;
    }
}
