package com.appflowers.fc.Fragmentos.Facturacion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Facturacion_ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Facturacion_ViewModel() {
        mText = new MutableLiveData<>();
        mText.postValue("");
        
    }

    public LiveData<String> getText() {
        return mText;
    }
}