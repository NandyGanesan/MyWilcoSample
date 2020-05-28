package com.android.wilcoconnect.shared.expense;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.model.expense.FoodExpenseData;
import com.android.wilcoconnect.network_interface.RecyclerViewListener;
import com.android.wilcoconnect.shared.leave.CalenderListAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;

public class FoodExpenseAdapter extends RecyclerView.Adapter<FoodExpenseAdapter.MyViewHolder> {

    private ArrayList<FoodExpenseData> data;
    private Context c;
    private RecyclerViewListener listener;

    public FoodExpenseAdapter(ArrayList<FoodExpenseData> data, Context c, RecyclerViewListener listener) {
        this.data = data;
        this.c = c;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comp_off,parent,false);
        return new FoodExpenseAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.status.setText("Claim Number : "+data.get(position).getClaimNumber());
        holder.fromDate.setText("Date : "+data.get(position).getStrBillDate());
        holder.toDate.setText("Amount : "+data.get(position).getRequestedAmount());
        holder.remarks.setText(data.get(position).getStatus());
        holder.cardView.setOnClickListener(v -> {
            FoodExpenseData datas = new FoodExpenseData();
            datas.setClaimNumber(data.get(position).getClaimNumber());
            datas.setProjectID(data.get(position).getProjectID());
            datas.setStrBillDate(data.get(position).getStrBillDate());
            datas.setRemarks(data.get(position).getRemarks());
            datas.setRequestedAmount(data.get(position).getRequestedAmount());
            datas.setEmpReceiptList(data.get(position).getEmpReceiptList());

            /**
             * Show the data in New Update View
             * and perform respective operations
             * */
            Gson gson = new Gson();
            String s = gson.toJson(datas);
            if (listener != null)
                listener.onClick(v, s);
        });
    }

    @Override
    public int getItemCount() {
        return data.size()>0 ? data.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView status, fromDate, toDate, remarks;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.CardView_GridData);
            status = itemView.findViewById(R.id.tv_LeaveStatus);
            fromDate = itemView.findViewById(R.id.tv_fromDate);
            remarks = itemView.findViewById(R.id.tv_remarks);
            toDate = itemView.findViewById(R.id.tv_ToDate);
        }
    }
}
