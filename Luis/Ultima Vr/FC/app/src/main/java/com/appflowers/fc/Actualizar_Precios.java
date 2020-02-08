package com.appflowers.fc;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.appflowers.fc.Global.Conexion_SQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Actualizar_Precios extends AppCompatActivity {

    Conexion_SQL conex = new Conexion_SQL();
    EditText codigo,mh,mm,mf,mparro,mpart,mmat,dh,dm,df,dparro,dpart,dmat,uh,um,uf,uparro,upart,umat;
    TextView producto,precio;
    Button leer;
    String TipoProd;
    private ZXingScannerView vistascanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar__precios);
        getSupportActionBar().setTitle("Actualización de precios".toUpperCase());
        codigo = (EditText) findViewById(R.id.codigo);
        codigo.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                { switch (keyCode)
                { case KeyEvent.KEYCODE_ENTER: BuscarPrecio();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    return true;
                    case KeyEvent.KEYCODE_DEL: Limpiar();
                        return true;
                    default: break;

                }
                }

                return false;
            }
        });
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
            setContentView(R.layout.activity_actualizar__precios);
            vistascanner.stopCamera();
            codigo=(EditText)findViewById(R.id.codprod);
            codigo.setText(dato);
            BuscarPrecio();
        }
    }

    public void BuscarPrecio ()
    {
        String sql;
        try
        {
            codigo=(EditText)findViewById(R.id.codigo);


            PreparedStatement pst =conex.ConexionBD().prepareStatement("SELECT * FROM PRODUCTOS WHERE COD_PROD=? ");
            pst.setString(1,codigo.getText().toString());

            ResultSet rs = pst.executeQuery();
            if (rs.next())
            {
                producto=(TextView) findViewById(R.id.descripcion);
                producto.setText(rs.getString("DESCRIPCION"));
                TipoProd=rs.getString("TIPO_PROD");
                //return true;
            }
            else
            {
                Toast.makeText(getApplicationContext(),"EL CODIGO DE ESE PRODUCTO NO EXISTE "  ,Toast.LENGTH_SHORT).show();
                return;
            }

            if (TipoProd.equals("Flores"))
            {
                pst =conex.ConexionBD().prepareStatement("SELECT * FROM TIPO_PRECIOS WHERE  COD_PROD=? ");

            }   else
            {
                pst =conex.ConexionBD().prepareStatement("SELECT * FROM TIPO_PRECIOS WHERE UNIDAD=3 AND COD_PROD=? ");
            }


            pst.setString(1,codigo.getText().toString());

            rs = pst.executeQuery();
            //return true;
            while  (rs.next())
            {

                if (rs.getString("unidad").equals("1")) {
                    mh = (EditText) findViewById(R.id.mh);
                    mh.setText(rs.getString("PREC_MAY_HUEMBES"));
                    mm = (EditText) findViewById(R.id.mm);
                    mm.setText(rs.getString("PREC_MAYOR"));
                    mf = (EditText) findViewById(R.id.mf);
                    mf.setText(rs.getString("PREC_FLOR"));
                    mparro = (EditText) findViewById(R.id.mparro);
                    mparro.setText(rs.getString("PREC_PARROQUIA"));
                    mpart = (EditText) findViewById(R.id.mpart);
                    mpart.setText(rs.getString("PREC_PART"));
                    mmat = (EditText) findViewById(R.id.mmat);
                    mmat.setText(rs.getString("PREC_NACIONAL_MATAGALPA"));
                } else if (rs.getString("unidad").equals("2")) {
                    dh = (EditText) findViewById(R.id.dh);
                    dh.setText(rs.getString("PREC_MAY_HUEMBES"));
                    dm = (EditText) findViewById(R.id.dm);
                    dm.setText(rs.getString("PREC_MAYOR"));
                    df = (EditText) findViewById(R.id.df);
                    df.setText(rs.getString("PREC_FLOR"));
                    dparro = (EditText) findViewById(R.id.dparro);
                    dparro.setText(rs.getString("PREC_PARROQUIA"));
                    dpart = (EditText) findViewById(R.id.dpart);
                    dpart.setText(rs.getString("PREC_PART"));
                    dmat = (EditText) findViewById(R.id.dmat);
                    dmat.setText(rs.getString("PREC_NACIONAL_MATAGALPA"));
                } else if (rs.getString("unidad").equals("3")) {
                    uh = (EditText) findViewById(R.id.uh);
                    uh.setText(rs.getString("PREC_MAY_HUEMBES"));
                    um = (EditText) findViewById(R.id.um);
                    um.setText(rs.getString("PREC_MAYOR"));
                    uf = (EditText) findViewById(R.id.uf);
                    uf.setText(rs.getString("PREC_FLOR"));
                    uparro = (EditText) findViewById(R.id.uparro);
                    uparro.setText(rs.getString("PREC_PARROQUIA"));
                    upart = (EditText) findViewById(R.id.upart);
                    upart.setText(rs.getString("PREC_PART"));
                    umat = (EditText) findViewById(R.id.umat);
                    umat.setText(rs.getString("PREC_NACIONAL_MATAGALPA"));
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"EL CODIGO DE ESE PRODUCTO NO EXISTE "  ,Toast.LENGTH_SHORT).show();


                }
            }

            rs.close();
        }
        catch (SQLException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            // return false;
        }
    }


    public void Limpiar()
    {
        codigo = (EditText) findViewById(R.id.codigo);
        codigo.setText("");
        producto = (TextView) findViewById(R.id.descripcion);
        producto.setText("");
        mh = (EditText) findViewById(R.id.mh);
        mh.setText("");
        mm = (EditText) findViewById(R.id.mm);
        mm.setText("");
        mf = (EditText) findViewById(R.id.mf);
        mf.setText("");
        mparro = (EditText) findViewById(R.id.mparro);
        mparro.setText("");
        mpart = (EditText) findViewById(R.id.mpart);
        mpart.setText("");
        mmat = (EditText) findViewById(R.id.mmat);
        mmat.setText("");
        dh = (EditText) findViewById(R.id.dh);
        dh.setText("");
        dm = (EditText) findViewById(R.id.dm);
        dm.setText("");
        df = (EditText) findViewById(R.id.df);
        df.setText("");
        dparro = (EditText) findViewById(R.id.dparro);
        dparro.setText("");
        dpart = (EditText) findViewById(R.id.dpart);
        dpart.setText("");
        dmat = (EditText) findViewById(R.id.dmat);
        dmat.setText("");
        uh = (EditText) findViewById(R.id.uh);
        uh.setText("");
        um = (EditText) findViewById(R.id.um);
        um.setText("");
        uf = (EditText) findViewById(R.id.uf);
        uf.setText("");
        uparro = (EditText) findViewById(R.id.uparro);
        uparro.setText("");
        upart = (EditText) findViewById(R.id.upart);
        upart.setText("");
        umat = (EditText) findViewById(R.id.umat);
        umat.setText("");

    }


    public void ActualizarPrecios (View view)
    {
        String sql;
        boolean existe;
        existe =false;
        try
        {
            codigo=(EditText)findViewById(R.id.codigo);
            PreparedStatement pst =conex.ConexionBD().prepareStatement("SELECT * FROM PRODUCTOS INNER JOIN TIPO_PRECIOS ON TIPO_PRECIOS.COD_PROD=PRODUCTOS.COD_PROD WHERE PRODUCTOS.COD_PROD=? ");
            pst.setString(1,codigo.getText().toString());

            ResultSet rs = pst.executeQuery();
            if (rs.next())
            {

                //TipoProd=rs.getString("TIPO_PROD");
                existe=true;
                //return true;
            }
            else
            {
                Toast.makeText(getApplicationContext(),"EL CODIGO DE ESE PRODUCTO NO EXISTE "  ,Toast.LENGTH_SHORT).show();
                existe=false;
                return;
            }
            if (existe)
            {
                if (TipoProd.equals("Flores"))
                {
                    pst =conex.ConexionBD().prepareStatement("UPDATE TIPO_PRECIOS SET PREC_MAY_HUEMBES=?,PREC_MAYOR=?,PREC_FLOR=?,PREC_PARROQUIA=?,PREC_PART=?,PREC_NACIONAL_MATAGALPA=?,PREC_EMPLEADOS=?  WHERE UNIDAD=1 AND COD_PROD=? ");

                    mh=(EditText)findViewById(R.id.mh);
                    pst.setString(1,mh.getText().toString());
                    mm=(EditText)findViewById(R.id.mm);
                    pst.setString(2,mm.getText().toString());
                    mf=(EditText)findViewById(R.id.mf);
                    pst.setString(3,mf.getText().toString());
                    mparro=(EditText)findViewById(R.id.mparro);
                    pst.setString(4,mparro.getText().toString());
                    mpart=(EditText)findViewById(R.id.mpart);
                    pst.setString(5,mpart.getText().toString());
                    mmat=(EditText)findViewById(R.id.mmat);
                    pst.setString(6,mmat.getText().toString());
                    pst.setString(7,mpart.getText().toString());
                    pst.setString(8,codigo.getText().toString());
                    pst.executeUpdate();

                    pst =conex.ConexionBD().prepareStatement("UPDATE TIPO_PRECIOS SET PREC_MAY_HUEMBES=?,PREC_MAYOR=?,PREC_FLOR=?,PREC_PARROQUIA=?,PREC_PART=?,PREC_NACIONAL_MATAGALPA=?,PREC_EMPLEADOS=?  WHERE UNIDAD=2 AND COD_PROD=? ");

                    dh=(EditText)findViewById(R.id.dh);
                    pst.setString(1,dh.getText().toString());
                    dm=(EditText)findViewById(R.id.dm);
                    pst.setString(2,dm.getText().toString());
                    df=(EditText)findViewById(R.id.df);
                    pst.setString(3,df.getText().toString());
                    dparro=(EditText)findViewById(R.id.dparro);
                    pst.setString(4,dparro.getText().toString());
                    dpart=(EditText)findViewById(R.id.dpart);
                    pst.setString(5,dpart.getText().toString());
                    dmat=(EditText)findViewById(R.id.dmat);
                    pst.setString(6,dmat.getText().toString());
                    pst.setString(7,dpart.getText().toString());
                    pst.setString(8,codigo.getText().toString());
                    pst.executeUpdate();

                    pst =conex.ConexionBD().prepareStatement("UPDATE TIPO_PRECIOS SET PREC_MAY_HUEMBES=?,PREC_MAYOR=?,PREC_FLOR=?,PREC_PARROQUIA=?,PREC_PART=?,PREC_NACIONAL_MATAGALPA=?,PREC_EMPLEADOS=?  WHERE UNIDAD=3 AND COD_PROD=? ");

                    uh=(EditText)findViewById(R.id.uh);
                    pst.setString(1,uh.getText().toString());
                    um=(EditText)findViewById(R.id.um);
                    pst.setString(2,um.getText().toString());
                    uf=(EditText)findViewById(R.id.uf);
                    pst.setString(3,uf.getText().toString());
                    uparro=(EditText)findViewById(R.id.uparro);
                    pst.setString(4,uparro.getText().toString());
                    upart=(EditText)findViewById(R.id.upart);
                    pst.setString(5,upart.getText().toString());
                    umat=(EditText)findViewById(R.id.umat);
                    pst.setString(6,umat.getText().toString());
                    pst.setString(7,upart.getText().toString());
                    pst.setString(8,codigo.getText().toString());
                    pst.executeUpdate();
                    Toast.makeText(getApplicationContext(),"PRECIOS ACTUALIZADOS "  ,Toast.LENGTH_SHORT).show();

                }
                else
                {
                    pst =conex.ConexionBD().prepareStatement("UPDATE TIPO_PRECIOS SET PREC_MAY_HUEMBES=?,PREC_MAYOR=?,PREC_FLOR=?,PREC_PARROQUIA=?,PREC_PART=?,PREC_NACIONAL_MATAGALPA=?,PREC_EMPLEADOS=?  WHERE UNIDAD=3 AND COD_PROD=? ");

                    uh=(EditText)findViewById(R.id.uh);
                    pst.setString(1,uh.getText().toString());
                    um=(EditText)findViewById(R.id.um);
                    pst.setString(2,um.getText().toString());
                    uf=(EditText)findViewById(R.id.uf);
                    pst.setString(3,uf.getText().toString());
                    uparro=(EditText)findViewById(R.id.uparro);
                    pst.setString(4,uparro.getText().toString());
                    upart=(EditText)findViewById(R.id.upart);
                    pst.setString(5,upart.getText().toString());
                    umat=(EditText)findViewById(R.id.umat);
                    pst.setString(6,umat.getText().toString());
                    pst.setString(7,upart.getText().toString());
                    pst.setString(8,codigo.getText().toString());
                    pst.executeUpdate();
                    Toast.makeText(getApplicationContext(),"PRECIOS ACTUALIZADOS "  ,Toast.LENGTH_SHORT).show();
                }
            }
            Limpiar();
            rs.close();

        }
        catch (SQLException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            // return false;
        }
    }
}
