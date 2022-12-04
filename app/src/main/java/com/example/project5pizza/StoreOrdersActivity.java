package com.example.project5pizza;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import pizzaManager.Order;
import pizzaManager.Topping;

public class StoreOrdersActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ListView pizzaOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storeorders);

        pizzaOrders = (ListView)findViewById(R.id.pizzaOrders);

        ArrayList<Order> selectedOrders = new ArrayList<Order>();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, selectedOrders);
        pizzaOrders.setAdapter(arrayAdapter);

        Spinner spinner = (Spinner)findViewById(R.id.spinner);

        ArrayList<Integer> orders= new ArrayList<Integer>();
        orders.add(1);
        orders.add(2);

        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, orders);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter1);
        spinner.setOnItemSelectedListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
