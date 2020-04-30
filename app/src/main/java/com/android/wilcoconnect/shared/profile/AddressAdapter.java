package com.android.wilcoconnect.shared.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.model.profile.BasicDetails;


public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder> {

    private BasicDetails basicinformationdata;
    private Context c;

    public AddressAdapter(BasicDetails basicinformationdata, Context c) {
        this.basicinformationdata = basicinformationdata;
        this.c = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addressdetail,parent,false);
        return new AddressAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.address1.setText(basicinformationdata.getData().getCommunication_Address1());
        holder.country1.setText(basicinformationdata.getData().getCommunicationCountryName());
        holder.city1.setText(basicinformationdata.getData().getCommunicationCity());
        holder.state1.setText(basicinformationdata.getData().getCommunicationStateName());
        holder.pincode1.setText(basicinformationdata.getData().getCommAddressPincode());
        holder.address2.setText(basicinformationdata.getData().getAddress2());
        holder.country2.setText(basicinformationdata.getData().getCountryName());
        holder.city2.setText(basicinformationdata.getData().getCity());
        holder.state2.setText(basicinformationdata.getData().getStateName());
        holder.pincode2.setText(basicinformationdata.getData().getAddressPincode());
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView address1,country1,state1,city1,pincode1,address2,country2,state2,city2,pincode2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            address1 = itemView.findViewById(R.id.et_View_address1);
            country1 = itemView.findViewById(R.id.et_View_country1);
            state1 = itemView.findViewById(R.id.et_View_state1);
            city1 = itemView.findViewById(R.id.et_View_city1);
            pincode1 = itemView.findViewById(R.id.et_View_pincode1);
            address2 = itemView.findViewById(R.id.et_View_address2);
            country2 = itemView.findViewById(R.id.et_View_country2);
            state2 = itemView.findViewById(R.id.et_View_state2);
            city2 = itemView.findViewById(R.id.et_View_city2);
            pincode2 = itemView.findViewById(R.id.et_View_pincode2);
        }
    }
}
