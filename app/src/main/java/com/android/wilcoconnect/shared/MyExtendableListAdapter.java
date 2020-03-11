package com.android.wilcoconnect.shared;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.android.wilcoconnect.R;

import java.util.HashMap;
import java.util.List;

public class MyExtendableListAdapter extends BaseExpandableListAdapter {

    private HashMap<String, List<String>> mstringListHashMap;
    private String[] mArrayList;

    public MyExtendableListAdapter(HashMap<String, List<String>> mstringListHashMap) {
        this.mstringListHashMap = mstringListHashMap;
        this.mArrayList = mstringListHashMap.keySet().toArray(new String[0]);
    }

    @Override
    public int getGroupCount() {
        return mArrayList.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mstringListHashMap.get(mArrayList[groupPosition]).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mArrayList[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mstringListHashMap.get(mArrayList[groupPosition]).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition*childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_list,parent,false);

        TextView textView = convertView.findViewById(R.id.item_main_text);
        textView.setText(String.valueOf(getGroup(groupPosition)));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_extend_list_item,parent,false);

        TextView textView = convertView.findViewById(R.id.extend_item);
        textView.setText(String.valueOf(getChild(groupPosition,childPosition)));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
