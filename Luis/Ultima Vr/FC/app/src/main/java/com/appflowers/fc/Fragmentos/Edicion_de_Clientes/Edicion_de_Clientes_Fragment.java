package com.appflowers.fc.Fragmentos.Edicion_de_Clientes;

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

public class Edicion_de_Clientes_Fragment extends Fragment {

    private Edicion_de_Clientes_ViewModel ediciondeClientesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ediciondeClientesViewModel =
                ViewModelProviders.of(this).get(Edicion_de_Clientes_ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_edicion_clientes, container, false);
        final TextView textView = root.findViewById(R.id.text_edicion_clientes);
        ediciondeClientesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}