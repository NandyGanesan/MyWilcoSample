package com.android.wilcoconnect.shared;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.model.profile.ProfileMenu;
import com.android.wilcoconnect.network_interface.RecyclerViewListener;
import com.google.gson.Gson;

import java.util.ArrayList;


public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ProfileMenu> menu;
    private RecyclerViewListener listener;

    public ProfileAdapter(Context context, ArrayList<ProfileMenu> menu, RecyclerViewListener listener) {
        this.context = context;
        this.menu = menu;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.profilemenu_data,parent,false);
        return new ProfileAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(menu.get(position).getValues());
        holder.imageView.setImageResource(menu.get(position).getImages());

        holder.cardView.setOnClickListener(v -> {

            ProfileMenu selected_menu = new ProfileMenu();
            selected_menu.setValues(menu.get(position).getValues());

            /**
             * Show the data in New Update View
             * and perform respective operations
             * */
            Gson gson = new Gson();
            String data = gson.toJson(selected_menu);
            if (listener != null)
                listener.onClick(v, data);

        });
    }

    @Override
    public int getItemCount() {
        if(menu.size()>0){
            return menu.size();
        }
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
              super(itemView);
              cardView = itemView.findViewById(R.id.carddata);
              imageView = itemView.findViewById(R.id.image);
              textView = itemView.findViewById(R.id.tv_title);
        }
    }
}
