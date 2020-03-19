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

public class MyleaveListAdapter extends RecyclerView.Adapter<MyleaveListAdapter.ViewHolder> {

    RecyclerViewListener listener;
    private Context c;
    private ArrayList<MyLeaveData> myLeaveData;

    public MyleaveListAdapter(Context c, ArrayList<MyLeaveData> myLeaveData,RecyclerViewListener listener) {
        this.c = c;
        this.myLeaveData = myLeaveData;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leavelist,parent,false);
        return new MyleaveListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.leavetype.setText(myLeaveData.get(position).getLeaveTypeText());
        holder.status.setText(myLeaveData.get(position).getRequestStatus());
        holder.fromdate.setText(myLeaveData.get(position).getFromDate());
        holder.Todate.setText(myLeaveData.get(position).getToDate());

        holder.cardView.setOnClickListener(v -> {
            MyLeaveData particularleavedata = new MyLeaveData();
            particularleavedata.setLeaveTypeText(myLeaveData.get(position).getLeaveTypeText());
            particularleavedata.setLeaveAppliedDate(myLeaveData.get(position).getLeaveAppliedDate());
            particularleavedata.setFromDate(myLeaveData.get(position).getFromDate());
            particularleavedata.setToDate(myLeaveData.get(position).getToDate());
            particularleavedata.setNoofDays(myLeaveData.get(position).getNoofDays());
            particularleavedata.setRequestStatus(myLeaveData.get(position).getRequestStatus());
            particularleavedata.setRequestRemarks(myLeaveData.get(position).getRequestRemarks());
            particularleavedata.setApprovedDate(myLeaveData.get(position).getApprovedDate());
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
