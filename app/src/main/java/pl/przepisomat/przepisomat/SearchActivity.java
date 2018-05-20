package pl.przepisomat.przepisomat;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import pl.przepisomat.przepisomat.Adapters.CategoriesAdapter;
import pl.przepisomat.przepisomat.api.ApiService;
import pl.przepisomat.przepisomat.api.CategoryList;
import pl.przepisomat.przepisomat.api.RecipesNames;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends BaseActivity {

    private RecipesNames recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setDefaults();

        Call<RecipesNames> recipesNamesCall = ApiService.getService().getRecipesNames();
        recipesNamesCall.enqueue(new Callback<RecipesNames>() {
            @Override
            public void onResponse(@NonNull Call<RecipesNames> call, @NonNull Response<RecipesNames> response) {
                //SearchActivity.this.recipes = response.body();


                Log.d("TAG", "dziala");
            }

            @Override
            public void onFailure(@NonNull Call<RecipesNames> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Blad pobierania danych: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.commonmenus, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(SearchActivity.this, query, Toast.LENGTH_LONG);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(SearchActivity.this, newText, Toast.LENGTH_LONG);
                return false;
            }
        });
        searchView.setQueryHint("Wyszukaj przepisz...");
        searchView.setIconified(false);
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);
        if (searchPlate!=null) {
            searchPlate.setBackgroundColor(getResources().getColor(R.color.colorMenu));

            int searchTextId = searchPlate.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            TextView searchText = (TextView) searchPlate.findViewById(searchTextId);
            if (searchText!=null) {
                searchText.setTextColor(Color.BLACK);
                searchText.setHintTextColor(Color.BLACK);
            }
        }
        return super.onCreateOptionsMenu(menu);
    }
}
