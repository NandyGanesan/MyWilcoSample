package com.android.wilcoconnect.fragment.leave.compensatory;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.api.ApiManager;
import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.model.leave.ApprovePost;
import com.android.wilcoconnect.model.leave.Onduty.OnDutyApprovePost;
import com.android.wilcoconnect.model.leave.compensatory.CompOffApprovePost;
import com.android.wilcoconnect.model.leave.compensatory.CompOffDetail;
import com.android.wilcoconnect.model.leave.compensatory.CompOffDetailData;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.android.wilcoconnect.network_interface.RecyclerViewListener;
import com.android.wilcoconnect.shared.leave.compensatory.CompAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class CompensatoryDetail extends Fragment {

    private static String TAG = "CompensatoryDetail";
    private AddRequest addRequest = new AddRequest();
    private static String MYPREFS_NAME = "logininfo";
    private RecyclerView recyclerView;
    private FrameLayout frameLayout;
    private CoordinatorLayout coordinatorLayout;
    private ArrayList<CompOffDetailData> compOffDetailData;
    private CompAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comp_off, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        coordinatorLayout = view.findViewById(R.id.coordinatorLayout);
        frameLayout = view.findViewById(R.id.leave_frame);

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

        ApiManager.getInstance().getMyCompOffDetail(addRequest, new Callback<CompOffDetail>() {
            @Override
            public void onResponse(Call<CompOffDetail> call, Response<CompOffDetail> response) {
                if (response.body() != null && response.isSuccessful()) {
                    compOffDetailData = response.body().getData();
                    set_list();
                }
            }
            @Override
            public void onFailure(Call<CompOffDetail> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
            }
        });
        return view;
    }

    private void apply_compOff() {
        ApplyCompensatory comp = new ApplyCompensatory();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        comp.show(transaction,comp.TAG);
    }

    private void set_list() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        if (compOffDetailData.size() < 0) {
            adapter = null;
            recyclerView.setAdapter(adapter);
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "No Data Found", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            adapter = new CompAdapter(getActivity(), compOffDetailData, new RecyclerViewListener() {
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
                }
            });
            recyclerView.setAdapter(adapter);
        }
    }

    private void newInstance(String s) {
        CompOffDetailDisplay compOffDetail = new CompOffDetailDisplay();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("compOff", s);
        compOffDetail.setArguments(bundle);
        compOffDetail.show(transaction,compOffDetail.TAG);
    }
}