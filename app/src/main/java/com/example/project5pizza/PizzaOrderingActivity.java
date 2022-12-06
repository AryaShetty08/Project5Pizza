package com.example.project5pizza;

import static pizzaManager.Constant.*;
import static pizzaManager.Topping.*;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import pizzaManager.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PizzaOrderingActivity extends AppCompatActivity {

    private static final DecimalFormat df = new DecimalFormat("0.00");
    Double pizzaPrice = 0.0;
    private final PizzaFactory nypizza = new NYPizza();
    private final PizzaFactory chicpizza = new ChicagoPizza();
    private Pizza orderInProgress = new Deluxe(chicpizza);
    ListView addToppings;
    TextView crust;
    RecyclerView recyclerView;
    Button addToOrder;
    RadioButton small;
    RadioButton medium;
    RadioButton large;
    TextView price;
    ArrayList<Topping> selectedToppings = new ArrayList<Topping>();

    RecyclerView.Adapter programAdaptper;
    RecyclerView.LayoutManager layoutmanager;
    String[] programNameList = {"Chicago BYO", "Chicago Deluxe", "Chicago Meatzza", "Chicago BBQ Chicken",
            "NY BYO", "NY Deluxe", "NY Meatzza", "NY BBQ Chicken"};
    String[] programDescriptionList = {"Build Your Own", "So many toppings!", "So much meat!", "Contains Chicken!",
            "Build Your Own", "So many toppings!", "So much meat!", "Contains Chicken!"};
    int[] programImages = {R.drawable.chicagopizzaimagebuildyourown, R.drawable.deluxepizzachicago, R.drawable.meatzzachicago,
            R.drawable.bbqchickenchicago, R.drawable.newyorkbuildyourown, R.drawable.deluxepizzanewyork, R.drawable.meatzzanewyork,
            R.drawable.bbqchickennewyork};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pizzaordering);

        addToppings = (ListView)findViewById(R.id.addToppings);
        crust = (TextView)findViewById(R.id.crust);
        recyclerView = findViewById(R.id.rvProgram);
        addToOrder = (Button)findViewById(R.id.cancelOrder);
        small = (RadioButton)findViewById(R.id.small);
        medium = (RadioButton)findViewById(R.id.medium);
        large = (RadioButton)findViewById(R.id.large);
        price = (TextView)findViewById(R.id.price);
        price.setText("Pizza Price $:" + (String.valueOf(orderInProgress.price())));


        small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  orderInProgress.setSize(Size.SMALL);
                  //price.setText("Pizza Price $:" + (String.valueOf(orderInProgress.price())));
                price.setText("Pizza Price $:" + (String.valueOf(orderInProgress.price())));
            }
        });

        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderInProgress.setSize(Size.MEDIUM);
                price.setText("Pizza Price $:" + (String.valueOf(orderInProgress.price())));
            }
        });

        large.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderInProgress.setSize(Size.LARGE);
                price.setText("Pizza Price $:" + (String.valueOf(orderInProgress.price())));
            }
        });

        ArrayList<Topping> toppings = new ArrayList<Topping>();
        for (Topping topping: Topping.values()){
            if (!toppings.contains(topping)){
                toppings.add(topping);
            }
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, toppings);
        addToppings.setChoiceMode(addToppings.CHOICE_MODE_MULTIPLE);
        addToppings.setAdapter(arrayAdapter);

        addToppings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(orderInProgress.getToppings().contains((Topping) adapterView.getItemAtPosition(i))) {
                    selectedToppings.add((Topping) adapterView.getItemAtPosition(i));
                    orderInProgress.add(selectedToppings.get(i));
                    price.setText("Pizza Price $:" + (String.valueOf(orderInProgress.price())));
                }
                else{
                    selectedToppings.remove((Topping) adapterView.getItemAtPosition(i));
                    orderInProgress.remove(selectedToppings.get(i));
                    price.setText("Pizza Price $:" + (String.valueOf(orderInProgress.price())));
                }
            }
        });


        programAdaptper = new ProgramAdapter(this, programNameList, programDescriptionList, programImages, new ProgramAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String s){
                switch (s){
                    case "Chicago Deluxe":
                        Pizza test = new Deluxe(chicpizza);
                        initializePizza(test);
                    case "Chicago BBQ Chicken":
                        initializePizza(chicpizza.createBBQChicken());
                    case "Chicago Meatzza":
                        initializePizza(chicpizza.createMeatzza());
                    case "Chicago BYO":
                        initializePizzaBYO(chicpizza.createBuildYourOwn());
                    case "NY Deluxe":
                        initializePizza(nypizza.createDeluxe());
                    case "NY BBQ Chicken":
                        initializePizza(nypizza.createBBQChicken());
                    case "NY Meatzza":
                        initializePizza(nypizza.createMeatzza());
                    case "NY BYO":
                        initializePizzaBYO(nypizza.createBuildYourOwn());
                }
                Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(programAdaptper);
        programAdaptper.getItemCount();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog("Check Current Order!");
                Intent intent = new Intent();
                ArrayList<String> pizzaList = new ArrayList<String>();
                pizzaList.add("Hello");
                intent.putStringArrayListExtra(MainActivity.PIZZA_ARRAYLIST_IDENTIFIER, pizzaList);
                setResult(MainActivity.PIZZA_ACTIVITY_RESULT, intent);
            }
        });
    }

    private void initializePizzaBYO(Pizza pizza) {
        orderInProgress = pizza;
        String c = "Crust:" + orderInProgress.getCrust().name();
        crust.setText(c);
        if(small.isChecked()) {
            orderInProgress.setSize(Size.SMALL);
        }
        else if(medium.isChecked()){
            orderInProgress.setSize(Size.MEDIUM);
        }
        else if(large.isChecked()){
            orderInProgress.setSize(Size.LARGE);
        }

        addToppings.getChildAt(0).setEnabled(true);
        addToppings.getChildAt(1).setEnabled(true);
        addToppings.getChildAt(2).setEnabled(true);
        addToppings.getChildAt(3).setEnabled(true);
        //addToppings.setEnabled(true);
        //dont know why crust is not being updated
        //orderInProgress.add(SAUSAGE);
        //orderInProgress.add(PEPPERONI);

        price.setText("Pizza Price $:" + (String.valueOf(orderInProgress.price())));
    }

    private void initializePizza(Pizza pizza) {
        orderInProgress = pizza;
        String c = "Crust:" + orderInProgress.getCrust().name();
        crust.setText(c);
        if(small.isChecked()) {
            orderInProgress.setSize(Size.SMALL);
        }
        else if(medium.isChecked()){
            orderInProgress.setSize(Size.MEDIUM);
        }
        else if(large.isChecked()){
            orderInProgress.setSize(Size.LARGE);
        }
        crust.setText("Crust:" + (String)(orderInProgress.getCrust().toString()));
        selectedToppings.addAll(orderInProgress.getToppings());
        //orderInProgress.add(SAUSAGE);
        //orderInProgress.add(PEPPERONI);



        addToppings.setItemChecked(1, true);

        addToppings.getChildAt(0).setEnabled(false);
        addToppings.getChildAt(1).setEnabled(false);
        addToppings.getChildAt(2).setEnabled(false);
        addToppings.getChildAt(3).setEnabled(false);

        //addToppings.getChildAt(1).setContextClickable(false);
        //addToppings.setEnabled(false);

        price.setText("Pizza Price $:" + (String.valueOf(orderInProgress.price())));
    }

    private void showAlertDialog(String message){
        AlertDialog dialog = new AlertDialog.Builder(PizzaOrderingActivity.this)
                .setTitle("Added Pizza!")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();
        dialog.show();
    }

}
