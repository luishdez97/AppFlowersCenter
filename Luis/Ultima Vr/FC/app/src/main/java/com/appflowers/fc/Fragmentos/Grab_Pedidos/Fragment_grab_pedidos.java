package com.appflowers.fc.Fragmentos.Grab_Pedidos;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appflowers.fc.R;

public class Fragment_grab_pedidos extends Fragment {

    private GrabPedidosViewModel mViewModel;

    public static Fragment_grab_pedidos newInstance() {
        return new Fragment_grab_pedidos();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.grab_pedidos_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(GrabPedidosViewModel.class);
        // TODO: Use the ViewModel
    }

}
