package com.android.wilcoconnect.shared;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.model.profile.PassportDetailData;

import java.util.List;

public class PassportDetailAdapter extends RecyclerView.Adapter<PassportDetailAdapter.MyViewHolder> {

    private Context c;
    private List<PassportDetailData> passportDetailData;

    public PassportDetailAdapter(Context c, List<PassportDetailData> passportDetailData) {
        this.c = c;
        this.passportDetailData = passportDetailData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.passportdetail,parent,false);
        return new PassportDetailAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.organization.setText(passportDetailData.get(position).getGivenNames());
        holder.fromdate.setText(passportDetailData.get(position).getSurName());
        holder.todate.setText(passportDetailData.get(position).getPassportNumber());
        holder.lastposition.setText(passportDetailData.get(position).getStrDateOfIssue());
        holder.initialposition.setText(passportDetailData.get(position).getPlaceofIssue());
        holder.jobresponsibilities.setText(passportDetailData.get(position).getCountry());
        holder.supervisor.setText(passportDetailData.get(position).getNationality());
        holder.grosssalary.setText(passportDetailData.get(position).getPlaceofBirth());
        holder.leaving.setText(passportDetailData.get(position).getStrValidUpTo());
    }

    @Override
    public int getItemCount() {
        if(passportDetailData.size()>0)
            return passportDetailData.size();
        else
            return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView organization,fromdate,todate,lastposition,initialposition,jobresponsibilities,supervisor,grosssalary,leaving;
        ImageView file;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            organization = itemView.findViewById(R.id.tv_organization);
            fromdate = itemView.findViewById(R.id.tv_fromdate);
            todate = itemView.findViewById(R.id.tv_todate);
            lastposition = itemView.findViewById(R.id.tv_lastposition);
            initialposition = itemView.findViewById(R.id.tv_initialposition);
            jobresponsibilities = itemView.findViewById(R.id.tv_jobresponsibilities);
            supervisor = itemView.findViewById(R.id.tv_supervisor);
            grosssalary = itemView.findViewById(R.id.tv_grosssalary);
            leaving = itemView.findViewById(R.id.tv_leaving);
            file = itemView.findViewById(R.id.tv_file);
        }
    }
}
