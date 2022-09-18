package app.jabrex.retell.ui.busqueda;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BusquedaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BusquedaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}