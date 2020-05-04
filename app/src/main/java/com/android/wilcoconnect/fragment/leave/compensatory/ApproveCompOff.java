package com.android.wilcoconnect.fragment.leave.compensatory;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

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
import com.android.wilcoconnect.shared.leave.compensatory.ApproveCompOffAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ApproveCompOff extends Fragment implements DialogListener {

    /*
     * Initialize the XML element or views
     * */
    private RecyclerView recyclerView;
    private String TAG = "ApproveCompOff";
    private static String MYPREFS_NAME = "logininfo";
    private ArrayList<CompOffDetailData> compOffArrayList = new ArrayList<>();
    private ArrayList<CompOffDetailData> selectedList = new ArrayList<>();
    private AddRequest request = new AddRequest();
    private TextView dataNotFound;
    private ApproveCompOffAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_approve_comp_off, container, false);

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
        * API Call to get the Comp-Off List
        * */
        ApiManager.getInstance().getApproveCompOffDetail(request, new Callback<CompOffDetail>() {
            //API Success
            @Override
            public void onResponse(Call<CompOffDetail> call, Response<CompOffDetail> response) {
                if(response.body() != null && response.isSuccessful()){
                    compOffArrayList = response.body().getData();
                    set_Approve_onDuty_list();
                }
            }
            //API Failure
            @Override
            public void onFailure(Call<CompOffDetail> call, Throwable t) {
                Log.e(TAG,t.getLocalizedMessage());
            }
        });

        return view;
    }

    /*
    * Set the Approve List into the Adapter Class
    * */
    private void set_Approve_onDuty_list() {
        selectedList = new ArrayList<>();
        for (int i=0;i<compOffArrayList.size();i++){
            /*
            * Get the Applied List
            * */
            if(compOffArrayList.get(i).getRequestStatus().equals("Applied")){
                CompOffDetailData data = new CompOffDetailData();
                data.setEmpCompensatoryID(compOffArrayList.get(i).getEmpCompensatoryID());
                data.setEmployeeID(compOffArrayList.get(i).getEmployeeID());
                data.setFirstName(compOffArrayList.get(i).getFirstName());
                data.setStrAppliedDate(compOffArrayList.get(i).getStrAppliedDate());
                data.setStrFromDate(compOffArrayList.get(i).getStrFromDate());
                data.setStrToDate(compOffArrayList.get(i).getStrToDate());
                data.setRequestStatus(compOffArrayList.get(i).getRequestStatus());
                data.setNoOfDays(compOffArrayList.get(i).getNoOfDays());
                data.setRequestRemarks(compOffArrayList.get(i).getRequestRemarks());
                selectedList.add(data);
            }
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        /*
        * Adapter Data is an Empty
        * */
        if(selectedList.size()<=0){
            recyclerView.setAdapter(null);
            recyclerView.setVisibility(View.GONE);
            dataNotFound.setVisibility(View.VISIBLE);
        }
        /*
        * Adapter Data is not an Empty
        * */
        else {
            adapter = new ApproveCompOffAdapter(getActivity(), selectedList, request, new RecyclerViewListener() {
                            @Override
                            public void onClick(View view, String value) {}
                            @Override
                            public void OnStore(View view, OnDutyApprovePost postData) { }
                            @Override
                            public void OnCompOffStore(View view, CompOffApprovePost post) {
                                getNewInstance(post);
                            }
                            @Override
                            public void onClick(View view, ApprovePost post) {}
                        });
            recyclerView.setVisibility(View.VISIBLE);
            dataNotFound.setVisibility(View.GONE);
            recyclerView.setAdapter(adapter);
        }
    }

    /*
     * Call the Remarks Dialog Fragment to get the Remarks
     * */
    private void getNewInstance(CompOffApprovePost post) {
        Gson gson = new Gson();
        String value = gson.toJson(post);
        Bundle bundle = new Bundle();
        bundle.putString("SubmitCompOff",value);
        CompOffRemarks remarks = new CompOffRemarks();
        remarks.setTargetFragment(this, 0);
        assert getFragmentManager() != null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        remarks.setArguments(bundle);
        remarks.show(transaction, CompOffRemarks.TAG);
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
        assert getFragmentManager() != null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.comp_off_frame, new CompensatoryDetail());
        transaction.replace(R.id.approve_comp_off_frame,new ApproveCompOff());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
