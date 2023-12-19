package org.meicode.rafflescreennew;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import org.meicode.rafflescreennew.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    EditText editTextEmail, editTextPassword;
    Button loginButton; // Initializes the login button
    FirebaseAuth mAuth; // Creates an object to reference the database


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance(); // Get an instance of the database

        editTextEmail = findViewById(R.id.email); // Email and password are initialized from the @email and @password in the activity_home.xml
        editTextPassword = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton); // Login button from the activity_home

        loginButton.setOnClickListener(v -> { // Receives the input from the @email and @password in the activity_home.xml
            String email, password;
            email = String.valueOf(editTextEmail.getText());
            password = String.valueOf(editTextPassword.getText());

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        //This is where it is important to change the class to whatever the raffle screen is
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Login Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), AdapterClass.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        TextView forgotPass = findViewById(R.id.forgotPass);
        TextView createAcc = findViewById(R.id.createAcc);
        // When user clicks on "Forgot Password?", screen switches to forgotpass screen
        forgotPass.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ForgotpassActivity.class);
            startActivity(intent);
        });

        // When the user clicks "Create an Account", screen switches to create_account screen
        createAcc.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateAccount.class);
            startActivity(intent);
        });
    }}


























//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.navView, navController);
//    }}

//setContentView(binding.getRoot());
//        homeBinding = FragmentHomeBinding.inflate(getLayoutInflater()); // Add this line





//        mAuth = FirebaseAuth.getInstance();
//        // Reference views from fragment_home.xml
//        editTextEmail = homeBinding.email;
//        editTextPassword = homeBinding.password;
//        loginButton = homeBinding.loginButton;

        // Set up RecyclerView and adapter
//        recyclerView = binding.mainRecyclerView; // Use binding object to reference views
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
//
//        adapter = new ShowItemAdapter(this);
//        recyclerView.setAdapter(adapter);
//
//        cartImageView = binding.cartIv; // Use binding object to reference views
//        coordinatorLayout = binding.coordinatorLayout; // Use binding object to reference views
//
//        initializeVariables();
//        setUpList();
//
//        recyclerView = findViewById(R.id.mainRecyclerView);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
//
//        adapter.setShoeItemList(shoeItemList);
//        recyclerView.setAdapter(adapter);
//
//        cartImageView.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, CartActivity.class)));
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        viewModel.getAllCartItems().observe(this, shoeCarts -> shoeCartList.addAll(shoeCarts));
//    }
//
//    // sets up items to be displayed on the device
//    private void setUpList() {
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy", Locale.US);
//        Date releaseDate1 = parseDate("25/11/23", dateFormat);
//        Date releaseDate2 = parseDate("29/11/23", dateFormat);
//        Date releaseDate3 = parseDate("09/12/23", dateFormat);
//        Date releaseDate4 = parseDate("11/12/23", dateFormat);
//        Date releaseDate5 = parseDate("30/12/23", dateFormat);
//
//        shoeItemList.add(new ShoeItem("New Balance 2002r Protection Pack Purple", "Nike", R.drawable.__ccb10249_2702_4b4f_87d2_de9809363657_540x, 130, releaseDate1));
//        shoeItemList.add(new ShoeItem("Travis Scott 1 Fragment", "Jordan", R.drawable.ipad_travis_scott_x_fragment_x_air_jordan_1_high_og_military_blue, 160, releaseDate2));
//        shoeItemList.add(new ShoeItem("Travis Scott Air Jordan 1 Mocha", "Jordan", R.drawable.travis_scott_x_air_jordan_1_retro_high_og, 160, releaseDate3));
//        shoeItemList.add(new ShoeItem("Nike Air Force 1 Blue Void", "Nike", R.drawable._2nikmrfrc107lv8fmns_sail_blue_void, 90, releaseDate4));
//        shoeItemList.add(new ShoeItem("Nike Low Blazers Black and White Sail", "Nike", R.drawable._1nikmblzrlw77vntmns_black_white_sail, 90, releaseDate5));
//    }
//
//    private Date parseDate(String dateString, SimpleDateFormat dateFormat) {
//        try {
//            return dateFormat.parse(dateString);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return null; // Return null in case of parsing failure
//        }
//    }
//
//    private void initializeVariables() {
//
//        cartImageView = findViewById(R.id.cartIv);
//        coordinatorLayout = findViewById(R.id.coordinatorLayout);
//        shoeCartList = new ArrayList<>();
//        viewModel = new ViewModelProvider(this).get(CartViewModel.class);
//        shoeItemList = new ArrayList<>();
//        recyclerView = findViewById(R.id.mainRecyclerView);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
//
//        adapter = new ShowItemAdapter(this);
//    }
//
//    @Override
//    public void onCardClicked(ShoeItem shoe) {
//
//        Intent intent = new Intent(MainActivity.this, DetailedActivity.class);
//        intent.putExtra("shoeItem", shoe);
//        startActivity(intent);
//    }
//
//    //was originally override
//    //@Override
//    public void onAddToCartBtnClicked(ShoeItem shoeItem) {
//        ShoeCart shoeCart = new ShoeCart();
//        shoeCart.setShoeName(shoeItem.getShoeName());
//        shoeCart.setShoeBrandName(shoeItem.getShoeBrandName());
//        shoeCart.setShoePrice(shoeItem.getShoePrice());
//        shoeCart.setShoeImage(shoeItem.getShoeImage());
//
//        final int[] quantity = {1};
//        final int[] id = new int[1];
//
//        if (!shoeCartList.isEmpty()) {
//            for (int i = 0; i < shoeCartList.size(); i++)  {
//                if (shoeCart.getShoeName().equals(shoeCartList.get(i).getShoeName())) {
//                    quantity[0] = shoeCartList.get(i).getQuantity();
//                    quantity[0]++;
//                    id[0] = shoeCartList.get(i).getId();
//                }
//            }
//        }
//
//
//        if (quantity[0] == 1) {
//            shoeCart.setQuantity(quantity[0]);
//            shoeCart.setTotalItemPrice(quantity[0] * shoeCart.getShoePrice());
//            viewModel.insertCartItem(shoeCart);
//        } else {
//            viewModel.updateQuantity(id[0], quantity[0]);
//            viewModel.updatePrice(id[0], quantity[0] * shoeCart.getShoePrice());
//        }
//
//        makeSnackBar();
//    }
//
//    private void makeSnackBar() {
//        Snackbar.make(coordinatorLayout, "Item Added To Cart", Snackbar.LENGTH_SHORT)
//                .setAction("Go to Cart", view -> startActivity(new Intent(MainActivity.this, CartActivity.class))).show();
//    }
//}
