package app.jabrex.assot;
import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import app.jabrex.assot.VG;

public class ConexionMysql {
    Connection con=null;
    Session session=null;
    public String USUARIO;
    public String PASSWORD;
    private static Session session2;
    public ConexionMysql(){

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }
        catch (Exception e){
            VG.Comentario_Consulta=e.getMessage();
        }
    }
    public Boolean OpenConnection(){
        boolean CHECK=false;
        try {
                final JSch jsch = new JSch();
                session = jsch.getSession("pi",VG.DBIP, 22);
                session.setPassword("RETELL");
                final Properties config = new Properties();
                config.put("StrictHostKeyChecking", "no");
                session.setConfig(config);
                session.connect();
                session.setPortForwardingL(3306, "localhost", 3306);
                con=DriverManager.getConnection("jdbc:mysql://localhost:3306/RETELL","ROOT2","RETELL");
                CHECK=true;
        }
        catch (SQLException | JSchException E)  {
            CHECK=false;
            VG.Comentario_Consulta=E.getMessage();
        }
        return CHECK;
    }
    public void CloseConnection(){
        try {
            session.disconnect();
            con.close();
        }
        catch (Exception E){
            //VG.Comentario_Consulta=E.getMessage();
        }
    }
    public String JOIN(){
        String K1="";
        try{
            if(OpenConnection())
            {
                String Query="SELECT L.ORDEN_S AS ORDEN, C.SOLICITANTE AS SOLICITANTE FROM llantas L JOIN clientes C ON C.N_IDE=L.N_IDE WHERE C.N_IDE=?";
                PreparedStatement preparedStatement=con.prepareStatement(Query);
                preparedStatement.setString(1,"1091183353");
                ResultSet fr = preparedStatement.executeQuery();
                if (fr.next()) {
                    K1=fr.getNString("ORDEN")+fr.getNString("SOLICITANTE");
                }
                else{
                    K1="ERROR EN C1";
                }
            }
            else {
                K1="ERROR AL ESTABLECER CONEXION";
            }
        }
        catch (Exception E){
            K1=E.getMessage();
        }
        CloseConnection();
        return K1;
    }
    public boolean CHECK(){
        boolean Check=false;
        String RES2="";
        try {
            if (OpenConnection()) {
                String Query="SELECT *FROM usuarios WHERE USUARIO=?";
                PreparedStatement preparedStatement=con.prepareStatement(Query);
                preparedStatement.setString(1,USUARIO);
                ResultSet fr = preparedStatement.executeQuery();
                if (fr.next()) {
                    if(fr.getString("CONTRASEÑA").equals(PASSWORD)){
                        Check=true;
                        RES2="Contraseña Correcta";
                    }
                    else {
                        RES2="Contraseña Incorrecta";
                        Check=false;
                    }
                }
                else {
                        RES2="Usuario Incorrecto";
                        Check=false;
                }
            }
            else {
                RES2=VG.Comentario_Consulta;
            }
        } catch (SQLException e) {
            RES2=e.getMessage();
        }
        VG.Comentario_Consulta=RES2;
        CloseConnection();
        return Check;
    }
    public Boolean executeCommand(String Orden,OutputStream os) {
        boolean N10=false;
        VG.Comentario_Consulta="PASO XD";
        try {
            final JSch jsch2 = new JSch();
            session2 = jsch2.getSession("pi","192.168.20.200", 22);
            session2.setPassword("RETELL");
            final Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session2.setConfig(config);
            session2.connect();
            Channel channel=session2.openChannel("exec");
            InputStream in=channel.getInputStream();
            ((ChannelExec) channel).setCommand("cd /home/pi/Fotos && mkdir "+Orden);
            ((ChannelExec) channel).setErrStream(System.err);
            channel.connect();
            BufferedReader reader=new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine())!=null){
                VG.Comentario_Consulta=line;
            }
            channel.isClosed();
            Get_Image(Orden,os);
            channel.disconnect();
            N10=true;
            //session.setPortForwardingL(3306, "localhost", 3306);

        } catch (Exception e) {
            VG.Comentario_Consulta=e.getMessage();
        }
        return N10;

    }
    private boolean Get_Image(String Orden_SFTP,OutputStream os){
        boolean Check=false;
        try {
            Channel channelSFTP=session2.openChannel("sftp");
            channelSFTP.connect();
            ChannelSftp sftp=(ChannelSftp) channelSFTP;
            sftp.cd("cd /home/pi/fotos/"+Orden_SFTP);
            sftp.get("H1.jpeg",os);
            VG.Comentario_Consulta="Todo OK";
            Check=true;
        }
        catch (Exception E){
            VG.Comentario_Consulta=E.getMessage();
        }
        return  Check;
    }




}
