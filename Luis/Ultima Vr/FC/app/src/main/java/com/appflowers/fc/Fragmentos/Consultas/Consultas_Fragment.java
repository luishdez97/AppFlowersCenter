package com.appflowers.fc.Fragmentos.Consultas;

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

public class Consultas_Fragment extends Fragment {

    private Consultas_ViewModel consultasViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        consultasViewModel =
                ViewModelProviders.of(this).get(Consultas_ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_consultas, container, false);
        final TextView textView = root.findViewById(R.id.text_consultas);
        consultasViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}