package com.android.wilcoconnect.api;

import com.android.wilcoconnect.app.MainApplication;
import com.android.wilcoconnect.model.MenuList.Menu;
import com.android.wilcoconnect.model.leave.ApplyLeavePost;
import com.android.wilcoconnect.model.leave.ApproveLeaveData;
import com.android.wilcoconnect.model.leave.ApprovePost;
import com.android.wilcoconnect.model.leave.HolidayData;
import com.android.wilcoconnect.model.leave.LeaveCalender;
import com.android.wilcoconnect.model.leave.LeaveType;
import com.android.wilcoconnect.model.leave.Myleave;
import com.android.wilcoconnect.model.leave.TeamLeaveAutoList;
import com.android.wilcoconnect.model.profile.AdditionalDetails;
import com.android.wilcoconnect.model.profile.AttachmentDetails;
import com.android.wilcoconnect.model.profile.BasicDetails;
import com.android.wilcoconnect.model.profile.EducationDetails;
import com.android.wilcoconnect.model.profile.EmergencyDetails;
import com.android.wilcoconnect.model.profile.ExperienceDetails;
import com.android.wilcoconnect.model.profile.FamilyDetails;
import com.android.wilcoconnect.model.profile.LastPositionDetails;
import com.android.wilcoconnect.model.profile.PassportDetails;
import com.android.wilcoconnect.model.profile.ReferenceDetails;
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

        /*
         * Get the Menu List
         * */
        public void getMenuList(AddRequest request, Callback<Menu> callback){
            Call<Menu> menuList = service.getMobileMenuList(request.Email,request.companyCode,request.EmployeeID);
            menuList.enqueue(callback);
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
        public void getIssueTracking(SendRequest request, Callback<IssueTracking> callback) {
            Call<IssueTracking> myRequest = service.IssueTracking(request.Email, request.companyCode, request.employeeID, request.statusCode, request.masterID);
            myRequest.enqueue(callback);
        }

        /*
        * Get the Profile Basic information
        * */
        public void getBasicDetail(AddRequest request, Callback<BasicDetails> callback){
            Call<BasicDetails> information = service.getProfileData(request.Email,request.companyCode,request.EmployeeID);
            information.enqueue(callback);
        }

        /*
         * Get the Profile Family Details
         * */
        public void getFamilyDetail(AddRequest request, Callback<FamilyDetails> callback){
            Call<FamilyDetails> information = service.getFamilyData(request.Email,request.companyCode,request.EmployeeID);
            information.enqueue(callback);
        }

        /*
         * Get the Profile Education Details
         * */
        public void getEducationDetail(AddRequest request, Callback<EducationDetails> callback){
            Call<EducationDetails> information = service.getEducationData(request.Email,request.companyCode,request.EmployeeID);
            information.enqueue(callback);
        }

        /*
         * Get the Profile Last Position Details
         * */
        public void getLastPostionDetail(AddRequest request, Callback<LastPositionDetails> callback){
            Call<LastPositionDetails> information = service.getLastPositionData(request.Email,request.companyCode,request.EmployeeID);
            information.enqueue(callback);
        }

        /*
        * Get the Profile Experience Details
        * */
        public void getExperienceDetail(AddRequest request, Callback<ExperienceDetails> callback){
            Call<ExperienceDetails> experienceDetails = service.getExperienceDetails(request.Email,request.companyCode,request.EmployeeID);
            experienceDetails.enqueue(callback);
        }

        /*
        * Get the Profile Reference Details
        * */
        public void getReferenceDetail(AddRequest request, Callback<ReferenceDetails> callback){
            Call<ReferenceDetails> referenceDetailsList = service.getReferenceDetails(request.Email,request.companyCode,request.EmployeeID);
            referenceDetailsList.enqueue(callback);
        }

        /*
        * Get the Profile Additional Details
        * */
        public void getAdditionalDetail(AddRequest request, Callback<AdditionalDetails> callback){
            Call<AdditionalDetails> additionalDetail = service.getAdditionalDetail(request.Email,request.companyCode,request.EmployeeID);
            additionalDetail.enqueue(callback);
        }

        /*
        * Get the Profile Emergency Details
        * */
        public void getEmergencyDetail(AddRequest request, Callback<EmergencyDetails> callback){
            Call<EmergencyDetails> emergencyDetail = service.getEmergencyDetail(request.Email,request.companyCode,request.EmployeeID);
            emergencyDetail.enqueue(callback);
        }

        /*
        * Get the Profile Passport Details
        * */
        public void getPassportDetail(AddRequest request, Callback<PassportDetails> callback){
            Call<PassportDetails> passportDetailsData = service.getPassportDetail(request.Email,request.companyCode,request.EmployeeID);
            passportDetailsData.enqueue(callback);
        }

        /*
        * Get the Profile Attachment Details
        * */
        public void getAttachmentDetails(AddRequest request, Callback<AttachmentDetails> callback){
            Call<AttachmentDetails> attachmentDetail = service.getAttachmentDetail(request.Email,request.companyCode,request.EmployeeID);
            attachmentDetail.enqueue(callback);
        }

        /*
        * Get the Holiday List
        * */
        public void getHolidayList(AddRequest request, Callback<HolidayData> callback) {
            Call<HolidayData> holidayList = service.getHolidayList(request.Email, request.companyCode, request.EmployeeID);
            holidayList.enqueue(callback);
        }

        /*
        * Get the MyLeave List
        * */
        public void getMyLeaveList(AddRequest request, Callback<Myleave> callback){
            Call<Myleave> leaveList = service.getMyLeaveList(request.Email, request.companyCode, request.EmployeeID);
            leaveList.enqueue(callback);
        }

        /*
        * Get the LeaveType
        * */
        public void getLeaveType(AddRequest request, Callback<LeaveType> callback){
            Call<LeaveType> leaveType = service.getLeaveType(request.Email,request.companyCode,request.EmployeeID);
            leaveType.enqueue(callback);
        }

        /*
         * Store the Leave Request
        * */
        public void storeLeaveDetail(ApplyLeavePost leavePost, Callback<Success> callback){
            Call<Success> reply = service.storeLeaveRequest(leavePost);
            reply.enqueue(callback);
        }

        /*
        * Get the overall approve list
        * */
        public void getApproveList(AddRequest request, Callback<ApproveLeaveData> callback){
            Call<ApproveLeaveData> approveLeaveData = service.getApproveLeaveList(request.Email,request.companyCode,request.EmployeeID);
            approveLeaveData.enqueue(callback);
        }

        /*
        * Store the Approve leave list based on the click
        * */
        public void storeApproveLeave(ApprovePost post,Callback<Success> callback){
            Call<Success> reply = service.storeApproveList(post);
            reply.enqueue(callback);
        }

        /*
        * Get the Leave details for Calender For Team
        * */
        public void getLeaveDetailforCalender(AddRequest request, Callback<LeaveCalender> callback){
            Call<LeaveCalender> calender = service.getCalenderDetail(request.Email,request.companyCode,request.EmployeeID);
            calender.enqueue(callback);
        }

        /*
        * Get the Employee Name List for Apply Leave For Team Module
        * */
        public void getEmployeeNameList(AddRequest request, Callback<TeamLeaveAutoList> callback){
            Call<TeamLeaveAutoList> teamlist = service.getEmployeeNameList(request.Email,request.companyCode,request.EmployeeID);
            teamlist.enqueue(callback);
        }

        /*
        * Get the LeaveType for Apply Leave For Team
        * */
        public void getLeaveTypeforTeam(AddRequest request,Callback<LeaveType> callback){
            Call<LeaveType> leaveType = service.getTeamLeaveType(request.Email,request.companyCode,request.EmployeeID);
            leaveType.enqueue(callback);
        }
}
