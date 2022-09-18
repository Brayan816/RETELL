package app.jabrex.retell.ui.retellLlantas;

import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.database.SQLException;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;
import com.example.retell.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import app.jabrex.retell.UC;
import app.jabrex.retell.ui.retellLlantas.RetellLlantasViewModel;

public class RetellLantasFragment extends Fragment {

    private RetellLlantasViewModel retellLlantasViewModel;
    AutoCompleteTextView TD2;
    Button ENVIAR;
    private TextInputEditText TD1,TD3,TD4,TD5;
    public String A1,A2,A3,A4,A5,A6,HER1="NO",HER2="NO",HER3="NO";
    public ProgressBar P1;
    public TextInputLayout TF1,TF2,TF3,TF4,TF5;
    public RadioButton H1,H2,H3;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        retellLlantasViewModel =
                new ViewModelProvider(this).get(RetellLlantasViewModel.class);

        View root = inflater.inflate(R.layout.fragment_retell_llantas, container, false);

        String[] TPU4 = new String[]{"100R","10R22.5","11R22.5","235-285R","8.25R","900R","9R22.5","255/75R","1000R","11R24.5","12R22.5","295/80R22.5","305/70R/22.5","315/70R22.5","365R","12R24.5","1200R20","1200R24","13R22.5","R15/80R","385R"};
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>( getContext(), R.layout.dropdownitem, TPU4);
        P1=root.findViewById(R.id.PRAD);
        //DATOS
        //MARCA
        TD1=root.findViewById(R.id.sliT1);
        TF1=root.findViewById(R.id.sliTD1);
        //TAMAÑO
        TD2=root.findViewById(R.id.sliT8);
        TD2.setAdapter(adapter4);
        TF2=root.findViewById(R.id.sliTD8);
        //SERIE
        TD3=root.findViewById(R.id.sliT9);
        TF3=root.findViewById(R.id.sliTD9);
        //OTRO
        TD4=root.findViewById(R.id.sliT10);
        TF4=root.findViewById(R.id.sliTD10);
        //PRECIO
        TD5=root.findViewById(R.id.sliT15);
        TF5=root.findViewById(R.id.sliTD15);

        ENVIAR=root.findViewById(R.id.SEND);
        ENVIAR.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                DATOSB();
            }
        });
        H1=root.findViewById(R.id.HE1);
        H1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(HER1.equals("NO"))
                {
                    HER1="SI";
                }
                else {
                    H1.setChecked(false);
                    HER1="NO";
                }
            }
        });
        H2=root.findViewById(R.id.HE2);
        H2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(HER2.equals("NO"))
                {
                    HER2="SI";
                }
                else {
                    H2.setChecked(false);
                    HER2="NO";
                }
            }
        });
        H3=root.findViewById(R.id.R1OP);
        H3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(HER3.equals("NO"))
                {
                    HER3="SI";
                }
                else {
                    H3.setChecked(false);
                    HER3="NO";
                }
            }
        });

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void DATOSB()
    {
        //OBTENER DATOS GENERALES
        //MARCA
        A1=TD1.getText().toString();
        //TAMAÑO
        A2=TD2.getText().toString();
        //SERIE
        A3=TD3.getText().toString();
        //OTRO
        A4=TD4.getText().toString();
        //PRECIO
        A5=TD5.getText().toString();
        //FECHA
        LocalDate localDate=LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        A6 = localDate.format(formatter);


        if(A1.equals("") || A2.equals("") || A3.equals("") || A5.equals("") )
        {
            if(A1.equals(("")))
            {
                TF1.setHelperText("Requerido*");
                TF1.setHelperTextColor(ColorStateList.valueOf(Color.rgb(204,51,84)));
                TF1.setHelperTextEnabled(true);
            }
            if(A2.equals(("")))
            {
                TF2.setHelperText("Requerido*");
                TF2.setHelperTextColor(ColorStateList.valueOf(Color.rgb(204,51,84)));
                TF2.setHelperTextEnabled(true);
            }
            if(A3.equals(("")))
            {
                TF3.setHelperText("Requerido*");
                TF3.setHelperTextColor(ColorStateList.valueOf(Color.rgb(204,51,84)));
                TF3.setHelperTextEnabled(true);
            }

            if(A5.equals(("")))
            {
                TF5.setHelperText("Requerido*");
                TF5.setHelperTextColor(ColorStateList.valueOf(Color.rgb(204,51,84)));
                TF5.setHelperTextEnabled(true);
            }
            ToastGenerator("LLENE TODOS LOS CAMPOS NECESARIOS");
        }
        else {

                RetellLantasFragment.ConexionMysql4 RTD = new RetellLantasFragment.ConexionMysql4();
                RTD.execute("");
        }
    }

    public void ToastGenerator(String string){
        Toast toast = Toast.makeText(getContext(), string, Toast.LENGTH_LONG);
        toast.show();
    }
    public void vaciar()
    {
        ToastGenerator("llanta REGISTRADA CORRECTAMENTE");
        TD1.setText("");
        TD2.setText("");
        TD3.setText("");
        TD4.setText("");
        TD5.setText("");
        TF1.setHelperTextEnabled(false);
        TF2.setHelperTextEnabled(false);
        TF3.setHelperTextEnabled(false);
        TF4.setHelperTextEnabled(false);
        TF5.setHelperTextEnabled(false);
        //REINICIAR BOTONES
        H1.setChecked(false);
        HER1="NO";
        H2.setChecked(false);
        HER2="NO";
        H3.setChecked(false);
        HER3="NO";
    }

    private class ConexionMysql4 extends AsyncTask<String,String,String>
    {
        ProgressDialog proceso;
        String msg="";

        @Override
        protected void onPreExecute() {
            P1.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(String... strings) {
            Connection connection=null;
            Session session =null;
            try {
                final JSch jsch = new JSch();
                session = jsch.getSession("pi", UC.DBIP, 22);
                session.setPassword("RETELL");
                final Properties config = new Properties();
                config.put("StrictHostKeyChecking", "no");
                session.setConfig(config);
                session.connect();
                session.setPortForwardingL(3306, "localhost", 3306);
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                connection= DriverManager.getConnection("jdbc:mysql://localhost/llantas", UC.DBUSER, UC.DBPASS);
                Statement statement=connection.createStatement();
                statement.executeUpdate("insert into retell (MARCA_LLANTA,TAMAÑO,SERIE,COSTADO,BANDA,HOMBRO,OTRO,FECHAI,PRECIO,UBICACION,POSICION,E_A) values ('"+A1+"','"+A2+"','"+A3+"','"+HER1+"','"+HER2+"','"+HER3+"','"+A4+"','"+A6+"','"+A5+"','','','INGRESO')");
                msg="a1";
                connection.close();
                session.disconnect();

            } catch (SQLException |ClassNotFoundException e) {
                msg = e.getMessage();
            }
            catch (Exception e)
            {
                msg=e.getMessage();
            }
            return msg;
        }
        @Override
        protected void onPostExecute(String s) {
            P1.setVisibility(View.GONE);
            if(msg.equals("a1"))
            {
                vaciar();

            }
            else if (msg.equals("a12"))
            {
                ToastGenerator("LA llanta YA FUE REGISTRADA");
            }
            else
            {
                ToastGenerator(msg);
                ToastGenerator("OCURRIO UN ERROR AL REGISTRAR LA llanta");
            }
        }
    }

}