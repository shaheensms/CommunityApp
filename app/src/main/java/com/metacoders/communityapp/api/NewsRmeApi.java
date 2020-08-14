package com.metacoders.communityapp.api;

import com.google.gson.JsonObject;
import com.metacoders.communityapp.models.Audio_List_Model;
import com.metacoders.communityapp.models.LoginResponse;
import com.metacoders.communityapp.models.News_List_Model;
import com.metacoders.communityapp.models.Profile_Model;
import com.metacoders.communityapp.models.RegistrationResponse;
import com.metacoders.communityapp.models.Video_List_Model;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface NewsRmeApi {

    @FormUrlEncoded
    @POST("auth/user-login")
    Call<LoginResponse> login(
            @Field("user_name") String userName,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("registration/user-registration")
    Call<RegistrationResponse> registration(
            @Field("name") String name,
            @Field("user_name") String username,
            @Field("email") String email,
            @Field("password") String password
    );


    @FormUrlEncoded
    @POST("auth/forget-password")
    Call<LoginResponse.forgetPassResponse> forget_password(
            @Field("user_name") String user_name,
            @Field("email") String email
    );


    @FormUrlEncoded
    @POST("profile/change-password")
    Call<LoginResponse.forgetPassResponse> changePassword(
            @Field("old_password") String old_password,
            @Field("new_password") String new_password
    );


    @GET("common/get-news-lists")
    Call<News_List_Model> getNewsList();

    @GET("common/get-news-audio-lists")
    Call<Audio_List_Model> getAudioList();

    @GET("common/get-news-video-lists")
    Call<Video_List_Model> getVideoList();

    @GET("profile/get-profile-info")
    Call<Profile_Model.Profile_Response> getProfileInfo();

    @Multipart
    @POST("profile/store-photo")
    Call<UploadResult> uploadImage(@Part("image\"; filename=\"myfile.jpg\" ") RequestBody file);

   // @FormUrlEncoded
    @Multipart
    @POST("dashboard/news-post") //("video\"; filename=\"myfile.mp4\" ")  ("image\"; filename=\"myfifle.image\" ")
    Call<LoginResponse.forgetPassResponse> uploadFilePost(@Part MultipartBody.Part file,
                                                          @Part("title")  RequestBody  title,
                                                          @Part("title_slug") RequestBody title_slug,
                                                          @Part("content")  RequestBody content,
                                                          @Part("post_type")  RequestBody post_type,
                                                          @Part("lang_id")  RequestBody lang_id,
                                                          @Part("category_id")   RequestBody category_id,
                                                          @Part("sub_category_id")   RequestBody sub_category_id,
                                                          @Part MultipartBody.Part image);

    @Multipart
    @POST("dashboard/news-post") //("video\"; filename=\"myfile.mp4\" ")  ("image\"; filename=\"myfifle.image\" ")
    Call<LoginResponse.forgetPassResponse> uploadPost(    @Part("title")  RequestBody  title,
                                                          @Part("title_slug") RequestBody title_slug,
                                                          @Part("content")  RequestBody content,
                                                          @Part("post_type")  RequestBody post_type,
                                                          @Part("lang_id")  RequestBody lang_id,
                                                          @Part("category_id")   RequestBody category_id,
                                                          @Part("sub_category_id")   RequestBody sub_category_id,
                                                          @Part MultipartBody.Part image);
    


}
