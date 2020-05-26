package com.android.wilcoconnect.fragment.leave;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.api.ApiManager;
import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.model.leave.ApproveLeaveData;
import com.android.wilcoconnect.model.leave.ApprovePost;
import com.android.wilcoconnect.model.leave.MyLeaveData;
import com.android.wilcoconnect.model.leave.Onduty.OnDutyApprovePost;
import com.android.wilcoconnect.model.leave.compensatory.CompOffApprovePost;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.android.wilcoconnect.network_interface.DialogListener;
import com.android.wilcoconnect.network_interface.RecyclerViewListener;
import com.android.wilcoconnect.shared.leave.ApproveLeaveListAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ApproveLeaveFromGrid extends Fragment implements DialogListener {

    /*
     * Initialize the XML element or views
     * */
    private RecyclerView recyclerView;
    private String TAG = "ApproveLeaveFromGrid";
    private static String MYPREFS_NAME = "logininfo";
    private ArrayList<MyLeaveData> approveList = new ArrayList<>();
    private ArrayList<MyLeaveData> appliedList = new ArrayList<>();
    private AddRequest request = new AddRequest();
    private TextView dataNotFound;
    private String data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_approve_leave, container, false);

        /*
        * Assign the Values for the Particular View Elements
        * */
        recyclerView = view.findViewById(R.id.recycler_view);
        dataNotFound = view.findViewById(R.id.label_name);

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
            request.setEmail(prefs.getString("Email", "No name defined"));
            request.setCompanyCode(prefs.getString("CompanyCode", "No name defined"));
            request.setEmployeeID(prefs.getString("EmployeeID", "No name defined"));
        }

        /*
        * Assign the object to Convert the String Value
        * */
        Gson gson = new Gson();
        data = gson.toJson(request);

        /*
        * Get the Approve List from the API Call
        * */
        ApiManager.getInstance().getApproveList(request, new Callback<ApproveLeaveData>() {
            //API Success
            @Override
            public void onResponse(Call<ApproveLeaveData> call, Response<ApproveLeaveData> response) {
                if(response.body()!=null && response.isSuccessful()){
                    approveList = response.body().getData();
                        set_Approve_leave_list();
                }
            }
            //API Failure
            @Override
            public void onFailure(Call<ApproveLeaveData> call, Throwable t) {
                Log.e(TAG,t.getLocalizedMessage());
            }
        });

        return view;
    }

    /*
    * Get the Approve List
    * */
    private void set_Approve_leave_list() {
        ApproveLeaveListAdapter approve_adapter;
        if (appliedList.size() <= 0) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
            //Adapter Data is Null
            recyclerView.setAdapter(null);
            recyclerView.setVisibility(View.GONE);
            dataNotFound.setVisibility(View.VISIBLE);
        }
        // Set Adapter to List of Data
        else {
            for (int i = 0; i < approveList.size(); i++) {
                /*
                 * Store the Applied list Into the Selected List
                 * */
                if (approveList.get(i).getRequestStatus().equals("Applied")) {
                    MyLeaveData data = new MyLeaveData();
                    data.setEmail(approveList.get(i).getEmail());
                    data.setStrFromDate(approveList.get(i).getStrFromDate());
                    data.setStrToDate(approveList.get(i).getStrToDate());
                    data.setStrApprovedDate(approveList.get(i).getStrApprovedDate());
                    data.setLeaveRequestID(approveList.get(i).getLeaveRequestID());
                    data.setEmployeeID(approveList.get(i).getEmployeeID());
                    data.setNoofDays(approveList.get(i).getNoofDays());
                    data.setRequestRemarks(approveList.get(i).getRequestRemarks());
                    data.setRequestStatus(approveList.get(i).getRequestStatus());
                    data.setFirstName(approveList.get(i).getFirstName());
                    data.setLeaveTypeText(approveList.get(i).getLeaveTypeText());
                    appliedList.add(data);
                }
                approve_adapter = new ApproveLeaveListAdapter(getActivity(), appliedList, request, new RecyclerViewListener() {
                    @Override
                    public void onClick(View view, String value) {
                        newInstance(value);
                    }

                    @Override
                    public void OnStore(View view, OnDutyApprovePost postData) {
                    }

                    @Override
                    public void OnCompOffStore(View view, CompOffApprovePost post) {
                    }

                    @Override
                    public void onClick(View view, ApprovePost post) {
                        getNewInstance(post);
                    }
                });
                recyclerView.setVisibility(View.VISIBLE);
                dataNotFound.setVisibility(View.GONE);
                recyclerView.setAdapter(approve_adapter);
            }
        }
    }

    /*
     * Call the Approve From Page Dialog Fragment to Display the Detail
     * */
    private void newInstance(String s) {
        ApproveFromPage approve = new ApproveFromPage();
        assert getFragmentManager() != null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("leave", s);
        bundle.putString("email", data);
        approve.setArguments(bundle);
        approve.show(transaction, ApproveFromPage.TAG);
    }

    /*
     * Call the Remarks Dialog Fragment to get the Remarks
     * */
    private void getNewInstance(ApprovePost post){
        Gson gson = new Gson();
        String value = gson.toJson(post);
        Bundle bundle = new Bundle();
        bundle.putString("Submit",value);
        Remarks remarks = new Remarks();
        remarks.setTargetFragment(this, 0);
        assert getFragmentManager() != null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        remarks.setArguments(bundle);
        remarks.show(transaction, Remarks.TAG);
    }

    /*
     * Listener - Return Value from the Dialog Fragment
     * */
    @Override
    public void onDialogClick(String value) {
        if(value.equals("Success")){
            replaceFragment();
        }
    }

    /*
     * After Data Submission to refresh the Fragment
     * */
    private void replaceFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.approve_frame, new ApproveLeaveFromGrid());
        transaction.replace(R.id.leave_frame,new Leave());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}