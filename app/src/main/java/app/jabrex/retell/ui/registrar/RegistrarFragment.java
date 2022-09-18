package app.jabrex.retell.ui.registrar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.retell.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import app.jabrex.retell.provider.retellServer.llanta;
import app.jabrex.retell.UC;


public class RegistrarFragment extends Fragment {

    private RegistrarViewModel registrarViewModel;
    AutoCompleteTextView AT1,AT2,AT3,AT1S,AT2S,AT3S,Ttamaño;
    Button ENVIAR;
    private Boolean E_C=false;
    private TextInputEditText Tsolicitante,Tidentificacion,Tdireccion,Tbarrio,Tciudad,Ttelefono,Tmarca,Tserie,Totro,Torden,Tvalor,Tabono;
    public String A11,A12,A13,A11S,A12S,A13S;
    public String Solicitante;
    public String Identificacion;
    public String Direccion;
    public String Barrio;
    public String Ciudad;
    public String Telefono;
    public String Marca;
    public String Tamaño;
    public String Serie;
    public String Costado="NO";
    public String Banda="NO";
    public String Hombro="NO";
    public String Otro;
    public String Fecha;
    public String Orden;
    public int Valor;
    public int Abono;
    public String E_A;
    public String Ubicacion;
    public String Posicion;
    public String FechaS;
    public ProgressBar P1;
    public TextInputLayout T1,T11,T12,T13;
    public RadioButton H1,H2,H3;
    private int Caso=0;
    app.jabrex.retell.provider.retellServer.llanta llanta=new llanta();

    @SuppressLint({"SetTextI18n", "CutPasteId"})
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        registrarViewModel =
                new ViewModelProvider(this).get(RegistrarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_registrar, container, false);
        registrarViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        T1=root.findViewById(R.id.sliTD1);
        T11=root.findViewById(R.id.sliTD11);
        T12=root.findViewById(R.id.sliTD12);
        T13=root.findViewById(R.id.sliTD13);
        T1.setHelperTextEnabled(false);
        //FECHA INGRESO
        AT1=root.findViewById(R.id.sliT11);
        AT2=root.findViewById(R.id.sliT12);
        AT3=root.findViewById(R.id.sliT13);
        //FECHA SALIDA
        AT1S=root.findViewById(R.id.sliT11S);
        AT2S=root.findViewById(R.id.sliT12S);
        AT3S=root.findViewById(R.id.sliT13S);
        //PROGRESSBAR
        P1=root.findViewById(R.id.PRAD);

