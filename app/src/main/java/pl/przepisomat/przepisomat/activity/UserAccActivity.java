package pl.przepisomat.przepisomat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import pl.przepisomat.przepisomat.activity.BaseActivity;
import pl.przepisomat.przepisomat.R;
import pl.przepisomat.przepisomat.api.model.FavoriteRecipe;

public class UserAccActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_acc);

        //wyswietlenie dolnego menu
        setDefaults(true);

        TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        Button btLogout = (Button) findViewById(R.id.btLogout);

        //przypisanie działania przyciskowi wylogowania
        btLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
            Intent i = new Intent(UserAccActivity.this, LoginActivity.class);
            startActivity(i);
            }
        });

        //Ustawienie konfiguracji dla paczki realm odpowiedzialnej za przechowywanie danych na urządzeniu
        Realm.init(this);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.compactRealm(realmConfiguration);
        Realm.setDefaultConfiguration(realmConfiguration);

        Realm realm = Realm.getDefaultInstance();

        //Wczytanie wszystkich ulubionych przepisów
        RealmResults<FavoriteRecipe> favorites = realm
                .where(FavoriteRecipe.class)
                .findAll();

        TextView tvFavourites = findViewById(R.id.tvFavourites);
        if(favorites.size() > 0) {
            tvFavourites.setText("Ilość ulubionych przepisów: " + favorites.size());
        }

    }
}
