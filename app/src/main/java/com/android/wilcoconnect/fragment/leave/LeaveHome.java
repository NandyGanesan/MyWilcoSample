package com.android.wilcoconnect.fragment.leave;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.api.ApiManager;
import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.fragment.leave.Onduty_other.ApplyOnDuty;
import com.android.wilcoconnect.fragment.leave.Onduty_other.ApproveOnDutyGrid;
import com.android.wilcoconnect.fragment.leave.Onduty_other.OnDuty;
import com.android.wilcoconnect.fragment.leave.Onduty_other.OnDutyApprovedList;
import com.android.wilcoconnect.fragment.leave.compensatory.ApplyCompensatory;
import com.android.wilcoconnect.fragment.leave.compensatory.ApproveCompOff;
import com.android.wilcoconnect.fragment.leave.compensatory.CompensatoryDetail;
import com.android.wilcoconnect.model.MenuList.MainMenu;
import com.android.wilcoconnect.model.MenuList.Menu;
import com.android.wilcoconnect.model.MenuList.SubMenu;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.android.wilcoconnect.shared.common.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class LeaveHome extends Fragment {

    private ArrayList<Fragment> fragments;
    private String TAG = "LeaveHome";
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private static final String MYPREFS_NAME = "logininfo";
    private AddRequest addRequest = new AddRequest();
    private Menu menu;
    private ArrayList<MainMenu> mainMenus = new ArrayList<>();
    private ArrayList<SubMenu> subMenuArrayList = new ArrayList<>();
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_leave_module_way, container, false);

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

        /*
         * Toolbar Initialization
         * And set the Title color and title
         * */
        Toolbar main_toolbar = view.findViewById(R.id.main_nonav_toolbar);
        main_toolbar.setTitle("LEAVE");
        main_toolbar.setTitleTextColor(getActivity().getColor(R.color.toolbarbackground));

        /*
         * Initialize the Elements
         * */
        viewPager = view.findViewById(R.id.leavepager);
        tabLayout = view.findViewById(R.id.leavetabLayout);

        ApiManager.getInstance().getMenuList(addRequest, new Callback<Menu>() {
            @Override
            public void onResponse(Call<Menu> call, Response<Menu> response) {
                menu= response.body();
                if(response.isSuccessful() && menu!=null){
                    mainMenus = menu.getData();
                    for(int i=0;i<mainMenus.size();i++) {
                        if (mainMenus.get(i).getMenuID() == 5){
                            subMenuArrayList = mainMenus.get(i).getSubMenu();
                        }
                    }
                    if(subMenuArrayList.size()>0){
                        /*
                         * Define the Fragment Array with Fragment
                         * */
                        fragments = new ArrayList<>();
                        int count = 0;
                        for (int i=0;i<subMenuArrayList.size();i++) {
                            if (subMenuArrayList.get(i).getMenuName().equals("Apply Leave")){
                                fragments.add(new Leave());
                                tabLayout.getTabAt(count).setText("MY LEAVE");
                                count++;
                                fragments.add(new LeaveCalenderForTeam());
                                tabLayout.getTabAt(count).setText("LEAVE CALENDER");
                            }
                            else if(subMenuArrayList.get(i).getMenuName().equals("Approve Leave")){
                                fragments.add(new ApproveLeaveFromGrid());
                                tabLayout.getTabAt(count).setText("APPROVE LEAVE");
                                count++;
                                fragments.add(new ApplyLeaveForTeam());
                                tabLayout.getTabAt(count).setText("APPLY LEAVE FOR TEAM");
                            }
                            else if(subMenuArrayList.get(i).getMenuName().equals("Approve On Duty")){
                                fragments.add(new ApproveOnDutyGrid());
                                tabLayout.getTabAt(count).setText("APPROVE ON DUTY");
                            }
                            else if(subMenuArrayList.get(i).getMenuName().equals("Holiday")){
                                fragments.add(new Holiday());
                                tabLayout.getTabAt(count).setText("HOLIDAY");
                            }
                            else if(subMenuArrayList.get(i).getMenuName().equals("Apply Comp-Off")){
                                fragments.add(new CompensatoryDetail());
                                tabLayout.getTabAt(count).setText("COMPENSATORY");
                            }
                            else if(subMenuArrayList.get(i).getMenuName().equals("Approve Comp-Off")){
                                fragments.add(new ApproveCompOff());
                                tabLayout.getTabAt(count).setText("APPROVE COMP-OFF");
                            }
                            else if(subMenuArrayList.get(i).getMenuName().equals("Apply On Duty")){
                                fragments.add(new OnDuty());
                                tabLayout.getTabAt(count).setText("ON DUTY");
                            }
                            count++;
                        }

                        if(fragments.size()>0){
                            /*
                             * Set the FragmentAdapter with the Fragment
                             * */
                            final FragmentAdapter pagerAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), getActivity().getApplicationContext(), fragments);
                            viewPager.setAdapter(pagerAdapter);
                            viewPager.setOffscreenPageLimit(fragments.size());
                            tabLayout.setupWithViewPager(viewPager);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Menu> call, Throwable t) {
                Log.e(TAG,t.getLocalizedMessage());
            }
        });

        /*
         * Define the Tab Layout Action
         * */
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
                    /*
                     * Style for Selected Tab
                     * */
                    @Override
                    public void onTabSelected(@NonNull TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                    }
                    /*
                     * Style for Unselected Tab
                     * */
                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                    }
                    /*
                     * Define the Reselected Tab Design
                     * */
                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);
                    }
                });

        return view;
    }
}