package com.appflowers.fc;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.appflowers.fc.Global.Conexion_SQL;
import com.google.android.material.navigation.NavigationView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Conexion_SQL conex = new Conexion_SQL();
    private long backPressedTime;
    private AppBarConfiguration mAppBarConfiguration;
    final private int REQUEST_CODE_ASK_PERMISSIONS =124;
    TextView nomb_usuario;
    int hasOpenCamera;

    EditText user, password;
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void Acceso_Permisos()
    {
        hasOpenCamera= checkSelfPermission(Manifest.permission.CAMERA);
        if (hasOpenCamera!= PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    REQUEST_CODE_ASK_PERMISSIONS);
        }
   }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults)
    {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                // If request is cancelled, the result arrays are empty.
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                }
                else
                {
                }
                break;
                default:
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Acceso_Permisos();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home,
                R.id.nav_facturacion, R.id.nav_grab_pedidos, R.id.nav_home,
                R.id.nav_cambio_precios, R.id.nav_consultas, R.id.nav_abonos_clientes)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public boolean Nombre_Usuario ()
    {

        try
        {

            nomb_usuario=(TextView)findViewById(R.id.textView_Nombre_Usuario);

            PreparedStatement pst =conex.ConexionBD().prepareStatement("SELECT * FROM SEGURIDAD WHERE USUARIO=? AND PASSWORD=?");
            pst.setString(1,nomb_usuario.getText().toString());
            ResultSet rs = pst.executeQuery();
            if (rs.next())
            {
                Toast.makeText(getApplicationContext(),"EL USUARIO ES " + rs.getString("NOMBRE") ,Toast.LENGTH_SHORT).show();
                return true;
            }
            else
            {
                Toast.makeText(getApplicationContext(),"USUARIO INVALIDO "  ,Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        catch (SQLException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home)
        {

        }
        else
        if (id == R.id.nav_facturacion)
        {
            startActivity(new Intent(MainActivity.this, Facturacion.class));
        }
        else
            if (id == R.id.nav_grab_pedidos)
        {

        }
            else
            if (id == R.id.nav_home)
            {

            }
            else
                if (id == R.id.nav_cambio_precios)
                {
                    startActivity(new Intent(MainActivity.this, Actualizar_Precios.class));
                }
            else
                if (id==R.id.nav_consultas)
                {
                    startActivity(new Intent(MainActivity.this, Ver_Precios.class));
                }
            else
                if (id == R.id.nav_Salir)
                {
                    finish();
                }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    }