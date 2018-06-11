package pl.przepisomat.przepisomat.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pl.przepisomat.przepisomat.R;
import pl.przepisomat.przepisomat.api.service.ApiService;
import pl.przepisomat.przepisomat.api.model.RecipeName;
import pl.przepisomat.przepisomat.api.model.RecipesNames;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends BaseActivity {

    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arrayList;
    private ArrayList<String> idArrayList;
    private ListView listView;
    private SearchView searchView;
    private boolean isDataLoaded = false;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setDefaults(false);

        this.arrayList = new ArrayList<String>();
        this.idArrayList = new ArrayList<String>();

        this.progressBar = findViewById(R.id.progressbar);
        this.progressBar.setVisibility(View.VISIBLE);

        Call<RecipesNames> recipesNamesCall = ApiService.getService().getRecipesNames();
        recipesNamesCall.enqueue(new Callback<RecipesNames>() {
            @Override
            public void onResponse(@NonNull Call<RecipesNames> call, @NonNull Response<RecipesNames> response) {
                RecipesNames recipesNames = response.body();
                SearchActivity.this.isDataLoaded = true;
                invalidateOptionsMenu();
                SearchActivity.this.progressBar.setVisibility(View.INVISIBLE);
                for(RecipeName recipeName : recipesNames.names )
                {
                    SearchActivity.this.arrayList.add(recipeName.name);
                    SearchActivity.this.idArrayList.add(String.valueOf(recipeName.id));
                }
                SearchActivity.this.arrayAdapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, SearchActivity.this.arrayList);
                SearchActivity.this.listView = findViewById(R.id.searchListView);
                SearchActivity.this.listView.setAdapter(SearchActivity.this.arrayAdapter);
                SearchActivity.this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getBaseContext(), RecipeDetailsActivity.class);
                    intent.putExtra("RecipeID", Long.parseLong(SearchActivity.this.idArrayList.get(i)));
                    startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(@NonNull Call<RecipesNames> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Blad pobierania danych: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(SearchActivity.this.isDataLoaded == true) {
            getMenuInflater().inflate(R.menu.search_view_menu, menu);
            MenuItem searchItem = menu.findItem(R.id.action_search);
            searchItem.expandActionView();
            this.searchView = (SearchView) searchItem.getActionView();
            this.searchView.setEnabled(false);
            this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if (SearchActivity.this.arrayAdapter != null) {
                        SearchActivity.this.arrayAdapter.getFilter().filter(query);
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (SearchActivity.this.arrayAdapter != null) {
                        SearchActivity.this.arrayAdapter.getFilter().filter(newText);
                    }
                    return false;
                }
            });
            this.searchView.setQueryHint("Wyszukaj przepisz...");
            this.searchView.setIconified(false);
            int searchPlateId = this.searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
            View searchPlate = this.searchView.findViewById(searchPlateId);
            if (searchPlate != null) {
                searchPlate.setBackgroundColor(getResources().getColor(R.color.colorMenu));
                int searchTextId = searchPlate.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
                TextView searchText = (TextView) searchPlate.findViewById(searchTextId);
                if (searchText != null) {
                    searchText.setTextColor(Color.BLACK);
                    searchText.setHintTextColor(Color.BLACK);
                }
            }
        }
        return super.onCreateOptionsMenu(menu);
    }
}
