package com.pasofe.gestinclave.preferencias;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

public class preferenciasActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace
                (android.R.id.content, new preferenciasFragment()).commit();
    }
}
