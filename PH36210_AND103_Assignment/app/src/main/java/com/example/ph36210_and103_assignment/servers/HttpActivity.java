package com.example.ph36210_and103_assignment.servers;

import static com.example.ph36210_and103_assignment.servers.Api_Servser.BASE_URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpActivity {
    private  Api_Servser apiServices;

    public HttpActivity(){
        apiServices= new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Api_Servser.class);

    }
    public Api_Servser callAPI(){
        return apiServices;
    }
}
