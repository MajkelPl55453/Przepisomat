package pl.przepisomat.przepisomat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import pl.przepisomat.przepisomat.R;
import pl.przepisomat.przepisomat.Adapters.StepsAdapter;
import pl.przepisomat.przepisomat.api.model.FavoriteRecipe;
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
    private Long recipeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details_recipe);
        findWidgets();

        this.recipeID = getIntent().getLongExtra("RecipeID", 0);
        Call<ResponseRecipe> recipeCall = ApiService.getService().getRecipe(this.recipeID);

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

        Realm.init(this);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.compactRealm(realmConfiguration);
        Realm.setDefaultConfiguration(realmConfiguration);



        setDefaults(true);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipe_top, menu);

        Realm realm = Realm.getDefaultInstance();

        FavoriteRecipe favorite = realm
                .where(FavoriteRecipe.class)
                .equalTo("recipeId", this.recipeID)
                .findFirst();

        if (favorite == null) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_star_border_black_24dp));
        } else {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_star_black_24dp));
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_search:
                Intent intent = new Intent(getBaseContext(), SearchActivity.class);
                startActivity(intent);
            case R.id.action_add_favourite:
                if(addRemoveFavorite() == true)
                {
                    item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_star_black_24dp));
                }
                else
                {
                    item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_star_border_black_24dp));
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean addRemoveFavorite() {
        Realm realm = Realm.getDefaultInstance();

        FavoriteRecipe favorite = realm
                .where(FavoriteRecipe.class)
                .equalTo("recipeId", this.recipeID)
                .findFirst();

        if (favorite == null) {
            addToFavorites(realm);
            return true;
        } else {
            removeFromFavorites(realm, favorite);
            return false;
        }

    }
    private void addToFavorites(Realm realm) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                FavoriteRecipe favorite = realm.createObject(FavoriteRecipe.class);
                favorite.setRecipeId(RecipeDetailsActivity.this.recipeID);

                Toast.makeText(RecipeDetailsActivity.this, "Dodano do ulubionych", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeFromFavorites(Realm realm, final FavoriteRecipe favorite) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                favorite.deleteFromRealm();

                Toast.makeText(RecipeDetailsActivity.this, "Usunięto z ulubionych", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
