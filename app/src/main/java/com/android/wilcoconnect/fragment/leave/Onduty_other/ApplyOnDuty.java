package com.android.wilcoconnect.fragment.leave.Onduty_other;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.api.ApiManager;
import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.model.common.Success;
import com.android.wilcoconnect.model.leave.ApplyLeavePost;
import com.android.wilcoconnect.model.leave.LeaveType;
import com.android.wilcoconnect.model.leave.Onduty.OnDutyMasterData;
import com.android.wilcoconnect.model.leave.Onduty.OnDutyPost;
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

public class ApplyOnDuty extends DialogFragment {

    /*
     * Initialize the variables to access the Module
     * */
    public static final String TAG = "ApplyOnDuty";
    private String[] Type;
    private Button btn_Type, btn_from_date, btn_to_date,btn_clear,btn_submit;
    private ImageView iv_from_date, iv_to_date;
    private EditText et_remarks;
    private TextView tv_no_of_days_count, tv_date_error;
    private int checkItem =0;
    private int fromYear,fromMonth,fromDay,toYear,toMonth,toDay;
    private AddRequest addRequest= new AddRequest();
    private static String MYPREFS_NAME = "logininfo";
    private OnDutyMasterData onDutyType = new OnDutyMasterData();
    private ArrayList<OnDutyMasterData.Data> type = new ArrayList<>();
    private OnDutyPost post = new OnDutyPost();
    private String from_date,to_date;
    private DialogListener listener;
    private String value;

