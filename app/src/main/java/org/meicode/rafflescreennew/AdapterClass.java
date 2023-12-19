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

import org.meicode.rafflescreennew.utils.adapter.ShowItemAdapter;
import org.meicode.rafflescreennew.utils.model.ShoeCart;
import org.meicode.rafflescreennew.utils.model.ShoeItem;
import org.meicode.rafflescreennew.view.CartActivity;
import org.meicode.rafflescreennew.view.DetailedActivity;
import org.meicode.rafflescreennew.view.DetailedActivityShop;
import org.meicode.rafflescreennew.viewmodel.CartViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdapterClass extends AppCompatActivity implements ShowItemAdapter.ShoeClickedListeners {

    private RecyclerView recyclerView;
    private List<ShoeItem> shoeItemList;
    private ShowItemAdapter adapter;
    private CartViewModel viewModel;
    private List<ShoeCart> shoeCartList;
    private CoordinatorLayout coordinatorLayout;
    private ImageView cartImageView;
    private boolean isMainLayout = true; // Flag to track the current layout


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        // Set the initial layout
        if (isMainLayout) {
            setContentView(R.layout.activity_main);
        } else {
            setContentView(R.layout.activity_store_front);
        }

        initializeVariables();
        // setUpListForStoreFrontActivity();
        setUpListForMainActivity();

        adapter.setShoeItemList(shoeItemList);
        recyclerView.setAdapter(adapter);

        //might not be needed
        cartImageView.setOnClickListener(view -> startActivity(new Intent(AdapterClass.this, CartActivity.class)));

        // Set up the click listener for the back button
        ImageView backButton = findViewById(R.id.backButton);
        // Set an OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toggle the layout flag and switch layout
                isMainLayout = !isMainLayout;
                switchLayoutAndFinish(isMainLayout ? MainActivity.class : StoreFrontActivity.class);
            }
        });
    }
    // Method to handle the click event and navigate to a different screen
    private void switchLayoutAndFinish(Class<?> destinationClass) {
        Intent intent = new Intent(AdapterClass.this, destinationClass);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        viewModel.getAllCartItems().observe(this, shoeCarts -> shoeCartList.addAll(shoeCarts));
    }

    private void setUpListForMainActivity() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy", Locale.US);

        Date releaseDate1 = parseDate("19/12/23", dateFormat);
        Date releaseDate2 = parseDate("16/12/23", dateFormat);
        Date releaseDate3 = parseDate("17/12/23", dateFormat);
        Date releaseDate4 = parseDate("16/12/23", dateFormat);
        Date releaseDate5 = parseDate("16/12/23", dateFormat);

        // Add raffle end dates here
        Date raffleEndDate1 = parseDate("19/12/23", dateFormat);
        Date raffleEndDate2 = parseDate("16/12/23", dateFormat);
        Date raffleEndDate3 = parseDate("16/12/23", dateFormat);
        Date raffleEndDate4 = parseDate("16/12/23", dateFormat);
        Date raffleEndDate5 = parseDate("31/01/24", dateFormat);

        shoeItemList.add(new ShoeItem("New Balance 2002r Protection Pack Purple", "Nike", R.drawable.__ccb10249_2702_4b4f_87d2_de9809363657_540x, 130, releaseDate1, raffleEndDate1, "10:45:00", "16:08:00"));
        shoeItemList.add(new ShoeItem("Travis Scott 1 Fragment", "Jordan", R.drawable.ipad_travis_scott_x_fragment_x_air_jordan_1_high_og_military_blue, 160, releaseDate2, raffleEndDate2, "10:45:00", "10:49:00"));
        shoeItemList.add(new ShoeItem("Travis Scott Air Jordan 1 Mocha", "Jordan", R.drawable.travis_scott_x_air_jordan_1_retro_high_og, 160, releaseDate3, raffleEndDate3, "13:45:00", "15:45:00"));
        shoeItemList.add(new ShoeItem("Nike Air Force 1 Blue Void", "Nike", R.drawable._2nikmrfrc107lv8fmns_sail_blue_void, 90, releaseDate4, raffleEndDate4, "11:45:00", "11:49:00"));
        shoeItemList.add(new ShoeItem("Nike Low Blazers Black and White Sail", "Nike", R.drawable._1nikmblzrlw77vntmns_black_white_sail, 90, releaseDate5, raffleEndDate5, "10:45:00", "10:49:00"));

        Log.d("MainActivity", "Release Date 1: " + releaseDate1);
        Log.d("MainActivity", "Release Date 2: " + releaseDate2);
        Log.d("MainActivity", "Release Date 3: " + releaseDate3);
        Log.d("MainActivity", "Release Date 4: " + releaseDate4);
        Log.d("MainActivity", "Release Date 5: " + releaseDate5);

        Log.d("MainActivity", "Raffle End Date 1: " + raffleEndDate1);
        Log.d("MainActivity", "Raffle End Date 2: " + raffleEndDate2);
        Log.d("MainActivity", "Raffle End Date 3: " + raffleEndDate3);
        Log.d("MainActivity", "Raffle End Date 4: " + raffleEndDate4);
        Log.d("MainActivity", "Raffle End Date 5: " + raffleEndDate5);
    }



    private Date parseDate(String dateString, SimpleDateFormat dateFormat) {
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Return null in case of parsing failure
        }
    }

    private void initializeVariables() {

        cartImageView = findViewById(R.id.cartIv);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        shoeCartList = new ArrayList<>();
        viewModel = new ViewModelProvider(this).get(CartViewModel.class);
        shoeItemList = new ArrayList<>();
        recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new ShowItemAdapter(this);

    }

    @Override
    public void onCardClicked(ShoeItem shoe) {

        Intent intent = new Intent(AdapterClass.this, DetailedActivity.class);
        intent.putExtra("shoeItem", shoe);
        startActivity(intent);
    }

    @Override // Pulled method below to "ShowClickedListeners" in the ShowItemAdapter class
    public void onAddToCartBtnClicked(ShoeItem shoeItem) {
        ShoeCart shoeCart = new ShoeCart();
        shoeCart.setShoeName(shoeItem.getShoeName());
        shoeCart.setShoeBrandName(shoeItem.getShoeBrandName());
        shoeCart.setShoePrice(shoeItem.getShoePrice());
        shoeCart.setShoeImage(shoeItem.getShoeImage());

        final int[] quantity = {1};
        final int[] id = new int[1];

        if (!shoeCartList.isEmpty()) {
            for (int i = 0; i < shoeCartList.size(); i++) {
                if (shoeCart.getShoeName().equals(shoeCartList.get(i).getShoeName())) {
                    quantity[0] = shoeCartList.get(i).getQuantity();
                    quantity[0]++;
                    id[0] = shoeCartList.get(i).getId();
                }
            }
        }


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
                .setAction("Go to Cart", view -> startActivity(new Intent(AdapterClass.this, CartActivity.class))).show();
    }
}


