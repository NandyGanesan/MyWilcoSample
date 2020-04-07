package com.android.wilcoconnect.fragment.leave.compensatory;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.model.leave.compensatory.CompOffDetailData;
import com.google.gson.Gson;

public class CompOffDetailDisplay extends DialogFragment {

    public static final String TAG = "CompOffDetailDisplay";
    private CompOffDetailData data;
    private TextView tv_View_fromdate;
    private TextView tv_View_todate;
    private TextView tv_View_no_of_counts;
    private TextView tv_View_remarks;
    private TextView tv_View_leavestatus;
    private TextView tv_View_approveddate;
    private TextView tv_View_remarksbyapprover;
    private TextView tv_View_approver;
    private TextView tv_View_applieddate;
    private TextView label1,label2,label3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comp_off_detail, container, false);

        /*
         * Retrieve the Intent Value
         * */
        String value = this.getArguments().getString("compOff");
        Gson gson = new Gson();
        data = gson.fromJson(value, CompOffDetailData.class);

        Toolbar detail_toolbar = view.findViewById(R.id.main_withnav_toolbar);
        detail_toolbar.setTitle("COMP-OFF DETAILS");
        detail_toolbar.setNavigationIcon(R.drawable.close);
        detail_toolbar.setNavigationOnClickListener(v -> dismiss());

        tv_View_applieddate = view.findViewById(R.id.et_View_applieddate);
        tv_View_fromdate = view.findViewById(R.id.et_View_fromdate);
        tv_View_todate = view.findViewById(R.id.et_View_todate);
        tv_View_no_of_counts = view.findViewById(R.id.et_View_no_of_counts);
        tv_View_remarks = view.findViewById(R.id.et_View_remarks);
        tv_View_leavestatus = view.findViewById(R.id.et_View_status);
        tv_View_approveddate = view.findViewById(R.id.et_View_approveddate);
        tv_View_remarksbyapprover = view.findViewById(R.id.et_View_remarksbyapprover);
        tv_View_approver = view.findViewById(R.id.et_View_approver);

        label1 = view.findViewById(R.id.tv_View_approveddate);
        label2 = view.findViewById(R.id.tv_View_remarksbyapprover);
        label3 = view.findViewById(R.id.tv_View_approver);

        tv_View_applieddate.setText(data.getAppliedDate());
        tv_View_fromdate.setText(data.getStrFromDate());
        tv_View_todate.setText(data.getStrToDate());
        tv_View_no_of_counts.setText(""+data.getNoOfDays());
        tv_View_remarks.setText(data.getRequestRemarks());
        tv_View_leavestatus.setText(data.getRequestStatus());
        if(data.getRequestStatus().equals("Applied")){
            tv_View_approveddate.setVisibility(View.GONE);
            tv_View_remarksbyapprover.setVisibility(View.GONE);
            tv_View_approver.setVisibility(View.GONE);
            label1.setVisibility(View.GONE);
            label2.setVisibility(View.GONE);
            label3.setVisibility(View.GONE);
        }
        tv_View_approveddate.setText(data.getStrApprovedDate());
        tv_View_remarksbyapprover.setText(data.getApprovedRemarks());
        tv_View_approver.setText(data.getPrimaryApprover());

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
