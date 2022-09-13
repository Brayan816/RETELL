package app.jabrex.assot;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assot.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

public class   editar_datos extends AppCompatActivity {
    AutoCompleteTextView T_Tamaño,FE_DIA,FE_MES,FE_AÑO,FS_DIA,FS_MES,FS_AÑO,FI_DIA,FI_MES,FI_AÑO,FG_DIA,FG_MES,FG_AÑO,actEstado;
    public Button ENVIAR;
    public TextInputLayout AF1;
    private TextInputEditText T_Solicitante,T_Identificacion,T_Direccion,T_Barrio,T_Ciudad,T_Telefono,T_Marca,T_Serie,T_Otro,T_OrdenS,
            T_Valor,T_Abono;
    public String A1,A2,A3,A4,A5,A6,A7,A8,A9,A10,A11,A12,A13,FECHA,A14,HER1="NO",HER2="NO",HER3="NO",A15,A16,SQLQ;
    public int ABONO,VALOR,N_ORDEN;
    public ProgressBar progressBar;
    private TextView FG1;
    public RadioButton R_Costado,R_Hombro,R_Banda;
    private TextView DATOS;
    private LLANTA llanta=new LLANTA();
    private int CASO =0;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_datos);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);
        getSupportActionBar().hide();
        try {
            String[] TPU5 = new String[]{"INGRESO", "ESCARIADO","PRESENTACION DE MATERIALES","TESTURIZADO","CEMENTADO","INSTALACION DE MATERIALES","RELLENO CON CAUCHO","VULCANIZACION","TERMINACION","LISTO PARA ENTREGA","ENTREGADO"};
            ArrayAdapter<String> adapter4 = new ArrayAdapter<>( this, R.layout.dropdownitem, TPU5);
            actEstado=findViewById(R.id.actEstado);
            actEstado.setInputType(InputType.TYPE_NULL);
            actEstado.setAdapter(adapter4);
            FG1=findViewById(R.id.INI);
            T_Solicitante = findViewById(R.id.T_Solicitante);
            T_Identificacion = findViewById(R.id.T_Identificacion);
            T_Direccion = findViewById(R.id.T_Direccion);
            T_Barrio = findViewById(R.id.T_Barrio);
            T_Ciudad = findViewById(R.id.T_Ciudad);
            T_Telefono = findViewById(R.id.T_Telefono);
            T_Marca = findViewById(R.id.T_Marca);
            T_Tamaño = findViewById(R.id.T_Tama);
            T_Serie = findViewById(R.id.T_Serie);
            R_Costado = findViewById(R.id.HE1);
            R_Banda = findViewById(R.id.HE2);
            R_Hombro = findViewById(R.id.R1OP);
            T_Otro = findViewById(R.id.T_Otro);
            //FECHA ENTREGA
            FE_DIA = findViewById(R.id.E_DIA);
            FE_MES = findViewById(R.id.E_MES);
            FE_AÑO = findViewById(R.id.E_AÑO);
            //FECHA DE SALIDA PROGRAMADA
            FS_DIA = findViewById(R.id.S_DIA);
            FS_MES = findViewById(R.id.S_MES);
            FS_AÑO = findViewById(R.id.S_AÑO);
            //FECHA DE SALIDA DE LA LLANTA
            FI_DIA = findViewById(R.id.DIAINGRESO);
            FI_MES = findViewById(R.id.MESINGRESO);
            FI_AÑO = findViewById(R.id.AÑOINGRESO);
            //FECHA GARANTIA
            FG_DIA = findViewById(R.id.DIAGARANTIA);
            FG_MES = findViewById(R.id.MESGARANTIA);
            FG_AÑO = findViewById(R.id.AÑOGARANTIA);

            T_OrdenS = findViewById(R.id.T_OrdenS);
            T_Valor = findViewById(R.id.T_Valor);
            T_Abono = findViewById(R.id.T_Abono);
            String[] TPU4 = new String[]{"100R", "10R22.5", "11R22.5", "235-285R", "8.25R", "900R", "9R22.5", "255/75R", "1000R", "11R24.5", "12R22.5", "295/80R22.5", "305/70R/22.5", "315/70R22.5", "365R", "12R24.5", "1200R20", "1200R24", "13R22.5", "R15/80R", "385R"};
            ArrayAdapter<String> A_Tamaño = new ArrayAdapter<>(this, R.layout.dropdownitem, TPU4);
            T_Tamaño.setAdapter(A_Tamaño);
            String[] TP_DIA = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
            ArrayAdapter<String> F_DIA = new ArrayAdapter<>(this, R.layout.dropdownitem, TP_DIA);
            String[] TP_MES = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
            ArrayAdapter<String> F_MES = new ArrayAdapter<>(this, R.layout.dropdownitem, TP_MES);
            String[] TP_AÑO = new String[]{"19", "20", "21", "22", "23", "24", "25"};
            ArrayAdapter<String> F_AÑO = new ArrayAdapter<>(this, R.layout.dropdownitem, TP_AÑO);
            FE_DIA.setAdapter(F_DIA);
            FE_DIA.setInputType(InputType.TYPE_NULL);
            FE_MES.setAdapter(F_MES);
            FE_MES.setInputType(InputType.TYPE_NULL);
            FE_AÑO.setAdapter(F_AÑO);
            FE_AÑO.setInputType(InputType.TYPE_NULL);
            FS_DIA.setAdapter(F_DIA);
            FS_DIA.setInputType(InputType.TYPE_NULL);
            FS_MES.setAdapter(F_MES);
            FS_MES.setInputType(InputType.TYPE_NULL);
            FS_AÑO.setAdapter(F_AÑO);
            FS_AÑO.setInputType(InputType.TYPE_NULL);

            FI_DIA.setAdapter(F_DIA);
            FI_DIA.setInputType(InputType.TYPE_NULL);
            FI_MES.setAdapter(F_MES);
            FI_MES.setInputType(InputType.TYPE_NULL);
            FI_AÑO.setAdapter(F_AÑO);
            FI_AÑO.setInputType(InputType.TYPE_NULL);
            FG_DIA.setAdapter(F_DIA);
            FG_DIA.setInputType(InputType.TYPE_NULL);
            FG_MES.setAdapter(F_MES);
            FG_MES.setInputType(InputType.TYPE_NULL);
            FG_AÑO.setAdapter(F_AÑO);
            FG_AÑO.setInputType(InputType.TYPE_NULL);
            R_Costado.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (llanta.Costado.equals("NO")) {
                        llanta.Costado = "SI";
                    } else {
                        R_Costado.setChecked(false);
                        llanta.Costado = "NO";
                    }
                }
            });
            R_Banda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (llanta.Banda.equals("NO")) {
                        llanta.Banda = "SI";
                    } else {
                        R_Banda.setChecked(false);
                        llanta.Banda = "NO";
                    }
                }
            });
            R_Hombro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (llanta.Hombro.equals("NO")) {
                        llanta.Hombro = "SI";
                    } else {
                        R_Hombro.setChecked(false);
                        llanta.Hombro = "NO";
                    }
                }
            });
            ENVIAR = findViewById(R.id.SEND);
            ENVIAR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActualizarData();
                }
            });
            BuscarDatos();
        }
        catch (Exception E){
            ToastGenerator(E.getMessage());
        }

        }
    private void BuscarDatos(){
        llanta.Orden=VG.ORDENS;
        CASO=0;
        new  Dback().execute("");

    }
    private void CARGARDATA(){

            T_Solicitante.setText(llanta.Solicitante);
            T_Identificacion.setText(llanta.Identificacion);
            A1=llanta.Identificacion;
            T_Direccion.setText(llanta.Direccion);
            T_Barrio.setText(llanta.Barrio);
            T_Ciudad.setText(llanta.Ciudad);
            T_Telefono.setText(llanta.Telefono);
            T_Marca.setText(llanta.Marca);
            T_Tamaño.setText(llanta.Tamaño);
            T_Serie.setText(llanta.Serie);
            R_Costado.setChecked(llanta.Costado.equals("SI"));
            R_Banda.setChecked(llanta.Banda.equals("SI"));
            R_Hombro.setChecked(llanta.Hombro.equals("SI"));

                    String[] FechaI=Datos_Fechas(llanta.Fecha);
                    FI_DIA.setText(FechaI[1],false);
                    FI_MES.setText(FechaI[0],false);
                    FI_AÑO.setText(FechaI[2],false);
                    //FECHA DE SALIDA PROGRAMADA
                    String[] FechaS=Datos_Fechas(llanta.FechaS);
                    FS_DIA.setText(FechaS[1],false);
                    FS_MES.setText(FechaS[0],false);
                    FS_AÑO.setText(FechaS[2],false);
                    //FECHA DE ENTREGA
                    String[] FechaE=Datos_Fechas(llanta.FechaE);
                    FE_DIA.setText(FechaE[1],false);
                    FE_MES.setText(FechaE[0],false);
                    FE_AÑO.setText(FechaE[2],false);

                    //FECHA DE GARANTIA
                    String[] FechaG=Datos_Fechas(llanta.FechaG);
                    FG_DIA.setText(FechaG[1],false);
                    FG_MES.setText(FechaG[0],false);
                    FG_AÑO.setText(FechaG[2],false);

            T_OrdenS.setText(llanta.Orden);
            T_OrdenS.setInputType(InputType.TYPE_NULL);
            T_Valor.setText(String.valueOf(llanta.Valor));
            T_Valor.setInputType(InputType.TYPE_CLASS_NUMBER);
            T_Abono.setText(String.valueOf(llanta.Abono));
            T_Abono.setInputType(InputType.TYPE_CLASS_NUMBER);
            actEstado.setText(llanta.E_A,false);
            ToastGenerator("Datos Actualizados");


    }
    private String[] Datos_Fechas(String FECHA){
        String[] S1 = new String[]{"", "",""};
        List<String> S2=null;
        try {
            if(2 > FECHA.length()) {
            }
            else {
                S2=Arrays.asList(FECHA.split("/"));
                S1[0]=S2.get(0);
                S1[1]=S2.get(1);
                S1[2]=S2.get(2);
            }
        }
        catch (Exception E){
            VG.Comentario_Consulta=E.getMessage();
        }

        return S1;
    }

    private void ToastGenerator(String string){
        Toast toast = Toast.makeText(this, string, Toast.LENGTH_LONG);
        toast.show();
    }
    private void ActualizarData(){
        try {
            llanta.Solicitante=T_Solicitante.getText().toString();
            llanta.Identificacion=T_Identificacion.getText().toString();
            llanta.Direccion=T_Direccion.getText().toString();;
            llanta.Barrio=T_Barrio.getText().toString();
            llanta.Ciudad=T_Ciudad.getText().toString();
            llanta.Telefono=T_Telefono.getText().toString();
            llanta.Marca=T_Marca.getText().toString();
            llanta.Tamaño=T_Tamaño.getText().toString();
            llanta.Serie=T_Serie.getText().toString();
            llanta.Otro=T_Otro.getText().toString();
            llanta.FechaE=FE_MES.getText().toString()+"/"+FE_DIA.getText().toString()+"/"+FE_AÑO.getText().toString();
            llanta.FechaS=FS_MES.getText().toString()+"/"+FS_DIA.getText().toString()+"/"+FS_AÑO.getText().toString();
            llanta.Fecha=FI_MES.getText().toString()+"/"+FI_DIA.getText().toString()+"/"+FI_AÑO.getText().toString();
            llanta.FechaG=FG_MES.getText().toString()+"/"+FG_DIA.getText().toString()+"/"+FG_AÑO.getText().toString();
            llanta.Valor=Integer.parseInt(T_Valor.getText().toString());
            llanta.Abono=Integer.parseInt(T_Abono.getText().toString());
            llanta.E_A=actEstado.getText().toString();
            CASO=1;
            new Dback().execute("");
        }
        catch (Exception E){
            ToastGenerator("Ocurrio un error al realizar la actualizacion de los datos");
        }
    }
    private class Dback extends AsyncTask<String, Void, String> {
        ProgressDialog N3 = null;
        boolean Check=false;
        @Override
        protected void onPreExecute() {
            N3=new ProgressDialog(editar_datos.this);
            N3.show();
            N3.setContentView(R.layout.progress_dialog);
            N3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                if(CASO==0){
                    Check=llanta.Buscar_Datos();
                }
                else if (CASO==1){
                    Check=llanta.Editar_Datos(A1);
                }

            } catch (Exception e) {

                VG.Comentario_Consulta=e.getMessage();
            }
            return strings[0];
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                if(CASO==0){
                    if (Check) {
                        ToastGenerator("Datos se han cargado con exito");
                        CARGARDATA();
                    } else {
                        ToastGenerator(VG.Comentario_Consulta);
                    }
                }
                else if(CASO==1){
                    if (Check) {
                        ToastGenerator("Datos se han cambiado con exito");
                    } else {
                        ToastGenerator(VG.Comentario_Consulta);
                        FG1.setText(VG.Comentario_Consulta);
                    }
                }

            }
            catch (Exception E){
                ToastGenerator(E.getMessage());
            }
            N3.dismiss();
        }
    }
}