package com.android.wilcoconnect.fragment.leave;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.model.leave.ApproveList;
import com.android.wilcoconnect.shared.leave.CalenderListAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ViewCalenderLeaveDetail extends DialogFragment {

    /*
     * Initialize the variables to access the Module
     * */
    public String TAG = "ViewCalenderLeaveDetail";
    private ArrayList<ApproveList> leaveData = new ArrayList<>();
    private RecyclerView recyclerView;
    private CalenderListAdapter calenderListAdapter;
    private Button close_button;
    private TextView dataNotFound;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_calender_leave_detail, container, false);

        /*
        * Define the UI Element and It's Action
        * */
        close_button = view.findViewById(R.id.btn_close);
        close_button.setOnClickListener(v -> dismiss());
        dataNotFound = view.findViewById(R.id.label_name);

        /*
         * Define the ToolBar
         * */
        Toolbar calender_Toolbar = view.findViewById(R.id.main_nonav_toolbar);
        calender_Toolbar.setTitle("VIEW LEAVE DETAILS");
        calender_Toolbar.setNavigationIcon(R.drawable.close);
        calender_Toolbar.setNavigationOnClickListener(v -> dismiss());

        /*
         * Retrieve the Intent Value
         * */
        String value = this.getArguments().getString("leavecalender");
        Gson gson = new Gson();
        leaveData = new ArrayList<>();
        leaveData = gson.fromJson(value, new TypeToken<List<ApproveList>>(){}.getType());

        recyclerView = view.findViewById(R.id.calender_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        /*
        * Set Adapter to list of Data
        * */
        if(leaveData.size()>0){
            dataNotFound.setVisibility(View.GONE);
            calenderListAdapter = new CalenderListAdapter(getActivity(),leaveData);
            recyclerView.setAdapter(calenderListAdapter);
        }
        /*
         * Set Adapter to list of Data is an Empty
         * */
        else {
            recyclerView.setAdapter(null);
            dataNotFound.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(),"No data found",Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    /*
     * Dialog Window OnStart Method
     * */
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}
