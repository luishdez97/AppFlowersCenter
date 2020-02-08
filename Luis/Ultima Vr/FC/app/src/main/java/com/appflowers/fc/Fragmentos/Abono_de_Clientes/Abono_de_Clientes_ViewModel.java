package com.appflowers.fc.Fragmentos.Abono_de_Clientes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Abono_de_Clientes_ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Abono_de_Clientes_ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Abonos de clientes");
    }

    public LiveData<String> getText() {
        return mText;
    }
}