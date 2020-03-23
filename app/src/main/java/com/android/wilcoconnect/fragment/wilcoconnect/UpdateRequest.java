package com.android.wilcoconnect.fragment.wilcoconnect;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.api.ApiManager;
import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.android.wilcoconnect.model.wilcoconnect.IssueTracking;
import com.android.wilcoconnect.model.wilcoconnect.IssueTrackingData;
import com.android.wilcoconnect.model.wilcoconnect.Priority;
import com.android.wilcoconnect.model.wilcoconnect.RequestType;
import com.android.wilcoconnect.model.wilcoconnect.SendRequest;
import com.android.wilcoconnect.model.wilcoconnect.ServiceRequest;
import com.android.wilcoconnect.model.common.Success;
import com.google.gson.Gson;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission_group.CAMERA;
import static android.content.Context.MODE_PRIVATE;

public class UpdateRequest extends Fragment {

    private View view;
    private Button btn_support, btn_request, btn_priority, btn_create, attach_file, btn_update;
    private EditText summary, description, closure, latest;
    private TextView attach, status, tv_closure, tv_error, tv_latest, tv_attachments;
    private ImageView iv_attachments;
    private LinearLayout mylayout;
    private static final String MY_PREFS_NAME = "logininfo";
    private String TAG = "NewService";
    private int checkItem = -1;
    private List<IssueTrackingData> trackdata;
    private List<RequestType.Data> requestList;
    private List<Priority> priorityData;
    private SendRequest sendRequest = new SendRequest();
    private AddRequest newrequest = new AddRequest();
    private RequestType dropdowndata;
    private String[] prioritydd;
    private String update_statuscode = "S";
    private String cancel_statuscode = "CC";
    private String clarified_statuscode = "CF";
    private String reopen_statuscode = "RO";
    private String close_statuscode = "C";
    private static int RESULT_LOAD_FILE = 1;
    private IssueTracking data = new IssueTracking();
    private Toolbar updaterequest_toolbar;
    private ServiceRequest newServiceRequest = new ServiceRequest();
    private MediaType text = MediaType.parse("text/plain");
    private Fragment fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_newservice, container, false);

        updaterequest_toolbar = view.findViewById(R.id.main_withnav_toolbar);
        updaterequest_toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
        /*
         * Retrieve the Intent Value
         * */
        String value = this.getArguments().getString("data");
        Gson gson = new Gson();
        newServiceRequest = gson.fromJson(value, ServiceRequest.class);

        /*
         * Get the Shared Preference Data...To assign the another object
         * */
        final SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        /*
         *  Initialize the EditText,TextView and Button
         * */
        btn_support = view.findViewById(R.id.btn_support);
        btn_request = view.findViewById(R.id.btn_request);
        btn_priority = view.findViewById(R.id.btn_priority);
        btn_update = view.findViewById(R.id.btn_update);
        summary = view.findViewById(R.id.et_summary);
        description = view.findViewById(R.id.et_description);
        btn_create = view.findViewById(R.id.btn_create);
        attach_file = view.findViewById(R.id.btn_attach);
        attach = view.findViewById(R.id.tv_attach);
        status = view.findViewById(R.id.tv_status);
        closure = view.findViewById(R.id.et_closure);
        tv_closure = view.findViewById(R.id.tv_closure);
        tv_error = view.findViewById(R.id.tv_error);
        latest = view.findViewById(R.id.et_latest);
        tv_latest = view.findViewById(R.id.tv_latest);
        mylayout = view.findViewById(R.id.linearData);
        tv_attachments = view.findViewById(R.id.tv_attachments);
        iv_attachments = view.findViewById(R.id.iv_attachments);

        /*
         * set text for the current field
         * */
        status.setText("Current Status : " + newServiceRequest.getStatus());
        btn_support.setText(newServiceRequest.getSupport());
        btn_request.setText(newServiceRequest.getRequest());
        summary.setText(newServiceRequest.getSummary());
        description.setText(newServiceRequest.getDescription());
        btn_priority.setText(newServiceRequest.getPriority());


        /*
         * Get the Shared Preference data to assign the another object..
         * */
        sendRequest.setEmail(prefs.getString("Email", "No name defined"));
        sendRequest.setCompanyCode(prefs.getString("CompanyCode", "No name defined"));
        sendRequest.setEmployeeID(prefs.getString("EmployeeID", "No name defined"));
        sendRequest.setMasterID(newServiceRequest.getMasterID());
        sendRequest.setStatusCode(newServiceRequest.getStatusCode());

        newrequest.setEmail(prefs.getString("Email", "No name defined"));
        newrequest.setCompanyCode(prefs.getString("CompanyCode", "No name defined"));
        newrequest.setEmployeeID(prefs.getString("EmployeeID", "No name defined"));

        /*
         * Hide all the TextView and EditText
         * And then Set Enable and Disable for the particular EditText
         * */
        summary.setEnabled(false);
        description.setEnabled(false);
        closure.setEnabled(false);
        attach_file.setVisibility(View.GONE);
        attach.setVisibility(View.GONE);
        closure.setVisibility(View.GONE);
        tv_closure.setVisibility(View.GONE);
        tv_error.setVisibility(View.GONE);
        latest.setVisibility(View.GONE);
        tv_latest.setVisibility(View.GONE);
        btn_update.setVisibility(View.GONE);
        btn_create.setVisibility(View.GONE);
        summary.setFocusable(false);
        mylayout.setVisibility(View.GONE);
        tv_error.setVisibility(View.GONE);
        tv_attachments.setVisibility(View.GONE);
        iv_attachments.setVisibility(View.GONE);


        /*
         * Check whether the Token is Enable or not
         * */
        SharedPreferences preferences = getActivity().getSharedPreferences(MainApplication.MY_PREFS_NAME, MODE_PRIVATE);
        if (preferences != null) {
            MainApplication.token_data = preferences.getString("header", "No name defined");
        }

        /*
         * Get the Overall IssueTracking for the particular Request
         * */
        ApiManager.getInstance().getIssueTracking(sendRequest, new Callback<IssueTracking>() {
            /*
             * Api call success..
             * */
            @Override
            public void onResponse(@NonNull Call<IssueTracking> call, @NonNull Response<IssueTracking> response) {
                data = response.body();
                if (response.isSuccessful() && data != null) {
                    /*
                     * Get the IssueTracking Details and Request Details.
                     * */
                    trackdata = data.getData().get(0).getIssueTracking();
                    updaterequest_toolbar.setTitle(data.getData().get(0).getRequestDetails().get(0).getIssueNumber());

                    if (trackdata != null) {
                        for (int k = 0; k < trackdata.size(); k++) {
                            /*
                             * Design the UI for display the IssueTracking History
                             * */
                            TableRow row = new TableRow(getActivity());
                            TextView tv = new TextView(getActivity());
                            GradientDrawable gd = new GradientDrawable();
                            gd.setColor(getActivity().getColor(R.color.gray)); // Changes this drawable to use a single color instead of a gradient
                            gd.setCornerRadius(2);
                            gd.setStroke(3, 0xF0000000);
                            tv.setGravity(Gravity.LEFT);
                            tv.setTextSize(18);
                            tv.setTextColor(Color.BLACK);
                            tv.setPadding(8, 8, 8, 8);
                            tv.setBackground(gd);
                            tv.append("Created/Last Updated Date: " + trackdata.get(k).getCreated_Date() + "\n" +
                                    "Status: " + trackdata.get(k).getStatus() + "\n" +
                                    "Remarks: " + trackdata.get(k).getRemarks() + "\n" +
                                    "By: " + trackdata.get(k).getFirstName());
                            row.addView(tv);
                            mylayout.addView(row);
                        }
                    }
                }
            }

            /*
             * Api call Failure..
             * */
            @Override
            public void onFailure(@NonNull Call<IssueTracking> call, @NonNull Throwable t) {
                Log.e(TAG, "getIssueTraking: " + t.getLocalizedMessage());
            }
        });

        /*
         * Set the Disabled EditText Color
         * */
        if (!summary.isEnabled()) {
            summary.setTextColor(Color.BLACK);
        }
        if (!description.isEnabled()) {
            description.setTextColor(Color.BLACK);
        }
        if (!closure.isEnabled()) {
            closure.setTextColor(Color.BLACK);
        }

        /*
         * Get the overall dropdown data
         * */
        ApiManager.getInstance().getDropdownData(newrequest, new Callback<RequestType>() {
            /*
             * Get the Api success..
             * */
            @Override
            public void onResponse(@NonNull Call<RequestType> call, @NonNull Response<RequestType> response) {
                dropdowndata = response.body();
                if (response.isSuccessful() && dropdowndata != null) {
                    requestList = dropdowndata.getData();
                    Log.d(TAG, "GET DROPDOWN DATA");
                    /*
                     * Get the Priority Dropdown data
                     * */
                    priorityData = requestList.get(0).getPriorityList();
                    /*
                     * Initialize the Priority Array
                     * */
                    prioritydd = new String[priorityData.size()];
                    for (int item = 0; item < priorityData.size(); item++) {
                        prioritydd[item] = priorityData.get(item).getPriorityLevel();
                    }
                }
            }

            /*
             * Get the Api Failure
             * */
            @Override
            public void onFailure(@NonNull Call<RequestType> call, @NonNull Throwable t) {
                Log.e(TAG, Objects.requireNonNull(t.getLocalizedMessage()));
            }
        });

        if (newServiceRequest.getFilepath().equals("")) {
            tv_attachments.setVisibility(View.GONE);
            iv_attachments.setVisibility(View.GONE);
        } else {
            tv_attachments.setVisibility(View.VISIBLE);
            iv_attachments.setVisibility(View.VISIBLE);
        }

        iv_attachments.setOnClickListener(v -> {
            if(newServiceRequest.getFilepath().equals("")){
                getActivity().finish();
            }
            else{
                Uri uri = Uri.parse("http://192.168.1.50/hrdev/content/"+newServiceRequest.getFilepath());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        /*
         * Status = 'Cancelled'
         * */
        if (newServiceRequest.getStatus().equals("Cancelled")) {
            closure.setVisibility(View.VISIBLE);
            tv_closure.setVisibility(View.VISIBLE);
            tv_closure.setText("Closure Comments:");
            closure.setText(newServiceRequest.getLastComment());
            closure.setEnabled(true);
        }
        /*
         * Status = 'Resolved'
         * */
        else if (newServiceRequest.getStatus().equals("Resolved")) {
            closure.setVisibility(View.VISIBLE);
            tv_closure.setVisibility(View.VISIBLE);
            tv_closure.setText("Last Updated Remarks:");
            closure.setText(newServiceRequest.getLastComment());
            closure.setEnabled(false);
            btn_create.setVisibility(View.VISIBLE);
            btn_update.setVisibility(View.VISIBLE);
            latest.setVisibility(View.VISIBLE);
            tv_latest.setVisibility(View.VISIBLE);
            tv_latest.setText("Comments:");
            btn_create.setText("REOPEN");
            btn_update.setText("CLOSE SR");
            mylayout.setVisibility(View.VISIBLE);
        }
        /*
         * Status = 'Rejected'
         * */
        else if (newServiceRequest.getStatus().equals("Rejected")) {
            closure.setVisibility(View.VISIBLE);
            tv_closure.setVisibility(View.VISIBLE);
            tv_closure.setText("Last Updated Remarks:");
            closure.setText(newServiceRequest.getLastComment());
            closure.setEnabled(false);
            btn_create.setVisibility(View.VISIBLE);
            btn_update.setVisibility(View.VISIBLE);
            latest.setVisibility(View.VISIBLE);
            tv_latest.setVisibility(View.VISIBLE);
            tv_latest.setText("Comments:");
            btn_create.setText("REOPEN");
            btn_update.setText("CLOSE SR");
            mylayout.setVisibility(View.VISIBLE);
        }
        /*
         * Status = 'Closed'
         * */
        else if (newServiceRequest.getStatus().equals("Closed")) {
            closure.setVisibility(View.VISIBLE);
            tv_closure.setVisibility(View.VISIBLE);
            tv_closure.setText("Closure Comments:");
            closure.setText(newServiceRequest.getLastComment());
            mylayout.setVisibility(View.VISIBLE);
        }
        /*
         * Status = 'Need Clarification'
         * */
        else if (newServiceRequest.getStatus().equals("Need Clarification")) {
            closure.setVisibility(View.VISIBLE);
            tv_closure.setVisibility(View.VISIBLE);
            tv_closure.setText("Last Updated Remarks:");
            closure.setText(newServiceRequest.getLastComment());
            closure.setEnabled(false);
            btn_create.setVisibility(View.VISIBLE);
            btn_create.setText("CLARIFIED");
            latest.setVisibility(View.VISIBLE);
            tv_latest.setVisibility(View.VISIBLE);
            tv_latest.setText("Comments:");
            mylayout.setVisibility(View.VISIBLE);
        }
        /*
         * Status = 'Clarified'
         * */
        else if (newServiceRequest.getStatus().equals("Clarified")) {
            mylayout.setVisibility(View.VISIBLE);
        }
        /*
         * Status = 'Open'
         * */
        else if (newServiceRequest.getStatus().equals("Open")) {
            attach_file.setVisibility(View.VISIBLE);
            attach.setVisibility(View.VISIBLE);
            closure.setVisibility(View.VISIBLE);
            tv_closure.setVisibility(View.VISIBLE);
            btn_create.setVisibility(View.VISIBLE);
            btn_update.setVisibility(View.VISIBLE);
            closure.setEnabled(true);
            description.setEnabled(true);
            summary.setEnabled(true);
            btn_create.setText("CANCEL SR");
            btn_update.setText("UPDATE");

            /*
             * select the priority level from the dropdown list
             * */
            btn_priority.setOnClickListener(v -> priorityData());
        }

        btn_update.setOnClickListener(v -> {
            RequestBody ReEmail = RequestBody.create(text, prefs.getString("Email", "No name defined"));
            Log.i(TAG, "Email         : " + prefs.getString("Email", "No name defined"));
            RequestBody ReEmployeeId = RequestBody.create(text, prefs.getString("EmployeeID", "No name defined"));
            Log.i(TAG, "EmployeeID        : " + prefs.getString("EmployeeID", "No name defined"));
            RequestBody ReCompanyCode = RequestBody.create(text, prefs.getString("CompanyCode", "No name defined"));
            Log.i(TAG, "CompanyCode         : " + prefs.getString("CompanyCode", "No name defined"));
            RequestBody ReIssueMasterId = RequestBody.create(text, String.valueOf(newServiceRequest.getMasterID()));
            Log.i(TAG, "IssueMasterId         : " + newServiceRequest.getMasterID());
            RequestBody RePriorityId = RequestBody.create(text, String.valueOf(newrequest.getPriorityLevelID()));
            Log.i(TAG, "PriorityId         : " + newrequest.getPriorityLevelID());
            RequestBody ReSupportId = RequestBody.create(text, String.valueOf(data.getData().get(0).getRequestDetails().get(0).getSupportIndexFunctionID()));
            Log.i(TAG, "SupportId         : " + data.getData().get(0).getRequestDetails().get(0).getSupportIndexFunctionID());
            RequestBody ReRequestId = RequestBody.create(text, String.valueOf(data.getData().get(0).getRequestDetails().get(0).getRequestTypeID()));
            Log.i(TAG, "RequestId         : " + data.getData().get(0).getRequestDetails().get(0).getRequestTypeID());
            RequestBody Redescription = RequestBody.create(text, description.getText().toString());
            Log.i(TAG, "description         : " + description.getText().toString());
            RequestBody Resummary = RequestBody.create(text, summary.getText().toString());
            Log.i(TAG, "summary         : " + summary.getText().toString());

            if (btn_update.getText().toString().equals("UPDATE")) {
                Log.d("NewUpdateView", "Update Click");
                if (closure.getText().toString().equals("")) {
                    closure.setError("Enter the Comments");
                } else {
                    RequestBody Recomments = RequestBody.create(text, closure.getText().toString());
                    Log.i(TAG, "comments         : " + closure.getText().toString());
                    RequestBody RestatusCode = RequestBody.create(text, update_statuscode);
                    Log.i(TAG, "statusCode         : " + update_statuscode);
                    update(ReEmail, ReEmployeeId, ReCompanyCode, ReIssueMasterId, ReSupportId, ReRequestId, RePriorityId, Resummary, Redescription, RestatusCode, Recomments);
                }
            }
            if (btn_update.getText().toString().equals("CLOSE SR")) {
                Log.d("NewUpdateView", "Close Click");
                if (closure.getText().toString().equals("")) {
                    closure.setError("Enter the Comments");
                } else if (latest.getText().toString().equals("")) {
                    latest.setError("Enter the Comment");
                } else {
                    RequestBody Recomments = RequestBody.create(text, latest.getText().toString());
                    Log.i(TAG, "comments         : " + latest.getText().toString());
                    RequestBody RestatusCode = RequestBody.create(text, close_statuscode);
                    Log.i(TAG, "statusCode         : " + close_statuscode);
                    update(ReEmail, ReEmployeeId, ReCompanyCode, ReIssueMasterId, ReSupportId, ReRequestId, RePriorityId, Resummary, Redescription, RestatusCode, Recomments);
                }
            }
        });

        btn_create.setOnClickListener(v -> {
            RequestBody ReEmail = RequestBody.create(text, prefs.getString("Email", "No name defined"));
            Log.i(TAG, "Email         : " + prefs.getString("Email", "No name defined"));
            RequestBody ReEmployeeId = RequestBody.create(text, prefs.getString("EmployeeID", "No name defined"));
            Log.i(TAG, "EmployeeID        : " + prefs.getString("EmployeeID", "No name defined"));
            RequestBody ReCompanyCode = RequestBody.create(text, prefs.getString("CompanyCode", "No name defined"));
            Log.i(TAG, "CompanyCode         : " + prefs.getString("CompanyCode", "No name defined"));
            RequestBody ReIssueMasterId = RequestBody.create(text, String.valueOf(newServiceRequest.getMasterID()));
            Log.i(TAG, "IssueMasterId         : " + newServiceRequest.getMasterID());
            RequestBody RePriorityId = RequestBody.create(text, String.valueOf(data.getData().get(0).getRequestDetails().get(0).getPriorityLevelID()));
            Log.i(TAG, "PriorityId         : " + data.getData().get(0).getRequestDetails().get(0).getPriorityLevelID());
            RequestBody ReSupportId = RequestBody.create(text, String.valueOf(data.getData().get(0).getRequestDetails().get(0).getSupportIndexFunctionID()));
            Log.i(TAG, "SupportFunctionId         : " + data.getData().get(0).getRequestDetails().get(0).getSupportIndexFunctionID());
            RequestBody ReRequestId = RequestBody.create(text, String.valueOf(data.getData().get(0).getRequestDetails().get(0).getRequestTypeID()));
            Log.i(TAG, "questId         : " + data.getData().get(0).getRequestDetails().get(0).getRequestTypeID());
            RequestBody Redescription = RequestBody.create(text, description.getText().toString());
            Log.i(TAG, "description         : " + description.getText().toString());
            RequestBody Resummary = RequestBody.create(text, summary.getText().toString());
            Log.i(TAG, "summary         : " + summary.getText().toString());
            if (btn_create.getText().toString().equals("CANCEL SR")) {
                Log.d("NewUpdateView", "Cancel click");
                if (closure.getText().toString().equals("")) {
                    closure.setError("Enter the Comments");
                } else {
                    RequestBody Recomments = RequestBody.create(text, closure.getText().toString());
                    Log.i(TAG, "comments         : " + closure.getText().toString());
                    RequestBody RestatusCode = RequestBody.create(text, cancel_statuscode);
                    Log.i(TAG, "statusCode         : " + cancel_statuscode);
                    update(ReEmail,
                            ReEmployeeId,
                            ReCompanyCode,
                            ReIssueMasterId,
                            ReSupportId,
                            ReRequestId,
                            RePriorityId,
                            Resummary,
                            Redescription,
                            RestatusCode,
                            Recomments);
                }
            }
            if (btn_create.getText().toString().equals("CLARIFIED")) {
                Log.d("NewUpdateView", "Clarified click");
                if (closure.getText().toString().equals("")) {
                    closure.setError("Enter the Comments");
                } else if (latest.getText().toString().equals("")) {
                    latest.setError("Enter the Comment");
                } else {
                    RequestBody Recomments = RequestBody.create(text, latest.getText().toString());
                    Log.i(TAG, "comments         : " + latest.getText().toString());
                    RequestBody RestatusCode = RequestBody.create(text, clarified_statuscode);
                    Log.i(TAG, "statusCode         : " + clarified_statuscode);
                    update(ReEmail,
                            ReEmployeeId,
                            ReCompanyCode,
                            ReIssueMasterId,
                            ReSupportId,
                            ReRequestId,
                            RePriorityId,
                            Resummary,
                            Redescription,
                            RestatusCode,
                            Recomments);
                }
            }
            if (btn_create.getText().toString().equals("REOPEN")) {
                Log.d("NewUpdateView", "Reopen click");
                if (closure.getText().toString().equals("")) {
                    closure.setError("Enter the Comments");
                } else if (latest.getText().toString().equals("")) {
                    latest.setError("Enter the Comment");
                } else {
                    RequestBody Recomments = RequestBody.create(text, latest.getText().toString());
                    Log.i(TAG, "comments         : " + latest.getText().toString());
                    RequestBody RestatusCode = RequestBody.create(text, reopen_statuscode);
                    Log.i(TAG, "statusCode         : " + reopen_statuscode);
                    update(ReEmail,
                            ReEmployeeId,
                            ReCompanyCode,
                            ReIssueMasterId,
                            ReSupportId,
                            ReRequestId,
                            RePriorityId,
                            Resummary,
                            Redescription,
                            RestatusCode,
                            Recomments);
                }
            }
        });

        attach_file.setOnClickListener(v -> {
            int result = checkPermission();
            if (result == PackageManager.PERMISSION_GRANTED) {
                Intent image_intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(image_intent, RESULT_LOAD_FILE);
            } else {
                requestPermission();
            }
        });
        return view;
    }

    /**
    * When get the priority from the changes
    * */
    public void priorityData() {
        /*
         * select the priority level from the dropdown list
         * */
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select the Priority Level:");
        builder.setItems(prioritydd, (dialog, which) -> {
            checkItem = which;
            btn_priority.setText(priorityData.get(which).getPriorityLevel());
            newrequest.setPriorityLevelID(priorityData.get(which).getID());
            Log.i(TAG, "RePriorityId         : " + newrequest.getPriorityLevelID());
            dialog.dismiss();
        }).setNegativeButton("Cancel", (dialog, which) -> {
            if (checkItem < 0)
                btn_request.setText("Priority");
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
    * When the Request update function call method
    * */
    public void update(RequestBody ReEmail,
                       RequestBody ReEmployeeId,
                       RequestBody ReCompanyCode,
                       RequestBody ReIssueMasterId,
                       RequestBody ReSupportId,
                       RequestBody ReRequestId,
                       RequestBody RePriorityId,
                       RequestBody Resummary,
                       RequestBody Redescription,
                       RequestBody RestatusCode,
                       RequestBody Recomments) {

        ApiManager.getInstance().StoreRequest(
                null,
                ReEmail,
                ReEmployeeId,
                ReCompanyCode,
                ReIssueMasterId,
                ReSupportId,
                ReRequestId,
                RePriorityId,
                Resummary,
                Redescription,
                RestatusCode,
                Recomments,
                new Callback<Success>() {
                    /*
                     * Update the previous Request.
                     * When the api call is Success..
                     * */
                    @Override
                    public void onResponse(@NonNull Call<Success> call, @NonNull Response<Success> response) {
                        Success success = response.body();
                        /*
                         * Data update Success
                         * */
                        if (response.isSuccessful() && Objects.requireNonNull(success).getStatus().equals("true")) {
                            tv_error.setVisibility(View.GONE);
                            Log.d(TAG, "Request Update Success");
                            fragment = new WilcoConnect();
                            replaceFragment(fragment);
                        }
                        /*
                         * Data update Failure
                         * */
                        else {
                            tv_error.setVisibility(View.VISIBLE);
                            tv_error.setText("Update Failure");
                            Log.d(TAG, "New Update Failure");
                        }
                    }

                    /*
                     * When the api call is Failure..
                     * */
                    @Override
                    public void onFailure(@NonNull Call<Success> call, @NonNull Throwable t) {
                        tv_error.setVisibility(View.VISIBLE);
                        tv_error.setText("Update Failure");
                        Log.d(TAG, "Request Update Failure");
                    }
                });
    }

    /**
    * Check whether the Permission is Granted or Not
    * */
    private int checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return 0;
        } else {
            return -1;
        }
    }

    /**
    * Request the Write permission to load the file
    * */
    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(getActivity(), "Read and write External Storage permission allows us to retrieve files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, RESULT_LOAD_FILE);
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, RESULT_LOAD_FILE);
        }
    }

    /**
    * Get the Permission result from the dialog box
    * */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], int[] grantResults) {
        if (grantResults.length >= 0) {
            boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if (cameraAccepted)
                Toast.makeText(null, "Permission Granted, Now you can access location data and camera.", Toast.LENGTH_SHORT).show();
            else {
                Toast.makeText(null, "Permission Denied, You cannot access location data and camera.", Toast.LENGTH_LONG).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (shouldShowRequestPermissionRationale(CAMERA)) {
                        showMessageOKCancel("You need to allow access to external storage read/write permissions",
                                (dialog, which) -> {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        Intent imageintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        startActivityForResult(imageintent, RESULT_LOAD_FILE);
                                    }
                                });
                    }
                }
            }
        }
    }

    /**
     * Alert Box to display permission granted or denied
     */
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}