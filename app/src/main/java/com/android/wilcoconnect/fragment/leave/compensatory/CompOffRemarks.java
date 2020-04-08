package com.android.wilcoconnect.fragment.leave.compensatory;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.api.ApiManager;
import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.model.common.Success;
import com.android.wilcoconnect.model.leave.compensatory.CompOffApprovePost;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class CompOffRemarks extends DialogFragment {

    public static final String TAG = "CompOffRemarks";
    private EditText et_remarks;
    private AddRequest addRequest = new AddRequest();
    private static String MYPREFS_NAME = "logininfo";
    private Button submit;
    private CompOffApprovePost post = new CompOffApprovePost();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_remarks, container, false);

        /*
         * Retrieve the Intent Value
         * */
        String value = this.getArguments().getString("SubmitCompOff");
        Gson gson = new Gson();
        post = gson.fromJson(value, CompOffApprovePost.class);

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
        if (prefs != null) {
            addRequest.setEmail(prefs.getString("Email", "No name defined"));
            addRequest.setCompanyCode(prefs.getString("CompanyCode", "No name defined"));
            addRequest.setEmployeeID(prefs.getString("EmployeeID", "No name defined"));
        }

        et_remarks = view.findViewById(R.id.et_remarks);
        submit = view.findViewById(R.id.btn_submit);

        submit.setOnClickListener(v -> {
            if (et_remarks.getText().equals("")) {
                et_remarks.setError("Enter the Valid Remarks");
            } else {
                et_remarks.setError(null);
                post.setApproverRemarks(et_remarks.getText().toString());

                ApiManager.getInstance().storeCompOffApprove(post, new Callback<Success>() {
                    @Override
                    public void onResponse(Call<Success> call, Response<Success> response) {
                        if(response.isSuccessful() && response.body()!=null && response.body().getStatus().equals("true")){
                            dismiss();
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
