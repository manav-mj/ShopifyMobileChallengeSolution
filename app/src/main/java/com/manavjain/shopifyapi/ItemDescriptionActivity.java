package com.manavjain.shopifyapi;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.manavjain.shopifyapi.fragments.ItemDescriptionFragment;
import com.manavjain.shopifyapi.models.ShopifyProductList;

public class ItemDescriptionActivity extends AppCompatActivity {

    public final static String ITEM_PAYLOAD_KEY = "item_payload";
    private ShopifyProductList.ShopifyProduct product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        product = getProduct();
        setTitle(product.title);

        ItemDescriptionFragment fragment = new ItemDescriptionFragment();
        Bundle args = new Bundle();
        args.putSerializable(ITEM_PAYLOAD_KEY, getProduct());
        fragment.setArguments(args);
        getFragmentManager().beginTransaction()
            .add(R.id.desc_fragment_frame, fragment)
            .commit();
    }

    public ShopifyProductList.ShopifyProduct getProduct() {
        return (ShopifyProductList.ShopifyProduct) getIntent().getSerializableExtra(ITEM_PAYLOAD_KEY);
    }
}
