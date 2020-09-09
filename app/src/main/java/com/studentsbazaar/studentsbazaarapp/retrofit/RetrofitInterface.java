package com.studentsbazaar.studentsbazaarapp.retrofit;


import com.studentsbazaar.studentsbazaarapp.model.DownloadResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetrofitInterface {
    @GET
    Call<DownloadResponse> getHomeComponentList(@Url String url);


    @FormUrlEncoded
    @POST("/forgotpassword.php")
    Call<String> updatepassword(@Field("data") String data,
                                @Field("password") String password);

    @FormUrlEncoded
    @POST("/checkuid.php")
    Call<String> checkuid(@Field("uid") String uid);

    @FormUrlEncoded
    @POST("/checgetuid.php")
    Call<String> getuid(@Field("uid") String phno,
                        @Field("did") String device);

    @FormUrlEncoded
    @POST("/updatememepost.php")
    Call<String> updatememepost(@Field("approved") String approved,
                                @Field("meme") String meme,
                                @Field("UID") String UID);


    @FormUrlEncoded
    @POST("/updatetoken.php")
    Call<String> updatetoken(@Field("Token") String token,
                             @Field("UID") String UID,
                             @Field("mobile_no") String mobno);


    @GET
    Call<String> addresultstoprofile(@Url String url);

    @GET
    Call<String> addresulttable(@Url String url);

    @GET
    Call<DownloadResponse> getposters(@Url String url);

    @GET
    Call<String> updateqsflag(@Url String url);

    @GET
    Call<String> updatephsts(@Url String url);

    @GET
    Call<DownloadResponse> getrs(@Url String url);

    @FormUrlEncoded
    @POST("/Login.php")
    Call<DownloadResponse> getLoginDetails(@Field("mobile") String mobile,
                                           @Field("password") String password,
                                           @Field("device") String device);

    @FormUrlEncoded
    @POST("/addtechnews.php")
    Call<String> addtechnews(@Field("comname") String comname,
                             @Field("poster") String poster);

    @FormUrlEncoded
    @POST("/addmems.php")
    Call<String> addmemes(@Field("uid") String uid,
                          @Field("comname") String comname,
                          @Field("poster") String poster);

    @GET
    Call<String> updateEventStatus(@Url String url);

    @GET
    Call<String> getupdateapp(@Url String url);

    @GET
    Call<DownloadResponse> getcollegedetails(@Url String url);

    @GET
    Call<String> getserversts(@Url String url);

    @FormUrlEncoded
    @POST("/addplacement.php")
    Call<String> addplacements(@Field("date") String date,
                               @Field("cname") String cname,
                               @Field("comname") String comname,
                               @Field("noofstu") String noofstu,
                               @Field("pack") String pack,
                               @Field("comments") String comments,
                               @Field("domain") String domain);

    @FormUrlEncoded
    @POST("/update_quizquestion.php")
    Call<String> addquizquestions(@Field("type") String qstype,
                               @Field("question") String qspicurl,
                               @Field("answer") String anspicurl,
                               @Field("crct") String ans);

    @GET("/Register.php")
    Call<DownloadResponse> addaccount(@Query("name") String name,
                            @Query("password") String password,
                            @Query("cname") String collegename,
                            @Query("affiliation") String affiliation,
                            @Query("degree") String degree,
                            @Query("dept") String dept,
                            @Query("year") String year,
                            @Query("sem") String sem,
                            @Query("mobile") String mobile,
                            @Query("acyear") String acyear,
                            @Query("mail") String mail,
                            @Query("device") String device, @Query("refcode") String refcode);

    @GET("/accountverify.php")
    Call<DownloadResponse> getaccountverification(@Query("device") String device);

    @GET("/get_quiz.php")
    Call<DownloadResponse> getQuizQuestions(@Query("uid") String uid);

    @GET
    Call<DownloadResponse> getquizresult(@Url String uid);

    @FormUrlEncoded
    @POST("/addeventdetails.php")
    Call<String> insertUser(
            @Field("eid") String eid,
            @Field("title") String title,
            @Field("type") String type,
            @Field("name") String name,
            @Field("start")
                    String start,
            @Field("end")
                    String end,
            @Field("conducted")
                    String conducted,
            @Field("degree")
                    String degree,
            @Field("dept")
                    String dept,
            @Field("colladd")
                    String colladd,
            @Field("district")
                    String district,
            @Field("state")
                    String state,
            @Field("organiser")
                    String organiser,
            @Field("events")
                    String events,
            @Field("eventdis")
                    String eventdis,
            @Field("eventweb")
                    String eventweb,
            @Field("collegeweb")
                    String collegeweb,
            @Field("insta")
                    String insta,
            @Field("cpname1")
                    String cpname1,
            @Field("cpno1")
                    String cpno1,
            @Field("cpname2")
                    String cpname2,
            @Field("cpno2")
                    String cpno2,
            @Field("poster")
                    String poster,
            @Field("fees")
                    String fees,
            @Field("accept")
                    String accept,
            @Field("lat")
                    String lat,
            @Field("long")
                    String longt,
            @Field("guest")
                    String guest,
            @Field("pronites")
                    String pronites,
            @Field("accom")
                    String accom,
            @Field("reach")
                    String reach,
            @Field("sponser")
                    String sponser,
            @Field("lastdate")
                    String lastdate,
            @Field("status")
                    String status,
            @Field("comment")
                    String comment);

    @FormUrlEncoded
    @POST("/coordinatorEventUpdate.php")
    Call<String> updateevents(@Field("title") String title,
                              @Field("type") String type,
                              @Field("start") String start,
                              @Field("end") String end,
                              @Field("dept") String dept,
                              @Field("district") String district,
                              @Field("state") String state,
                              @Field("organiser") String organiser,
                              @Field("events") String events,
                              @Field("eventdis") String eventdis,
                              @Field("eventweb") String eventweb,
                              @Field("collegeweb") String collegeweb,
                              @Field("insta")String insta,
                              @Field("cpname1") String cpname1,
                              @Field("cpno1") String cpno1,
                              @Field("cpname2") String cpname2,
                              @Field("cpno2") String cpno2,
                              @Field("fees") String fees,
                              @Field("pronites") String pronites,
                              @Field("accom") String accom,
                              @Field("reach") String reach,
                              @Field("lastdate") String lastdate,
                              @Field("guest") String guest,
                              @Field("Eid") String Eid,
                              @Field("id") String id);

}

