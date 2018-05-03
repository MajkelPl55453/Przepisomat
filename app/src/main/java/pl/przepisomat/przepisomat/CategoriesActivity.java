package pl.przepisomat.przepisomat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.gson.Gson;

import pl.przepisomat.przepisomat.Adapters.CategoriesAdapter;
import pl.przepisomat.przepisomat.api.ApiService;
import pl.przepisomat.przepisomat.api.CategoryList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//Rozszerzamy o aktywność Base ponieważ ona posiada dolne menu.
public class CategoriesActivity extends BaseActivity {
    public ExpandableListView expandableListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        expandableListView = findViewById(R.id.categoryListView);

        //Domyślne ustawienia dla dolnego paska menu
        setDefaults();

        Call<CategoryList> categoryListCall = ApiService.getService().getCategories();
        categoryListCall.enqueue(new Callback<CategoryList>() {
            @Override
            public void onResponse(@NonNull Call<CategoryList> call, @NonNull Response<CategoryList> response) {
                CategoryList categoryList = response.body();
                CategoriesAdapter categoriesAdapter = new CategoriesAdapter(CategoriesActivity.this, categoryList);
                expandableListView.setAdapter(categoriesAdapter);
                Log.d("TAG", new Gson().toJson(categoryList));
            }

            @Override
            public void onFailure(@NonNull Call<CategoryList> call, Throwable t) {
                Toast.makeText(CategoriesActivity.this, "Blad pobierania danych: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
