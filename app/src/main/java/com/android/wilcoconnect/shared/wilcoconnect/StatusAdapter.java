package com.android.wilcoconnect.shared.wilcoconnect;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.wilcoconnect.network_interface.RecyclerViewListener;
import com.android.wilcoconnect.R;


public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.MyViewHolder>{
    private Context context;
    private String[] statusList;
    private static String value="all";
    private RecyclerViewListener viewListener;

    private int selectedItem;

    public StatusAdapter(Context c, String[] status, RecyclerViewListener listener) {
        context = c;
        statusList = status;
        viewListener = listener;
        selectedItem = 0;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.requestfilter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.status.setText(statusList[position]);

        holder.cardView.setCardBackgroundColor(context.getColor(R.color.orange));
        holder.status.setTextColor(context.getColor(R.color.white));

        if (selectedItem == position) {
            holder.cardView.setCardBackgroundColor(context.getColor(R.color.toolbarbackground));
            holder.status.setTextColor(context.getColor(R.color.white ));
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG","Click" +position);

                int previousItem = selectedItem;
                selectedItem = position;

                if (statusList[position].equals("All")) {
                    value = "all";
                } else if (statusList[position].equals("New")) {
                    value = "N";
                } else if (statusList[position].equals("Open")) {
                    value = "OpenTickets";
                } else if (statusList[position].equals("Resolved")) {
                    value = "RS";
                } else if (statusList[position].equals("Rejected")) {
                    value = "RJ";
                } else if (statusList[position].equals("Closed")) {
                    value = "C";
                } else if (statusList[position].equals("Cancelled")) {
                    value = "CC";
                }

                if (viewListener != null)
                    viewListener.onClick(v, value);

                notifyItemChanged(previousItem);
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (statusList != null) return statusList.length;
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView status;
        CardView cardView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            status = itemView.findViewById(R.id.tv_statusList);
            cardView = itemView.findViewById(R.id.statuscard);
        }

    }
}
