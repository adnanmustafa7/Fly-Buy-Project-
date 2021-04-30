package com.example.fly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class product_view extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new refragment()).commit();

    }
}