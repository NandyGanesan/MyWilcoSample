package com.android.wilcoconnect.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.api.ApiManager;
import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.model.MenuList.MainMenu;
import com.android.wilcoconnect.model.MenuList.Menu;
import com.android.wilcoconnect.model.MenuList.SubMenu;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.android.wilcoconnect.shared.common.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ExpenseHome extends Fragment {

    /*
     * Initialize the variables to access the Module
     * */
    private ArrayList<Fragment> fragments;
    private static String TAG = "LeaveHome";
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private static final String MYPREFS_NAME = "logininfo";
    private AddRequest addRequest = new AddRequest();
    private Menu menu;
    private ArrayList<MainMenu> mainMenus = new ArrayList<>();
    private ArrayList<SubMenu> subMenuArrayList = new ArrayList<>();
    private String[] tabName = new String[30];
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_expense_home, container, false);

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
        main_toolbar.setTitle("Expense");
        main_toolbar.setTitleTextColor(getActivity().getColor(R.color.toolbarbackground));

        /*
         * Initialize the Elements
         * */
        viewPager = view.findViewById(R.id.expensePager);
        tabLayout = view.findViewById(R.id.expenseTabLayout);

        /*
         * API Call to get the Over all Menu List
         * */
        ApiManager.getInstance().getMenuList(addRequest, new Callback<Menu>() {
            //API Success
            @Override
            public void onResponse(Call<Menu> call, Response<Menu> response) {
                menu= response.body();
                if(response.isSuccessful() && menu!=null){
                    mainMenus = menu.getData();
                    for(int i=0;i<mainMenus.size();i++) {
                        if (mainMenus.get(i).getMenuID() == 5174){
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
                            if (subMenuArrayList.get(i).getMenuName().equals("Raise Claim")){
                                fragments.add(new Expense());
                                tabName[count] = "FOOD";
                                count++;
                            }
                        }

                        if(fragments.size()>0){
                            /*
                             * Set the FragmentAdapter with the Fragment
                             * */
                            final FragmentAdapter pagerAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), getActivity().getApplicationContext(), fragments);
                            viewPager.setAdapter(pagerAdapter);
                            viewPager.setOffscreenPageLimit(fragments.size());
                            tabLayout.setupWithViewPager(viewPager);
                            /*
                             * Assign the Tab Name
                             * */
                            for(int i = 0; i<fragments.size(); i++) {
                                tabLayout.getTabAt(i).setText(tabName[i]);
                            }

                        }
                    }
                }
            }
            //API Failure
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