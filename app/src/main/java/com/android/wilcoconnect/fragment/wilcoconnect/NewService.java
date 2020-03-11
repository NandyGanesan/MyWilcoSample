package com.android.wilcoconnect.fragment.wilcoconnect;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.api.ApiManager;
import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.android.wilcoconnect.model.wilcoconnect.Priority;
import com.android.wilcoconnect.model.wilcoconnect.RequestFunction;
import com.android.wilcoconnect.model.wilcoconnect.RequestType;
import com.android.wilcoconnect.model.common.Success;
import com.android.wilcoconnect.model.wilcoconnect.SupportFunction;

import java.io.File;
import java.util.List;
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

public class NewService extends Fragment {

    /**
     * Initialize the all variables and attributes....
     */

    private static String MY_PREFS_NAME = "MyPrefsFile";
    private String TAG = "NewService";
    private int checkItem = -1;
    private static int RESULT_LOAD_FILE = 1;

    private Button btn_support, btn_request, btn_priority, btn_create, btn_update, btn_file;
    private LinearLayout linearlayout;
    private TextView tv_comment, tv_error, tv_latest, tv_attachments;
    private EditText summary, description, comment, latestcmd;
    private ImageView iv_attachments;

    private List<Priority> priorityData;
    private List<RequestFunction> requestdropdown;
    private List<SupportFunction> supportdropdown;
    private List<RequestType.Data> requestList;

    private AddRequest newrequest = new AddRequest();
    private RequestType dropdowndata;
    private Fragment fragment;