    /*
     * Define the OnCreate method to set the Fragment to the Particular Listener
     * */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener = (DialogListener) getTargetFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_apply_on_duty, container, false);

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
        * Define the Toolbar
        * */
        Toolbar detail_toolbar = view.findViewById(R.id.main_withnav_toolbar);
        detail_toolbar.setTitle("APPLY ON-DUTY");
        detail_toolbar.setNavigationIcon(R.drawable.close);
        detail_toolbar.setNavigationOnClickListener(v -> dismiss());

        /*
        * API Call to get the Dropdown list
        * */
        ApiManager.getInstance().getMasterList(addRequest, new Callback<OnDutyMasterData>() {
            @Override
            public void onResponse(Call<OnDutyMasterData> call, Response<OnDutyMasterData> response) {
                onDutyType = response.body();
                if(onDutyType!=null && response.isSuccessful()){
                    type = onDutyType.getData();
                    get_dropdown_value();
                }
            }

            @Override
            public void onFailure(Call<OnDutyMasterData> call, Throwable t) {
                Log.e(TAG,t.getLocalizedMessage());
            }
        });

        /*
         * Assign the Values for the Particular View Elements
         * */
        btn_Type = view.findViewById(R.id.btn_type);
        btn_from_date = view.findViewById(R.id.btn_fromdate);
        btn_to_date = view.findViewById(R.id.btn_todate);
        btn_clear = view.findViewById(R.id.btn_clear);
        btn_submit = view.findViewById(R.id.btn_submit);
        iv_from_date = view.findViewById(R.id.iv_fromdate);
        iv_to_date = view.findViewById(R.id.iv_todate);
        et_remarks = view.findViewById(R.id.et_reason);
        tv_no_of_days_count = view.findViewById(R.id.tv_noofdayscount);
        tv_date_error = view.findViewById(R.id.tv_dateerror);

        /*
         * Select the Particular leave type to enable the From and To date
         * */
        btn_Type.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Select the Leave Type:");
            builder.setItems(Type, (dialog, which) -> {
                checkItem = which;
                btn_Type.setText(Type[which]);
                post.setOnDutyID(type.get(which).getOnDutyID());
                iv_from_date.setEnabled(true);
                iv_to_date.setEnabled(true);
            }).setNegativeButton("Cancel", (dialog, which) -> {
                checkItem=-1;
                if (checkItem < 0) {
                    btn_Type.setText("--- SELECT ---");
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
        if(btn_Type.getText().toString().equals("--- SELECT ---")){
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

        /*
         * Clear all the Field values
         * */
        btn_clear.setOnClickListener(v -> {
            btn_Type.setText("--- SELECT ---");
            btn_from_date.setText("");
            iv_from_date.setEnabled(false);
            btn_to_date.setText("");
            iv_to_date.setEnabled(false);
            tv_date_error.setVisibility(View.GONE);
            tv_no_of_days_count.setText("");
            et_remarks.setText("");
        });

        /*
         * When submit the leave request
         * */
        btn_submit.setOnClickListener(v -> {
            if(btn_Type.getText().toString().equals("--- SELECT ---")||
                    btn_from_date.getText().toString().equals("")||
                    btn_to_date.getText().toString().equals("")||
                    et_remarks.getText().toString().equals(""))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Error:");
                if (btn_Type.getText().toString().equals("--- SELECT ---")) {
                    builder.setMessage("Select the valid Type");
                }
                else  if(btn_from_date.getText().toString().equals("")){
                    builder.setMessage("Select the valid From Date");
                }
                else  if(btn_to_date.getText().toString().equals("")){
                    builder.setMessage("Select the valid To Date");
                }
                else  if(et_remarks.getText().toString().equals("")){
                    builder.setMessage("Enter the valid Reason");
                }
                builder.setPositiveButton("OK",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else{
                post.setEmail(addRequest.getEmail());
                post.setEmployeeCode(addRequest.getEmployeeID());
                post.setFromDate(from_date);
                post.setToDate(to_date);
                post.setNoofDays(Integer.parseInt(tv_no_of_days_count.getText().toString()));
                post.setReason(et_remarks.getText().toString());
                post.setWorkFromHomeEmployeeRequestID(0);

                /*
                * API Call to store the New Request
                * */
                ApiManager.getInstance().storeOnDuty(post, new Callback<Success>() {
                    //API Call Success
                    @Override
                    public void onResponse(Call<Success> call, Response<Success> response) {
                        assert response.body() != null;
                        //Data Stored Success
                        if(response.isSuccessful() && response.body().getStatus().equals("true") && response.body().getMessage().equals("successfully Stored")){
                            dismiss();
                            value = "Success";
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle(response.body().getMessage());
                            builder.setPositiveButton("Ok", (dialog, which) -> listener.onDialogClick(value));
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            btn_Type.setText("--- SELECT ---");
                            btn_from_date.setText("");
                            iv_from_date.setEnabled(false);
                            btn_to_date.setText("");
                            iv_to_date.setEnabled(false);
                            tv_date_error.setVisibility(View.GONE);
                            tv_no_of_days_count.setText("");
                            et_remarks.setText("");
                        }
                        //Data Stored Failure
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle(response.body().getMessage());
                            builder.setPositiveButton("Ok",null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                    //API Call Failure
                    @Override
                    public void onFailure(Call<Success> call, Throwable t) {
                        Log.e(TAG,t.getLocalizedMessage());
                    }
                });
            }
        });

        return view;
    }

    /*
    * Get the DropDown Data
    * */
    private void get_dropdown_value() {
        if(type.size()>0) {
            Type = new String[type.size()];
            for (int i = 0; i < type.size(); i++) {
                Type[i] = type.get(i).getOnDutyName();
            }
        }
    }

    /*
     * Count the Number of days from the from and to date
     * */
    private void getCount(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date from_date = simpleDateFormat.parse(btn_from_date.getText().toString());
            Date to_date = simpleDateFormat.parse(btn_to_date.getText().toString());

            assert from_date != null;
            assert to_date != null;
            if(from_date.after(to_date) || to_date.before(from_date)){
                tv_date_error.setText("From Date must Lower than the To Date");
                tv_date_error.setVisibility(View.VISIBLE);
            }
            else{
                tv_date_error.setVisibility(View.GONE);
                long different = to_date.getTime() - from_date.getTime();
                long daysInMilli = 1000 * 60 * 60* 24;
                long elapsedDays = different / daysInMilli;
                tv_no_of_days_count.setText(Integer.toString((int)(elapsedDays+1)));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
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