package com.appflowers.fc.Fragmentos.Abono_de_Clientes;

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

public class Abono_de_Clientes_Fragment extends Fragment {

    private Abono_de_Clientes_ViewModel abonodeClientesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        abonodeClientesViewModel =
                ViewModelProviders.of(this).get(Abono_de_Clientes_ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_abonos_clientes, container, false);
        final TextView textView = root.findViewById(R.id.text_abono_clientes);
        abonodeClientesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}