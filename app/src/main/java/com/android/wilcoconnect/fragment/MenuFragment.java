package com.android.wilcoconnect.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.activity.LoginActivity;
import com.android.wilcoconnect.shared.MyExtendableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuFragment extends Fragment {

    private String TAG = "MenuFragment";
    private Toolbar menu_toolbar;
    private static String MY_PREFS_NAME = "MyPrefsFile";
    private ExpandableListView expandable_ListView;
    private MyExtendableListAdapter adapter;
    private HashMap<String, List<String>> item;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        menu_toolbar = view.findViewById(R.id.main_nonav_toolbar);
        menu_toolbar.setTitle("MENU");

        expandable_ListView = view.findViewById(R.id.elv_data);

        item = new HashMap<>();

        ArrayList<String> organization = new ArrayList<>();
        organization.add("Employee Directory");
        item.put("Organization", organization);

        ArrayList<String> training = new ArrayList<>();
        training.add("My Training");
        item.put("Training", training);

        ArrayList<String> logout = new ArrayList<>();
        item.put("Logout",logout);

        adapter = new MyExtendableListAdapter(item);
        expandable_ListView.setAdapter(adapter);

        expandable_ListView.setOnGroupClickListener((parent, v, groupPosition, id) -> {
            if(adapter.getChildrenCount(groupPosition)==0){
                if(adapter.getGroupId(groupPosition)==2){
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    SharedPreferences preferences = getActivity().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();
                    getActivity().finish();
                }
            }
            return false;
        });
        return view;
    }
}