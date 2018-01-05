package com.manavjain.shopifyapi.network;

import com.manavjain.shopifyapi.models.ShopifyProductList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by YourFather on 27-12-2017.
 */

public interface ApiInterface {

    @GET("/admin/products.json")
    Call<ShopifyProductList> getProductListByPage(@Query("page") int page);

}
