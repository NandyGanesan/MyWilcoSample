package com.android.wilcoconnect.fragment.leave.Onduty_other;

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
import com.android.wilcoconnect.model.leave.ApprovePost;
import com.android.wilcoconnect.model.leave.Onduty.OnDutyApprovePost;
import com.android.wilcoconnect.model.leave.Onduty.OnDutyData;
import com.android.wilcoconnect.model.leave.Onduty.OnDutyDetails;
import com.android.wilcoconnect.model.leave.compensatory.CompOffApprovePost;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.android.wilcoconnect.network_interface.RecyclerViewListener;
import com.android.wilcoconnect.shared.leave.onduty_other.OnDutyListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class OnDutyApprovedList extends Fragment {

    /*
     * Initialize the XML element or views
     * */
    private String TAG = "OnDuty";
    View view;
    private RecyclerView recyclerView;
    private FrameLayout frameLayout;
    private AddRequest addRequest = new AddRequest();
    private static String MYPREFS_NAME = "logininfo";
    private ArrayList<OnDutyData> dutyData = new ArrayList<>();
    private OnDutyListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_leave, container, false);

        /*
         * Assign the Values for the Particular View Elements
         * */
        recyclerView = view.findViewById(R.id.recycler_view);
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
        if(prefs!=null) {
            addRequest.setEmail(prefs.getString("Email", "No name defined"));
            addRequest.setCompanyCode(prefs.getString("CompanyCode", "No name defined"));
            addRequest.setEmployeeID(prefs.getString("EmployeeID", "No name defined"));
        }

        /*
         * Assign the Values for the Particular View Elements
         * */
        FloatingActionButton fab = view.findViewById(R.id.fab_add_task);
        fab.setVisibility(View.GONE);

        /*
        * API Call to get the Approved List
        * */
        ApiManager.getInstance().getOnDutyApprovedList(addRequest, new Callback<OnDutyDetails>() {
            // API Call Success
            @Override
            public void onResponse(Call<OnDutyDetails> call, Response<OnDutyDetails> response) {
                if(response.body()!=null && response.isSuccessful()){
                    dutyData = response.body().getData();
                    set_list();
                }
            }
            // API Call Failure
            @Override
            public void onFailure(Call<OnDutyDetails> call, Throwable t) {
                Log.e(TAG,t.getLocalizedMessage());
            }
        });
        return view;
    }

    /*
     * Set the List of Data into the Adapter Class
     * */
    private void set_list() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        /*
         * Adapter is an Empty
         * */
        if (dutyData.size() <= 0) {
            adapter = null;
            recyclerView.setAdapter(adapter);
            Snackbar snackbar = Snackbar
                    .make(frameLayout, "No Data Found", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        /*
         * Adapter is not an Empty
         * */
        else {
            adapter = new OnDutyListAdapter(getActivity(), dutyData, new RecyclerViewListener() {
                @Override
                public void onClick(View view, String value) {
                    Instance(value);
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
     * Call the OnDuty Detail Dialog Fragment to Display the Details
     * */
    private void Instance(String s) {
        OnDutyListDisplayDetail detail = new OnDutyListDisplayDetail();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("OnDuty", s);
        detail.setArguments(bundle);
        detail.show(transaction,detail.TAG);
    }

}
