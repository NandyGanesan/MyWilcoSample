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
import android.widget.TextView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.api.ApiManager;
import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.fragment.leave.ApproveFromPage;
import com.android.wilcoconnect.fragment.leave.Leave;
import com.android.wilcoconnect.fragment.leave.Remarks;
import com.android.wilcoconnect.model.leave.ApproveLeaveData;
import com.android.wilcoconnect.model.leave.ApprovePost;
import com.android.wilcoconnect.model.leave.MyLeaveData;
import com.android.wilcoconnect.model.leave.Onduty.OnDutyApprovePost;
import com.android.wilcoconnect.model.leave.Onduty.OnDutyData;
import com.android.wilcoconnect.model.leave.Onduty.OnDutyDetails;
import com.android.wilcoconnect.model.leave.compensatory.CompOffApprovePost;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.android.wilcoconnect.network_interface.DialogListener;
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

public class ApproveOnDutyGrid extends Fragment implements DialogListener {

    /*
     * Initialize the XML element or views
     * */
    private RecyclerView recyclerView;
    private String TAG = "ApproveLeaveFromGrid";
    private static String MYPREFS_NAME = "logininfo";
    private ArrayList<OnDutyData> onDutyDataArrayList = new ArrayList<>();
    private AddRequest request = new AddRequest();
    private TextView dataNotFound;
    private ApproveOnDutyAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_approve_on_duty, container, false);

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

        ApiManager.getInstance().getAppliedOnDutyDetail(request, new Callback<OnDutyDetails>() {
            @Override
            public void onResponse(Call<OnDutyDetails> call, Response<OnDutyDetails> response) {
                if(response.body()!=null && response.isSuccessful()){
                    onDutyDataArrayList = response.body().getData();
                    set_Approve_onDuty_list();
                }
            }

            @Override
            public void onFailure(Call<OnDutyDetails> call, Throwable t) {
                Log.e(TAG,t.getLocalizedMessage());
            }
        });


        return view;
    }

    private void set_Approve_onDuty_list() {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        if(onDutyDataArrayList.size()<=0){
            recyclerView.setAdapter(null);
            recyclerView.setVisibility(View.GONE);
            dataNotFound.setVisibility(View.VISIBLE);
        }
        else {
            adapter = new ApproveOnDutyAdapter(new RecyclerViewListener() {
                            @Override
                            public void onClick(View view, String value) {
                            }
                            @Override
                            public void OnStore(View view, OnDutyApprovePost postData) {
                                getNewInstance(postData);
                            }

                @Override
                public void OnCompOffStore(View view, CompOffApprovePost post) {

                }

                @Override
                            public void onClick(View view, ApprovePost post) {}
                        }, getActivity(), onDutyDataArrayList, request);
            recyclerView.setVisibility(View.VISIBLE);
            dataNotFound.setVisibility(View.GONE);
            recyclerView.setAdapter(adapter);
        }
    }

    private void getNewInstance(OnDutyApprovePost post){
        Gson gson = new Gson();
        String value = gson.toJson(post);
        Bundle bundle = new Bundle();
        bundle.putString("SubmitOnDuty",value);
        OnDutyRemarks remarks = new OnDutyRemarks();
        remarks.setTargetFragment(this, 0);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        remarks.setArguments(bundle);
        remarks.show(transaction,remarks.TAG);
    }

    @Override
    public void onDialogClick(String value) {
        if(value == "Success"){
            replaceFragment();
        }
    }

    private void replaceFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.approve_on_duty_frame, new ApproveOnDutyGrid());
        transaction.replace(R.id.on_duty_frame,new OnDuty());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
