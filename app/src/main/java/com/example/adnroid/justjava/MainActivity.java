package com.example.adnroid.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    //Keys for saving sate of important values when app is destroyed
    private static final String PLANE_COFFEE_QUANTITY = "SavedStateOfPlaneCoffeesQuantity";
    private static final String PLANE_COFFEE_PRICE = "SavedStateOfPlaneCoffeesPrice";
    private static final String COFFEE_CREAM_QUANTITY = "SavedStateOfCoffeesWithCreamQuantity";
    private static final String COFFEE_CREAM_PRICE = "SavedStateOfCoffeesWithCreamPrice";
    private static final String COFFEE_CHOCOLATE_QUANTITY = "SavedStateOfCoffeesWithChocolateQuantity";
    private static final String COFFEE_CHOCOLATE_PRICE = "SavedStateOfCoffeesWithChocolatePrice";
    private static final String COFFEE_ALL_TOPPINGS_QUANTITY = "SavedStateOfCoffeesWithAllToppingsQuantity";
    private static final String COFFEE_ALL_TOPPINGS_PRICE = "SavedStateOfCoffeesWithAllToppingsPrice";
    private static final String HAS_WHIPPED_CREAM = "SavedStateOfWhippedCreamTopping";
    private static final String HAS_CHOCOLATE = "SavedStateOfChocolateTopping";
    //Tracks the quantity of coffees without toppings
    int planeCoffeeQuantity = 0;
    //Tracks the total cost of coffees without toppings
    int planeCoffeePrice = 0;
    //Tracks the quantity of coffees with whipped cream
    int coffeeCreamQuantity = 0;
    //Tracks the total cost of coffees with whipped cream
    int coffeeCreamPrice = 0;
    //Tracks the quantity of coffees with chocolate
    int coffeeChocolateQuantity = 0;
    //Tracks the total cost of coffees with chocolate
    int coffeeChocolatePrice = 0;
    //Track the quantity of coffees with both toppings
    int coffeeAllToppingsQuantity = 0;
    //Tracks the total cost of coffees with both toppings
    int coffeeAllToppingsPrice = 0;
    //Tracks the cost of whole order
    int totalPrice = 0;
    //Tracks if Whipped Cream topping has been added
    boolean hasWhippedCream = false;
    //Tracks if Chocolate topping has been added
    boolean hasChocolate = false;
    //Ads Whipped Cream topping
    CheckBox whippedCreamChechBox;
    //Ads Chocolate topping
    CheckBox chocolateCheckBox;
    //Displays order summary for coffees without toppings
    TextView planeCoffeeSum;
    //Displays order summary for coffees with whipped cream
    TextView coffeeCreamSum;
    //Displays order summary for coffees with chocolate
    TextView coffeeChocolateSum;
    //Displays order summary for coffees with both toppings
    TextView coffeeAllToppingsSum;
    //Displays cost of whole order
    TextView total;
    //Shows or hides order summary for coffees without toppings
    TableRow planeCoffeeRow;
    //Shows or hides order summary for coffees with whipped cream
    TableRow coffeeCreamRow;
    //Shows of hides order summary for coffees with chocolate
    TableRow coffeeChocolateRow;
    //Shows or hides order summary for coffees with both toppings
    TableRow coffeeAllToppingsRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        whippedCreamChechBox = findViewById(R.id.whipped_cream);
        chocolateCheckBox = findViewById(R.id.chocolate);
        planeCoffeeSum = findViewById(R.id.plane_coffee_sum);
        coffeeCreamSum = findViewById(R.id.coffee_cream_sum);
        coffeeChocolateSum = findViewById(R.id.coffee_chocolate_sum);
        coffeeAllToppingsSum = findViewById(R.id.coffee_all_toppings_sum);
        total = findViewById(R.id.total);
        planeCoffeeRow = findViewById(R.id.plane_coffee_row);
        coffeeCreamRow = findViewById(R.id.coffee_cream_row);
        coffeeChocolateRow = findViewById(R.id.coffee_chocolate_row);
        coffeeAllToppingsRow = findViewById(R.id.coffee_all_toppings_row);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        planeCoffeeQuantity = savedInstanceState.getInt(PLANE_COFFEE_QUANTITY);
        planeCoffeePrice = savedInstanceState.getInt(PLANE_COFFEE_PRICE);
        if (planeCoffeeQuantity > 0) {
            display(getString(R.string.sum, planeCoffeeQuantity, NumberFormat.getCurrencyInstance().format(planeCoffeePrice)), planeCoffeeSum);
            planeCoffeeRow.setVisibility(View.VISIBLE);
        }
        coffeeCreamQuantity = savedInstanceState.getInt(COFFEE_CREAM_QUANTITY);
        coffeeCreamPrice = savedInstanceState.getInt(COFFEE_CREAM_PRICE);
        if (coffeeCreamQuantity > 0) {
            display(getString(R.string.sum, coffeeCreamQuantity, NumberFormat.getCurrencyInstance().format(coffeeCreamPrice)), coffeeCreamSum);
            coffeeCreamRow.setVisibility(View.VISIBLE);
        }
        coffeeChocolateQuantity = savedInstanceState.getInt(COFFEE_CHOCOLATE_QUANTITY);
        coffeeChocolatePrice = savedInstanceState.getInt(COFFEE_CHOCOLATE_PRICE);
        if (coffeeChocolateQuantity > 0) {
            display(getString(R.string.sum, coffeeChocolateQuantity, NumberFormat.getCurrencyInstance().format(coffeeChocolatePrice)), coffeeChocolateSum);
            coffeeChocolateRow.setVisibility(View.VISIBLE);
        }
        coffeeAllToppingsQuantity = savedInstanceState.getInt(COFFEE_ALL_TOPPINGS_QUANTITY);
        coffeeAllToppingsPrice = savedInstanceState.getInt(COFFEE_ALL_TOPPINGS_PRICE);
        if (coffeeAllToppingsQuantity > 0) {
            display(getString(R.string.sum, coffeeAllToppingsQuantity, NumberFormat.getCurrencyInstance().format(coffeeAllToppingsPrice)), coffeeAllToppingsSum);
            coffeeAllToppingsRow.setVisibility(View.VISIBLE);
        }
        setTotalPrice();
        hasWhippedCream = savedInstanceState.getBoolean(HAS_WHIPPED_CREAM);
        hasChocolate = savedInstanceState.getBoolean(HAS_CHOCOLATE);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(PLANE_COFFEE_QUANTITY, planeCoffeeQuantity);
        savedInstanceState.putInt(PLANE_COFFEE_PRICE, planeCoffeePrice);
        savedInstanceState.putInt(COFFEE_CREAM_QUANTITY, coffeeCreamQuantity);
        savedInstanceState.putInt(COFFEE_CREAM_PRICE, coffeeCreamPrice);
        savedInstanceState.putInt(COFFEE_CHOCOLATE_QUANTITY, coffeeChocolateQuantity);
        savedInstanceState.putInt(COFFEE_CHOCOLATE_PRICE, coffeeChocolatePrice);
        savedInstanceState.putInt(COFFEE_ALL_TOPPINGS_QUANTITY, coffeeAllToppingsQuantity);
        savedInstanceState.putInt(COFFEE_ALL_TOPPINGS_PRICE, coffeeAllToppingsPrice);
        savedInstanceState.putBoolean(HAS_WHIPPED_CREAM, hasWhippedCream);
        savedInstanceState.putBoolean(HAS_CHOCOLATE, hasChocolate);
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * This method is called when the Whipped Cream checkbox is clicked (checked or unchecked).
     */
    public void whippedCreamChecked(View view) {
        hasWhippedCream = whippedCreamChechBox.isChecked();
    }

    /**
     * This method is called when the Chocolate checkbox is clicked (checked or unchecked).
     */
    public void chocolateChecked(View view) {
        hasChocolate = chocolateCheckBox.isChecked();
    }

    /**
     * This method is called when the +1 Coffee button is clicked.
     */
    public void addCoffee(View view) {
        if (planeCoffeeQuantity + coffeeCreamQuantity + coffeeChocolateQuantity + coffeeAllToppingsQuantity >= 50) {
            Toast.makeText(this, getString(R.string.too_many_toast), Toast.LENGTH_SHORT).show();
            return;
        }
        if (hasWhippedCream && !hasChocolate) {
            coffeeCreamQuantity += 1;
            coffeeCreamPrice += 6;
            String sumMessage = getString(R.string.sum, coffeeCreamQuantity, NumberFormat.getCurrencyInstance().format(coffeeCreamPrice));
            display(sumMessage, coffeeCreamSum);
            coffeeCreamRow.setVisibility(View.VISIBLE);
        } else if (!hasWhippedCream && hasChocolate) {
            coffeeChocolateQuantity += 1;
            coffeeChocolatePrice += 7;
            String sumMessage = getString(R.string.sum, coffeeChocolateQuantity, NumberFormat.getCurrencyInstance().format(coffeeChocolatePrice));
            display(sumMessage, coffeeChocolateSum);
            coffeeChocolateRow.setVisibility(View.VISIBLE);
        } else if (hasWhippedCream && hasChocolate) {
            coffeeAllToppingsQuantity += 1;
            coffeeAllToppingsPrice += 8;
            String sumMessage = getString(R.string.sum, coffeeAllToppingsQuantity, NumberFormat.getCurrencyInstance().format(coffeeAllToppingsPrice));
            display(sumMessage, coffeeAllToppingsSum);
            coffeeAllToppingsRow.setVisibility(View.VISIBLE);
        } else {
            planeCoffeeQuantity += 1;
            planeCoffeePrice += 5;
            String sumMessage = getString(R.string.sum, planeCoffeeQuantity, NumberFormat.getCurrencyInstance().format(planeCoffeePrice));
            display(sumMessage, planeCoffeeSum);
            planeCoffeeRow.setVisibility(View.VISIBLE);
        }
        setTotalPrice();
    }

    public void setTotalPrice() {
        totalPrice = planeCoffeePrice + coffeeCreamPrice + coffeeChocolatePrice + coffeeAllToppingsPrice;
        String sumMessage = getString(R.string.total, NumberFormat.getCurrencyInstance().format(totalPrice));
        display(sumMessage, total);
    }

    /**
     * This method displays specified message in specified TextView
     *
     * @param message  to be desplayed
     * @param textView in which the message should be displayed
     */
    public void display(String message, TextView textView) {
        textView.setText(message);
    }

    //This method is called when minus button next to "Coffee with no toppings" in order summary is clicked.
    public void planeCoffeeMinus(View view) {
        planeCoffeeQuantity -= 1;
        planeCoffeePrice -= 5;
        String sumMessage = getString(R.string.sum, planeCoffeeQuantity, NumberFormat.getCurrencyInstance().format(planeCoffeePrice));
        display(sumMessage, planeCoffeeSum);
        if (planeCoffeeQuantity == 0) {
            planeCoffeeRow.setVisibility(View.GONE);
        }
        setTotalPrice();
    }

    //This method is called when X button next to "Coffee with no toppings" in order summary is clicked.
    public void planeCoffeeDelete(View view) {
        planeCoffeeQuantity = 0;
        planeCoffeePrice = 0;
        planeCoffeeRow.setVisibility(View.GONE);
        setTotalPrice();
    }

    //This method is called when minus button next to "Coffee with Whipped Cream" in order summary is clicked.
    public void coffeeCreamMinus(View view) {
        coffeeCreamQuantity -= 1;
        coffeeCreamPrice -= 6;
        String sumMessage = getString(R.string.sum, coffeeCreamQuantity, NumberFormat.getCurrencyInstance().format(coffeeCreamPrice));
        display(sumMessage, coffeeCreamSum);
        if (coffeeCreamQuantity == 0) {
            coffeeCreamRow.setVisibility(View.GONE);
        }
        setTotalPrice();
    }

    //This method is called when X button next to "Coffee with Whipped Cream" in order summary is clicked.
    public void coffeeCreamDelete(View view) {
        coffeeCreamQuantity = 0;
        coffeeCreamPrice = 0;
        coffeeCreamRow.setVisibility(View.GONE);
        setTotalPrice();
    }

    //This method is called when minus button next to "Coffee with Chocolate" in order summary is clicked.
    public void coffeeChocolateMinus(View view) {
        coffeeChocolateQuantity -= 1;
        coffeeChocolatePrice -= 7;
        String sumMessage = getString(R.string.sum, coffeeChocolateQuantity, NumberFormat.getCurrencyInstance().format(coffeeChocolatePrice));
        display(sumMessage, coffeeChocolateSum);
        if (coffeeChocolateQuantity == 0) {
            coffeeChocolateRow.setVisibility(View.GONE);
        }
        setTotalPrice();
    }

    //This method is called when X button next to "Coffee with Chocolate" in order summary is clicked.
    public void coffeeChocolateDelete(View view) {
        coffeeChocolateQuantity = 0;
        coffeeChocolatePrice = 0;
        coffeeChocolateRow.setVisibility(View.GONE);
        setTotalPrice();
    }

    //This method is called when minus button next to "Coffee with both toppings" in order summary is clicked.
    public void coffeeAllToppingsMinus(View view) {
        coffeeAllToppingsQuantity -= 1;
        coffeeAllToppingsPrice -= 8;
        String sumMessage = getString(R.string.sum, coffeeAllToppingsQuantity, NumberFormat.getCurrencyInstance().format(coffeeAllToppingsPrice));
        display(sumMessage, coffeeAllToppingsSum);
        if (coffeeAllToppingsQuantity == 0) {
            coffeeAllToppingsRow.setVisibility(View.GONE);
        }
        setTotalPrice();
    }

    //This method is called when X button next to "Coffee with both toppings" in order summary is clicked.
    public void coffeeAllToppingsDelete(View view) {
        coffeeAllToppingsQuantity = 0;
        coffeeAllToppingsPrice = 0;
        coffeeAllToppingsRow.setVisibility(View.GONE);
        setTotalPrice();
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        if (planeCoffeeQuantity + coffeeCreamQuantity + coffeeChocolateQuantity + coffeeAllToppingsQuantity > 0) {
            String name = ((EditText) findViewById(R.id.name_input)).getText().toString();
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
            intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(name));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, getString(R.string.too_few_toast), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * creates order summary
     *
     * @param name of customer
     * @return order message
     */
    private String createOrderSummary(String name) {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n";
        if (planeCoffeeQuantity > 0) {
            priceMessage += "\n" + getString(R.string.plane_coffee);
            priceMessage += "\n" + getString(R.string.sum, planeCoffeeQuantity, NumberFormat.getCurrencyInstance().format(planeCoffeePrice));
        }
        if (coffeeCreamQuantity > 0) {
            priceMessage += "\n" + getString(R.string.coffee_cream);
            priceMessage += "\n" + getString(R.string.sum, coffeeCreamQuantity, NumberFormat.getCurrencyInstance().format(coffeeCreamPrice));
        }
        if (coffeeChocolateQuantity > 0) {
            priceMessage += "\n" + getString(R.string.coffee_chocolate);
            priceMessage += "\n" + getString(R.string.sum, coffeeChocolateQuantity, NumberFormat.getCurrencyInstance().format(coffeeChocolatePrice));
        }
        if (coffeeAllToppingsQuantity > 0) {
            priceMessage += "\n" + getString(R.string.coffee_all_toppings);
            priceMessage += "\n" + getString(R.string.sum, coffeeAllToppingsQuantity, NumberFormat.getCurrencyInstance().format(coffeeAllToppingsPrice));
        }
        priceMessage += "\n";
        priceMessage += "\n" + getString(R.string.total, NumberFormat.getCurrencyInstance().format(totalPrice));
        priceMessage += "\n" + getString(R.string.order_summary_thank_you);
        return priceMessage;
    }

}
