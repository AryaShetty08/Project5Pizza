package com.example.project5pizza;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

import pizzaManager.Order;
import pizzaManager.Pizza;

/**
 * CurrentOrderActivity class for current order Layout
 * This view lets users place orders for pizzas they made that will be transferred
 * To the Store Order View
 * @author Arya Shetty, John Greaney-Cheng
 */
public class CurrentOrderActivity extends AppCompatActivity {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    ListView pizzaOrder;
    TextView subtotal;
    TextView salestax;
    TextView ordertotal;
    TextView orderNumber;
    Button removePizza;
    Button placeOrder;
    Button clearOrder;
    Order current;
    int pizzaToRemove;
    ArrayAdapter arrayAdapter;
    ArrayList<String> currentOrder;

    /**
     * Private helper method to define UI variables with findViewByID()
     */
    private void setCurrentOrderVariables() {
        pizzaOrder = (ListView)findViewById(R.id.currentPizza);
        removePizza = (Button)findViewById(R.id.cancelOrder);
        subtotal = (TextView)findViewById(R.id.subTotal);
        salestax = (TextView)findViewById(R.id.salesTax);
        ordertotal = (TextView)findViewById(R.id.totalprice);
        placeOrder = (Button)findViewById(R.id.placeOrder);
        clearOrder = (Button)findViewById(R.id.clearOrder);
        orderNumber = (TextView)findViewById(R.id.currentNumber);
    }

    /**
     * Private helper method to get current order data from main and setup prices and order number
     * Also makes adapter for list view
     */
    private void setUpInterface() {
        Intent intent = getIntent();
        currentOrder = intent.getStringArrayListExtra(MainActivity.CURRENT_ORDER_PIZZA_LIST_IDENTIFIER);

        current = new Order(intent.getIntExtra(MainActivity.SERIAL_NUMBER_IDENTIFIER, -1));

        for(String pizza : currentOrder) {
            current.add(Pizza.stringToPizza(pizza));
        }

        updateTextPrices();
        orderNumber.setText("Order Number: " + current.getSerialNumber());

        if(!currentOrder.isEmpty()){
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, currentOrder);
            pizzaOrder.setChoiceMode(pizzaOrder.CHOICE_MODE_SINGLE);
            pizzaOrder.setAdapter(arrayAdapter);
        }
    }

    /**
     * Private helper method to setup listener for remove button
     */
    private void setUpRemoveListener() {
        pizzaOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Updates instance variable to be the index of the user's currently selected pizza
             * @param adapterView default onItemClick parameter
             * @param view default onItemClick parameter
             * @param i index of currently selected pizza
             * @param l default onItemClick parameter
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pizzaToRemove = i;
            }
        });

        removePizza.setOnClickListener(new View.OnClickListener() {
            /**
             * Removes pizza from current order and list view when button is clicked
             * @param view default onClick parameter
             */
            @Override
            public void onClick(View view) {
                if(pizzaToRemove < currentOrder.size()){
                    Pizza removing = Pizza.stringToPizza(currentOrder.get(pizzaToRemove));
                    if (!current.remove(removing)){
                        return;
                    }
                    updateTextPrices();
                    removeOnePizza(pizzaToRemove);
                    Toast.makeText(CurrentOrderActivity.this, "Removing Pizza...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Private helper method to setup listener for clear button
     */
    private void setUpClearListener(){
        clearOrder.setOnClickListener(new View.OnClickListener() {
            /**
             * Empties current order and list view when button is clicked
             * @param view default onClick parameter
             */
            @Override
            public void onClick(View view) {
                currentOrder.clear();
                current = new Order(current.getSerialNumber());
                pizzaOrder.setAdapter(null);
                updateTextPrices();
                Toast.makeText(CurrentOrderActivity.this, "Removing All Pizzas...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Private helper method to setup listener for place order button
     */
    private void setUpPlaceOrderListener() {
        placeOrder.setOnClickListener(new View.OnClickListener() {
            /**
             * Places an Order and returns user to Main Activity
             * Sends Order data back to main to be
             * collected as an order in the Store Order
             * @param view default onClick parameter
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                if (current.getPizzaList().isEmpty()){
                    ArrayList<Pizza> temp = current.getPizzaList();
                    ArrayList<String> pizzaList = new ArrayList<String>();
                    for (Pizza pizza: temp){
                        pizzaList.add(pizza.toString());
                    }
                    intent.putStringArrayListExtra(MainActivity.ORDER_ARRAYLIST_IDENTIFIER, pizzaList);
                    setResult(MainActivity.ORDER_ACTIVITY_BACK_BUTTON, intent);
                    finish();
                }
                ArrayList<Pizza> temp = current.getPizzaList();
                ArrayList<String> pizzaList = new ArrayList<String>();
                for (Pizza pizza: temp){
                    pizzaList.add(pizza.toString());
                }
                intent.putStringArrayListExtra(MainActivity.ORDER_ARRAYLIST_IDENTIFIER, pizzaList);
                intent.putExtra(MainActivity.ORDER_NUMBER_IDENTIFIER, current.getSerialNumber());
                setResult(MainActivity.ORDER_ACTIVITY_RESULT, intent);
                finish();
            }
        });
    }

    /**
     * Initializes Current Order Activity
     * Sets up UI Variables, pulls current order data from main activity and button listeners
     * @param savedInstanceState can be used to reinitialize previous save states
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currentorder);

        setCurrentOrderVariables();
        setUpInterface();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpRemoveListener();
        setUpClearListener();
        setUpPlaceOrderListener();

    }

    /**
     * Private helper method to update text prices
     */
    private void updateTextPrices() {
        subtotal.setText("Subtotal $" + df.format(current.getSubtotal()));
        salestax.setText("Sales Tax $" + df.format(current.getTax()));
        ordertotal.setText("Order Total $" + df.format(current.getTotal()));
    }

    /**
     * Private helper method to remove pizza from adapter (for list view)
     * @param position index of the pizza to remove in the adapter
     */
    private void removeOnePizza(int position) {
        arrayAdapter.remove(arrayAdapter.getItem(position));
        arrayAdapter.notifyDataSetChanged();
    }

    /**
     * Overrides the back button so that data in main is updated based on changes in this activity
     * For when clicking the back button to exit the current order view instead of
     * clicking the place order button
     * @param item, default parameter needed for method
     * @return true if button returns to main and is executed properly
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                ArrayList<Pizza> temp = current.getPizzaList();
                ArrayList<String> pizzaList = new ArrayList<String>();
                for (Pizza pizza: temp){
                    pizzaList.add(pizza.toString());
                }
                intent.putStringArrayListExtra(MainActivity.ORDER_ARRAYLIST_IDENTIFIER, pizzaList);
                setResult(MainActivity.ORDER_ACTIVITY_BACK_BUTTON, intent);
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
