package com.android.wilcoconnect.shared;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.model.profile.EducationDetailData;

import java.util.List;

public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.MyViewHolder> {

    private List<EducationDetailData> educationlist;
    private Context c;

    public EducationAdapter(List<EducationDetailData> educationlist, Context c) {
        this.educationlist = educationlist;
        this.c = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.educationdetail,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.qualification.setText(educationlist.get(position).getQualification());
        holder.university.setText(educationlist.get(position).getUniversityInstitute());
        holder.yop.setText(educationlist.get(position).getYearofPassing());
        holder.marks.setText(""+educationlist.get(position).getMarksCGPA());
        holder.major.setText(educationlist.get(position).getMajorSubject());
    }

    @Override
    public int getItemCount() {
        return educationlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView qualification,university,yop,marks,major;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            qualification = itemView.findViewById(R.id.tv_qualification);
            university = itemView.findViewById(R.id.tv_university);
            yop = itemView.findViewById(R.id.tv_yop);
            marks = itemView.findViewById(R.id.tv_cgpa);
            major = itemView.findViewById(R.id.tv_major);

        }
    }
}
