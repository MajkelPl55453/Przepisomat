package pl.przepisomat.przepisomat.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Map;

import pl.przepisomat.przepisomat.R;
import pl.przepisomat.przepisomat.adapters.StepsAdapter;
import pl.przepisomat.przepisomat.api.model.Product;
import pl.przepisomat.przepisomat.api.model.Steps;
import pl.przepisomat.przepisomat.api.model.v2.Recipe;
import pl.przepisomat.przepisomat.api.service.ApiService;
import pl.przepisomat.przepisomat.components.BulletTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetailsActivity extends BaseActivity{

    private TextView detailsTitle,detailsTime,detailsDif,detailsCount;
    private BulletTextView bulletTextView;
    private ListView stepsList;

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
                StringBuilder sb = new StringBuilder();
                for(Map.Entry<String, Product> products: detailedRecipe.recipe.getProducts().entrySet()){
                    sb.append("--").append(products.getValue().getNazwa()).append(" ").append(products.getValue().getWartosc());
                }
                bulletTextView.injectBulletPoints(sb.toString());
                StepsAdapter stepsAdapter = new StepsAdapter(RecipeDetailsActivity.this, detailedRecipe.recipe.getSteps().toArray(new Steps[detailedRecipe.recipe.getSteps().size()-1]));

                stepsList.setAdapter(stepsAdapter);
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
        bulletTextView = findViewById(R.id.bulletSteps);
        stepsList = findViewById(R.id.listSteps);
    }

    public class ResponseRecipe{
        public Recipe recipe;
    }
}
