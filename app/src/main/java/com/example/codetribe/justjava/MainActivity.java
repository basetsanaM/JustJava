package com.example.codetribe.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;









import android.widget.Toast;

import java.text.NumberFormat;


/**
 * This app displays an order form to order coffee
 */

public class MainActivity extends AppCompatActivity
{
    int quantity=1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void submitOrder (View view)
    {
        EditText nameField =(EditText) findViewById(R.id.name_field);
        String name= nameField.getText().toString();

        //figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox =(CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        //figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox =(CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate= chocolateCheckBox.isChecked();


        int price=calculatePrice(hasWhippedCream,hasChocolate);
        String message=createOrderSummary(price,hasWhippedCream,name,hasChocolate);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,"Just Java order for "+ name);
        intent.putExtra(Intent.EXTRA_TEXT,message);
        if (intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }

    }
    /**
     * Calculates the price of the order based on the current quantity
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @return  total price
     */
    private int calculatePrice (boolean addWhippedCream, boolean addChocolate)
    {
        //price of one cup of coffee
        int basePrice =5;
        //add $1 if the user wants whipped cream
        if (addWhippedCream){
            basePrice+=1;
        }
        //add $2 if the user wants chocolate
        if (addChocolate){
            basePrice+=2;
        }
        //Calculate the total order price by multiplying by quantity
        return  quantity * basePrice;
    }

    /**
     *
     * @param price of the order
     * @param addWhippedCream is whether or not the user wants whipped cream
     * @return text summary
     */
    private String createOrderSummary(int price, boolean addWhippedCream, String name, boolean addChocolate)
    {
        String priceMessage="Name: "+ name;
        priceMessage+="\nAdd whipped cream? "+ addWhippedCream;
        priceMessage+="\nAdd whipped chocolate? "+ addChocolate;
        priceMessage+="\nQuantity: "+ quantity;
        priceMessage+="\nTotal: $"+ price;
        priceMessage+="\nThank you!";

            return  priceMessage;
    }
    public void increment (View view)
    {
        if(quantity==100){
            //show an error message as a toast
            Toast.makeText(this,"You cannot have more than 100 coffees",Toast.LENGTH_SHORT).show();
            //Exit this method early because there's nothing to do
            return;
        }
        quantity=quantity+1;
        displayQuantity ( quantity);
    }
    public void decrement (View view)
    {
        if(quantity==1){
            //show an error message as a toast
            Toast.makeText(this,"You cannot have less than 1 coffee",Toast.LENGTH_SHORT).show();
            //Exit this method early because there's nothing to do
            return;
        }
        quantity=quantity-1;
        displayQuantity ( quantity);
    }
    private void displayQuantity(int number)
    {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(""+number);
    }

 

}
