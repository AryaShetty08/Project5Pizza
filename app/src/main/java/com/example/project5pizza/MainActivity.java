package com.example.project5pizza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import pizzaManager.Pizza;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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