package com.android.wilcoconnect.shared.leave.onduty_other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.model.leave.Onduty.OnDutyData;
import com.android.wilcoconnect.network_interface.RecyclerViewListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class OnDutyListAdapter extends RecyclerView.Adapter<OnDutyListAdapter.MyViewHolder> {

    Context c;
    RecyclerViewListener listener;
    ArrayList<OnDutyData> data;

    public OnDutyListAdapter(Context c, ArrayList<OnDutyData> data, RecyclerViewListener listener) {
        this.c = c;
        this.listener = listener;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leavelist,parent,false);
        return new OnDutyListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.type.setText(data.get(position).getOnDutyName());
        holder.status.setText(data.get(position).getRequestStatus());
        holder.fromdate.setText(data.get(position).getFromDate());
        holder.Todate.setText(data.get(position).getToDate());

        holder.cardView.setOnClickListener(v -> {
            OnDutyData onDutyData = new OnDutyData();
            onDutyData.setOnDutyName(data.get(position).getOnDutyName());
            onDutyData.setFromDate(data.get(position).getFromDate());
            onDutyData.setToDate(data.get(position).getToDate());
            onDutyData.setNumberOfDays(data.get(position).getNumberOfDays());
            onDutyData.setReason(data.get(position).getReason());
            onDutyData.setRequestStatus(data.get(position).getRequestStatus());
            onDutyData.setApprovedDate(data.get(position).getApprovedDate());
            onDutyData.setApprovedRemarks(data.get(position).getApprovedRemarks());
            onDutyData.setPrimaryApprover(data.get(position).getPrimaryApprover());

            /**
             * Show the data in New Update View
             * and perform respective operations
             * */
            Gson gson = new Gson();
            String s = gson.toJson(onDutyData);
            if (listener != null)
                listener.onClick(v, s);
        });
    }

    @Override
    public int getItemCount() {
        return data.size()>0 ? data.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView type,status,fromdate,Todate;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.CardView_GridData);
            type = itemView.findViewById(R.id.tv_leaveType);
            status = itemView.findViewById(R.id.tv_LeaveStatus);
            fromdate = itemView.findViewById(R.id.tv_fromDate);
            Todate = itemView.findViewById(R.id.tv_ToDate);
        }
    }
}
