package com.manavjain.shopifyapi.network;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by YourFather on 27-12-2017.
 */

public class ApiClient {
    private static ApiInterface apiInterface;

    public static ApiInterface getApiInterface(){

        if (apiInterface == null){
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://shopicruit.myshopify.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new OkHttpClient.Builder().addInterceptor(new ShopifyInterceptor()).build())
                    .build();

            apiInterface = retrofit.create(ApiInterface.class);
        }

        return apiInterface;
    }

    private static class ShopifyInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            HttpUrl url = request.url().newBuilder()
                    .addQueryParameter("access_token","c32313df0d0ef512ca64d5b336a0d7c6")
                    .build();
            request = request.newBuilder().url(url).build();

            return chain.proceed(request);
        }
    }
}
