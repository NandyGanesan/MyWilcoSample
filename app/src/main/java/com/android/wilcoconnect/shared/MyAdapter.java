package com.android.wilcoconnect.shared;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.model.wilcoconnect.MyRequestData;
import com.android.wilcoconnect.model.wilcoconnect.ServiceRequest;
import com.android.wilcoconnect.network_interface.RecyclerViewListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context c;
    private ArrayList<MyRequestData.Data.MyRequests> serviceRequests;
    RecyclerViewListener listener;

    public MyAdapter(Context c, ArrayList<MyRequestData.Data.MyRequests> serviceRequests, RecyclerViewListener listener) {
        this.c = c;
        this.serviceRequests = serviceRequests;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listoffdata, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAdapter.MyViewHolder holder, final int position) {

        holder.support.setText(" " + serviceRequests.get(position).getSupportFunctionName() + "- " + serviceRequests.get(position).getRequestDescription());
        holder.status.setText(" " + serviceRequests.get(position).getStatus());
        holder.issueno.setText(" " + serviceRequests.get(position).getIssueNumber());

        if (serviceRequests.get(position).getStatusCode().equals("N") ||
                serviceRequests.get(position).getStatusCode().equals("NCF") ||
                serviceRequests.get(position).getStatusCode().equals("RJ") ||
                serviceRequests.get(position).getStatusCode().equals("RS")) {
            holder.datalist.setCardBackgroundColor(c.getColor(R.color.white));
        } else {
            holder.datalist.setCardBackgroundColor(c.getColor(R.color.gray));
        }

        holder.datalist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "cardview click" + position);
                if (serviceRequests.get(position).getStatusCode().equals("CF") ||
                        serviceRequests.get(position).getStatusCode().equals("A") ||
                        serviceRequests.get(position).getStatusCode().equals("RO") ||
                        serviceRequests.get(position).getStatusCode().equals("RJ") ||
                        serviceRequests.get(position).getStatusCode().equals("RS") ||
                        serviceRequests.get(position).getStatusCode().equals("C") ||
                        serviceRequests.get(position).getStatusCode().equals("CC") ||
                        serviceRequests.get(position).getStatusCode().equals("N") ||
                        serviceRequests.get(position).getStatusCode().equals("NCF")) {

                    ServiceRequest newRequest = new ServiceRequest();
                    newRequest.setSupport(serviceRequests.get(position).getSupportFunctionName());
                    newRequest.setRequest(serviceRequests.get(position).getRequestDescription());
                    newRequest.setDescription(serviceRequests.get(position).getShortDescription());
                    newRequest.setSummary(serviceRequests.get(position).getShortDescription());
                    newRequest.setPriority(serviceRequests.get(position).getPriorityLevel());
                    newRequest.setStatus(serviceRequests.get(position).getStatus());
                    newRequest.setLastComment(serviceRequests.get(position).getUserstatus());
                    newRequest.setIssueNumber(serviceRequests.get(position).getIssueNumber());
                    newRequest.setMasterID(serviceRequests.get(position).getIssueMasterID());
                    newRequest.setStatusCode(serviceRequests.get(position).getStatusCode());
                    newRequest.setFilepath(serviceRequests.get(position).getFilePath());

                    /**
                     * Show the data in New Update View
                     * and perform respective operations
                     * */
                    Gson gson = new Gson();
                    String s = gson.toJson(newRequest);
                    if (listener != null)
                        listener.onClick(v, s);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (serviceRequests != null) return serviceRequests.size();
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView issueno, support, status;
        CardView datalist;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            datalist = itemView.findViewById(R.id.CardView_GridData);
            support = itemView.findViewById(R.id.tv_support);
            status = itemView.findViewById(R.id.tv_status);
            issueno = itemView.findViewById(R.id.tv_issueno);
        }
    }
}