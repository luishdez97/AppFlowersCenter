package com.appflowers.fc;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Ver_Pedidos extends AppCompatActivity
{
    private long backPressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_pedidos);
        getSupportActionBar().setTitle("CONSULTA DE PEDIDOS");
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_pedidos);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.nav_precios:
                    {
                        startActivity(new Intent(getApplicationContext(),Ver_Precios.class));
                        overridePendingTransition(0,0);
                        return true;
                    }
                    case R.id.nav_inventarios:
                    {
                        startActivity(new Intent(getApplicationContext(), Consulta_Inventario.class));
                        overridePendingTransition(0,0);
                        return true;
                    }

                    case R.id.nav_pedidos:
                    {

                        return true;
                    }
                }

                return true;
            }
        });
    }

    public void Regresar(View view)
    {
        Intent intent = new Intent (view.getContext(), MainActivity.class);
        startActivityForResult(intent, 0);
    }
    @Override
    public void onBackPressed() {
        long t = System.currentTimeMillis();
        if (t - backPressedTime > 0)
        {
            backPressedTime = t;
        }
        else
        {
            super.onBackPressed();
        }
    }
}
