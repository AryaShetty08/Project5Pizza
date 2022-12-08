package com.example.project5pizza;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

import pizzaManager.Order;
import pizzaManager.Pizza;
import pizzaManager.StoreOrder;
import pizzaManager.Topping;

public class StoreOrdersActivity extends AppCompatActivity {

    ListView pizzaOrders;
    Button cancelOrder;
    TextView total;
    int orderPosition;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storeorders);

        pizzaOrders = (ListView)findViewById(R.id.pizzaOrders);
        cancelOrder = (Button)findViewById(R.id.cancelOrder);
        total = (TextView)findViewById(R.id.total);

        StoreOrder storeOrder = new StoreOrder();
        Intent intent = getIntent();
        int size = intent.getIntExtra("Size", -1);
        for (int i = 0; i < size; i++){
            Order order = new Order(intent.getIntExtra(String.valueOf(-i-1), -1));
            ArrayList<String> temp = intent.getStringArrayListExtra(String.valueOf(i+1));
            for (String str: temp){
                order.add(Pizza.stringToPizza(str));
            }
            storeOrder.add(order);
        }

        ArrayList<String> pizzaInOrder = new ArrayList<>();
        for(Pizza pizza : storeOrder.getOrderList().get(0).getPizzaList()) {
            pizzaInOrder.add(pizza.toString());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, pizzaInOrder);
        pizzaOrders.setAdapter(arrayAdapter);
        total.setText("Order Total $" + df.format(storeOrder.getOrderList().get(0).getTotal()));

        Spinner spinner = (Spinner)findViewById(R.id.spinner);

        ArrayList<Integer> orderNumbers = new ArrayList<>();

        for(Order order : storeOrder.getOrderList()) {
            orderNumbers.add(order.getSerialNumber());
        }


        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, orderNumbers);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter1);
        //spinner.setOnItemSelectedListener(this);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                orderPosition = i;
                pizzaInOrder.clear();
                for(Pizza pizza : storeOrder.getOrderList().get(i).getPizzaList()) {
                    pizzaInOrder.add(pizza.toString());
                }
                switchOrderList(pizzaInOrder);
                total.setText("Order Total $" + df.format(storeOrder.getOrderList().get(i).getTotal()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(storeOrder.getOrderList().size() == 1) {
                    storeOrder.remove(storeOrder.getOrderList().get(0));
                    pizzaOrders.setAdapter(null);
                    total.setText("Order Total $");
                    spinner.setAdapter(null);
                }
                else {
//                    storeOrder.remove(storeOrder.getOrderList().get(orderPosition));
//                    pizzaOrders.setAdapter();
//                    total.setText("Order Total $" + df.format(storeOrder.getOrderList().get().getTotal()));
//                    spinner.
                }
                showAlertDialog("Order More Pizza!");
            }
        });

    }

    private void switchOrderList(ArrayList<String> pizzaInOrder){
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, pizzaInOrder);
        pizzaOrders.setAdapter(arrayAdapter2);
    }


    private void showAlertDialog(String message){
        AlertDialog dialog = new AlertDialog.Builder(StoreOrdersActivity.this)
                .setTitle("Deleted Order!")
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
