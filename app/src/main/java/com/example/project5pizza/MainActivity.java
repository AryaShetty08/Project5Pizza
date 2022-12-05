package com.example.project5pizza;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pizzaManager.*;

public class MainActivity extends AppCompatActivity {

    public static final int PIZZA_ACTIVITY_RESULT = 1;
    public static final String PIZZA_ARRAYLIST_IDENTIFIER = "Pizza";
    public static final int ORDER_ACTIVITY_RESULT = 2;
    public static final String ORDER_ARRAYLIST_IDENTIFIER = "Order";
    public static final int STORE_ORDER_ACTIVITY_RESULT = 3;
    public static final String STORE_ORDER_ARRAYLIST_IDENTIFIER = "Store Order";

    private ArrayList<Pizza> pizzaList;
    private ArrayList<Order> orderList;
    ImageView pizzaOrder;
    ImageView currentOrder;
    ImageView storeOrders;

    ActivityResultLauncher<Intent> pizzaActivityLauncher  = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result != null && result.getResultCode() == PIZZA_ACTIVITY_RESULT){
                        Intent intent = result.getData();
                        if (intent != null){
                            ArrayList<String> temp = intent.getStringArrayListExtra(PIZZA_ARRAYLIST_IDENTIFIER);
                            for (String str: temp){
                                Pizza pizza = Pizza.stringToPizza(str);
                                pizzaList.add(pizza);
                            }
                        }
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.pizzaList = new ArrayList<Pizza>();
        this.orderList = new ArrayList<Order>();
        setImageViews();
    }

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