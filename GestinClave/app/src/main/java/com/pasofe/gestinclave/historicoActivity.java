package com.pasofe.gestinclave;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.pasofe.gestinclave.databinding.ActivityHistoricoBinding;
import com.pasofe.gestinclave.databinding.ActivityMainBinding;
import com.pasofe.gestinclave.recyclerview.Adaptador;

public class historicoActivity extends AppCompatActivity {

    private ActivityHistoricoBinding binding3;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding3 = ActivityHistoricoBinding.inflate(getLayoutInflater());
        setContentView(binding3.getRoot());



        layoutManager = new LinearLayoutManager(this);
        binding3.mainLista.recyclerView.setLayoutManager(layoutManager);
        adapter = new Adaptador();
        binding3.mainLista.recyclerView.setAdapter(adapter);

    }
}