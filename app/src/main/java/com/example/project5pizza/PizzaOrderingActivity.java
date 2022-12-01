package com.example.project5pizza;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pizzaManager.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PizzaOrderingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pizzaordering);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView crust = (TextView)findViewById(R.id.crust);

        Button addToOrder = (Button)findViewById(R.id.addToOrder);
        Intent intent = getIntent();
        ArrayList<Pizza> pizzaList = intent.getParcelableArrayListExtra("PIZZA_ARRAYLIST");

        addToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crust.setText("hello");
                pizzaList.add(new Deluxe(new NYPizza()));
            }
        });
    }

}
