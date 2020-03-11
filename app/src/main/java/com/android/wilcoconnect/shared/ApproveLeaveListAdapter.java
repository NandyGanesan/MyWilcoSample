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
import com.android.wilcoconnect.model.leave.ApproveList;
import com.android.wilcoconnect.network_interface.RecyclerViewListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ApproveLeaveListAdapter extends RecyclerView.Adapter<ApproveLeaveListAdapter.ViewHolder> {

    RecyclerViewListener listener;
    private Context c;
    private ArrayList<ApproveList> approveLists;

    public ApproveLeaveListAdapter( Context c, ArrayList<ApproveList> approveLists, RecyclerViewListener listener) {
        this.listener = listener;
        this.c = c;
        this.approveLists = approveLists;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.approverequestlist,parent,false);
        return new ApproveLeaveListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.employeeid.setText(approveLists.get(position).getEmployeeId());
        holder.employeename.setText(approveLists.get(position).getEmployeeName());
        holder.leavetype.setText(approveLists.get(position).getLeaveType());
        holder.status.setText(approveLists.get(position).getLeaveStatus());
        holder.fromdate.setText(approveLists.get(position).getFromDate());
        holder.todate.setText(approveLists.get(position).getToDate());

        holder.approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApproveList approveList = new ApproveList();
                approveList.setEmployeeId(approveLists.get(position).getEmployeeId());
                approveList.setEmployeeName(approveLists.get(position).getEmployeeName());
                approveList.setFromDate(approveLists.get(position).getFromDate());
                approveList.setToDate(approveLists.get(position).getToDate());
                approveList.setNo_of_days(approveLists.get(position).getNo_of_days());
                approveList.setRemarks(approveLists.get(position).getRemarks());

                /**
                 * Show the data in New Update View
                 * and perform respective operations
                 * */
                Gson gson = new Gson();
                String data = gson.toJson(approveList);
                if (listener != null)
                    listener.onClick(v, data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return approveLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView leavetype,status,fromdate,todate,employeeid,employeename;
        CardView approve;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            leavetype = itemView.findViewById(R.id.tv_leaveType);
            status = itemView.findViewById(R.id.tv_LeaveStatus);
            fromdate = itemView.findViewById(R.id.tv_fromDate);
            todate = itemView.findViewById(R.id.tv_ToDate);
            employeeid = itemView.findViewById(R.id.tv_employeeid);
            employeename = itemView.findViewById(R.id.tv_employeename);
            approve = itemView.findViewById(R.id.approverequest);

        }
    }
}
