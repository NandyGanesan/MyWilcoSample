package com.android.wilcoconnect.fragment.leave;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.api.ApiManager;
import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.model.leave.ApproveList;
import com.android.wilcoconnect.model.leave.LeaveCalender;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class LeaveCalenderForTeam extends Fragment {

    private CalendarView calendarview;
    private static String TAG = "LeaveCalenderForTeam";
    private ArrayList<ApproveList> leavelist = new ArrayList<>();
    private AddRequest addRequest = new AddRequest();
    private static String MYPREFS_NAME = "logininfo";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leave_calender_for_team, container, false);

        calendarview = view.findViewById(R.id.custom_calender);

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
        SharedPreferences prefs = getActivity().getSharedPreferences(MYPREFS_NAME, MODE_PRIVATE);
        if(prefs!=null) {
            addRequest.setEmail(prefs.getString("Email", "No name defined"));
            addRequest.setCompanyCode(prefs.getString("CompanyCode", "No name defined"));
            addRequest.setEmployeeID(prefs.getString("EmployeeID", "No name defined"));
        }

        set_leave_list();
        calendarview.setOnDayClickListener(eventDay -> {
            set_leave_list();
            Calendar clickedDayCalendar = eventDay.getCalendar();
            SimpleDateFormat format1 = new SimpleDateFormat("dd-M-yyyy");
            clickedDayCalendar.add(Calendar.DATE,0);
            String formatted = format1.format(clickedDayCalendar.getTime());
            System.out.println(formatted);
            ArrayList<ApproveList> leavedetail = new ArrayList<>();
            for(int i=0;i<leavelist.size();i++){
                if(leavelist.get(i).getFromDate().equals(formatted)){
                    ApproveList approveList = new ApproveList();
                    approveList.setFirstName(leavelist.get(i).getFirstName());
                    approveList.setLeaveTypeText(leavelist.get(i).getLeaveTypeText());
                    approveList.setFromDate(leavelist.get(i).getFromDate());
                    approveList.setToDate(leavelist.get(i).getToDate());
                    approveList.setRequestRemarks(leavelist.get(i).getRequestRemarks());
                    leavedetail.add(approveList);
                }
            }
            if(leavedetail.size()>0){
                Gson gson = new Gson();
                String s = gson.toJson(leavedetail);
                newInstance(s);
            }
            else{

            }
        });

        return view;
    }

    private void set_leave_list() {
        leavelist = new ArrayList<>();

        ApiManager.getInstance().getLeaveDetailforCalender(addRequest, new Callback<LeaveCalender>() {
            @Override
            public void onResponse(Call<LeaveCalender> call, Response<LeaveCalender> response) {
                if(response.body()!=null && response.isSuccessful()){
                    leavelist = response.body().getData();
                }
            }

            @Override
            public void onFailure(Call<LeaveCalender> call, Throwable t) {
                Log.e(TAG,t.getLocalizedMessage());
            }
        });

        add_events();
    }

    private void add_events(){
        List<EventDay> events = new ArrayList<>();
        String[] datedata;
        for(int data=0;data<leavelist.size();data++){
            String date = leavelist.get(data).getFromDate();
            datedata = date.split("-");
            int[] dataintarray = new int[datedata.length];
            for(int j=0;j<datedata.length;j++){
                dataintarray[j]=Integer.parseInt(datedata[j]);
            }
            Calendar calendar = Calendar.getInstance();
            calendar.set(dataintarray[2],dataintarray[1]-1,dataintarray[0]);
            events.add(new EventDay(calendar, R.drawable.event));
        }
        calendarview.setEvents(events);
    }

    private void newInstance(String s) {
        ViewCalenderLeaveDetail viewCalenderLeaveDetail = new ViewCalenderLeaveDetail();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("leavecalender", s);
        viewCalenderLeaveDetail.setArguments(bundle);
        viewCalenderLeaveDetail.show(transaction,viewCalenderLeaveDetail.TAG);
    }
}
