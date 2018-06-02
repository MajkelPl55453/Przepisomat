package pl.przepisomat.przepisomat.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import pl.przepisomat.przepisomat.R;
import pl.przepisomat.przepisomat.api.model.v2.Recipe;
import pl.przepisomat.przepisomat.api.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetailsActivity extends BaseActivity{

    private TextView detailsTitle,detailsTime,detailsDif,detailsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details_recipe);
        findWidgets();

        Long recipeID = getIntent().getLongExtra("RecipeID", 0);

        Call<ResponseRecipe> recipeCall = ApiService.getService().getRecipe(recipeID);

        recipeCall.enqueue(new Callback<ResponseRecipe>() {
            @Override
            public void onResponse(Call<ResponseRecipe> call, Response<ResponseRecipe> response) {

                ResponseRecipe detailedRecipe = response.body();
                detailsTitle.setText(detailedRecipe.recipe.getName());
                detailsCount.setText(detailedRecipe.recipe.getPortions());
                detailsDif.setText(detailedRecipe.recipe.getDifficulty());
                detailsTime.setText(detailedRecipe.recipe.getTime());
            }

            @Override
            public void onFailure(Call<ResponseRecipe> call, Throwable t) {
                Log.d("Details failure", t.getLocalizedMessage(), t);
            }
        });

        setDefaults();
    }

    private void findWidgets(){
        detailsTitle = findViewById(R.id.detailsTitle);
        detailsTime = findViewById(R.id.detailsTime);
        detailsDif = findViewById(R.id.detailsDif);
        detailsCount = findViewById(R.id.detailsCount);
    }

    public class ResponseRecipe{
        public Recipe recipe;
    }
}
