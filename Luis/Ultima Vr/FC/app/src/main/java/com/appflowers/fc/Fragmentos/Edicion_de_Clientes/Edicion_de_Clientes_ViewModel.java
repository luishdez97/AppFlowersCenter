package com.appflowers.fc.Fragmentos.Edicion_de_Clientes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Edicion_de_Clientes_ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Edicion_de_Clientes_ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Edicion de clientes");
    }

    public LiveData<String> getText() {
        return mText;
    }
}