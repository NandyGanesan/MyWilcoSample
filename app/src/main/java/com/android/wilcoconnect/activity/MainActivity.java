package com.android.wilcoconnect.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;

import com.android.wilcoconnect.api.ApiManager;
import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.fragment.expense.ExpenseHome;
import com.android.wilcoconnect.fragment.leave.LeaveHome;
import com.android.wilcoconnect.fragment.MenuFragment;
import com.android.wilcoconnect.fragment.profile.Profile;
import com.android.wilcoconnect.fragment.wilcoconnect.WilcoConnect;
import com.android.wilcoconnect.R;
import com.android.wilcoconnect.model.MenuList.MainMenu;
import com.android.wilcoconnect.model.MenuList.Menu;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.android.wilcoconnect.shared.common.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    /*
     * Initialize the XML element or views
     * and then declare the Array and tag
     * */
    private ArrayList<Fragment> fragments;
    private String TAG = "MainActivity";
    private Toolbar main_toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private static final String MYPREFS_NAME = "logininfo";
    private AddRequest addRequest = new AddRequest();
    private Menu menu;
    private ArrayList<MainMenu> mainMenus = new ArrayList<>();
    private String[] Menus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Get the Header
         * */
        SharedPreferences preferences = getSharedPreferences(MainApplication.MY_PREFS_NAME, MODE_PRIVATE);
        if (preferences != null) {
            MainApplication.token_data = preferences.getString("header", "No name defined");
        }

        /*
         * Get the shared preference data to assign the another object..
         * */
        SharedPreferences prefs = getSharedPreferences(MYPREFS_NAME, MODE_PRIVATE);
        if(prefs!=null) {
            addRequest.setEmail(prefs.getString("Email", "No name defined"));
            addRequest.setCompanyCode(prefs.getString("CompanyCode", "No name defined"));
            addRequest.setEmployeeID(prefs.getString("EmployeeID", "No name defined"));
        }

        getMenuList();

        /*
         * Toolbar Initialization
         * And set the Title color and title
         * */
        main_toolbar = findViewById(R.id.main_nonav_toolbar);
        setSupportActionBar(main_toolbar);
        main_toolbar.setTitle("MYWILCO HOME");
        main_toolbar.setTitleTextColor(getColor(R.color.toolbarbackground));

        /*
         * Initialize the Elements
         * */
        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tabLayout);
        fragments = new ArrayList<>();

        /*
         * Define the Fragment Array with Fragment
         * */
        fragments.add(new WilcoConnect());
        fragments.add(new LeaveHome());
        fragments.add(new Profile());
        fragments.add(new ExpenseHome());
        fragments.add(new MenuFragment());

        /*
         * Set the FragmentAdapter with the Fragment
         * */
        FragmentAdapter pagerAdapter = new FragmentAdapter(getSupportFragmentManager(), getApplicationContext(), fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(viewPager);

        /*
         * Define the Tab Layout index based Icons
         * */
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_add_circle_outline_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.leave);
        tabLayout.getTabAt(2).setIcon(R.drawable.user);
        tabLayout.getTabAt(3).setIcon(R.drawable.home);
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_dehaze_black_24dp);

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
                        int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.orange);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    }

                    /*
                     * Style for Unselected Tab
                     * */
                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.white);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    }

                    /*
                     * Define the Reselected Tab Design
                     * */
                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);
                        int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.orange);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    }
                }
        );
    }

    private void getMenuList() {
        ApiManager.getInstance().getMenuList(addRequest, new Callback<Menu>() {
            @Override
            public void onResponse(Call<Menu> call, Response<Menu> response) {
                menu= response.body();
                if(response.isSuccessful() && menu!=null){
                    mainMenus = menu.getData();
                    Menus = new String[mainMenus.size()];
                    for(int i=0;i<mainMenus.size();i++) {
                        Menus[i] = mainMenus.get(i).getMenuName();
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