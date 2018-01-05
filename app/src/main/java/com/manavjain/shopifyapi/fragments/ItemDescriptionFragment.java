package com.manavjain.shopifyapi.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.manavjain.shopifyapi.ItemDescriptionActivity;
import com.manavjain.shopifyapi.R;
import com.manavjain.shopifyapi.models.ShopifyProductList;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ItemDescriptionFragment extends Fragment {

    private final String TAG = getClass().getName();
    private ShopifyProductList.ShopifyProduct product;

    @BindView(R.id.item_image_fragment)
    ImageView productImageView;
    @BindView(R.id.item_price_fragment)
    TextView priceTextView;
    @BindView(R.id.color_text_view_fragment)
    TextView colorTextView;
    @BindView(R.id.item_name_fragment)
    TextView nameTextView;
    @BindView(R.id.weight_text_view_fragment)
    TextView weightTextView;
    @BindView(R.id.inventory_id_text_view_fragment)
    TextView inventoryIDTextView;
    @BindView(R.id.quantity_text_view_fragment)
    TextView quantityTextView;
    @BindView(R.id.color_option_layout_fragment)
    LinearLayout variantLayout;

    private Unbinder unbinder;

    private int variantNumber;
    private int variant;
    private int prevSelectedVariant;


    public ItemDescriptionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            product = (ShopifyProductList.ShopifyProduct) getArguments().getSerializable(ItemDescriptionActivity.ITEM_PAYLOAD_KEY);
        } else
            Log.e(TAG, "argument not passed");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_description, container, false);
        ((TextView) view.findViewById(R.id.item_id_fragment)).setText(Long.toString(product.id));
        unbinder = ButterKnife.bind(this, view);

        Picasso.with(getContext()).load(product.image.src).into(productImageView);
        nameTextView.setText(product.title);


        for (ShopifyProductList.ShopifyProduct.ProductVariant v :
                product.variants) {
            Button variantChild = new Button(getContext());
            variantChild.setText(v.title);
            variantChild.setOnClickListener(view1 -> {
                for (int i = 0; i < product.variants.size(); i++) {
                    ShopifyProductList.ShopifyProduct.ProductVariant variant = product.variants.get(i);
                    if (variant.title.equals(((Button) view1).getText().toString()))
                        setVariant(i);
                }
            });
            variantLayout.addView(variantChild);
        }

        setVariant(variantNumber);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public void setVariant(int variantNumber) {
        ShopifyProductList.ShopifyProduct.ProductVariant variant = product.variants.get(variantNumber);
        priceTextView.setText(variant.price);
        weightTextView.setText((variant.grams / 1000F) + " kg");
        colorTextView.setText(variant.title);
        inventoryIDTextView.setText(variant.inventory_item_id + "");
        quantityTextView.setText(variant.inventory_quantity);
        ((Button) variantLayout.getChildAt(variantNumber)).setEnabled(false);
        if (prevSelectedVariant != variantNumber)
            ((Button) variantLayout.getChildAt(prevSelectedVariant)).setEnabled(true);
        prevSelectedVariant = variantNumber;
    }
}
