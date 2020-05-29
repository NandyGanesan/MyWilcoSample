package com.android.wilcoconnect.fragment.expense;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.api.ApiManager;
import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.model.expense.FoodExpenseData;
import com.android.wilcoconnect.model.expense.FoodExpenseDetail;
import com.android.wilcoconnect.model.leave.ApprovePost;
import com.android.wilcoconnect.model.leave.Onduty.OnDutyApprovePost;
import com.android.wilcoconnect.model.leave.compensatory.CompOffApprovePost;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.android.wilcoconnect.network_interface.DialogListener;
import com.android.wilcoconnect.network_interface.RecyclerViewListener;
import com.android.wilcoconnect.shared.expense.FoodExpenseAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class FoodExpense extends Fragment implements DialogListener {

    private static String TAG = "FoodExpense";
    private RecyclerView recyclerView;
    private AddRequest addRequest = new AddRequest();
    private static String MYPREFS_NAME = "logininfo";
    private TextView dataNotFound;
    private ArrayList<FoodExpenseData> data = new ArrayList<>();
    FoodExpenseAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food_expense, container, false);

        /*
         * Define the UI Element
         * */
        recyclerView = view.findViewById(R.id.recycler_view);
        dataNotFound = view.findViewById(R.id.label_name);

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
        * Api call to get the Data
        * */
        ApiManager.getInstance().getFoodExpense(addRequest, new Callback<FoodExpenseDetail>() {
            @Override
            public void onResponse(Call<FoodExpenseDetail> call, Response<FoodExpenseDetail> response) {
                data = new ArrayList<>();
                if(response.body()!=null && response.isSuccessful()){
                    data = response.body().getData();
                    display_data();
                }
            }

            @Override
            public void onFailure(Call<FoodExpenseDetail> call, Throwable t) {
                Log.e(TAG,t.getLocalizedMessage());
            }
        });

        /*
         * Click the FloatingActionButton Action or call another Activity
         */
        FloatingActionButton add_new_food_expense = view.findViewById(R.id.fab_add_task);
        add_new_food_expense.setOnClickListener(v -> {
            apply_new_food_expense();
        });

        return view;
    }

    /*
    * Open the Dialog Fragment
    * */
    private void apply_new_food_expense() {
        ApplyFoodExpense foodExpense = new ApplyFoodExpense();
        foodExpense.setTargetFragment(this, 0);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        foodExpense.show(transaction,foodExpense.TAG);
    }

    /*
    * Set the Recycler View Data
    * */
    private void display_data() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        /*
         * Adapter data is Empty
         * */
        if(data.size()<=0){
            recyclerView.setAdapter(null);
            recyclerView.setVisibility(View.GONE);
            dataNotFound.setVisibility(View.VISIBLE);
        }
        /*
         * Adapter data is not an Empty
         * */
        adapter = new FoodExpenseAdapter(data, getActivity(), new RecyclerViewListener() {
                    @Override
                    public void onClick(View view, String value) {
                        newInstance(value);
                    }
                    @Override
                    public void onClick(View view, ApprovePost post) {}
                    @Override
                    public void OnStore(View view, OnDutyApprovePost postData) {}
                    @Override
                    public void OnCompOffStore(View view, CompOffApprovePost post) {}
                });
        recyclerView.setVisibility(View.VISIBLE);
        dataNotFound.setVisibility(View.GONE);
        recyclerView.setAdapter(adapter);
    }

    /*
     * Call the Apply Leave Dialog Fragment to Store New Leave
     * */
    private void newInstance(String s) {
        ApplyFoodExpense edit = new ApplyFoodExpense();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("FoodExpenseEdit", s);
        edit.setArguments(bundle);
        edit.show(transaction,ApplyFoodExpense.TAG);
    }

    /*
     * Listener - Return Value from the Dialog Fragment
     * */
    @Override
    public void onDialogClick(String value) {
        if(value.equals("Success")){
            replaceFragment();
        }
    }

    /*
     * After Data Submission to refresh the Fragment
     * */
    private void replaceFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.food_expense_frame, new FoodExpense());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
