package com.pasofe.gestinclave;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pasofe.gestinclave.accesoadatos.sqlAplicacion;
import com.pasofe.gestinclave.databinding.ActivityCambioDatosBinding;
import com.pasofe.gestinclave.databinding.ActivityMainBinding;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

public class cambioDatosActivity extends AppCompatActivity {

    private ActivityCambioDatosBinding binding3;

    private EditText editNombre, editApellidos, editCorreo, editNumero;
    private sqlAplicacion clasesql;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding3 = ActivityCambioDatosBinding.inflate(getLayoutInflater());
        setContentView(binding3.getRoot());

        String nombre = "", apellidos = "", correo = "";
        int tlf = 0;


        editNombre = findViewById(R.id.editTextNombre);
        editApellidos = findViewById(R.id.editTextApellidos);
        editCorreo = findViewById(R.id.editTextCorreo);
        editNumero = findViewById(R.id.editTextNumero);

        clasesql = new sqlAplicacion(getApplicationContext());

        //Posible NULL
        nombre = clasesql.recogerNombre();


        if(nombre == null) {
            binding3.buttonSoloNombre.setVisibility(View.INVISIBLE);
            binding3.buttonGuardarCambio.setVisibility(View.VISIBLE);
            binding3.buttonSoloApellidos.setVisibility(View.INVISIBLE);
            binding3.buttonSoloCorreo.setVisibility(View.INVISIBLE);
            binding3.buttonSoloTlf.setVisibility(View.INVISIBLE);
        }else {
            binding3.buttonGuardarCambio.setVisibility(View.INVISIBLE);
            binding3.buttonSoloNombre.setVisibility(View.VISIBLE);
            binding3.buttonSoloApellidos.setVisibility(View.VISIBLE);
            binding3.buttonSoloCorreo.setVisibility(View.VISIBLE);
            binding3.buttonSoloTlf.setVisibility(View.VISIBLE);
        }





        binding3.buttonGuardarCambio.setOnClickListener(new View.OnClickListener() {
            boolean todoOK = false;
            @Override
            public void onClick(View v) {
                todoOK = clasesql.guardarTodo(editNombre.getText().toString(), editApellidos.getText().toString(),
                        editNumero.getText().toString(), editCorreo.getText().toString());
                if(todoOK) {
                    Toast.makeText(cambioDatosActivity.this, "Guardado Correctamente", Toast.LENGTH_SHORT).show();
                    editNombre.setText(" "); editApellidos.setText(" "); editCorreo.setText(" "); editNombre.setText(" ");
                }
            }
        });

        binding3.buttonSoloNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clasesql.updateNombre(editNombre.getText().toString());
                Toast.makeText(cambioDatosActivity.this, "Nombre Actualizado", Toast.LENGTH_SHORT).show();
                editNombre.setText("");
            }
        });

        binding3.buttonSoloApellidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clasesql.updateApellidos(editApellidos.getText().toString());
                Toast.makeText(cambioDatosActivity.this, "Apellidos Actualizado", Toast.LENGTH_SHORT).show();
                editApellidos.setText("");
            }
        });

        binding3.buttonSoloCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clasesql.updateCorreo(editCorreo.getText().toString());
                Toast.makeText(cambioDatosActivity.this, "Correo Actualizado", Toast.LENGTH_SHORT).show();
                editCorreo.setText("");
            }
        });

        binding3.buttonSoloTlf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clasesql.updateNumero(Integer.parseInt(editNumero.getText().toString()));
                Toast.makeText(cambioDatosActivity.this, "Numero Actualizado", Toast.LENGTH_SHORT).show();
                editNumero.setText(0);
            }
        });
    }
}