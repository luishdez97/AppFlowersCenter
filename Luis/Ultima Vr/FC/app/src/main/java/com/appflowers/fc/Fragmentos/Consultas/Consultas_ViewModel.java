package com.appflowers.fc.Fragmentos.Consultas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Consultas_ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Consultas_ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Modulo de consulta");
    }

    public LiveData<String> getText() {
        return mText;
    }
}