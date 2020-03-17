package com.android.wilcoconnect.api;

import androidx.annotation.NonNull;

import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.model.profile.BasicDetails;
import com.android.wilcoconnect.model.profile.EducationDetails;
import com.android.wilcoconnect.model.profile.FamilyDetails;
import com.android.wilcoconnect.model.profile.LastPositionDetails;
import com.android.wilcoconnect.model.wilcoconnect.AddRequest;
import com.android.wilcoconnect.model.wilcoconnect.IssueTracking;
import com.android.wilcoconnect.model.login.Login;
import com.android.wilcoconnect.model.wilcoconnect.MyRequestData;
import com.android.wilcoconnect.model.wilcoconnect.RequestType;
import com.android.wilcoconnect.model.wilcoconnect.SendRequest;
import com.android.wilcoconnect.model.common.Success;
import com.android.wilcoconnect.model.login.TokenData;
import com.android.wilcoconnect.model.common.UserData;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    /*
     * Initialize the XML element or views
     * */
     private static ApiInterface service;
     private static ApiManager apiRequestManager;
     private OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
     private String headers;

        /**
         * Define the Base Url in Retrofit
         * */
        private ApiManager() {
            /*
            * Define the Authorization Header. From the Api call
            * */
            headers = MainApplication.token_data;

            /*
            * When the Header is NULL then Call the Authorization based Api
            * */
            if(headers==null) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(service.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                service = retrofit.create(ApiInterface.class);
            }

            /*
            * Token is Enable then Call the Authorized Api.
            * */
            else{
                httpClient.addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Authorization", headers)
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                });
                OkHttpClient client = httpClient.build();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(service.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build();
                service = retrofit.create(ApiInterface.class);
            }

        }

        /**
         * Define the Instance
         * */
        public static ApiManager getInstance() {
            if (apiRequestManager == null) {
                apiRequestManager = new ApiManager();
            }
            return apiRequestManager;
        }

        /**
        * Logout operation to reset the Object to NULL
        * */
        public static ApiManager resetInstance() {
        return apiRequestManager = null;
    }

        /**
        * Get the Token from the Api Call
        * */
        public void getToken(Login data, Callback<TokenData> callback){
            Call<TokenData> tokenDataCall = service.getToken(data.username,data.password,data.grant_type);
            tokenDataCall.enqueue(callback);
        }

        /**
         * Login Api Call
         * */
        public void createUser(Login data, Callback<UserData> callback) {
            Call<UserData> userCall = service.login(data);
            userCall.enqueue(callback);
        }

        /**
         * Get the Overall Dropdown Data
         * */
        public void getDropdownData(AddRequest request, Callback<RequestType> callback) {
            Call<RequestType> dropdowndata = service.getDropdownData(request.Email, request.companyCode, request.EmployeeID);
            dropdowndata.enqueue(callback);
        }

        /**
         * store the New Request
         * And the also used to Update the Request
         * */
        public void StoreRequest(MultipartBody.Part file,
                                 RequestBody ReEmail,
                                 RequestBody ReEmployeeId,
                                 RequestBody ReCompanyCode,
                                 RequestBody ReIssueMasterId,
                                 RequestBody ReSupportId,
                                 RequestBody ReRequestId,
                                 RequestBody RePriorityId,
                                 RequestBody Resummary,
                                 RequestBody Redescription,
                                 RequestBody RestatusCode,
                                 RequestBody ReComments,
                                 Callback<Success> callback) {

            Call<Success> data = service.StoreRequest(file,
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
                    ReComments);
            data.enqueue(callback);
        }

        /**
         * Get the List of Data From the Api Call
         * */
        public void getMyRequest(SendRequest request, Callback<MyRequestData> callback) {
            Call<MyRequestData> myRequest = service.MyRequest(request.Email, request.companyCode, request.employeeID, request.statusLink);
            myRequest.enqueue(callback);
        }

        /**
         * Get the IssueTracking Details
         * */
        public void getIssueTraking(SendRequest request, Callback<IssueTracking> callback) {
            Call<IssueTracking> myRequest = service.IssueTracking(request.Email, request.companyCode, request.employeeID, request.statusCode, request.masterID);
            myRequest.enqueue(callback);
        }

        /*
        * Get the Profile Basic information
        * */
        public void getBasicDetail(String EmployeeId, Callback<BasicDetails> callback){
            Call<BasicDetails> information = service.get_ProfileData(EmployeeId);
            information.enqueue(callback);
        }

        /*
         * Get the Profile Basic information
         * */
        public void getFamilyDetail(String EmployeeId, Callback<FamilyDetails> callback){
            Call<FamilyDetails> information = service.get_FamilyData(EmployeeId);
            information.enqueue(callback);
        }

        /*
         * Get the Profile Basic information
         * */
        public void getEducationDetail(String EmployeeId, Callback<EducationDetails> callback){
            Call<EducationDetails> information = service.get_EducationData(EmployeeId);
            information.enqueue(callback);
        }

        /*
         * Get the Profile Basic information
         * */
        public void getLastPostionDetail(String EmployeeId, Callback<LastPositionDetails> callback){
            Call<LastPositionDetails> information = service.get_LastPositionData(EmployeeId);
            information.enqueue(callback);
        }
}
