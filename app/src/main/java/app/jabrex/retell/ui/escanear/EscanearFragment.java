package app.jabrex.retell.ui.escanear;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.retell.R;
import com.google.zxing.Result;
import app.jabrex.retell.UC;
import app.jabrex.retell.ui.principal.ActitvityPrincipal;


public class EscanearFragment extends Fragment {
    CodeScanner codeScanner2;
    private static final String TEG="SearchActivity";
    CodeScannerView scannview2;
    private EscanearViewModel escanearViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        escanearViewModel =
                new ViewModelProvider(this).get(EscanearViewModel.class);
        View root = inflater.inflate(R.layout.fragment_escanear, container, false);
        escanearViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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
                            UC.ORDENS="";
                            UC.ORDENS=result.getText();
                            ToastGenerator(UC.ORDENS);
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
        Intent intent = new Intent(getActivity(), ActitvityPrincipal.class);
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