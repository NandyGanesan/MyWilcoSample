package com.android.wilcoconnect.fragment.leave;

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
import com.android.wilcoconnect.model.leave.MyLeaveData;
import com.android.wilcoconnect.network_interface.RecyclerViewListener;
import com.android.wilcoconnect.shared.MyleaveListAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Leave extends Fragment {

    /*
    * Initialize the variables to access the Module
    * */
    private String TAG = "Leave";
    private ArrayList<MyLeaveData> leavedata = new ArrayList<>();
    private RecyclerView recyclerView;
    private FrameLayout frameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leave, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        frameLayout = view.findViewById(R.id.leave_frame);

        /*
         * Get the List of Data
         * */
        getlist();
        /*
         * When append the list of data into the RecyclerView within the CardView
         * */
        setleavelist();
        return view;
    }

    /*
     * When append the list of data into the RecyclerView within the CardView
     * */
    private void setleavelist() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        MyleaveListAdapter leaveadapter;
        if(leavedata.size()<0){
            leaveadapter =null;
            recyclerView.setAdapter(leaveadapter);
            Snackbar snackbar = Snackbar
                    .make(frameLayout, "No Data Found", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else{
            leaveadapter = new MyleaveListAdapter(getActivity(), leavedata, (view, value) -> newInstance(value));
            recyclerView.setAdapter(leaveadapter);
        }
    }

    private void getlist() {
        leavedata = new ArrayList<>();
        MyLeaveData myleave = new MyLeaveData();
        myleave.setLeaveType("Casual Leave");
        myleave.setLeaveStatus("Approved");
        myleave.setFromDate("17-Jan-2020");
        myleave.setToDate("17-Jan-2020");
        myleave.setAppliedDate("03-Jan-2020");
        myleave.setNo_of_Counts(1);
        myleave.setApprovedDate("03-Feb-2020");
        myleave.setRemarksByApprover("Approved");
        myleave.setApprover("Selvakumar Raju");
        myleave.setRemarks("I am going to my hometown to celebrate Pongal.");
        leavedata.add(myleave);

        MyLeaveData myleave1 = new MyLeaveData();
        myleave1.setLeaveType("Casual Leave");
        myleave1.setLeaveStatus("Applied");
        myleave1.setFromDate("14-Jan-2020");
        myleave1.setToDate("14-Jan-2020");
        myleave1.setAppliedDate("27-Dec-2020");
        myleave1.setNo_of_Counts(1);
        myleave1.setApprovedDate("");
        myleave1.setRemarksByApprover("");
        myleave1.setApprover("");
        myleave1.setRemarks("I am going to hometown for the purpose of festival.");
        leavedata.add(myleave1);
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