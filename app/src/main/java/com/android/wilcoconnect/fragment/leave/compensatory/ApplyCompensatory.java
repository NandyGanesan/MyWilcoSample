package com.android.wilcoconnect.fragment.leave.compensatory;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.api.ApiManager;
import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.model.common.Success;
import com.android.wilcoconnect.model.leave.compensatory.CompOffPost;
import com.android.wilcoconnect.model.leave.compensatory.GetCompOffDays;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.android.wilcoconnect.network_interface.DialogListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ApplyCompensatory extends DialogFragment {

    /*
     * Initialize the variables to access the Module
     * */
    private Button btn_from_date, btn_to_date,btn_clear,btn_submit;
    public static final String TAG = "ApplyCompensatory";
    private ImageView iv_from_date, iv_to_date;
    private EditText et_remarks;
    private TextView tv_no_of_days_count, tv_date_error,content1,content2;
    private int fromYear,fromMonth,fromDay,toYear,toMonth,toDay;
    private AddRequest addRequest= new AddRequest();
    private static String MYPREFS_NAME = "logininfo";
    private CompOffPost post = new CompOffPost();
    private String from_date,to_date;
    private GetCompOffDays.Data data;
    private DialogListener listener;
    private String value;
    private Calendar cal;

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
        View view = inflater.inflate(R.layout.fragment_apply_compensatory, container, false);

        /*
         * Assign the Values for the Particular View Elements
         * */
        btn_from_date = view.findViewById(R.id.btn_fromdate);
        btn_to_date = view.findViewById(R.id.btn_todate);
        btn_clear = view.findViewById(R.id.btn_clear);
        btn_submit = view.findViewById(R.id.btn_submit);
        iv_from_date = view.findViewById(R.id.iv_fromdate);
        iv_to_date = view.findViewById(R.id.iv_todate);
        et_remarks = view.findViewById(R.id.et_remarks);
        tv_no_of_days_count = view.findViewById(R.id.tv_noofdayscount);
        tv_date_error = view.findViewById(R.id.tv_dateerror);
        content1 = view.findViewById(R.id.content1);
        content2 = view.findViewById(R.id.content2);

        /*
         * Define the ToolBar
         * */
        Toolbar detail_toolbar = view.findViewById(R.id.main_withnav_toolbar);
        detail_toolbar.setTitle("ADD COMP-OFF");
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

        /*
        * API Call to get the Number of Days
        * */
        ApiManager.getInstance().getDays(addRequest, new Callback<GetCompOffDays>() {
            //API Success
            @Override
            public void onResponse(Call<GetCompOffDays> call, Response<GetCompOffDays> response) {
                if(response.body()!=null && response.isSuccessful()){
                    data = response.body().getData();
                    content1.setText("1.You have to Credit Before " + data.getCreateBefore() +" Days.");
                    content2.setText("2.You Should Avail Before " + data.getAvailBefore() +" Days.");
                }
            }
            //API Failure
            @Override
            public void onFailure(Call<GetCompOffDays> call, Throwable t) {
                Log.e(TAG,t.getLocalizedMessage());
            }
        });

         /*
         * Select the From Date
         * */
        iv_from_date.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            fromYear = c.get(Calendar.YEAR);
            fromMonth = c.get(Calendar.MONTH);
            fromDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (view1, year, monthOfYear, dayOfMonth) -> {
                int value = analyse_day(dayOfMonth,monthOfYear,year);
                if(value==1) {
                    btn_from_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    from_date = (year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    if (btn_to_date.getText().toString() != "" && btn_from_date.getText().toString() != "" &&
                            btn_to_date.getText().toString() != null && btn_from_date.getText().toString() != null) {
                        getCount();
                    }
                }
                else {
                    alert_msg();
                }
            }, fromYear, fromMonth, fromDay);
            cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -data.getCreateBefore());
            datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
            cal = Calendar.getInstance();
            datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
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
                int value = analyse_day(dayOfMonth,monthOfYear,year);
                if(value==1) {
                    to_date = (year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    btn_to_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    if (btn_to_date.getText().toString() != "" && btn_from_date.getText().toString() != ""
                            && btn_to_date.getText().toString() != null && btn_from_date.getText().toString() != null) {
                        getCount();
                    }
                }
                else {
                    alert_msg();
                }
            }, toYear, toMonth, toDay);
            cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -data.getCreateBefore());
            datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
            cal = Calendar.getInstance();
            datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
            datePickerDialog.show();
        });

        /*
         * Clear all the Field values
         * */
        btn_clear.setOnClickListener(v -> {
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
            if(btn_from_date.getText().toString().equals("")||btn_to_date.getText().toString().equals("")||et_remarks.getText().toString().equals(""))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Error:");
                if(btn_from_date.getText().toString().equals("")){
                    builder.setMessage("Select the valid From Date");
                }
                else  if(btn_to_date.getText().toString().equals("")){
                    builder.setMessage("Select the valid To Date");
                }
                else  if(et_remarks.getText().toString().equals("")){
                    builder.setMessage("Enter the valid Description");
                }
                builder.setPositiveButton("OK",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else{
                post.setEmail(addRequest.getEmail());
                post.setEmployeeCode(addRequest.getEmployeeID());
                post.setEmpCompensatoryID(0);
                post.setFromDate(from_date);
                post.setToDate(to_date);
                post.setNoofDays(Integer.parseInt(tv_no_of_days_count.getText().toString()));
                post.setRequestRemarks(et_remarks.getText().toString());

                /*
                * Store the new Comp-Off Detail
                * */
                ApiManager.getInstance().storeCompOffDetail(post, new Callback<Success>() {
                    //API Success
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
                            btn_from_date.setText("");
                            iv_from_date.setEnabled(false);
                            btn_to_date.setText("");
                            iv_to_date.setEnabled(false);
                            tv_date_error.setVisibility(View.GONE);
                            tv_no_of_days_count.setText("");
                            et_remarks.setText("");
                        }
                        //Data Stored Failure
                        else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle(response.body().getMessage());
                            builder.setPositiveButton("Ok",null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                    //API Failure
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
    * When the Days not a Holiday Then display the alert message.
    * */
    private void alert_msg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Error Message : ");
        builder.setMessage("Please select the dates in Holidays or Weekends.");
        builder.setPositiveButton("Ok", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /*
    * Analyse the Day is Holiday or not
    * */
    private int analyse_day(int dayOfMonth,int monthOfYear,int year) {
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth); // Note that Month value is 0-based. e.g., 0 for January.
        int result = calendar.get(Calendar.DAY_OF_WEEK);
        int value=0;
        switch (result) {
            case Calendar.MONDAY:
                value=0;
                break;
            case Calendar.TUESDAY:
                value=0;
                break;
            case Calendar.WEDNESDAY:
                value=0;
                break;
            case Calendar.THURSDAY:
                value=0;
                break;
            case Calendar.FRIDAY:
                value=0;
                break;
            case Calendar.SATURDAY:
                value=1;
                break;
            case Calendar.SUNDAY:
                value=1;
                break;
        }
        return value;
    }

    /*
     * Count the Number of days from the from and to date
     * */
    private void getCount(){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
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