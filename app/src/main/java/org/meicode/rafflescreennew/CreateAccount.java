package org.meicode.rafflescreennew;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Patterns;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAccount extends AppCompatActivity {

    EditText editTextEmail, editTextPassword, createPass2;
    Button buttonCreate;
    FirebaseAuth mAuth; // Creates an objec to reference the database

/*    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account); // Sets the view to Create Account xml file
        mAuth = FirebaseAuth.getInstance(); // Get an instance of the database
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.createPass1);
        createPass2 = findViewById(R.id.createPass2);
        TextView alreadyHaveAcc = findViewById(R.id.alreadyHaveAcc);
        buttonCreate = findViewById(R.id.createButton);


        // When user clicks on "Create Account":
        buttonCreate.setOnClickListener(v -> {
            // if entered passwords match and email is valid, account is created
            String email,password,confirmPassword;

            email = String.valueOf(editTextEmail.getText());
            password = String.valueOf(editTextPassword.getText());
            confirmPassword = String.valueOf(createPass2.getText());

            if (password.equals(confirmPassword) && validateEmail(email)) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(CreateAccount.this, task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(CreateAccount.this, "Account created. Please login.", Toast.LENGTH_SHORT).show();
                                // user is then redirected to main
                                Intent intent = new Intent(CreateAccount.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(CreateAccount.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(CreateAccount.this, "Account creation failed. Please check fields.", Toast.LENGTH_SHORT).show();
            }

        });

        // When user clicks on "Already Have an Account?", screen switches to main screen
        alreadyHaveAcc.setOnClickListener(v -> {
            Intent intent = new Intent(CreateAccount.this, MainActivity.class);
            startActivity(intent);
        });

    }

    // Checks whether or not user's email is in a valid format
    private boolean validateEmail(String email) {
        String emailInput = email;

        // if email is in valid format, email is accepted
        if (!emailInput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            return true;
        } else {
            Toast.makeText(this, "Invalid Email.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }






}