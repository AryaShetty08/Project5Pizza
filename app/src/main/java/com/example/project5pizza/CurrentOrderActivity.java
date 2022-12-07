package com.example.project5pizza;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;


import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import pizzaManager.Order;
import pizzaManager.Pizza;
import pizzaManager.Topping;

public class CurrentOrderActivity extends AppCompatActivity {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private ArrayList<Pizza> currentOrder;

    ListView pizzaOrder;
    TextView subtotal;
    TextView salestax;
    TextView ordertotal;
    TextView orderNumber;
    Button removePizza;
    Button placeOrder;
    Button clearOrder;
    Order current = new Order(1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currentorder);

        pizzaOrder = (ListView)findViewById(R.id.currentPizza);
        removePizza = (Button)findViewById(R.id.cancelOrder);
        subtotal = (TextView)findViewById(R.id.subTotal);
        salestax = (TextView)findViewById(R.id.salesTax);
        ordertotal = (TextView)findViewById(R.id.totalprice);
        placeOrder = (Button)findViewById(R.id.placeOrder);
        clearOrder = (Button)findViewById(R.id.clearOrder);
        orderNumber = (TextView)findViewById(R.id.currentNumber);

        ArrayList<Pizza> selectedPizza = new ArrayList<Pizza>();

        Intent intent = getIntent();
        ArrayList<String> currentOrder = intent.getStringArrayListExtra(MainActivity.PIZZA_LIST_IDENTIFIER);
        Toast.makeText(getApplicationContext(), currentOrder.get(0), Toast.LENGTH_LONG).show();

        for(String pizza : currentOrder) {
            current.add(Pizza.stringToPizza(pizza));
        }
        subtotal.setText("Subtotal $" + df.format(current.getSubtotal()));
        salestax.setText("Sales Tax $" + df.format(current.getTax()));
        ordertotal.setText("Order Total $" + df.format(current.getTotal()));
        orderNumber.setText("Order Number: " + df.format(current.getSerialNumber()));

        if(!currentOrder.isEmpty()){
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, currentOrder);
            pizzaOrder.setAdapter(arrayAdapter);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        removePizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeOnePizza();
                Toast.makeText(CurrentOrderActivity.this, "Removing Pizza...", Toast.LENGTH_SHORT).show();
            }
        });

        clearOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentOrder.clear();
                resetCurrentOrder();
                Toast.makeText(CurrentOrderActivity.this, "Removing All Pizzas...", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void resetCurrentOrder() {
        pizzaOrder.setAdapter(null);
        subtotal.setText("Subtotal $");
        salestax.setText("Sales Tax $");
        ordertotal.setText("Order Total $");
        orderNumber.setText("Order Number: ");
    }

    private void removeOnePizza() {
        currentOrder.remove(0);
        //ArrayAdapter temp = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, currentOrder);
        //pizzaOrder.setAdapter(temp);
        subtotal.setText("Subtotal $" + df.format(current.getSubtotal()));
        salestax.setText("Sales Tax $" + df.format(current.getTax()));
        ordertotal.setText("Order Total $" + df.format(current.getTotal()));
    }

    private void setclicks() {
        ArrayAdapter temp = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, currentOrder);
        pizzaOrder.setAdapter(temp);
    }

}
