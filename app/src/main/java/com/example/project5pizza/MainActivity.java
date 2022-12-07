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

    public static final int PIZZA_ACTIVITY_RESULT = 1;
    public static final String PIZZA_STRING_IDENTIFIER = "Pizza";
    public static final int ORDER_ACTIVITY_RESULT = 2;
    public static final String ORDER_ARRAYLIST_IDENTIFIER = "Order";
    public static final int STORE_ORDER_ACTIVITY_RESULT = 3;
    public static final String STORE_ORDER_ARRAYLIST_IDENTIFIER = "Store Order";

    private ArrayList<Pizza> pizzaList;
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
                            Pizza pizza = Pizza.stringToPizza(intent.getStringExtra(PIZZA_STRING_IDENTIFIER));
                            pizzaList.add(pizza);
                            Toast.makeText(getApplicationContext(), "Added Pizza to Order :)", Toast.LENGTH_LONG).show();
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
        this.pizzaList = new ArrayList<Pizza>();
        this.orderList = new ArrayList<Order>();
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
            @Override
            public void onClick(View view) {
                Intent switchToPizza = new Intent(getApplicationContext(), PizzaOrderingActivity.class);
                pizzaActivityLauncher.launch(switchToPizza);
            }
        });

        currentOrder = (ImageView)findViewById(R.id.currentOrder);
        currentOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchToCurrentOrder = new Intent(getApplicationContext(), CurrentOrderActivity.class);
                startActivity(switchToCurrentOrder);
            }
        });

        storeOrders = (ImageView)findViewById(R.id.storeOrders);
        storeOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchToStoreOrder = new Intent(getApplicationContext(), StoreOrdersActivity.class);
                startActivity(switchToStoreOrder);
            }
        });
    }
}