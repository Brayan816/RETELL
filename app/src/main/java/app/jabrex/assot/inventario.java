package app.jabrex.assot;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assot.R;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import app.jabrex.assot.ui.home.HomeFragment;
import app.jabrex.assot.ui.home.HomeViewModel;


public class inventario extends Fragment {
    private HomeViewModel homeViewModel;
    public TextView M1;
    private Spinner spinner;
    public String A1,A2,A3,WX1;
    public ListView LV;
    public inventario.MyADAPTER adapter4;
    AutoCompleteTextView FILTRO;
    public ProgressBar P1;

    public ArrayList<String> DFA1=new ArrayList<>(),DFA2=new ArrayList<>(),DFA3=new ArrayList<>(),DFA4=new ArrayList<>(),DFA5=new ArrayList<>(),DFA6=new ArrayList<>(),DFA7=new ArrayList<>();
    public ArrayList<String> DXA1=new ArrayList<>(),DXA2=new ArrayList<>(),DXA3=new ArrayList<>(),DXA4=new ArrayList<>(),DXA5=new ArrayList<>(),DXA6=new ArrayList<>(),DXA7=new ArrayList<>();

    public ArrayAdapter<String> adapter;
    String[] TPU3 = new String[]{"CC", "#O","TEL","SER"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =inflater.inflate(R.layout.fragment_inventario, container, false);
        String[] TPU4 = new String[]{"100R","10R22.5","11R22.5","235-285R","8.25R","900R","9R22.5","255/75R","1000R","11R24.5","12R22.5","295/80R22.5","305/70R/22.5","315/70R22.5","365R","12R24.5","1200R20","1200R24","13R22.5","R15/80R","385R"};
        ArrayAdapter<String> adapter5 = new ArrayAdapter<>( getContext(), R.layout.dropdownitem, TPU4);
        FILTRO=root.findViewById(R.id.Search);
        FILTRO.setAdapter(adapter5);
        adapter4=new inventario.MyADAPTER(getContext(),DFA1,DFA2,DFA3,DFA4,DFA6,DFA7);
        P1=root.findViewById(R.id.PRAD4);
        P1.setVisibility(View.GONE);
        //listview
        LV=root.findViewById(R.id.LW1);
        adapter4=new inventario.MyADAPTER(getContext(),DFA1,DFA2,DFA3,DFA4,DFA6,DFA7);
        LV.setAdapter(adapter4);
        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                A3=String.valueOf(adapterView.getItemAtPosition(i));
                VG.MODO="Z";
                VG.ORDENS=DXA5.get(i);
                AbrirQr();
                ToastGenerator(A3+"--"+DXA5.get(i));
            }
        });
        cargarData();
        FILTRO.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                WX1 =charSequence.toString();
                filtroX RTF=new filtroX();
                RTF.execute("");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return root;
    }
    private void cargarData(){
        ConexionMysql7 RTD=new ConexionMysql7();
        RTD.execute("");
    }
    private void AbrirQr() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

    private class filtroX extends  AsyncTask<String,String,String>
    {   int F2=0;
        ArrayList<String> gh1;
        String msg="inicial";
        @Override
        protected void onPreExecute() {
            DXA1.clear();
            DXA2.clear();
            DXA3.clear();
            DXA4.clear();
            DXA5.clear();
            DXA6.clear();
            DXA7.clear();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                int f=0;
                for (String SOPA : DFA1) {
                    if (SOPA.contains(WX1)) {
                        DXA1.add(SOPA);
                        DXA2.add(DFA2.get(f));
                        DXA3.add(DFA3.get(f));
                        DXA4.add(DFA4.get(f));
                        DXA5.add(DFA5.get(f));
                        DXA6.add(DFA6.get(f));
                        DXA7.add(DFA7.get(f));
                        msg="entra";
                    }
                    else{

                        msg="borro";
                    }
                    f=f+1;
                }
                return msg;
            }
            catch (Exception e)
            {
                msg=e.getMessage();
                return msg;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            //ToastGenerator(msg +" - "+DXA1+" -  "+WX1);
            LV.setAdapter(new MyADAPTER(getActivity(),DXA1,DXA2,DXA3,DXA4,DXA6,DXA7));

        }
    }
    public class ConexionMysql7 extends AsyncTask<String,String,String>
    {
        ProgressDialog proceso;
        String msg="";
        int i=0;
        ArrayList<String> Hak ;
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
        @Override
        protected String doInBackground(String... strings) {
            Connection connection=null;
            Session session=null;
            try {
                String DBUSER,DBPASS,IPDB;
                if(VG.TC==0)
                {
                    DBUSER="ROOT2";DBPASS="RETELL";IPDB="localhost";
                    final JSch jsch = new JSch();
                    session = jsch.getSession("pi", "192.168.1.200", 22);
                    session.setPassword("RETELL");
                    final Properties config = new Properties();
                    config.put("StrictHostKeyChecking", "no");
                    session.setConfig(config);
                    session.connect();
                    session.setPortForwardingL(3306, "localhost", 3306);

                }
                else
                {
                    DBUSER=VG.DBUSER;DBPASS=VG.DBPASS;IPDB=VG.DBIP;
                }
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                connection= DriverManager.getConnection("jdbc:mysql://"+IPDB+"/llantas",DBUSER,DBPASS);
                Statement statement=connection.createStatement();
                ResultSet FA=statement.executeQuery("SELECT *FROM retell");
                ArrayList<String> WF1=new ArrayList<>();
                ArrayList<String> WF2=new ArrayList<>();
                ArrayList<String> WF3=new ArrayList<>();
                ArrayList<String> WF4=new ArrayList<>();
                ArrayList<String> WF5=new ArrayList<>();
                ArrayList<String> WF6=new ArrayList<>();
                ArrayList<String> WF7=new ArrayList<>();
                while (FA.next())
                {
                    WF1.add(FA.getString(2));
                    WF2.add(FA.getString(1));
                    WF3.add(FA.getString(3));
                    WF4.add(FA.getString(4));
                    WF5.add(FA.getString(13));
                    WF6.add(FA.getString(6));
                    WF7.add(FA.getString(7)+" - "+FA.getString(8));
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
                    DFA1.clear();
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
                        DFA1.add(Hak.get(i));
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

    class MyADAPTER extends ArrayAdapter<String>  {

        Context context;
        ArrayList<String> eMarca;
        ArrayList<String> eSolicitante;
        ArrayList<String> eTamao;
        ArrayList<String> eSerie;
        ArrayList<String> eEstado;
        ArrayList<String> ePosicion;

        MyADAPTER(Context c,ArrayList<String> Marca,ArrayList<String> Solicitante,ArrayList<String> Tamao,ArrayList<String> Serie,ArrayList<String> estadoaA,ArrayList<String> posicionA)
        {
            super(c,R.layout.columna,R.id.LV_MARCA,Marca);
            this.context=c;
            this.eMarca=Marca;
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
            View row=layoutInflater.inflate(R.layout.columna,parent,false);
            TextView bMARCA=row.findViewById(R.id.LV_MARCA);
            TextView bSOLICITANTE=row.findViewById(R.id.LV_SOLICITANTE);
            TextView bTAMAÑO=row.findViewById(R.id.LV_TAMAÑO);
            TextView bSERIE=row.findViewById(R.id.LV_SERIE);
            TextView bESTADO=row.findViewById(R.id.LV_ESTADO);
            TextView bPOSICON=row.findViewById(R.id.LV_POSICION);
            bMARCA.setText(eMarca.get(position));
            bSOLICITANTE.setText(eSolicitante.get(position));
            bTAMAÑO.setText(eTamao.get(position));
            bSERIE.setText(eSerie.get(position));
            bESTADO.setText(eEstado.get(position));
            bPOSICON.setText(ePosicion.get(position));
            return row;
        }

    }

}