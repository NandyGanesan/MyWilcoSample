package com.android.wilcoconnect.fragment.leave;

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
import android.widget.TextView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.api.ApiManager;
import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.model.leave.ApprovePost;
import com.android.wilcoconnect.model.leave.MyLeaveData;
import com.android.wilcoconnect.model.leave.MyLeave;
import com.android.wilcoconnect.model.leave.Onduty.OnDutyApprovePost;
import com.android.wilcoconnect.model.leave.compensatory.CompOffApprovePost;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.android.wilcoconnect.network_interface.DialogListener;
import com.android.wilcoconnect.network_interface.RecyclerViewListener;
import com.android.wilcoconnect.shared.leave.MyLeaveListDataAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class Leave extends Fragment implements DialogListener {

    /*
    * Initialize the variables to access the Module
    * */
    private static String TAG = "Leave";
    private ArrayList<MyLeaveData> leavedata = new ArrayList<>();
    private RecyclerView recyclerView;
    private AddRequest addRequest = new AddRequest();
    private static String MYPREFS_NAME = "logininfo";
    private TextView dataNotFound;
    private MyLeaveListDataAdapter leaveadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leave, container, false);

        /*
        * Define the UI Element
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
            addRequest.setEmail(prefs.getString("Email", "No name defined"));
            addRequest.setCompanyCode(prefs.getString("CompanyCode", "No name defined"));
            addRequest.setEmployeeID(prefs.getString("EmployeeID", "No name defined"));
        }

        /*
        * API Call to get the Leave List
        * */
        ApiManager.getInstance().getMyLeaveList(addRequest, new Callback<MyLeave>() {
            //API Success
            @Override
            public void onResponse(Call<MyLeave> call, Response<MyLeave> response) {
                if(response.body()!=null && response.isSuccessful()){
                    leavedata = response.body().getData().get(0).getLeaveList();
                    if(leavedata!=null) {
                        setLeaveList();
                    }
                }
            }
            //API Failure
            @Override
            public void onFailure(Call<MyLeave> call, Throwable t) {
                Log.d(TAG,t.getLocalizedMessage());
            }
        });

        /*
         * Click the FloatingActionButton Action or call another Activity
         */
        FloatingActionButton add_new_leave = view.findViewById(R.id.fab_add_task);
        add_new_leave.setOnClickListener(v -> {
            apply_Leave();
        });

        return view;
    }

    /*
     * Call the Apply Leave Dialog Fragment to Store the new leave
     * */
    private void apply_Leave() {
        ApplyLeave leave = new ApplyLeave();
        leave.setTargetFragment(this, 0);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        leave.show(transaction,leave.TAG);
    }

    /*
     * When append the list of data into the RecyclerView within the CardView
     * */
    private void setLeaveList() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        /*
        * Adapter data is Empty
        * */
        if(leavedata.size()<=0){
            recyclerView.setAdapter(null);
            recyclerView.setVisibility(View.GONE);
            dataNotFound.setVisibility(View.VISIBLE);
        }
        /*
        * Adapter data is not an Empty
        * */
        else{
            leaveadapter = new MyLeaveListDataAdapter(getActivity(), leavedata, new RecyclerViewListener() {
                @Override
                public void onClick(View view, String value) {
                    newInstance(value);
                }
                @Override
                public void OnStore(View view, OnDutyApprovePost postData) {}
                @Override
                public void OnCompOffStore(View view, CompOffApprovePost post) {}
                @Override
                public void onClick(View view, ApprovePost post) {}
            });
            recyclerView.setVisibility(View.VISIBLE);
            dataNotFound.setVisibility(View.GONE);
            recyclerView.setAdapter(leaveadapter);
        }
    }

    /*
     * Call the Apply Leave Dialog Fragment to Store New Leave
     * */
    private void newInstance(String s) {
        ViewApplyLeaveDetails viewApplyLeaveDetails = new ViewApplyLeaveDetails();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("leave", s);
        viewApplyLeaveDetails.setArguments(bundle);
        viewApplyLeaveDetails.show(transaction,viewApplyLeaveDetails.TAG);
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
        transaction.replace(R.id.leave_frame, new Leave());
        transaction.replace(R.id.approve_frame,new ApproveLeaveFromGrid());
        transaction.replace(R.id.calender_frame,new LeaveCalenderForTeam());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}