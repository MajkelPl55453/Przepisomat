package pl.przepisomat.przepisomat.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import pl.przepisomat.przepisomat.R;
import pl.przepisomat.przepisomat.api.model.Recipe;
import pl.przepisomat.przepisomat.api.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesActivity extends BaseActivity{
    public ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        listView = findViewById(R.id.recipiesListView);

        setDefaults();

        Call<RecipesList> categoryListCall = ApiService.getService().getRecipes(10,1);
        categoryListCall.enqueue(new Callback<RecipesList>() {
            @Override
            public void onResponse(@NonNull Call<RecipesList> call, @NonNull Response<RecipesList> response) {
                RecipesList recipes = response.body();
                ArrayAdapter<Recipe> arrayAdapter = new ArrayAdapter<>(RecipesActivity.this, android.R.layout.simple_list_item_1, recipes.recipes);
                listView.setAdapter(arrayAdapter);
                Log.d("TAG", new Gson().toJson(recipes));
            }

            @Override
            public void onFailure(@NonNull Call<RecipesList> call, Throwable t) {
                Toast.makeText(RecipesActivity.this, "Blad pobierania danych: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    public static class RecipesList{
        public List<Recipe> recipes;
    }
}


