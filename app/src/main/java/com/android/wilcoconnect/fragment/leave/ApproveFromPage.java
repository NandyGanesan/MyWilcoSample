package com.android.wilcoconnect.fragment.leave;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.api.ApiManager;
import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.model.leave.ApproveList;
import com.android.wilcoconnect.model.leave.ApprovePost;
import com.android.wilcoconnect.model.leave.leavebalance.GetLeaveBalance;
import com.android.wilcoconnect.model.leave.leavebalance.LeaveDetails;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.android.wilcoconnect.network_interface.DialogListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ApproveFromPage extends DialogFragment implements DialogListener {

    /*
     * Initialize the XML element or views
     * */
    private ApproveList approveList;
    private TextView tv_View_leavetype,tv_View_applieddate,tv_View_fromdate,tv_View_todate,tv_View_no_of_counts,tv_View_remarks,tv_View_leavestatus,tv_View_approveddate,tv_View_remarksbyapprover,tv_View_approver;
    private TextView labelid,labelname,label1,label2,label3,label4;
    private Button accept,reject;
    private TableLayout tableLayout;
    private AddRequest addRequest= new AddRequest();
    private static String MYPREFS_NAME = "logininfo";
    private ArrayList<LeaveDetails> leaveBalanceDetail = new ArrayList<>();
    private View view;
    private HorizontalScrollView scrollView;
    public static String TAG = "ApproveFromPage";
    private ApprovePost approvePost = new ApprovePost();
    private AddRequest request = new AddRequest();
    private DialogListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener = (DialogListener) getTargetFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_leave_details, container, false);

        /*
         * Retrieve the Intent Value
         * */
        String value = this.getArguments().getString("leave");
        Gson gson = new Gson();
        approveList = gson.fromJson(value, ApproveList.class);

        String value1 = this.getArguments().getString("email");
        request = gson.fromJson(value1,AddRequest.class);

        /*
         * Get the Header
         * */
        SharedPreferences preferences = getActivity().getSharedPreferences(MainApplication.MY_PREFS_NAME, MODE_PRIVATE);
        if (preferences != null) {
            MainApplication.token_data = preferences.getString("header", "No name defined");
        }

        /*
         * Get the shared preference data to assign the another object..
         * */
        SharedPreferences prefs = getActivity().getSharedPreferences(MYPREFS_NAME, MODE_PRIVATE);
        if(prefs!=null) {
            addRequest.setEmail(prefs.getString("Email", "No name defined"));
            addRequest.setCompanyCode(prefs.getString("CompanyCode", "No name defined"));
            addRequest.setEmployeeID(prefs.getString("EmployeeID", "No name defined"));
        }

        /*
        * Define the Toolbar and set title
        * set navigation operation
        * */
        Toolbar view_toolbar = view.findViewById(R.id.main_withnav_toolbar);
        view_toolbar.setTitle("LEAVE SUMMARY DETAILS");
        view_toolbar.setNavigationIcon(R.drawable.close);
        view_toolbar.setNavigationOnClickListener(v -> dismiss());

        /*
         * Initialize the UI elements
         * */
        tv_View_leavetype = view.findViewById(R.id.et_View_leavetype);
        tv_View_applieddate = view.findViewById(R.id.et_View_applieddate);
        tv_View_fromdate = view.findViewById(R.id.et_View_fromdate);
        tv_View_todate = view.findViewById(R.id.et_View_todate);
        tv_View_no_of_counts = view.findViewById(R.id.et_View_no_of_counts);
        tv_View_remarks = view.findViewById(R.id.et_View_remarks);
        tv_View_leavestatus = view.findViewById(R.id.et_View_status);
        tv_View_approveddate = view.findViewById(R.id.et_View_approveddate);
        tv_View_remarksbyapprover = view.findViewById(R.id.et_View_remarksbyapprover);
        tv_View_approver = view.findViewById(R.id.et_View_approver);
        labelid = view.findViewById(R.id.tv_View_type);
        labelname = view.findViewById(R.id.tv_View_applieddate);
        accept = view.findViewById(R.id.btn_accept);
        reject = view.findViewById(R.id.btn_reject);
        tableLayout = view.findViewById(R.id.tableLayout);
        label1 = view.findViewById(R.id.tv_View_status);
        label2 = view.findViewById(R.id.tv_View_approveddate);
        label3 = view.findViewById(R.id.tv_View_remarksbyapprover);
        label4 = view.findViewById(R.id.tv_View_approver);
        scrollView = view.findViewById(R.id.scroll);

        approvePost.setEmployeeCode(request.getEmployeeID());
        approvePost.setEmail(request.getEmail());
        approvePost.setLeaveRequestID(approveList.getLeaveRequestStatusID());

        /*
        * Show and Hide the Some unwanted views in XML
        * */
        tv_View_leavestatus.setVisibility(View.GONE);
        tv_View_approveddate.setVisibility(View.GONE);
        tv_View_remarksbyapprover.setVisibility(View.GONE);
        tv_View_approver.setVisibility(View.GONE);
        label1.setVisibility(View.GONE);
        label2.setVisibility(View.GONE);
        label3.setVisibility(View.GONE);
        label4.setVisibility(View.GONE);
        accept.setVisibility(View.VISIBLE);
        reject.setVisibility(View.VISIBLE);
        tableLayout.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.VISIBLE);

        accept.setOnClickListener(v -> {
            approvePost.setApproveStatus("Approved");
            get_remarks();
        });

        reject.setOnClickListener(v -> {
            approvePost.setApproveStatus("Rejected");
            get_remarks();
        });

        getTableData();

        /*
        * Assign the value for the UI elements
        * */
        labelid.setText("EMPLOYEE ID");
        labelname.setText("EMPLOYEE NAME");
        tv_View_leavetype.setText(approveList.getEmployeeID());
        tv_View_applieddate.setText(approveList.getFirstName());
        tv_View_fromdate.setText(approveList.getStrFromDate());
        tv_View_todate.setText(approveList.getStrToDate());
        tv_View_no_of_counts.setText(""+approveList.getNo_of_days());
        tv_View_remarks.setText(approveList.getRequestRemarks());

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

    /*
    * Method to Design the Table view in Approve leave
    * */
    private void setTableData() {
        TableLayout stk =  view.findViewById(R.id.tableLayout);
        TableRow tbrow0 = new TableRow(getActivity());
        TextView tv0 = new TextView(getActivity());
        tv0.setText(" Type ");
        tv0.setTypeface(null, Typeface.BOLD);
        tv0.setGravity(Gravity.CENTER);
        tv0.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        tv0.setTextColor(Color.BLACK);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(getActivity());
        tv1.setText(" Taken ");
        tv1.setGravity(Gravity.CENTER);
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        tv1.setTextColor(Color.BLACK);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(getActivity());
        tv2.setText(" Availability ");
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        tv2.setTypeface(null, Typeface.BOLD);
        tv2.setTextColor(Color.BLACK);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(getActivity());
        tv3.setText(" Applied ");
        tv3.setGravity(Gravity.CENTER);
        tv3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        tv3.setTypeface(null, Typeface.BOLD);
        tv3.setTextColor(Color.BLACK);
        tbrow0.addView(tv3);
        stk.addView(tbrow0);
        for (int i = 0; i < leaveBalanceDetail.size(); i++) {
            TableRow tbrow = new TableRow(getActivity());
            TextView t1v = new TextView(getActivity());
            t1v.setText("" + leaveBalanceDetail.get(i).getLeaveTypeText());
            t1v.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
            t1v.setTextColor(Color.BLACK);
            t1v.setPadding(4,4,7,4);
            tbrow.addView(t1v);
            TextView t2v = new TextView(getActivity());
            t2v.setText("" + leaveBalanceDetail.get(i).getLeaveTaken());
            t2v.setTextColor(Color.BLACK);
            t2v.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);
            TextView t3v = new TextView(getActivity());
            t3v.setText("" + leaveBalanceDetail.get(i).getLeaveAvailability());
            t3v.setTextColor(Color.BLACK);
            t3v.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);
            TextView t4v = new TextView(getActivity());
            t4v.setText("" + leaveBalanceDetail.get(i).getAppliedLeave());
            t4v.setTextColor(Color.BLACK);
            t4v.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);
            stk.addView(tbrow);
        }
    }

    /*
    * Method to assign the Table Content in ArrayList
    * */
    private void getTableData() {
        leaveBalanceDetail = new ArrayList<>();
        ApiManager.getInstance().getLeaveBalance(addRequest, new Callback<GetLeaveBalance>() {
            @Override
            public void onResponse(Call<GetLeaveBalance> call, Response<GetLeaveBalance> response) {
                if(response.isSuccessful() && response.body()!=null){
                    leaveBalanceDetail = response.body().getData().getLeaveDetails();
                    if(leaveBalanceDetail.size()>0) {
                        setTableData();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetLeaveBalance> call, Throwable t) {
                Log.e(TAG,t.getLocalizedMessage());
            }
        });
    }

    private void get_remarks(){
        Gson gson = new Gson();
        String value = gson.toJson(approvePost);
        Bundle bundle = new Bundle();
        bundle.putString("Submit",value);
        Remarks remarks = new Remarks();
        remarks.setTargetFragment(this, 0);
        assert getFragmentManager() != null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        remarks.setArguments(bundle);
        remarks.show(transaction, Remarks.TAG);
    }

    @Override
    public void onDialogClick(String value) {
        if(value.equals("Success")){
            dismiss();
            replaceFragment();
        }
    }

    private void replaceFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.approve_frame, new ApproveLeaveFromGrid());
        transaction.replace(R.id.leave_frame,new Leave());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
