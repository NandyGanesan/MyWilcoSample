package com.android.wilcoconnect.fragment.leave;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.api.ApiManager;
import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.model.leave.HolidayData;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.android.wilcoconnect.shared.leave.HolidayListAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class Holiday extends Fragment {

    /*
     * Initialize the variables to access the Module
     * */
    private String TAG = "Holiday";
    private Button btn_location;
    private String[] location={"All","Tamil Nadu","California","Telangana"};
    private ArrayList<HolidayData> holidayDataList = new ArrayList<>();
    private ArrayList<HolidayData> selectedholidaylist = new ArrayList<>();
    private int checkItem=0;
    private RecyclerView recyclerView;
    private AddRequest addRequest = new AddRequest();
    private static String MY_PREFS_NAME = "logininfo";
    private TextView dataNotFound;
    private HolidayListAdapter holidayadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_holiday, container, false);

        /*
         * Assign the Values for the Particular View Elements
         * */
        btn_location = view.findViewById(R.id.btn_location);
        dataNotFound = view.findViewById(R.id.label_name);
        recyclerView = view.findViewById(R.id.holiday_recycler_view);

        /*
         * Get the Header
         * */
        SharedPreferences preferences = getActivity().getSharedPreferences(MainApplication.MY_PREFS_NAME, MODE_PRIVATE);
        if (preferences != null) {
            MainApplication.token_data = preferences.getString("header", "No name defined");
        }

        /*
         * Get the shared preference data to assign the another object..
         * */
        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        if(prefs!=null) {
            addRequest.setEmail(prefs.getString("Email", "No name defined"));
            addRequest.setCompanyCode(prefs.getString("CompanyCode", "No name defined"));
            addRequest.setEmployeeID(prefs.getString("EmployeeID", "No name defined"));
        }

        /*
        * Get the List of Data
        * */
        getlist();

        /*
        * Click the Selection Dropdown to select Particular Location
        * */
        btn_location.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Select the Location:");
            checkItem=1;
            builder.setItems(location, (dialog, which) -> {
                checkItem = which;
                btn_location.setText(location[which]);
                set_holiday_list();
            }).setNegativeButton("Cancel", (dialog, which) -> {
                if (checkItem < 0)
                    btn_location.setText(location[0]);
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        return view;
    }

    private void getlist() {
        ApiManager.getInstance().getHolidayList(addRequest, new Callback<com.android.wilcoconnect.model.leave.Holiday>() {
            @Override
            public void onResponse(Call<com.android.wilcoconnect.model.leave.Holiday> call, Response<com.android.wilcoconnect.model.leave.Holiday> response) {
                if(response.body()!=null && response.isSuccessful()){
                    holidayDataList = new ArrayList<>();
                    holidayDataList = response.body().getData();
                    if(holidayDataList.size()>0) {
                        set_holiday_list();
                    }
                }
            }

            @Override
            public void onFailure(Call<com.android.wilcoconnect.model.leave.Holiday> call, Throwable t) {
                Log.d(TAG , t.getLocalizedMessage());
            }
        });
    }

    /*
     * When append the list of data into the RecyclerView within the CardView
     * */
    private void set_holiday_list() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        if(holidayDataList.size()>0) {
            if (btn_location.getText().equals("All")) {
                holidayadapter = new HolidayListAdapter(getActivity(), holidayDataList);
                recyclerView.setVisibility(View.VISIBLE);
                dataNotFound.setVisibility(View.GONE);
                recyclerView.setAdapter(holidayadapter);
            } else {
                selectedholidaylist = new ArrayList<>();
                for(int i = 0; i< holidayDataList.size(); i++){
                    if(btn_location.getText().equals(holidayDataList.get(i).getStateName())){
                        HolidayData data = new HolidayData();
                        data.setStateID(holidayDataList.get(i).getStateID());
                        data.setHolidayID(holidayDataList.get(i).getHolidayID());
                        data.setLeaveDate(holidayDataList.get(i).getLeaveDate());
                        data.setLeaveDay(holidayDataList.get(i).getLeaveDay());
                        data.setStateName(holidayDataList.get(i).getStateName());
                        data.setDescription(holidayDataList.get(i).getDescription());
                        selectedholidaylist.add(data);
                    }
                }
                if(selectedholidaylist.size()>0) {
                    holidayadapter = new HolidayListAdapter(getActivity(), selectedholidaylist);
                    dataNotFound.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(holidayadapter);
                }
                else{
                    recyclerView.setAdapter(null);
                    recyclerView.setVisibility(View.GONE);
                    dataNotFound.setVisibility(View.VISIBLE);
                }
            }
        }
        else{
            recyclerView.setAdapter(null);
            recyclerView.setVisibility(View.GONE);
            dataNotFound.setVisibility(View.VISIBLE);
        }
    }
}
