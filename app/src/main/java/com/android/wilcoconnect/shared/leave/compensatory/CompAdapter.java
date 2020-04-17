package com.android.wilcoconnect.shared.leave.compensatory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.model.leave.compensatory.CompOffDetailData;
import com.android.wilcoconnect.network_interface.RecyclerViewListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CompAdapter extends RecyclerView.Adapter<CompAdapter.MyViewHolder> {

    Context c;
    RecyclerViewListener listener;
    ArrayList<CompOffDetailData> data;

    public CompAdapter(Context c, ArrayList<CompOffDetailData> data, RecyclerViewListener listener) {
        this.c = c;
        this.listener = listener;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comp_off,parent,false);
        return new CompAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.status.setText(data.get(position).getRequestStatus());
        holder.fromDate.setText(data.get(position).getStrFromDate());
        holder.toDate.setText(data.get(position).getStrToDate());
        holder.remarks.setText(data.get(position).getRequestRemarks());

        holder.cardView.setOnClickListener(v -> {
            CompOffDetailData compOffDetailData = new CompOffDetailData();
            compOffDetailData.setEmpCompensatoryID(data.get(position).getEmpCompensatoryID());
            compOffDetailData.setAppliedDate(data.get(position).getAppliedDate());
            compOffDetailData.setStrFromDate(data.get(position).getStrFromDate());
            compOffDetailData.setStrToDate(data.get(position).getStrToDate());
            compOffDetailData.setNoOfDays(data.get(position).getNoOfDays());
            compOffDetailData.setRequestRemarks(data.get(position).getRequestRemarks());
            compOffDetailData.setRequestStatus(data.get(position).getRequestStatus());
            compOffDetailData.setApprovedDate(data.get(position).getApprovedDate());
            compOffDetailData.setApprovedRemarks(data.get(position).getApprovedRemarks());
            compOffDetailData.setApproverName(data.get(position).getApproverName());

            /**
             * Show the data in New Update View
             * and perform respective operations
             * */
            Gson gson = new Gson();
            String s = gson.toJson(compOffDetailData);
            if (listener != null)
                listener.onClick(v, s);
        });
    }

    @Override
    public int getItemCount() {
        return data.size()>0 ? data.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView status, fromDate, toDate, remarks;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.CardView_GridData);
            status = itemView.findViewById(R.id.tv_LeaveStatus);
            fromDate = itemView.findViewById(R.id.tv_fromDate);
            remarks = itemView.findViewById(R.id.tv_remarks);
            toDate = itemView.findViewById(R.id.tv_ToDate);
        }
    }
}