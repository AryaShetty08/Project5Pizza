package com.example.project5pizza;

import static pizzaManager.Constant.*;

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

        ListView addToppings = (ListView)findViewById(R.id.addToppings);
        TextView crust = (TextView)findViewById(R.id.crust);
        RecyclerView recyclerView = findViewById(R.id.rvProgram);
        Button addToOrder = (Button)findViewById(R.id.cancelOrder);
        RadioButton small = (RadioButton)findViewById(R.id.small);
        RadioButton medium = (RadioButton)findViewById(R.id.medium);
        RadioButton large = (RadioButton)findViewById(R.id.large);
        TextView price = (TextView)findViewById(R.id.price);

        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);

        small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(radioId);
                if(radioButton.getText().equals(small.getText())){
                    pizzaPrice = pizzaPrice + BUILD_YOUR_OWN_SMALL_PRICE.getValue();
                  price.setText("Pizza Price $:" + df.format(pizzaPrice));
                }
                else if(radioButton.getText().equals(medium.getText())){
                    pizzaPrice = pizzaPrice + BUILD_YOUR_OWN_MEDIUM_PRICE.getValue();
                  price.setText("Pizza Price $:" + df.format(pizzaPrice));
                }
                else if(radioButton.getText().equals(large.getText())){
                    pizzaPrice = pizzaPrice + BUILD_YOUR_OWN_LARGE_PRICE.getValue();
                    price.setText("Pizza Price $:" + df.format(pizzaPrice));
                }
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

        ArrayList<Topping> selectedToppings = new ArrayList<Topping>();

        addToppings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedToppings.add((Topping) adapterView.getItemAtPosition(i));
                pizzaPrice = pizzaPrice + PRICE_PER_TOPPING.getValue();
                price.setText("Pizza Price $:" + df.format(pizzaPrice));
            }
        });


        programAdaptper = new ProgramAdapter(this, programNameList, programDescriptionList, programImages, new ProgramAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String s){
                switch (s){
                    case "NY Deluxe":
                        initializeDeluxe(new NYPizza());
                }
                Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(programAdaptper);
        programAdaptper.getItemCount();

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(programNameList[1].equals("Chicago Deluxe")){
                    initializeChicagoDeluxe();
                }
                else if(programNameList[programAdaptper.getItemCount()].equals("Chicago BBQ Chicken")){
                    initializeChicagoBBQ();
                }
                else if(programNameList[programAdaptper.getItemCount()].equals("Chicago Meatzza")){
                    initializeChicagoMeatzza();
                }
                else if(programNameList[programAdaptper.getItemCount()].equals("Chicago BYO")){
                    initializeChicagoBuildYourOwn();
                }
                else if(programNameList[programAdaptper.getItemCount()].equals("NY Deluxe")){
                    initializeNYDeluxe();
                }
                else if(programNameList[programAdaptper.getItemCount()].equals("NY BBQ Chicken")){
                    initializeNYBBQ();
                }
                else if(programNameList[programAdaptper.getItemCount()].equals("NY Meatzza")){
                    initializeNYMeatzza();
                }
                else if(programNameList[programAdaptper.getItemCount()].equals("NY BYO")){
                    initializeNYBuildYourOwn();
                }
            }

            private void initializeNYBuildYourOwn() {
                pizzaPrice = pizzaPrice + BUILD_YOUR_OWN_LARGE_PRICE.getValue();
                price.setText("Pizza Price $:" + df.format(pizzaPrice));
            }

            private void initializeNYMeatzza() {
            }

            private void initializeNYBBQ() {
            }

            private void initializeDeluxe(PizzaFactory pizzaFactory) {
            }

            private void initializeChicagoBuildYourOwn() {
            }

            private void initializeChicagoMeatzza() {
            }

            private void initializeChicagoBBQ() {
            }

//            private void initializeChicagoDeluxe() {
//                pizzaPrice = pizzaPrice + BUILD_YOUR_OWN_LARGE_PRICE.getValue();
//                price.setText("Pizza Price $:" + df.format(pizzaPrice));
//            }
        });

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

    private void initializeDeluxe(NYPizza nyPizza) {
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
