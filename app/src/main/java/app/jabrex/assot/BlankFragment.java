package app.jabrex.assot;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assot.R;
import com.google.android.material.textfield.TextInputEditText;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import app.jabrex.assot.ui.home.HomeFragment;
import app.jabrex.assot.ui.home.HomeViewModel;

public class BlankFragment extends Fragment {
    private HomeViewModel homeViewModel;
    public TextView M1;
    private Spinner spinner;
    public String A1,A2,A3;
    public ListView LV;
    public MyADAPTER2 adapter4;
    AutoCompleteTextView FILTRO;
    public ProgressBar P1;
    public ArrayList<Integer> W1=new ArrayList<>();
    public ArrayList<String> DFA1=new ArrayList<>(),DFA2=new ArrayList<>(),DFA3=new ArrayList<>(),DFA4=new ArrayList<>(),DFA5=new ArrayList<>(),DFA6=new ArrayList<>(),DFA7=new ArrayList<>();
    public ArrayAdapter<String> adapter;
    String[] TPU3 = new String[]{"CC", "#O","TEL","SER"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_blank, container, false);
        FILTRO=root.findViewById(R.id.FILTRO);
        FILTRO.setInputType(InputType.TYPE_NULL);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>( getContext(), R.layout.dropdownitem, TPU3);
        FILTRO.setAdapter(adapter2);


        P1=root.findViewById(R.id.PRAD4);
        P1.setVisibility(View.GONE);
        //listview

