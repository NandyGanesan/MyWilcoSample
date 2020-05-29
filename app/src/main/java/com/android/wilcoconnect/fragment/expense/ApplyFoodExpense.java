package com.android.wilcoconnect.fragment.expense;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.api.ApiManager;
import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.model.common.Success;
import com.android.wilcoconnect.model.expense.FoodExpenseData;
import com.android.wilcoconnect.model.expense.FoodExpenseProject;
import com.android.wilcoconnect.model.expense.FoodExpenseProjectList;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.android.wilcoconnect.network_interface.DialogListener;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission_group.CAMERA;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class ApplyFoodExpense extends DialogFragment implements View.OnClickListener {

    public static final String TAG = "ApplyFoodExpense";
    private AddRequest addRequest= new AddRequest();
    private static String MYPREFS_NAME = "logininfo";
    private Button date,project,clear,submit;
    private TextView project_label;
    private RadioGroup purpose;
    private EditText remarks,amount,actionDate,approvedamount,settlementamount,status;
    private ImageView attachment,date_picker;
    private int Year,Month,Day;
    private String applied_date,ProjectID;
    private ArrayList<FoodExpenseProject> projects = new ArrayList<>();
    private static int RESULT_LOAD_FILE = 1;
    private String FileName,value;
    private int checkItem =0;
    private View view;
    private DialogListener listener;
    private String[] projectName;
    private FoodExpenseData data = new FoodExpenseData();
    private LinearLayout layout,file;

    /*
     * Define the OnCreate method to set the Fragment to the Particular Listener
     * */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener = (DialogListener) getTargetFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_apply_food_expense, container, false);

        /*
         * Retrieve the Intent Value
         * */
        if(this.getArguments()!=null) {
            String foodExpenseEdit = this.getArguments().getString("FoodExpenseEdit");
            Gson gson = new Gson();
            data = gson.fromJson(foodExpenseEdit, FoodExpenseData.class);
        }

        date = view.findViewById(R.id.btn_date);
        project = view.findViewById(R.id.btn_type);
        project_label = view.findViewById(R.id.tv_type);
        purpose = view.findViewById(R.id.radioGroupleave);
        remarks = view.findViewById(R.id.et_remarks);
        amount = view.findViewById(R.id.et_amount);
        attachment = view.findViewById(R.id.iv_receipt);
        date_picker = view.findViewById(R.id.iv_date);
        submit = view.findViewById(R.id.btn_submit);
        clear = view.findViewById(R.id.btn_clear);
        actionDate = view.findViewById(R.id.et_actionDate);
        approvedamount = view.findViewById(R.id.et_approvedamount);
        settlementamount = view.findViewById(R.id.et_settlementamount);
        status = view.findViewById(R.id.et_status);
        layout = view.findViewById(R.id.approverDetail_Frame);
        file = view.findViewById(R.id.attachment_frame);

        RadioButton radioButton_project = (RadioButton) view.findViewById(R.id.radioProject);

        /*
         * Define the ToolBar
         * */
        Toolbar detail_toolbar = view.findViewById(R.id.main_withnav_toolbar);
        detail_toolbar.setTitle("FOOD EXPENSE");
        detail_toolbar.setNavigationIcon(R.drawable.close);
        detail_toolbar.setNavigationOnClickListener(v -> dismiss());

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
        * Get the Project Name
        * */
        ApiManager.getInstance().getProjectDetail(addRequest, new Callback<FoodExpenseProjectList>() {
            //API Call Success
            @Override
            public void onResponse(Call<FoodExpenseProjectList> call, Response<FoodExpenseProjectList> response) {
                if(response.isSuccessful() && response.body()!=null){
                    projects = response.body().getData();
                    get_dropdown_value();
                    if(data.getClaimNumber()!=null){
                        date.setText(data.getStrBillDate());
                        remarks.setText(data.getRemarks());
                        amount.setText("" + data.getRequestedAmount());
                        if (data.getProjectID() != null) {
                            if(projects.size()>0) {
                                for (int i = 0; i < projects.size(); i++) {
                                    if(projects.get(i).getProjectID().equals(data.getProjectID())){
                                        project.setText(projects.get(i).getProjectName());
                                        radioButton_project.setChecked(true);
                                    }
                                }
                            }
                            project_label.setVisibility(View.VISIBLE);
                            project.setVisibility(View.VISIBLE);
                        }
                        get_filepath();
                        if(data.getStatus().equals("SUBMITTED")) {
                            project.setEnabled(true);
                            date_picker.setEnabled(true);
                            purpose.setEnabled(true);
                            remarks.setEnabled(true);
                            amount.setEnabled(true);
                            attachment.setEnabled(true);
                            submit.setVisibility(View.VISIBLE);
                            clear.setVisibility(View.VISIBLE);
                            layout.setVisibility(View.GONE);
                            attachment.setVisibility(View.VISIBLE);
                            file.setVisibility(View.VISIBLE);
                        }
                        else {
                            project.setEnabled(false);
                            date_picker.setEnabled(false);
                            purpose.setEnabled(false);
                            remarks.setEnabled(false);
                            amount.setEnabled(false);
                            attachment.setEnabled(false);
                            submit.setVisibility(View.GONE);
                            clear.setVisibility(View.GONE);
                            layout.setVisibility(View.VISIBLE);
                            attachment.setVisibility(View.GONE);
                            actionDate.setText(data.getActionDate());
                            settlementamount.setText(data.getSettlementAmount());
                            status.setText(data.getStatus());
                            approvedamount.setText(""+data.getApprovedAmount());
                            file.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
            //API Call Failure
            @Override
            public void onFailure(Call<FoodExpenseProjectList> call, Throwable t) {
                Log.e(TAG,t.getLocalizedMessage());
            }
        });

        /*
         * Select the Applied Date
         * */
        date_picker.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            Year = c.get(Calendar.YEAR);
            Month = c.get(Calendar.MONTH);
            Day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (view1, year, monthOfYear, dayOfMonth) -> {
                date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                applied_date = (year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }, Year, Month, Day);
            Calendar cal = Calendar.getInstance();
            datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
            datePickerDialog.show();
        });

        /*
         * Get the Project or Other in Purpose Selection
         * Based view display
         * */
        purpose.setOnCheckedChangeListener((group, checkedId) -> {
            int radioButtonID = purpose.getCheckedRadioButtonId();
            View radioButton = purpose.findViewById(radioButtonID);
            int idx = purpose.indexOfChild(radioButton);
            if(idx==0){
                project_label.setVisibility(View.VISIBLE);
                project.setVisibility(View.VISIBLE);
            }
            else {
                project_label.setVisibility(View.GONE);
                project.setVisibility(View.GONE);
            }
        });

        project.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Select the Leave Type:");
            builder.setItems(projectName, (dialog, which) -> {
                checkItem = which;
                project.setText(projectName[which]);
                ProjectID = projects.get(which).getProjectID();
            }).setNegativeButton("Cancel", (dialog, which) -> {
                checkItem=-1;
                if (checkItem < 0) {
                    project.setText("--- SELECT ---");
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        /*
         * Get the image file from the Gallery..
         * */
        attachment.setOnClickListener(v -> {
            int result = checkPermission();
            if (result == PackageManager.PERMISSION_GRANTED) {
                Intent image_intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(image_intent, RESULT_LOAD_FILE);
            } else {
                requestPermission();
            }
        });

        clear.setOnClickListener(v -> {
            date.setText("");
            project_label.setVisibility(View.GONE);
            project.setVisibility(View.GONE);
            remarks.setText("");
            amount.setText("");
            attachment.setTag("");
            purpose.clearCheck();
        });

        /*
         * When submit the leave request
         * */
        submit.setOnClickListener(v -> {

            int selectedId = purpose .getCheckedRadioButtonId();

            // find the radio button by returned id
            RadioButton radioButton = (RadioButton) view.findViewById(selectedId);
            if(date.getText().toString().equals("")||
                    project.getText().toString().equals("")||
                    remarks.getText().toString().equals("")||
                    amount.getText().toString().equals("")||
                    FileName == null)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Error:");
                if (date.getText().toString().equals("")) {
                    builder.setMessage("Select the valid date");
                }
                else  if(radioButton.getText().toString().equals("  Project")){
                    if(project.getText().toString().equals("--- SELECT ---")){
                        builder.setMessage("Select the valid Project Name");
                    }
                }
                else  if(remarks.getText().toString().equals("")){
                    builder.setMessage("Enter the valid Remarks");
                }
                else  if(amount.getText().toString().equals("")){
                    builder.setMessage("Enter the valid amount");
                }
                else if(FileName == null){
                    builder.setMessage("Please attach the Receipt");
                }
                builder.setPositiveButton("OK",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else {
                MediaType text = MediaType.parse("text/plain");
                RequestBody ReEmployeeID = RequestBody.create(text,addRequest.getEmployeeID());
                RequestBody ReEmail = RequestBody.create(text,addRequest.getEmail());
                RequestBody ReFoodID = RequestBody.create(text, String.valueOf(0));
                RequestBody ReClaimNumber = RequestBody.create(text, String.valueOf(0));
                RequestBody ReBillDate = RequestBody.create(text,applied_date);
                RequestBody ReProjectID = RequestBody.create(text, String.valueOf(ProjectID));
                RequestBody ReRemarks = RequestBody.create(text,remarks.getText().toString());
                RequestBody ReFoodExpenseAmount = RequestBody.create(text,amount.getText().toString());
                File file = new File(FileName);
                RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                // MultipartBody.Part is used to send also the actual file name
                MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody1);

                ApiManager.getInstance().StoreNewFoodExpenseRequest(ReEmployeeID, ReEmail, ReFoodID, ReClaimNumber, ReBillDate, ReProjectID, ReRemarks, ReFoodExpenseAmount, body,
                        new Callback<Success>() {
                            @Override
                            public void onResponse(Call<Success> call, Response<Success> response) {
                                //Data Store Success
                                if(response.isSuccessful() && response.body().getStatus().equals("true") && response.body().getMessage().equals("successfully Stored")) {
                                    dismiss();
                                    value = "Success";
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setTitle(response.body().getMessage());
                                    builder.setPositiveButton("Ok", (dialog, which) -> listener.onDialogClick(value));
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                    date.setText("");
                                    project_label.setVisibility(View.GONE);
                                    project.setVisibility(View.GONE);
                                    remarks.setText("");
                                    amount.setText("");
                                    attachment.setTag("");
                                    purpose.clearCheck();
                                }
                                //Data Store Failure
                                else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setTitle(response.body().getMessage());
                                    builder.setPositiveButton("Ok", null);
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Success> call, Throwable t) {
                                Log.e(TAG,t.getLocalizedMessage());
                            }
                        });
            }
        });
        return view;
    }

    private void get_filepath() {
        for (int i=0; i<data.getEmpReceiptList().size();i++){
            for (int j=0;j<data.getEmpReceiptList().get(i).getFilepath().size();j++) {
                ImageView imageView = new ImageView(getActivity());
                imageView.setLayoutParams(new LinearLayout.LayoutParams(80, 80));
                imageView.setPadding(8, 8, 8, 8);
                imageView.setTag(data.getEmpReceiptList().get(i).getFilepath().get(j));
                imageView.setOnClickListener(this);
                imageView.setImageResource(R.drawable.ic_attachment_black_24dp);
                layout.addView(imageView);
            }
        }
    }

    /*
     * Get the DropDown Value from the API Call
     * */
    private void get_dropdown_value() {
        if(projects.size()>0) {
            projectName = new String[projects.size()];
            for (int i = 0; i < projects.size(); i++) {
                projectName[i] = projects.get(i).getProjectName();
            }
        }
    }

    /*
     * Select the Image From the Gallery ans set to the Profile or Image View
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_FILE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(Objects.requireNonNull(selectedImage), filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            FileName = picturePath;
        }
    }

    private int checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return 0;
        } else {
            return -1;
        }
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(getActivity(), "Read and write External Storage permission allows us to retrieve files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, RESULT_LOAD_FILE);
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, RESULT_LOAD_FILE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], int[] grantResults) {
        if (grantResults.length >= 0) {
            boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if (cameraAccepted)
                Toast.makeText(null, "Permission Granted, Now you can access location data and camera.", Toast.LENGTH_SHORT).show();
            else {
                Toast.makeText(null, "Permission Denied, You cannot access location data and camera.", Toast.LENGTH_LONG).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (shouldShowRequestPermissionRationale(CAMERA)) {
                        showMessageOKCancel("You need to allow access to external storage read/write permissions",
                                (dialog, which) -> {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        Intent image_intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        startActivityForResult(image_intent, RESULT_LOAD_FILE);
                                    }
                                });
                    }
                }
            }
        }
    }

    /**
     * Alert Box to display permission granted or denied
     */
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    /*
     * Dialog Window OnStart Method
     * */
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

    /*
     * OnClick Action for Attachment File
     * */
    @Override
    public void onClick(View v) {
        String url = this.getTag();
        Uri uri = Uri.parse("http://192.168.1.50/hrdev/content/"+url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}