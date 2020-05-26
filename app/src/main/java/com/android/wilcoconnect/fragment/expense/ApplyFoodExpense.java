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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.api.ApiManager;
import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.model.expense.FoodExpenseProject;
import com.android.wilcoconnect.model.expense.FoodExpenseProjectList;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission_group.CAMERA;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class ApplyFoodExpense extends DialogFragment {

    public static final String TAG = "ApplyFoodExpense";
    private AddRequest addRequest= new AddRequest();
    private static String MYPREFS_NAME = "logininfo";
    private Button date,project,clear,submit;
    private TextView project_label;
    private RadioGroup purpose;
    private EditText remarks,amount;
    private ImageView attachment,date_picker;
    private int Year,Month,Day;
    private String applied_date;
    private ArrayList<FoodExpenseProject> projects = new ArrayList<>();
    private static int RESULT_LOAD_FILE = 1;
    private String Name;
    private int checkItem =0;
    private String[] projectName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_apply_food_expense, container, false);

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

        /*
         * Define the ToolBar
         * */
        Toolbar detail_toolbar = view.findViewById(R.id.main_withnav_toolbar);
        detail_toolbar.setTitle("APPLY LEAVE");
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
                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
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
                Name = projectName[which];
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

        return view;
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
            Objects.requireNonNull(cursor).moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            attachment.setTag(picturePath);
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
     * Alert Box to display permission granted or denaied
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
}