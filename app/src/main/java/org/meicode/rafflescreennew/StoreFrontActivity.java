package org.meicode.rafflescreennew;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import org.meicode.rafflescreennew.utils.adapter.ShowItemAdapter;
import org.meicode.rafflescreennew.utils.model.ShoeCart;
import org.meicode.rafflescreennew.utils.model.ShoeItem;
import org.meicode.rafflescreennew.view.CartActivity;
import org.meicode.rafflescreennew.view.DetailedActivity;
import org.meicode.rafflescreennew.view.DetailedActivityShop;
import org.meicode.rafflescreennew.viewmodel.CartViewModel;

import java.util.ArrayList;
import java.util.List;

public class StoreFrontActivity extends AppCompatActivity implements ShowItemAdapter.ShoeClickedListeners {

    private RecyclerView recyclerView;
    private List<ShoeItem> storeFrontItemList;
    private ShowItemAdapter adapter;

    private CartViewModel viewModel;
    private List<ShoeCart> shoeCartList;
    private CoordinatorLayout coordinatorLayout;
    private ImageView cartImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_front);

        initializeVariables();
        setUpListForStoreFrontActivity();

        adapter.setShoeItemList(storeFrontItemList);
        recyclerView.setAdapter(adapter);

        // Set an OnClickListener for the back button
        ImageView backButton = findViewById(R.id.backButton);
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Toggle the layout flag and switch layout
//                switchLayoutAndFinish(MainActivity.class);
//            }
//        });
//    }
//
//    private void switchLayoutAndFinish(Class<?> destinationClass) {
//        Intent intent = new Intent(StoreFrontActivity.this, destinationClass);
//        startActivity(intent);
//        finish();
//    }
    }
    private void setUpListForStoreFrontActivity() {
        storeFrontItemList = new ArrayList<>();

        storeFrontItemList.add(new ShoeItem("Nike Revolution", "Nike", R.drawable.nike_revolution_road, 150.0));
        storeFrontItemList.add(new ShoeItem("Nike Flex Run 2021", "NIKE", R.drawable.flex_run_road_running, 120.0));
        storeFrontItemList.add(new ShoeItem("Court Zoom Vapor", "NIKE", R.drawable.nikecourt_zoom_vapor_cage, 80.0));
        storeFrontItemList.add(new ShoeItem("EQ21 Run COLD.RDY", "ADIDAS", R.drawable.adidas_eq_run, 95.0));
        storeFrontItemList.add(new ShoeItem("Adidas Ozelia", "ADIDAS", R.drawable.adidas_ozelia_shoes_grey, 220.0));
        storeFrontItemList.add(new ShoeItem("Adidas Questar", "ADIDAS", R.drawable.adidas_questar_shoes, 100.0));
        storeFrontItemList.add(new ShoeItem("Adidas Questar", "ADIDAS", R.drawable.adidas_questar_shoes, 65.0));
        storeFrontItemList.add(new ShoeItem("Adidas Ultraboost", "ADIDAS", R.drawable.adidas_ultraboost, 75.0));
    }

    private void initializeVariables() {
        recyclerView = findViewById(R.id.storeFrontRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new ShowItemAdapter((ShowItemAdapter.ShoeClickedListeners) this);
        recyclerView.setAdapter(adapter);
        cartImageView = findViewById(R.id.cartIv);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        shoeCartList = new ArrayList<>();
        viewModel = new ViewModelProvider(this).get(CartViewModel.class);
        storeFrontItemList = new ArrayList<>();
    }
    //added this whole block from shoe app
    @Override
    public void onCardClicked(ShoeItem shoe) {
        //main activity from shop app
        //switched
        Intent intent = new Intent(StoreFrontActivity.this, DetailedActivityShop.class);
        intent.putExtra("shoeItem", shoe);
        startActivity(intent);
    }

    @Override
    public void onAddToCartBtnClicked(ShoeItem shoeItem) {
        ShoeCart shoeCart = new ShoeCart();
        shoeCart.setShoeName(shoeItem.getShoeName());
        shoeCart.setShoeBrandName(shoeItem.getShoeBrandName());
        shoeCart.setShoePrice(shoeItem.getShoePrice());
        shoeCart.setShoeImage(shoeItem.getShoeImage());

        final int[] quantity = {1};
        final int[] id = new int[1];

        if (!storeFrontItemList.isEmpty()) {
            for (int i = 0; i < storeFrontItemList.size(); i++) {
                if (shoeCart.getShoeName().equals(storeFrontItemList.get(i).getShoeName())) {
                    quantity[0] = storeFrontItemList.get(i).getQuantity();
                    quantity[0]++;
                    id[0] = storeFrontItemList.get(i).getId();
                }
            }
        }

        Log.d("TAG", "onAddToCartBtnClicked: " + quantity[0]);

        if (quantity[0] == 1) {
            shoeCart.setQuantity(quantity[0]);
            shoeCart.setTotalItemPrice(quantity[0] * shoeCart.getShoePrice());
            viewModel.insertCartItem(shoeCart);
        } else {
            viewModel.updateQuantity(id[0], quantity[0]);
            viewModel.updatePrice(id[0], quantity[0] * shoeCart.getShoePrice());
        }

        makeSnackBar();
    }

    private void makeSnackBar() {
        Snackbar.make(coordinatorLayout, "Item Added To Cart", Snackbar.LENGTH_SHORT)
                .setAction("Go to Cart", view -> startActivity(new Intent(StoreFrontActivity.this, CartActivity.class))).show();
    }
}
