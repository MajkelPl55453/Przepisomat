package pl.przepisomat.przepisomat.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Majkel on 2018-05-03.
 */

public interface ApiClient {

    @GET("getCategories")
    Call<CategoryList> getCategories();

    @POST("/login")
    @FormUrlEncoded
    Call<LoginResponse> login (@Field("username") String username,
                        @Field("password") String password);


}
