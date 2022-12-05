package com.example.project5pizza;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import pizzaManager.Pizza;
import pizzaManager.Topping;

public class CurrentOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currentorder);

        ListView currentPizza = (ListView)findViewById(R.id.currentPizza);
        Button removePizza = (Button)findViewById(R.id.cancelOrder);

        ArrayList<Pizza> selectedPizza = new ArrayList<Pizza>();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, selectedPizza);
        currentPizza.setAdapter(arrayAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        removePizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CurrentOrderActivity.this, "Adding Topping...", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
