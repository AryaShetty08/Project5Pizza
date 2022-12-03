package com.example.project5pizza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import pizzaManager.Pizza;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter programAdaptper;
    RecyclerView.LayoutManager layoutmanager;
    String[] programNameList = {"Chicago BYO", "Chicago Deluxe", "Chicago Meatzza", "Chicago BBQ Chicken",
            "NY BYO", "NY Deluxe", "NY Meatzza", "NY BBQ Chicken"};
    String[] programDescriptionList = {"Chicago BYO", "Chicago Deluxe", "Chicago Meatzza", "Chicago BBQ Chicken",
            "NY BYO", "NY Deluxe", "NY Meatzza", "NY BBQ Chicken"};
    int[] programImages = {R.drawable.chicagopizzaimagebuildyourown, R.drawable.deluxepizzachicago, R.drawable.meatzzachicago,
    R.drawable.bbqchickenchicago, R.drawable.newyorkbuildyourown, R.drawable.deluxepizzanewyork, R.drawable.meatzzanewyork,
    R.drawable.bbqchickennewyork};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvProgram);
        recyclerView.setHasFixedSize(true);
        layoutmanager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutmanager);
        programAdaptper = new ProgramAdapter(this, programNameList, programDescriptionList, programImages);
        recyclerView.setAdapter(programAdaptper);

        ArrayList<Pizza> a = new ArrayList<Pizza>();

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(a.get(0));
            }
        });

        ImageView pizzaOrder = (ImageView)findViewById(R.id.pizzaOrder);
        pizzaOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchToPizza = new Intent(getApplicationContext(), PizzaOrderingActivity.class);
                startActivity(switchToPizza);
            }
        });

        ImageView currentOrder = (ImageView)findViewById(R.id.currentOrder);
        currentOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchtoCurrent = new Intent(getApplicationContext(), CurrentOrderActivity.class);
                startActivity(switchtoCurrent);
            }
        });

        ImageView storeOrders = (ImageView)findViewById(R.id.storeOrders);
        storeOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchtoStore = new Intent(getApplicationContext(), StoreOrdersActivity.class);
                switchtoStore.putParcelableArrayListExtra("PIZZA_ARRAYLIST", a);
                startActivity(switchtoStore);
            }
        });

    }


}