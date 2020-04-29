package com.android.wilcoconnect.fragment.leave;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.api.ApiManager;
import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.model.common.Success;
import com.android.wilcoconnect.model.leave.ApplyLeavePost;
import com.android.wilcoconnect.model.leave.leavebalance.GetLeaveBalance;
import com.android.wilcoconnect.model.leave.leavebalance.LeaveDetails;
import com.android.wilcoconnect.model.leave.leavebalance.LeaveTypeDetails;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.android.wilcoconnect.network_interface.DialogListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ApplyLeave extends DialogFragment {

    /*
     * Initialize the variables to access the Module
     * */
    private String[] LeaveType;
    private Button btn_leaveType, btn_from_date, btn_to_date,btn_clear,btn_submit;
    private ImageView iv_from_date, iv_to_date;
    private EditText et_remarks;
    private TextView tv_no_of_days_count, tv_date_error;
    private int checkItem =0;
    private int fromYear,fromMonth,fromDay,toYear,toMonth,toDay;
    private RadioGroup full_half, mrng_evening;
    private AddRequest addRequest= new AddRequest();
    private static String MYPREFS_NAME = "logininfo";
    private String leavelevel;
    private ApplyLeavePost leavepost = new ApplyLeavePost();
    private String from_date,to_date;
    public static final String TAG = "ApplyLeave";
    private ArrayList<LeaveDetails> leaveBalanceDetail = new ArrayList<>();
    private ArrayList<LeaveTypeDetails> leaveTypeDetail = new ArrayList<>();
    private int availableBalance=0;
    private DialogListener listener;
    private String value;
    private String LeaveTypeName;
    private LinearLayout balanceFrame;
    private TextView title,content;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         listener = (DialogListener) getTargetFragment();
    }

    //Session: F->Fullday, A->After Noon,M ->Morning

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_apply_leave, container, false);

        balanceFrame = view.findViewById(R.id.leaveBalanceFrame);
        title = view.findViewById(R.id.tv_leaveType);
        content = view.findViewById(R.id.tv_leaveBalance);

        Toolbar detail_toolbar = view.findViewById(R.id.main_withnav_toolbar);
        detail_toolbar.setTitle("APPLY LEAVE");
        detail_toolbar.setNavigationIcon(R.drawable.close);
        detail_toolbar.setNavigationOnClickListener(v -> dismiss());

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

        ApiManager.getInstance().getLeaveBalance(addRequest, new Callback<GetLeaveBalance>() {
            @Override
            public void onResponse(Call<GetLeaveBalance> call, Response<GetLeaveBalance> response) {
                if(response.isSuccessful() && response.body()!=null){
                    leaveBalanceDetail = response.body().getData().getLeaveDetails();
                    leaveTypeDetail = response.body().getData().getLeaveTypeDetails();
                    get_dropdown_value();
                }
            }

            @Override
            public void onFailure(Call<GetLeaveBalance> call, Throwable t) {
                Log.e(TAG,t.getLocalizedMessage());
            }
        });

        /*
         * Assign the Values for the Particular View Elements
         * */
        btn_leaveType = view.findViewById(R.id.btn_type);
        btn_from_date = view.findViewById(R.id.btn_fromdate);
        btn_to_date = view.findViewById(R.id.btn_todate);
        btn_clear = view.findViewById(R.id.btn_clear);
        btn_submit = view.findViewById(R.id.btn_submit);
        iv_from_date = view.findViewById(R.id.iv_fromdate);
        iv_to_date = view.findViewById(R.id.iv_todate);
        et_remarks = view.findViewById(R.id.et_remarks);
        tv_no_of_days_count = view.findViewById(R.id.tv_noofdayscount);
        tv_date_error = view.findViewById(R.id.tv_dateerror);
        full_half = view.findViewById(R.id.radioGroupleave);
        mrng_evening = view.findViewById(R.id.radioGroup);
        mrng_evening.setVisibility(View.GONE);

        /*
        * Select the Particular leave type to enable the From and To date
        * */
        btn_leaveType.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Select the Leave Type:");
            builder.setItems(LeaveType, (dialog, which) -> {
                checkItem = which;
                btn_leaveType.setText(LeaveType[which]);
                LeaveTypeName = LeaveType[which];
                display_balance();
                leavepost.setLeaveTypeID(leaveTypeDetail.get(which).getLeaveTypeID());
                iv_from_date.setEnabled(true);
                iv_to_date.setEnabled(true);
            }).setNegativeButton("Cancel", (dialog, which) -> {
                checkItem=-1;
                if (checkItem < 0) {
                    btn_leaveType.setText("--- SELECT ---");
                    balanceFrame.setVisibility(View.GONE);
                    iv_from_date.setEnabled(false);
                    iv_to_date.setEnabled(false);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        /*
        * Enable the From and To date
        * */
        if(btn_leaveType.getText().toString().equals("--- SELECT ---")){
            iv_from_date.setEnabled(false);
            iv_to_date.setEnabled(false);
        }
        else {
            iv_from_date.setEnabled(true);
            iv_to_date.setEnabled(true);
        }

        /*
        * Select the From Date
        * */
        iv_from_date.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            fromYear = c.get(Calendar.YEAR);
            fromMonth = c.get(Calendar.MONTH);
            fromDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (view1, year, monthOfYear, dayOfMonth) -> {
                   btn_from_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                   from_date = (year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                   if(btn_to_date.getText().toString()!="" && btn_from_date.getText().toString()!="" &&
                      btn_to_date.getText().toString()!=null && btn_from_date.getText().toString()!=null){
                         getCount();
                   }
            }, fromYear, fromMonth, fromDay);
            datePickerDialog.show();
        });


        /*
        * Select the To date
        * */
        iv_to_date.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            toYear = c.get(Calendar.YEAR);
            toMonth = c.get(Calendar.MONTH);
            toDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (view12, year, monthOfYear, dayOfMonth) -> {
                to_date = (year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                btn_to_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                if(btn_to_date.getText().toString()!=""&& btn_from_date.getText().toString()!=""
                        && btn_to_date.getText().toString()!=null && btn_from_date.getText().toString()!=null){
                    getCount();
                }
            }, toYear, toMonth, toDay);
            datePickerDialog.show();
        });

        full_half.setOnCheckedChangeListener((group, checkedId) -> {
            int radioButtonID = full_half.getCheckedRadioButtonId();
            View radioButton = full_half.findViewById(radioButtonID);
            int idx = full_half.indexOfChild(radioButton);
            if(idx==0){
                tv_no_of_days_count.setText(""+1);
                mrng_evening.setVisibility(View.GONE);
            }
            else {
                mrng_evening.setVisibility(View.VISIBLE);
                tv_no_of_days_count.setText(""+0.5);
            }
        });

        /*
        * Clear all the Field values
        * */
        btn_clear.setOnClickListener(v -> {
            btn_leaveType.setText("--- SELECT ---");
            btn_from_date.setText("");
            iv_from_date.setEnabled(false);
            btn_to_date.setText("");
            iv_to_date.setEnabled(false);
            tv_date_error.setVisibility(View.GONE);
            tv_no_of_days_count.setText("");
            et_remarks.setText("");
            balanceFrame.setVisibility(View.GONE);
            mrng_evening.setVisibility(View.GONE);
            full_half.setVisibility(View.GONE);
        });

        /*
        * When submit the leave request
        * */
        btn_submit.setOnClickListener(v -> {
            if(btn_leaveType.getText().toString().equals("--- SELECT ---")||
                btn_from_date.getText().toString().equals("")||
                btn_to_date.getText().toString().equals("")||
                et_remarks.getText().toString().equals(""))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Error:");
                if (btn_leaveType.getText().toString().equals("--- SELECT ---")) {
                    builder.setMessage("Select the valid Leave Type");
                }
                else  if(btn_from_date.getText().toString().equals("")){
                    builder.setMessage("Select the valid From Date");
                }
                else  if(btn_to_date.getText().toString().equals("")){
                    builder.setMessage("Select the valid To Date");
                }
                else  if(et_remarks.getText().toString().equals("")){
                    builder.setMessage("Enter the valid Remarks");
                }

                builder.setPositiveButton("OK",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else{
                if(availableBalance<Integer.parseInt(tv_no_of_days_count.getText().toString())){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("LOP?");
                    builder.setPositiveButton("YES", (dialog ,which)->{
                        store_value();
                    });
                    builder.setNegativeButton("NO", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    store_value();
                }
            }
        });
        return view;
    }

    private void store_value(){

        get_radiobutton_value();
        leavepost.setEmail(addRequest.getEmail());
        leavepost.setEmployeeCode(addRequest.getEmployeeID());
        leavepost.setFromDate(from_date);
        leavepost.setToDate(to_date);
        leavepost.setNoofDays(Integer.parseInt(tv_no_of_days_count.getText().toString()));
        leavepost.setRequestRemarks(et_remarks.getText().toString());
        leavepost.setSession(leavelevel);
        leavepost.setLeaveRequestID(0);

        ApiManager.getInstance().storeLeaveDetail(leavepost, new Callback<Success>() {
            @Override
            public void onResponse(Call<Success> call, Response<Success> response) {
                assert response.body() != null;
                if(response.isSuccessful() && response.body().getStatus().equals("true") && response.body().getMessage().equals("successfully Stored")){
                    dismiss();
                    value = "Success";
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(response.body().getMessage());
                    builder.setPositiveButton("Ok", (dialog, which) -> listener.onDialogClick(value));
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    btn_leaveType.setText("--- SELECT ---");
                    btn_from_date.setText("");
                    iv_from_date.setEnabled(false);
                    btn_to_date.setText("");
                    iv_to_date.setEnabled(false);
                    tv_date_error.setVisibility(View.GONE);
                    tv_no_of_days_count.setText("");
                    et_remarks.setText("");
                    balanceFrame.setVisibility(View.GONE);
                    mrng_evening.setVisibility(View.GONE);
                    full_half.setVisibility(View.GONE);
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(response.body().getMessage());
                    builder.setPositiveButton("Ok", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }

            @Override
            public void onFailure(Call<Success> call, Throwable t) {
                Log.e(TAG,t.getLocalizedMessage());
            }
        });
    }

    private void display_balance() {

        for(int i=0;i<leaveBalanceDetail.size();i++){
            if(leaveBalanceDetail.get(i).getLeaveTypeText().equals(LeaveTypeName)){
                title.setText(leaveBalanceDetail.get(i).getLeaveTypeText());
                content.setText("Eligible:  "+leaveBalanceDetail.get(i).getLeaveEligible()+"\n"
                        +"Taken:  "+leaveBalanceDetail.get(i).getLeaveTaken()+"\n"
                        +"Leave Not Approved:  "+leaveBalanceDetail.get(i).getAppliedLeave()+"\n"
                        +"Available:  "+leaveBalanceDetail.get(i).getLeaveAvailability());
                availableBalance = (int)leaveBalanceDetail.get(i).getLeaveAvailability();
                balanceFrame.setVisibility(View.VISIBLE);
            }
        }

    }

    private void get_radiobutton_value() {
        int radioButtonID = full_half.getCheckedRadioButtonId();
        View radioButton = full_half.findViewById(radioButtonID);
        int idx = full_half.indexOfChild(radioButton);
        if(idx==0){
            leavelevel = "F";
        }
        else {
            int radio = mrng_evening.getCheckedRadioButtonId();
            View radioButtons = mrng_evening.findViewById(radio);
            int index = mrng_evening.indexOfChild(radioButtons);
            if(index==0){
                leavelevel = "M";
            }
            else {
                leavelevel = "A";
            }
        }
    }

    private void get_dropdown_value() {
        if(leaveTypeDetail.size()>0) {
            LeaveType = new String[leaveTypeDetail.size()];
            for (int i = 0; i < leaveTypeDetail.size(); i++) {
                LeaveType[i] = leaveTypeDetail.get(i).getLeaveTypeText();
            }
        }
    }

    /*
    * Count the Number of days from the from and to date
    * */
    private void getCount(){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date fromdate = simpleDateFormat.parse(btn_from_date.getText().toString());
                Date todate = simpleDateFormat.parse(btn_to_date.getText().toString());

                assert fromdate != null;
                assert todate != null;
                if(fromdate.after(todate) || todate.before(fromdate)){
                  tv_date_error.setText("From Date must Lower than the To Date");
                  tv_date_error.setVisibility(View.VISIBLE);
               }
               else{
                   tv_date_error.setVisibility(View.GONE);
                   long different = todate.getTime() - fromdate.getTime();
                    long daysInMilli = 1000 * 60 * 60* 24;
                    long elapsedDays = different / daysInMilli;
                   tv_no_of_days_count.setText(Integer.toString((int)(elapsedDays+1)));
                   if(elapsedDays+1 == 1){
                       full_half.setVisibility(View.VISIBLE);
                       mrng_evening.setVisibility(View.GONE);
                   }
               }
            } catch (ParseException e) {
                e.printStackTrace();
            }
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