package com.pasofe.gestinclave.preferencias;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.pasofe.gestinclave.R;

public class preferenciasFragment extends PreferenceFragment {
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);
    }
}
