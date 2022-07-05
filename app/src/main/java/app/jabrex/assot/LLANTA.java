    package app.jabrex.assot;

import android.os.Build;
import android.renderscript.ScriptGroup;
import android.util.Pair;

import androidx.annotation.RequiresApi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class LLANTA extends ConexionMysql{
    public String Solicitante;
    public String Identificacion;
    public String Direccion;
    public String Barrio;
    public String Ciudad;
    public String Telefono;
    public String Marca;
    public String Tamaño;
    public String Serie;
    public String Costado;
    public String Banda;
    public String Hombro;
    public String Otro;
    public String Fecha;
    public String Orden;
    public int Valor;
    public int Abono;
    public String E_A;
    public String Ubicacion;
    public String Posicion;
    public String FechaS;
    public String FechaE;
    public String TipoS;
    public String FechaG;
    public String M1;
    public String M2;
    public String M3;
    public String M4;
    public String M5;
    public String M6;
    public String N1;
    public String N2;
    public String N3;
    public String N4;
    public String N5;
    public String N6;
    public String PM1;
    public String PM2;
    public String PM3;
    public String PM4;
    public String PM5;
    public String PM6;

    public LLANTA(String Solicitante,String Identficacion,String Direccion,String Barrio,String Ciudad,String Telefono,String Marca,String Tamaño,String Serie,String Costado,
                  String Banda, String Hombro,String Otro,String Fecha,String Orden,int Valor,int Abono,String E_A,String Ubicacion,String Posicion,String FechaS,String FechaE,String TipoS,String FechaG)
    {
        this.Solicitante=Solicitante;
        this.Identificacion=Identficacion;
        this.Direccion=Direccion;
        this.Barrio=Barrio;
        this.Ciudad=Ciudad;
        this.Telefono=Telefono;
        this.Marca=Marca;
        this.Tamaño=Tamaño;
        this.Serie=Serie;
        this.Costado=Costado;
        this.Banda=Banda;
        this.Hombro=Hombro;
        this.Otro=Otro;
        this.Fecha=Fecha;
        this.Orden=Orden;
        this.Valor=Valor;
        this.Abono=Abono;
        this.E_A=E_A;
        this.Ubicacion=Ubicacion;
        this.Posicion=Posicion;
        this.FechaS=FechaS;
        this.FechaE=FechaE;
        this.TipoS=TipoS;
        this.FechaG=FechaG;
    }
    public LLANTA(){

    }

    public boolean AgregarAbono(int Suma) {
        boolean Check=false;
        int Total=0;
        try {
            if ((Suma + this.Abono) <= Valor)
            {
                Total=this.Abono+Suma;
                if (OpenConnection()) {
                    String Query = "UPDATE llantas SET ABONO=? WHERE ORDEN_S=?";
                    PreparedStatement preparedStatement = con.prepareStatement(Query);
                    preparedStatement.setString(1, Integer.toString(Total));
                    preparedStatement.setString(2, this.Orden);
                    preparedStatement.executeUpdate();
                    this.Abono=this.Abono+Suma;
                    Check = true;
                }
            }
        }
        catch (Exception E){
            VG.Comentario_Consulta=E.getMessage();
        }
        CloseConnection();
        return Check;
    }
    public boolean A_FechaG(){
        boolean Check=false;
        try {
            if (OpenConnection()) {
                String Query = "UPDATE llantas SET FECHAG=? WHERE ORDEN_S=?";
                PreparedStatement preparedStatement = con.prepareStatement(Query);
                preparedStatement.setString(1,this.FechaG);
                preparedStatement.setString(2,this.Orden);
                preparedStatement.executeUpdate();
                Check=true;
            }
        }
        catch (Exception E){
            VG.Comentario_Consulta=E.getMessage();
        }
        CloseConnection();
        return Check;
    }
    public Boolean REGISTRAR()
    {
        boolean Check=false;
        try
        {
            if(OpenConnection()) {
                String QUERY = "insert into llantas (SOLICITANTE,N_IDE,DIRECCION,BARRIO,CIUDAD,TELEFONO,MARCA_LLANTA,TAMAÑO,SERIE,COSTADO,BANDA,HOMBRO,OTRO,FECHA,ORDEN_S,VALOR,ABONO,E_A,UBICACION,POSICION,FECHAS,FECHAE,TIPO_S,FECHAG,M1,M2,M3,M4,M5,M6,N1,N2,N3,N4,N5,N6,PM1,PM2,PM3,PM4,PM5,PM6) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement preparedStatement = con.prepareStatement(QUERY);
                preparedStatement.setString(1, this.Solicitante);
                preparedStatement.setString(2, this.Identificacion);
                preparedStatement.setString(3, this.Direccion);
                preparedStatement.setString(4, this.Barrio);
                preparedStatement.setString(5, this.Ciudad);
                preparedStatement.setString(6, this.Telefono);
                preparedStatement.setString(7, this.Marca);
                preparedStatement.setString(8, this.Tamaño);
                preparedStatement.setString(9, this.Serie);
                preparedStatement.setString(10, this.Costado);
                preparedStatement.setString(11, this.Banda);
                preparedStatement.setString(12, this.Hombro);
                preparedStatement.setString(13, this.Otro);
                preparedStatement.setString(14, this.Fecha);
                preparedStatement.setString(15, this.Orden);
                preparedStatement.setString(16, Integer.toString(this.Valor));
                preparedStatement.setString(17, Integer.toString(this.Abono));
                preparedStatement.setString(18, this.E_A);
                preparedStatement.setString(19, "");
                preparedStatement.setString(20, "");
                preparedStatement.setString(21, this.FechaS);
                preparedStatement.setString(22, "");
                preparedStatement.setString(23, "REPARACION");
                preparedStatement.setString(24, "");
                preparedStatement.setString(25, "");
                preparedStatement.setString(26, "");
                preparedStatement.setString(27, "");
                preparedStatement.setString(28, "");
                preparedStatement.setString(29, "");
                preparedStatement.setString(30, "");
                preparedStatement.setString(31, "");
                preparedStatement.setString(32, "");
                preparedStatement.setString(33, "");
                preparedStatement.setString(34, "");
                preparedStatement.setString(35, "");
                preparedStatement.setString(36, "");
                preparedStatement.setString(37, "");
                preparedStatement.setString(38, "");
                preparedStatement.setString(39, "");
                preparedStatement.setString(40, "");
                preparedStatement.setString(41, "");
                preparedStatement.setString(42, "");
                preparedStatement.executeUpdate();
                VG.Comentario_Consulta="REGISTRADO EXITOSAMENTE";
                Check=true;
            }
        }
        catch (Exception E){
            VG.Comentario_Consulta=E.getMessage();
        }
        CloseConnection();
        return Check;
    }

    public Boolean Buscar_Datos(){
        boolean Check=false;
        try {
            if (OpenConnection()) {
                String Query = "SELECT C.SOLICITANTE AS SOLICITANTE, C.N_IDE AS N_IDE,C.DIRECCION AS DIRECCION,C.BARRIO AS BARRIO,C.CIUDAD AS CIUDAD, C.TELEFONO AS TELEFONO,L.MARCA AS MARCA, L.TAMA AS TAMA,L.SERIE AS SERIE,L.COSTADO AS COSTADO,L.BANDA AS BANDA, L.HOMBRO AS HOMBRO,L.OTRO AS OTRO,L.FECHA AS FECHA,L.VALOR AS VALOR,L.ABONO AS ABONO,L.E_A AS E_A,L.UBICACION AS UBICACION,L.POSICION AS POSICION,L.FECHAS AS FECHAS,L.FECHAE AS FECHAE,L.TIPO_S AS TIPO_S,L.FECHAG AS FECHAG,L.M1 AS M1,L.M2 AS M2,L.M3 AS M3,L.M4 AS M4,L.M5 AS M5,L.M6 AS M6,L.N1 AS N1,L.N2 AS N2,L.N3 AS N3,L.N4 AS N4,L.N5 AS N5,L.N6 AS N6,L.PM1 AS PM1,L.PM2 AS PM2,L.PM3 AS PM3,L.PM4 AS PM4,L.PM5 AS PM5,L.PM6 AS PM6 "
                +"FROM llantas L "+
                "JOIN clientes C ON C.N_IDE=L.N_IDE "+
                "WHERE L.ORDEN_S=?";
                PreparedStatement preparedStatement = con.prepareStatement(Query);
                preparedStatement.setString(1, Orden);
                ResultSet fr = preparedStatement.executeQuery();
                if (fr.next()) {
                    Solicitante = (fr.getString("SOLICITANTE"));
                    Identificacion = (fr.getString("N_IDE"));
                    Direccion = (fr.getNString("DIRECCION"));
                    Barrio = (fr.getString("BARRIO"));
                    Ciudad = (fr.getString("CIUDAD"));
                    Telefono = (fr.getString("TELEFONO"));
                    Marca = (fr.getString("MARCA"));
                    Tamaño = (fr.getString("TAMA"));
                    Serie = (fr.getString("SERIE"));
                    Costado = (fr.getString("COSTADO"));
                    Banda = (fr.getString("BANDA"));
                    Hombro = (fr.getString("HOMBRO"));
                    Otro = (fr.getString("OTRO"));
                    Fecha = (fr.getString("FECHA"));
                    Valor = (Integer.parseInt(fr.getString("VALOR")));
                    Abono= (Integer.parseInt(fr.getString("ABONO")));
                    E_A = (fr.getString("E_A"));
                    Ubicacion = (fr.getString("UBICACION"));
                    Posicion = (fr.getString("POSICION"));
                    FechaS = (fr.getString("FECHAS"));
                    FechaE = (fr.getString("FECHAE"));
                    TipoS = (fr.getString("TIPO_S"));
                    FechaG = (fr.getString("FECHAG"));
                    M1 = (fr.getString("M1"));
                    M2 = (fr.getString("M2"));
                    M3 = (fr.getString("M3"));
                    M4 = (fr.getString("M4"));
                    M5 = (fr.getString("M5"));
                    M6 = (fr.getString("M6"));
                    N1 = (fr.getString("N1"));
                    N2 = (fr.getString("N2"));
                    N3 = (fr.getString("N3"));
                    N4 = (fr.getString("N4"));
                    N5 = (fr.getString("N5"));
                    N6 = (fr.getString("N6"));
                    PM1 = (fr.getString("PM1"));
                    PM2 = (fr.getString("PM2"));
                    PM3 = (fr.getString("PM3"));
                    PM4 = (fr.getString("PM4"));
                    PM5 = (fr.getString("PM5"));
                    PM6 = (fr.getString("PM6"));
                    VG.Comentario_Consulta="Datos cargados exitosamente";
                    Check=true;
                }
            }
        }
        catch (Exception E){
            VG.Comentario_Consulta=E.getMessage();
        }
        CloseConnection();
        return Check;
    }

    public Boolean ActualizarUbicacion(){

        boolean CHECK=false;
        try {
            if (OpenConnection())
            {
                String Query = "UPDATE llantas SET UBICACION=?,POSICION=? WHERE ORDEN_S=?";
                PreparedStatement preparedStatement = con.prepareStatement(Query);
                preparedStatement.setString(1,this.Ubicacion);
                preparedStatement.setString(2,this.Posicion);
                preparedStatement.setString(3,this.Orden);
                preparedStatement.executeUpdate();
                CHECK=true;
            }
        }
        catch (Exception E){
            VG.Comentario_Consulta=E.getMessage();
        }
        CloseConnection();
        return CHECK;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Boolean ActualizarEstado(){
        LocalDate localDate=LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedString = localDate.format(formatter);
        boolean Check =false;
        try {
            if(!E_A.equals("ENTREGADO")){
                FechaE="";
            }
            else {
                FechaE=formattedString;
            }
            if (OpenConnection()) {
                String Query = "UPDATE llantas SET E_A=?,FECHAE=? WHERE ORDEN_S=?";
                PreparedStatement preparedStatement = con.prepareStatement(Query);
                preparedStatement.setString(1,this.E_A);
                preparedStatement.setString(2,this.FechaE);
                preparedStatement.setString(3,this.Orden);
                preparedStatement.executeUpdate();
                Check=true;
            }
        }
        catch (Exception E){
            VG.Comentario_Consulta=E.getMessage();
        }
        CloseConnection();
        return Check;

    }
    public Boolean Datos_Cliente(){
        boolean Check=false;
        try {
            if (OpenConnection()) {
                String Query = "SELECT * FROM llantas where N_IDE = ? ";
                PreparedStatement preparedStatement = con.prepareStatement(Query);
                preparedStatement.setString(1, Identificacion);
                ResultSet fr = preparedStatement.executeQuery();
                if (fr.next()) {
                    Solicitante = (fr.getString("SOLICITANTE"));
                    Direccion = (fr.getNString("DIRECCION"));
                    Barrio = (fr.getString("BARRIO"));
                    Ciudad = (fr.getString("CIUDAD"));
                    Telefono = (fr.getString("TELEFONO"));
                    Check=true;
                }
            }
        }
        catch (Exception E){
            VG.Comentario_Consulta=E.getMessage();
        }
        CloseConnection();
        return Check;
    }
    public Boolean ActualizarMateriales(){
        Boolean Check=false;
        try {
            if(OpenConnection()){
                String Query = "UPDATE llantas SET M1=?,M2=?,M3=?,M4=?,M5=?,M6=?,N1=?,N2=?,N3=?,N4=?,N5=?,N6=?,PM1=?,PM2=?,PM3=?,PM4=?,PM5=?,PM6=? WHERE ORDEN_S=?";
                PreparedStatement preparedStatement = con.prepareStatement(Query);
                preparedStatement.setString(1,M1);
                preparedStatement.setString(2,M2);
                preparedStatement.setString(3,M3);
                preparedStatement.setString(4,M4);
                preparedStatement.setString(5,M5);
                preparedStatement.setString(6,M6);
                preparedStatement.setString(7,N1);
                preparedStatement.setString(8,N2);
                preparedStatement.setString(9,N3);
                preparedStatement.setString(10,N4);
                preparedStatement.setString(11,N5);
                preparedStatement.setString(12,N6);
                preparedStatement.setString(13,PM1);
                preparedStatement.setString(14,PM2);
                preparedStatement.setString(15,PM3);
                preparedStatement.setString(16,PM4);
                preparedStatement.setString(17,PM5);
                preparedStatement.setString(18,PM6);
                preparedStatement.setString(19,Orden);
                preparedStatement.executeUpdate();
                Check=true;
            }
            else {
                VG.Comentario_Consulta="Ocurrio Un error al Actualizar los materiales";
            }
        }
        catch (Exception E){
            VG.Comentario_Consulta=E.getMessage();
        }
        CloseConnection();
        return Check;
    }
    public Boolean Editar_Datos(String A1){
        Boolean Check=false;
        try {
            if(OpenConnection()){
                String Query1=
                        "UPDATE llantas " +
                        "SET MARCA='"+Marca+"',TAMA='"+Tamaño+"',SERIE='"+Serie+"',COSTADO='"+Costado+"',BANDA='"+Banda+"',HOMBRO='"+Hombro+"',OTRO='"+Otro+"',FECHAS='"+FechaS+"',FECHAE='"+FechaE+"',FECHA='"+Fecha+"',FECHAG='"+FechaG+"',VALOR='"+Valor+"',ABONO='"+Abono+"',N_IDE="+Identificacion+" " +
                        "WHERE ORDEN_S="+Orden;
                String Query2=  "UPDATE clientes " +
                                "SET SOLICITANTE='"+Solicitante+"',N_IDE="+Identificacion+",DIRECCION='"+Direccion+"',BARRIO='"+Barrio+"',CIUDAD='"+Ciudad+"',TELEFONO="+Telefono+" " +
                                "WHERE N_IDE="+A1;
                String Query3="UPDATE llantas " +
                        "SET MARCA='Goodyear2' " +
                        "WHERE ORDEN_S=10486";
                String Query4=  "UPDATE clientes " +
                                "SET SOLICITANTE='JAMES2' " +
                                "WHERE N_IDE=1098151723";
                String Query = "UPDATE llantas SET SOLICITANTE=?,N_IDE=?,DIRECCION=?,BARRIO=?,CIUDAD=?,TELEFONO=?,MARCA_LLANTA=?,TAMAÑO=?,SERIE=?,COSTADO=?,BANDA=?,HOMBRO=?,OTRO=?,FECHAS=?,FECHAE=?,VALOR=?,ABONO=? WHERE ORDEN_S=?";
                Statement S=con.createStatement();
                S.addBatch(Query1);
                S.addBatch(Query2);
                S.executeBatch();
                //
                //PreparedStatement preparedStatement = con.prepareStatement(Query3);
                /*preparedStatement.setString(1,Marca);
                preparedStatement.setString(2,Tamaño);
                preparedStatement.setString(3,Serie);
                preparedStatement.setString(4,Costado);
                preparedStatement.setString(5,Banda);
                preparedStatement.setString(6,Hombro);
                preparedStatement.setString(7,Otro);
                preparedStatement.setString(8,FechaS);
                preparedStatement.setString(9,FechaE);
                preparedStatement.setString(10,Fecha);
                preparedStatement.setString(11,FechaG);
                preparedStatement.setString(12, Integer.toString(Valor));
                preparedStatement.setString(13,Integer.toString(Abono));
                preparedStatement.setInt(14,Integer.parseInt(Identificacion));
                preparedStatement.setString(15,Orden);
                preparedStatement.setString(16,Solicitante);
                preparedStatement.setInt(17,Integer.parseInt(Identificacion));
                preparedStatement.setString(18,Direccion);
                preparedStatement.setString(19,Barrio);
                preparedStatement.setString(20,Ciudad);
                preparedStatement.setInt(21,Integer.parseInt(Telefono));
                preparedStatement.setInt(22,Integer.parseInt(Identificacion));
                */
                //preparedStatement.executeUpdate();

                Check=true;
            }
            else {
                VG.Comentario_Consulta="Ocurrio un problema al raalizar el cambio de los datos";
            }
        }
        catch (Exception E){
            VG.Comentario_Consulta=E.getMessage();
        }
        CloseConnection();
        return Check;
    }

    public String getSolicitante() { return Solicitante; }
    public void setSolicitante(String Solicitante) { this.Solicitante=Solicitante; }

    public String getIdentificacion() { return Identificacion; }
    public void setIdentificacion(String Identificacion) { this.Identificacion=Identificacion; }

    public String getDireccion() { return Direccion; }
    public void setDireccion(String W) { this.Direccion=W; }

    public String getBarrio() { return Barrio; }
    public void setBarrio(String W) { this.Barrio=W; }

    public String getCiudad(){ return Ciudad;}
    public void setCiudad(String W){this.Ciudad=W;}

    public String getTelefono(){ return Telefono;}
    public void setTelefono(String W){this.Telefono=W;}

    public String getMarca(){ return Marca;}
    public void setMarca(String W){this.Marca=W;}

    public String getSerie(){ return Serie;}
    public void setSerie(String W){this.Serie=W;}

    public String getOtro(){ return Otro;}
    public void setOtro(String W){this.Otro=W;}

    public String getOrden(){ return Orden;}
    public void setOrden(String W){this.Orden=W;}

    public int getValor(){ return Valor;}
    public void setValor(int W){this.Valor=W;}

    public int getAbono(){ return Abono;}
    public void setAbono(int W){this.Abono=W;}

}
