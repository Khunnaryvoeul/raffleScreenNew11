package org.meicode.rafflescreennew.utils.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.meicode.rafflescreennew.R;
import org.meicode.rafflescreennew.utils.model.ShoeItem;

import java.util.List;

public class ShowItemAdapter extends RecyclerView.Adapter<ShowItemAdapter.ShoeItemViewHolder> {


    private List<ShoeItem> shoeItemList;
    private ShoeClickedListeners shoeClickedListeners;

    public ShowItemAdapter(ShoeClickedListeners shoeClickedListeners) {
        this.shoeClickedListeners = shoeClickedListeners;
    }

    public void setShoeItemList(List<ShoeItem> shoeItemList) {
        this.shoeItemList = shoeItemList;
    }

    @NonNull
    @Override
    public ShowItemAdapter.ShoeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_shoe, parent, false);
        return new ShoeItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowItemAdapter.ShoeItemViewHolder holder, int position) {
        ShoeItem shoeItem = shoeItemList.get(position);
        holder.shoeNameTv.setText(shoeItem.getShoeName());
        holder.shoeBrandNameTv.setText(shoeItem.getShoeBrandName());
        holder.shoePriceTv.setText(String.valueOf(shoeItem.getShoePrice()));
        holder.shoeImageView.setImageResource(shoeItem.getShoeImage());

        // Display release date (assuming releaseDate is a Date object)
        if (shoeItem.getReleaseDate() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            String formattedReleaseDate = dateFormat.format(shoeItem.getReleaseDate());
            holder.releaseDateTv.setText(formattedReleaseDate);
        } else {
            holder.releaseDateTv.setText("Release Date N/A");
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shoeClickedListeners.onCardClicked(shoeItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(shoeItemList == null){
            return 0;
        }else {
            return shoeItemList.size();
        }


    }

    public class ShoeItemViewHolder extends RecyclerView.ViewHolder{
        public TextView releaseDateTv;
        private ImageView shoeImageView , addToCartBtn;
        private TextView shoeNameTv, shoeBrandNameTv, shoePriceTv;
        private CardView cardView;
        public ShoeItemViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.eachShoeCardView);
            addToCartBtn = itemView.findViewById(R.id.eachShoeAddToCartBtn);
            shoeNameTv = itemView.findViewById(R.id.eachShoeName);
            shoeImageView = itemView.findViewById(R.id.eachShoeIv);
            shoeBrandNameTv = itemView.findViewById(R.id.eachShoeBrandNameTv);
            shoePriceTv = itemView.findViewById(R.id.eachShoePriceTv);
            releaseDateTv = itemView.findViewById(R.id.eachShoeReleaseDate);
        }
    }
    public interface ShoeClickedListeners{
        void onCardClicked(ShoeItem shoe);

        void onAddToCartBtnClicked(ShoeItem shoeItem);
    }
}