        LV=root.findViewById(R.id.LW1);
        adapter4=new MyADAPTER2(getContext(),W1,DFA2,DFA3,DFA4,DFA6,DFA7);
        //adapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,DFA1);
        LV.setAdapter(adapter4);


        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                A3=String.valueOf(adapterView.getItemAtPosition(i));
                VG.ORDENS=DFA5.get(i);
                AbrirQr();
            }
        });
        SearchView searchView=root.findViewById(R.id.SW);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(FILTRO.getText().toString().equals(""))
                {
                    ToastGenerator("Seleccione el tipo de busqueda");
                }
                else
                {
                    A1=searchView.getQuery().toString();
                    A3=FILTRO.getText().toString();
                    if(A3.equals("CC"))
                    {
                        A2="N_IDE";
                    }
                    else if(A3.equals("#O"))
                    {
                        A2="ORDEN_S";
                    }
                    else if(A3.equals("SER"))
                    {
                        A2="SERIE";
                    }
                    else {
                        A2="TELEFONO";
                    }
                    W1.clear();
                    DFA2.clear();
                    DFA3.clear();
                    DFA4.clear();
                    DFA5.clear();
                    DFA6.clear();
                    DFA7.clear();
                    BlankFragment.ConexionMysql7 RTD=new BlankFragment.ConexionMysql7();
                    RTD.execute("");
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return root;
    }

    public void AbrirQr() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }


    public class ConexionMysql7 extends AsyncTask<String,String,String>
    {
        ProgressDialog proceso;
        String msg="";
        int i=0;
        List<Integer> Hak ;
        ArrayList<String> Hak2 ;
        ArrayList<String> Hak3 ;
        ArrayList<String> Hak4 ;
        ArrayList<String> Hak5 ;
        ArrayList<String> Hak6 ;
        ArrayList<String> Hak7 ;
        @Override
        protected void onPreExecute()
        {
            P1.setVisibility(View.VISIBLE);
        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(String... strings) {
            Connection connection=null;
            Session session=null;
            try {
                final JSch jsch = new JSch();
                session = jsch.getSession("pi", VG.DBIP, 22);
                session.setPassword("RETELL");
                final Properties config = new Properties();
                config.put("StrictHostKeyChecking", "no");
                session.setConfig(config);
                session.connect();
                session.setPortForwardingL(3306, "localhost", 3306);
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                connection= DriverManager.getConnection("jdbc:mysql://localhost/llantas",VG.DBUSER,VG.DBPASS);
                Statement statement=connection.createStatement();
                msg="SELECT *FROM datos WHERE "+A2+"='"+A1+"'";
                ResultSet FA=statement.executeQuery("SELECT *FROM datos WHERE "+A2+"='"+A1+"'");
                ArrayList<Integer> WF1=new ArrayList<Integer>();
                ArrayList<String> WF2=new ArrayList<>();
                ArrayList<String> WF3=new ArrayList<>();
                ArrayList<String> WF4=new ArrayList<>();
                ArrayList<String> WF5=new ArrayList<>();
                ArrayList<String> WF6=new ArrayList<>();
                ArrayList<String> WF7=new ArrayList<>();
                String[] parts={"","",""},ACTUAL={"","",""};
                String F1="",F2="";
                LocalDate localDate=LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formattedString = localDate.format(formatter);
                int Z1,Z2,Z3,XX2=R.drawable.ic_baseline_warning_27;
                String FAL;
                ACTUAL=formattedString.split("/");
                Z1=Integer.parseInt(ACTUAL[0]);
                Z2=Integer.parseInt(ACTUAL[1]);
                Z3=Integer.parseInt(ACTUAL[2]);
                while (FA.next())
                {
                    F2=FA.getString(19);
                    if(F2.equals("ENTREGADO"))
                    {
                        F1=FA.getString(25);
                        parts=F1.split("/");
                        int X1,X2,X3,AF1;
                        X1=Integer.parseInt(parts[0]);
                        X2=Integer.parseInt(parts[1]);
                        X3=Integer.parseInt(parts[2]);
                        if(X3>=Z3)
                        {
                            if(X2>=Z2)
                            {
                                if (X1<=Z1 && Z2==X2)
                                {
                                    FAL="EN GARANTIA";
                                    XX2=R.drawable.ic_baseline_warning_25;
                                }
                                else if(Z2<X2){
                                    FAL="EN GARANTIA";
                                    XX2=R.drawable.ic_baseline_warning_25;
                                }
                                else { FAL="FUERA DE GARANTIA";
                                    XX2=R.drawable.ic_baseline_warning_24;}
                            }
                            else { FAL="FUERA DE GARANTIA";
                                XX2=R.drawable.ic_baseline_warning_24; }
                        }
                        else { FAL="FUERZA DE GARANTIA";
                            XX2=R.drawable.ic_baseline_warning_24;}
                    }
                    else {
                        FAL="EN PROCESO";
                        XX2=R.drawable.ic_c;
                    }
                    WF1.add(XX2);
                    WF2.add(FA.getString(2));
                    WF3.add(FA.getString(8));
                    WF4.add(FA.getString(9));
                    WF5.add(FA.getString(16));
                    WF6.add(FA.getString(10));
                    WF7.add(FAL+"-"+FA.getString(22));
                    msg="WWW";
                    i=i +1;
                }
                Hak=WF1;
                Hak2=WF2;
                Hak3=WF3;
                Hak4=WF4;
                Hak5=WF5;
                Hak6=WF6;
                Hak7=WF7;
                connection.close();
                if(VG.TC==0){
                    session.disconnect();
                }

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
            if(i>0)
            {
                try {
                    adapter4.clear();
                    W1.clear();
                    DFA2.clear();
                    DFA3.clear();
                    DFA4.clear();
                    DFA5.clear();
                    DFA6.clear();
                    DFA7.clear();
                    String ml=Integer.toString(i);
                    String M2=msg+"--"+ml+"--"+ Hak.size();
                    //ToastGenerator(msg);
                    for(int i=0;i<Hak.size();i++)
                    {
                        W1.add(Hak.get(i));
                        DFA2.add(Hak2.get(i));
                        DFA3.add(Hak3.get(i));
                        DFA4.add(Hak4.get(i));
                        DFA5.add(Hak5.get(i));
                        DFA6.add(Hak6.get(i));
                        DFA7.add(Hak7.get(i));
                    }
                    adapter4.notifyDataSetChanged();
                }
                catch (Exception e)
                {
                    ToastGenerator(e.getMessage());
                }
            }
            else if(i==0)
            {
                ToastGenerator("No se encontro ninguna llanta registrada");
            }
            else {
                ToastGenerator(msg);
            }

        }
    }

    public void ToastGenerator(String string){
        Toast toast = Toast.makeText(getContext(), string, Toast.LENGTH_LONG);
        toast.show();
    }

    private class MyADAPTER2 extends ArrayAdapter<String> {

        Context context;
        ArrayList<Integer> eIMAGEN;
        ArrayList<String> eSolicitante;
        ArrayList<String> eTamao;
        ArrayList<String> eSerie;
        ArrayList<String> eEstado;
        ArrayList<String> ePosicion;

        MyADAPTER2(Context c,ArrayList<Integer> Imagen ,ArrayList<String> Solicitante,ArrayList<String> Tamao,ArrayList<String> Serie,ArrayList<String> estadoaA,ArrayList<String> posicionA)
        {
            super(c,R.layout.warranty,R.id.LV_SERIE,Serie);
            this.context=c;
            this.eIMAGEN=Imagen;
            this.eSolicitante=Solicitante;
            this.eTamao=Tamao;
            this.eSerie=Serie;
            this.eEstado=estadoaA;
            this.ePosicion=posicionA;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


            LayoutInflater layoutInflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=layoutInflater.inflate(R.layout.warranty,parent,false);
            ImageView bIMAGEN =row.findViewById(R.id.LV_IMAGE);
            TextView bSOLICITANTE=row.findViewById(R.id.LV_SOLICITANTE);
            TextView bTAMAÑO=row.findViewById(R.id.LV_TAMAÑO);
            TextView bSERIE=row.findViewById(R.id.LV_SERIE);
            TextView bESTADO=row.findViewById(R.id.LV_ESTADO);
            TextView bPOSICON=row.findViewById(R.id.LV_POSICION);

            bIMAGEN.setImageResource(eIMAGEN.get(position));
            bSOLICITANTE.setText(eSolicitante.get(position));
            bTAMAÑO.setText(eTamao.get(position));
            bSERIE.setText(eSerie.get(position));
            bESTADO.setText(eEstado.get(position));
            bPOSICON.setText(ePosicion.get(position));
            return row;
        }

    }
}