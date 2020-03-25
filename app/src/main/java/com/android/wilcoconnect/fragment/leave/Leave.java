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
import android.widget.FrameLayout;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.api.ApiManager;
import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.model.leave.MyLeaveData;
import com.android.wilcoconnect.model.leave.Myleave;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.android.wilcoconnect.shared.MyLeaveListDataAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class Leave extends Fragment {

    /*
    * Initialize the variables to access the Module
    * */
    private String TAG = "Leave";
    private ArrayList<MyLeaveData> leavedata = new ArrayList<>();
    private RecyclerView recyclerView;
    private FrameLayout frameLayout;
    private AddRequest addRequest = new AddRequest();
    private static String MYPREFS_NAME = "logininfo";
    private MyLeaveListDataAdapter leaveadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leave, container, false);
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

        ApiManager.getInstance().getMyLeaveList(addRequest, new Callback<Myleave>() {
            @Override
            public void onResponse(Call<Myleave> call, Response<Myleave> response) {
                if(response.body()!=null && response.isSuccessful()){
                    leavedata = response.body().getData().get(0).getLeaveList();
                    if(leavedata!=null) {
                        setleavelist();
                    }
                }
            }

            @Override
            public void onFailure(Call<Myleave> call, Throwable t) {
                Log.d(TAG,t.getLocalizedMessage());
            }
        });

        return view;
    }

    /*
     * When append the list of data into the RecyclerView within the CardView
     * */
    private void setleavelist() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        if(leavedata.size()<0){
            leaveadapter =null;
            recyclerView.setAdapter(leaveadapter);
            Snackbar snackbar = Snackbar
                    .make(frameLayout, "No Data Found", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else{
            leaveadapter = new MyLeaveListDataAdapter(getActivity(), leavedata, (view, value) -> newInstance(value));
            recyclerView.setAdapter(leaveadapter);
        }
    }

    private void newInstance(String s) {
        ViewApplyLeaveDetails viewApplyLeaveDetails = new ViewApplyLeaveDetails();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("leave", s);
        viewApplyLeaveDetails.setArguments(bundle);
        viewApplyLeaveDetails.show(transaction,viewApplyLeaveDetails.TAG);
    }
}