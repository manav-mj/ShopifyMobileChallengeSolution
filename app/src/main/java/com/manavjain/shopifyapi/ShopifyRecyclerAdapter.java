package com.manavjain.shopifyapi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.manavjain.shopifyapi.models.ShopifyProductList;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YourFather on 05-01-2018.
 */

public class ShopifyRecyclerAdapter extends RecyclerView.Adapter<ShopifyRecyclerAdapter.ShopifyViewHolder>{

    Context mContext;

    ShopifyProductList mProductList;

    OnShopifyItemClickListener mListener;

    ShopifyRecyclerAdapter(Context context, ShopifyProductList productList, OnShopifyItemClickListener listener){
        mContext = context;
        mProductList = productList;
        mListener = listener;
    }

    @Override
    public ShopifyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_product_list, parent,false);
        return new ShopifyViewHolder(view, (v, pos) -> mListener.onItemClick(v, pos));
    }

    @Override
    public void onBindViewHolder(ShopifyViewHolder holder, int position) {
        ShopifyProductList.ShopifyProduct product = mProductList.products.get(position);
        String imageUrl = product.image.src;
        Picasso.with(mContext).load(imageUrl).into(holder.itemImageView);

        holder.itemNameView.setText(product.title);
        holder.itemPriceView.setText(product.body_html);
    }

    @Override
    public int getItemCount() {
        return mProductList.products.size();
    }

    public class ShopifyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_image_view) ImageView itemImageView;
        @BindView(R.id.item_name_text_view) TextView itemNameView;
        @BindView(R.id.item_price_text_view) TextView itemPriceView;

        OnShopifyItemClickListener listener;

        int position;

        public ShopifyViewHolder(View itemView, OnShopifyItemClickListener listener) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            this.listener = listener;

            itemView.setOnClickListener(view -> this.listener.onItemClick(view, getAdapterPosition()));
        }


    }

    public interface OnShopifyItemClickListener{
        void onItemClick(View v, int pos);
    }
}
