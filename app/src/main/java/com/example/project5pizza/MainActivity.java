package com.example.project5pizza;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pizzaManager.*;

/**
 * Activity class for Activity Main Layout
 * Hub that can access all other activities
 * Responsible for acting as a bridge to transport data amongst activities
 * @author Arya Shetty, John Greaney-Cheng
 */
public class MainActivity extends AppCompatActivity {

    public static final int FAILED_RESULT = -1;
    public static final int PIZZA_ACTIVITY_RESULT = 1;
    public static final String PIZZA_STRING_IDENTIFIER = "Pizza";
    public static final String PIZZA_LIST_IDENTIFIER = "Pizza List";
    public static final String SERIAL_NUMBER_IDENTIFIER = "Serial Code";
    public static final int ORDER_ACTIVITY_RESULT = 2;
    public static final String ORDER_ARRAYLIST_IDENTIFIER = "Order";
    public static final String ORDER_NUMBER_IDENTIFIER = "Order Number";
    public static final int ORDER_ACTIVITY_BACK_BUTTON = 3;
    public static final int STORE_ORDER_ACTIVITY_RESULT = 4;
    public static final String STORE_ORDER_ARRAYLIST_IDENTIFIER = "Store Order";

    private ArrayList<String> pizzaList;
    private int currentSerialNumber;
    private ArrayList<Order> orderList;

    private ImageView pizzaOrder;
    private ImageView currentOrder;
    private ImageView storeOrders;

    ActivityResultLauncher<Intent> pizzaActivityLauncher  = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                /**
                 * Stores Pizzas ordered in PizzaOrderingActivity in an ArrayList
                 * ArrayList will be used when Current Order Activity is launched
                 * to help place orders
                 * @param result Data from PizzaOrderingActivity (contains Pizzas that were ordered)
                 */
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result != null && result.getResultCode() == PIZZA_ACTIVITY_RESULT){
                        Intent intent = result.getData();
                        if (intent != null){
                            String pizza = intent.getStringExtra(PIZZA_STRING_IDENTIFIER);
                            pizzaList.add(pizza);
                            Toast.makeText(getApplicationContext(), "Added Pizza to Order :)", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });

    ActivityResultLauncher<Intent> currentOrderActivityLauncher  = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                /**
                 * Stores orders placed in CurrentOrderActivity in an ArrayList
                 * ArrayList will be used when Store Order Activity is launched
                 * to help keep track of all the orders placed
                 * @param result Data from CurrentOrderActivity (contains orders that were placed)
                 */
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result != null) {
                        if (result.getResultCode() == ORDER_ACTIVITY_RESULT) {
                            Intent intent = result.getData();
                            if (intent != null) {
                                ArrayList<String> stringList = intent.getStringArrayListExtra(ORDER_ARRAYLIST_IDENTIFIER);
                                Order temp = new Order(intent.getIntExtra(ORDER_NUMBER_IDENTIFIER, -1));
                                for (String pizza : stringList) {
                                    temp.add(Pizza.stringToPizza(pizza));
                                }
                                orderList.add(temp);
                                currentSerialNumber++;
                                pizzaList.clear();
                                Toast.makeText(getApplicationContext(), "Added Order :)", Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (result.getResultCode() == ORDER_ACTIVITY_BACK_BUTTON) {
                            Intent intent = result.getData();
                            if (intent != null) {
                                pizzaList = intent.getStringArrayListExtra(ORDER_ARRAYLIST_IDENTIFIER);
                            }
                        }
                    }
                }
            });

    ActivityResultLauncher<Intent> storeOrderActivityLauncher  = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                /**
                 * Stores List of Orders in StoreOrdersActivity in an ArrayList
                 * ArrayList will be used when StoreOrdersActivity is launched and relaunched
                 * to help save orders that were made
                 * @param result Data from StoreOrdersActivity (contains orders that were placed)
                 */
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result != null && result.getResultCode() == STORE_ORDER_ACTIVITY_RESULT){
                        Intent intent = result.getData();
                        if (intent != null){
                            int size = intent.getIntExtra("Size", -1);
                            for (int i = 0; i < size; i++){
                                Order order = new Order(intent.getIntExtra(String.valueOf(-i-1), -1));
                                ArrayList<String> temp = intent.getStringArrayListExtra(String.valueOf(i+1));
                                for (String str: temp){
                                    order.add(Pizza.stringToPizza(str));
                                }
                                orderList.add(order);
                            }
                        }
                    }
                }
            });

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.pizzaList = new ArrayList<String>();
        this.orderList = new ArrayList<Order>();
        this.currentSerialNumber = 1;
        setImageViews();
    }

    /**
     * Private helper method to initialize Image Views
     * Also sets up listeners to move data to and from
     * other activities when launching them
     */
    private void setImageViews(){
        pizzaOrder = (ImageView)findViewById(R.id.pizzaOrder);
        pizzaOrder.setOnClickListener(new View.OnClickListener() {
            /**
             * On Click method to switch activities when a button is clicked
             * This method switches to pizza ordering activity
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent switchToPizza = new Intent(getApplicationContext(), PizzaOrderingActivity.class);
                pizzaActivityLauncher.launch(switchToPizza);
            }
        });

        currentOrder = (ImageView)findViewById(R.id.currentOrder);
        currentOrder.setOnClickListener(new View.OnClickListener() {
            /**
             * On Click method to switch activities when a button is clicked
             * This method switches to current order activity
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent switchToCurrentOrder = new Intent(getApplicationContext(), CurrentOrderActivity.class);
                switchToCurrentOrder.putStringArrayListExtra(PIZZA_LIST_IDENTIFIER, pizzaList);
                switchToCurrentOrder.putExtra(SERIAL_NUMBER_IDENTIFIER, currentSerialNumber);
                currentOrderActivityLauncher.launch(switchToCurrentOrder);
            }
        });

        storeOrders = (ImageView)findViewById(R.id.storeOrders);
        storeOrders.setOnClickListener(new View.OnClickListener() {
            /**
             * On Click method to switch activities when a button is clicked
             * This method switches to store order activity
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent switchToStoreOrder = new Intent(getApplicationContext(), StoreOrdersActivity.class);
                for (int i = 0; i < orderList.size(); i++){
                    ArrayList<Pizza> tempPizzaList = orderList.get(i).getPizzaList();
                    ArrayList<String> toStoreOrder = new ArrayList<String>();
                    for (Pizza pizza: tempPizzaList){
                        toStoreOrder.add(pizza.toString());
                    }
                    switchToStoreOrder.putStringArrayListExtra(String.valueOf(i+1), toStoreOrder);
                    switchToStoreOrder.putExtra(String.valueOf(-i-1), orderList.get(i).getSerialNumber());
                }
                switchToStoreOrder.putExtra("Size", orderList.size());
                storeOrderActivityLauncher.launch(switchToStoreOrder);
            }
        });
    }
}