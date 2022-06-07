package com.pasofe.gestinclave.accesoadatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class sqlAplicacion extends SQLiteOpenHelper {

    public sqlAplicacion(Context context) {
        super(context, "bbddclavegestion", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE datosUsuario (" +
                "nombre TEXT PRIMARY KEY," +
                "apellidos TEXT," +
                "numero INTEGER," +
                "correo TEXT);");

        db.execSQL("CREATE TABLE fechas (" +
                //"idLinea INTEGER," +
                "dia INTEGER, " +
                "mes TEXT, " +
                "anyo INTEGER," +
                "PRIMARY KEY (dia, mes,anyo));");

        db.execSQL("CREATE TABLE ingresosMes (" +
                "cantidad DECIMAL(10,5), " +
                "concepto TEXT," +
                "dia INTEGER);");

        db.execSQL("CREATE TABLE gastosMes (" +
                "cantidad DECIMAL(10,5), " +
                "concepto TEXT," +
                "dia INTEGER);");

        db.execSQL("CREATE TABLE historico (" +
                "mes TEXT PRIMARY KEY, " +
                "totalIngreso DECIMAL(10,5), " +
                "totalGasto DECIMAL(10,5));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion==1 && newVersion==2){

            onCreate(db); //Crea las nuevas tablas

            Cursor cursorDatos = db.rawQuery("SELECT nombre, apellidos, numero, correo"+
                    "FROM datosUsuario",null);
            while (cursorDatos.moveToNext()) { //Recorre la tabla antigua
                guardarTodo(cursorDatos.getString(0), cursorDatos.getString(1),
                        cursorDatos.getString(2), cursorDatos.getString(3));
            }
            cursorDatos.close();

            Cursor cursorFechas = db.rawQuery("SELECT dia, mes, anyo FROM fechas", null);
            while (cursorFechas.moveToNext()){
                guardarFechas(cursorFechas.getInt(0), cursorFechas.getString(1), cursorFechas.getInt(2));
            }
            cursorFechas.close();

            Cursor cursorIngresos = db.rawQuery("SELECT cantidad, concepto, dia FROM ingresosMes", null);
            while (cursorIngresos.moveToNext()){
                guardarIngreso(cursorIngresos.getDouble(0), cursorIngresos.getString(1), cursorIngresos.getInt(2));
            }
            cursorIngresos.close();

            Cursor cursorGastos = db.rawQuery("SELECT cantidad, concepto, diaFROM gastosMes", null);
            while (cursorGastos.moveToNext()){
                guardarGasto(cursorGastos.getDouble(0), cursorGastos.getString(1), cursorGastos.getInt(2));
            }
            cursorGastos.close();

            Cursor cursorHistorico = db.rawQuery("SELECT dia, mes, anyo FROM fechas", null);
            while (cursorHistorico.moveToNext()){
                guardarHistorico(cursorHistorico.getString(0), cursorHistorico.getDouble(1), cursorHistorico.getDouble(2));
            }
            cursorHistorico.close();

            db.execSQL("DROP TABLE datosUsuario"); //Eliminar tablas antiguas
            db.execSQL("DROP TABLE fechas");
            db.execSQL("DROP TABLE ingresosMes");
            db.execSQL("DROP TABLE gastosMes");
            db.execSQL("DROP TABLE historico");

        }

    }


    public String recogerNombre(){
        SQLiteDatabase db = getReadableDatabase();
        String nombre = null;

        try{
            Cursor cursor =db.rawQuery("SELECT nombre "
                    + "FROM datosUsuario" , null);

            while (cursor.moveToNext()){
                nombre = cursor.getString(0);
            }

            db.close();
            return  nombre;
        }catch (Exception e){
            System.out.println();
            System.err.println("ERROR" + e);
            System.out.println();
            db.close();
            return null;
        }

    }

    public String recogerApellido() {
        SQLiteDatabase db = getReadableDatabase();
        String apellidos = null;

        try {

            Cursor cursor =db.rawQuery("SELECT apellidos "
                    + "FROM datosUsuario" , null);

            while (cursor.moveToNext()){
                apellidos = cursor.getString(0);
            }

            db.close();
            return  apellidos;

        }catch (Exception e){
            System.out.println();
            System.err.println("ERROR");
            System.out.println();
            db.close();
            return null;
        }

    }

    public String recogerCorreo() {
        SQLiteDatabase db = getReadableDatabase();
        String correo = null;

        try {

            Cursor cursor =db.rawQuery("SELECT correo "
                    + "FROM datosUsuario" , null);

            while (cursor.moveToNext()){
                correo = cursor.getString(0);
            }

            db.close();
            return  correo;

        }catch (Exception e){
            System.out.println();
            System.err.println("ERROR");
            System.out.println();
            db.close();
            return null;
        }

    }

    public int recogerTelefono() {
        SQLiteDatabase db = getReadableDatabase();
        int tlf = 0;

        try {

            Cursor cursor =db.rawQuery("SELECT numero "
                    + "FROM datosUsuario" , null);

            while (cursor.moveToNext()){
                tlf = cursor.getInt(0);
            }

            db.close();
            return  tlf;

        }catch (Exception e){
            System.out.println();
            System.err.println("ERROR");
            System.out.println();
            db.close();
            return 0;
        }

    }

    public int recogerDia() {
        SQLiteDatabase db = getReadableDatabase();
        int dia = 0;

        try{

            Cursor cursor =db.rawQuery("SELECT dia "
                    + "FROM fechas" , null);

            while (cursor.moveToNext()){
                dia = cursor.getInt(0);
            }
            db.close();
            return  dia;
        }catch (Exception e){
            System.out.println();
            System.err.println("ERROR");
            System.out.println();
            db.close();
            return 0;
        }

    }

    public String recogerMes() {
        SQLiteDatabase db = getReadableDatabase();
        String mes = null;

        try{
            Cursor cursor =db.rawQuery("SELECT mes "
                    + "FROM fechas" , null);

            while (cursor.moveToNext()){
                mes = cursor.getString(0);
            }

            db.close();
            return  mes;
        }catch (Exception e){
            System.out.println();
            System.err.println("ERROR");
            System.out.println();
            db.close();
            return null;
        }

    }

    public int recogerAnyo() {
        SQLiteDatabase db = getReadableDatabase();
        int anyo = 0;

        try{
            Cursor cursor =db.rawQuery("SELECT anyo "
                    + "FROM fechas" , null);

            while (cursor.moveToNext()){
                anyo = cursor.getInt(0);
            }
            db.close();
            return  anyo;
        }catch (Exception e){
            System.out.println();
            System.err.println("ERROR");
            System.out.println();
            db.close();
            return 0;
        }

    }

    //------------------------

    public void rellenarHistorico(){
        SQLiteDatabase db = getWritableDatabase();
        String[] meses = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        try{

            for (int i=0;i<meses.length;i++){
                ContentValues values = new ContentValues();
                values.put("mes", meses[i]);
                values.put("totalIngreso", 0);
                values.put("totalGasto", 0);

                db.insert("historico", null, values);
            }


        }catch (SQLiteConstraintException ex){
            System.out.println();
            System.err.println("Insertando sobre una tabla ya creada");
            System.out.println();
            db.close();
        }catch (Exception e){
            System.out.println();
            System.err.println("ERROR " + "\n " + e);
            System.out.println();
            db.close();
        }

    }


    //------------------------
    public void updateNombre(String n) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre", n);

        String oldName = recogerNombre();

        db.update("datosUsuario", values, "nombre=?", new String[]{oldName});

        db.close();
    }

    public void updateApellidos(String a) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("apellidos", a);

        String oldName = recogerNombre();

        db.update("datosUsuario", values, "nombre=?", new String[]{oldName});

        db.close();
    }

    public void updateCorreo(String c) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("correo", c);

        String oldName = recogerNombre();

        db.update("datosUsuario", values, "nombre=?", new String[]{oldName});

        db.close();
    }

    public void updateNumero(int n) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("numero", n);

        String oldName = recogerNombre();

        db.update("datosUsuario", values, "nombre=?", new String[]{oldName});

        db.close();
    }

    //)()()()()()()()()()()()(()()()())()()(

    public void updateAnyo(int n) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("anyo", n);

        db.update("fechas", values, null, null);

        db.close();
    }

    public void updateMes(String m) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("mes", m);

        db.update("fechas", values, null, null);

        db.close();
    }

    public void updateDia(int d) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("dia", d);

        db.update("fechas", values, null, null);

        db.close();
    }

    //--------------------------------------------

    public boolean guardarTodo(String n, String a,String num,  String c) {
        SQLiteDatabase db = getWritableDatabase();
        int numBueno = 111111111;
        try{


            if(n.length()==0){
                return false;
            }
            if(a.length()==0){
                a = "Apellido";
            }
            if(c.length()==0){
                c = "correo@gmail.com";
            }
            if(num.length()==0){
                numBueno = 111111111;
            }else{
                numBueno = Integer.parseInt(num);
            }



            System.out.println();
            System.out.println(" |> " + n + "   |> " + a + "   |> " + c + "   |> " + num  );
            System.out.println();


            ContentValues values = new ContentValues();
            values.put("nombre", n);
            values.put("apellidos", a);
            values.put("numero", numBueno);
            values.put("correo", c);

            db.insert("datosUsuario", null, values);

            db.close();

        }catch (Exception e){
            System.out.println();
            System.err.println("ERROR");
            System.out.println();
            return false;
        }
        return true;
    }

    public boolean guardarIngreso(double cant, String concep, int dia){
        SQLiteDatabase db = getWritableDatabase();
        try{

            ContentValues values = new ContentValues();
            values.put("cantidad", cant);
            values.put("concepto", concep);
            values.put("dia", dia);

            db.insert("ingresosMes", null, values);


            sumatorioMes(String.valueOf(LocalDate.now().getMonth()));
        }catch (Exception e){
            System.out.println();
            System.err.println("ERROR");
            System.out.println();
            return false;
        }
        return true;
    }

    public boolean guardarGasto(double cant, String concep, int dia){
        SQLiteDatabase db = getWritableDatabase();
        try{

            ContentValues values = new ContentValues();
            values.put("cantidad", cant);
            values.put("concepto", concep);
            values.put("dia", dia);

            db.insert("gastosMes", null, values);

        }catch (Exception e){
            System.out.println();
            System.err.println("ERROR");
            System.out.println();
            return false;
        }
        return true;
    }

    public boolean guardarHistorico(String mes, double ingreso, double gasto){
        SQLiteDatabase db = getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("mes", mes);
            values.put("totalIngreso",ingreso );
            values.put("totalGasto",gasto );


            db.insert("historico", null, values);

        }catch (Exception e){
            System.out.println();
            System.err.println("ERROR " + e);
            System.out.println();
            return false;
        }
        return true;
    }

    public boolean guardarFechas(int dia, String mes, int anyo){
        SQLiteDatabase db = getWritableDatabase();
        try{
            System.out.println("guardarFechas con parametros");

            ContentValues values = new ContentValues();
            values.put("dia", dia);
            values.put("mes", mes);
            values.put("anyo",anyo);

            db.insert("fechas", null, values);

        }catch (Exception e){
            System.out.println();
            System.err.println("ERROR " + e);
            System.out.println();
            return false;
        }
        return true;
    }

    public boolean guardarAnyo(){
        SQLiteDatabase db = getWritableDatabase();
        try{
            System.out.println("Guardar Anyo");
            int anyoDB = recogerAnyo();
            if(anyoDB!=0){
                return false;
            }


            ContentValues values = new ContentValues();
            values.put("anyo", LocalDate.now().getYear());

            db.insert("fechas", null, values);

        }catch (Exception e){
            System.out.println();
            System.err.println("ERROR " + e);
            System.out.println();
            return false;
        }
        return true;
    }

    public boolean guardarMes(){
        SQLiteDatabase db = getWritableDatabase();
        try{
            System.out.println("Guardar Mes");

            String mesBD = recogerMes();
            if(mesBD!=null){
                return false;
            }


            ContentValues values = new ContentValues();
            values.put("mes", String.valueOf(LocalDate.now().getMonth()));

            db.insert("fechas", null, values);

        }catch (Exception e){
            System.out.println();
            System.err.println("ERROR " + e);
            System.out.println();
            return false;
        }
        return true;
    }

    public boolean guardarDia(){
        SQLiteDatabase db = getWritableDatabase();
        try{
            System.out.println("Guardar Dia");
            int diaDB = recogerDia();
            if( diaDB!=0){
                return false;
            }

            ContentValues values = new ContentValues();
            values.put("dia",LocalDate.now().getDayOfMonth());

            db.insert("fechas", null, values);

        }catch (Exception e){
            System.out.println();
            System.err.println("ERROR " + e);
            System.out.println();
            return false;
        }
        return true;
    }

    //______________________

    public double cogerIngresoDia() {
        SQLiteDatabase db = getReadableDatabase();
        double ganado = 0;
        int dia = LocalDate.now().getDayOfMonth();

        try{
            Cursor cursor = db.rawQuery("SELECT SUM(cantidad) FROM ingresosMes " +
                    "WHERE dia = " + dia, null );

            while (cursor.moveToNext()){
                ganado = cursor.getDouble(0);
            }

        }catch (Exception e){
            System.out.println();
            System.err.println("ERROR en RecogerIngreso" + e);
            System.out.println();
            return 0;
        }
        return ganado;
    }

    public double cogerGastoDia() {
        SQLiteDatabase db = getReadableDatabase();
        double gastado = 0;
        int dia = LocalDate.now().getDayOfMonth();

        try{
            Cursor cursor = db.rawQuery("SELECT SUM(cantidad) FROM gastosMes " +
                    "WHERE dia = " + dia, null );

            while (cursor.moveToNext()){
                gastado = cursor.getDouble(0);
            }

        }catch (Exception e){
            System.out.println();
            System.err.println("ERROR en RecogerIngreso" + e);
            System.out.println();
            return 0;
        }
        return gastado;
    }

    public double cogerIngresoMes() {
        SQLiteDatabase db = getReadableDatabase();
        double ganado = 0;
        int dia = LocalDate.now().getDayOfMonth();

        try{
            Cursor cursor = db.rawQuery("SELECT SUM(cantidad) FROM ingresosMes ", null );

            while (cursor.moveToNext()){
                ganado = cursor.getDouble(0);
            }

        }catch (Exception e){
            System.out.println();
            System.err.println("ERROR en RecogerIngreso" + e);
            System.out.println();
            return 0;
        }
        return ganado;
    }

    public double cogerGastoMes() {
        SQLiteDatabase db = getReadableDatabase();
        double gastado = 0;
        int dia = LocalDate.now().getDayOfMonth();

        try{
            Cursor cursor = db.rawQuery("SELECT SUM(cantidad) FROM gastosMes ", null );

            while (cursor.moveToNext()){
                gastado = cursor.getDouble(0);
            }

        }catch (Exception e){
            System.out.println();
            System.err.println("ERROR en RecogerIngreso" + e);
            System.out.println();
            return 0;
        }
        return gastado;
    }

    public String cogerConcepto() {
        SQLiteDatabase db = getReadableDatabase();
        String concepto = null;

        try{
            Cursor cursor = db.rawQuery("SELECT concepto, COUNT(concepto) as aux FROM gastosMes " +
                    "GROUP BY concepto " +
                    "ORDER BY aux ASC ",
                    null );

            while (cursor.moveToNext()){
                concepto = cursor.getString(0);
            }

        }catch (Exception e){
            System.out.println();
            System.err.println("ERROR en RecogerIngreso" + e);
            System.out.println();
            return null;
        }
        return concepto;
    }

    //----

    public boolean vaciarHistorico() {
        SQLiteDatabase db = getWritableDatabase();
        String[] meses = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        try{
            db.beginTransaction();
            for (int i=0;i<meses.length;i++){

                db.delete("historico", "mes = " + i, null);
            }
        }catch (Exception e){
            System.out.println();
            System.err.println("ERROR");
            System.out.println();
            return false;

        }
        db.endTransaction();
        return true;
    }

    public void sumatorioMes(String mActual) {
        SQLiteDatabase db = getReadableDatabase();
        double cantidadIng = 0, cantidadGas = 0;
        try{
            Cursor cursor = db.rawQuery("SELECT SUM(cantidad) FROM ingresosMes ", null );

            if(cursor.moveToFirst()){
                do {
                    cantidadIng = cursor.getDouble(0);
                }while (cursor.moveToNext());
            }


            Cursor cursor2 = db.rawQuery("SELECT SUM(cantidad) FROM gastosMes ", null );

            if(cursor2.moveToFirst()){
                do {
                    cantidadGas = cursor2.getDouble(0);
                }while (cursor2.moveToNext());
            }


            cursor.close();
            cursor2.close();

            System.out.println("GASTO : " + cantidadGas + "INGRESO : " + cantidadIng);

            ContentValues values = new ContentValues();

            values.put("totalIngreso", cantidadIng);
            values.put("totalGasto", cantidadGas);

            db.update("historico", values, "mes = ?", new String[]{mActual});


        }catch (Exception e){
            System.out.println();
            System.err.println("ERROR " + e);
            System.out.println();
        }

    }

    //_________

    public double[] cogerIngresoTodosMeses(){
        SQLiteDatabase db = getReadableDatabase();
        double[] arr = new double[12];

        String[] meses = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};

        try{

            for(int x =0; x<meses.length;x++){
                Cursor cursor = db.rawQuery("SELECT totalIngreso FROM historico " +
                        "WHERE mes = " + String.valueOf(meses[x]),null);


                while(cursor.moveToNext()){
                    arr[x] = cursor.getDouble(0);

                }


                cursor.close();
            }




        }catch (Exception e){
            System.out.println();
            System.err.println("ERROR en RecogerIngreso" + e);
            System.out.println();
            return arr;
        }
        return arr;
    }
    public double[] cogerGastoTodosMeses(){
        SQLiteDatabase db = getReadableDatabase();
        double[] arr = new double[12];

        String[] meses = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};

        try{

                Cursor cursor = db.rawQuery("SELECT totalGasto FROM historico",null);

                int i = 0;
                while(cursor.moveToNext()){
                    arr[i] = cursor.getDouble(0);
                    i++;
                }

                cursor.close();



        }catch (Exception e){
            System.out.println();
            System.err.println("ERROR en RecogerGasto " + e);
            System.out.println();
            return arr;
        }
        return arr;
    }
}

