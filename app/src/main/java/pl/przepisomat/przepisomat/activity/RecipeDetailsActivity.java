package pl.przepisomat.przepisomat.activity;

import android.os.Bundle;
import android.widget.TextView;

import pl.przepisomat.przepisomat.R;
import pl.przepisomat.przepisomat.api.model.Recipe;

public class RecipeDetailsActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details_recipe);

        Recipe recipe = (Recipe) getIntent().getSerializableExtra("Recipe");

        TextView detailsTitle = findViewById(R.id.detailsTitle);
        TextView detailsTime = findViewById(R.id.detailsTime);
        TextView detailsDif = findViewById(R.id.detailsDif);
        TextView detailsCount = findViewById(R.id.detailsCount);

        detailsTitle.setText(recipe.getNazwa());
        detailsCount.setText(recipe.getIlosc_porcji());
        detailsDif.setText(recipe.getTrudnosc());
        detailsTime.setText(recipe.getCzas_przygotowania());
    }
}
