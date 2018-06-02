package pl.przepisomat.przepisomat.api.service;

import pl.przepisomat.przepisomat.activity.RecipesActivity;
import pl.przepisomat.przepisomat.api.model.CategoryList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Majkel on 2018-05-03.
 */
public interface ApiClient {

    @GET("getCategories")
    Call<CategoryList> getCategories();

    @GET("getRecipesList/{id}/{limit}/{offset}")
    Call<RecipesActivity.RecipesList> getRecipes(@Path("id") Long id, @Path("limit") int limit, @Path("offset") int offset);
}
