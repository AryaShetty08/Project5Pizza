package com.example.project5pizza;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import pizzaManager.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PizzaOrderingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ListView addToppings;
    ListView selectToppings;
    RadioButton small;
    RadioButton medium;
    RadioButton large;
    TextView crust;
    TextView price;
    Button addToOrder;
    Button add;
    Button remove;

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
        setContentView(R.layout.pizzaordering);

        addToppings = (ListView)findViewById(R.id.addToppings);
        selectToppings = (ListView)findViewById(R.id.selectToppings);

        ArrayList<Topping> toppings = new ArrayList<Topping>();
        for (Topping topping: Topping.values()){
            if (!toppings.contains(topping)){
                toppings.add(topping);
            }
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, toppings);
        addToppings.setAdapter(arrayAdapter);

        ArrayList<Topping> selectedToppings = new ArrayList<Topping>();

        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, selectedToppings);
        selectToppings.setAdapter(arrayAdapter2);

        recyclerView = findViewById(R.id.rvProgram);
        recyclerView.setHasFixedSize(true);
        programAdaptper = new ProgramAdapter(this, programNameList, programDescriptionList, programImages);
        recyclerView.setAdapter(programAdaptper);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        crust = (TextView)findViewById(R.id.crust);

        addToOrder = (Button)findViewById(R.id.cancelOrder);

        addToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                ArrayList<String> pizzaList = new ArrayList<String>();
                pizzaList.add("Hello");
                intent.putStringArrayListExtra(MainActivity.PIZZA_ARRAYLIST_IDENTIFIER, pizzaList);
                setResult(MainActivity.PIZZA_ACTIVITY_RESULT, intent);
            }
        });
    }



}
