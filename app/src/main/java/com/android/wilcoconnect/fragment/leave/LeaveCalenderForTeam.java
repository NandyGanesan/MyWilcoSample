package com.android.wilcoconnect.fragment.leave;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.model.leave.ApproveList;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LeaveCalenderForTeam extends Fragment {

    private CalendarView calendarview;
    private ArrayList<ApproveList> leavelist = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leave_calender_for_team, container, false);

        calendarview = view.findViewById(R.id.custom_calender);

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
                    approveList.setEmployeeName(leavelist.get(i).getEmployeeName());
                    approveList.setLeaveType(leavelist.get(i).getLeaveType());
                    approveList.setFromDate(leavelist.get(i).getFromDate());
                    approveList.setToDate(leavelist.get(i).getToDate());
                    approveList.setRemarks(leavelist.get(i).getRemarks());
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

        ApproveList list = new ApproveList();
        list.setEmployeeName("Ranjith Senthilvel");
        list.setLeaveType("Casual Leave");
        list.setFromDate("12-2-2020");
        list.setToDate("12-2-2020");
        list.setRemarks("Need Leave");
        leavelist.add(list);

        ApproveList list1 = new ApproveList();
        list1.setEmployeeName("Nandhini Ganesan");
        list1.setLeaveType("Sick Leave");
        list1.setFromDate("24-2-2020");
        list1.setToDate("24-2-2020");
        list1.setRemarks("Native");
        leavelist.add(list1);

        ApproveList list2 = new ApproveList();
        list2.setEmployeeName("Pooja Madhanagopal");
        list2.setLeaveType("Compensatory Off");
        list2.setFromDate("15-3-2020");
        list2.setToDate("15-3-2020");
        list2.setRemarks("Emergency");
        leavelist.add(list2);

        ApproveList list3 = new ApproveList();
        list3.setEmployeeName("Bavadharini Asokan");
        list3.setLeaveType("Casual Leave");
        list3.setFromDate("20-1-2020");
        list3.setToDate("20-1-2020");
        list3.setRemarks("Native");
        leavelist.add(list3);

        ApproveList list4 = new ApproveList();
        list4.setEmployeeName("Pooja Madhanagopal");
        list4.setLeaveType("Sick Leave");
        list4.setFromDate("20-12-2019");
        list4.setToDate("20-12-2019");
        list4.setRemarks("Need permission");
        leavelist.add(list4);

        ApproveList list5 = new ApproveList();
        list5.setEmployeeName("Bavadharini Asokan");
        list5.setLeaveType("Casual Leave");
        list5.setFromDate("20-12-2019");
        list5.setToDate("20-12-2019");
        list5.setRemarks("Native");
        leavelist.add(list5);
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
