package com.example.project5pizza;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pizzaManager.Order;
import pizzaManager.Pizza;
import pizzaManager.Topping;

public class CurrentOrderActivity extends AppCompatActivity {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    //private ArrayList<Pizza> currentOrder;

    ListView pizzaOrder;
    TextView subtotal;
    TextView salestax;
    TextView ordertotal;
    TextView orderNumber;
    Button removePizza;
    Button placeOrder;
    Button clearOrder;
    Order current;
    int pizzaToRemove;
    ArrayAdapter arrayAdapter;


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

        current = new Order(intent.getIntExtra(MainActivity.SERIAL_NUMBER_IDENTIFIER, -1));
        for(String pizza : currentOrder) {
            current.add(Pizza.stringToPizza(pizza));
        }

        subtotal.setText("Subtotal $" + df.format(current.getSubtotal()));
        salestax.setText("Sales Tax $" + df.format(current.getTax()));
        ordertotal.setText("Order Total $" + df.format(current.getTotal()));
        orderNumber.setText("Order Number: " + current.getSerialNumber());

        if(!currentOrder.isEmpty()){
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, currentOrder);
            pizzaOrder.setChoiceMode(pizzaOrder.CHOICE_MODE_SINGLE);
            pizzaOrder.setAdapter(arrayAdapter);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pizzaOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pizzaToRemove = i;
            }
        });

        removePizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pizzaToRemove < currentOrder.size()){
                    Pizza removing = Pizza.stringToPizza(currentOrder.get(pizzaToRemove));
                    if (!current.remove(removing)){
                        Toast.makeText(CurrentOrderActivity.this, removing.toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(CurrentOrderActivity.this, current.getPizzaList().get(pizzaToRemove).toString(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (current.isEmpty()){
                        subtotal.setText("Subtotal $");
                        salestax.setText("Sales Tax $");
                        ordertotal.setText("Order Total $");
                        orderNumber.setText("Order Number: ");
                    }
                    else {
                        subtotal.setText("Subtotal $" + df.format(current.getSubtotal()));
                        salestax.setText("Sales Tax $" + df.format(current.getTax()));
                        ordertotal.setText("Order Total $" + df.format(current.getTotal()));
                    }
                    removeOnePizza(pizzaToRemove);
                    Toast.makeText(CurrentOrderActivity.this, "Removing Pizza...", Toast.LENGTH_SHORT).show();
                }
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

    private void removeOnePizza(int position) {
        arrayAdapter.remove(arrayAdapter.getItem(position));
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(MainActivity.FAILED_RESULT, null);
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