        //DIA
        String[] TPU = new String[]{"1", "2", "3", "4", "5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>( getContext(), R.layout.dropdownitem, TPU);
        AT1.setAdapter(adapter);
        AT1S.setAdapter(adapter);
        //MES
        String[] TPU3 = new String[]{"18", "19", "20", "21","22","23"};

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>( getContext(), R.layout.dropdownitem, TPU3);
        AT2.setAdapter(adapter2);
        AT2S.setAdapter(adapter2);
        //AÑO
        String[] TPU2 = new String[]{"1", "2", "3", "4", "5","6","7","8","9","10","11","12"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>( getContext(), R.layout.dropdownitem, TPU2);
        AT3.setAdapter(adapter3);
        AT3S.setAdapter(adapter3);

        Ttamaño=root.findViewById(R.id.sliT8);
        String[] TPU4 = new String[]{"100R","10R22.5","11R22.5","235-285R","8.25R","900R","9R22.5","255/75R","1000R","11R24.5","12R22.5","295/80R22.5","305/70R/22.5","315/70R22.5","365R","12R24.5","1200R20","1200R24","13R22.5","R15/80R","385R","315/80R22.5","19.5L-24","20.5R24","23.5R25","29.5R25","275/70R22.5","275/80R22.5","1400R24","1400-24","825R16","365/60R22.5","385/60R22.5","425/60R22.5"};
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>( getContext(), R.layout.dropdownitem, TPU4);
        Ttamaño.setAdapter(adapter4);
        //BLOQUEO DE FECHA
        AT1.setInputType(InputType.TYPE_NULL);
        AT2.setInputType(InputType.TYPE_NULL);
        AT3.setInputType(InputType.TYPE_NULL);
        AT1S.setInputType(InputType.TYPE_NULL);
        AT2S.setInputType(InputType.TYPE_NULL);
        AT3S.setInputType(InputType.TYPE_NULL);
        //DATOS GENERALES
        Tsolicitante=root.findViewById(R.id.sliT1);
        Tidentificacion=root.findViewById(R.id.sliT2);
        Tidentificacion.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    llanta.Identificacion=Tidentificacion.getText().toString();
                    Caso=2;
                    llanta.Datos_Cliente();
                    new Dback().execute("");
                    return true;
                }
                return false;
            }
        });
        Tdireccion=root.findViewById(R.id.sliT3);
        Tbarrio=root.findViewById(R.id.sliT4);
        Tciudad=root.findViewById(R.id.sliT5);
        Ttelefono=root.findViewById(R.id.sliT6);
        Tmarca=root.findViewById(R.id.sliT7);
        Tserie=root.findViewById(R.id.sliT9);
        Totro=root.findViewById(R.id.sliT10);
        Torden=root.findViewById(R.id.sliT14);
        Tvalor=root.findViewById(R.id.sliT15);
        Tabono=root.findViewById(R.id.sliT16);
        //BOTON
        ENVIAR=root.findViewById(R.id.SEND);
        ENVIAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DATOSB();
            }
        });
        H1=root.findViewById(R.id.HE1);
        H1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Costado.equals("NO"))
                {
                    Costado="SI";
                }
                else {
                    H1.setChecked(false);
                    Costado="NO";
                }
            }
        });
        H2=root.findViewById(R.id.HE2);
        H2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Banda.equals("NO"))
                {
                    Banda="SI";
                }
                else {
                    H2.setChecked(false);
                    Banda="NO";
                }
            }
        });
        H3=root.findViewById(R.id.R1OP);
        H3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Hombro.equals("NO")==true)
                {
                    Hombro="SI";
                }
                else {
                    H3.setChecked(false);
                    Hombro="NO";
                }
            }
        });


        return root;
    }
    public void DATOSB()
    {
        //OBTENER DATOS GENERALES
        //SOLICITANTE
        Solicitante=Tsolicitante.getText().toString();
        //# IDENTIFICACION
        Identificacion=Tidentificacion.getText().toString();
        //DIRECCION
        Direccion=Tdireccion.getText().toString();
        //BARRIO
        Barrio=Tbarrio.getText().toString();
        //CIUDAD
        Ciudad=Tciudad.getText().toString();
        //TELEFONO
        Telefono=Ttelefono.getText().toString();
        //MARCA llanta
        Marca=Tmarca.getText().toString();
        //TAMAÑO
        Tamaño=Ttamaño.getText().toString();
        //SERIE
        Serie=Tserie.getText().toString();
        //OTRO
        Otro=Totro.getText().toString();
        //OBTENER FECHA
        A11=AT1.getText().toString();
        A13=AT2.getText().toString();
        A12=AT3.getText().toString();
        A11S=AT1S.getText().toString();
        A13S=AT2S.getText().toString();
        A12S=AT3S.getText().toString();
        //FECHA FINAL
        Fecha=A12+"/"+A11+"/"+A13;
        //FECHA SALIDA
        FechaS=A12S+"/"+A11S+"/"+A13S;
        //ODEN DE SERVICIO
        Orden=Torden.getText().toString();
        //VALOR
        Valor=Integer.parseInt(Tvalor.getText().toString());
        //ABONO
        Abono=Integer.parseInt(Tabono.getText().toString());
        if(Solicitante.equals("") || Identificacion.equals("") || Direccion.equals("") || Barrio.equals("")|| Ciudad.equals("") || Telefono.equals("") || Marca.equals("") || Tamaño.equals("") || Serie.equals("") || Fecha.equals("//") || FechaS.equals("//") || Orden.equals(""))
        {
            ToastGenerator("LLENE TODOS LOS CAMPOS NECESARIOS");
        }
        else {
            if(Abono>Valor)
            {
                ToastGenerator("ABONO SUPERA AL VALOR TOTAL, POR FAVOR CORREGIR");
            }
            else {
                Boolean N3;
                llanta=new llanta(Solicitante,Identificacion,Direccion,Barrio,Ciudad,Telefono,Marca,Tamaño,Serie,Costado,Banda,Hombro,Otro,Fecha,Orden,Valor,Abono,"INGRESO",Ubicacion,Posicion,FechaS,"","","");
                Caso=1;
                new Dback().execute("");
            }
        }
    }

    public void ToastGenerator(String string){
        Toast toast = Toast.makeText(getContext(), string, Toast.LENGTH_LONG);
        toast.show();
    }
    public void vaciar()
    {
        ToastGenerator("llanta REGISTRADA CORRECTAMENTE");
        Tsolicitante.setText("");
        Tidentificacion.setText("");
        Tdireccion.setText("");
        Tbarrio.setText("");
        Tciudad.setText("");
        Ttelefono.setText("");
        Tmarca.setText("");
        Ttamaño.setText("");
        Tserie.setText("");
        Totro.setText("");
        Torden.setText("");
        Tvalor.setText("");
        Tabono.setText("");
        AT1.setText("");
        AT2.setText("");
        AT3.setText("");
        AT1S.setText("");
        AT2S.setText("");
        AT3S.setText("");
        //REINICIAR BOTONES
        H1.setChecked(false);
        Costado="NO";
        H2.setChecked(false);
        Banda="NO";
        H3.setChecked(false);
        Hombro="NO";
    }
    private class Dback extends AsyncTask<String, Void, String> {
        ProgressDialog N3 = null;
        Boolean Check=false;
        @Override
        protected void onPreExecute() {
            N3=new ProgressDialog(getContext());
            N3.show();
            N3.setContentView(R.layout.progress_dialog);
            N3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                if(Caso==1)
                {
                    Check=llanta.REGISTRAR(E_C);
                }
                else if(Caso==2){
                    Check=llanta.Datos_Cliente();
                    E_C=Check;
                }

            } catch (Exception e) {

                ToastGenerator(e.getMessage());
            }
            return strings[0];
        }

        @Override
        protected void onPostExecute(String s) {
            if(Caso==1){
                if(Check) {
                    vaciar();
                }
                else {
                    ToastGenerator(UC.Comentario_Consulta);
                }
            }
            else if(Caso==2){
                if(Check){
                    Tsolicitante.setText(llanta.Solicitante);
                    Tdireccion.setText(llanta.Direccion);
                    Tbarrio.setText(llanta.Barrio);
                    Tciudad.setText(llanta.Ciudad);
                    Ttelefono.setText(llanta.Telefono);
                }
                else {
                    ToastGenerator("El cliente aun no ha sido registrado");
                }
            }
            N3.dismiss();
        }
    }
}