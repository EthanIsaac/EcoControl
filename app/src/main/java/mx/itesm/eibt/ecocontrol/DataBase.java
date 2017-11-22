package mx.itesm.eibt.ecocontrol;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ethan on 12/10/2017.
 */

public class DataBase extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ecoControl.db";

    public static final String TABLA_USUARIO = "usuario";
    public static final String COLUMNA_NOMBRE_USUARIO = "nombre";
    public static final String COLUMNA_CONTRASEÑA_USUARIO = "contraseña";

    public static final String TABLA_US_FAB = "us_fab";
    public static final String COLUMNA_NOMBRE_US_FAB = "nombre";
    public static final String COLUMNA_ID_FABRICA_US_FAB = "id_fabrica";

    public static final String TABLA_FABRICA = "fabrica";
    public static final String COLUMNA_ID_FABRICA = "id_fabrica";
    public static final String COLUMNA_NOMBRE_FABRICA = "nombre";
    public static final String COLUMNA_DESC_LIMITE_FABRICA = "desc_limite";
    public static final String COLUMNA_LOGO_FABRICA = "logo";

    public static final String TABLA_REGISTRO = "registro";
    public static final String COLUMNA_FECHA_HORA_REGISTRO = "fecha_hora";
    public static final String COLUMNA_ID_FABRICA_REGISTRO = "id_fabrica";
    public static final String COLUMNA_PRESION_AIRE_REGISTRO = "presion_aire";
    public static final String COLUMNA_PPM_REGISTRO = "ppm";

    private static final String SQL_CREAR_TABLA_USUARIO = "CREATE TABLE "
            + TABLA_USUARIO + "(" + COLUMNA_NOMBRE_USUARIO
            + " text PRIMARY KEY, " + COLUMNA_CONTRASEÑA_USUARIO
            + " text NOT NULL);";

    private static final String SQL_CREAR_TABLA_US_FAB= "CREATE TABLE "
            + TABLA_US_FAB + "(" + COLUMNA_ID_FABRICA_US_FAB
            + " integer PRIMARY KEY AUTOINCREMENT, " + COLUMNA_NOMBRE_US_FAB
            + " text NOT NULL,"
            + " FOREIGN KEY (" + COLUMNA_ID_FABRICA_US_FAB + ") REFERENCES " + TABLA_FABRICA + "("
            + COLUMNA_ID_FABRICA + ") ON DELETE CASCADE ON UPDATE CASCADE,"
            + " FOREIGN KEY (" + COLUMNA_NOMBRE_US_FAB + ") REFERENCES " + TABLA_USUARIO + "("
            + COLUMNA_NOMBRE_USUARIO + ") ON DELETE CASCADE ON UPDATE CASCADE);";

    private static final String SQL_CREAR_TABLA_FABRICA = "CREATE TABLE "
            + TABLA_FABRICA + "(" + COLUMNA_ID_FABRICA
            + " integer PRIMARY KEY AUTOINCREMENT, " + COLUMNA_NOMBRE_FABRICA
            + " text NOT NULL, " + COLUMNA_DESC_LIMITE_FABRICA
            + " text, " + COLUMNA_LOGO_FABRICA
            + " text NOT NULL);";

    private static final String SQL_CREAR_TABLA_REGISTRO= "CREATE TABLE "
            + TABLA_REGISTRO + "(" + COLUMNA_FECHA_HORA_REGISTRO
            + " datetime NOT NULL, " + COLUMNA_ID_FABRICA_REGISTRO
            + " integer NOT NULL, " + COLUMNA_PRESION_AIRE_REGISTRO
            + " integer NOT NULL, " + COLUMNA_PPM_REGISTRO
            + " integer NOT NULL, PRIMARY KEY(" + COLUMNA_FECHA_HORA_REGISTRO + "," + COLUMNA_ID_FABRICA_REGISTRO +"),"
            + " FOREIGN KEY (" + COLUMNA_ID_FABRICA_REGISTRO + ") REFERENCES " + TABLA_FABRICA + "("
            + COLUMNA_ID_FABRICA + ") ON DELETE CASCADE ON UPDATE CASCADE);";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**public boolean deleteAllFromVacacion()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.delete(TABLA_USUARIO,
                    null,
                    null);
            db.close();
            return true;

        }catch (Exception e){
            return false;
        }
    }

    public boolean deleteFromVacacion(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.delete(TABLA_USUARIO,
                    " id = ?",
                    new String[] {String.valueOf(id)});
            db.close();
            return true;

        }catch (Exception e){
            return false;
        }
    }

    public boolean deleteFromEvent(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.delete(TABLE_EVENT,
                    " id = ?",
                    new String[] {String.valueOf(id)});
            db.close();
            return true;

        }catch (Exception e){
            return false;
        }
    }

    public void updateVacation(int id, String place, String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMNA_TIPO_USUARIO, place);
        values.put(COLUMN_VACATION_DATE, date);

        int i = db.update(TABLA_USUARIO,
                values,
                " id = ?",
                new String[] {String.valueOf(id)});
        db.close();
    }

    public void updateEvent(int id, String name, int cost, String description, String link)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_NAME, name);
        values.put(COLUMN_EVENT_COST, cost);
        values.put(COLUMN_EVENT_DESCRIPTION, description);
        values.put(COLUMN_EVENT_LINK, link);

        int i = db.update(TABLE_EVENT,
                values,
                " id = ?",
                new String[] {String.valueOf(id)});
        db.close();
    }**/

    public void insertarEnUsuario(String usuario, String contraseña)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMNA_NOMBRE_USUARIO, usuario);
        values.put(COLUMNA_CONTRASEÑA_USUARIO, contraseña);
        db.insert(TABLA_USUARIO, null, values);
        db.close();
    }

    public void insertarEnUs_Fab(String usuario, int id_fabrica)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMNA_NOMBRE_US_FAB, usuario);
        values.put(COLUMNA_ID_FABRICA_US_FAB, id_fabrica);
        db.insert(TABLA_US_FAB, null, values);
        db.close();
    }

    public void insertarEnFabrica(String nombre, String desc_limite, String logo)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMNA_NOMBRE_FABRICA, nombre);
        values.put(COLUMNA_DESC_LIMITE_FABRICA, desc_limite);
        values.put(COLUMNA_LOGO_FABRICA, logo);
        db.insert(TABLA_FABRICA, null, values);
        db.close();
    }

    public void insertarEnRegistro(int id_fabrica, int presion_aire, int ppm)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMNA_ID_FABRICA_REGISTRO, id_fabrica);
        values.put(COLUMNA_PRESION_AIRE_REGISTRO, presion_aire);
        values.put(COLUMNA_PPM_REGISTRO, ppm);
        db.insert(TABLA_REGISTRO, null, values);
        db.close();
    }

    public Cursor buscarUsuario(String nombre_usuario)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "SELECT nombre, contraseña FROM usuario where nombre = '" + nombre_usuario + "'";
        Cursor cursor = db.rawQuery(sqlQuery,null);
        if(cursor.moveToFirst())
        {
            db.close();
            return cursor;
        }
        db.close();
        return null;
    }

    public Cursor buscarFabricas(String nombre_usuario)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String sqlQuery = "SELECT f.id_fabrica, f.nombre, f.desc_limite, f.logo FROM us_fab uf, fabrica f WHERE uf.nombre = '" + nombre_usuario + "' AND uf.id_fabrica = f.id_fabrica";
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if(cursor.moveToFirst())
        {
            db.close();
            return cursor;
        }
        db.close();
        return null;
    }

    public Cursor buscarRegistros(int id_fabrica)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMNA_NOMBRE_USUARIO, COLUMNA_CONTRASEÑA_USUARIO};

        String sqlQuery = "SELECT fecha_hora, id_fabrica, presion_aire, ppm FROM registro WHERE id_fabrica = " + id_fabrica;
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if(cursor != null)
            cursor.moveToFirst();
        db.close();
        return cursor;
    }

    /**public Cursor selectAllFromVacation()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMNA_NOMBRE_USUARIO, COLUMNA_TIPO_USUARIO, COLUMN_VACATION_DATE};

        Cursor cursor = db.query(TABLA_USUARIO,
                projection,
                null,
                null,
                null,
                null,
                null,
                null);
        db.close();
        return cursor;
    }

    public Cursor selectFromEvent(int id_vacation)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_EVENT_ID, COLUMN_EVENT_ID_VACATION, COLUMN_EVENT_NAME, COLUMN_EVENT_COST, COLUMN_EVENT_DESCRIPTION, COLUMN_EVENT_LINK};

        Cursor cursor = db.query(TABLE_EVENT,
                projection,
                " id = ?",
                new String[]{String.valueOf(id_vacation)},
                null,
                null,
                null,
                null);
        if(cursor != null)
            cursor.moveToFirst();
        db.close();
        return cursor;
    }**/

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREAR_TABLA_USUARIO);
        sqLiteDatabase.execSQL(SQL_CREAR_TABLA_FABRICA);
        sqLiteDatabase.execSQL(SQL_CREAR_TABLA_REGISTRO);
        sqLiteDatabase.execSQL(SQL_CREAR_TABLA_US_FAB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
