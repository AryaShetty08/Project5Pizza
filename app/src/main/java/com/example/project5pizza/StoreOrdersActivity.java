package com.example.project5pizza;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.time.chrono.MinguoChronology;
import java.util.ArrayList;

import pizzaManager.Order;
import pizzaManager.Pizza;
import pizzaManager.StoreOrder;

/**
 * StoreOrdersActivity class for store orders Layout
 * This activity displays all orders made on the app at that time it was launched
 * Users can cancel orders and go through each one they have made
 * @author Arya Shetty, John Greaney-Cheng
 */
public class StoreOrdersActivity extends AppCompatActivity {

    ListView pizzaOrders;
    Button cancelOrder;
    TextView total;
    int orderPosition;
    StoreOrder temp;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    StoreOrder storeOrder;
    Spinner spinner;
    ArrayAdapter arrayAdapterListView;
    ArrayAdapter arrayAdapterSpinner;

    /**
     * This method initializes variables that are on store orders interface
     * For example the list view that displays the order, the cancel button,
     * And the order total for the pizza list displayed
     */
    private void initializeStoreVariables() {
        pizzaOrders = (ListView)findViewById(R.id.pizzaOrders);
        cancelOrder = (Button)findViewById(R.id.cancelOrder);
        total = (TextView)findViewById(R.id.total);
    }

    /**
     * This method unpacks the intent that was sent from current order
     * To main activity and then to Store Orders activity
     * Since the intent was an arraylist for the order and an integer for the number of orders
     * The code below transfers that data into a store order variable that holds all the orders
     */
    private void unpackIntent() {
        storeOrder = new StoreOrder();
        Intent intent = getIntent();
        int size = intent.getIntExtra(MainActivity.NUMBER_OF_ORDERS, -1);
        for (int i = 0; i < size; i++){
            Order order = new Order(intent.getIntExtra(String.valueOf(-i-1), -1));
            ArrayList<String> temp = intent.getStringArrayListExtra(String.valueOf(i+1));
            for (String str: temp){
                order.add(Pizza.stringToPizza(str));
            }
            storeOrder.add(order);
        }
    }

