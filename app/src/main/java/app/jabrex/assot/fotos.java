package app.jabrex.assot;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import com.jcraft.jsch.ChannelSftp.LsEntry;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
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
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.assot.R;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

public class fotos extends AppCompatActivity {

    private Button ChargeBtn,UploadImagaBtn;
    private ImageButton BackBtn,NextBtn;
    private ImageSwitcher imageIs;
    private List<String> UploadImages=new ArrayList<>();
    private TextView Contador;
    private ArrayList<Uri> imageUris;
    private int UpDown=0;
    private static final int PICK_IMAGES_CODE=0;
    private List<String> A1;
    int position =0;
    private int ServerFotos=0;

    int Fotos =0;




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);
        getSupportActionBar().hide();
        imageIs=findViewById(R.id.imageSwitcher);
        BackBtn=findViewById(R.id.fotos_back);
        NextBtn=findViewById(R.id.fotos_next);
        Contador=findViewById(R.id.fotos_posicion);
        //Iniciar lista
        imageUris=new ArrayList<>();
        //
        imageIs.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView=new  ImageView(getApplicationContext());
                return imageView;
            }
        });


        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position>0){
                    position--;
                    imageIs.setImageURI(imageUris.get(position));
                    Contador.setText((position+1)+"/"+Fotos);
                }
                else {
                    ToastGenerator("No Hay imagenes previas");
                }

            }
        });

        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position<imageUris.size()-1){
                    position++;
                    imageIs.setImageURI(imageUris.get(position));
                    Contador.setText((position+1)+"/"+Fotos);
                }
                else {
                    ToastGenerator("No hay mas imagenes");
                }
            }
        });

        ChargeBtn=findViewById(R.id.Boton_1);
        ChargeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImagesIntent();
            }
        });

        UploadImagaBtn=findViewById(R.id.fotos_subir);
        UploadImagaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImagesTosServer();
            }
        });
        AutoChargeImages();

    }
    private void AutoChargeImages(){
        UpDown=1;
        new Dback().execute("");
    }

    private void UploadImagesTosServer(){
        UpDown=0;
        new Dback().execute("");
    }

    private void pickImagesIntent(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        startActivityForResult(Intent.createChooser(intent,"Seleccionar imagenes"),PICK_IMAGES_CODE);

    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        List<String> A1=new ArrayList<>();
        try {
            if (requestCode ==PICK_IMAGES_CODE) {
                if (resultCode == RESULT_OK) {
                    if (data.getClipData() != null) {
                        //picked multiple images
                        Fotos = data.getClipData().getItemCount();
                        String nn="Cantidad de fotos seleccionadas: "+Fotos;
                        ToastGenerator(nn);
                        for (int i = 0; i < Fotos; i++) {
                            Uri imageUri = data.getClipData().getItemAt(i).getUri();
                            imageUris.add(imageUri);
                            InputStream inputStream=getContentResolver().openInputStream(imageUri);
                            OutputStream OT= new FileOutputStream(new File("/storage/emulated/0/Android/data/app.jabrex.retell/files/UploadImage"+(ServerFotos+1+i)+".jpeg"));
                            UploadImages.add("/storage/emulated/0/Android/data/app.jabrex.retell/files/CompImage"+(ServerFotos+1+i)+".jpeg");
                            byte[] buf = new byte[1024];
                            int len;
                            while((len=inputStream.read(buf))>0){
                                OT.write(buf,0,len);
                            }
                            OT.close();
                            inputStream.close();
                            ComprimirImagen("/storage/emulated/0/Android/data/app.jabrex.retell/files/UploadImage"+(ServerFotos+1+i)+".jpeg",(ServerFotos+1+i));

                        }
                        imageIs.setImageURI(imageUris.get(0));
                        position = 0;
                        Contador.setText("1/" + Fotos);
                    } else {
                        //picked single image
                        Fotos = 1;
                        Uri imageUri = data.getData();
                        InputStream inputStream=getContentResolver().openInputStream(imageUri);
                        OutputStream OT= new FileOutputStream(new File("/storage/emulated/0/Android/data/app.jabrex.retell/files/UploadImage"+(ServerFotos+1)+".jpeg"));
                        byte[] buf = new byte[1024];
                        int len;
                        while((len=inputStream.read(buf))>0){
                            OT.write(buf,0,len);
                        }
                        OT.close();
                        inputStream.close();
                        ComprimirImagen("/storage/emulated/0/Android/data/app.jabrex.retell/files/UploadImage"+(ServerFotos+1)+".jpeg",(ServerFotos+1));
                        UploadImages.add("/storage/emulated/0/Android/data/app.jabrex.retell/files/CompImage"+(ServerFotos+1)+".jpeg");
                        imageUris.add(imageUri);
                        imageIs.setImageURI(imageUris.get(0));
                        position = 0;
                        Contador.setText("1/1");
                    }
                }
            }

        } catch (Exception E) {
            ToastGenerator(E.getMessage());
        }
    }
    private Boolean ComprimirImagen(String Path,int i){
        Boolean N1=false;
        try {
            OutputStream outputStream=new FileOutputStream("/storage/emulated/0/Android/data/app.jabrex.retell/files/CompImage"+(i)+".jpeg");
            BitmapFactory.Options bitmapOptions=new BitmapFactory.Options();
            Bitmap bitmap=BitmapFactory.decodeFile("/storage/emulated/0/Android/data/app.jabrex.retell/files/UploadImage"+(i)+".jpeg",bitmapOptions);
            bitmap.compress(Bitmap.CompressFormat.JPEG,90,outputStream);
            outputStream.close();
            N1=true;
        }
        catch (Exception E){
            VG.Comentario_Consulta=E.getMessage();
        }
        return N1;
    }


    private void LoadServerImages(List<String> ImageList){
        ServerFotos =ImageList.size();
        if(ImageList.size()>1){
            //picked multiple images
            for (int i=0;i<ImageList.size();i++){
                imageUris.add(Uri.fromFile(new File("/storage/emulated/0/Android/data/app.jabrex.retell/files/"+"TemporalImage"+i+ImageList.get(i).substring(ImageList.get(i).lastIndexOf(".")))));
            }
            imageIs.setImageURI(imageUris.get(0));
            position=0;
            Contador.setText("1/"+ImageList.size());
        }
        else if(ImageList.size()==1){
            imageUris.add(Uri.fromFile(new File("/storage/emulated/0/Android/data/app.jabrex.retell/files/TemporalImage0"+ImageList.get(0).substring(ImageList.get(0).lastIndexOf(".")))));
            imageIs.setImageURI(imageUris.get(0));
            position=0;
            Contador.setText("1/1");
        }
        else if(ImageList.size()==0){
            ToastGenerator("Aun no se han cargado imagenes de la reparacion de la llanta");
        }
    }

    public void ToastGenerator(String string){
        Toast toast = Toast.makeText(this, string, Toast.LENGTH_LONG);
        toast.show();
    }
    private class Dback extends AsyncTask<String, Void, String> {
        ProgressDialog N3 = null;
        Session session2=null;
        boolean Check=false;
        Vector<LsEntry> fileList;
        List<String> list =new ArrayList<>();
        @Override
        protected void onPreExecute() {
            N3=new ProgressDialog(fotos.this);
            N3.show();
            N3.setContentView(R.layout.progress_dialog);
            N3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                final JSch jsch = new JSch();
                session2 = jsch.getSession("pi", VG.DBIP, 22);
                session2.setPassword("RETELL");
                final Properties config = new Properties();
                config.put("StrictHostKeyChecking", "no");
                session2.setConfig(config);
                session2.connect();
                Channel channelSFTP = session2.openChannel("sftp");
                channelSFTP.connect();
                ChannelSftp sftp = (ChannelSftp) channelSFTP;
                if(UpDown==1) {
                    try {
                        sftp.mkdir("/home/pi/fotos/" + VG.ORDENS);
                    } catch (Exception E) {

                    }
                    fileList = sftp.ls("/home/pi/fotos/" + VG.ORDENS);
                    for (LsEntry entry : fileList) {
                        if (!entry.getFilename().equals(".") && !entry.getFilename().equals("..")) {
                            list.add(entry.getFilename());
                        }
                    }
                    for (int i = 0; i < list.size(); i++) {
                        File tempName = new File(getExternalFilesDir(null), "TemporalImage" + i + list.get(i).substring(list.get(i).lastIndexOf(".")));
                        OutputStream tempOs = new FileOutputStream(tempName);
                        sftp.get("/home/pi/fotos/"+VG.ORDENS+"/" + list.get(i), tempOs);
                    }
                    Check=true;
                    channelSFTP.disconnect();
                }
                else if(UpDown==0){
                    int Vsize=UploadImages.size();
                    if(Vsize>1){
                        for(int i=0;i<UploadImages.size();i++){
                            sftp.put(UploadImages.get(i),"/home/pi/fotos/"+VG.ORDENS+"/UploadImage"+(ServerFotos+i+1)+".png");
                        }
                    }
                    else if(Vsize==1){
                        sftp.put(UploadImages.get(0),"/home/pi/fotos/"+VG.ORDENS+"/UploadImage"+(ServerFotos+1)+".png");
                    }
                    Check=true;
                    channelSFTP.disconnect();
                }

            }
            catch (Exception E){
                VG.Comentario_Consulta=E.getMessage();

            }

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                if(Check){
                    switch (UpDown){
                        case 1:{
                            if(list.size()==0){
                                ToastGenerator("Aun no se han cargado imagenes al servidor");
                            }
                            else
                            {
                                ToastGenerator("Las imagenes han sido descargadas con exito del servidor:"+list.size());
                                LoadServerImages(list);
                            }
                            break;
                        }
                        case 0:{
                            ToastGenerator("Las imagenes han sido cargadas con exito al servidor");
                        }
                    }
                }
                else {
                    ToastGenerator(VG.Comentario_Consulta);
                }
            }
            catch (Exception E){
                if(UpDown==0){
                    ToastGenerator("Ocurrio un error al descargar las imagenes del servidor: "+E.getMessage());
                }
                else if (UpDown==1){
                    ToastGenerator("Ocurrio un error al cargar las imagenes al servidor: "+E.getMessage());
                }

            }
            N3.dismiss();
        }
    }
}