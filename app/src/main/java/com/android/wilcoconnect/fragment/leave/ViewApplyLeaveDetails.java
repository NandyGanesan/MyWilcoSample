package com.android.wilcoconnect.fragment.leave;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.model.leave.MyLeaveData;
import com.google.gson.Gson;

public class ViewApplyLeaveDetails extends DialogFragment {

    private MyLeaveData leaveData;
    private TextView tv_View_leavetype;
    private TextView tv_View_fromdate;
    private TextView tv_View_todate;
    private TextView tv_View_no_of_counts;
    private TextView tv_View_remarks;
    private TextView tv_View_leavestatus;
    private TextView tv_View_approveddate;
    private TextView tv_View_remarksbyapprover;
    private TextView tv_View_approver;
    private TextView tv_View_applieddate;
    public static String TAG = "ViewApplyLeaveDetails";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_leave_details, container, false);

        /*
         * Retrieve the Intent Value
         * */
        String value = this.getArguments().getString("leave");
        Gson gson = new Gson();
        leaveData = gson.fromJson(value, MyLeaveData.class);

        Toolbar detail_toolbar = view.findViewById(R.id.main_withnav_toolbar);
        detail_toolbar.setTitle("VIEW LEAVE DETAILS");
        detail_toolbar.setNavigationIcon(R.drawable.close);
        detail_toolbar.setNavigationOnClickListener(v -> dismiss());

        tv_View_leavetype = view.findViewById(R.id.et_View_leavetype);
        tv_View_applieddate = view.findViewById(R.id.et_View_applieddate);
        tv_View_fromdate = view.findViewById(R.id.et_View_fromdate);
        tv_View_todate = view.findViewById(R.id.et_View_todate);
        tv_View_no_of_counts = view.findViewById(R.id.et_View_no_of_counts);
        tv_View_remarks = view.findViewById(R.id.et_View_remarks);
        tv_View_leavestatus = view.findViewById(R.id.et_View_leavestatus);
        tv_View_approveddate = view.findViewById(R.id.et_View_approveddate);
        tv_View_remarksbyapprover = view.findViewById(R.id.et_View_remarksbyapprover);
        tv_View_approver = view.findViewById(R.id.et_View_approver);


        tv_View_leavetype.setText(leaveData.getLeaveType());
        tv_View_applieddate.setText(leaveData.getAppliedDate());
        tv_View_fromdate.setText(leaveData.getFromDate());
        tv_View_todate.setText(leaveData.getToDate());
        tv_View_no_of_counts.setText(""+leaveData.getNo_of_Counts());
        tv_View_remarks.setText(leaveData.getRemarks());
        tv_View_leavestatus.setText(leaveData.getLeaveStatus());
        tv_View_approveddate.setText(leaveData.getApprovedDate());
        tv_View_remarksbyapprover.setText(leaveData.getRemarksByApprover());
        tv_View_approver.setText(leaveData.getApprover());

        return view;
    }

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
