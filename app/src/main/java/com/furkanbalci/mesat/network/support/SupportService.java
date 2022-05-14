package com.furkanbalci.mesat.network.support;

import com.furkanbalci.mesat.models.support.Support;
import com.furkanbalci.mesat.models.user.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface SupportService {


    /**
     * Add support.
     *
     * @param support Support.
     * @return Support.
     */
    @POST("supports/add")
    Call<Support> add(@Body Support support);

}
