package com.manavjain.shopifyapi.models;

import android.util.Log;
import android.widget.ImageView;

import com.manavjain.shopifyapi.network.ApiClient;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by YourFather on 27-12-2017.
 */

public class ShopifyProductList {
    private final String TAG = getClass().getName();

    public ArrayList<ShopifyProduct> products;

    public ShopifyProductList() {
        products = new ArrayList<>();
    }

    public class ShopifyProduct implements Serializable {
        public long id;
        public String title;
        public String body_html;
        public String vendor;
        public String product_type;
        public String handle;
        public String published_scope;
        public String tags;
        public ArrayList<ProductImage> images;
        public ProductImage image;
        public ArrayList<ProductVariant> variants;

        public class ProductImage implements Serializable {
            public int width;
            public int height;
            public String src;
        }

        public class ProductVariant implements Serializable {
            public long id;
            public String title;
            public String price;
            public String option1;
            public String option2;
            public String option3;
            public int grams;
            public String inventory_quantity;
            public long inventory_item_id;
            public transient int variant_position;
        }
    }

    // fetches product list from network
    public void getProductListFromNetwork(final OnListDownloadCompleteListener listener) {
        ApiClient.getApiInterface().getProductListByPage(1).enqueue(new Callback<ShopifyProductList>() {
            @Override
            public void onResponse(Call<ShopifyProductList> call, Response<ShopifyProductList> response) {
                if (response.isSuccessful()) {
                    ShopifyProductList.this.products = response.body().products;
                    listener.onDownloadComplete();
                } else {
                    Log.e(TAG, "onResponse failed : " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ShopifyProductList> call, Throwable t) {
                Log.e(TAG, "retrofit onFailure", t);
            }
        });
    }

    public interface OnListDownloadCompleteListener {
        void onDownloadComplete();
    }
}