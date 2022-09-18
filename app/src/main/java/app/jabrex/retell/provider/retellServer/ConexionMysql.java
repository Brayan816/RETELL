package app.jabrex.retell.provider.retellServer;
import java.io.OutputStream;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import app.jabrex.retell.UC;

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
            UC.Comentario_Consulta=e.getMessage();
        }
    }
    public Boolean OpenConnection(){
        boolean CHECK=false;
        try {
                final JSch jsch = new JSch();
                session = jsch.getSession("pi", UC.DBIP, 22);
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
            UC.Comentario_Consulta=E.getMessage();
        }
        return CHECK;
    }
    public void CloseConnection(){
        try {
            session.disconnect();
            con.close();
        }
        catch (Exception E){
            //UC.Comentario_Consulta=E.getMessage();
        }
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
                RES2= UC.Comentario_Consulta;
            }
        } catch (SQLException e) {
            RES2=e.getMessage();
        }
        UC.Comentario_Consulta=RES2;
        CloseConnection();
        return Check;
    }
    public Boolean executeCommand(String Orden,OutputStream os) {
        boolean N10=false;
        UC.Comentario_Consulta="PASO XD";
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
                UC.Comentario_Consulta=line;
            }
            channel.isClosed();
            Get_Image(Orden,os);
            channel.disconnect();
            N10=true;
            //session.setPortForwardingL(3306, "localhost", 3306);

        } catch (Exception e) {
            UC.Comentario_Consulta=e.getMessage();
        }
        return N10;

    }
    private boolean Get_Image(String Orden_SFTP,OutputStream os){
        boolean Check=false;
        try {
            Channel channelSFTP=session2.openChannel("sftp");
            channelSFTP.connect();
            ChannelSftp sftp=(ChannelSftp) channelSFTP;
            sftp.cd("cd /home/pi/ActivityFotos/"+Orden_SFTP);
            sftp.get("H1.jpeg",os);
            UC.Comentario_Consulta="Todo OK";
            Check=true;
        }
        catch (Exception E){
            UC.Comentario_Consulta=E.getMessage();
        }
        return  Check;
    }




}
