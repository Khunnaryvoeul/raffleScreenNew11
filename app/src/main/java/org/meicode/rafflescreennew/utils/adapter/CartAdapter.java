package org.meicode.rafflescreennew.utils.adapter;  // or the common package name

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.meicode.rafflescreennew.R;  // or the correct path to your resources
import org.meicode.rafflescreennew.utils.model.ShoeCart;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private CartClickedListeners cartClickedListeners;
    private List<ShoeCart> shoeCartList;

    public CartAdapter(CartClickedListeners cartClickedListeners) {
        this.cartClickedListeners = cartClickedListeners;
    }

    public void setShoeCartList(List<ShoeCart> shoeCartList) {
        this.shoeCartList = shoeCartList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        ShoeCart shoeCart = shoeCartList.get(position);
        holder.bind(shoeCart);
    }

    @Override
    public int getItemCount() {
        return shoeCartList == null ? 0 : shoeCartList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        private TextView shoeNameTv, shoeBrandNameTv, shoePriceTv, shoeQuantity;
        private ImageView deleteShoeBtn;
        private ImageView shoeImageView;
        private ImageButton addQuantityBtn, minusQuantityBtn;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            shoeNameTv = itemView.findViewById(R.id.eachCartItemName);
            shoeBrandNameTv = itemView.findViewById(R.id.eachCartItemBrandNameTv);
            shoePriceTv = itemView.findViewById(R.id.eachCartItemPriceTv);
            deleteShoeBtn = itemView.findViewById(R.id.eachCartItemDeleteBtn);
            shoeImageView = itemView.findViewById(R.id.eachCartItemIV);
            shoeQuantity = itemView.findViewById(R.id.eachCartItemQuantityTV);
            addQuantityBtn = itemView.findViewById(R.id.eachCartItemAddQuantityBtn);
            minusQuantityBtn = itemView.findViewById(R.id.eachCartItemMinusQuantityBtn);

            setupClickListeners();
        }

        private void setupClickListeners() {
            deleteShoeBtn.setOnClickListener(view -> cartClickedListeners.onDeleteClicked(shoeCartList.get(getAdapterPosition())));
            addQuantityBtn.setOnClickListener(view -> cartClickedListeners.onPlusClicked(shoeCartList.get(getAdapterPosition())));
            minusQuantityBtn.setOnClickListener(view -> cartClickedListeners.onMinusClicked(shoeCartList.get(getAdapterPosition())));
        }

        public void bind(ShoeCart shoeCart) {
            shoeImageView.setImageResource(shoeCart.getShoeImage());
            shoeNameTv.setText(shoeCart.getShoeName());
            shoeBrandNameTv.setText(shoeCart.getShoeBrandName());
            shoeQuantity.setText(String.valueOf(shoeCart.getQuantity()));
            shoePriceTv.setText(String.valueOf(shoeCart.getTotalItemPrice()));
        }
    }

    public interface CartClickedListeners {
        void onDeleteClicked(ShoeCart shoeCart);

        void onPlusClicked(ShoeCart shoeCart);

        void onMinusClicked(ShoeCart shoeCart);
    }
}
