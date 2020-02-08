package com.appflowers.fc.Fragmentos.Facturacion;

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

public class Facturacion_Fragment extends Fragment {

    private Facturacion_ViewModel facturacionViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        facturacionViewModel =
                ViewModelProviders.of(this).get(Facturacion_ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_facturacion, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        facturacionViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}