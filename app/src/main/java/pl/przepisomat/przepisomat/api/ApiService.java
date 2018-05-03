package pl.przepisomat.przepisomat.api;

import retrofit2.Retrofit;

/**
 * Created by Majkel on 2018-05-03.
 */

public class ApiService {
    public static ApiClient getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.laravel.lhc/api/")
                .build();

        return retrofit.create(ApiClient.class);
    }
}
