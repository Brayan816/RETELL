package app.jabrex.assot;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.assot.R;
import com.google.android.material.textfield.TextInputEditText;

public class INICIO extends AppCompatActivity {
    private static final String TEG = "SearchActivity";
    private static final int REQUEST_CODE = 1;
    public TextView FTW;
    public Button BY, BD;
    ProgressDialog progressDialog;
    public TextInputEditText TD1, TD2;
    public String A1, A2, USER;
    private Switch SELECTOR;
    ConexionMysql conexionMysql = new ConexionMysql();

    @SuppressLint({"SetTextI18n", "WrongViewCast"})
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_n_i_c_i_o);
        getWindow().setStatusBarColor(Color.rgb(242, 161, 84));
        getSupportActionBar().hide();
        TD1 = (TextInputEditText) findViewById(R.id.T221);
        TD2 = (TextInputEditText) findViewById(R.id.T222);
        BD = findViewById(R.id.button2);
        SELECTOR=findViewById(R.id.switch1);
        SELECTOR.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    VG.DBIP="192.168.1.200";
                    TD1.setText(VG.DBIP);
                }
                else {
                    VG.DBIP="192.168.0.200";
                    TD1.setText(VG.DBIP);
                }
            }
        });
        PERMISOSXD();
        BD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                A1 = TD1.getText().toString();
                A2 = TD2.getText().toString();
                if (A1 == null) {
                    A1 = "";
                }
                if (A2 == null) {
                    A2 = "";
                }
                DATOSF();
                //new Dback().execute("");
            }
        });
        CARGAR();

    }

    public void DATOSF() {
        try {
            if (A1.equals("") && A2.equals("")) {
                ToastGenerator("INGRESE LOS DATOS NECESARIOS");
            } else if (A1.equals("")) {
                ToastGenerator("INGRESE EL USUARIO");
            } else if (A2.equals("")) {
                ToastGenerator("INGRESE LA CONTRASEÑA");
            } else {
                conexionMysql.USUARIO = TD1.getText().toString();
                conexionMysql.PASSWORD = TD2.getText().toString();
                new Dback().execute("");
            }
        } catch (Exception E) {
            ToastGenerator(E.getMessage());
        }
    }

    public void AbrirQr() {
        Intent intent = new Intent(this, NAVEGACION.class);
        intent.putExtra("Nombre", USER);
        startActivity(intent);
    }


    private void PERMISOSXD() {
        Log.d(TEG, "Verificar Permisos");
        String[] PERMISOS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), PERMISOS[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(), PERMISOS[1]) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this.getApplicationContext(), PERMISOS[2]) == PackageManager.PERMISSION_GRANTED) {
        } else {

            ActivityCompat.requestPermissions(INICIO.this, PERMISOS, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PERMISOSXD();
    }

    public void ToastGenerator(String string) {
        Toast toast = Toast.makeText(this, string, Toast.LENGTH_LONG);
        toast.show();
    }

    public void CARGAR() {
        SharedPreferences S1 = getSharedPreferences("CREDENCIALES", this.MODE_PRIVATE);
        String USER1 = S1.getString("USER", "");
        String PASS1 = S1.getString("PASS", "");
        A1 = USER1;
        A2 = PASS1;
        TD1.setText(USER1);
        TD2.setText(PASS1);
        conexionMysql.USUARIO = A1;
        conexionMysql.PASSWORD = A2;
        new Dback().execute("");
    }

    public void GUARDAR(String User, String Pass1) {
        SharedPreferences S1 = getSharedPreferences("CREDENCIALES", this.MODE_PRIVATE);
        SharedPreferences.Editor EDITOR = S1.edit();
        EDITOR.putString("USER", User);
        EDITOR.putString("PASS", Pass1);
        EDITOR.commit();
    }

    class Dback extends AsyncTask<String, Void, String> {
        ProgressDialog N3 = null;
        Boolean N1=false;
        String B1 ="";
        @Override
        protected void onPreExecute() {
            N3=new ProgressDialog(INICIO.this);
            N3.show();
            N3.setContentView(R.layout.progress_dialog);
            N3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                N1 = conexionMysql.CHECK();
                //B1=conexionMysql.JOIN();
            } catch (Exception e) {

                ToastGenerator(e.getMessage());
            }
            return strings[0];
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                if (N1) {
                    AbrirQr();
                    GUARDAR(TD1.getText().toString(), TD2.getText().toString());
                } else {
                    ToastGenerator(VG.Comentario_Consulta);
                }

            }
            catch (Exception E){
                ToastGenerator(E.getMessage());
            }
            N3.dismiss();
        }
    }

}