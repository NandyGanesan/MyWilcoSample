package com.android.wilcoconnect.fragment.wilcoconnect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.wilcoconnect.network_interface.RecyclerViewListener;
import com.android.wilcoconnect.R;
import com.android.wilcoconnect.api.ApiManager;
import com.android.wilcoconnect.model.wilcoconnect.MyRequestData;
import com.android.wilcoconnect.model.wilcoconnect.SendRequest;
import com.android.wilcoconnect.model.common.UserData;
import com.android.wilcoconnect.shared.MyAdapter;
import com.android.wilcoconnect.shared.StatusAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WilcoConnect extends Fragment {
    private String TAG = "WilcoConnect";
    private RecyclerView recyclerView, recycleStatus;
    private MyAdapter adapter;
    private StatusAdapter statusAdapter;
    private UserData Tv_UserData;
    private static final String MY_PREFS_NAME = "MyPrefsFile";
    private SendRequest sendRequest = new SendRequest();
    private String[] statusType = new String[]{"All", "New", "Open", "Resolved", "Rejected", "Closed", "Cancelled"};
    private static ArrayList<MyRequestData.Data.MyRequests> request = new ArrayList<>();
    private View view;
    private CoordinatorLayout coordinatorLayout;
    private Fragment fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_wilco_connect, container, false);
        coordinatorLayout = view.findViewById(R.id.coordinatorLayout);

        Toolbar wilco_connect_toolbar = view.findViewById(R.id.main_nonav_toolbar);
        wilco_connect_toolbar.setTitle("WilcoConnect-My Service Request");

        /*
         * Get the value from the Login Activity
         * Display the Employee Id and Employee Email in Navigation View
         * */
        Intent intent = Objects.requireNonNull(getActivity()).getIntent();
        if (intent.getExtras() != null)
            if (intent.getExtras().getString("key") != null) {
                String value = intent.getExtras().getString("key");
                Gson gson = new Gson();
                Tv_UserData = gson.fromJson(value, UserData.class);
                /*
                 * Store User Email and EmployeeId and CompanyCode in Shared Preference
                 * For the purpose of frequent Access
                 * */
                SharedPreferences.Editor editor = this.getActivity().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
                editor.putString("Email", Tv_UserData.data.getEmail());
                editor.putString("EmployeeID", Tv_UserData.data.getEmployeeID());
                editor.putString("CompanyCode", "WL");
                editor.apply();
            }

        /*
         * Get the Shared Preference Data...To assign the another object
         * */
        SharedPreferences prefs = this.getActivity().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        sendRequest.setEmail(prefs.getString("Email", "No name defined"));
        sendRequest.setEmployeeID(prefs.getString("EmployeeID", "No name defined"));
        sendRequest.setCompanyCode(prefs.getString("CompanyCode", "No name defined"));
        sendRequest.setStatusLink("all");
        call_recycler_view(sendRequest);

        /*
         * Get the Status List to display in RecyclerView
         * Pass the data into the Adapter..
         * */
        recycleStatus = view.findViewById(R.id.recycler_view_filters);
        recycleStatus.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleStatus.setLayoutManager(layoutManager);
        statusAdapter = new StatusAdapter(getActivity(), statusType, (view, value) -> {
            if (!value.equals("")) {
                sendRequest.setStatusLink(value);
                call_recycler_view(sendRequest);
            } else {
                sendRequest.setStatusLink("all");
                call_recycler_view(sendRequest);
            }
        });
        recycleStatus.setAdapter(statusAdapter);

        /*
         *List of data displayed in the another RecyclerView
         *Initialize another RecyclerView
         * */
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        /*
         * Click the FloatingActionButton Action or call another Activity
         */
        FloatingActionButton Add_New_Service = view.findViewById(R.id.fab_add_task);
        Add_New_Service.setOnClickListener(v -> {
            fragment = new NewService();
            replaceFragment(fragment);
        });
        return view;
    }

    /*
     * Append the Data into the Recycler View
     * Get the Data from the api call..
     * */
    private void call_recycler_view(SendRequest sendRequest) {
        ApiManager.getInstance().getMyRequest(sendRequest, new Callback<MyRequestData>() {
            /*
             * Get the request based data..
             * When the api call is Success..
             * */
            @Override
            public void onResponse(@NonNull Call<MyRequestData> call, @NonNull Response<MyRequestData> response) {
                MyRequestData requestData = response.body();

                if (response.isSuccessful() && requestData != null) {
                    List<MyRequestData.Data> my_data = Objects.requireNonNull(response.body()).getData();
                    //When change get(0) for more then one data..
                    request = (ArrayList<MyRequestData.Data.MyRequests>) my_data.get(0).getMyrequests();
                }

                /*
                 * List Data should be append with Recycler View
                 * Pass the data into the Adapter..
                 * */
                if (request.size() != 0) {
                    adapter = new MyAdapter(getActivity(), request, (view, value) -> replaceFragment(newInstance(value)));
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter=null;
                    recyclerView.setAdapter(adapter);
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "No Data Found", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            /*
             * When the api call is Failure..
             * */
            @Override
            public void onFailure(@NonNull Call<MyRequestData> call, @NonNull Throwable t) {
                Log.e(TAG, "getMyRequest: " + t.getLocalizedMessage());
            }
        });
    }

    /*
    * When pass the string value into the another fragment
    * */
    private UpdateRequest newInstance(String s) {
        UpdateRequest updaterequest = new UpdateRequest();
        Bundle bundle = new Bundle();
        bundle.putString("data", s);
        updaterequest.setArguments(bundle);
        return updaterequest;
    }

    /*
     * When replace the fragemnt for particular user action
     * */
    private void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

