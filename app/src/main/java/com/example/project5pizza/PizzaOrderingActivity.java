package com.example.project5pizza;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import pizzaManager.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class PizzaOrderingActivity extends AppCompatActivity {

    private static final DecimalFormat df = new DecimalFormat("0.00");
    private Double pizzaPrice = 0.0;
    private final PizzaFactory nypizza = new NYPizza();
    private final PizzaFactory chicpizza = new ChicagoPizza();
    private Pizza orderInProgress = chicpizza.createBuildYourOwn();
    private ListView addToppings;
    private TextView crust;
    private RecyclerView recyclerView;
    Button addToOrder;
    private RadioButton small;
    private RadioButton medium;
    private RadioButton large;
    private TextView price;
    private ArrayList<Topping> toppings = new ArrayList<Topping>();

    private RecyclerView.Adapter programAdaptper;
    RecyclerView.LayoutManager layoutmanager;
    private final String[] programNameList = {"Chicago BYO", "Chicago Deluxe", "Chicago Meatzza", "Chicago BBQ Chicken",
            "NY BYO", "NY Deluxe", "NY Meatzza", "NY BBQ Chicken"};
    private final String[] programDescriptionList = {"Build Your Own", "So many toppings!", "So much meat!", "Contains Chicken!",
            "Build Your Own", "So many toppings!", "So much meat!", "Contains Chicken!"};
    private final int[] programImages = {R.drawable.chicagopizzaimagebuildyourown, R.drawable.deluxepizzachicago, R.drawable.meatzzachicago,
            R.drawable.bbqchickenchicago, R.drawable.newyorkbuildyourown, R.drawable.deluxepizzanewyork, R.drawable.meatzzanewyork,
            R.drawable.bbqchickennewyork};

    private void initializeToppingsList() {
        toppings.addAll(Arrays.asList(Topping.values()));
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, toppings);
        addToppings.setChoiceMode(addToppings.CHOICE_MODE_MULTIPLE);
        addToppings.setAdapter(arrayAdapter);
    }

    private void setPizzaSize(Size size) {
        if(size.equals(Size.SMALL)) {
            orderInProgress.setSize(Size.SMALL);
        }
        if(size.equals(Size.MEDIUM)) {
            orderInProgress.setSize(Size.MEDIUM);
        }
        if(size.equals(Size.LARGE)) {
            orderInProgress.setSize(Size.LARGE);
        }
        price.setText("Pizza Price $:" + (String.valueOf(orderInProgress.price())));
    }

    private void setSizeListener() {
        small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPizzaSize(Size.SMALL);
            }
        });
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPizzaSize(Size.MEDIUM);
            }
        });
        large.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPizzaSize(Size.LARGE);
            }
        });
    }

    private void initializeVariables() {
        addToppings = (ListView)findViewById(R.id.addToppings);
        crust = (TextView)findViewById(R.id.crust);
        recyclerView = findViewById(R.id.rvProgram);
        addToOrder = (Button)findViewById(R.id.cancelOrder);
        small = (RadioButton)findViewById(R.id.small);
        medium = (RadioButton)findViewById(R.id.medium);
        large = (RadioButton)findViewById(R.id.large);
        price = (TextView)findViewById(R.id.price);
    }

    private void addToppingListener() {
        addToppings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!(orderInProgress instanceof BuildYourOwn)){
                    Boolean supposedToBeOnPizza = orderInProgress.getToppings().contains(toppings.get(i));
                    addToppings.setItemChecked(i, supposedToBeOnPizza);
                    return;
                }
                else if (!addToppings.isItemChecked(i)){
                    orderInProgress.remove(toppings.get(i));
                    price.setText("Pizza Price $:" + (String.valueOf(orderInProgress.price())));
                }
                else if (orderInProgress.add(toppings.get(i))){
                    price.setText("Pizza Price $:" + (String.valueOf(orderInProgress.price())));
                }
                else {
                    addToppings.setItemChecked(i, false);
                }
            }
        });
    }

    private void setPizzaChoiceListener() {
        programAdaptper = new ProgramAdapter(this, programNameList, programDescriptionList, programImages, new ProgramAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String s){
                switch (s){
                    case "Chicago Deluxe":
                        Pizza pizza = chicpizza.createDeluxe();
                        initializePizza(pizza);
                        break;
                    case "Chicago BBQ Chicken":
                        initializePizza(chicpizza.createBBQChicken());
                        break;
                    case "Chicago Meatzza":
                        initializePizza(chicpizza.createMeatzza());
                        break;
                    case "Chicago BYO":
                        initializePizzaBYO(chicpizza.createBuildYourOwn());
                        break;
                    case "NY Deluxe":
                        initializePizza(nypizza.createDeluxe());
                        break;
                    case "NY BBQ Chicken":
                        initializePizza(nypizza.createBBQChicken());
                        break;
                    case "NY Meatzza":
                        initializePizza(nypizza.createMeatzza());
                        break;
                    case "NY BYO":
                        initializePizzaBYO(nypizza.createBuildYourOwn());
                }
            }
        });
    }

    private void setRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(programAdaptper);
        programAdaptper.getItemCount();
    }

    private void initializeFirstPizza(){
        small.setChecked(true);
        orderInProgress = chicpizza.createBuildYourOwn();
        orderInProgress.setSize(Size.SMALL);
        String c = "Crust:" + orderInProgress.getCrust().name();
        crust.setText(c);
        price.setText("Pizza Price $:" + (String.valueOf(orderInProgress.price())));
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
        for(int i = 0; i < toppings.size(); i++){
            addToppings.setItemChecked(i, false);
            if(i < 4) {
                if(!addToppings.getChildAt(i).isEnabled())
                {
                    addToppings.getChildAt(i).setEnabled(true);
                }
            }
        }
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
        for(int i = 0; i < toppings.size(); i++){
            addToppings.setItemChecked(i, false);
            if(i < 4) {
                if(addToppings.getChildAt(i).isEnabled())
                {
                    addToppings.getChildAt(i).setEnabled(false);
                }
            }
        }
        for(Topping topping: orderInProgress.getToppings()){
            addToppings.setItemChecked(topping.getOrder(), true);
        }
        price.setText("Pizza Price $:" + (String.valueOf(orderInProgress.price())));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pizzaordering);

        initializeVariables();
        setSizeListener();
        initializeToppingsList();
        addToppingListener();
        setPizzaChoiceListener();
        setRecyclerView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog("Check Current Order!");
                Intent intent = new Intent();
                intent.putExtra(MainActivity.PIZZA_STRING_IDENTIFIER, orderInProgress.toString());
                setResult(MainActivity.PIZZA_ACTIVITY_RESULT, intent);
                finish();
            }
        });

        initializeFirstPizza();
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
