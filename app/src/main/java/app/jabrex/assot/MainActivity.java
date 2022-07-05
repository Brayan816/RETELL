package app.jabrex.assot;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.text.Layout;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assot.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class MainActivity extends AppCompatActivity {
    public TextView Pmarca,PORDEN,PSERIE,PFECHA,PVALOR,PABONO,PTAMA,PSOLI,PDIRE,PBARRIO,PCIUDAD,PTELEFONO,PFECHAS,PFECHAE,TIPOS;
    public Button Bmateriaeles;
    public ProgressBar P1;
    public ImageButton APosicion,AEstado,IBA_Abono,GARANTIAB,EditarDatos;
    public String DF1;
    public String DF2;
    public String DF3;
    public TextInputLayout PABONOA;
    public TextInputEditText ABONOX;
    public TextView TW1,TW2,TW3,GARANTIATEXT;
    AutoCompleteTextView LOTE,POSICION,ESTADO,GARANTIA;
    public TextInputLayout T11,T12,H_ESTADO,GARANTIAP;
    public ImageButton PHOTO1,PHOTO2,PHOTO3,MATETEXT;
    private EditText QUERY;
    public ImageView EABONO,ETSERVICIO;
    public String Soli,nide,dire,barrio,ciudad,tel,mllanta,tamaño,serie,costado,banda,hombro,otro,fecha,orden_S,valor,abono,EA,LOTED,POSID,PFECHAX,FECHAE,FECHAG,SERVICIO;
    public LLANTA llanta =new LLANTA();
    public int Caso;
    Dialog dialog;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);
        getSupportActionBar().hide();
        StrictMode.enableDefaults();
        QUERY=findViewById(R.id.editTextTextPersonName);
        QUERY.setVisibility(View.INVISIBLE);
        //EDITAR DATOS
        EditarDatos=findViewById(R.id.Editar_A);
        EditarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AbrirEditarDatos();
            }
        });
        //DATOS LLANTA
        Pmarca= findViewById(R.id.Pmarca);
        PTAMA=  findViewById(R.id.Ptama);
        PSERIE= findViewById(R.id.Pserie);
        PORDEN= findViewById(R.id.Porden);
        PFECHA= findViewById(R.id.Pfecha);
        PFECHAS=findViewById(R.id.PfechaS);
        PFECHAE=findViewById(R.id.PfechaX);
        PVALOR= findViewById(R.id.Pvalor);
        PABONO= findViewById(R.id.Pabono);
        PORDEN= findViewById(R.id.Porden);
        TIPOS=  findViewById(R.id.PTIPO2);
        //ICONOS COLORES
        PHOTO1=findViewById(R.id.PHOTO1);
        CambiarIconoRojo(PHOTO1);
        PHOTO2=findViewById(R.id.PHOTO2);
        CambiarIconoRojo(PHOTO2);
        PHOTO3=findViewById(R.id.PHOTO3);
        CambiarIconoRojo(PHOTO3);
        PHOTO1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AbrirFOTOS();
            }
        });
        PHOTO2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AbrirFOTOS();
            }
        });
        PHOTO3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AbrirFOTOS();
            }
        });
        ETSERVICIO=findViewById(R.id.Tipos_S);
        EABONO=findViewById(R.id.E_ABONO);
        //DATOS DEL SOLICITANTE
        PSOLI=      findViewById(R.id.Pnombre);
        PDIRE=      findViewById(R.id.Pdire);
        PBARRIO=    findViewById(R.id.Pbarrio);
        PCIUDAD=    findViewById(R.id.Pciudad);
        PTELEFONO=  findViewById(R.id.Ptele);
        //UBICACION LLANTA
            //LOTE
        String[] TPU3 = new String[]{"LOTE 1", "LOTE 2","LOTE 3","LOTE 4","LOTE 5","LOTE 6","LOTE 7","LOTE 8","LOTE 9","LOTE 10","LOTE 11","LOTE 12","LOTE 13","LOTE 14","LOTE 15","LOTE 16","LOTE 17","LOTE 18","LOTE 19","LOTE 20","LOTE 21","LOTE 22","LOTE 23","LOTE 24","LOTE 25"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>( this, R.layout.dropdownitem, TPU3);
        LOTE=findViewById(R.id.LOTE);
        LOTE.setInputType(InputType.TYPE_NULL);
        LOTE.setAdapter(adapter2);
            //POSICION
        String[] TPU4 = new String[]{"P1", "P2","P3","P4","P5","P6","P7","P8","P9","P10","P11","P12","P13","P14","P15","P16"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>( this, R.layout.dropdownitem, TPU4);
        POSICION=findViewById(R.id.POSICION);
        POSICION.setInputType(InputType.TYPE_NULL);
        POSICION.setAdapter(adapter3);
            //ACTUALIZAR POSICION
        APosicion=findViewById(R.id.ibPosicion);
        APosicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DF1=LOTE.getText().toString();
                DF2=POSICION.getText().toString();
                if(DF1.equals("") && DF2.equals(""))
                {
                    ToastGenerator("SELECCIONE UN LOTE O UNA POSICION PARA ACTUALIZAR");
                }
                else
                {
                    if(!DF1.equals("")){
                        llanta.Ubicacion=DF1;
                    }
                    if(!DF2.equals("")){
                        llanta.Posicion=DF2;
                    }
                    Caso=1;
                    new Dback().execute("");
                }
            }

        });
        //ESTADO LLANTA
        String[] TPU5 = new String[]{"INGRESO", "ESCARIADO","PRESENTACION DE MATERIALES","TESTURIZADO","CEMENTADO","INSTALACION DE MATERIALES","RELLENO CON CAUCHO","VULCANIZACION","TERMINACION","LISTO PARA ENTREGA","ENTREGADO"};
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>( this, R.layout.dropdownitem, TPU5);
        ESTADO=findViewById(R.id.ESTADO);
        ESTADO.setInputType(InputType.TYPE_NULL);
        ESTADO.setAdapter(adapter4);
        //ACTUALIZAR ESTADO
        AEstado=findViewById(R.id.Aestado);
        AEstado.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                DF3=ESTADO.getText().toString();
                llanta.E_A=DF3;
                Caso=2;
                new Dback().execute("");
            }
        });
        P1=findViewById(R.id.PRAD4);
        TW1=findViewById(R.id.PPRE3);
        TW2=findViewById(R.id.PPRE1);
        TW3=findViewById(R.id.PPRE2);
        T11=findViewById(R.id.Plote);
        T12=findViewById(R.id.PPOSI);
        H_ESTADO=findViewById(R.id.PESTADO);
        //ABONO
        ABONOX=findViewById(R.id.DF4);
        PABONOA=findViewById(R.id.PPABONO);
        IBA_Abono=findViewById(R.id.ibAbono);
        IBA_Abono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Caso=3;
                new Dback().execute("");
            }
        });
        //GARANTIA
        GARANTIATEXT=findViewById(R.id.PPRE4);
        GARANTIA=findViewById(R.id.DF5);
        GARANTIAP=findViewById(R.id.PGARANTIA);
        String[] TPU7 = new String[]{"1", "2","3"};
        ArrayAdapter<String> adapter5= new ArrayAdapter<>( this, R.layout.dropdownitem, TPU7);
        GARANTIA.setInputType(InputType.TYPE_NULL);
        GARANTIA.setAdapter(adapter5);
        GARANTIAB=findViewById(R.id.A_Garantia);
        GARANTIAB.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                try {
                    String FWX = llanta.FechaE, U1, U2, U3;
                    U1 = GARANTIA.getText().toString();
                    if (U1.equals("1") || U1.equals("2") || U1.equals("3")) {
                        String[] parts = FWX.split("/");
                        int X1, X2, X3, AF1;
                        X1 = Integer.parseInt(parts[0]);
                        X2 = Integer.parseInt(parts[1]);
                        X3 = Integer.parseInt(parts[2]);
                        AF1 = Integer.parseInt(U1);
                        LocalDate PROXM = LocalDate.of(X3, X2, X1).plusMonths(AF1);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        String formattedString = PROXM.format(formatter);
                        FECHAE = formattedString;
                        llanta.FechaG = formattedString;
                        Caso=4;
                        new Dback().execute("");
                    } else {
                        ToastGenerator("POR VAFOR SELECCIONE LOS MESES DE GARANTIA");
                    }
                }
                catch (Exception E)
                {
                    ToastGenerator(E.getMessage());
                }
            }

        });
        Bmateriaeles=findViewById(R.id.PPRE5);
        dialog=new Dialog(this);
        Bmateriaeles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowPopUP();
            }
        });
        BuscarLlanta();
    }
    public void ShowPopUP(){
        try{
            dialog.setContentView(R.layout.materiales_popup);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            AutoCompleteTextView M1,M2,M3,M4,M5,M6;
            TextInputEditText GM1,GM2,GM3,GM4,GM5,GM6;
            TextInputEditText MB1,MB2,MB3,MB4,MB5,MB6;
            Button Guadar_Materiales;
            //CINTURON RADIAL
            M1=dialog.findViewById(R.id.S1_CinturonRadial);
            String[] Materiales_1 = new String[]{ "No Aplica", "RETELL", "CR1", "CR2", "CR3", "CR4", "CR5", "CR5+", "CR6", "CR6+", "CR7", "CR8", "CR8+", "CR9", "CR9+"};
            ArrayAdapter<String> A_Materiales1 = new ArrayAdapter<>( this, R.layout.dropdownitem, Materiales_1);
            M1.setInputType(InputType.TYPE_NULL);
            M1.setAdapter(A_Materiales1);
            M1.setText(llanta.M1,false);
            GM1=dialog.findViewById(R.id.S2_CinturonRadial);
            GM1.setText(llanta.N1);
            GM1.setInputType(InputType.TYPE_CLASS_NUMBER);
            //PROTECTORES
            M2=dialog.findViewById(R.id.S1_Protectores);
            String[] Materiales_2 = new String[]{"No Aplica", "RETELL", "P1A", "P2A", "P2AA", "P2B", "P3A", "P3AA", "P3B", "P4A", "P4B", "P4C", "P5A", "P5B", "P5C", "PXL", "PT5A", "PT5B", "PT5C", "PT6A", "PT6B", "PT6C", "PT7A", "PT7B", "PT7C", "PT8A", "PT8B", "PT8C", "PT9A", "PT9B", "PT9C"};
            ArrayAdapter<String> A_Materiales2 = new ArrayAdapter<>( this, R.layout.dropdownitem, Materiales_2);
            M2.setInputType(InputType.TYPE_NULL);
            M2.setAdapter(A_Materiales2);
            M2.setText(llanta.M2,false);
            GM2=dialog.findViewById(R.id.S2_Protectores);
            GM2.setText(llanta.N2);
            GM2.setInputType(InputType.TYPE_CLASS_NUMBER);
            //UNIDAD U
            M3=dialog.findViewById(R.id.S1_UnidadU);
            String[] Materiales_3 = new String[]{"No Aplica", "RETELL", "U1", "U2", "U3", "U4", "U5", "U6", "U7", "U8", "U9", "U10", "U11", "U12", "U13"};
            ArrayAdapter<String> A_Materiales3 = new ArrayAdapter<>( this, R.layout.dropdownitem, Materiales_3);
            M3.setInputType(InputType.TYPE_NULL);
            M3.setAdapter(A_Materiales3);
            M3.setText(llanta.M3,false);
            GM3=dialog.findViewById(R.id.S2_UnidadU);
            GM3.setText(llanta.N3);
            GM3.setInputType(InputType.TYPE_CLASS_NUMBER);
            //UNIDAD F
            M4=dialog.findViewById(R.id.S1_UnidadF);
            String[] Materiales_4 = new String[]{"No Aplica", "RETELL", "F000", "F00", "F0", "F0sp", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12", "F13" };
            ArrayAdapter<String> A_Materiales4 = new ArrayAdapter<>( this, R.layout.dropdownitem, Materiales_4);
            M4.setInputType(InputType.TYPE_NULL);
            M4.setAdapter(A_Materiales4);
            M4.setText(llanta.M4,false);
            GM4=dialog.findViewById(R.id.S2_UnidadF);
            GM4.setText(llanta.N4);
            GM4.setInputType(InputType.TYPE_CLASS_NUMBER);
            //UNIDAD MC
            M5=dialog.findViewById(R.id.S1_UnidadMC);
            String[] Materiales_5 = new String[]{ "No Aplica", "RETELL", "M-C2", "M-C3", "M-C4", "M-C5", "M-C6", "M-C7", "M-C8", "M-C9", "M-C10", "C001", "C000+", "C0", "CR1" };
            ArrayAdapter<String> A_Materiales5 = new ArrayAdapter<>( this, R.layout.dropdownitem, Materiales_5);
            M5.setInputType(InputType.TYPE_NULL);
            M5.setAdapter(A_Materiales5);
            M5.setText(llanta.M5,false);
            GM5=dialog.findViewById(R.id.S2_UnidadMC);
            GM5.setText(llanta.N5);
            GM5.setInputType(InputType.TYPE_CLASS_NUMBER);
            //UNIDAD BC
            M6=dialog.findViewById(R.id.S1_UnidadBC);
            String[] Materiales_6 = new String[]{ "No Aplica", "RETELL", "BC2", "BC3", "BC4", "BC5", "BC6", "BC7", "BC8", "BC9", "BC10", "BCXL", "BCi", "BCe" };
            ArrayAdapter<String> A_Materiales6 = new ArrayAdapter<>( this, R.layout.dropdownitem, Materiales_6);
            M6.setInputType(InputType.TYPE_NULL);
            M6.setAdapter(A_Materiales6);
            M6.setText(llanta.M6,false);
            GM6=dialog.findViewById(R.id.S2_UnidadBC);
            GM6.setText(llanta.N6);
            GM6.setInputType(InputType.TYPE_CLASS_NUMBER);
            //CAUCHO COJIN
            MB1=dialog.findViewById(R.id.S2_CauchoCojin);
            MB1.setText(llanta.PM1);
            //CAUCHO BLANDO
            MB2=dialog.findViewById(R.id.S2_CauchoBlando);
            MB2.setText(llanta.PM2);
            //CAUCHO DURO
            MB3=dialog.findViewById(R.id.S2_CauchoDuro);
            MB3.setText(llanta.PM3);
            //CORDON FABRE
            MB4=dialog.findViewById(R.id.S2_CordonFabre);
            MB4.setText(llanta.PM4);
            //CEMENTO
            MB5=dialog.findViewById(R.id.S2_Cemento);
            MB5.setText(llanta.PM5);
            //DISOLVENTE
            MB6=dialog.findViewById(R.id.S2_Disolvente);
            MB6.setText(llanta.PM6);
            Guadar_Materiales=dialog.findViewById(R.id.Guardar_Materiales);
            Guadar_Materiales.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    llanta.M1=M1.getText().toString();
                    llanta.M2=M2.getText().toString();
                    llanta.M3=M3.getText().toString();
                    llanta.M4=M4.getText().toString();
                    llanta.M5=M5.getText().toString();
                    llanta.M6=M6.getText().toString();
                    llanta.N1=GM1.getText().toString();
                    llanta.N2=GM2.getText().toString();
                    llanta.N3=GM3.getText().toString();
                    llanta.N4=GM4.getText().toString();
                    llanta.N5=GM5.getText().toString();
                    llanta.N6=GM6.getText().toString();
                    llanta.PM1=MB1.getText().toString();
                    llanta.PM2=MB2.getText().toString();
                    llanta.PM3=MB3.getText().toString();
                    llanta.PM4=MB4.getText().toString();
                    llanta.PM5=MB5.getText().toString();
                    llanta.PM6=MB6.getText().toString();
                    Caso=10;
                    new Dback().execute("");

                }
            });
            dialog.show();
        }
        catch (Exception E){
            ToastGenerator(E.getMessage());
        }
    }


    private void OcultarGarantia()
    {
        GARANTIA.setVisibility(View.GONE);
        GARANTIATEXT.setVisibility(View.GONE);
        GARANTIAB.setVisibility(View.GONE);
        GARANTIAP.setVisibility(View.GONE);
    }
    public void MostrarGarantia(){
        GARANTIA.setVisibility(View.VISIBLE);
        GARANTIATEXT.setVisibility(View.VISIBLE);
        GARANTIAB.setVisibility(View.VISIBLE);
        GARANTIAP.setVisibility(View.VISIBLE);
    }

    public void AbrirEditarDatos() {
        Intent intent = new Intent(this, editar_datos.class);
        startActivity(intent);
    }

    public void AbrirFOTOS() {
        Intent intent = new Intent(this, fotos.class);
        startActivity(intent);
    }

    private void BuscarLlanta()
    {
        Caso=0;
        new Dback().execute("");
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

    @SuppressLint("SetTextI18n")
    private void C2()
    {
        //DATOS DE LA LLANTA
        Pmarca.setText("MARCA: "+llanta.Marca);
        PTAMA.setText("TAMAÑO: "+llanta.Tamaño);
        PORDEN.setText("#ORDEN DE SERVICIO: "+llanta.Orden);
        PSERIE.setText("SERIE: "+llanta.Serie);
        if(llanta.Fecha.equals("")){
            PFECHA.setText("FECHA DE INGRESO: sin fecha de ingreso");
        }
        else
        {
            String[] FechaI=Datos_Fechas(llanta.Fecha);
            PFECHA.setText("FECHA DE INGRESO: "+ FechaI[1]+"/"+FechaI[0]+"/"+FechaI[2]);
        }
        if(llanta.FechaS.equals("") || llanta.FechaS.equals("//")){
            PFECHAS.setText("FECHA DE ENTREGA: Sin fehcha programada");
        }
        else
        {
            String[] FechaS=Datos_Fechas(llanta.FechaS);
            PFECHAS.setText("FECHA DE ENTREGA: "+ FechaS[1]+"/"+FechaS[0]+"/"+FechaS[2]);
        }
        if(llanta.FechaE.equals("")|| llanta.FechaE.equals("//")){
            PFECHAE.setText("FECHA DE SALIDA: No se ha Entregado");
        }
        else {
            String[] FechaE=Datos_Fechas(llanta.FechaE);
            PFECHAE.setText("FECHA DE SALIDA: "+ FechaE[1]+"/"+FechaE[0]+"/"+FechaE[2]);
        }
        PVALOR.setText("VALOR: "+llanta.Valor);
        PABONO.setText("ABONO: "+llanta.Abono);
        //DATOS DEL SOLIITANTE
        PSOLI.setText("NOMBRE: "+llanta.Solicitante);
        PDIRE.setText("DIR: "+llanta.Direccion);
        PBARRIO.setText("BARRIO: "+llanta.Barrio);
        PCIUDAD.setText("CIUDAD: "+llanta.Ciudad);
        PTELEFONO.setText("TEL: "+llanta.Telefono);
        LOTE.setText(llanta.Ubicacion,false);
        POSICION.setText(llanta.Posicion,false);
        ESTADO.setText(llanta.E_A,false);

        if(llanta.TipoS.equals(""))
        {
            TIPOS.setText("REPARACION");
        }
        else {
            TIPOS.setText(SERVICIO);
        }

        if(llanta.TipoS.equals("GARANTIA"))
        {
            TIPOS.setTextColor(Color.rgb(204,51,0));
        }
        else if(llanta.TipoS.equals("VENTA"))
        {
            TIPOS.setTextColor(Color.rgb(242,161,84));
        }
        else {
            TIPOS.setTextColor(Color.rgb(51,153,0));
        }

        if(llanta.Valor>llanta.Abono)
        {
            int A2=llanta.Valor-llanta.Abono;
            PABONOA.setHelperText("MAXIMO = "+ A2);
        }

        if(llanta.Abono!=llanta.Valor)
        {
            EABONO.setColorFilter(Color.rgb(204,51,0),PorterDuff.Mode.SRC_IN);
        }
        else {
            EABONO.setColorFilter(Color.rgb(51,153,0),PorterDuff.Mode.SRC_IN);
            PABONOA.setVisibility(View.GONE);
            ABONOX.setVisibility(View.GONE);
            IBA_Abono.setVisibility(View.GONE);
            TW1.setVisibility(View.GONE);
            ConstraintLayout.LayoutParams params=(ConstraintLayout.LayoutParams)TW1.getLayoutParams();
            params.setMargins(0,0,0,40);
            TW1.setLayoutParams(params);
        }

        if(llanta.E_A.equals("ENTREGADO"))
        {
            ETSERVICIO.setColorFilter(Color.rgb(51,153,0),PorterDuff.Mode.SRC_IN);
            if(!llanta.FechaG.equals("")){
                OcultarGarantia();
            }
        }
        else if(llanta.E_A.equals("LISTO PARA ENTREGA"))
        {
            ETSERVICIO.setColorFilter(Color.rgb(51,153,0),PorterDuff.Mode.SRC_IN);
            OcultarGarantia();
        }
        else{
            ETSERVICIO.setColorFilter(Color.rgb(204,51,0),PorterDuff.Mode.SRC_IN);
            OcultarGarantia();
        }
    }

    public void ToastGenerator(String string){
        Toast toast = Toast.makeText(this, string, Toast.LENGTH_LONG);
        toast.show();
    }
    public void CambiarIconoRojo(ImageButton imageButton){
        imageButton.setColorFilter(Color.rgb(204,51,0),PorterDuff.Mode.SRC_IN);
    }
    public void CambiarIconoAverde(ImageButton imageButton){
        imageButton.setColorFilter(Color.rgb(204,51,0),PorterDuff.Mode.SRC_IN);
    }
    private class Dback extends AsyncTask<String, Void, String> {
        ProgressDialog N3 = null;
        Pair<Boolean, String> N1=null;
        //Buscar Datos
        boolean Check=false;
        @Override
        protected void onPreExecute() {
            N3=new ProgressDialog(MainActivity.this);
            N3.show();
            N3.setContentView(R.layout.progress_dialog);
            N3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(String... strings) {
            try {
                if(Caso==0){
                        llanta.Orden=VG.ORDENS;
                        Check=llanta.Buscar_Datos();
                }
                else if(Caso==1) {
                    Check= llanta.ActualizarUbicacion();
                }
                else if(Caso==2){
                    Check=llanta.ActualizarEstado();
                }
                else if(Caso==3){
                    int A1;
                    if(ABONOX.getText() != null){
                        A1=Integer.parseInt(ABONOX.getText().toString());
                        Check=llanta.AgregarAbono(A1);
                    }
                }
                else if(Caso==4){
                    Check=llanta.A_FechaG();
                }
                else if(Caso==10){
                    Check=llanta.ActualizarMateriales();
                }

            } catch (Exception e) {

                ToastGenerator(e.getMessage());
            }
            return strings[0];
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                if(Caso==0){
                    if(Check)
                    {
                        C2();
                    }
                    else {
                        ToastGenerator(VG.Comentario_Consulta);
                        //QUERY.setText(VG.Comentario_Consulta);
                    }
                }
                else if(Caso==1){
                    if(Check){
                        ToastGenerator("Datos actualizandos con Exito");
                    }
                    else {
                        ToastGenerator(VG.Comentario_Consulta);
                    }

                }
                else if(Caso==2){
                    if(Check){
                        ToastGenerator("Estado Actualizado con Exito");
                        if(llanta.E_A.equals("ENTREGADO") && llanta.FechaG.equals("")){
                            MostrarGarantia();
                        }
                    }
                    else {
                        ToastGenerator("Error al actualizar el Estado");
                    }
                }
                else if(Caso==3){
                    if(Check){
                        ToastGenerator("Abono actualizado Exitosamente");
                        PABONOA.setHelperText("MAXIMO = "+ (llanta.Valor-llanta.Abono));
                    }
                    else {
                        ToastGenerator("No se puede realizar el Abono, Verficar ");
                    }
                }
                else if (Caso==4){
                    if(Check){
                        ToastGenerator("Fecha de garantia actualizada con exito");
                        GARANTIA.setText(llanta.FechaG);
                    }
                    else {
                        ToastGenerator("Ocurrio un error al actualizar la fecha de garantia");
                    }
                }
                else if(Caso==10){
                    if(Check){
                        ToastGenerator("Materiales actualizados con Exito");
                        dialog.dismiss();
                    }
                    else {
                        ToastGenerator(VG.Comentario_Consulta);

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