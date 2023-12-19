package org.meicode.rafflescreennew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ForgotpassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);
        EditText email = findViewById(R.id.email);
        TextView alreadyKnowPass = (TextView) findViewById(R.id.alreadyKnowPass);
        TextView resetButton = (TextView) findViewById(R.id.resetButton);

        // When user clicks on "Reset":
        resetButton.setOnClickListener(v -> {
            // validateEmail method is on line 45
            validateEmail(email);
        });

        // When user clicks on "Already Know Your Password?", screen switches to main screen
        alreadyKnowPass.setOnClickListener(v -> {
            Intent intent = new Intent(ForgotpassActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    // Checks whether or not user's email is in a valid format.
    private boolean validateEmail(EditText email) {
        String emailInput = email.getText().toString();

        // if email is in valid format, email will be sent
        if(!emailInput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            Toast.makeText(this, "Email Sent.", Toast.LENGTH_SHORT).show();
            return true;

            // if email is in an invalid format, user is notified
        } else {
            Toast.makeText(this, "Invalid Email.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
