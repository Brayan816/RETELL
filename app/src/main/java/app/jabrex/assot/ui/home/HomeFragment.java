package app.jabrex.assot.ui.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.assot.R;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import app.jabrex.assot.INICIO;
import app.jabrex.assot.MainActivity;
import app.jabrex.assot.VG;


public class    HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public String A1,A2,A3;
    public ListView LV;
    public MyADAPTER adapter4;
    AutoCompleteTextView FILTRO;
    public ProgressBar P1;

    public ArrayList<String> DFA1=new ArrayList<>(),DFA2=new ArrayList<>(),DFA3=new ArrayList<>(),DFA4=new ArrayList<>(),DFA5=new ArrayList<>(),DFA6=new ArrayList<>(),DFA7=new ArrayList<>();
    public ArrayAdapter<String> adapter;
    String[] TPU3 = new String[]{"CC", "#O","TEL","SER"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        FILTRO=root.findViewById(R.id.FILTRO);
        FILTRO.setInputType(InputType.TYPE_NULL);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>( getContext(), R.layout.dropdownitem, TPU3);
        FILTRO.setAdapter(adapter2);
        FILTRO.setText("CC",false);
        P1=root.findViewById(R.id.PRAD4);
        P1.setVisibility(View.GONE);
        //listview
        LV=root.findViewById(R.id.LW1);
        adapter4=new MyADAPTER(getContext(),DFA1,DFA2,DFA3,DFA4,DFA6,DFA7);
        LV.setAdapter(adapter4);
        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                A3=String.valueOf(adapterView.getItemAtPosition(i));
                VG.ORDENS=DFA5.get(i);
                VG.MODO="A";
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
                        A2= "SELECT L.MARCA AS MARCA,C.SOLICITANTE AS SOLICITANTE, L.SERIE AS SERIE,L.TAMA AS TAMA,L.ORDEN_S AS ORDEN_S,L.E_A AS E_A, L.UBICACION AS UBICACION, L.POSICION AS POSICION " +
                                "FROM llantas L " +
                                "JOIN clientes C ON L.N_IDE=C.N_IDE " +
                                "WHERE C.N_IDE="+A1;
                    }
                    else if(A3.equals("#O"))
                    {
                        A2="SELECT L.MARCA AS MARCA,C.SOLICITANTE AS SOLICITANTE, L.SERIE AS SERIE,L.TAMA AS TAMA,L.ORDEN_S AS ORDEN_S,L.E_A AS E_A, L.UBICACION AS UBICACION, L.POSICION AS POSICION " +
                                "FROM llantas L " +
                                "JOIN clientes C ON L.N_IDE=C.N_IDE " +
                                "WHERE L.ORDEN_S="+A1;
                    }
                    else if(A3.equals("SER"))
                    {
                        A2="SELECT L.MARCA AS MARCA,C.SOLICITANTE AS SOLICITANTE, L.SERIE AS SERIE,L.TAMA AS TAMA,L.ORDEN_S AS ORDEN_S,L.E_A AS E_A, L.UBICACION AS UBICACION, L.POSICION AS POSICION " +
                                "FROM llantas L " +
                                "JOIN clientes C ON L.N_IDE=C.N_IDE " +
                                "WHERE L.SERIE="+A1;;
                    }
                    else {
                        A2="SELECT L.MARCA AS MARCA,C.SOLICITANTE AS SOLICITANTE, L.SERIE AS SERIE,L.TAMA AS TAMA,L.ORDEN_S AS ORDEN_S,L.E_A AS E_A, L.UBICACION AS UBICACION, L.POSICION AS POSICION " +
                                "FROM llantas L " +
                                "JOIN clientes C ON L.N_IDE=C.N_IDE " +
                                "WHERE C.TELEFONO="+A1;
                    }
                    DFA1.clear();
                    DFA2.clear();
                    DFA3.clear();
                    DFA4.clear();
                    DFA5.clear();
                    DFA6.clear();
                    DFA7.clear();
                    ConexionMysql7 RTD=new ConexionMysql7();
                    RTD.execute("");
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

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
        ProgressDialog N3=null;
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
            N3=new ProgressDialog(getContext());
            N3.show();
            N3.setContentView(R.layout.progress_dialog);
            N3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
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
                connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/RETELL","ROOT2","RETELL");
                Statement statement=connection.createStatement();
                ResultSet FA=statement.executeQuery(A2);
                ArrayList<String> WF1=new ArrayList<>();
                ArrayList<String> WF2=new ArrayList<>();
                ArrayList<String> WF3=new ArrayList<>();
                ArrayList<String> WF4=new ArrayList<>();
                ArrayList<String> WF5=new ArrayList<>();
                ArrayList<String> WF6=new ArrayList<>();
                ArrayList<String> WF7=new ArrayList<>();
                while (FA.next())
                {
                    WF1.add(FA.getString("MARCA"));
                    WF2.add(FA.getString("SOLICITANTE"));
                    WF3.add(FA.getString("SERIE"));
                    WF4.add(FA.getString("TAMA"));
                    WF5.add(FA.getString("ORDEN_S"));
                    WF6.add(FA.getString("E_A"));
                    WF7.add(FA.getString("UBICACION")+" - "+FA.getString("POSICION"));
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
                session.disconnect();
                connection.close();

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
            N3.dismiss();
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
                ToastGenerator(msg);
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

    class MyADAPTER extends ArrayAdapter<String> {
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