    private String[] support;
    private String[] requestdd;
    private String[] prioritydd;
    private Toolbar newservice_toolbar;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_newservice, container, false);
        /*
         * Initialize the UI element
         * */
        summary = view.findViewById(R.id.et_summary);
        description = view.findViewById(R.id.et_description);
        comment = view.findViewById(R.id.et_closure);
        tv_comment = view.findViewById(R.id.tv_closure);
        btn_support = view.findViewById(R.id.btn_support);
        btn_request = view.findViewById(R.id.btn_request);
        btn_priority = view.findViewById(R.id.btn_priority);
        btn_create = view.findViewById(R.id.btn_create);
        tv_error = view.findViewById(R.id.tv_error);
        tv_latest = view.findViewById(R.id.tv_latest);
        latestcmd = view.findViewById(R.id.et_latest);
        btn_update = view.findViewById(R.id.btn_update);
        btn_file = view.findViewById(R.id.btn_attach);
        linearlayout = view.findViewById(R.id.linearData);
        tv_attachments = view.findViewById(R.id.tv_attachments);
        iv_attachments = view.findViewById(R.id.iv_attachments);

        /*
         * Set the Hidden Visibility for Particular View
         * */
        tv_comment.setVisibility(View.GONE);
        comment.setVisibility(View.GONE);
        tv_error.setVisibility(View.GONE);
        tv_latest.setVisibility(View.GONE);
        latestcmd.setVisibility(View.GONE);
        btn_update.setVisibility(View.GONE);
        linearlayout.setVisibility(View.GONE);
        tv_attachments.setVisibility(View.GONE);
        iv_attachments.setVisibility(View.GONE);

        /*
         * Get the shared preference data to assign the another object..
         * */
        final SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        newrequest.setEmail(prefs.getString("Email", "No name defined"));
        newrequest.setCompanyCode(prefs.getString("CompanyCode", "No name defined"));
        newrequest.setEmployeeID(prefs.getString("EmployeeID", "No name defined"));
        newrequest.setIssueMasterID(0);

        /*
        * Toolbar Back action and set Title
        * */
        newservice_toolbar = view.findViewById(R.id.main_withnav_toolbar);
        newservice_toolbar.setTitle("New Service Request");//WilcoConnect-
        newservice_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        /*
         * Get the Header
         * */
        SharedPreferences preferences = getActivity().getSharedPreferences(MainApplication.MY_PREFS_NAME, MODE_PRIVATE);
        if (preferences != null) {
            MainApplication.token_data = preferences.getString("header", "No name defined");
        }

        /*
         * Get the image file from the Gallery..
         * */
        btn_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = checkPermission();
                if (result == PackageManager.PERMISSION_GRANTED) {
                    Intent image_intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(image_intent, RESULT_LOAD_FILE);
                } else {
                    requestPermission();
                }
            }
        });

        /*
         * Get the overall dropdown data
         * */
        ApiManager.getInstance().getDropdownData(newrequest, new Callback<RequestType>() {
            /*
             * Get the Api success..
             * */
            @Override
            public void onResponse(@NonNull Call<RequestType> call, @NonNull Response<RequestType> response) {
                dropdowndata = response.body();
                if (response.isSuccessful() && dropdowndata != null) {
                    requestList = dropdowndata.getData();
                    Log.d(TAG, "GET DROPDOWN DATA");
                    /*
                     * Get the Support Dropdown data
                     * */
                    supportdropdown = requestList.get(0).getSupportFunction();
                    /*
                     * Get the Priority Dropdown data
                     * */
                    priorityData = requestList.get(0).getPriorityList();
                    /*
                     * Initialize the Support Function Array
                     * */
                    support = new String[supportdropdown.size()];
                    for (int item = 0; item < supportdropdown.size(); item++) {
                        support[item] = supportdropdown.get(item).getSupportFunctionName();
                    }
                    /*
                     * Initialize the Priority Array
                     * */
                    prioritydd = new String[priorityData.size()];
                    for (int item = 0; item < priorityData.size(); item++) {
                        prioritydd[item] = priorityData.get(item).getPriorityLevel();
                    }
                }
            }

            /*
             * Get the Api Failure
             * */
            @Override
            public void onFailure(@NonNull Call<RequestType> call, @NonNull Throwable t) {
                Log.e(TAG, Objects.requireNonNull(t.getLocalizedMessage()));
            }
        });

        /*
         * select the Support Function from the dropdown list
         * */
        btn_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select the Support Function:");
                builder.setItems(support, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkItem = which;
                        //Toast.makeText(getActivity(), support[which], Toast.LENGTH_SHORT).show();
                        btn_support.setText(support[which]);
                        for (int item = 0; item < supportdropdown.size(); item++) {
                            if (support[which].equalsIgnoreCase(supportdropdown.get(item).getSupportFunctionName())) {
                                newrequest.setSupportFunctionID(supportdropdown.get(item).getSupportFunctionID());
                                requestdropdown = supportdropdown.get(item).getRequestType();
                            }
                        }
                        dialog.dismiss();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (checkItem < 0)
                            btn_support.setText("Support Function");
                        requestdropdown = null;

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        /*
         * select the request type from the dropdown list
         * */
        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkItem = -1;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select the Request Type:");
                if (requestdropdown != null) {
                    requestdd = new String[requestdropdown.size()];
                    for (int item = 0; item < requestdropdown.size(); item++) {
                        requestdd[item] = requestdropdown.get(item).getFunctionShortForm();
                    }
                    builder.setItems(requestdd, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            checkItem = which;
                            for (int item = 0; item < requestdropdown.size(); item++) {
                                if (requestdd[checkItem].equalsIgnoreCase(requestdropdown.get(item).getFunctionShortForm())) {
                                    btn_request.setText(requestdropdown.get(item).getFunctionShortForm());
                                    newrequest.setRequestTypeId(requestdropdown.get(item).getRequestTypeID());
                                }
                            }
                            dialog.dismiss();
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (checkItem < 0)
                                btn_request.setText("Request Type");
                        }
                    });
                } else {
                    builder.setTitle("Error:");
                    builder.setMessage("First to select the Support Function and then to select the Request Type...");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            btn_request.setText("Request Type");
                        }
                    });
                }

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        /*
         * select the priority level from the dropdown list
         * */
        btn_priority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkItem = -1;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select the Priority Level:");
                builder.setItems(prioritydd, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkItem = which;
                        btn_priority.setText(priorityData.get(which).getPriorityLevel());
                        newrequest.setPriorityLevelID(priorityData.get(which).getID());
                        dialog.dismiss();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (checkItem < 0)
                            btn_priority.setText("Priority");
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        /*
         * Get all the Data from the User..
         * And Post the User Request..
         * */
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_support.setError(null);
                btn_request.setError(null);
                description.setError(null);
                summary.setError(null);
                btn_priority.setError(null);
                if (btn_support.getText().toString().equals("Support Function")) {
                    btn_support.setError("Select the Support Function");
                } else if (btn_request.getText().toString().equals("Request Type")) {
                    btn_request.setError("Select the Request Type");
                } else if (description.getText().toString().equals("")) {
                    description.setError("Require to fill Description");
                } else if (summary.getText().toString().equals("")) {
                    summary.setError("Require to fill Summary");
                } else if (btn_priority.getText().toString().equals("Priority")) {
                    btn_priority.setError("Select the Priority Level");
                } else {
                    newrequest.setStatusCode("N");
                    newrequest.setIssueDescription(description.getText().toString());
                    newrequest.setIssueSummary(summary.getText().toString());
                    //pass it like this
                    File file = new File(btn_file.getText().toString());
                    RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    // MultipartBody.Part is used to send also the actual file name
                    MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody1);
                    MediaType text = MediaType.parse("text/plain");
                    Log.i(TAG, "NEW REQUEST DATA");
                    RequestBody Redescription = RequestBody.create(text, description.getText().toString());
                    Log.i(TAG, "description: " + description.getText().toString());
                    RequestBody Resummary = RequestBody.create(text, summary.getText().toString());
                    Log.i(TAG, "summary: " + summary.getText().toString());
                    RequestBody ReEmail = RequestBody.create(text, newrequest.getEmail());
                    Log.i(TAG, "Email: " + newrequest.getEmail());
                    RequestBody ReEmployeeId = RequestBody.create(text, newrequest.getEmployeeID());
                    Log.i(TAG, "EmployeeID: " + newrequest.getEmployeeID());
                    RequestBody ReCompanyCode = RequestBody.create(text, newrequest.getCompanyCode());
                    Log.i(TAG, "CompanyCode: " + newrequest.getCompanyCode());
                    RequestBody ReSupportId = RequestBody.create(text, String.valueOf(newrequest.getSupportFunctionID()));
                    Log.i(TAG, "SupportFunctionID: " + newrequest.getSupportFunctionID());
                    RequestBody ReRequestId = RequestBody.create(text, String.valueOf(newrequest.getRequestTypeId()));
                    Log.i(TAG, "RequestTypeId: " + newrequest.getRequestTypeId());
                    RequestBody RePriorityId = RequestBody.create(text, String.valueOf(newrequest.getPriorityLevelID()));
                    Log.i(TAG, "PriorityLevelID: " + newrequest.getPriorityLevelID());
                    RequestBody ReIssueMasterId = RequestBody.create(text, String.valueOf(newrequest.getIssueMasterID()));
                    Log.i(TAG, "IssueMasterID: " + newrequest.getIssueMasterID());
                    RequestBody RestatusCode = RequestBody.create(text, "N");
                    Log.i(TAG, "statusCode: " + "N");
                    RequestBody ReComments = RequestBody.create(text, "");
                    Log.i(TAG, "Comments: " + " ");

                    ApiManager.getInstance().StoreRequest(
                            null,
                            ReEmail,
                            ReEmployeeId,
                            ReCompanyCode,
                            ReIssueMasterId,
                            ReSupportId,
                            ReRequestId,
                            RePriorityId,
                            Resummary,
                            Redescription,
                            RestatusCode,
                            ReComments, new Callback<Success>() {
                                /*
                                 * Store the new request.
                                 * When the api call is Success..
                                 * */
                                @Override
                                public void onResponse(@NonNull Call<Success> call, @NonNull Response<Success> response) {
                                    Success success = response.body();
                                    /*
                                     * Data store Success
                                     * */
                                    if (response.isSuccessful() && Objects.requireNonNull(success).getStatus().equals("true")) {
                                        Log.d(TAG, "New Request Add Success");
                                        tv_error.setVisibility(View.GONE);
                                        fragment = new WilcoConnect();
                                        replaceFragment(fragment);
                                    }
                                    /*
                                     * Data store Failure
                                     * */
                                    else {
                                        tv_error.setVisibility(View.VISIBLE);
                                        Log.d(TAG, "New Request Add Failure");
                                    }
                                }

                                /**
                                 * When the api call is Failure..
                                 * */
                                @Override
                                public void onFailure(@NonNull Call<Success> call, @NonNull Throwable t) {
                                    tv_error.setVisibility(View.VISIBLE);
                                    Log.d(TAG, "New Request Add Failure");
                                }
                            });
                }
            }
        });
        return view;
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

            btn_file = view.findViewById(R.id.btn_attach);
            btn_file.setText(picturePath);
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
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            Intent image_intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                            startActivityForResult(image_intent, RESULT_LOAD_FILE);
                                        }
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

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}