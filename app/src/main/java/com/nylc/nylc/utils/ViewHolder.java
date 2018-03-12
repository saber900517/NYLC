package com.nylc.nylc.utils;

import android.util.SparseArray;
import android.view.View;

/**
 * 万能ViewHolder
 * Created by 吴曰阳 on 2018/3/3.
 */

public class ViewHolder {
    public static <T extends View> T get(View view, int id) {

        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        //如果根view没有用来缓存View的集合
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);//创建集合和根View关联
        }
        View childView = viewHolder.get(id);//获取根View储存在集合中的孩纸
        if (childView == null) {//如果没有改孩纸
            //找到该孩纸
            childView = view.findViewById(id);
            viewHolder.put(id, childView);//保存到集合
        }
        return (T) childView;
    }

}
