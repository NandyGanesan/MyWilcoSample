package com.android.wilcoconnect.shared.leave.compensatory;

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
import com.android.wilcoconnect.model.leave.compensatory.CompOffApprovePost;
import com.android.wilcoconnect.model.leave.compensatory.CompOffDetailData;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.android.wilcoconnect.network_interface.RecyclerViewListener;

import java.util.ArrayList;

public class ApproveCompOffAdapter extends RecyclerView.Adapter<ApproveCompOffAdapter.MyViewHolder> {

    private RecyclerViewListener listener;
    private Context c;
    private ArrayList<CompOffDetailData> appliedlist;
    private AddRequest request;

    public ApproveCompOffAdapter(Context c, ArrayList<CompOffDetailData> appliedList, AddRequest request, RecyclerViewListener listener) {
        this.listener = listener;
        this.c = c;
        this.appliedlist = appliedList;
        this.request = request;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_approve_on_duty_grid,parent,false);
        return new ApproveCompOffAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_id.setText(appliedlist.get(position).getEmployeeID());
        holder.tv_name.setText(appliedlist.get(position).getFirstName());
        holder.tv_type.setText(appliedlist.get(position).getStrAppliedDate());
        holder.tv_status.setText(appliedlist.get(position).getRequestStatus());
        holder.tv_fromdate.setText(appliedlist.get(position).getStrFromDate());
        holder.tv_todate.setText(appliedlist.get(position).getStrToDate());
        holder.tv_noofdays.setText(""+appliedlist.get(position).getNoOfDays());
        holder.tv_reason.setText(appliedlist.get(position).getRequestRemarks());

        holder.btn_accept.setOnClickListener(v -> {
            CompOffApprovePost post = new CompOffApprovePost();
            post.setEmployeeCode(request.getEmployeeID());
            post.setEmail(request.getEmail());
            post.setEmpCompensatoryID(appliedlist.get(position).getEmpCompensatoryID());
            post.setStatus("Approve");

            if (listener != null)
                listener.OnCompOffStore(v, post);

        });

        holder.btn_reject.setOnClickListener(v -> {
            CompOffApprovePost post = new CompOffApprovePost();
            post.setEmployeeCode(request.getEmployeeID());
            post.setEmail(request.getEmail());
            post.setEmpCompensatoryID(appliedlist.get(position).getEmpCompensatoryID());
            post.setStatus("Reject");

            if (listener != null)
                listener.OnCompOffStore(v, post);
        });
    }

    @Override
    public int getItemCount() {
        return appliedlist.size()>0 ? appliedlist.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_id,tv_name,tv_type,tv_status,tv_fromdate,tv_todate,tv_noofdays,tv_reason;
        TextView label_id,label_name,label_type,label_status,label_fromdate,label_todate,label_noofdays,label_reason;
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

            label_id = itemView.findViewById(R.id.label_id);
            label_name = itemView.findViewById(R.id.label_name);
            label_type = itemView.findViewById(R.id.label_type);
            label_status = itemView.findViewById(R.id.label_status);
            label_fromdate = itemView.findViewById(R.id.label_fromdate);
            label_todate = itemView.findViewById(R.id.label_todate);
            label_noofdays = itemView.findViewById(R.id.label_noofdays);
            label_reason = itemView.findViewById(R.id.label_reason);

            label_type.setText("APPLIED DATE");

            btn_accept = itemView.findViewById(R.id.btn_accept);
            btn_reject = itemView.findViewById(R.id.btn_reject);
            cardview = itemView.findViewById(R.id.CardView_GridData);
        }
    }
}
