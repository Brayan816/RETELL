package app.jabrex.assot.ui.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.assot.R;
import com.google.zxing.Result;

import java.util.Objects;

import app.jabrex.assot.MainActivity;
import app.jabrex.assot.VG;


public class GalleryFragment extends Fragment {
    CodeScanner codeScanner2;
    private static final String TEG="SearchActivity";
    private static final int REQUEST_CODE=1;
    CodeScannerView scannview2;
    public TextView TXA2;
    public String TAG;
    public int DRE=1;
    private GalleryViewModel galleryViewModel;
    private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        scannview2=root.findViewById(R.id.scanner_view);
        codeScanner2=new CodeScanner(getActivity(),scannview2);
        codeScanner2.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(result.getText().length()>3 && result.getText().length()<=5) {
                            VG.ORDENS="";
                            VG.ORDENS=result.getText();
                            ToastGenerator(VG.ORDENS);
                            AbrirQr();
                        }
                        else{
                            ToastGenerator("ERROR, QR EQUIVOCADO");
                            onResume();
                        }
                    }
                });
            }
        });

        return root;
    }
    public void AbrirQr() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
    public void ToastGenerator(String string){
        Toast toast = Toast.makeText(getContext(), string, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        codeScanner2.startPreview();
    }
}