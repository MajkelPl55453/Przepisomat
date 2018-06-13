package pl.przepisomat.przepisomat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pl.przepisomat.przepisomat.activity.BaseActivity;
import pl.przepisomat.przepisomat.R;

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

    }
    //wyswietlenie powitania
    private void Welcome (String username, TextView tvWelcome) {


        System.out.println( tvWelcome + username);

    }
}
