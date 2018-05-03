package pl.przepisomat.przepisomat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


//Rozszerzamy o aktywność Base ponieważ ona posiada dolne menu.
public class CategoriesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        //Domyślne ustawienia dla dolnego paska menu
        setDefaults();
    }
}
