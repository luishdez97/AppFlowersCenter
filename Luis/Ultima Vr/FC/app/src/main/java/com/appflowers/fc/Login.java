package com.appflowers.fc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.appflowers.fc.Global.Conexion_SQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends AppCompatActivity {

    private long backPressedTime;
    Conexion_SQL conex = new Conexion_SQL();
    EditText varusuario,varpass;
    Button btningreso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        //Metodo 1
        varusuario =(EditText) findViewById(R.id.usuario);
        varpass=(EditText) findViewById(R.id.pass);
        btningreso=(Button)findViewById(R.id.btningresar);
        btningreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if  (IniciarSesion())
                {
                    Intent intent = new Intent (v.getContext(), MainActivity.class);
                    startActivityForResult(intent, 0);
                }
            }
        });

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
    public boolean IniciarSesion ()
    {

        try
        {
            PreparedStatement pst =conex.ConexionBD().prepareStatement("SELECT * FROM SEGURIDAD WHERE USUARIO=? AND PASSWORD=?");
            pst.setString(1,varusuario.getText().toString());
            pst.setString(2,varpass.getText().toString());
            ResultSet rs = pst.executeQuery();
            if (rs.next())
            {
                Toast.makeText(getApplicationContext(),"BIENVENIDO " + rs.getString("NOMBRE") ,Toast.LENGTH_SHORT).show();
                return true;
            }
            else
            {
                Toast.makeText(getApplicationContext(),"USUARIO O CONTRASEÑA INCORRECTOS "  ,Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        catch (SQLException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
