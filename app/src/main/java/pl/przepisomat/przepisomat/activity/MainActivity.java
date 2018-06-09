package pl.przepisomat.przepisomat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import pl.przepisomat.przepisomat.R;
import pl.przepisomat.przepisomat.activity.RecipesActivity.RecipesList;
import pl.przepisomat.przepisomat.Adapters.MainGrid;
import pl.przepisomat.przepisomat.api.model.Recipe;
import pl.przepisomat.przepisomat.api.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);
        gridView = findViewById(R.id.grid);

        Call<RecipesList> topRecipesCall = ApiService.getService().getMostPopularRecipes(10,0);
        topRecipesCall.enqueue(new Callback<RecipesList>() {
            @Override
            public void onResponse(@NonNull Call<RecipesList> call, @NonNull Response<RecipesList> response) {
                RecipesList recipes = response.body();
                MainGrid adapter = new MainGrid(MainActivity.this, recipes.recipes.toArray(new Recipe[recipes.recipes.size()]));
                gridView.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<RecipesList> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Blad pobierania danych: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getBaseContext(), RecipeDetailsActivity.class);
                intent.putExtra("RecipeID", ((Recipe)gridView.getAdapter().getItem(i)).getId());
                startActivity(intent);
            }
        });

        setDefaults(true);
    }
}
