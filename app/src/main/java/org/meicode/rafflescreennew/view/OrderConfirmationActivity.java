package org.meicode.rafflescreennew.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;

import org.meicode.rafflescreennew.R;

public class OrderConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view to the layout defined in activity_order_confirmation.xml
        setContentView(R.layout.activity_order_confirmation);

        // Find the confirm payment button by its ID in the layout
        Button confirmPaymentButton = findViewById(R.id.buttonConfirmPayment);

        // Set an OnClickListener on the confirm payment button
        confirmPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create an intent to start CartActivity after successful payment
                Intent intent = new Intent(OrderConfirmationActivity.this, CartActivity.class);

                // Put an extra flag 'paymentSuccess' in the intent to indicate payment was successful
                intent.putExtra("paymentSuccess", true);

                // Start CartActivity with the intent
                startActivity(intent);

                // Finish this activity to remove it from the activity stack
                finish();
            }
        });
    }
}