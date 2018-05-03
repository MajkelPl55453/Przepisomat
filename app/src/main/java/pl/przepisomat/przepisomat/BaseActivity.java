package pl.przepisomat.przepisomat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {

    ImageView home_button;
    ImageView categories_button;
    ImageView favourites_button;
    ImageView shopping_cart_button;
    ImageView user_account_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        this.setDefaults();
    }

    protected void setDefaults()
    {
        this.home_button = findViewById(R.id.bmHomeButton);
        this.categories_button = findViewById(R.id.bmCategoriesButton);
        this.favourites_button = findViewById(R.id.bmFavouritesButton);
        this.shopping_cart_button = findViewById(R.id.bmShoppingCartButton);
        this.user_account_button = findViewById(R.id.bmUserAccountButton);

        this.home_button.setOnClickListener(this.homeButtonListener);
        this.categories_button.setOnClickListener(this.categoriesButtonListener);
        this.favourites_button.setOnClickListener(this.favouritesButtonListener);
        this.shopping_cart_button.setOnClickListener(this.shoppingCartButtonListener);
        this.user_account_button.setOnClickListener(this.userAccountButtonListener);
    }

    View.OnClickListener homeButtonListener = new View.OnClickListener(){
        public void onClick(View v)
        {
            Log.println(Log.DEBUG,"tag","dziala");
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
            Log.println(Log.DEBUG,"tag","dziala");
        }
    };

    View.OnClickListener shoppingCartButtonListener = new View.OnClickListener(){
        public void onClick(View v)
        {
            Log.println(Log.DEBUG,"tag","dziala");
        }
    };

    View.OnClickListener userAccountButtonListener = new View.OnClickListener(){
        public void onClick(View v)
        {
            Log.println(Log.DEBUG,"tag","dziala");
        }
    };
}
