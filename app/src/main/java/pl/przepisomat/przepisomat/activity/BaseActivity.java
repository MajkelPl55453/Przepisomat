package pl.przepisomat.przepisomat.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import pl.przepisomat.przepisomat.R;


public class BaseActivity extends AppCompatActivity {

    ImageView home_button;
    ImageView categories_button;
    ImageView favourites_button;
    ImageView user_account_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        this.setDefaults(false);
    }

    protected void setDefaults(boolean setHomeIcon)
    {
        this.home_button = findViewById(R.id.bmHomeButton);
        this.categories_button = findViewById(R.id.bmCategoriesButton);
        this.favourites_button = findViewById(R.id.bmFavouritesButton);
        this.user_account_button = findViewById(R.id.bmUserAccountButton);

        this.home_button.setOnClickListener(this.homeButtonListener);
        this.categories_button.setOnClickListener(this.categoriesButtonListener);
        this.favourites_button.setOnClickListener(this.favouritesButtonListener);
        this.user_account_button.setOnClickListener(this.userAccountButtonListener);

        getSupportActionBar().setDisplayShowHomeEnabled(setHomeIcon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(setHomeIcon);


    }



    View.OnClickListener homeButtonListener = new View.OnClickListener(){
        public void onClick(View v)
        {
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener categoriesButtonListener = new View.OnClickListener(){
        public void onClick(View v)
        {
            Intent intent = new Intent(getBaseContext(), CategoriesActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener favouritesButtonListener = new View.OnClickListener(){
        public void onClick(View v)
        {
            Intent intent = new Intent(getBaseContext(), FavouritesActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener userAccountButtonListener = new View.OnClickListener(){
        public void onClick(View v)
        {

            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
            this.finish();

        return super.onOptionsItemSelected(item);
    }
}