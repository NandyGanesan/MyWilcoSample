package com.android.wilcoconnect.fragment.leave;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.model.leave.MyLeaveData;
import com.google.gson.Gson;

public class ApprovedListDisplay extends DialogFragment {

    /*
     * Initialize the variables to access the Module
     * */
    public static final String TAG = "ApprovedListDisplay";
    private MyLeaveData leaveData;
    private TextView et_View_employeeid;
    private TextView et_View_employeename;
    private TextView et_View_leavetype;
    private TextView et_View_applieddate;
    private TextView et_View_fromdate;
    private TextView et_View_todate;
    private TextView et_View_noofday;
    private TextView et_View_remarks;
    private TextView et_View_status;
    private TextView et_View_approveddate;
    private TextView et_View_approvedremarks;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_approved_list_display, container, false);

        /*
         * Retrieve the Intent Value
         * */
        String value = this.getArguments().getString("leave");
        Gson gson = new Gson();
        leaveData = gson.fromJson(value, MyLeaveData.class);

        /*
         * Define the ToolBar
         * */
        Toolbar detail_toolbar = view.findViewById(R.id.main_withnav_toolbar);
        detail_toolbar.setTitle("VIEW LEAVE DETAILS");
        detail_toolbar.setNavigationIcon(R.drawable.close);
        detail_toolbar.setNavigationOnClickListener(v -> dismiss());

        /*
         * Define the UI element
         * */
        et_View_employeeid = view.findViewById(R.id.et_View_employeeid);
        et_View_employeename = view.findViewById(R.id.et_View_employeename);
        et_View_leavetype = view.findViewById(R.id.et_View_leavetype);
        et_View_applieddate = view.findViewById(R.id.et_View_applieddate);
        et_View_fromdate = view.findViewById(R.id.et_View_fromdate);
        et_View_todate = view.findViewById(R.id.et_View_todate);
        et_View_noofday = view.findViewById(R.id.et_View_noofday);
        et_View_remarks = view.findViewById(R.id.et_View_remarks);
        et_View_status = view.findViewById(R.id.et_View_status);
        et_View_approveddate = view.findViewById(R.id.et_View_approveddate);
        et_View_approvedremarks = view.findViewById(R.id.et_View_approvedremarks);

        /*
         * Assign the Value to the UI element
         * */
        et_View_employeeid.setText(leaveData.getEmployeeID());
        et_View_employeename.setText(leaveData.getFirstName());
        et_View_leavetype.setText(leaveData.getLeaveTypeText());
        et_View_applieddate.setText(leaveData.getLeaveAppliedDate());
        et_View_fromdate.setText(leaveData.getStrFromDate());
        et_View_todate.setText(leaveData.getStrToDate());
        et_View_noofday.setText(""+leaveData.getNoofDays());
        et_View_remarks.setText(leaveData.getRequestRemarks());
        et_View_status.setText(leaveData.getRequestStatus());
        et_View_approveddate.setText(leaveData.getApprovedDate());
        et_View_approvedremarks.setText(leaveData.getApprovedRemarks());

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
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}
