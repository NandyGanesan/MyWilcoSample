package com.android.wilcoconnect.fragment.profile;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.api.ApiManager;
import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.model.profile.BasicDetails;
import com.android.wilcoconnect.model.profile.BasicInformation;
import com.android.wilcoconnect.model.profile.EducationDetails;
import com.android.wilcoconnect.model.profile.FamilyDetails;
import com.android.wilcoconnect.model.profile.LastPositionDetails;
import com.android.wilcoconnect.model.profile.ProfileMenu;
import com.android.wilcoconnect.shared.ProfileInformationDisplayAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.android.wilcoconnect.app.MainApplication.MY_PREFS_NAME;

public class ProfileInformation extends DialogFragment {

    public static String TAG = "ProfileInformation";
    private HashMap<String, ArrayList<BasicInformation>> item = new HashMap<>();
    private ArrayList<BasicInformation> selectedList = new ArrayList<>();
    private BasicDetails basicinformationdata = new BasicDetails();
    private EducationDetails educationDetailsdata = new EducationDetails();
    private FamilyDetails familyDetailsdata = new FamilyDetails();
    private LastPositionDetails lastPositionDetailsdata = new LastPositionDetails();
    private static final String MYPREFS_NAME = "MyPrefsFile";
    private String EmployeeId;
    private RecyclerView recyclerView;
    private ProfileMenu menu;

    @Override
    public View onCreateView(LayoutInflater  inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_information, container, false);

        recyclerView = view.findViewById(R.id.profile_menu_data);

        /*
         * Retrieve the Intent Value
         * */
        String value = this.getArguments().getString("selectedoption");
        Gson gson = new Gson();
        menu = gson.fromJson(value, ProfileMenu.class);

        Toolbar profile_toolbar = view.findViewById(R.id.main_withnav_toolbar);
        profile_toolbar.setTitle(menu.getValues());
        profile_toolbar.setNavigationIcon(R.drawable.close);
        profile_toolbar.setNavigationOnClickListener(v -> dismiss());

        /*
         * Get the Header
         * */
        SharedPreferences preferences = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        if (preferences != null) {
            MainApplication.token_data = preferences.getString("header", "No name defined");
        }

        /*
         * Get the shared preference data to assign the another object..
         * */
        SharedPreferences prefs = getActivity().getSharedPreferences(MYPREFS_NAME, MODE_PRIVATE);
        EmployeeId = prefs.getString("EmployeeID", "No name defined");

