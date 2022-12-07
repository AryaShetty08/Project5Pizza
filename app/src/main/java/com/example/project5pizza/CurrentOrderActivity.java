package com.example.project5pizza;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import pizzaManager.Pizza;
import pizzaManager.Topping;

public class CurrentOrderActivity extends AppCompatActivity {
    private ArrayList<Pizza> currentOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currentorder);

//        ListView currentPizza = (ListView)findViewById(R.id.currentPizza);
//        Button removePizza = (Button)findViewById(R.id.cancelOrder);
//
//        Intent intent = getIntent();
//        ArrayList<String> tempList = intent.getStringArrayListExtra(MainActivity.PIZZA_LIST_IDENTIFIER);
//        currentOrder = new ArrayList<Pizza>();
//        for (String s: tempList){
//            currentOrder.add(Pizza.stringToPizza(s));
//        }
//
//        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, selectedPizza);
//        currentPizza.setAdapter(arrayAdapter);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        removePizza.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(CurrentOrderActivity.this, "Adding Topping...", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

}
