package com.android.wilcoconnect.fragment.profile;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.api.ApiManager;
import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.model.profile.AttachmentDetailData;
import com.android.wilcoconnect.model.profile.AttachmentDetails;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.android.wilcoconnect.app.MainApplication.MY_PREFS_NAME;

public class Attachment extends DialogFragment {

    public static String TAG = "Attachment";
    private static final String MYPREFS_NAME = "logininfo";
    private AddRequest addRequest = new AddRequest();
    private AttachmentDetails attachmentDetail = new AttachmentDetails();
    private AttachmentDetailData attachmentDetailData = new AttachmentDetailData();
    private String[] adarCard,panCard,presentAddress,dob,passport,edu1_degree,edu1_provisional,edu1_marksheet,edu2_degree,edu2_provisional,edu2_marksheet,experience_certificate,payslips,Experience;
    private LinearLayout adharCard_layout,panCard_layout,presentAddress_layout,dob_layout,passport_layout,edu1degree_layout,edu1provisional_layout,edu1marksheet_layout,edu2degree_layout,edu2provisional_layout,edu2marksheet_layout,experiencecertificate_layout,payslips_layout,experience_layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attachment, container, false);

        adharCard_layout = view.findViewById(R.id.et_View_aadhar);
        panCard_layout = view.findViewById(R.id.et_View_pan);
        presentAddress_layout = view.findViewById(R.id.et_View_addressproof);
        dob_layout = view.findViewById(R.id.et_View_dob);
        passport_layout = view.findViewById(R.id.et_View_passport);
        edu1degree_layout = view.findViewById(R.id.et_View_edu1);
        edu1provisional_layout = view.findViewById(R.id.et_View_prov);
        edu1marksheet_layout = view.findViewById(R.id.et_View_marksheet);
        edu2degree_layout = view.findViewById(R.id.et_View_degree);
        edu2provisional_layout = view.findViewById(R.id.et_View_provcert);
        edu2marksheet_layout = view.findViewById(R.id.et_View_Marks);
        experiencecertificate_layout = view.findViewById(R.id.et_View_exper);
        payslips_layout = view.findViewById(R.id.et_View_payslip);
        experience_layout = view.findViewById(R.id.et_View_experience);

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
        if(prefs!=null) {
            addRequest.setEmail(prefs.getString("Email", "No name defined"));
            addRequest.setCompanyCode(prefs.getString("CompanyCode", "No name defined"));
            addRequest.setEmployeeID(prefs.getString("EmployeeID", "No name defined"));
        }

        Toolbar profile_toolbar = view.findViewById(R.id.main_withnav_toolbar);
        profile_toolbar.setTitle("Attachments");
        profile_toolbar.setNavigationIcon(R.drawable.close);
        profile_toolbar.setNavigationOnClickListener(v -> dismiss());

        /*
         * Get the Attachment Details
         * */
        ApiManager.getInstance().getAttachmentDetails(addRequest, new Callback<AttachmentDetails>() {
            /*
             * Api Call Success
             * */
            @Override
            public void onResponse(Call<AttachmentDetails> call, Response<AttachmentDetails> response) {
                attachmentDetail = response.body();
                if(attachmentDetail!=null && response.isSuccessful()){
                    attachmentDetailData = attachmentDetail.getData().get(0).getObjAttachement();
                    get_array_data();
            }
            }
            /*
             * Api Call Failure
             * */
            @Override
            public void onFailure(Call<AttachmentDetails> call, Throwable t) {
                Log.e(TAG,t.getLocalizedMessage());
            }
        });

        return view;
    }

    private void get_array_data() {
        for (int i = 0; i < attachmentDetailData.getDocumentList().size(); i++) {
            if (attachmentDetailData.getDocumentList().get(i).getAttachmentFileName().equals("Aadhar Card")) {
                adarCard = new String[attachmentDetailData.getDocumentList().get(i).getFilelist().size()];
                for (int a = 0; a < attachmentDetailData.getDocumentList().get(i).getFilelist().size(); a++) {
                    adarCard[a] = attachmentDetailData.getDocumentList().get(i).getFilelist().get(a);
                }
            } else if (attachmentDetailData.getDocumentList().get(i).getAttachmentFileName().equals("PAN Card")) {
                panCard = new String[attachmentDetailData.getDocumentList().get(i).getFilelist().size()];
                for (int a = 0; a < attachmentDetailData.getDocumentList().get(i).getFilelist().size(); a++) {
                    panCard[a] = attachmentDetailData.getDocumentList().get(i).getFilelist().get(a);
                }
            } else if (attachmentDetailData.getDocumentList().get(i).getAttachmentFileName().equals("Present Address Proof")) {
                presentAddress = new String[attachmentDetailData.getDocumentList().get(i).getFilelist().size()];
                for (int b = 0; b < attachmentDetailData.getDocumentList().get(i).getFilelist().size(); b++) {
                    presentAddress[b] = attachmentDetailData.getDocumentList().get(i).getFilelist().get(b);
                }
            } else if (attachmentDetailData.getDocumentList().get(i).getAttachmentFileName().equals("Proof of Date of Birth")) {
                dob = new String[attachmentDetailData.getDocumentList().get(i).getFilelist().size()];
                for (int a = 0; a < attachmentDetailData.getDocumentList().get(i).getFilelist().size(); a++) {
                    dob[a] = attachmentDetailData.getDocumentList().get(i).getFilelist().get(a);
                }
            } else if (attachmentDetailData.getDocumentList().get(i).getAttachmentFileName().equals("Passport")) {
                passport = new String[attachmentDetailData.getDocumentList().get(i).getFilelist().size()];
                for (int a = 0; a < attachmentDetailData.getDocumentList().get(i).getFilelist().size(); a++) {
                    passport[a] = attachmentDetailData.getDocumentList().get(i).getFilelist().get(a);
                }
            } else if (attachmentDetailData.getDocumentList().get(i).getAttachmentFileName().equals("Education1 - Degree")) {
                edu1_degree = new String[attachmentDetailData.getDocumentList().get(i).getFilelist().size()];
                for (int a = 0; a < attachmentDetailData.getDocumentList().get(i).getFilelist().size(); a++) {
                    edu1_degree[a] = attachmentDetailData.getDocumentList().get(i).getFilelist().get(a);
                }
            } else if (attachmentDetailData.getDocumentList().get(i).getAttachmentFileName().equals("Education1 - Provisional Certificate")) {
                edu1_provisional = new String[attachmentDetailData.getDocumentList().get(i).getFilelist().size()];
                for (int a = 0; a < attachmentDetailData.getDocumentList().get(i).getFilelist().size(); a++) {
                    edu1_provisional[a] = attachmentDetailData.getDocumentList().get(i).getFilelist().get(a);
                }
            } else if (attachmentDetailData.getDocumentList().get(i).getAttachmentFileName().equals("Education1 - Marksheets")) {
                edu1_marksheet = new String[attachmentDetailData.getDocumentList().get(i).getFilelist().size()];
                for (int a = 0; a < attachmentDetailData.getDocumentList().get(i).getFilelist().size(); a++) {
                    edu1_marksheet[a] = attachmentDetailData.getDocumentList().get(i).getFilelist().get(a);
                }
            } else if (attachmentDetailData.getDocumentList().get(i).getAttachmentFileName().equals("Degree")) {
                edu2_degree = new String[attachmentDetailData.getDocumentList().get(i).getFilelist().size()];
                for (int a = 0; a < attachmentDetailData.getDocumentList().get(i).getFilelist().size(); a++) {
                    edu2_degree[a] = attachmentDetailData.getDocumentList().get(i).getFilelist().get(a);
                }
            } else if (attachmentDetailData.getDocumentList().get(i).getAttachmentFileName().equals("Provisional Certificate")) {
                edu2_provisional = new String[attachmentDetailData.getDocumentList().get(i).getFilelist().size()];
                for (int a = 0; a < attachmentDetailData.getDocumentList().get(i).getFilelist().size(); a++) {
                    edu2_provisional[a] = attachmentDetailData.getDocumentList().get(i).getFilelist().get(a);
                }
            } else if (attachmentDetailData.getDocumentList().get(i).getAttachmentFileName().equals("Marksheets")) {
                edu2_marksheet = new String[attachmentDetailData.getDocumentList().get(i).getFilelist().size()];
                for (int a = 0; a < attachmentDetailData.getDocumentList().get(i).getFilelist().size(); a++) {
                    edu2_marksheet[a] = attachmentDetailData.getDocumentList().get(i).getFilelist().get(a);
                }
            } else if (attachmentDetailData.getDocumentList().get(i).getAttachmentFileName().equals("Experience Certificate / Relieving Letter from Previous Employment ")) {
                experience_certificate = new String[attachmentDetailData.getDocumentList().get(i).getFilelist().size()];
                for (int a = 0; a < attachmentDetailData.getDocumentList().get(i).getFilelist().size(); a++) {
                    experience_certificate[a] = attachmentDetailData.getDocumentList().get(i).getFilelist().get(a);
                }
            } else if (attachmentDetailData.getDocumentList().get(i).getAttachmentFileName().equals("Payslips")) {
                payslips = new String[attachmentDetailData.getDocumentList().get(i).getFilelist().size()];
                for (int a = 0; a < attachmentDetailData.getDocumentList().get(i).getFilelist().size(); a++) {
                    payslips[a] = attachmentDetailData.getDocumentList().get(i).getFilelist().get(a);
                }
            } else if (attachmentDetailData.getDocumentList().get(i).getAttachmentFileName().equals("Experience")) {
                Experience = new String[attachmentDetailData.getDocumentList().get(i).getFilelist().size()];
                for (int a = 0; a < attachmentDetailData.getDocumentList().get(i).getFilelist().size(); a++) {
                    Experience[a] = attachmentDetailData.getDocumentList().get(i).getFilelist().get(a);
                }
            }
        }
        set_attachment();
    }

    private void set_attachment() {
        for (int i = 0; i< adarCard.length; i++){
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(80,80));
            imageView.setPadding(8,8,8,8);
            imageView.setImageResource(R.drawable.attachmentblue);
            adharCard_layout.addView(imageView);
        }
        for (int i=0; i<panCard.length;i++){
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(80,80));
            imageView.setPadding(8,8,8,8);
            imageView.setImageResource(R.drawable.attachmentblue);
            panCard_layout.addView(imageView);
        }
        for (int i=0; i<presentAddress.length;i++){
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(80,80));
            imageView.setPadding(8,8,8,8);
            imageView.setImageResource(R.drawable.attachmentblue);
            presentAddress_layout.addView(imageView);
        }
        for (int i=0; i<passport.length;i++){
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(80,80));
            imageView.setPadding(8,8,8,8);
            imageView.setImageResource(R.drawable.attachmentblue);
            passport_layout.addView(imageView);
        }
        for (int i=0; i<dob.length;i++){
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(80,80));
            imageView.setPadding(8,8,8,8);
            imageView.setImageResource(R.drawable.attachmentblue);
            dob_layout.addView(imageView);
        }
        for (int i=0; i<edu1_degree.length;i++){
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(80,80));
            imageView.setPadding(8,8,8,8);
            imageView.setImageResource(R.drawable.attachmentblue);
            edu1degree_layout.addView(imageView);
        }
        for (int i=0; i<edu1_provisional.length;i++){
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(80,80));
            imageView.setPadding(8,8,8,8);
            imageView.setImageResource(R.drawable.attachmentblue);
            edu1provisional_layout.addView(imageView);
        }
        for (int i=0; i<edu1_marksheet.length;i++){
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(80,80));
            imageView.setPadding(8,8,8,8);
            imageView.setImageResource(R.drawable.attachmentblue);
            edu1marksheet_layout.addView(imageView);
        }
        for (int i=0; i<edu2_degree.length;i++){
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(80,80));
            imageView.setPadding(8,8,8,8);
            imageView.setImageResource(R.drawable.attachmentblue);
            edu2degree_layout.addView(imageView);
        }
        for (int i=0; i<edu2_provisional.length;i++){
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(80,80));
            imageView.setPadding(8,8,8,8);
            imageView.setImageResource(R.drawable.attachmentblue);
            edu2provisional_layout.addView(imageView);
        }
        for (int i=0; i<edu2_marksheet.length;i++){
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(80,80));
            imageView.setPadding(8,8,8,8);
            imageView.setImageResource(R.drawable.attachmentblue);
            edu2marksheet_layout.addView(imageView);
        }
        for (int i=0; i<experience_certificate.length;i++){
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(80,80));
            imageView.setPadding(8,8,8,8);
            imageView.setImageResource(R.drawable.attachmentblue);
            experiencecertificate_layout.addView(imageView);
        }
        for (int i=0; i<payslips.length;i++){
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(80,80));
            imageView.setPadding(8,8,8,8);
            imageView.setImageResource(R.drawable.attachmentblue);
            payslips_layout.addView(imageView);
        }
        for (int i=0; i<Experience.length;i++){
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(80,80));
            imageView.setPadding(8,8,8,8);
            imageView.setImageResource(R.drawable.attachmentblue);
            experience_layout.addView(imageView);
        }
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

}
