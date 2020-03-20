package com.android.wilcoconnect.fragment.leave;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.model.MenuList.Menu;
import com.android.wilcoconnect.model.MenuList.SubMenu;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.android.wilcoconnect.shared.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class LeaveHome extends Fragment {

    private ArrayList<Fragment> fragments;
    private String TAG = "LeaveHome";
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private static final String MY_PREFS_NAME = "MyPrefsFile";
    private AddRequest addRequest;
    private Menu menu;
    private ArrayList<SubMenu> subMenuArrayList = new ArrayList<>();
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_leave_module_way, container, false);

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
        fragments = new ArrayList<>();

        /*
         * Define the Fragment Array with Fragment
         * */
        fragments.add(new Leave());
        fragments.add(new ApplyLeave());
        fragments.add(new Holiday());
        fragments.add(new ApproveLeaveFromGrid());
        fragments.add(new ApplyLeaveForTeam());
        fragments.add(new LeaveCalenderForTeam());

        /*
         * Set the FragmentAdapter with the Fragment
         * */
        final FragmentAdapter pagerAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), getActivity().getApplicationContext(), fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(5);
        tabLayout.setupWithViewPager(viewPager);

        /*
         * Define the Tab Layout index based Icons
         * */
        tabLayout.getTabAt(0).setText("MY LEAVE");
        tabLayout.getTabAt(1).setText("APPLY LEAVE");
        tabLayout.getTabAt(2).setText("HOLIDAY");
        tabLayout.getTabAt(3).setText("APPROVE LEAVE");
        tabLayout.getTabAt(4).setText("APPLY LEAVE FOR TEAM");
        tabLayout.getTabAt(5).setText("LEAVE CALENDER");

        /*
         * Define the Tab Layout Action
         * */
        tabLayout.addOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
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
                }
        );

        return view;
    }
}
