package com.android.wilcoconnect.fragment.profile;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.model.profile.BasicInformation;
import com.android.wilcoconnect.model.profile.ProfileMenu;
import com.android.wilcoconnect.shared.ProfileInformationDisplayAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileInformation extends DialogFragment {

    public static String TAG = "ProfileInformation";
    private HashMap<String, ArrayList<BasicInformation>> item = new HashMap<>();
    private ArrayList<BasicInformation> selectedList = new ArrayList<>();
   /* private HorizontalScrollView horizontalScrollView;
    private TableLayout table;*/

    @Override
    public View onCreateView(LayoutInflater  inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_information, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.profile_menu_data);
        /*horizontalScrollView = view.findViewById(R.id.scroll);
        table = view.findViewById(R.id.tableLayout);*/

        /*
         * Retrieve the Intent Value
         * */
        String value = this.getArguments().getString("selectedoption");
        Gson gson = new Gson();
        ProfileMenu menu = gson.fromJson(value, ProfileMenu.class);

        Toolbar profile_toolbar = view.findViewById(R.id.main_withnav_toolbar);
        profile_toolbar.setTitle(menu.getValues());
        profile_toolbar.setNavigationIcon(R.drawable.close);
        profile_toolbar.setNavigationOnClickListener(v -> dismiss());

        getHashmapvalue();
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

    private void getHashmapvalue(){

        item = new HashMap<>();
        ArrayList<BasicInformation> basicInformations=new ArrayList<>();

        BasicInformation information = new BasicInformation();
        information.setDataLabel("First Name");
        information.setDataDetail("Ranjith");
        basicInformations.add(information);

        BasicInformation information0 = new BasicInformation();
        information0.setDataLabel("Last Name");
        information0.setDataDetail("Senthilvel");
        basicInformations.add(information0);

        BasicInformation information1 = new BasicInformation();
        information1.setDataLabel("Father/Husband Name");
        information1.setDataDetail("");
        basicInformations.add(information1);

        BasicInformation information2 = new BasicInformation();
        information2.setDataLabel("Date of Birth");
        information2.setDataDetail("");
        basicInformations.add(information2);

        BasicInformation information3 = new BasicInformation();
        information3.setDataLabel("EMail ID");
        information3.setDataDetail("ranjiths@wilcosource.com");
        basicInformations.add(information3);

        BasicInformation information4 = new BasicInformation();
        information4.setDataLabel("Marital Status");
        information4.setDataDetail("Unmarried");
        basicInformations.add(information4);

        BasicInformation information5 = new BasicInformation();
        information5.setDataLabel("Contact Number-1");
        information5.setDataDetail("9655335476");
        basicInformations.add(information5);

        BasicInformation information6 = new BasicInformation();
        information6.setDataLabel("Contact Number-2");
        information6.setDataDetail("");
        basicInformations.add(information6);

        BasicInformation information7 = new BasicInformation();
        information7.setDataLabel("Pan Number");
        information7.setDataDetail("CJSPR7415K");
        basicInformations.add(information7);

        BasicInformation information8 = new BasicInformation();
        information8.setDataLabel("Aadhar Number");
        information8.setDataDetail("5653 9671 2415");
        basicInformations.add(information8);

        BasicInformation information9 = new BasicInformation();
        information9.setDataLabel("Personal Email ID");
        information9.setDataDetail("");
        basicInformations.add(information9);

        BasicInformation information11 = new BasicInformation();
        information11.setDataLabel("Band Level");
        information11.setDataDetail("B-1");
        basicInformations.add(information11);

        BasicInformation information12 = new BasicInformation();
        information12.setDataLabel("Designation");
        information12.setDataDetail("Software Engineer");
        basicInformations.add(information12);

        BasicInformation information13 = new BasicInformation();
        information13.setDataLabel("Stream");
        information13.setDataDetail("General");
        basicInformations.add(information13);

        BasicInformation information14 = new BasicInformation();
        information14.setDataLabel("Work Branch");
        information14.setDataDetail("Chennai - Guindy");
        basicInformations.add(information14);

        BasicInformation information15 = new BasicInformation();
        information15.setDataLabel("Department");
        information15.setDataDetail("Delivery");
        basicInformations.add(information15);

        BasicInformation information16 = new BasicInformation();
        information16.setDataLabel("Work Extension");
        information16.setDataDetail("");
        basicInformations.add(information16);

        BasicInformation information17 = new BasicInformation();
        information17.setDataLabel("Work Number");
        information17.setDataDetail("");
        basicInformations.add(information17);

        BasicInformation information18 = new BasicInformation();
        information18.setDataLabel("Height");
        information18.setDataDetail("");
        basicInformations.add(information18);

        BasicInformation information19 = new BasicInformation();
        information19.setDataLabel("Weight");
        information19.setDataDetail("");
        basicInformations.add(information19);

        BasicInformation information20 = new BasicInformation();
        information20.setDataLabel("Date of Join");
        information20.setDataDetail("");
        basicInformations.add(information20);

        item.put("Basic information", basicInformations);
    }
}
