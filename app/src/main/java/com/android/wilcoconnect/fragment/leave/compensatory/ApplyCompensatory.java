package com.android.wilcoconnect.fragment.leave.compensatory;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

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
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ApplyCompensatory extends Fragment {

    /*
     * Initialize the variables to access the Module
     * */
    private String TAG = "ApplyCompensatory";
    private Button btn_from_date, btn_to_date,btn_clear,btn_submit;
    private ImageView iv_from_date, iv_to_date;
    private EditText et_remarks;
    private TextView tv_no_of_days_count, tv_date_error;
    private int fromYear,fromMonth,fromDay,toYear,toMonth,toDay;
    private AddRequest addRequest= new AddRequest();
    private static String MYPREFS_NAME = "logininfo";
    private CompOffPost post = new CompOffPost();
    private String from_date,to_date;

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

                ApiManager.getInstance().storeCompOffDetail(post, new Callback<Success>() {
                    @Override
                    public void onResponse(Call<Success> call, Response<Success> response) {
                        assert response.body() != null;
                        if(response.isSuccessful() && response.body().getStatus().equals("true")){
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle(response.body().getMessage());
                            builder.setPositiveButton("Ok",null);
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
                        else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle(response.body().getMessage());
                            builder.setPositiveButton("Ok",null);
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
        });
        return view;
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
}