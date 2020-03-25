package com.android.wilcoconnect.fragment.leave;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.api.ApiManager;
import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.model.common.Success;
import com.android.wilcoconnect.model.leave.ApplyLeavePost;
import com.android.wilcoconnect.model.leave.LeaveType;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ApplyLeave extends Fragment {

    /*
     * Initialize the variables to access the Module
     * */
    private String TAG = "ApplyLeave";
    private String[] LeaveType;
    private Button btn_leaveType, btn_from_date, btn_to_date,btn_clear,btn_submit;
    private ImageView iv_from_date, iv_to_date;
    private EditText et_remarks;
    private TextView tv_no_of_days_count, tv_date_error;
    private int checkItem =0;
    private int fromYear,fromMonth,fromDay,toYear,toMonth,toDay;
    private RadioGroup fullandhalf,mrngandevening;
    private AddRequest addRequest= new AddRequest();
    private static String MYPREFS_NAME = "logininfo";
    private LeaveType leaveType = new LeaveType();
    private String leavelevel;
    private ArrayList<LeaveType.Data> type = new ArrayList<>();
    private ApplyLeavePost leavepost = new ApplyLeavePost();

    //Session: F->Fullday, A->After Noon,M ->Morning

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_apply_leave, container, false);

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

        ApiManager.getInstance().getLeaveType(addRequest, new Callback<LeaveType>() {
            @Override
            public void onResponse(Call<LeaveType> call, Response<LeaveType> response) {
                leaveType = response.body();
                if(leaveType!=null && response.isSuccessful()){
                    type = leaveType.getData();
                    if(type.size()>0){
                        get_dropdown_value();
                    }
                }
            }

            @Override
            public void onFailure(Call<LeaveType> call, Throwable t) {
                Log.e(TAG,t.getLocalizedMessage());
            }
        });

        /*
         * Assign the Values for the Particular View Elements
         * */
        btn_leaveType = view.findViewById(R.id.btn_leavetype);
        btn_from_date = view.findViewById(R.id.btn_fromdate);
        btn_to_date = view.findViewById(R.id.btn_todate);
        btn_clear = view.findViewById(R.id.btn_clear);
        btn_submit = view.findViewById(R.id.btn_submit);
        iv_from_date = view.findViewById(R.id.iv_fromdate);
        iv_to_date = view.findViewById(R.id.iv_todate);
        et_remarks = view.findViewById(R.id.et_remarks);
        tv_no_of_days_count = view.findViewById(R.id.tv_noofdayscount);
        tv_date_error = view.findViewById(R.id.tv_dateerror);
        fullandhalf = view.findViewById(R.id.radioGroupleave);
        mrngandevening = view.findViewById(R.id.radioGroup);
        mrngandevening.setVisibility(View.GONE);

        /*
        * Select the Particular leave type to enable the From and To date
        * */
        btn_leaveType.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Select the Leave Type:");
            builder.setItems(LeaveType, (dialog, which) -> {
                checkItem = which;
                btn_leaveType.setText(LeaveType[which]);
                leavepost.setLeaveTypeID(type.get(which).getLeaveTypeID());
                iv_from_date.setEnabled(true);
                iv_to_date.setEnabled(true);
            }).setNegativeButton("Cancel", (dialog, which) -> {
                checkItem=-1;
                if (checkItem < 0) {
                    btn_leaveType.setText("--- SELECT ---");
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

                btn_to_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                if(btn_to_date.getText().toString()!=""&& btn_from_date.getText().toString()!=""
                        && btn_to_date.getText().toString()!=null && btn_from_date.getText().toString()!=null){
                    getCount();
                }
            }, toYear, toMonth, toDay);
            datePickerDialog.show();
        });

        fullandhalf.setOnCheckedChangeListener((group, checkedId) -> {
            int radioButtonID = fullandhalf.getCheckedRadioButtonId();
            View radioButton = fullandhalf.findViewById(radioButtonID);
            int idx = fullandhalf.indexOfChild(radioButton);
            if(idx==0){
                tv_no_of_days_count.setText(""+1);
                mrngandevening.setVisibility(View.GONE);
            }
            else {
                mrngandevening.setVisibility(View.VISIBLE);
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
            mrngandevening.setVisibility(View.GONE);
            fullandhalf.setVisibility(View.GONE);
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
                get_radiobutton_value();
                leavepost.setEmail(addRequest.getEmail());
                leavepost.setEmployeeCode(addRequest.getEmployeeID());
                leavepost.setFromDate(btn_from_date.getText().toString());
                leavepost.setToDate(btn_to_date.getText().toString());
                leavepost.setNoofDays(Integer.parseInt(tv_no_of_days_count.getText().toString()));
                leavepost.setRequestRemarks(et_remarks.getText().toString());
                leavepost.setSession(leavelevel);
                leavepost.setLeaveRequestID(0);

                ApiManager.getInstance().storeleavedetail(leavepost, new Callback<Success>() {
                    @Override
                    public void onResponse(Call<Success> call, Response<Success> response) {
                        assert response.body() != null;
                        if(response.isSuccessful() && response.body().getStatus().equals("true")){
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Data Submitted Successfully..!!");
                            builder.setPositiveButton("Ok",null);
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
                            mrngandevening.setVisibility(View.GONE);
                            fullandhalf.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<Success> call, Throwable t) {
                        Log.e(TAG,t.getLocalizedMessage());
                    }
                });
            }
        });

        return view;
    }

    private void get_radiobutton_value() {
        int radioButtonID = fullandhalf.getCheckedRadioButtonId();
        View radioButton = fullandhalf.findViewById(radioButtonID);
        int idx = fullandhalf.indexOfChild(radioButton);
        if(idx==0){
            leavelevel = "F";
        }
        else {
            int radio = mrngandevening.getCheckedRadioButtonId();
            View radioButtons = mrngandevening.findViewById(radio);
            int index = mrngandevening.indexOfChild(radioButtons);
            if(index==0){
                leavelevel = "M";
            }
            else {
                leavelevel = "A";
            }
        }
    }

    private void get_dropdown_value() {
        if(type.size()>0) {
            LeaveType = new String[type.size()];
            for (int i = 0; i < type.size(); i++) {
                LeaveType[i] = type.get(i).getLeaveType();
            }
        }
    }

    /*
    * Count the Number of days from the from and to date
    * */
    private void getCount(){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy");
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
                   long daysInMilli = 1000 * 60 * 60* 24;                   long elapsedDays = different / daysInMilli;
                   tv_no_of_days_count.setText(Integer.toString((int)(elapsedDays+1)));
                   if(elapsedDays+1 == 1){
                       fullandhalf.setVisibility(View.VISIBLE);
                       mrngandevening.setVisibility(View.GONE);
                   }
               }
            } catch (ParseException e) {
                e.printStackTrace();
            }
    }
}