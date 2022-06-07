package com.pasofe.gestinclave;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.pasofe.gestinclave.accesoadatos.sqlAplicacion;
import com.pasofe.gestinclave.databinding.ActivityMainBinding;
import com.pasofe.gestinclave.preferencias.preferenciasActivity;

import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private NotificationManager notificationManager;

    private double totalGastadoMes = 0, totalGastadoDia = 0;
    private double totalGanadoMes = 0, totalGanadoDia;

    private String nombreUsu;
    private String temaGastos = "";

    private sqlAplicacion clasesql;


    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        clasesql = new sqlAplicacion(getApplicationContext());
        clasesql.guardarFechas(LocalDate.now().getDayOfMonth(), String.valueOf(LocalDate.now().getMonth()),LocalDate.now().getYear());
        System.out.println("PASANDO POR EL ONCREATE");
        System.out.println("PASANDO POR EL ONCREATE");
        System.out.println("PASANDO POR EL ONCREATE");

        binding.buttonAnyadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(MainActivity.this, agregarActivity.class);
                startActivity(intento);
            }
        });

        binding.buttonHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(MainActivity.this, historicoActivity.class);
                startActivity(intento);
            }
        });

        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel(
                "com.pasofe.notify",
                "Limite Alcanzado",
                "Has alcanzado el límite");
    }


    @Override
    public void onStart() {

        super.onStart();


        nombreUsu = clasesql.recogerNombre();
        if(nombreUsu==null){
            Snackbar.make(binding.Coordinator, "No tiene nombre de usuario. \n  Cambielo en el menú de arriba a la derecha", Snackbar.LENGTH_LONG).show();
        }else{
            Snackbar.make(binding.Coordinator, "Bienvenido " + nombreUsu, Snackbar.LENGTH_LONG).show();
        }
    }



    private void mirarSiMesDiferente(){
        String mActual = String.valueOf(LocalDate.now().getMonth());
        String mBaseDatos = clasesql.recogerMes();


        System.out.println("\t" + "Actual: " + mActual + " \n BaseDatos: " + mBaseDatos );

        clasesql.sumatorioMes(mActual);


        clasesql.updateMes(mActual);

    }

    private void mirarSiAnyoDiferente(){
        int aActual = LocalDate.now().getYear();
        int aBaseDatos = clasesql.recogerAnyo();

        System.out.println("\t" + "Actual: " + aActual + " \n BaseDatos: " + aBaseDatos );
        if(aActual!=aBaseDatos || aBaseDatos == 0) {
            if (clasesql.vaciarHistorico())
                clasesql.rellenarHistorico();

        }
        clasesql.updateAnyo(aActual);

        mirarSiMesDiferente();
    }

    @Override
    public void onResume() {
        super.onResume();

        mirarSiAnyoDiferente();

        Locale locale = new Locale ("es", "ES");
        NumberFormat objF = NumberFormat.getInstance (locale);

        totalGanadoDia = clasesql.cogerIngresoDia();
        totalGastadoDia = clasesql.cogerGastoDia();
        totalGanadoMes = clasesql.cogerIngresoMes();
        totalGastadoMes = clasesql.cogerGastoMes();
        temaGastos = clasesql.cogerConcepto();

        binding.textCantidadGastadoHoy.setText(objF.format(totalGastadoDia) + " €");
        binding.textCantidadIngresadoHoy.setText(objF.format(totalGanadoDia) + " €");
        binding.textCantidadGastadoMes.setText(objF.format(totalGastadoMes) + " €");
        binding.textCantidadIngresadoMes.setText(objF.format(totalGanadoMes) + " €");
        binding.textDatos.setText(temaGastos);

        //------------------------

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        String mensual = pref.getString("limiteMensual", "0");
        String diario = pref.getString("limiteDiario", "0");


        try{
            if(totalGastadoMes>=Double.parseDouble(mensual)){
                sendNotificationMes();
            }
            if(totalGastadoDia>=Double.parseDouble(diario)){
                sendNotificationDia();
            }
        }catch (java.lang.NumberFormatException e){
            Toast.makeText(this, "Introduzca un numero en los límites", Toast.LENGTH_SHORT).show();
            System.out.println("ERROR " + e);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.menuDatos:
                Intent cambios = new Intent(getApplicationContext(), cambioDatosActivity.class);
                startActivity(cambios);

                break;

            case R.id.menuManual:
                Toast.makeText(getApplicationContext(), "Work In Progress", Toast.LENGTH_SHORT).show();
                break;

            case R.id.limitesGastos:
                Intent limit = new Intent(getApplicationContext(), preferenciasActivity.class);
                startActivity(limit);
                break;
            case R.id.mnuSalir:
                this.finishAffinity();
                break;
        }



        return super.onOptionsItemSelected(item);

    }


    private void createNotificationChannel(String id, String nombre, String descrip) {
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(id, nombre, importance);
        channel.setDescription(descrip);
        channel.setLightColor(Color.MAGENTA);
        channel.enableVibration(true);
        channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        notificationManager.createNotificationChannel(channel);
    }

    public void sendNotificationDia() {
        String channelID = "com.pasofe.notify";
        int notificationID = 100;
        Notification notification = new Notification.Builder(MainActivity.this, channelID)
                        .setContentTitle("LIMITE DIARIO")
                        .setContentText("Has alcanzado el limite")
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setChannelId(channelID)
                        .setNumber(4)
                        .build();
        notificationManager.notify(notificationID, notification);
    }
    public void sendNotificationMes() {
        String channelID = "com.pasofe.notify";
        int notificationID = 101;
        Notification notification = new Notification.Builder(MainActivity.this, channelID)
                .setContentTitle("LIMITE MENSUAL")
                .setContentText("Has alcanzado el limite")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setChannelId(channelID)
                .setNumber(4)
                .build();
        notificationManager.notify(notificationID, notification);
    }
}