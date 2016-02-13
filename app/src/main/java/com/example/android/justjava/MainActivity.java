package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;
    boolean addWhipped = false;
    boolean addChocolate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button decrementButton = (Button)findViewById(R.id.decrement_button);
        Button incrementButton = (Button)findViewById(R.id.increment_button);
        Button orderButton = (Button)findViewById(R.id.order_button);

        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment();
            }
        });

        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrement();
            }
        });

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrder();
            }
        });

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrder();
            }
        });

    }

    /**
     * This method is called when the order button is clicked.
     */
    private void submitOrder() {

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_checkbox);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);

        addWhipped = whippedCreamCheckBox.isChecked();
        addChocolate = chocolateCheckBox.isChecked();
        String name = nameEditText.getText().toString();
        String message = createOrderSummary(calculatePrice(addWhipped, addChocolate), addWhipped, addChocolate, name);

        Intent callIntent = new Intent(Intent.ACTION_SENDTO);
        callIntent.setData(Uri.parse("mailto:"));
        callIntent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        callIntent.putExtra(Intent.EXTRA_TEXT, message);
        if (callIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(callIntent);
        }

    }

    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name){

        String priceMessage = "Name : " + name +
                "\nQuantity : " + quantity + "" +
                "\nAdd whipped cream: " + addWhippedCream +
                "\nAdd chocolate: " + addChocolate +
                "\nTotal : $" + price + "" +
                "\nThank you!";
        return priceMessage;

    }

    /**
     * This method calculates and returns the how much the order costs
     * @return total rice
     */
    private int calculatePrice(boolean whipped, boolean chocolate) {
        int price = 5;
        if (whipped)
            price = price + 1;
        if (chocolate)
            price = price + 2;
        return quantity * price;
    }

    /**
     * This method increments the quantity when the + button is clicked.
     */
    private void increment() {
        if (quantity < 100) {
            quantity = quantity + 1;
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), "You cannot have more than 100 coffees!", Toast.LENGTH_SHORT);
            toast.show();
        }
        displayQuantity(quantity);
    }

    /**
     * This method increments the quantity when the - button is clicked.
     */
    private void decrement() {
        if (quantity > 1)
            quantity = quantity - 1;
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "You cannot have less than 1 coffee!", Toast.LENGTH_SHORT);
            toast.show();
        }
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}