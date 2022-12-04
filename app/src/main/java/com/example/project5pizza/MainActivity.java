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
    private ArrayList<Pizza> pizzaList;
    private ArrayList<Order> orderList;
    ImageView pizzaOrder;
    ImageView currentOrder;
    ImageView storeOrders;

    private ActivityResultLauncher<Intent> pizzaActivityLauncher  = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result != null && result.getResultCode() == PIZZA_ACTIVITY_RESULT){
                        Intent intent = result.getData();
                        if (intent != null){
                            ArrayList<String> pizzaList = intent.getStringArrayListExtra(PIZZA_ARRAYLIST_IDENTIFIER);
                            temp.setText(pizzaList.get(0));
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