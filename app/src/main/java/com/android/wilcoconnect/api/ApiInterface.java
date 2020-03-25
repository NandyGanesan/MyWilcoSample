package com.android.wilcoconnect.api;

import com.android.wilcoconnect.model.MenuList.Menu;
import com.android.wilcoconnect.model.leave.ApplyLeavePost;
import com.android.wilcoconnect.model.leave.ApproveLeaveData;
import com.android.wilcoconnect.model.leave.ApprovePost;
import com.android.wilcoconnect.model.leave.HolidayData;
import com.android.wilcoconnect.model.leave.LeaveType;
import com.android.wilcoconnect.model.leave.Myleave;
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
    Call<HolidayData> getHolidayList(@Query("Email") String Email,
                                     @Query("companyCode") String companyCode,
                                     @Query("employeeID") String employeeID);

    /*
    * Get My leave list
    * */
    @GET("api/Leave/myleaveList")
    Call<Myleave> getMyLeaveList(@Query("Email") String Email,
                                 @Query("companyCode") String companyCode,
                                 @Query("employeeID") String employeeID);

    /*
     * Get Overall menu list
     * */
    @GET("api/Menu/mobileMenuList")
    Call<Menu> getMobileMenuList(@Query("Email") String Email,
                                 @Query("companyCode") String companyCode,
                                 @Query("employeeID") String employeeID);

    /*
    * Get the Leave Types
    * */
    @GET("api/Leave/GetLeaveType")
    Call<LeaveType> getLeaveType(@Query("Email") String Email,
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
    * Store the Approve List based on the click
    * */
    @POST("api/Leave/LeaveApproveStore")
    Call<Success> storeApproveList(@Body ApprovePost data);


}