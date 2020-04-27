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

        ApiManager.getInstance().getApproveCompOffDetail(request, new Callback<CompOffDetail>() {
            @Override
            public void onResponse(Call<CompOffDetail> call, Response<CompOffDetail> response) {
                if(response.body() != null && response.isSuccessful()){
                    compOffArrayList = response.body().getData();
                    set_Approve_onDuty_list();
                }
            }

            @Override
            public void onFailure(Call<CompOffDetail> call, Throwable t) {
                Log.e(TAG,t.getLocalizedMessage());
            }
        });

        return view;
    }

    private void set_Approve_onDuty_list() {
        selectedList = new ArrayList<>();
        for (int i=0;i<compOffArrayList.size();i++){
            if(compOffArrayList.get(i).getRequestStatus().equals("Applied")){
                CompOffDetailData data = new CompOffDetailData();
                data.setEmpCompensatoryID(compOffArrayList.get(i).getEmpCompensatoryID());
                data.setEmployeeID(compOffArrayList.get(i).getEmployeeID());
                data.setFirstName(compOffArrayList.get(i).getFirstName());
                data.setAppliedDate(compOffArrayList.get(i).getAppliedDate());
                data.setFromDate(compOffArrayList.get(i).getFromDate());
                data.setToDate(compOffArrayList.get(i).getToDate());
                data.setRequestStatus(compOffArrayList.get(i).getRequestStatus());
                data.setNoOfDays(compOffArrayList.get(i).getNoOfDays());
                data.setRequestRemarks(compOffArrayList.get(i).getRequestRemarks());
                selectedList.add(data);
            }
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        if(compOffArrayList.size()<=0){
            recyclerView.setAdapter(null);
            recyclerView.setVisibility(View.GONE);
            dataNotFound.setVisibility(View.VISIBLE);
        }
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
            recyclerView.setAdapter(adapter);
        }
    }

    private void getNewInstance(CompOffApprovePost post) {
        Gson gson = new Gson();
        String value = gson.toJson(post);
        Bundle bundle = new Bundle();
        bundle.putString("SubmitCompOff",value);
        CompOffRemarks remarks = new CompOffRemarks();
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
        transaction.replace(R.id.comp_off_frame, new CompensatoryDetail());
        transaction.replace(R.id.approve_comp_off_frame,new ApproveCompOff());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
