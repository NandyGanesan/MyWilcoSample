package com.android.wilcoconnect.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.wilcoconnect.R;

public class Home extends Fragment {

    private static String TAG = "Home";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Toolbar home_toolbar = view.findViewById(R.id.main_nonav_toolbar);
        home_toolbar.setTitle("MYWILCO HOME");
        return view;
    }
}
