package com.android.wilcoconnect.fragment.leave;

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
import android.widget.TextView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.api.ApiManager;
import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.model.common.Success;
import com.android.wilcoconnect.model.leave.ApprovePost;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.android.wilcoconnect.network_interface.DialogListener;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class Remarks extends DialogFragment {

    /*
     * Initialize the variables to access the Module
     * */
    public static final String TAG = "Remarks";
    private EditText et_remarks;
    private AddRequest addRequest = new AddRequest();
    private static String MYPREFS_NAME = "logininfo";
    private Button submit;
    private ApprovePost post = new ApprovePost();
    private String result;
    private DialogListener listener;

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
        View view = inflater.inflate(R.layout.fragment_remarks, container, false);

        /*
         * Retrieve the Intent Value
         * */
        String value = this.getArguments().getString("Submit");
        Gson gson = new Gson();
        post = gson.fromJson(value, ApprovePost.class);

        /*
         * Define the ToolBar
         * */
        Toolbar detail_toolbar = view.findViewById(R.id.main_withnav_toolbar);
        detail_toolbar.setTitle("REMARKS");
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
         * Define the UI element
         * */
        et_remarks = view.findViewById(R.id.et_remarks);
        submit = view.findViewById(R.id.btn_submit);

        /*
        * Submit Button Click Action
        * */
        submit.setOnClickListener(v -> {
            if(et_remarks.getText().equals("")){
                et_remarks.setError("Enter the Valid Remarks");
            }
            else {
                et_remarks.setError(null);
                post.setApprovedRemarks(et_remarks.getText().toString());
                /*
                * Store the Details
                * */
                ApiManager.getInstance().storeApproveLeave(post, new Callback<Success>() {
                    //API Success
                    @Override
                    public void onResponse(Call<Success> call, Response<Success> response) {
                        if(response.isSuccessful() && response.body().getStatus().equals("true") && response.body().getMessage().equals("successfully Stored")){
                            dismiss();
                            result = "Success";
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle(response.body().getMessage());
                            builder.setPositiveButton("Ok",(dialog, which) -> listener.onDialogClick(result));
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
     * Dialog Window OnStart Method
     * */
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}