    /**
     * This method sets up the listview and spinner listeners
     * For the store orders activity
     * It makes sure the listview shows the pizzas from the order selected by the spinner
     * And the spinner holds all the order numbers that are part of the store order variable
     * That was created earlier
     */
    private void setUpListAndSpinner() {
        ArrayList<String> pizzaInOrder = new ArrayList<>();
        if (!storeOrder.getOrderList().isEmpty()){
            for(Pizza pizza : storeOrder.getOrderList().get(0).getPizzaList()) {
                pizzaInOrder.add(pizza.toString());
            }
            total.setText("Order Total $" + df.format(storeOrder.getOrderList().get(0).getTotal()));
        }
        else {
            total.setText("Order Total $");
        }
        arrayAdapterListView = new ArrayAdapter(this, android.R.layout.simple_list_item_1, pizzaInOrder);
        pizzaOrders.setAdapter(arrayAdapterListView);
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayList<Integer> orderNumbers = new ArrayList<>();
        for(Order order : storeOrder.getOrderList()) {
            orderNumbers.add(order.getSerialNumber());
        }
        arrayAdapterSpinner = new ArrayAdapter(this, android.R.layout.simple_spinner_item, orderNumbers);
        arrayAdapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapterSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * When item is being clicked in the spinner
             * The listview is updated based on the order number selected
             * And the price is also updated
             * The order is received by the integer i, which is the position in the spinner
             * @param adapterView, default onItemClick parameter
             * @param view, default onItemClick parameter
             * @param i, the position the user clicked on in listview
             * @param l, default onItemClick parameter
             */
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                orderPosition = i;
                pizzaInOrder.clear();
                for(Pizza pizza : storeOrder.getOrderList().get(i).getPizzaList()) {
                    pizzaInOrder.add(pizza.toString());
                }
                switchOrderList(pizzaInOrder);
                total.setText("Order Total $" + df.format(storeOrder.getOrderList().get(i).getTotal()));
            }
            /**
             * Mandatory method for onItemSelected in spinner class
             * @param adapterView, default onNothingSelected parameter
             */
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
    }

    /**
     * This method sets up the cancel order button and listener
     * When the user clicks on cancel and nothing is displayed nothing happens
     * If there is only one order in store, then it just clears all the variables present
     * If there are multiple orders then the selected order in spinner is cleared,
     * And the corresponding listview and price, and the display data shown should be
     * The first order in spinner
     */
    private void setUpCancelOrder() {
        cancelOrder.setOnClickListener(new View.OnClickListener() {
            /**
             * When cancel button is clicked the display is changed
             * The listview and price is cleared and the spinner will go to
             * The first order, otherwise the screen will not display data order
             * @param view, default onClick parameter
             */
            @Override
            public void onClick(View view) {
                if (storeOrder.getOrderList().isEmpty()){
                    return;
                }
                if(storeOrder.getOrderList().size() == 1) {
                    storeOrder.remove(storeOrder.getOrderList().get(0));
                    pizzaOrders.setAdapter(null);
                    total.setText("Order Total $");
                    spinner.setAdapter(null);
                }
                else {
                    storeOrder.remove(storeOrder.getOrderList().get(orderPosition));
                    arrayAdapterListView.notifyDataSetChanged();
                    arrayAdapterSpinner.notifyDataSetChanged();
                    spinner.setAdapter(setSpinnerAdapter());
                    spinner.setSelection(0);
                }
                showAlertDialog("Order More Pizza!");
            }
        });
    }

    /**
     * Starts when the activity is launched and on screen
     * It calls the methods above to initialize variables
     * And makes sure users can see orders that were created
     * @param savedInstanceState, can be used to reinitialize previous saved states
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storeorders);

        initializeStoreVariables();
        unpackIntent();
        setUpListAndSpinner();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpListAndSpinner();
        setUpCancelOrder();

    }

    /**
     * This method updates the array adapter for the listview with the new pizzas to display
     * @param pizzaInOrder, arraylist containing strings of all pizzas in specific order
     */
    private void switchOrderList(ArrayList<String> pizzaInOrder){
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, pizzaInOrder);
        pizzaOrders.setAdapter(arrayAdapter2);
    }

    /**
     * This method creates a new adapter for spinner when the store order order list is updated
     * @return Array adapter that is refreshed whenever orders are added or deleted
     */
    private ArrayAdapter setSpinnerAdapter() {
        ArrayList<Integer> orderNumbers = new ArrayList<>();
        for(Order order : storeOrder.getOrderList()) {
            orderNumbers.add(order.getSerialNumber());
        }
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, orderNumbers);
        return arrayAdapter2;
    }

    /**
     * This method is called when an alert dialog needs to be popped up
     * This is used when the order is cleared in this activity
     * @param message, a string created in the clear method to show the user job is done
     */
    private void showAlertDialog(String message){
        AlertDialog dialog = new AlertDialog.Builder(StoreOrdersActivity.this)
                .setTitle("Deleted Order!")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    /**
                     * When OK button is clicked on dialog, alert disappears
                     * @param dialogInterface, default onClick parameter
                     * @param i, default onClick parameter
                     */
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();
        dialog.show();
    }

    /**
     * This method overrides the back button click so that data is still preserved
     * When exiting the store orders view the deleted and kept orders will remain the same
     * @param item, default parameter needed for method
     * @return true if button returns to main and is executed properly
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = getIntent();
                for (int i = 0; i < storeOrder.getOrderList().size(); i++){
                    Order tempOrder = storeOrder.getOrderList().get(i);
                    ArrayList<Pizza> tempMain = tempOrder.getPizzaList();
                    ArrayList<String> toMain = new ArrayList<>();
                    for (Pizza pizza: tempMain){
                        toMain.add(pizza.toString());
                    }
                    intent.putStringArrayListExtra(String.valueOf(i+1), toMain);
                    intent.putExtra(String.valueOf(-i-1), tempOrder.getSerialNumber());
                }
                intent.putExtra(MainActivity.NUMBER_OF_ORDERS, storeOrder.getOrderList().size());
                setResult(MainActivity.STORE_ORDER_ACTIVITY_RESULT, intent);
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
