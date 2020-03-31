package com.android.wilcoconnect.fragment.leave;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.api.ApiManager;
import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.model.leave.ApproveLeaveData;
import com.android.wilcoconnect.model.leave.ApprovePost;
import com.android.wilcoconnect.model.leave.MyLeaveData;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.android.wilcoconnect.network_interface.RecyclerViewListener;
import com.android.wilcoconnect.shared.MyLeaveListDataAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ApprovedList extends Fragment {
    View view;
    private static final String MYPREFS_NAME = "logininfo";
    private AddRequest addRequest = new AddRequest();
    private String TAG = "ApprovedList";
    private RecyclerView recyclerView;
    private FrameLayout frameLayout;
    private ApproveLeaveData data = new ApproveLeaveData();
    private ArrayList<MyLeaveData> approvedlist;
    private MyLeaveListDataAdapter leaveadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_approved_list, container, false);

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

        ApiManager.getInstance().getApprovedListDetail(addRequest, new Callback<ApproveLeaveData>() {
            @Override
            public void onResponse(Call<ApproveLeaveData> call, Response<ApproveLeaveData> response) {
                if(response.isSuccessful() && response.body()!=null){
                    data = response.body();
                    approvedlist = data.getData();
                    if(approvedlist.size()>0){
                        set_list();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApproveLeaveData> call, Throwable t) {

            }
        });


        return view;
    }

    private void set_list() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        if(approvedlist.size()<0){
            leaveadapter =null;
            recyclerView.setAdapter(leaveadapter);
            Snackbar snackbar = Snackbar
                    .make(frameLayout, "No Data Found", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else{
            leaveadapter = new MyLeaveListDataAdapter(getActivity(), approvedlist, new RecyclerViewListener() {
                @Override
                public void onClick(View view, String value) {
                    newInstance(value);
                }

                @Override
                public void onClick(View view, ApprovePost post) {

                }
            });
            recyclerView.setAdapter(leaveadapter);
        }
    }

    private void newInstance(String s) {
        ApprovedListDisplay LeaveDetails = new ApprovedListDisplay();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("leave", s);
        LeaveDetails.setArguments(bundle);
        LeaveDetails.show(transaction,LeaveDetails.TAG);
    }
}
