package com.android.wilcoconnect.shared.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.model.profile.FamilyDetailData;

import java.util.List;

public class FamilyAdapter extends RecyclerView.Adapter<FamilyAdapter.MyViewHolder> {

    private List<FamilyDetailData> familylist;
    private Context c;

    public FamilyAdapter(List<FamilyDetailData> familylist, Context c) {
        this.familylist = familylist;
        this.c = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.familydetail,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(familylist.get(position).getFamilyMemberName());
        holder.relation.setText(familylist.get(position).getRelation());
        if(familylist.get(position).getGender().equals("M")){
            holder.gender.setText("Male");
        }
        else {
            holder.gender.setText("Female");
        }
        holder.dob.setText(familylist.get(position).getFamilyPersonDateofBirth());
        holder.age.setText(""+familylist.get(position).getAge());
        holder.occupation.setText(familylist.get(position).getOccupation());
    }

    @Override
    public int getItemCount() {
        return familylist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,relation,gender,dob,age,occupation;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            relation = itemView.findViewById(R.id.tv_relation);
            gender = itemView.findViewById(R.id.tv_gender);
            dob = itemView.findViewById(R.id.tv_dob);
            age = itemView.findViewById(R.id.tv_age);
            occupation = itemView.findViewById(R.id.tv_occupation);
        }
    }
}
