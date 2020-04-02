package com.android.wilcoconnect.fragment.leave.Onduty_other;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.api.ApiManager;
import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.fragment.leave.ApproveFromPage;
import com.android.wilcoconnect.fragment.leave.Remarks;
import com.android.wilcoconnect.model.leave.ApproveLeaveData;
import com.android.wilcoconnect.model.leave.ApprovePost;
import com.android.wilcoconnect.model.leave.MyLeaveData;
import com.android.wilcoconnect.model.leave.Onduty.OnDutyApprovePost;
import com.android.wilcoconnect.model.leave.Onduty.OnDutyData;
import com.android.wilcoconnect.model.leave.Onduty.OnDutyDetails;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.android.wilcoconnect.network_interface.RecyclerViewListener;
import com.android.wilcoconnect.shared.leave.ApproveLeaveListAdapter;
import com.android.wilcoconnect.shared.leave.onduty_other.ApproveOnDutyAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ApproveOnDutyGrid extends Fragment {

    /*
     * Initialize the XML element or views
     * */
    private RecyclerView recyclerView;
    private String TAG = "ApproveLeaveFromGrid";
    private FrameLayout frameLayout;
    private static String MYPREFS_NAME = "logininfo";
    private ArrayList<OnDutyData> onDutyDataArrayList = new ArrayList<>();
    private AddRequest request = new AddRequest();
    private String data;
    private ApproveOnDutyAdapter adapter;

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

        ApiManager.getInstance().getAppliedOnDutyDetail(request, new Callback<OnDutyDetails>() {
            @Override
            public void onResponse(Call<OnDutyDetails> call, Response<OnDutyDetails> response) {
                if(response.body()!=null && response.isSuccessful()){
                    onDutyDataArrayList = response.body().getData();
                }
            }

            @Override
            public void onFailure(Call<OnDutyDetails> call, Throwable t) {
                Log.e(TAG,t.getLocalizedMessage());
            }
        });


        return view;
    }

    private void set_Approve_leave_list() {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        if(onDutyDataArrayList.size()<0){
            adapter =null;
            recyclerView.setAdapter(adapter);
            Snackbar snackbar = Snackbar
                    .make(frameLayout, "No Data Found", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else {
            adapter = new ApproveOnDutyAdapter(new RecyclerViewListener() {
                            @Override
                            public void onClick(View view, String value) {
                                newInstance(value);
                            }
                            @Override
                            public void OnStore(View view, OnDutyApprovePost postData) {}
                            @Override
                            public void onClick(View view, ApprovePost post) {
                                getNewInstance(post);
                            }
                        }, getActivity(), onDutyDataArrayList, request);
            recyclerView.setAdapter(adapter);
        }
    }

    private void newInstance(String s) {
        ApproveFromPage approve = new ApproveFromPage();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("onDutyApprove", s);
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
