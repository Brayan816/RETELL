package app.jabrex.retell.ui.fotos;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jcraft.jsch.ChannelSftp.LsEntry;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.retell.R;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import app.jabrex.retell.UC;

public class ActivityFotos extends AppCompatActivity {

    private ImageButton BackBtn,NextBtn;
    private ImageSwitcher imageIs;
    private FloatingActionButton chargeBtn,uploadImage,expand;
    private List<String> UploadImages=new ArrayList<>();
    private TextView Contador;
    private ArrayList<Uri> imageUris;
    private int UpDown=0;
    private static final int PICK_IMAGES_CODE=0;
    private int position=0,Fotos=0,ServerFotos=0;
    private Animation anOpen,anClose,anRotateForwad,anRotateBackward;
    boolean isOpen = false;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);
        getSupportActionBar().hide();
        expand=findViewById(R.id.fbExpandPhoto);
        chargeBtn=findViewById(R.id.fbCargar);
        uploadImage=findViewById(R.id.fbUpload);
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
                if(position<(imageUris.size()-1)){
                    position++;
                    imageIs.setImageURI(imageUris.get(position));
                    Contador.setText((position+1)+"/"+(Fotos));
                }
                else {
                    ToastGenerator("No hay mas imagenes");
                }
            }
        });
        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFab();
            }
        });
        chargeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImagesIntent();
            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImagesTosServer();
            }
        });
        //Animations
        anOpen= AnimationUtils.loadAnimation(this,R.anim.fab_open);
        anClose=AnimationUtils.loadAnimation(this,R.anim.fab_close);
        anRotateForwad=AnimationUtils.loadAnimation(this,R.anim.rotate_forward);
        anRotateBackward=AnimationUtils.loadAnimation(this,R.anim.rotate_backward);
        AutoChargeImages();

    }
    private void animateFab(){
        if (isOpen){
            expand.startAnimation(anRotateForwad);
            chargeBtn.startAnimation(anClose);
            uploadImage.startAnimation(anClose);
            isOpen=false;
        }else {
            expand.startAnimation(anRotateBackward);
            chargeBtn.startAnimation(anOpen);
            uploadImage.startAnimation(anOpen);
            isOpen=true;
        }
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
                        Fotos +=data.getClipData().getItemCount();
                        String nn="Cantidad de ActivityFotos seleccionadas: "+Fotos;
                        ToastGenerator(nn);
                        for (int i = 0; i < data.getClipData().getItemCount(); i++) {
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
                        Fotos += 1;
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
                        Contador.setText("1/"+Fotos);
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
            bitmap.compress(Bitmap.CompressFormat.JPEG,70,outputStream);
            outputStream.close();
            N1=true;
        }
        catch (Exception E){
            UC.Comentario_Consulta=E.getMessage();
        }
        return N1;
    }


    private void LoadServerImages(List<String> ImageList){
        String pathName="";

        try{
            for (int i=0;i<ImageList.size();i++){
                pathName="/storage/emulated/0/Android/data/app.jabrex.retell/files/"+"TemporalImage"+i+ImageList.get(i).substring(ImageList.get(i).lastIndexOf("."));
                imageUris.add(Uri.fromFile(new File("/storage/emulated/0/Android/data/app.jabrex.retell/files/"+"TemporalImage"+(i+1)+ImageList.get(i).substring(ImageList.get(i).lastIndexOf(".")))));
            }
            imageIs.setImageURI(imageUris.get(0));
            position=0;
            Fotos=ImageList.size();
            ServerFotos=Fotos;
            Contador.setText("1/"+Fotos);
        }
        catch (Exception E){
            ToastGenerator(E.getMessage()+"---"+ImageList);
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
            N3=new ProgressDialog(ActivityFotos.this);
            N3.show();
            N3.setContentView(R.layout.progress_dialog);
            N3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                final JSch jsch = new JSch();
                session2 = jsch.getSession("pi", UC.DBIP, 22);
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
                        sftp.mkdir("/home/pi/ActivityFotos/" + UC.ORDENS);
                    } catch (Exception E) {

                    }
                    fileList = sftp.ls("/home/pi/ActivityFotos/" + UC.ORDENS);
                    for (LsEntry entry : fileList) {
                        if (!entry.getFilename().equals(".") && !entry.getFilename().equals("..")) {
                            list.add(entry.getFilename());
                        }
                    }
                    for (int i = 0; i < list.size(); i++) {
                        File tempName = new File(getExternalFilesDir(null), "TemporalImage" + (i+1) + list.get(i).substring(list.get(i).lastIndexOf(".")));
                        OutputStream tempOs = new FileOutputStream(tempName);
                        sftp.get("/home/pi/ActivityFotos/"+ UC.ORDENS+"/" + list.get(i), tempOs);
                    }
                    Check=true;
                    channelSFTP.disconnect();
                }
                else if(UpDown==0){
                    int Vsize=UploadImages.size();
                    if(Vsize>1){
                        for(int i=0;i<UploadImages.size();i++){
                            sftp.put(UploadImages.get(i),"/home/pi/ActivityFotos/"+ UC.ORDENS+"/UploadImage"+(ServerFotos+i+1)+".png");
                        }
                    }
                    else if(Vsize==1){
                        sftp.put(UploadImages.get(0),"/home/pi/ActivityFotos/"+ UC.ORDENS+"/UploadImage"+(ServerFotos+1)+".png");
                    }
                    Check=true;
                    channelSFTP.disconnect();
                }

            }
            catch (Exception E){
                UC.Comentario_Consulta=E.getMessage();

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
                            break;
                        }
                    }
                }
                else {
                    ToastGenerator(UC.Comentario_Consulta);
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