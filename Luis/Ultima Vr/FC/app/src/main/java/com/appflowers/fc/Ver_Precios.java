package com.appflowers.fc;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.appflowers.fc.Global.Conexion_SQL;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Ver_Precios extends AppCompatActivity {

    EditText codigo;
    TextView producto,precio;
    Button leer;
    String TipoProd;
    private long backPressedTime;

    private ZXingScannerView vistascanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_precios);
        getSupportActionBar().setTitle("CONSULTA DE PRECIOS");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_precios);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.nav_precios:
                    {

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
                        startActivity(new Intent(getApplicationContext(),Ver_Pedidos.class));
                        overridePendingTransition(0,0);
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

    public void Escanear(View view)
    {
        vistascanner=new ZXingScannerView(this);
        vistascanner.setResultHandler(new zxingscaner());
        setContentView(vistascanner);
        vistascanner.startCamera();
    }

    class  zxingscaner implements ZXingScannerView.ResultHandler
    {
        @Override
        public void handleResult(com.google.zxing.Result result) {
            String dato=result.getText();
            setContentView(R.layout.activity_ver_precios);
            vistascanner.stopCamera();
            codigo=(EditText)findViewById(R.id.codprod);
            codigo.setText(dato);
            BuscarPrecio2();
        }
    }

    public void BuscarPrecio (View view)
    {
        Conexion_SQL conex = new Conexion_SQL();
        String sql;
        try
        {
            codigo=(EditText)findViewById(R.id.codprod);


            PreparedStatement pst =conex.ConexionBD().prepareStatement("SELECT * FROM PRODUCTOS WHERE COD_PROD=? ");
            pst.setString(1,codigo.getText().toString());

            ResultSet rs = pst.executeQuery();
            if (rs.next())
            {
                producto=(TextView) findViewById(R.id.producto);
                producto.setText(rs.getString("DESCRIPCION"));
                TipoProd=rs.getString("TIPO_PROD");
                //return true;
            }
            else
            {
                Toast.makeText(getApplicationContext(),"EL CODIGO DE ESE PRODUCTO NO EXISTE "  ,Toast.LENGTH_SHORT).show();
                //return false;
            }
            if (TipoProd.equals("Flores"))
            {
                pst =conex.ConexionBD().prepareStatement("SELECT * FROM TIPO_PRECIOS WHERE UNIDAD=1 AND COD_PROD=? ");

            }   else
            {
                pst =conex.ConexionBD().prepareStatement("SELECT * FROM TIPO_PRECIOS WHERE UNIDAD=3 AND COD_PROD=? ");
            }


            pst.setString(1,codigo.getText().toString());

            rs = pst.executeQuery();
            if (rs.next())
            {
                precio=(TextView) findViewById(R.id.precio);
                precio.setText("C$ " + rs.getString("PREC_PART"));
                //return true;
            }
            else
            {
                Toast.makeText(getApplicationContext(),"EL CODIGO DE ESE PRODUCTO NO EXISTE "  ,Toast.LENGTH_SHORT).show();
                //return false;
            }

            rs.close();



        }
        catch (SQLException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            // return false;
        }
    }

    public void BuscarPrecio2 ()
    {
        Conexion_SQL conex = new Conexion_SQL();
        String sql;
        try
        {
            codigo=(EditText)findViewById(R.id.codprod);
            PreparedStatement pst =conex.ConexionBD().prepareStatement("SELECT * FROM PRODUCTOS WHERE COD_PROD=? ");
            pst.setString(1,codigo.getText().toString());

            ResultSet rs = pst.executeQuery();
            if (rs.next())
            {
                producto=(TextView) findViewById(R.id.producto);
                producto.setText(rs.getString("DESCRIPCION"));
                TipoProd=rs.getString("TIPO_PROD");
                //return true;
            }
            else
            {
                Toast.makeText(getApplicationContext(),"EL CODIGO DE ESE PRODUCTO NO EXISTE "  ,Toast.LENGTH_SHORT).show();
                //return false;
            }
            if (TipoProd.equals("Flores"))
            {
                pst =conex.ConexionBD().prepareStatement("SELECT * FROM TIPO_PRECIOS WHERE UNIDAD=1 AND COD_PROD=? ");

            }   else
            {
                pst =conex.ConexionBD().prepareStatement("SELECT * FROM TIPO_PRECIOS WHERE UNIDAD=3 AND COD_PROD=? ");
            }


            pst.setString(1,codigo.getText().toString());

            rs = pst.executeQuery();
            if (rs.next())
            {
                precio=(TextView) findViewById(R.id.precio);
                precio.setText("C$ " + rs.getString("PREC_PART"));
                //return true;
            }
            else
            {
                Toast.makeText(getApplicationContext(),"EL CODIGO DE ESE PRODUCTO NO EXISTE "  ,Toast.LENGTH_SHORT).show();
                //return false;
            }

            rs.close();
        }
        catch (SQLException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            // return false;
        }
    }

}
