package com.android.wilcoconnect.fragment.leave.compensatory;

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
import com.android.wilcoconnect.model.leave.compensatory.CompOffApprovePost;
import com.android.wilcoconnect.model.leave.compensatory.CompOffDetail;
import com.android.wilcoconnect.model.leave.compensatory.CompOffDetailData;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.android.wilcoconnect.network_interface.RecyclerViewListener;
import com.android.wilcoconnect.shared.leave.compensatory.ApproveCompOffAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ApproveCompOff extends Fragment {

    /*
     * Initialize the XML element or views
     * */
    private RecyclerView recyclerView;
    private String TAG = "ApproveCompOff";
    private FrameLayout frameLayout;
    private static String MYPREFS_NAME = "logininfo";
    private ArrayList<CompOffDetailData> compOffArrayList = new ArrayList<>();
    private AddRequest request = new AddRequest();
    private String data;
    private ApproveCompOffAdapter adapter;

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

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        if(compOffArrayList.size()<0){
            adapter =null;
            recyclerView.setAdapter(adapter);
            Snackbar snackbar = Snackbar
                    .make(frameLayout, "No Data Found", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else {
            adapter = new ApproveCompOffAdapter(getActivity(), compOffArrayList, request, new RecyclerViewListener() {
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
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        remarks.setArguments(bundle);
        remarks.show(transaction,remarks.TAG);
    }
}
