package com.example.adnroid.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    //Tracks the quantity of coffees ordered
    int quantity = 2;

    //Track the current total price of the order
    int currentPrice = 10;

    //Track the current base price of one cup of coffee (can change if toppings are added)
    int basePrice = 5;

    //Tracks if Whipped Cream topping has been added
    boolean hasWhippedCream = false;

    //Tracks if Chocolate topping has been added
    boolean hasChocolate = false;

    //Displays the quantity of coffees ordered
    TextView quantityTextView;

    //Displays the current total price of order
    TextView priceTextView;

    //Ads Whipped Cream topping
    CheckBox whippedCreamChechBox;

    //Ads Chocolate topping
    CheckBox chocolateCheckBox;

    //Keys for saving sate of important values when app is destroyed
    private static final String QUANTITY = "SavedStateOfQuantity";
    private static final String CURRENT_PRICE = "SavedStateOfCurrentPrice";
    private static final String BASE_PRICE = "SavedStateOfBasePrice";
    private static final String HAS_WHIPPED_CREAM = "SavedStateOfWhippedCreamTopping";
    private static final String HAS_CHOCOLATE = "SavedStateOfChocolateTopping";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        priceTextView = (TextView) findViewById(R.id.total_price_text_view);
        whippedCreamChechBox = (CheckBox) findViewById(R.id.whipped_cream);
        chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate);
    }

    @Override
    public void onRestoreInstanceState (Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        quantity = savedInstanceState.getInt(QUANTITY);
        displayQuantity();
        currentPrice = savedInstanceState.getInt(CURRENT_PRICE);
        displayCurrentPrice();
        basePrice = savedInstanceState.getInt(BASE_PRICE);
        hasWhippedCream = savedInstanceState.getBoolean(HAS_WHIPPED_CREAM);
        hasChocolate = savedInstanceState.getBoolean(HAS_CHOCOLATE);
    }

    @Override
    public void onSaveInstanceState (Bundle savedInstanceState) {
        savedInstanceState.putInt(QUANTITY, quantity);
        savedInstanceState.putInt(CURRENT_PRICE, currentPrice);
        savedInstanceState.putInt(BASE_PRICE, basePrice);
        savedInstanceState.putBoolean(HAS_WHIPPED_CREAM, hasWhippedCream);
        savedInstanceState.putBoolean(HAS_CHOCOLATE, hasChocolate);
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String name = ((EditText) findViewById(R.id.name_input)).getText().toString();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(currentPrice, name));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     */
    private void calculatePrice() {
        currentPrice = quantity * basePrice;
        displayCurrentPrice();
    }

    /**
     * creates order summary
     *
     * @param name of customer
     * @param price of the order
     * @return order message
     */
    private String createOrderSummary (int price, String name){
        String whippedCream;
        String chocolate;
        if (hasWhippedCream) {whippedCream = getString(R.string.yes);} else {whippedCream = getString(R.string.no);}
        if (hasChocolate) {chocolate = getString(R.string.yes);} else {chocolate = getString(R.string.no);}
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, whippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, chocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.order_summary_thank_you);
        return priceMessage;
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, getString(R.string.too_many_toast), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity();
        calculatePrice();
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, getString(R.string.too_few_toast), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity();
        calculatePrice();
    }

    /**
     * This method is called when the Whipped Cream checkbox is clicked (checked or unchecked).
     */
    public void whippedCreamChecked (View view){
        hasWhippedCream = whippedCreamChechBox.isChecked();
        if (hasWhippedCream) {basePrice += 1;} else {basePrice -= 1;}
        calculatePrice();
    }

    /**
     * This method is called when the Chocolate checkbox is clicked (checked or unchecked).
     */
    public void chocolateChecked (View view){
        hasChocolate = chocolateCheckBox.isChecked();
        if (hasChocolate) {basePrice += 2;} else {basePrice -= 2;}
        calculatePrice();
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity() {
        quantityTextView.setText("" + quantity);
    }

    /**
     * This method displays the current total price of the order on the screen.
     */
    private void displayCurrentPrice() {
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(currentPrice));
    }

}
