package database;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import salonmachala.org.salonmachala.Global;
import salonmachala.org.salonmachala.SalonMachala;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "data";
    private static final int DB_SCHEME_VERSION = 5;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_SCHEME_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBInformacion.CREATE_TABLE);
        db.execSQL(DBObra.CREATE_TABLE);
        db.execSQL(DBParticipante.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        SharedPreferences sharedpreferences = Global.activity.getSharedPreferences(SalonMachala.Pref.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove(SalonMachala.Pref.Ejecutado).apply();
        editor.remove(SalonMachala.Pref.Actualizacion_Informacion).apply();
        editor.remove(SalonMachala.Pref.Actualizacion_Participantes).apply();
        editor.remove(SalonMachala.Pref.Actualizacion_Obras).apply();

        if(newVersion<oldVersion){
            db.execSQL("DROP TABLE IF EXISTS " + DBParticipante.NOMBRE_TABLA);
            db.execSQL("DROP TABLE IF EXISTS " + DBInformacion.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DBObra.TABLE_NAME);
            onCreate(db);
        }

        if(oldVersion<=4){
            db.execSQL("DROP TABLE IF EXISTS " + DBParticipante.NOMBRE_TABLA);
            db.execSQL("DROP TABLE IF EXISTS " + DBInformacion.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DBObra.TABLE_NAME);
            onCreate(db);
        }
    }
}
