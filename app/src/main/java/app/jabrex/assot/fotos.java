package app.jabrex.assot;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.assot.R;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class fotos extends AppCompatActivity {

    ImageView FOTO;
    ImageButton ROTATE;
    private String CPP;
    ImageView N1;
    Button Boton;
    Bitmap bitmap;
    ConexionMysql conexionMysql=new ConexionMysql();
    ImageSlider imageSlider;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);
        getSupportActionBar().hide();
        imageSlider=findViewById(R.id.Slider);
        imageSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastGenerator("Holis 1");
            }
        });
        File DIRA= new File(getExternalFilesDir(null),"H1.jpeg");
        List<SlideModel> slideModels=new ArrayList<>();
        slideModels.add(new SlideModel("/storage/emulated/0/Android/data/app.jabrex.retell/files/H1.jpeg","Prueba_1"));
        slideModels.add(new SlideModel(R.drawable.x2));
        slideModels.add(new SlideModel(R.drawable.x3));
        slideModels.add(new SlideModel(R.drawable.x4));
        slideModels.add(new SlideModel(R.drawable.x5));
        N1=findViewById(R.id.imageView2);
        N1.setImageURI(Uri.parse("/storage/emulated/0/Android/data/app.jabrex.retell/files/H1.jpeg"));
        imageSlider.setImageList(slideModels,true);
        Boton=findViewById(R.id.Boton_1);
        Boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Boton.setText("Preiono");
                    new Dback().execute("");

            }
        });

    }
    public void ToastGenerator(String string){
        Toast toast = Toast.makeText(this, string, Toast.LENGTH_LONG);
        toast.show();
    }
    private class Dback extends AsyncTask<String, Void, String> {
        ProgressDialog N3 = null;
        String FTA="";
        boolean Check=false;
        @Override
        protected void onPreExecute() {
            N3=new ProgressDialog(fotos.this);
            N3.show();
            N3.setContentView(R.layout.progress_dialog);
            N3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        @Override
        protected String doInBackground(String... strings) {
            Session session = null;
            Channel channel = null;
            ChannelSftp channelSftp = null;
            try {
                String Path = "/home/pi/Fotos/"+VG.ORDENS;
                JSch jsch = new JSch();
                java.util.Properties configuration = new java.util.Properties();
                configuration.put("StrictHostKeyChecking", "no");
                session = jsch.getSession("pi", "192.168.20.200",22);
                session.setPassword("RETELL");
                session.setConfig(configuration);
                session.connect();
                channel=session.openChannel("sftp");
                channel.connect();
                channelSftp=(ChannelSftp) channel;
                channelSftp.cd(Path);
                Vector<ChannelSftp.LsEntry> filelist = channelSftp.ls("*");
                ArrayList<String> DFA=new ArrayList<>();
                int i=0;
                for (ChannelSftp.LsEntry entry:filelist) {
                    channelSftp.get(entry.getFilename().toString());
                    DFA.add(entry.getFilename());
                }
                for (String FileName:DFA) {
                    File DIRA= new File(getExternalFilesDir(null),FileName);
                    OutputStream os=new FileOutputStream(DIRA);
                    channelSftp.get(FileName,os);

                }
                channel.disconnect();
                session.disconnect();

            } catch (Exception e) {

                VG.Comentario_Consulta=e.getMessage();
            }
            return strings[0];
        }

        @Override
        protected void onPostExecute(String s) {
            File DIRA= new File(getExternalFilesDir(null),"H1.jpeg");
            ToastGenerator(DIRA.getAbsolutePath());
            try{
                if (Check) {
                    ToastGenerator("si");
                } else {
                    ToastGenerator(VG.Comentario_Consulta);
                }
            }
            catch (Exception E){
                ToastGenerator(E.getMessage());
            }
            N3.dismiss();
        }
    }
}