        if(menu.getValues().equals("Basic information")||menu.getValues().equals("Address")||menu.getValues().equals("Last Position")){
            get_value();
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    private void get_Hashmap_value(){
        item = new HashMap<>();
        ArrayList<BasicInformation> basicInformations=new ArrayList<>();

        BasicInformation information = new BasicInformation();
        information.setDataLabel("First Name");
        information.setDataDetail(basicinformationdata.getData().getFirstName());
        basicInformations.add(information);

        BasicInformation information0 = new BasicInformation();
        information0.setDataLabel("Last Name");
        information0.setDataDetail(basicinformationdata.getData().getLastName());
        basicInformations.add(information0);

        BasicInformation information1 = new BasicInformation();
        information1.setDataLabel("Father/Husband Name");
        information1.setDataDetail(basicinformationdata.getData().getF_H_Name());
        basicInformations.add(information1);

        BasicInformation information2 = new BasicInformation();
        information2.setDataLabel("Date of Birth");
        information2.setDataDetail(basicinformationdata.getData().getDateofBirth());
        basicInformations.add(information2);

        BasicInformation information3 = new BasicInformation();
        information3.setDataLabel("EMail ID");
        information3.setDataDetail(basicinformationdata.getData().getEMailID());
        basicInformations.add(information3);

        BasicInformation information4 = new BasicInformation();
        information4.setDataLabel("Marital Status");
        information4.setDataDetail(basicinformationdata.getData().getMaritalStatus());
        basicInformations.add(information4);

        BasicInformation information5 = new BasicInformation();
        information5.setDataLabel("Contact Number-1");
        information5.setDataDetail(basicinformationdata.getData().getContactNumber1());
        basicInformations.add(information5);

        BasicInformation information6 = new BasicInformation();
        information6.setDataLabel("Contact Number-2");
        information6.setDataDetail(basicinformationdata.getData().getContactNumber2());
        basicInformations.add(information6);

        BasicInformation information7 = new BasicInformation();
        information7.setDataLabel("Pan Number");
        information7.setDataDetail(basicinformationdata.getData().getPanNumber());
        basicInformations.add(information7);

        BasicInformation information8 = new BasicInformation();
        information8.setDataLabel("Aadhar Number");
        information8.setDataDetail(basicinformationdata.getData().getAadharNumber());
        basicInformations.add(information8);

        BasicInformation information9 = new BasicInformation();
        information9.setDataLabel("Personal Email ID");
        information9.setDataDetail(basicinformationdata.getData().getPersonalEmailID());
        basicInformations.add(information9);

        BasicInformation information11 = new BasicInformation();
        information11.setDataLabel("Band Level");
        information11.setDataDetail(basicinformationdata.getData().getEmpBandLevel());
        basicInformations.add(information11);

        BasicInformation information12 = new BasicInformation();
        information12.setDataLabel("Designation");
        information12.setDataDetail(basicinformationdata.getData().getDesignation());
        basicInformations.add(information12);

        BasicInformation information13 = new BasicInformation();
        information13.setDataLabel("Stream");
        information13.setDataDetail(basicinformationdata.getData().getStream());
        basicInformations.add(information13);

        BasicInformation information14 = new BasicInformation();
        information14.setDataLabel("Work Branch");
        information14.setDataDetail(basicinformationdata.getData().getWorkBranch());
        basicInformations.add(information14);

        BasicInformation information15 = new BasicInformation();
        information15.setDataLabel("Department");
        information15.setDataDetail(basicinformationdata.getData().getDepartment());
        basicInformations.add(information15);

        BasicInformation information16 = new BasicInformation();
        information16.setDataLabel("Work Extension");
        information16.setDataDetail(basicinformationdata.getData().getWorkExtension());
        basicInformations.add(information16);

        BasicInformation information17 = new BasicInformation();
        information17.setDataLabel("Work Number");
        information17.setDataDetail(basicinformationdata.getData().getWorkNumber());
        basicInformations.add(information17);

        BasicInformation information18 = new BasicInformation();
        information18.setDataLabel("Height");
        information18.setDataDetail(basicinformationdata.getData().getHeight());
        basicInformations.add(information18);

        BasicInformation information19 = new BasicInformation();
        information19.setDataLabel("Weight");
        information19.setDataDetail(basicinformationdata.getData().getWeight());
        basicInformations.add(information19);

        BasicInformation information20 = new BasicInformation();
        information20.setDataLabel("Date of Join");
        information20.setDataDetail(basicinformationdata.getData().getDateofJoin());
        basicInformations.add(information20);

        item.put("Basic information", basicInformations);

        ArrayList<BasicInformation> AddressDetail=new ArrayList<>();

        BasicInformation addressinfo = new BasicInformation();
        addressinfo.setDataLabel("Communication Address");
        addressinfo.setDataDetail("");
        AddressDetail.add(addressinfo);

        BasicInformation addressinfo1 = new BasicInformation();
        addressinfo1.setDataLabel("Address");
        addressinfo1.setDataDetail(basicinformationdata.getData().getCommunication_Address1());
        AddressDetail.add(addressinfo1);

        BasicInformation addressinfo2 = new BasicInformation();
        addressinfo2.setDataLabel("Country");
        addressinfo2.setDataDetail(basicinformationdata.getData().getCommunicationCountryName());
        AddressDetail.add(addressinfo2);

        BasicInformation addressinfo3 = new BasicInformation();
        addressinfo3.setDataLabel("State");
        addressinfo3.setDataDetail(basicinformationdata.getData().getCommunicationStateName());
        AddressDetail.add(addressinfo3);

        BasicInformation addressinfo4 = new BasicInformation();
        addressinfo4.setDataLabel("City");
        addressinfo4.setDataDetail(basicinformationdata.getData().getCommunicationCity());
        AddressDetail.add(addressinfo4);

        BasicInformation addressinfo5 = new BasicInformation();
        addressinfo5.setDataLabel("Pincode");
        addressinfo5.setDataDetail(basicinformationdata.getData().getCommAddressPincode());
        AddressDetail.add(addressinfo5);

        BasicInformation addressinfo6 = new BasicInformation();
        addressinfo6.setDataLabel("Permanent Address");
        addressinfo6.setDataDetail("");
        AddressDetail.add(addressinfo6);

        BasicInformation addressinfo7 = new BasicInformation();
        addressinfo7.setDataLabel("Address");
        addressinfo7.setDataDetail(basicinformationdata.getData().getAddress1());
        AddressDetail.add(addressinfo7);

        BasicInformation addressinfo8 = new BasicInformation();
        addressinfo8.setDataLabel("Country");
        addressinfo8.setDataDetail(basicinformationdata.getData().getCountryName());
        AddressDetail.add(addressinfo8);

        BasicInformation addressinfo9 = new BasicInformation();
        addressinfo9.setDataLabel("State");
        addressinfo9.setDataDetail(basicinformationdata.getData().getStateName());
        AddressDetail.add(addressinfo9);

        BasicInformation addressinfo0 = new BasicInformation();
        addressinfo0.setDataLabel("City");
        addressinfo0.setDataDetail(basicinformationdata.getData().getCity());
        AddressDetail.add(addressinfo0);

        BasicInformation addressinfo11 = new BasicInformation();
        addressinfo11.setDataLabel("Pincode");
        addressinfo11.setDataDetail(basicinformationdata.getData().getAddressPincode());
        AddressDetail.add(addressinfo11);

        item.put("Address",AddressDetail);

        ArrayList<BasicInformation> lastpositiondetail=new ArrayList<>();

        BasicInformation lastposition = new BasicInformation();
        lastposition.setDataLabel("Designation");
        lastposition.setDataDetail(lastPositionDetailsdata.getData().getDesignation());
        lastpositiondetail.add(lastposition);

        BasicInformation lastposition1 = new BasicInformation();
        lastposition1.setDataLabel("Organization");
        lastposition1.setDataDetail(lastPositionDetailsdata.getData().getOrganisation());
        lastpositiondetail.add(lastposition1);

        BasicInformation lastposition2 = new BasicInformation();
        lastposition2.setDataLabel("Job Responsibilities");
        lastposition2.setDataDetail(lastPositionDetailsdata.getData().getJobResponsibilities());
        lastpositiondetail.add(lastposition2);

        BasicInformation lastposition3 = new BasicInformation();
        lastposition3.setDataLabel("Gross Salary(Per Month)");
        lastposition3.setDataDetail(""+lastPositionDetailsdata.getData().getGrossSalaryPerMonth());
        lastpositiondetail.add(lastposition3);

        BasicInformation lastposition4 = new BasicInformation();
        lastposition4.setDataLabel("Joined Date");
        lastposition4.setDataDetail(lastPositionDetailsdata.getData().getLastPositionDateofJoin());
        lastpositiondetail.add(lastposition4);

        BasicInformation lastposition5 = new BasicInformation();
        lastposition5.setDataLabel("Designation at Joined Date");
        lastposition5.setDataDetail(lastPositionDetailsdata.getData().getDateofJoinDesignation());
        lastpositiondetail.add(lastposition5);

        BasicInformation lastposition6 = new BasicInformation();
        lastposition6.setDataLabel("Reported Person");
        lastposition6.setDataDetail(lastPositionDetailsdata.getData().getReportingTo());
        lastpositiondetail.add(lastposition6);

        BasicInformation lastposition7 = new BasicInformation();
        lastposition7.setDataLabel("Reported Designation");
        lastposition7.setDataDetail(lastPositionDetailsdata.getData().getReportingToDesignation());
        lastpositiondetail.add(lastposition7);

        BasicInformation lastposition8 = new BasicInformation();
        lastposition8.setDataLabel("Address");
        lastposition8.setDataDetail(lastPositionDetailsdata.getData().getAddress1());
        lastpositiondetail.add(lastposition8);

        BasicInformation lastposition9 = new BasicInformation();
        lastposition9.setDataLabel("Country");
        lastposition9.setDataDetail(lastPositionDetailsdata.getData().getCountryName());
        lastpositiondetail.add(lastposition9);

        BasicInformation lastposition0 = new BasicInformation();
        lastposition0.setDataLabel("State");
        lastposition0.setDataDetail(lastPositionDetailsdata.getData().getStateName());
        lastpositiondetail.add(lastposition0);

        BasicInformation lastposition00 = new BasicInformation();
        lastposition00.setDataLabel("City");
        lastposition00.setDataDetail(lastPositionDetailsdata.getData().getLastPosCity());
        lastpositiondetail.add(lastposition00);

        item.put("Last Position",lastpositiondetail);

        if (item.containsKey(menu.getValues())) {
            selectedList = new ArrayList<>();
            selectedList = new ArrayList<>(item.get(menu.getValues()));
            selectedList.retainAll(item.get(menu.getValues()));
        }
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        if(selectedList.size()>0){
            ProfileInformationDisplayAdapter adapter = new ProfileInformationDisplayAdapter(selectedList,getActivity());
            recyclerView.setAdapter(adapter);
        }
    }

    private void get_value() {
        /*
         * Get the overall profile Basic Detail
         * */
        ApiManager.getInstance().getBasicDetail(EmployeeId, new Callback<BasicDetails>() {
            /*
             * Get the Api success..
             * */
            @Override
            public void onResponse(Call<BasicDetails> call, Response<BasicDetails> response) {
                basicinformationdata = response.body();
                if(basicinformationdata!=null){
                    /*
                     * Get the overall Last Position Detail
                     * */
                    ApiManager.getInstance().getLastPostionDetail(EmployeeId, new Callback<LastPositionDetails>() {
                        /*
                         * Get the Api success..
                         * */
                        @Override
                        public void onResponse(Call<LastPositionDetails> call, Response<LastPositionDetails> response) {
                            lastPositionDetailsdata = response.body();
                            if(lastPositionDetailsdata!=null){
                                get_Hashmap_value();
                            }
                        }

                        @Override
                        public void onFailure(Call<LastPositionDetails> call, Throwable t) {
                            Log.e(TAG, Objects.requireNonNull(t.getLocalizedMessage()));
                        }
                    });
                }
            }

            /*
             * Get the Api Failure
             * */
            @Override
            public void onFailure(Call<BasicDetails> call, Throwable t) {
                Log.e(TAG, Objects.requireNonNull(t.getLocalizedMessage()));
            }
        });
    }
}
