package com.android.wilcoconnect.fragment.leave;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.model.leave.ApproveList;
import com.android.wilcoconnect.network_interface.RecyclerViewListener;
import com.android.wilcoconnect.shared.ApproveLeaveListAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ApproveLeaveFromGrid extends Fragment {

    /*
     * Initialize the XML element or views
     * */
    private RecyclerView recyclerView;
    private FrameLayout frameLayout;
    private ArrayList<ApproveList> approveList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_approve_leave, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        frameLayout = view.findViewById(R.id.approve_frame);

        /*
         * Get the List of Data
         * */
        get_Approve_leave_list();

        /*
         * When append the list of data into the RecyclerView within the CardView
         * */
        set_Approve_leave_list();
        return view;
    }

    private void set_Approve_leave_list() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        ApproveLeaveListAdapter approve_adapter;
        if(approveList.size()<0){
            approve_adapter =null;
            recyclerView.setAdapter(approve_adapter);
            Snackbar snackbar = Snackbar
                    .make(frameLayout, "No Data Found", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else {
            approve_adapter = new ApproveLeaveListAdapter(getActivity(), approveList, new RecyclerViewListener() {
                @Override
                public void onClick(View view, String value) {
                    newInstance(value);
                }
            });
            recyclerView.setAdapter(approve_adapter);
        }
    }

    private void get_Approve_leave_list() {

        approveList = new ArrayList<>();

        ApproveList list = new ApproveList();
        list.setEmployeeId("WSPLE153");
        list.setEmployeeName("Ranjith Senthilvel");
        list.setFromDate("15-Jan-2020");
        list.setToDate("16-Jan-2020");
        list.setLeaveStatus("Applied");
        list.setLeaveType("Casual Leave");
        list.setNo_of_days(2);
        list.setRemarks("native");
        approveList.add(list);

        ApproveList list1 = new ApproveList();
        list1.setEmployeeId("WSPLE212");
        list1.setEmployeeName("Nandhini Ganesan");
        list1.setFromDate("18-Jan-2020");
        list1.setToDate("18-Jan-2020");
        list1.setLeaveStatus("Applied");
        list1.setLeaveType("Casual Leave");
        list1.setNo_of_days(1);
        list1.setRemarks("native");
        approveList.add(list1);
    }

    private void newInstance(String s) {
        ApproveFromPage approve = new ApproveFromPage();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("leave", s);
        approve.setArguments(bundle);
        approve.show(transaction,approve.TAG);
    }
}