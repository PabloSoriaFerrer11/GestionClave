package com.pasofe.gestinclave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.pasofe.gestinclave.accesoadatos.sqlAplicacion;
import com.pasofe.gestinclave.databinding.ActivityAgregarBinding;
import com.pasofe.gestinclave.databinding.ActivityMainBinding;

import java.time.LocalDate;

public class agregarActivity extends AppCompatActivity {

    ActivityAgregarBinding bindingAgregar;
    private sqlAplicacion clasesql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindingAgregar = ActivityAgregarBinding.inflate(getLayoutInflater());
        setContentView(bindingAgregar.getRoot());

        clasesql = new sqlAplicacion(getApplicationContext());

        //--

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);


        boolean modoEmpresa = pref.getBoolean("modoEmpresa",false);

        if(modoEmpresa){
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.arrayEmpresa, android.R.layout.simple_spinner_item);
            bindingAgregar.spinnerGastos.setAdapter(adapter);

            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.arrayEmpresa, android.R.layout.simple_spinner_item);
            bindingAgregar.spinnerIngresos.setAdapter(adapter1);
        }else{
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.arraySingular, android.R.layout.simple_spinner_item);
            bindingAgregar.spinnerGastos.setAdapter(adapter);

            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.arraySingular, android.R.layout.simple_spinner_item);
            bindingAgregar.spinnerIngresos.setAdapter(adapter1);
        }

        //--

        bindingAgregar.buttonAgregaGastos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bindingAgregar.editNumberGasto.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(), "Ingrese un numero", Toast.LENGTH_SHORT).show();
                }else{
                    clasesql.guardarGasto(Double.parseDouble(bindingAgregar.editNumberGasto.getText().toString()),
                            bindingAgregar.spinnerGastos.getSelectedItem().toString(),
                            LocalDate.now().getDayOfMonth());
                    bindingAgregar.editNumberGasto.setText("");
                }

            }
        });

        bindingAgregar.buttonAgregaIngresos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bindingAgregar.editNumberIngreso.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(), "Ingrese un numero", Toast.LENGTH_SHORT).show();
                }else{
                    clasesql.guardarIngreso(Double.parseDouble(bindingAgregar.editNumberIngreso.getText().toString()),
                            bindingAgregar.spinnerIngresos.getSelectedItem().toString(),
                            LocalDate.now().getDayOfMonth());
                    bindingAgregar.editNumberIngreso.setText("");
                }

            }
        });

    }
}