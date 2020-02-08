package com.appflowers.fc;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class Consulta_Inventario extends AppCompatActivity {

    private long backPressedTime;
    EditText codigo;
    TextView producto, precio;
    Button leer, consultar;
    String TipoProd;
    Spinner combo;
    String ip, db, un, passwords;

    private ZXingScannerView vistascanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta__inventario);
        getSupportActionBar().setTitle("CONSULTA DE INVENTARIOS");
        LlenarCombo();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_inventarios);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.nav_precios:
                    {
                        startActivity(new Intent(getApplicationContext(), Ver_Precios.class));
                        overridePendingTransition(0,0);
                        return true;
                    }
                    case R.id.nav_inventarios:
                    {

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
            setContentView(R.layout.activity_consulta__inventario);
            vistascanner.stopCamera();
            codigo=(EditText)findViewById(R.id.codprod);
            codigo.setText(dato);
            LlenarCombo();

        }
    }

    public void LlenarCombo()
    {
        String [] opciones={"TIENDA FLOWERS","BODEGA CUARTO FRIO 2","BODEGA CENTRAL","BODEGA CENTRAL 2","BODEGA CENTRAL 3","TIENDA MATAGALPA"};
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,opciones);
        combo=(Spinner)findViewById(R.id.cmbbodegas);
        combo.setAdapter(adapter);

    }
    public void BuscarPrecio (View view) {
        String sql, codbodega;
        String seleccion = combo.getSelectedItem().toString();
        Conexion_SQL conex = new Conexion_SQL();
        try {

            PreparedStatement pst = conex.ConexionBD().prepareStatement("SELECT * FROM BODEGAS WHERE DESCRIPCION=? ");
            pst.setString(1, seleccion);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                codbodega = rs.getString("COD_BODEGA");

                //return true;
            } else {
                codbodega = "";
                Toast.makeText(getApplicationContext(), "LA BODEGA SELECCIONADA NO EXISTE ", Toast.LENGTH_SHORT).show();
                //return false;
            }
            codigo = (EditText) findViewById(R.id.codprod);
            pst = conex.ConexionBD().prepareStatement("SELECT * FROM PRODUCTOS WHERE COD_PROD=? ");
            pst.setString(1, codigo.getText().toString());

            rs = pst.executeQuery();
            if (rs.next()) {
                producto = (TextView) findViewById(R.id.producto);
                producto.setText(rs.getString("DESCRIPCION"));
                TipoProd = rs.getString("TIPO_PROD");
            } else {
                Toast.makeText(getApplicationContext(), "EL CODIGO DE ESE PRODUCTO NO EXISTE ", Toast.LENGTH_SHORT).show();
                return;

            }
            if (TipoProd.equals("Flores")) {
                pst = conex.ConexionBD().prepareStatement("SELECT     dbo.PRODUCTOS.DESCRIPCION, dbo.Detalle_Documentos.COD_PROD, \n" +
                        "                     ROUND(SUM((CASE WHEN PRODUCTOS.TIPO_PROD = 'Flores' THEN (CASE WHEN Detalle_Documentos.UNIDAD = 'Unidad' THEN (Detalle_Documentos.CANT / PRODUCTOS.CANT_MOÑO) \n" +
                        "                      ELSE (CASE WHEN Detalle_Documentos.UNIDAD = 'Docena' THEN (Detalle_Documentos.CANT * 12 / PRODUCTOS.CANT_MOÑO) ELSE Detalle_Documentos.CANT END) END) \n" +
                        "                      ELSE Detalle_Documentos.CANT END)),2) AS Existencia, dbo.PRODUCTOS.COSTO\n" +
                        "FROM         dbo.Encabezado_Documentos INNER JOIN\n" +
                        "                      dbo.Detalle_Documentos ON dbo.Encabezado_Documentos.NUM_REG = dbo.Detalle_Documentos.NUM_REG INNER JOIN\n" +
                        "                      dbo.PRODUCTOS ON dbo.PRODUCTOS.COD_PROD = dbo.Detalle_Documentos.COD_PROD\n" +
                        "GROUP BY dbo.Detalle_Documentos.COD_PROD, dbo.Encabezado_Documentos.COD_BODEGA, dbo.PRODUCTOS.DESCRIPCION, dbo.PRODUCTOS.COSTO\n" +
                        "HAVING    (dbo.Encabezado_Documentos.COD_BODEGA = ?) AND  (dbo.Detalle_Documentos.COD_PROD = ?) ");

            } else {
                pst = conex.ConexionBD().prepareStatement("SELECT     ROUND(SUM(Detalle_Documentos.CANT),2) AS EXISTENCIA\n" +
                        "FROM         PRODUCTOS INNER JOIN\n" +
                        "                      Detalle_Documentos ON PRODUCTOS.COD_PROD = Detalle_Documentos.COD_PROD INNER JOIN\n" +
                        "                      Encabezado_Documentos ON Detalle_Documentos.NUM_REG = Encabezado_Documentos.NUM_REG\n" +
                        "WHERE     (Encabezado_Documentos.COD_BODEGA = ?)  GROUP BY PRODUCTOS.COD_PROD, PRODUCTOS.REFERENCIA, PRODUCTOS.DESCRIPCION\n" +
                        "HAVING      (PRODUCTOS.COD_PROD = ?)  ");
            }

            pst.setString(1, codbodega);
            pst.setString(2, codigo.getText().toString());

            rs = pst.executeQuery();
            if (rs.next()) {
                precio = (TextView) findViewById(R.id.precio);
                precio.setText(rs.getString("EXISTENCIA"));
                //return true;
            } else {
                // Toast.makeText(getApplicationContext(),"EL CODIGO DE ESE PRODUCTO NO EXISTE "  ,Toast.LENGTH_SHORT).show();
                precio = (TextView) findViewById(R.id.precio);
                precio.setText("0");
                //return false;
            }
            rs.close();
        } catch (SQLException e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}