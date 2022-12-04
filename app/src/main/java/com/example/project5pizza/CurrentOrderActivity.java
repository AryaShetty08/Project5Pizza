package com.example.project5pizza;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import pizzaManager.Pizza;
import pizzaManager.Topping;

public class CurrentOrderActivity extends AppCompatActivity {

    ListView currentPizza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currentorder);

        currentPizza = (ListView)findViewById(R.id.currentPizza);

        ArrayList<Pizza> selectedPizza = new ArrayList<Pizza>();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, selectedPizza);
        currentPizza.setAdapter(arrayAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}
