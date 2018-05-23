package pl.przepisomat.przepisomat;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;

import pl.przepisomat.przepisomat.api.ApiClient;
import pl.przepisomat.przepisomat.api.ApiService;
import pl.przepisomat.przepisomat.api.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {


    View submitBtn;
    ApiClient mApiService;
    CheckedTextView mResponseTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setDefaults();

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        Button btLogin = (Button) findViewById(R.id.bLogin);
        TextView mResponseTv = (TextView) findViewById(R.id.tvRegisterHere);

        btLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                    sendPost(username, password);
                }
            }
        });
    }

    private void sendPost(String username, String password) {

        Call<LoginResponse> loginRequest = ApiService.getService().login(username, password);
        loginRequest.enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                Log.d("tag", response.body().toString());
            }


        @Override
        public void onFailure (@NonNull Call < LoginResponse > call, Throwable t){
            Log.e("TAG", "Nie udalo sie wyslac do api");
        }
        });
    }




    private void showResponse(String response) {


        if(mResponseTv.getVisibility() == View.GONE) {
            mResponseTv.setVisibility(View.VISIBLE);
        }
        
        mResponseTv.setText(response);
    }
}
