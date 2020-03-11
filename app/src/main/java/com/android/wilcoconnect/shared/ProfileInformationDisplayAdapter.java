package com.android.wilcoconnect.shared;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.model.profile.BasicInformation;

import java.util.ArrayList;

public class ProfileInformationDisplayAdapter extends RecyclerView.Adapter<ProfileInformationDisplayAdapter.ViewHolder> {

    private ArrayList<BasicInformation> selectedList;
    private Context context;

    public ProfileInformationDisplayAdapter(ArrayList<BasicInformation> selectedList, Context context) {
        this.selectedList = selectedList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.profile_detail,parent,false);
        return new ProfileInformationDisplayAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.label.setText(selectedList.get(position).getDataLabel());
        holder.data.setText(selectedList.get(position).getDataDetail());
    }

    @Override
    public int getItemCount() {
        return selectedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView label,data;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            label = itemView.findViewById(R.id.tv_View_label);
            data = itemView.findViewById(R.id.tv_View_data);
        }
    }
}
