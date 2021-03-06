package com.android.wilcoconnect.api;

import com.android.wilcoconnect.model.MenuList.Menu;
import com.android.wilcoconnect.model.expense.FoodExpenseDetail;
import com.android.wilcoconnect.model.expense.FoodExpenseProjectList;
import com.android.wilcoconnect.model.leave.ApplyLeavePost;
import com.android.wilcoconnect.model.leave.ApproveLeaveData;
import com.android.wilcoconnect.model.leave.ApprovePost;
import com.android.wilcoconnect.model.leave.Holiday;
import com.android.wilcoconnect.model.leave.LeaveCalender;
import com.android.wilcoconnect.model.leave.MyLeave;
import com.android.wilcoconnect.model.leave.Onduty.OnDutyApprovePost;
import com.android.wilcoconnect.model.leave.Onduty.OnDutyDetails;
import com.android.wilcoconnect.model.leave.Onduty.OnDutyMasterData;
import com.android.wilcoconnect.model.leave.Onduty.OnDutyPost;
import com.android.wilcoconnect.model.leave.TeamLeaveAutoList;
import com.android.wilcoconnect.model.leave.compensatory.CompOffApprovePost;
import com.android.wilcoconnect.model.leave.compensatory.CompOffDetail;
import com.android.wilcoconnect.model.leave.compensatory.CompOffPost;
import com.android.wilcoconnect.model.leave.compensatory.GetCompOffDays;
import com.android.wilcoconnect.model.leave.leavebalance.GetLeaveBalance;
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
import com.android.wilcoconnect.model.wilcoconnect.IssueTracking;
import com.android.wilcoconnect.model.login.Login;
import com.android.wilcoconnect.model.wilcoconnect.MyRequestData;
import com.android.wilcoconnect.model.wilcoconnect.RequestType;
import com.android.wilcoconnect.model.common.Success;
import com.android.wilcoconnect.model.login.TokenData;
import com.android.wilcoconnect.model.common.UserData;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    String BASE_URL = "http://192.168.1.50/hrdev/";
    //String BASE_URL = "http://mywilco.wilcosource.com/";

    /*
    * Get the Token to get the session
    * */
    @FormUrlEncoded
    @POST("token")
    Call<TokenData> getToken(@Field("username") String username,
                             @Field("Password") String password,
                             @Field("grant_type") String grant_type);

    /*
    * Login Operation
    * */
    @POST("api/Login/Post")
    Call<UserData> login(@Body Login data);

    /*
     * Get Overall menu list
     * */
    @GET("api/Menu/mobileMenuList")
    Call<Menu> getMobileMenuList(@Query("Email") String Email,
                                 @Query("companyCode") String companyCode,
                                 @Query("employeeID") String employeeID);

    /*
    * Get the overall service request based
    * Support Function
    * Request Type
    * Priority
    * Dropdown data
    * */
    @GET("api/ServiceRequest/newServiceRequest?")
    Call<RequestType> getDropdownData(@Query("Email") String Email,
                                      @Query("companyCode") String companyCode,
                                      @Query("employeeID") String employeeID);
    /*
    * Store the particular service request to database
    * */
    @Multipart
    @POST("api/ServiceRequest/StoreRequests")
    Call<Success> StoreRequest(@Part MultipartBody.Part file,
                               @Part("Email") RequestBody Email,
                               @Part("EmployeeID") RequestBody employeeID,
                               @Part("companyCode") RequestBody companyCode,
                               @Part("IssueMasterID") RequestBody IssueMasterID,
                               @Part("supportFunctionID") RequestBody supportFunctionID,
                               @Part("requestTypeId") RequestBody requestTypeId,
                               @Part("priorityLevelID") RequestBody priorityLevelID,
                               @Part("issueSummary") RequestBody issueSummary,
                               @Part("issueDescription") RequestBody issueDescription,
                               @Part("statuscode") RequestBody statuscode,
                               @Part("Comments") RequestBody Comments);

    /*
    * Get the overall request detail for the particular user
    * */
    @GET("api/ServiceRequest/myRequestList")
    Call<MyRequestData> MyRequest(@Query("Email") String Email,
                                  @Query("companyCode") String companyCode,
                                  @Query("employeeID") String employeeID,
                                  @Query("statusLink") String statusLink);

    /*
    * Get the overall operation information
    * */
    @GET("api/ServiceRequest/getServiceRequestDetails")
    Call<IssueTracking> IssueTracking(@Query("Email") String Email,
                                      @Query("companyCode") String companyCode,
                                      @Query("employeeID") String employeeID,
                                      @Query("statusCode") String statusCode,
                                      @Query("masterID") int masterID);

    /*
    * Get the Profile Module BasicInformation and Address
    * */
    @GET("api/ProfileRequest/GetBasicDetails")
    Call<BasicDetails> getProfileData(@Query("eMail") String Email,
                                       @Query("companyCode") String companyCode,
                                       @Query("employeeID") String employeeID);

    /*
    * Get the Profile Module Family Details
    * */
    @GET("api/ProfileRequest/GetFamilyDetails")
    Call<FamilyDetails> getFamilyData(@Query("eMail") String Email,
                                       @Query("companyCode") String companyCode,
                                       @Query("employeeID") String employeeID);

    /*
     * Get the Profile Module Education Details
     * */
    @GET("api/ProfileRequest/GetEducationDetails")
    Call<EducationDetails> getEducationData(@Query("eMail") String Email,
                                             @Query("companyCode") String companyCode,
                                             @Query("employeeID") String employeeID);

    /*
     * Get the Profile Module Last Position Details
     * */
    @GET("api/ProfileRequest/GetLastPositionDetails")
    Call<LastPositionDetails> getLastPositionData(@Query("eMail") String Email,
                                                   @Query("companyCode") String companyCode,
                                                   @Query("employeeID") String employeeID);

    /*
    * Get the Profile Module Experience Details
    * */
    @GET("api/ProfileRequest/GetExperienceDetailsOfAnEmployee")
    Call<ExperienceDetails> getExperienceDetails(@Query("eMail") String Email,
                                                 @Query("companyCode") String companyCode,
                                                 @Query("employeeID") String employeeID);

    /*
    * Get the Profile Module Reference Details
    * */
    @GET("api/ProfileRequest/GetReferenceDetailsOfAnEmployee")
    Call<ReferenceDetails> getReferenceDetails(@Query("eMail") String Email,
                                               @Query("companyCode") String companyCode,
                                               @Query("employeeID") String employeeID);

    /*
     * Get the Profile Module Additional Detail
     * */
    @GET("api/ProfileRequest/GetAdditionalDetailsOfAnEmployee")
    Call<AdditionalDetails> getAdditionalDetail(@Query("eMail") String Email,
                                                @Query("companyCode") String companyCode,
                                                @Query("employeeID") String employeeID);

    /*
    * Get the Profile Module Emergency Detail
    * */
    @GET("api/ProfileRequest/getEmergencyDetailsofAnEmployee")
    Call<EmergencyDetails> getEmergencyDetail(@Query("eMail") String Email,
                                              @Query("companyCode") String companyCode,
                                              @Query("employeeID") String employeeID);

    /*
    * Get the Profile Module Passport Detail
    * */
    @GET("api/ProfileRequest/getPassportDetailsofAnEmployee")
    Call<PassportDetails> getPassportDetail(@Query("eMail") String Email,
                                            @Query("companyCode") String companyCode,
                                            @Query("employeeID") String employeeID);

    /*
    * Get the Profile Module Attachment Detail
    * */
    @GET("api/ProfileRequest/getAttachmentDetailsofAnEmployee")
    Call<AttachmentDetails> getAttachmentDetail(@Query("eMail") String Email,
                                                @Query("companyCode") String companyCode,
                                                @Query("employeeID") String employeeID);

    /*
     * Get the holiday list
     * */
    @GET("api/Leave/getHolidayList")
    Call<Holiday> getHolidayList(@Query("Email") String Email,
                                 @Query("companyCode") String companyCode,
                                 @Query("employeeID") String employeeID);

    /*
    * Get My leave list
    * */
    @GET("api/Leave/myleaveList")
    Call<MyLeave> getMyLeaveList(@Query("Email") String Email,
                                 @Query("companyCode") String companyCode,
                                 @Query("employeeID") String employeeID);

    /*
    * Get the Leave Balance Detail
    * */
    @GET("api/Leave/GetLeaveBalanceList")
    Call<GetLeaveBalance> getLeaveBalance(@Query("Email") String Email,
                                          @Query("companyCode") String companyCode,
                                          @Query("employeeID") String employeeID);

    /*
    * Store the Leave Request
    * */
    @POST("api/Leave/StoreApplyLeave")
    Call<Success> storeLeaveRequest(@Body ApplyLeavePost data);

    /*
    * Get the Approve Leave list
    * */
    @GET("api/Leave/GetLeaveRequestList")
    Call<ApproveLeaveData> getApproveLeaveList(@Query("Email") String Email,
                                               @Query("companyCode") String companyCode,
                                               @Query("employeeID") String employeeID);

    /*
    * Get the Approved List Data
    * */
    @GET("api/Leave/GetLeaveRequestApprovedList")
    Call<ApproveLeaveData> getApprovedList(@Query("Email") String Email,
                                           @Query("companyCode") String companyCode,
                                           @Query("employeeID") String employeeID);

    /*
    * Store the Approve List based on the click
    * */
    @POST("api/Leave/LeaveApproveStore")
    Call<Success> storeApproveList(@Body ApprovePost data);

    /*
    * Get the Leave details for Calender For Team
    * */
    @GET("api/Leave/GetEmployeeLeaveListCalender")
    Call<LeaveCalender> getCalenderDetail(@Query("Email") String Email,
                                          @Query("companyCode") String companyCode,
                                          @Query("employeeID") String employeeID);

    /*
    * Get the Employee Name List To select the Apply Leave for Team
    * */
    @GET("api/Leave/GetTeamEmployeeList")
    Call<TeamLeaveAutoList> getEmployeeNameList(@Query("Email") String Email,
                                                @Query("companyCode") String companyCode,
                                                @Query("employeeID") String employeeID);

    /*
    * Store Apply leave for Team
    * */
    @POST("api/Leave/StoreLeaveForTeam")
    Call<Success> storeLeaveForTeam(@Body ApplyLeavePost data);

    /*
    * Get the On-Duty Data Details
    * */
    @GET("api/WorkFromHome/GetmyOnDutyList")
    Call<OnDutyDetails> getMyOnDutyDetails(@Query("Email") String Email,
                                         @Query("companyCode") String companyCode,
                                         @Query("employeeID") String employeeID);

    /*
     * Get the Approved List in On - Duty
     * */
    @GET("api/workfromhome/GetOnDutyApprovedList")
    Call<OnDutyDetails> getApproveOnDutyDetail(@Query("Email") String Email,
                                               @Query("companyCode") String companyCode,
                                               @Query("employeeID") String employeeID);

    /*
    * Get the Master Detail for Apply On-Duty Dropdown Data
    * */
    @GET("api/WorkFromHome/GetOnDutyMaster")
    Call<OnDutyMasterData> getMasterList(@Query("Email") String Email,
                                         @Query("companyCode") String companyCode,
                                         @Query("employeeID") String employeeID);

    /*
    * Apply On-Duty Store
    * */
    @POST("api/WorkFromHome/StoreOD")
    Call<Success> storeOnDuty(@Body OnDutyPost data);

    /*
    * Get the Applied Detail to Display Approve List Page
    * */
    @GET("api/workfromhome/GetOnDutyRequest")
    Call<OnDutyDetails> getAppliedOnDutyDetail(@Query("Email") String Email,
                                               @Query("companyCode") String companyCode,
                                               @Query("employeeID") String employeeID);

    /*
    * Store the Approve or Reject Details
    * */
    @POST("api/WorkFromHome/UpdateStatus")
    Call<Success> storeApproveOrRejectOnDuty(@Body OnDutyApprovePost post);

    /*
    * Get my Comp-Off Details
    * */
    @GET("api/compoff/GetmyCompoffList")
    Call<CompOffDetail> getMyCompOffDetail(@Query("Email") String Email,
                                           @Query("companyCode") String companyCode,
                                           @Query("employeeID") String employeeID);

    /*
    * Store the Comp-Off Detail
    * */
    @POST("api/compoff/StoreCompoff")
    Call<Success> storeCompOffDetail(@Body CompOffPost post);

    /*
    * Get the Approve Comp-Off Detail
    * */
    @GET("api/compoff/GetApproverCompoffList")
    Call<CompOffDetail> getApproveCompOffDetail(@Query("Email") String Email,
                                         @Query("companyCode") String companyCode,
                                         @Query("employeeID") String employeeID);

    /*
    * Store Approve or Reject (Applied Comp-Off)
    * */
    @POST("api/compoff/StoreApprovedCompoff")
    Call<Success> storeApproveOrRejectCompOff(@Body CompOffApprovePost post);

    /*
    * Get the Days
    * */
    @GET("api/compoff/getNewcompoff")
    Call<GetCompOffDays> getDays(@Query("Email") String Email,
                                 @Query("companyCode") String companyCode,
                                 @Query("employeeID") String employeeID);

    /*
    * Get the Food Expense data
    * */
    @GET("api/foodexpenses/GetFoodExpenses")
    Call<FoodExpenseDetail> getFoodExpense(@Query("Email") String Email,
                                           @Query("employeeID") String employeeID);

    /*
    * Get the Project Name
    * */
    @GET("api/foodexpenses/GetAllProjects")
    Call<FoodExpenseProjectList> getProjectDetail(@Query("Email") String Email,
                                                  @Query("employeeID") String employeeID);

    @Multipart
    @POST("api/foodexpenses/StoreFoodExpenses")
    Call<Success> StoreNewFoodExpenseRequest(@Part("EmployeeID") RequestBody reEmployeeId,
                                             @Part("ReEmail") RequestBody reEmail,
                                             @Part("ReFoodID") RequestBody reFoodID,
                                             @Part("ReClaimNumber") RequestBody reClaimNumber,
                                             @Part("ReBillDate") RequestBody reBillDate,
                                             @Part("ReProjectID") RequestBody reProjectID,
                                             @Part("ReRemarks") RequestBody reRemarks,
                                             @Part("ReFoodExpenseAmount") RequestBody reFoodExpenseAmount,
                                             @Part MultipartBody.Part file);
}