package pl.przepisomat.przepisomat.api.service;

import pl.przepisomat.przepisomat.activity.RecipeDetailsActivity;
import pl.przepisomat.przepisomat.activity.RecipesActivity.RecipesList;
import pl.przepisomat.przepisomat.api.model.CategoryList;
import pl.przepisomat.przepisomat.api.model.RecipesNames;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Majkel on 2018-05-03.
 */
public interface ApiClient {

    @GET("getCategories")
    Call<CategoryList> getCategories();

    @GET("getRecipesNameList")
    Call<RecipesNames> getRecipesNames();

    @GET("getRecipesList/{id}/{limit}/{offset}")
    Call<RecipesList> getRecipes(@Path("id") Long id, @Path("limit") int limit, @Path("offset") int offset);

    @GET("getRecipe/{id}")
    Call<RecipeDetailsActivity.ResponseRecipe> getRecipe(@Path("id") Long id);

    @GET("getRecipesListByPopularity/{limit}/{offset}")
    Call<RecipesList> getMostPopularRecipes(@Path("limit") int limit, @Path("offset") int offset);

    @POST("getRecipesListByIds/{limit}/{offset}")
    Call<RecipesList> getRecipesListByIds(@Path("limit") int limit, @Path("offset") int offset, @Field("ids") String ids);
}
