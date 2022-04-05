package com.furkanbalci.mesat.network.user;

import com.furkanbalci.mesat.models.user.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * User retrofit service.
 */
public interface UserService {

    /**
     * Gets all users.
     *
     * @return User list.
     */
    @GET("users/all")
    Call<List<User>> getAll();

    /**
     * Gets user.
     *
     * @param id User Id.
     * @return User.
     */
    @GET("users/get")
    Call<User> get(@Query("id") int id);

    /**
     * Gets user.
     *
     * @param mail User mail.
     * @return User.
     */
    @GET("users/getByMail")
    Call<User> getByMail(@Query("mail") String mail);

    /**
     * Gets user.
     *
     * @param mail User mail.
     * @return User.
     */
    @GET("users/check")
    Call<User> check(@Query("mail") String mail, @Query("phone") String phone);

    /**
     * Register user.
     *
     * @param user User.
     * @return User.
     */
    @POST("users/add")
    Call<User> register(@Body User user);

}
