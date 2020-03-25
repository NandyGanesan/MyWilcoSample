package com.android.wilcoconnect.fragment.leave;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.api.ApiManager;
import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.model.leave.ApproveLeaveData;
import com.android.wilcoconnect.model.leave.ApproveList;
import com.android.wilcoconnect.model.leave.ApprovePost;
import com.android.wilcoconnect.model.leave.MyLeaveData;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.android.wilcoconnect.network_interface.RecyclerViewListener;
import com.android.wilcoconnect.shared.ApproveLeaveListAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ApproveLeaveFromGrid extends Fragment {

    /*
     * Initialize the XML element or views
     * */
    private RecyclerView recyclerView;
    private String TAG = "ApproveLeaveFromGrid";
    private FrameLayout frameLayout;
    private static String MYPREFS_NAME = "logininfo";
    private ArrayList<MyLeaveData> approveList = new ArrayList<>();
    private ArrayList<MyLeaveData> appliedList = new ArrayList<>();
    private ArrayList<MyLeaveData> otherList = new ArrayList<>();
    private AddRequest request = new AddRequest();
    private String data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_approve_leave, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        frameLayout = view.findViewById(R.id.approve_frame);

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

        Gson gson = new Gson();
        data = gson.toJson(request);

        ApiManager.getInstance().getApproveList(request, new Callback<ApproveLeaveData>() {
            @Override
            public void onResponse(Call<ApproveLeaveData> call, Response<ApproveLeaveData> response) {
                if(response.body()!=null && response.isSuccessful()){
                    approveList = response.body().getData();
                    if(approveList.size()>0) {
                        set_Approve_leave_list();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApproveLeaveData> call, Throwable t) {
                Log.e(TAG,t.getLocalizedMessage());
            }
        });

        return view;
    }

    private void set_Approve_leave_list() {
        for (int i=0;i<approveList.size();i++){
            if(approveList.get(i).getRequestStatus().equals("Applied")){
                MyLeaveData data = new MyLeaveData();
                data.setEmail(approveList.get(i).getEmail());
                data.setStrFromDate(approveList.get(i).getStrFromDate());
                data.setStrToDate(approveList.get(i).getStrToDate());
                data.setStrApprovedDate(approveList.get(i).getStrApprovedDate());
                data.setLeaveRequestID(approveList.get(i).getLeaveRequestID());
                data.setEmployeeID(approveList.get(i).getEmployeeID());
                data.setFromDate(approveList.get(i).getFromDate());
                data.setToDate(approveList.get(i).getToDate());
                data.setNoofDays(approveList.get(i).getNoofDays());
                data.setRequestRemarks(approveList.get(i).getRequestRemarks());
                data.setRequestStatus(approveList.get(i).getRequestStatus());
                data.setFirstName(approveList.get(i).getFirstName());
                data.setLeaveTypeText(approveList.get(i).getLeaveTypeText());
                appliedList.add(data);
            }
            else {
                MyLeaveData data = new MyLeaveData();
                data.setEmail(approveList.get(i).getEmail());
                data.setStrFromDate(approveList.get(i).getStrFromDate());
                data.setStrToDate(approveList.get(i).getStrToDate());
                data.setStrApprovedDate(approveList.get(i).getStrApprovedDate());
                data.setLeaveRequestID(approveList.get(i).getLeaveRequestID());
                data.setEmployeeID(approveList.get(i).getEmployeeID());
                data.setFromDate(approveList.get(i).getFromDate());
                data.setToDate(approveList.get(i).getToDate());
                data.setNoofDays(approveList.get(i).getNoofDays());
                data.setRequestRemarks(approveList.get(i).getRequestRemarks());
                data.setRequestStatus(approveList.get(i).getRequestStatus());
                data.setFirstName(approveList.get(i).getFirstName());
                data.setLeaveTypeText(approveList.get(i).getLeaveTypeText());
                otherList.add(data);
            }
        }
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        ApproveLeaveListAdapter approve_adapter;
        if(appliedList.size()<0){
            approve_adapter =null;
            recyclerView.setAdapter(approve_adapter);
            Snackbar snackbar = Snackbar
                    .make(frameLayout, "No Data Found", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else {
            approve_adapter = new ApproveLeaveListAdapter(getActivity(), appliedList, request, new RecyclerViewListener() {
                @Override
                public void onClick(View view, String value) {
                    newInstance(value);
                }

                @Override
                public void onClick(View view, ApprovePost post) {
                    getNewInstance(post);
                }
            });
            recyclerView.setAdapter(approve_adapter);
        }
    }

    private void newInstance(String s) {
        ApproveFromPage approve = new ApproveFromPage();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("leave", s);
        bundle.putString("email", data);
        approve.setArguments(bundle);
        approve.show(transaction,approve.TAG);
    }

    private void getNewInstance(ApprovePost post){
        Gson gson = new Gson();
        String value = gson.toJson(post);
        Bundle bundle = new Bundle();
        bundle.putString("Submit",value);
        Remarks remarks = new Remarks();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        remarks.setArguments(bundle);
        remarks.show(transaction,remarks.TAG);
    }
}