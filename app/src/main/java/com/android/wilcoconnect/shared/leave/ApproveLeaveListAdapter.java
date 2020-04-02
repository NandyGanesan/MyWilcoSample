package com.android.wilcoconnect.shared.leave;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.model.leave.ApproveList;
import com.android.wilcoconnect.model.leave.ApprovePost;
import com.android.wilcoconnect.model.leave.MyLeaveData;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.android.wilcoconnect.network_interface.RecyclerViewListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ApproveLeaveListAdapter extends RecyclerView.Adapter<ApproveLeaveListAdapter.ViewHolder> {

    RecyclerViewListener listener;
    private Context c;
    private ArrayList<MyLeaveData> approveLists;
    private AddRequest request;

    public ApproveLeaveListAdapter(Context c, ArrayList<MyLeaveData> approveLists,AddRequest request, RecyclerViewListener listener) {
        this.listener = listener;
        this.c = c;
        this.approveLists = approveLists;
        this.request = request;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.approverequestlist,parent,false);
        return new ApproveLeaveListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.employeeid.setText(approveLists.get(position).getEmployeeID());
            holder.employeename.setText(approveLists.get(position).getFirstName());
            holder.leavetype.setText(approveLists.get(position).getLeaveTypeText());
            holder.status.setText(approveLists.get(position).getRequestStatus());
            holder.fromdate.setText(approveLists.get(position).getStrFromDate());
            holder.todate.setText(approveLists.get(position).getStrToDate());

        holder.approve.setOnClickListener(v -> {
            ApproveList approveList = new ApproveList();
            approveList.setEmployeeID(approveLists.get(position).getEmployeeID());
            approveList.setFirstName(approveLists.get(position).getFirstName());
            approveList.setStrFromDate(approveLists.get(position).getStrFromDate());
            approveList.setStrToDate(approveLists.get(position).getStrToDate());
            approveList.setNo_of_days(approveLists.get(position).getNoofDays());
            approveList.setRequestRemarks(approveLists.get(position).getRequestRemarks());
            approveList.setLeaveRequestStatusID(approveLists.get(position).getLeaveRequestID());

            /**
             * Show the data in New Update View
             * and perform respective operations
             * */
            Gson gson = new Gson();
            String data = gson.toJson(approveList);
            if (listener != null)
                listener.onClick(v, data);
        });

        holder.accept.setOnClickListener(v -> {
            ApprovePost post = new ApprovePost();
            post.setEmployeeCode(request.getEmployeeID());
            post.setEmail(request.getEmail());
            post.setLeaveRequestID(approveLists.get(position).getLeaveRequestID());
            post.setApproveStatus("approved");

            if (listener != null)
                listener.onClick(v, post);

        });

        holder.reject.setOnClickListener(v -> {
            ApprovePost post = new ApprovePost();
            post.setEmployeeCode(request.getEmployeeID());
            post.setEmail(request.getEmail());
            post.setLeaveRequestID(approveLists.get(position).getLeaveRequestID());
            post.setApproveStatus("rejected");

            if (listener != null)
                listener.onClick(v, post);
        });
    }

    @Override
    public int getItemCount() {
        return approveLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView leavetype,status,fromdate,todate,employeeid,employeename;
        CardView approve;
        Button accept,reject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            leavetype = itemView.findViewById(R.id.tv_leaveType);
            status = itemView.findViewById(R.id.tv_LeaveStatus);
            fromdate = itemView.findViewById(R.id.tv_fromDate);
            todate = itemView.findViewById(R.id.tv_ToDate);
            employeeid = itemView.findViewById(R.id.tv_employeeid);
            employeename = itemView.findViewById(R.id.tv_employeename);
            approve = itemView.findViewById(R.id.approverequest);

            accept = itemView.findViewById(R.id.btn_accept);
            reject = itemView.findViewById(R.id.btn_reject);

        }
    }
}
