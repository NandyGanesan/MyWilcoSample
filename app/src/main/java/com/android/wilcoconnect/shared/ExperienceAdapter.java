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
import com.android.wilcoconnect.model.profile.ExperienceDetailData;

import java.util.List;

public class ExperienceAdapter extends RecyclerView.Adapter<ExperienceAdapter.MyViewHolder> {

    private List<ExperienceDetailData> experienceDetailList;
    private Context c;

    public ExperienceAdapter(List<ExperienceDetailData> experienceDetailList, Context c) {
        this.experienceDetailList = experienceDetailList;
        this.c = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.educationdetail,parent,false);
        return new ExperienceAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.organization.setText(experienceDetailList.get(position).getOrganisation());
        holder.fromdate.setText(experienceDetailList.get(position).getStrFromDate());
        holder.todate.setText(experienceDetailList.get(position).getStrToDate());
        holder.lastposition.setText(experienceDetailList.get(position).getLastPosition());
        holder.initialposition.setText(experienceDetailList.get(position).getInitialPosition());
        holder.jobresponsibilities.setText(experienceDetailList.get(position).getJobResponsibility());
        holder.supervisor.setText(experienceDetailList.get(position).getSuperiorPosition());
        holder.grosssalary.setText(""+experienceDetailList.get(position).getGrossSalary());
        holder.leaving.setText(experienceDetailList.get(position).getLeavingReason());
    }

    @Override
    public int getItemCount() {
        if(experienceDetailList.size()>0){
            return experienceDetailList.size();
        }
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
