package com.manavjain.shopifyapi;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.manavjain.shopifyapi.models.ShopifyProductList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private ShopifyProductList productList = new ShopifyProductList();

    @BindView(R.id.main_recycler_view) RecyclerView recyclerView;
    private ShopifyRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

        adapter = new ShopifyRecyclerAdapter(this, productList, this::onClick);

        recyclerView.setAdapter(adapter);

        productList.getProductListFromNetwork(() -> adapter.notifyDataSetChanged());
    }

    private void onClick(View v, int pos) {
        Intent intent = new Intent(this, ItemDescriptionActivity.class);
        intent.putExtra(ItemDescriptionActivity.ITEM_PAYLOAD_KEY, productList.products.get(pos));
        startActivity(intent);
    }

    @OnClick(R.id.search_view_main)
    void onSearchClick(View view){
        Intent intent = new Intent(this, SearchActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, "search");
        startActivity(intent, options.toBundle());
    }

}
