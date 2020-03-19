package com.android.wilcoconnect.shared;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.wilcoconnect.R;
import com.android.wilcoconnect.model.leave.HolidayDate;
import java.util.ArrayList;

public class HolidayListAdapter extends RecyclerView.Adapter<HolidayListAdapter.ViewHolder> {

    private Context c;
    private ArrayList<HolidayDate> holidayDates;

    public HolidayListAdapter(Context c, ArrayList<HolidayDate> holidayDates) {
        this.c = c;
        this.holidayDates = holidayDates;
    }

    @NonNull
    @Override
    public HolidayListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holidaylist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.holiday.setText(holidayDates.get(position).getDescription());
            holder.holidaydate.setText(holidayDates.get(position).getLeaveDate());
            holder.day.setText(holidayDates.get(position).getLeaveDay());
    }

    @Override
    public int getItemCount() {
        return holidayDates.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView holidaydate,holiday,day,leavelist;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.CardView_GridData);
            leavelist = itemView.findViewById(R.id.tv_leavelist);
            holidaydate = itemView.findViewById(R.id.tv_date);
            holiday = itemView.findViewById(R.id.tv_leave);
            day = itemView.findViewById(R.id.tv_day);
            holiday.setVisibility(View.VISIBLE);
            holidaydate.setVisibility(View.VISIBLE);
            day.setVisibility(View.VISIBLE);
            leavelist.setVisibility(View.GONE);

        }
    }
}
