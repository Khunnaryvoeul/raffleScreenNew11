package org.meicode.rafflescreennew.view;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;
import org.meicode.rafflescreennew.R;
import org.meicode.rafflescreennew.utils.model.ShoeItem;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class DetailedActivity extends AppCompatActivity {

    // for payment
    private PaymentSheet.CustomerConfiguration customerConfig;
    private String paymentIntentClientSecret;
    private PaymentSheet paymentSheet;
    private ImageView shoeImageView;
    private TextView shoeNameTV, shoeBrandNameTV, shoePriceTV;
    private AppCompatButton addToCartBtn;
    private ShoeItem shoe;
    private boolean enteredRaffle = false;
    private boolean raffleEnded = false;
    //stripe payment publishable key
    private static final String STRIPE_PUBLISHABLE_KEY = "pk_test_51OJgWyJkxsEIdX6o7Mar2oj9sxYNCGg3rqyJvo0eOipwRdE2es0WBrxY4QvfDam1d3TnyLsUtTLIKl9OJ8BJoQu500kHW7eERW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        // Initialize Stripe SDK with your publishable key
        PaymentConfiguration.init(getApplicationContext(), STRIPE_PUBLISHABLE_KEY);
        // Initialize paymentSheet when the activity is created
        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);

        initializeVariables();

        shoe = getIntent().getParcelableExtra("shoeItem");
        if (shoe != null) {
            setDataToWidgets();
            setUpCountdownTimer(shoe);
        }

        enteredRaffle = getEnteredRaffleStatus(); // Retrieve the entered raffle status

        // Set the button text based on the entered raffle status or release date
        Date releaseDate = shoe != null ? shoe.getReleaseDate() : null;
        String releaseTime = shoe != null ? shoe.getReleaseTime() : null;

        if (releaseDate != null && releaseTime != null) {
            long currentTime = System.currentTimeMillis();
            long releaseDateTime = combineDateTime(releaseDate, releaseTime);

            if (currentTime < releaseDateTime) {
                // It's before the release date
                addToCartBtn.setText("Coming Soon");
                addToCartBtn.setOnClickListener(null); // Disable button click if it's "Coming Soon"
            } else {
                // It's after the release date, set the default text for entering the raffle
                addToCartBtn.setText(enteredRaffle ? "Entered" : "Enter Raffle");
                addToCartBtn.setOnClickListener(v -> onAddToCartBtnClicked());
            }
        } else {
            // Handle the case where either releaseDate or releaseTime is null
            addToCartBtn.setText("No Raffle Announced");
        }
    }



    private void setDataToWidgets() {
        shoeNameTV.setText(shoe.getShoeName());
        shoeBrandNameTV.setText(shoe.getShoeBrandName());
        shoePriceTV.setText(String.valueOf(shoe.getShoePrice()));
        shoeImageView.setImageResource(shoe.getShoeImage());
    }

    private void initializeVariables() {
        shoeImageView = findViewById(R.id.detailActivityShoeIV);
        shoeNameTV = findViewById(R.id.detailActivityShoeNameTv);
        shoeBrandNameTV = findViewById(R.id.detailActivityShoeBrandNameTv);
        shoePriceTV = findViewById(R.id.detailActivityShoePriceTv);
        addToCartBtn = findViewById(R.id.detailActivityAddToCartBtn);
    }

    private void setUpCountdownTimer(ShoeItem shoe) {
        TextView countDownText = findViewById(R.id.countdown_text);

        Date releaseDate = shoe != null ? shoe.getReleaseDate() : null;
        Date raffleEndDate = shoe != null ? shoe.getRaffleEndDate() : null;
        String releaseTime = shoe != null ? shoe.getReleaseTime() : null;
        String raffleEndTime = shoe != null ? shoe.getRaffleEndTime() : null;

        Log.d("DetailedActivity", "Release Date: " + releaseDate);
        Log.d("DetailedActivity", "Raffle End Date: " + raffleEndDate);

        if (releaseDate != null && raffleEndDate != null && releaseTime != null && raffleEndTime != null) {
            long currentTime = System.currentTimeMillis();
            long releaseDateTime = combineDateTime(releaseDate, releaseTime);
            long raffleEndDateTime = combineDateTime(raffleEndDate, raffleEndTime);

            if (currentTime < releaseDateTime) {
                long releaseTimeDifference = releaseDateTime - currentTime;
                startCountdownTimer(releaseTimeDifference, countDownText, "Release in: ");
            } else if (currentTime < raffleEndDateTime) {
                long raffleTimeDifference = raffleEndDateTime - currentTime;
                startCountdownTimer(raffleTimeDifference, countDownText, "Raffle closes in: ");
            } else {
                countDownText.setText("Dates have passed");
                raffleEnded = true;
            }
        } else {
            Log.e("DetailedActivity", "Invalid date information");
            countDownText.setText("No valid date information");
        }
    }

    private long combineDateTime(Date date, String time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        String[] timeParts = time.split(":");
        int hours = Integer.parseInt(timeParts[0]);
        int minutes = Integer.parseInt(timeParts[1]);

        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTimeInMillis();
    }

    private void startCountdownTimer(long timeDifference, TextView countDownText, String prefix) {
        CountDownTimer timer = new CountDownTimer(timeDifference, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String formattedTime = formatTimeDaysHoursMinutes(millisUntilFinished);
                countDownText.setText(prefix + formattedTime);
            }

            @Override
            public void onFinish() {
                countDownText.setText(prefix + "Time expired");
                raffleEnded = true;
                // Check the raffle result when the timer finishes
                checkRaffleResult();
            }
        };

        timer.start();
    }

    private String formatTimeDaysHoursMinutes(long millis) {
        long days = millis / (24 * 60 * 60 * 1000);
        long hours = (millis % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000);
        long minutes = (millis % (60 * 60 * 1000)) / (60 * 1000);
        long seconds = millis / 1000 % 60;

        return String.format(Locale.getDefault(), "%d days, %02d hours, %02d minutes, %02d seconds", days, hours, minutes, seconds);
    }

    private void onAddToCartBtnClicked() {
        Date releaseDate = shoe != null ? shoe.getReleaseDate() : null;
        Date raffleEndDate = shoe != null ? shoe.getRaffleEndDate() : null;
        String releaseTime = shoe != null ? shoe.getReleaseTime() : null;
        String raffleEndTime = shoe != null ? shoe.getRaffleEndTime() : null;

        if (raffleEnded && enteredRaffle) {
            // Raffle has ended, and the user has entered the raffle
            checkRaffleResult();
        } else if (releaseDate != null && raffleEndDate != null && releaseTime != null && raffleEndTime != null) {
            long currentTime = System.currentTimeMillis();
            long releaseDateTime = combineDateTime(releaseDate, releaseTime);
            long raffleEndDateTime = combineDateTime(raffleEndDate, raffleEndTime);

            if (currentTime < releaseDateTime) {
                // It's before the release date
                Toast.makeText(DetailedActivity.this, "The raffle is not open yet.", Toast.LENGTH_SHORT).show();
            } else if (currentTime >= releaseDateTime && currentTime < raffleEndDateTime) {
                // User can enter or exit the raffle
                if (enteredRaffle) {
                    // User wants to exit the raffle
                    enteredRaffle = false;
                    Toast.makeText(DetailedActivity.this, "You've exited the raffle.", Toast.LENGTH_SHORT).show();
                    addToCartBtn.setText("Enter Raffle");
                    saveEnteredRaffleStatus(false); // Save the entered raffle status
                } else {
                    // User can enter the raffle
                    enteredRaffle = true;
                    Toast.makeText(DetailedActivity.this, "Raffle entered.", Toast.LENGTH_SHORT).show();
                    addToCartBtn.setText("Entered");
                    saveEnteredRaffleStatus(true); // Save the entered raffle status
                }
            } else {
                // Raffle is not open yet or has already ended
                Toast.makeText(DetailedActivity.this, "The raffle is not open yet or has already ended.", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Handle the case where either releaseDate, raffleEndDate, releaseTime, or raffleEndTime is null
            Toast.makeText(DetailedActivity.this, "Invalid date or time information", Toast.LENGTH_SHORT).show();
        }
    }



    // Assigns the user a random number from 0 to 9 when raffle is entered
    public static int getUserEntry(Random random) {
        return random.nextInt(1);
    }
    // Generates a random winning number from 0 to 9
    public static int getWinningNum(Random random) {
        return random.nextInt(1);
    }

    private void checkRaffleResult() {
        if (enteredRaffle && raffleEnded) {
            int userEntry = getUserEntry(new Random());
            int winningNum = getWinningNum(new Random());

            if (winningNum == userEntry) {
                // Show pop-up for winning result
                showRaffleResultDialog(true);
            } else {
                // Show pop-up for losing result
                showRaffleResultDialog(false);
            }
        } else {
            // Handle the case where the user hasn't entered the raffle or the raffle hasn't ended
            Toast.makeText(DetailedActivity.this, "You haven't entered the raffle or the raffle hasn't ended.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showRaffleResultDialog(boolean won) {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailedActivity.this);
        builder.setTitle(won ? "Congratulations!" : "Better luck next time!")
                .setMessage(won ? "You won the raffle! What would you like to do?" : "Unfortunately, you didn't win this time.");
        builder.setPositiveButton(won ? "Buy now" : "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the "Buy now" button click or simply dismiss the dialog for "OK"
                if (paymentIntentClientSecret != null) {
                    paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret, new PaymentSheet.Configuration("Example, Inc.", customerConfig));

                    // Fetch payment information
                    fetchPaymentApi();
                }

                // Move the openPaymentSheet() call here
                openPaymentSheet();
            }
        });

        // Add an "Exit" button only if the user won
        if (won) {
            builder.setNegativeButton("Exit", (dialog, which) -> {
                // Handle the "Exit" button click
                // For now, let's just close the dialog
                dialog.dismiss();
            });
        };

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void saveEnteredRaffleStatus(boolean entered) {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("enteredRaffle", entered);
        editor.apply();
    }

    private boolean getEnteredRaffleStatus() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        return preferences.getBoolean("enteredRaffle", false);
    }

    // from here on is the newly added code for payment methods
    private void openPaymentSheet() {
        if (paymentSheet != null) {
            if (paymentIntentClientSecret != null) {
                // Call openPaymentSheet when the payment button is clicked
                paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret, new PaymentSheet.Configuration("Your Company", customerConfig));
                fetchPaymentApi();
            } else {
                // Fetch payment information if paymentIntentClientSecret is null
                fetchPaymentApi();
            }
        } else {
            Log.e("MainActivity", "paymentSheet is null");
            // Handle the error or show a toast to inform the user
            Toast.makeText(this, "Payment sheet initialization failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchPaymentApi() {
        // Replace the URL with your actual API endpoint
        String url = "https://us-central1-sneaker-5d3ae.cloudfunctions.net/Sneaker?amt=50";

        // Perform a network request to fetch payment
        // information
        // Use Volley, Retrofit, or any other networking library of your choice

        // Example using Volley
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject result = new JSONObject(response);
                        customerConfig = new PaymentSheet.CustomerConfiguration(
                                result.getString("customer"),
                                result.getString("ephemeralKey")
                        );
                        paymentIntentClientSecret = result.getString("paymentIntent");
                        PaymentConfiguration.init(getApplicationContext(), result.getString("publishableKey"));

                        // Present the PaymentSheet with fetched data
                        paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret, new PaymentSheet.Configuration("Your Company", customerConfig));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Failed to parse payment information", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Failed to fetch payment information", Toast.LENGTH_SHORT).show();
                });

        queue.add(stringRequest);
    }

    private void onPaymentSheetResult(final PaymentSheetResult paymentSheetResult) {
        // Handle the result of the PaymentSheet
        if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            Toast.makeText(this, "Payment canceled", Toast.LENGTH_SHORT).show();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            // Payment successful, you can handle additional actions here if needed
            Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show();
        }
    }
}