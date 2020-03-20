package com.android.wilcoconnect.shared;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.model.profile.ReferenceDetailData;

import java.util.List;

public class ReferenceAdapter extends RecyclerView.Adapter<ReferenceAdapter.MyViewHolder> {

    private List<ReferenceDetailData> referenceDetailDataList;
    Context c;

    public ReferenceAdapter(List<ReferenceDetailData> referenceDetailDataList, Context c) {
        this.referenceDetailDataList = referenceDetailDataList;
        this.c = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.referencedetail,parent,false);
        return new ReferenceAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(referenceDetailDataList.get(position).getName());
        holder.designation.setText(referenceDetailDataList.get(position).getDesignation());
        holder.association.setText(referenceDetailDataList.get(position).getNatureofAssociation());
        holder.organization.setText(referenceDetailDataList.get(position).getOrganisation());
        holder.duration.setText(referenceDetailDataList.get(position).getDuration());
        holder.contact.setText(referenceDetailDataList.get(position).getContactNumber());
    }

    @Override
    public int getItemCount() {
        if(referenceDetailDataList.size()>0){
            return referenceDetailDataList.size();
        }
        else
            return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,organization,designation,duration,association,contact;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_Name);
            organization = itemView.findViewById(R.id.tv_organization);
            designation = itemView.findViewById(R.id.tv_designation);
            duration = itemView.findViewById(R.id.tv_duration);
            association = itemView.findViewById(R.id.tv_association);
            contact = itemView.findViewById(R.id.tv_contact);

        }
    }
}
