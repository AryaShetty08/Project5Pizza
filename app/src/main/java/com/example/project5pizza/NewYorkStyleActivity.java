package com.example.project5pizza;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NewYorkStyleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newyorkstylepizza);

        Button addToOrder = (Button)findViewById(R.id.addToOrder);
    }

}
