package com.android.wilcoconnect.fragment.leave;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.model.leave.HolidayDate;
import com.android.wilcoconnect.shared.HolidayListAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Objects;

public class Holiday extends Fragment {

    /*
     * Initialize the variables to access the Module
     * */
    private String TAG = "Holiday";
    private Button btn_location;
    private String[] location={"All","Tamil Nadu","California","Telangana"};
    private ArrayList<HolidayDate> holidayDateList = new ArrayList<>();
    private int checkItem=0;
    private RecyclerView recyclerView;
    private ConstraintLayout layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_holiday, container, false);

        /*
         * Assign the Values for the Particular View Elements
         * */
        btn_location = view.findViewById(R.id.btn_location);
        recyclerView = view.findViewById(R.id.holiday_recycler_view);
        layout = view.findViewById(R.id.layoutdata);

        /*
        * Get the List of Data
        * */
        getlist();

        /*
        * Click the Selection Dropdown to select Particular Location
        * */
        btn_location.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
            builder.setTitle("Select the Location:");
            checkItem=1;
            builder.setItems(location, (dialog, which) -> {
                checkItem = which;
                btn_location.setText(location[which]);
                setholidaylist();
            }).setNegativeButton("Cancel", (dialog, which) -> {
                if (checkItem < 0)
                    btn_location.setText(location[0]);
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
        /*
        * When append the list of data into the RecyclerView within the CardView
        * */
        setholidaylist();

        return view;
    }

    /*
     * When append the list of data into the RecyclerView within the CardView
     * */
    private void setholidaylist() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        HolidayListAdapter holidayadapter;
        if(btn_location.getText().toString().equals("Tamil Nadu")||btn_location.getText().toString().equals("All")){
            holidayadapter = new HolidayListAdapter(getActivity(),holidayDateList);
            recyclerView.setAdapter(holidayadapter);
        }
        else{
            holidayadapter =null;
            recyclerView.setAdapter(holidayadapter);
            Snackbar snackbar = Snackbar
                    .make(layout, "No Data Found", Snackbar.LENGTH_LONG);
            snackbar.show();
           // Toast.makeText(getActivity(), "No record found", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * Add the List of Data
     * */
    private void getlist() {

        holidayDateList = new ArrayList<>();

        HolidayDate holidayDate = new HolidayDate();
        holidayDate.setLeaveDate("15-Jan-2020");
        holidayDate.setDays("Wednesday");
        holidayDate.setDescription("Pongal");
        holidayDate.setState("Tamil Nadu");
        holidayDateList.add(holidayDate);

        HolidayDate holidayDate1  = new HolidayDate();
        holidayDate1.setLeaveDate("16-Jan-2020");
        holidayDate1.setDays("Thursday");
        holidayDate1.setDescription("Thiruvalluvar Day");
        holidayDate1.setState("Tamil Nadu");
        holidayDateList.add(holidayDate1);

        HolidayDate holidayDate2 = new HolidayDate();
        holidayDate2.setLeaveDate("20-Apr-2020");
        holidayDate2.setDays("Tuesday");
        holidayDate2.setDescription("Tamil New Year");
        holidayDate2.setState("Tamil Nadu");
        holidayDateList.add(holidayDate2);
    }
}
