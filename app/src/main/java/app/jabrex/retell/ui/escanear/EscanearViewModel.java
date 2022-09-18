package app.jabrex.retell.ui.escanear;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EscanearViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EscanearViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}