package pl.przepisomat.przepisomat.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;

import com.google.gson.Gson;

import pl.przepisomat.przepisomat.R;
import pl.przepisomat.przepisomat.api.service.ApiClient;
import pl.przepisomat.przepisomat.api.service.ApiService;
import pl.przepisomat.przepisomat.api.model.RegisterResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity {

        View bRegister;
        ApiClient mApiService;
        CheckedTextView mResponseTv;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);

            //wyswietlenie dolnego menu
            setDefaults(true);

            final EditText etEmail = (EditText) findViewById(R.id.etEmail);
            final EditText etUsername = (EditText) findViewById(R.id.etUsername);
            final EditText etPassword = (EditText) findViewById(R.id.etPassword);
            Button bRegister = (Button) findViewById(R.id.bRegister);

            //pobranie danych z formularza oraz przypisanie działania przyciskowi
            bRegister.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    String email = etEmail.getText().toString().trim();
                    String username = etUsername.getText().toString().trim();
                    String password = etPassword.getText().toString().trim();
                    if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                        sendPost(email, username, password);

                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(i);
                    }
                }
            });
        }

    //przekazanie pobranych danych z formularza do API
    private void sendPost(String email, String username, String password) {

        Call<RegisterResponse> registerRequest = ApiService.getService().register(email, username, password);
        registerRequest.enqueue(new Callback<RegisterResponse>() {

            @Override
            public void onResponse(@NonNull Call<RegisterResponse> call, @NonNull Response<RegisterResponse> response) {
                RegisterResponse registerResponse = response.body();
                Log.d("tag", new Gson().toJson(registerResponse));
            }


            @Override
            public void onFailure (@NonNull Call < RegisterResponse > call, Throwable t){
                Log.e("TAG", "Nie udalo sie wyslac do api");
            }
        });
    }

}


