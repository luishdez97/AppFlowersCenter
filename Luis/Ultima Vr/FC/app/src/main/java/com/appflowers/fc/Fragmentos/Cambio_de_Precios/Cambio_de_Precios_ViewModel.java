package com.appflowers.fc.Fragmentos.Cambio_de_Precios;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Cambio_de_Precios_ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Cambio_de_Precios_ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Cambio de precios");
    }

    public LiveData<String> getText() {
        return mText;
    }
}