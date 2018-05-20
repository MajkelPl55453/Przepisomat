package pl.przepisomat.przepisomat.api;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Majkel on 2018-05-03.
 */

public interface ApiClient {

    @GET("getCategories")
    Call<CategoryList> getCategories();

    @GET("getRecipesNameList")
    Call<RecipesNames> getRecipesNames();

}
