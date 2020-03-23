package com.android.wilcoconnect.shared;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.model.leave.MyLeaveData;
import com.android.wilcoconnect.network_interface.RecyclerViewListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MyLeaveListDataAdapter extends RecyclerView.Adapter<MyLeaveListDataAdapter.ViewHolder> {

    RecyclerViewListener listener;
    private Context c;
    private ArrayList<MyLeaveData> myLeaveData;

    public MyLeaveListDataAdapter( Context c, ArrayList<MyLeaveData> myLeaveData, RecyclerViewListener listener) {
        this.listener = listener;
        this.c = c;
        this.myLeaveData = myLeaveData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leavelist,parent,false);
        return new MyLeaveListDataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.leavetype.setText(myLeaveData.get(position).getLeaveTypeText());
        holder.status.setText(myLeaveData.get(position).getRequestStatus());
        holder.fromdate.setText(myLeaveData.get(position).getStrFromDate());
        holder.Todate.setText(myLeaveData.get(position).getStrToDate());

        holder.cardView.setOnClickListener(v -> {
            MyLeaveData particularleavedata = new MyLeaveData();
            particularleavedata.setLeaveTypeText(myLeaveData.get(position).getLeaveTypeText());
            particularleavedata.setLeaveAppliedDate(myLeaveData.get(position).getLeaveAppliedDate());
            particularleavedata.setStrFromDate(myLeaveData.get(position).getStrFromDate());
            particularleavedata.setStrToDate(myLeaveData.get(position).getStrToDate());
            particularleavedata.setNoofDays(myLeaveData.get(position).getNoofDays());
            particularleavedata.setRequestStatus(myLeaveData.get(position).getRequestStatus());
            particularleavedata.setRequestRemarks(myLeaveData.get(position).getRequestRemarks());
            particularleavedata.setStrApprovedDate(myLeaveData.get(position).getStrApprovedDate());
            particularleavedata.setApprovedRemarks(myLeaveData.get(position).getApprovedRemarks());
            particularleavedata.setPrimaryApprover(myLeaveData.get(position).getPrimaryApprover());
            /**
             * Show the data in New Update View
             * and perform respective operations
             * */
            Gson gson = new Gson();
            String s = gson.toJson(particularleavedata);
            if (listener != null)
                listener.onClick(v, s);
        });
    }

    @Override
    public int getItemCount() {
        return myLeaveData == null ? 0 : myLeaveData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView leavetype,status,fromdate,Todate;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.CardView_GridData);
            leavetype = itemView.findViewById(R.id.tv_leaveType);
            status = itemView.findViewById(R.id.tv_LeaveStatus);
            fromdate = itemView.findViewById(R.id.tv_fromDate);
            Todate = itemView.findViewById(R.id.tv_ToDate);
        }
    }
}
