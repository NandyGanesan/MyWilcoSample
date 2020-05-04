package com.android.wilcoconnect.fragment.leave.compensatory;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.api.ApiManager;
import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.fragment.leave.ApproveLeaveFromGrid;
import com.android.wilcoconnect.fragment.leave.Leave;
import com.android.wilcoconnect.model.leave.ApprovePost;
import com.android.wilcoconnect.model.leave.Onduty.OnDutyApprovePost;
import com.android.wilcoconnect.model.leave.compensatory.CompOffApprovePost;
import com.android.wilcoconnect.model.leave.compensatory.CompOffDetail;
import com.android.wilcoconnect.model.leave.compensatory.CompOffDetailData;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.android.wilcoconnect.network_interface.DialogListener;
import com.android.wilcoconnect.network_interface.RecyclerViewListener;
import com.android.wilcoconnect.shared.leave.compensatory.CompAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class CompensatoryDetail extends Fragment implements DialogListener {

    /*
     * Initialize the XML element or views
     * */
    private static String TAG = "CompensatoryDetail";
    private AddRequest addRequest = new AddRequest();
    private static String MYPREFS_NAME = "logininfo";
    private RecyclerView recyclerView;
    private ArrayList<CompOffDetailData> compOffDetailData = new ArrayList<>();
    private CompAdapter adapter;
    private TextView dataNotFound;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comp_off, container, false);

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
        if (prefs != null) {
            addRequest.setEmail(prefs.getString("Email", "No name defined"));
            addRequest.setCompanyCode(prefs.getString("CompanyCode", "No name defined"));
            addRequest.setEmployeeID(prefs.getString("EmployeeID", "No name defined"));
        }

        /*
         * Click the FloatingActionButton Action or call another Activity
         */
        FloatingActionButton add_new_compOff = view.findViewById(R.id.fab_add_task);
        add_new_compOff.setOnClickListener(v -> {
            apply_compOff();
        });

        /*
        * API Call to get the Comp-Off Detail
        * */
        ApiManager.getInstance().getMyCompOffDetail(addRequest, new Callback<CompOffDetail>() {
            //API Success
            @Override
            public void onResponse(Call<CompOffDetail> call, Response<CompOffDetail> response) {
                if (response.body() != null && response.isSuccessful()) {
                    compOffDetailData = response.body().getData();
                    set_list();
                }
            }
            //API Failure
            @Override
            public void onFailure(Call<CompOffDetail> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
            }
        });
        return view;
    }

    /*
     * Call the Apply Comp-Off Dialog Fragment to apply new Request
     * */
    private void apply_compOff() {
        ApplyCompensatory comp = new ApplyCompensatory();
        comp.setTargetFragment(this, 0);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        comp.show(transaction, ApplyCompensatory.TAG);
    }

    /*
    * Set the List of Data into the Adapter Class
    * */
    private void set_list() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        /*
        * Adapter Data is an Empty
        * */
        if (compOffDetailData.size() <= 0) {
            recyclerView.setAdapter(null);
            recyclerView.setVisibility(View.GONE);
            dataNotFound.setVisibility(View.VISIBLE);
        }
        /*
        * Adapter Date is not an Empty
        * */
        else {
            adapter = new CompAdapter(getActivity(), compOffDetailData, new RecyclerViewListener() {
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
            recyclerView.setAdapter(adapter);
        }
    }

    /*
     * Call the Detail Dialog Fragment
     * */
    private void newInstance(String s) {
        CompOffDetailDisplay compOffDetail = new CompOffDetailDisplay();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("compOff", s);
        compOffDetail.setArguments(bundle);
        compOffDetail.show(transaction,compOffDetail.TAG);
    }

    /*
     * Listener - Return Value from the Dialog Fragment
     * */
    @Override
    public void onDialogClick(String value) {
        if(value == "Success"){
            replaceFragment();
        }
    }

    /*
     * After Data Submission to refresh the Fragment
     * */
    private void replaceFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.comp_off_frame, new CompensatoryDetail());
        transaction.replace(R.id.approve_comp_off_frame,new ApproveCompOff());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}