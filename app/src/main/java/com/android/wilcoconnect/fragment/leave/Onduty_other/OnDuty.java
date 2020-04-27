package com.android.wilcoconnect.fragment.leave.Onduty_other;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
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
import com.android.wilcoconnect.fragment.leave.compensatory.ApplyCompensatory;
import com.android.wilcoconnect.model.leave.ApprovePost;
import com.android.wilcoconnect.model.leave.Onduty.OnDutyApprovePost;
import com.android.wilcoconnect.model.leave.Onduty.OnDutyData;
import com.android.wilcoconnect.model.leave.Onduty.OnDutyDetails;
import com.android.wilcoconnect.model.leave.compensatory.CompOffApprovePost;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.android.wilcoconnect.network_interface.DialogListener;
import com.android.wilcoconnect.network_interface.RecyclerViewListener;
import com.android.wilcoconnect.shared.leave.MyLeaveListDataAdapter;
import com.android.wilcoconnect.shared.leave.onduty_other.OnDutyListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class OnDuty extends Fragment implements DialogListener {

    private String TAG = "OnDuty";
    View view;
    private RecyclerView recyclerView;
    private AddRequest addRequest = new AddRequest();
    private static String MYPREFS_NAME = "logininfo";
    private ArrayList<OnDutyData> dutyData = new ArrayList<>();
    private OnDutyListAdapter adapter;
    private TextView dataNotFound;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_on_duty, container, false);
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

        ApiManager.getInstance().getMyOnDutyDetails(addRequest, new Callback<OnDutyDetails>() {
            @Override
            public void onResponse(Call<OnDutyDetails> call, Response<OnDutyDetails> response) {
                if(response.body()!=null && response.isSuccessful()){
                    dutyData = response.body().getData();
                    set_list();
                }
            }

            @Override
            public void onFailure(Call<OnDutyDetails> call, Throwable t) {
                Log.e(TAG,t.getLocalizedMessage());
            }
        });

        /*
         * Click the FloatingActionButton Action or call another Activity
         */
        FloatingActionButton add_new_compOff = view.findViewById(R.id.fab_add_task);
        add_new_compOff.setOnClickListener(v -> {
            apply_onDuty();
        });

        return view;
    }

    private void apply_onDuty() {
        ApplyOnDuty duty = new ApplyOnDuty();
        duty.setTargetFragment(this, 0);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        duty.show(transaction,duty.TAG);
    }

    private void set_list() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        if (dutyData.size() <= 0) {
            recyclerView.setAdapter(null);
            recyclerView.setVisibility(View.GONE);
            dataNotFound.setVisibility(View.VISIBLE);
        } else {
            adapter = new OnDutyListAdapter(getActivity(), dutyData, new RecyclerViewListener() {
                @Override
                public void onClick(View view, String value) {
                    Instance(value);
                }
                @Override
                public void OnStore(View view, OnDutyApprovePost postData) {}
                @Override
                public void OnCompOffStore(View view, CompOffApprovePost post) {

                }
                @Override
                public void onClick(View view, ApprovePost post) {}
            });
            recyclerView.setVisibility(View.VISIBLE);
            dataNotFound.setVisibility(View.GONE);
            recyclerView.setAdapter(adapter);
        }
    }

    private void Instance(String s) {
        OnDutyListDisplayDetail detail = new OnDutyListDisplayDetail();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("OnDuty", s);
        detail.setArguments(bundle);
        detail.show(transaction,detail.TAG);
    }

    @Override
    public void onDialogClick(String value) {
        if(value == "Success"){
            replaceFragment();
        }
    }

    private void replaceFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.on_duty_frame, new OnDuty());
        transaction.replace(R.id.approve_on_duty_frame,new ApproveOnDutyGrid());
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
