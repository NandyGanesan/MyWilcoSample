package com.android.wilcoconnect.shared.leave.onduty_other;

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
import com.android.wilcoconnect.model.leave.Onduty.OnDutyApprovePost;
import com.android.wilcoconnect.model.leave.Onduty.OnDutyData;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.android.wilcoconnect.network_interface.RecyclerViewListener;

import java.util.ArrayList;

public class ApproveOnDutyAdapter extends RecyclerView.Adapter<ApproveOnDutyAdapter.MyViewHolder> {

    RecyclerViewListener listener;
    private Context c;
    private ArrayList<OnDutyData> appliedlist;
    private AddRequest request;

    public ApproveOnDutyAdapter(RecyclerViewListener listener, Context c, ArrayList<OnDutyData> appliedlist, AddRequest request) {
        this.listener = listener;
        this.c = c;
        this.appliedlist = appliedlist;
        this.request = request;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_approve_on_duty_grid,parent,false);
        return new ApproveOnDutyAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_id.setText(appliedlist.get(position).getEmployeeID());
        holder.tv_name.setText(appliedlist.get(position).getEmployeeName());
        holder.tv_type.setText(appliedlist.get(position).getOnDutyName());
        holder.tv_status.setText(appliedlist.get(position).getRequestStatus());
        holder.tv_fromdate.setText(appliedlist.get(position).getStrFromDate());
        holder.tv_todate.setText(appliedlist.get(position).getStrToDate());
        holder.tv_noofdays.setText(""+appliedlist.get(position).getNumberOfDays());
        holder.tv_reason.setText(appliedlist.get(position).getReason());

        holder.btn_accept.setOnClickListener(v -> {
            OnDutyApprovePost post = new OnDutyApprovePost();
            post.setEmployeeCode(request.getEmployeeID());
            post.setEmail(request.getEmail());
            post.setWorkFromHomeEmployeeRequestID(appliedlist.get(position).getWorkFromHomeEmployeeRequestID());
            post.setRequeststatus("approved");

            if (listener != null)
                listener.OnStore(v, post);

        });

        holder.btn_reject.setOnClickListener(v -> {
            OnDutyApprovePost post = new OnDutyApprovePost();
            post.setEmployeeCode(request.getEmployeeID());
            post.setEmail(request.getEmail());
            post.setWorkFromHomeEmployeeRequestID(appliedlist.get(position).getWorkFromHomeEmployeeRequestID());
            post.setRequeststatus("rejected");

            if (listener != null)
                listener.OnStore(v, post);
        });
    }

    @Override
    public int getItemCount() {
        return appliedlist.size()>0 ? appliedlist.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_id,tv_name,tv_type,tv_status,tv_fromdate,tv_todate,tv_noofdays,tv_reason;
        Button btn_accept,btn_reject;
        CardView cardview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_status = itemView.findViewById(R.id.tv_status);
            tv_fromdate = itemView.findViewById(R.id.tv_fromdate);
            tv_todate = itemView.findViewById(R.id.tv_todate);
            tv_noofdays = itemView.findViewById(R.id.tv_noofdays);
            tv_reason = itemView.findViewById(R.id.tv_reason);
            btn_accept = itemView.findViewById(R.id.btn_accept);
            btn_reject = itemView.findViewById(R.id.btn_reject);
            cardview = itemView.findViewById(R.id.CardView_GridData);
        }
    }
}
