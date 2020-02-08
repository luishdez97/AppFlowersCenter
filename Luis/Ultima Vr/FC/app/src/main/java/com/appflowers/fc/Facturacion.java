package com.appflowers.fc;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Facturacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facturacion);
        getSupportActionBar().hide();
    }
}
