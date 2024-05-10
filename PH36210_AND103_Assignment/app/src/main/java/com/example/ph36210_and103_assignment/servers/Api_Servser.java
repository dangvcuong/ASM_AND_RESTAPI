package com.example.ph36210_and103_assignment.servers;

import com.example.ph36210_and103_assignment.modle.CalendarS;
import com.example.ph36210_and103_assignment.modle.Cart;
import com.example.ph36210_and103_assignment.modle.Response;
import com.example.ph36210_and103_assignment.modle.Shose;
import com.example.ph36210_and103_assignment.modle.SpHeart;
import com.example.ph36210_and103_assignment.modle.users;


import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api_Servser {
    public static String BASE_URL = "http://192.168.1.7:3000/api/";

    //192.168.1.7
    //10.0.2.2
    @Multipart
    @POST("register-send-email")
    Call<Response<users>> register(@Part("email") RequestBody email,
                                   @Part("password") RequestBody password,
                                   @Part("fullname") RequestBody fullname);
    @POST("login")
    Call<Response<users>> login(@Body users users);
    @GET("get_user_id/{id}")
    Call<Response<users>> getuserID(@Path("id") String id);
    @GET("get-list-user")
    Call<Response<users>> getUser();
    @Multipart
    @PUT("update-user-id/{id}")
    Call<Response<users>> updatause(@Path("id") String id,
                                    @Part("fullname") RequestBody fullname,
                                    @Part("ngay_sinh_user") RequestBody ngay_sinh_user,
                                    @Part("dia_chi_user") RequestBody dia_chi_user,
                                    @Part("sdt_user") RequestBody sdt_user,
                                    @Part MultipartBody.Part hinh_anh_user);
    @Multipart
    @PUT("updata-pass-id/{id}")
    Call<Response<users>> doiPass(@Path("id")String id,
                                  @Part("password") RequestBody password);
    @GET("get-list-shose")
    Call<Response<ArrayList<Shose>>> getListShose();
    @GET("search-shose")
    Call<Response<ArrayList<Shose>>> searchShose(@Query("key") String key);
    @POST("add-cart")
    Call<Response<Cart>> addCart(@Body Cart cart);
    @GET("get_cart")
    Call<Response<ArrayList<Cart>>> getListCart();
    @GET("get_cart_id/{id_User_cart}")
    Call<Response<ArrayList<Cart>>> getCartIdUser(@Path("id_User_cart") String id_User_cart);
    @DELETE("delete-cart-id/{id}")
    Call<Response<Cart>> deleteCart(@Path("id") String id);
    @GET("get_caledar")
    Call<Response<ArrayList<CalendarS>>> getListCalendar();
    @POST("add-calendar")
    Call<Response<CalendarS>> addCalendar(@Body CalendarS calendar);
    @GET("get_calendar_id/{id_User_Calendar}")
    Call<Response<ArrayList<CalendarS>>> getCalendarIdUser(@Path("id_User_Calendar") String id_User_Calendar);

    @POST("add-heart")
    Call<Response<SpHeart>> addHeart(@Body SpHeart spHeart);

    @GET("get_heart_id/{id_User_heart}")
    Call<Response<ArrayList<SpHeart>>> getHeartIdUser(@Path("id_User_heart") String id_User_heart);
    @DELETE("delete-heart-id/{id}")
    Call<Response<SpHeart>> deleteHeart(@Path("id") String id);
    @GET("get_heart")
    Call<Response<ArrayList<SpHeart>>> getListHeart();
}
