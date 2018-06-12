package pl.przepisomat.przepisomat.activity;

import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import pl.przepisomat.przepisomat.Adapters.RecipeArrayAdapter;
import pl.przepisomat.przepisomat.R;
import pl.przepisomat.przepisomat.api.model.FavoriteRecipe;
import pl.przepisomat.przepisomat.api.model.Recipe;
import pl.przepisomat.przepisomat.api.service.ApiService;
import pl.przepisomat.przepisomat.components.ListView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static pl.przepisomat.przepisomat.activity.CategoriesActivity.CATEGORY_EXTRA;

public class FavouritesActivity extends BaseActivity implements ListView.ListViewListener{

    private ListView listView;
    private int offset = 1;
    private Long catId;
    private RecipesActivity.RecipesList recipesList = new RecipesActivity.RecipesList();
    private RecipeArrayAdapter adapter;
    private ProgressBar loadingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        loadingSpinner = findViewById(R.id.pBar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setupList();
        setDefaults(true);
    }

    private String implode(String separator, String... data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length - 1; i++) {
            //data.length - 1 => to not add separator at the end
            if (!data[i].matches(" *")) {//empty string are ""; " "; "  "; and so on
                sb.append(data[i]);
                sb.append(separator);
            }
        }
        sb.append(data[data.length - 1].trim());
        return sb.toString();
    }

    private void setupList(){
        listView = findViewById(R.id.recipiesListView);
        listView.setListViewListener(this);
        String ids = "";
        String[] ids_arr = {};
        Realm realm = Realm.getDefaultInstance();

        RealmResults<FavoriteRecipe> favorites = realm
                .where(FavoriteRecipe.class)
                .findAll();

        if (favorites.size() > 0) {
            Toast.makeText(this, "Pobrano ulubione", Toast.LENGTH_SHORT).show();
            int x = 0;
            for (FavoriteRecipe favorite : favorites) {
                ids_arr[x] = favorite.getRecipeId().toString();
                x++;
            }
            ids = this.implode(",", ids_arr);
        } else {
            Toast.makeText(this, "Brak ulubionych", Toast.LENGTH_SHORT).show();
        }


        Call<RecipesActivity.RecipesList> categoryListCall = ApiService.getService().getRecipesListByIds(10, offset, ids);
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

        Call<RecipesActivity.RecipesList> recipesListCall = ApiService.getService().getRecipes(catId,10,offset);
        updateList(recipesListCall);
    }

    private void updateList(Call<RecipesActivity.RecipesList> recipesListCall){
        loadingSpinner.setVisibility(View.VISIBLE);
        recipesListCall.enqueue(new Callback<RecipesActivity.RecipesList>() {
            @Override
            public void onResponse(@NonNull Call<RecipesActivity.RecipesList> call, @NonNull Response<RecipesActivity.RecipesList> response) {
                RecipesActivity.RecipesList recipes = response.body();
                if(recipes != null && !recipes.recipes.isEmpty()){
                    if(adapter == null) {
                        FavouritesActivity.this.recipesList.recipes.addAll(recipes.recipes);
                        adapter = new RecipeArrayAdapter(FavouritesActivity.this, FavouritesActivity.this.recipesList.recipes.toArray(new Recipe[FavouritesActivity.this.recipesList.recipes.size()]));
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
            public void onFailure(@NonNull Call<RecipesActivity.RecipesList> call, Throwable t) {
                Toast.makeText(FavouritesActivity.this, "Blad pobierania danych: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class RecipesList{
        public List<Recipe> recipes = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.commonmenus, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getBaseContext(), SearchActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
