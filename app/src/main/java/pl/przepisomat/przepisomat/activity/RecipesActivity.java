package pl.przepisomat.przepisomat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import pl.przepisomat.przepisomat.R;
import pl.przepisomat.przepisomat.adapters.RecipeArrayAdapter;
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
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        listView = findViewById(R.id.recipiesListView);

        setDefaults();

        Call<RecipesList> categoryListCall = ApiService.getService().getRecipes(10,1);
        categoryListCall.enqueue(new Callback<RecipesList>() {
            @Override
            public void onResponse(@NonNull Call<RecipesList> call, @NonNull Response<RecipesList> response) {
                RecipesList recipes = response.body();
                //ArrayAdapter<Recipe> arrayAdapter = new ArrayAdapter<>(RecipesActivity.this, android.R.layout.simple_list_item_1, recipes.recipes);
                RecipeArrayAdapter adapter = new RecipeArrayAdapter(RecipesActivity.this, recipes.recipes.toArray(new Recipe[recipes.recipes.size()]));
                listView.setAdapter(adapter);
                Log.d("TAG", new Gson().toJson(recipes));
            }

            @Override
            public void onFailure(@NonNull Call<RecipesList> call, Throwable t) {
                Toast.makeText(RecipesActivity.this, "Blad pobierania danych: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("test",listView.getAdapter().getItem(i).toString());

                Intent intent = new Intent(getBaseContext(), RecipeDetailsActivity.class);
                intent.putExtra("Recipe", (Recipe)listView.getAdapter().getItem(i));
                startActivity(intent);
            }
        });
    }

    public static class RecipesList{
        public List<Recipe> recipes;
    }
}


