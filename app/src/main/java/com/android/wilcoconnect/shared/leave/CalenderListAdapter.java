package com.android.wilcoconnect.shared.leave;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.model.leave.ApproveList;

import java.util.ArrayList;

public class CalenderListAdapter extends RecyclerView.Adapter<CalenderListAdapter.ViewHolder> {

    private Context c;
    private ArrayList<ApproveList> approveLists;

    public CalenderListAdapter(Context c, ArrayList<ApproveList> approveLists) {
        this.c = c;
        this.approveLists = approveLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holidaylist,parent,false);
        return new CalenderListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText("NAME : " +approveLists.get(position).getFirstName());
        holder.detail.setText("Leave Type : "+approveLists.get(position).getLeaveTypeText() +"\n"+
                "From Date : "+approveLists.get(position).getStrFromDate() +"\n"+
                "To Date : "+approveLists.get(position).getStrToDate()+"\n"+
                "Description : "+approveLists.get(position).getRequestRemarks());
    }

    @Override
    public int getItemCount() {
        return approveLists.size() > 0 ? approveLists.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView detail,title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            detail = itemView.findViewById(R.id.tv_leavelist);
            title = itemView.findViewById(R.id.tv_title);
            title.setVisibility(View.VISIBLE);
        }
    }
}
