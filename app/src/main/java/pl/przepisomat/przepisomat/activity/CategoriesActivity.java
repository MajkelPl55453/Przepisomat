package pl.przepisomat.przepisomat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.gson.Gson;

import pl.przepisomat.przepisomat.R;
import pl.przepisomat.przepisomat.Adapters.CategoriesAdapter;
import pl.przepisomat.przepisomat.api.model.Category;
import pl.przepisomat.przepisomat.api.model.CategoryList;
import pl.przepisomat.przepisomat.api.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//Rozszerzamy o aktywność Base ponieważ ona posiada dolne menu.
public class CategoriesActivity extends BaseActivity {
    public static final String CATEGORY_EXTRA = "catId";
    CategoriesAdapter categoriesAdapter = null;

    public ExpandableListView expandableListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        expandableListView = findViewById(R.id.categoryListView);

        setDefaults(true);

        Call<CategoryList> categoryListCall = ApiService.getService().getCategories();
        categoryListCall.enqueue(new Callback<CategoryList>() {
            @Override
            public void onResponse(@NonNull Call<CategoryList> call, @NonNull Response<CategoryList> response) {
                CategoryList categoryList = response.body();
                CategoriesActivity.this.categoriesAdapter = new CategoriesAdapter(CategoriesActivity.this, categoryList);
                expandableListView.setAdapter(categoriesAdapter);
                Log.d("TAG", new Gson().toJson(categoryList));
            }

            @Override
            public void onFailure(@NonNull Call<CategoryList> call, Throwable t) {
                Toast.makeText(CategoriesActivity.this, "Blad pobierania danych: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Category clickedCat = (Category)categoriesAdapter.getChild(i, i1);
                Intent intent = new Intent(getBaseContext(), RecipesActivity.class);
                intent.putExtra(CATEGORY_EXTRA, clickedCat.id);
                startActivity(intent);
                return false;
            }
        });
    }
}
