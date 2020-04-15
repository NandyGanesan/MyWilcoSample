package com.android.wilcoconnect.fragment.profile;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.model.leave.ApprovePost;
import com.android.wilcoconnect.model.leave.Onduty.OnDutyApprovePost;
import com.android.wilcoconnect.model.leave.compensatory.CompOffApprovePost;
import com.android.wilcoconnect.model.profile.ProfileMenu;
import com.android.wilcoconnect.network_interface.RecyclerViewListener;
import com.android.wilcoconnect.shared.profile.ProfileAdapter;

import java.util.ArrayList;

public class Profile extends Fragment {

    private static String TAG = "Profile";
    private Toolbar profile_toolbar;
    private View view;
    private ArrayList<ProfileMenu> menu = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        profile_toolbar = view.findViewById(R.id.main_nonav_toolbar);
        profile_toolbar.setTitle("PROFILE");

        recyclerView = view.findViewById(R.id.griddata);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        getMenu();
        if (menu.size()>0) {
            ProfileAdapter adapter = new ProfileAdapter(getActivity(), menu, new RecyclerViewListener() {
                @Override
                public void onClick(View view, String value) {
                    newInstance(value);
                }
                @Override
                public void OnStore(View view, OnDutyApprovePost postData) {}
                @Override
                public void OnCompOffStore(View view, CompOffApprovePost post) {}
                @Override
                public void onClick(View view, ApprovePost post) {}
            });
            recyclerView.setAdapter(adapter);
        } else {
            ProfileAdapter adapter = null;
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    private void getMenu() {
        menu = new ArrayList<>();
        ProfileMenu menuobj = new ProfileMenu();
        menuobj.setValues("Basic information");
        menuobj.setImages(R.drawable.user);
        menu.add(menuobj);

        ProfileMenu menuobj1 = new ProfileMenu();
        menuobj1.setValues("Address");
        menuobj1.setImages(R.drawable.home);
        menu.add(menuobj1);

        ProfileMenu menuobj2 = new ProfileMenu();
        menuobj2.setValues("Family");
        menuobj2.setImages(R.drawable.approve);
        menu.add(menuobj2);

        ProfileMenu menuobj3 = new ProfileMenu();
        menuobj3.setValues("Education");
        menuobj3.setImages(R.drawable.menu);
        menu.add(menuobj3);

        ProfileMenu menuobj4 = new ProfileMenu();
        menuobj4.setValues("Last Position");
        menuobj4.setImages(R.drawable.extract_menu);
        menu.add(menuobj4);

        ProfileMenu menuobj5 = new ProfileMenu();
        menuobj5.setValues("Experience");
        menuobj5.setImages(R.drawable.holiday);
        menu.add(menuobj5);

        ProfileMenu menuobj6 = new ProfileMenu();
        menuobj6.setValues("Reference");
        menuobj6.setImages(R.drawable.more_menu);
        menu.add(menuobj6);

        ProfileMenu menuobj7 = new ProfileMenu();
        menuobj7.setValues("Additional");
        menuobj7.setImages(R.drawable.add);
        menu.add(menuobj7);

        ProfileMenu menuobj8 = new ProfileMenu();
        menuobj8.setValues("Emergency");
        menuobj8.setImages(R.drawable.ic_add_circle_outline_black_24dp);
        menu.add(menuobj8);

        ProfileMenu menuobj9 = new ProfileMenu();
        menuobj9.setValues("Attachments");
        menuobj9.setImages(R.drawable.attachment);
        menu.add(menuobj9);

        ProfileMenu menuobj0 = new ProfileMenu();
        menuobj0.setValues("Passport");
        menuobj0.setImages(R.drawable.ic_dehaze_black_24dp);
        menu.add(menuobj0);
    }

    private void newInstance(String s) {
        if(s.equals("Attachments")){
            Attachment attachment = new Attachment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            attachment.show(transaction, attachment.TAG);
        }
        else {
            ProfileInformation information = new ProfileInformation();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putString("selectedoption", s);
            information.setArguments(bundle);
            information.show(transaction, information.TAG);
        }
    }
}