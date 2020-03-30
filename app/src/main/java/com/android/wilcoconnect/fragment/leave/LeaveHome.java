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
import com.android.wilcoconnect.model.MenuList.MainMenu;
import com.android.wilcoconnect.model.MenuList.Menu;
import com.android.wilcoconnect.model.MenuList.SubMenu;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.android.wilcoconnect.shared.FragmentAdapter;
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


        getSubMenu();
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
                });

        return view;
    }

    private void getSubMenu() {
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
                }
            }

            @Override
            public void onFailure(Call<Menu> call, Throwable t) {
                Log.e(TAG,t.getLocalizedMessage());
            }
        });
    }
}
