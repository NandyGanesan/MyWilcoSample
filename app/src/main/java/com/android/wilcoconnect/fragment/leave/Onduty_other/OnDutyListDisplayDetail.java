package com.android.wilcoconnect.fragment.leave.Onduty_other;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.model.leave.Onduty.OnDutyData;
import com.google.gson.Gson;

public class OnDutyListDisplayDetail extends DialogFragment {

    /*
     * Initialize the XML element or views
     * */
    public static final String TAG = "OnDutyListDisplayDetail";
    private View view;
    private TextView tv_View_type;
    private TextView tv_View_fromdate;
    private TextView tv_View_todate;
    private TextView tv_View_no_of_counts;
    private TextView tv_View_reason;
    private TextView tv_View_status;
    private TextView tv_View_approveddate;
    private TextView tv_View_approvedremarks;
    private TextView tv_View_approver;
    private TextView label1,label2,label3;
    private OnDutyData dutyData = new OnDutyData();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_on_duty_list_display_detail, container, false);

        /*
        * Define the Toolbar
        * */
        Toolbar detail_toolbar = view.findViewById(R.id.main_withnav_toolbar);
        detail_toolbar.setTitle("VIEW ON-DUTY DETAILS");
        detail_toolbar.setNavigationIcon(R.drawable.close);
        detail_toolbar.setNavigationOnClickListener(v -> dismiss());

        /*
         * Retrieve the Intent Value
         * */
        String value = this.getArguments().getString("OnDuty");
        Gson gson = new Gson();
        dutyData = gson.fromJson(value, OnDutyData.class);

        /*
         * Assign the Values for the Particular View Elements
         * */
        tv_View_type = view.findViewById(R.id.et_View_type);
        tv_View_fromdate = view.findViewById(R.id.et_View_fromdate);
        tv_View_todate = view.findViewById(R.id.et_View_todate);
        tv_View_no_of_counts = view.findViewById(R.id.et_View_no_of_counts);
        tv_View_reason = view.findViewById(R.id.et_View_reason);
        tv_View_status = view.findViewById(R.id.et_View_status);
        tv_View_approveddate = view.findViewById(R.id.et_View_approveddate);
        tv_View_approvedremarks = view.findViewById(R.id.et_View_remarksbyapprover);
        tv_View_approver = view.findViewById(R.id.et_View_approver);
        label1 = view.findViewById(R.id.tv_View_approveddate);
        label2 = view.findViewById(R.id.tv_View_remarksbyapprover);
        label3 = view.findViewById(R.id.tv_View_approver);

        /*
        * Assign the Value to UI Element
        * */
        tv_View_type.setText(dutyData.getOnDutyName());
        tv_View_fromdate.setText(dutyData.getStrFromDate());
        tv_View_todate.setText(dutyData.getStrToDate());
        tv_View_no_of_counts.setText(""+dutyData.getNumberOfDays());
        tv_View_reason.setText(dutyData.getReason());
        tv_View_status.setText(dutyData.getRequestStatus());
        tv_View_approveddate.setText(dutyData.getApprovedDate());
        tv_View_approvedremarks.setText(dutyData.getApprovedRemarks());
        tv_View_approver.setText(dutyData.getPrimaryApprover());

        /*
        * Status based UI Element Hide and Show
        * */
        if(dutyData.getRequestStatus().equals("Applied")){
            tv_View_approveddate.setVisibility(View.GONE);
            tv_View_approvedremarks.setVisibility(View.GONE);
            tv_View_approver.setVisibility(View.GONE);
            label1.setVisibility(View.GONE);
            label2.setVisibility(View.GONE);
            label3.setVisibility(View.GONE);
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
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}