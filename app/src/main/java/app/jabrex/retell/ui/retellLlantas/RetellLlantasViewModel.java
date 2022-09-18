package app.jabrex.retell.ui.retellLlantas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RetellLlantasViewModel extends ViewModel {
    private MutableLiveData<String> mText;
    public RetellLlantasViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }
    public LiveData<String> getText() {
        return mText;
    }

}
