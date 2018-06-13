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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import pl.przepisomat.przepisomat.R;
import pl.przepisomat.przepisomat.api.service.ApiClient;
import pl.przepisomat.przepisomat.api.service.ApiService;
import pl.przepisomat.przepisomat.api.model.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {


    View submitBtn;
    ApiClient mApiService;
    CheckedTextView tvRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //wyswietlenie dolnego menu
        setDefaults(true);

        //deklaracja pól formularzy
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button btLogin = (Button) findViewById(R.id.bLogin);
        TextView tvRegister = (TextView) findViewById(R.id.tvRegisterHere);

        //przypisanie działania przyciskowi logowania
        btLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //pobranie danych z formularza
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                //sprawdzenie czy pola formularza nie są puste
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                    //przekazanie danych pobranych z formularza do metody sendPost
                    sendPost(username, password);

                    //przekierowanie do nowej aktywności po kliknięciu w przycisk
                    Intent i = new Intent(LoginActivity.this, UserAccActivity.class);
                    startActivity(i);
                }

            }
        });
        //przekierowanie do aktywności rejestracji po kliknięciu w TextView
        tvRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }

        });
    }

    private void sendPost(String username, String password) {

        //wywołanie zapytania do API, do zmiennej loginRequest przypisywana jest uzyskana odpowiedź
        Call<LoginResponse> loginRequest = ApiService.getService().login(username, password);
        //dodanie do kolejki, czyli wysłanie zapytania do API
        loginRequest.enqueue(new Callback<LoginResponse>() {

            @Override
            //gdy połączenie się powiedzie zostanie zwrócona cała odpowiedź z API
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                //wybranie z odpowiedzi API części response.body
                LoginResponse loginResponse = response.body();
                //komunikat do konsoli
                Log.d("tag", new Gson().toJson(loginResponse));
                if(loginResponse != null) {
                    //komunikaty dla użytkownika
                    if (loginResponse.status == "succes") {
                        //jeżeli uda się zalogować zostanie wyświetlona odpowiednia wiadomość pobrana z API
                        Toast.makeText(getApplicationContext(), (String) loginResponse.message, Toast.LENGTH_SHORT).show();
                    } else {
                        //jeżeli nie uda się zalogować, zostanie wyświetlona odpowiednia wiadomość pobrana z API
                        Toast.makeText(getApplicationContext(), (String) loginResponse.message, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Błąd połączenia się z API", Toast.LENGTH_SHORT).show();
                }
            }


        @Override
        //gdy połączenie się nie powiedzie w konsoli zostanie wyświetlony odpowiedni komunikat
        public void onFailure (@NonNull Call < LoginResponse > call, Throwable t){
            Log.e("TAG", "Nie udalo sie wyslac do api");
        }
        });
    }

}
