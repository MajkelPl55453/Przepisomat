package pl.przepisomat.przepisomat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ProgressBar;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pl.przepisomat.przepisomat.R;
import pl.przepisomat.przepisomat.adapters.RecipeArrayAdapter;
import pl.przepisomat.przepisomat.api.model.Recipe;
import pl.przepisomat.przepisomat.api.service.ApiService;
import pl.przepisomat.przepisomat.components.ListView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static pl.przepisomat.przepisomat.activity.CategoriesActivity.CATEGORY_EXTRA;

public class RecipesActivity extends BaseActivity implements ListView.ListViewListener {

    private ListView listView;
    private int offset = 1;
    private Long catId;
    private RecipesList recipesList = new RecipesList();
    private RecipeArrayAdapter adapter;
    private ProgressBar loadingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        loadingSpinner = findViewById(R.id.pBar);
        Long catId = getIntent().getLongExtra(CATEGORY_EXTRA, 0);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        this.catId = catId;
        setupList();
        setDefaults();
    }

    private void setupList(){
        listView = findViewById(R.id.recipiesListView);
        listView.setListViewListener(this);
        Call<RecipesList> categoryListCall = ApiService.getService().getRecipes(catId,10,offset);
        updateList(categoryListCall);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getBaseContext(), RecipeDetailsActivity.class);
                intent.putExtra("RecipeID", ((Recipe)listView.getAdapter().getItem(i)).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onEndOfList() {
        offset += 10;

        Call<RecipesList> recipesListCall = ApiService.getService().getRecipes(catId,10,offset);
        updateList(recipesListCall);
    }

    private void updateList(Call<RecipesList> recipesListCall){
        loadingSpinner.setVisibility(View.VISIBLE);
        recipesListCall.enqueue(new Callback<RecipesList>() {
            @Override
            public void onResponse(@NonNull Call<RecipesList> call, @NonNull Response<RecipesList> response) {
                RecipesList recipes = response.body();
                if(recipes != null && !recipes.recipes.isEmpty()){
                    if(adapter == null) {
                        RecipesActivity.this.recipesList.recipes.addAll(recipes.recipes);
                        adapter = new RecipeArrayAdapter(RecipesActivity.this, RecipesActivity.this.recipesList.recipes.toArray(new Recipe[RecipesActivity.this.recipesList.recipes.size()]));
                        listView.setAdapter(adapter);
                    } else {
                        adapter.addAll(recipes.recipes);
                    }
                    adapter.notifyDataSetChanged();
                    listView.invalidate();
                    loadingSpinner.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RecipesList> call, Throwable t) {
                Toast.makeText(RecipesActivity.this, "Blad pobierania danych: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class RecipesList{
        public List<Recipe> recipes = new ArrayList<>();
    }
}


