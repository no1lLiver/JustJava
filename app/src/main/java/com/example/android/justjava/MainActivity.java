package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            // Show an error message as a toast
            Toast.makeText(this, getString(R.string.toastUp), Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }


    public void decrement(View view) {
        if (quantity == 0) {
            // Show an error message as a toast
            Toast.makeText(this, getString(R.string.toastDown), Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }


    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_view);
        Editable nameEditable = nameField.getText();
        String name = nameEditable.toString();

        CheckBox whippedCream = (CheckBox) findViewById(R.id.notify_me_checkbox);
        boolean hasWhippedCream = whippedCream.isChecked();

        CheckBox chocolate = (CheckBox) findViewById(R.id.notify_me_checkbox1);
        boolean hasChocolate = chocolate.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }

    private int calculatePrice(boolean addWhippedCream, boolean  addChocolate) {
        int basePrice = 5;
        if(addChocolate) {
        basePrice=basePrice+2;
        }
        else if(addWhippedCream){

            basePrice=basePrice+1;
        }
        return quantity * basePrice;
    }

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = getString(R.string.order_summary_name, name)+ " \n" ;
        priceMessage += getString(R.string.order_summary_whipped_cream, addWhippedCream) + " \n";
        priceMessage += getString(R.string.order_summary_chocolate, addChocolate) + " \n";
        priceMessage += getString(R.string.order_summary_quantity, quantity) + " \n";
        priceMessage += getString(R.string.order_summary_price, price) + "$ \n";
        priceMessage += getString(R.string.thank_you);
        return priceMessage;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }



}
