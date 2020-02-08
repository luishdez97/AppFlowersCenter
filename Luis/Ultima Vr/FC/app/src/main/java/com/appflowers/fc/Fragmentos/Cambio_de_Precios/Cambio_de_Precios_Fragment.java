package com.appflowers.fc.Fragmentos.Cambio_de_Precios;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.appflowers.fc.R;

public class Cambio_de_Precios_Fragment extends Fragment {

    private Cambio_de_Precios_ViewModel cambiodePreciosViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cambiodePreciosViewModel =
                ViewModelProviders.of(this).get(Cambio_de_Precios_ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cambio_precios, container, false);
        final TextView textView = root.findViewById(R.id.text_cambio_precios);
        cambiodePreciosViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}