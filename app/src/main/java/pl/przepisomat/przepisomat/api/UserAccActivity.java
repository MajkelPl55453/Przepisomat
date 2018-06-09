package pl.przepisomat.przepisomat.api;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pl.przepisomat.przepisomat.BaseActivity;
import pl.przepisomat.przepisomat.LoginActivity;
import pl.przepisomat.przepisomat.R;

public class UserAccActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_acc);

        setDefaults();


        TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        Button btLogout = (Button) findViewById(R.id.btLogout);



        btLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                    Intent i = new Intent(UserAccActivity.this, LoginActivity.class);
                    startActivity(i);


            }
        });

    }

    private void Welcome (String username, TextView tvWelcome) {


        System.out.println( tvWelcome + username);

    }
}
