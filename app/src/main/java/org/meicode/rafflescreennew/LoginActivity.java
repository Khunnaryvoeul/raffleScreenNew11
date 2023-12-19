//package org.meicode.rafflescreennew;
//
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.navigation.NavController;
//import androidx.navigation.Navigation;
//import androidx.navigation.ui.AppBarConfiguration;
//import androidx.navigation.ui.NavigationUI;
//import org.meicode.rafflescreennew.databinding.ActivityMainBinding;
//
//import com.google.firebase.auth.FirebaseAuth;
//
//import android.content.Intent;
//import android.widget.Toast;
//
//public class LoginActivity extends AppCompatActivity {
//
//    private ActivityMainBinding binding;
//    EditText editTextEmail, editTextPassword;
//    Button loginButton; // Initializes the login button
//    FirebaseAuth mAuth; // Creates an object to reference the database
//
///*    @Override
//    public void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            Intent intent = new Intent(getApplicationContext(), CreateAccount.class);
//            startActivity(intent);
//            finish();
//        }
//    }*/
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        mAuth = FirebaseAuth.getInstance(); // Get an instance of the database
//        editTextEmail = findViewById(R.id.email); // Email and password are initialized from the @email and @password in the activity_home.xml
//        editTextPassword = findViewById(R.id.password);
//        loginButton = findViewById(R.id.loginButton); // Login button from the activity_home
//
//        loginButton.setOnClickListener(v -> { // Receives the input from the @email and @password in the activity_home.xml
//            String email, password;
//            email = String.valueOf(editTextEmail.getText());
//            password = String.valueOf(editTextPassword.getText());
//
//            mAuth.signInWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(task -> {
//                        //This is where it is important to change the class to whatever the raffle screen is
//                        if (task.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(),"Login Success", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(getApplicationContext(), CreateAccount.class);
//                            startActivity(intent);
//                            finish();
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Toast.makeText(LoginActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    });
//        });
//
//        TextView forgotPass = findViewById(R.id.forgotPass);
//        TextView createAcc = findViewById(R.id.createAcc);
//        // When user clicks on "Forgot Password?", screen switches to forgotpass screen
//        forgotPass.setOnClickListener(v -> {
//            Intent intent = new Intent(LoginActivity.this, ForgotpassActivity.class);
//            startActivity(intent);
//        });
//
//        // When the user clicks "Create an Account", screen switches to create_account screen
//        createAcc.setOnClickListener(v -> {
//            Intent intent = new Intent(LoginActivity.this, CreateAccount.class);
//            startActivity(intent);
//        });
//
//    }
//}
