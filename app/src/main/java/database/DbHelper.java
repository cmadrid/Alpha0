package database;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import salonmachala.org.salonmachala.Global;
import salonmachala.org.salonmachala.SalonMachala;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "data";
    private static final int DB_SCHEME_VERSION = 4;

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

       /* if (oldVersion<=1){
            db.execSQL("ALTER TABLE "+DBParticipante.NOMBRE_TABLA+" ADD COLUMN "+DBParticipante.TIPO+" text default 'PA';");
        }*/
        if(oldVersion<=3){
            db.execSQL("DROP TABLE IF EXISTS " + DBParticipante.NOMBRE_TABLA);
            db.execSQL("DROP TABLE IF EXISTS " + DBInformacion.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DBObra.TABLE_NAME);
            onCreate(db);

           /* db.execSQL("ALTER TABLE "+DBParticipante.NOMBRE_TABLA+" ADD COLUMN "+DBParticipante.NACIONALIDAD_EN+" text not null default '';");
            db.execSQL("ALTER TABLE "+DBParticipante.NOMBRE_TABLA+" ADD COLUMN "+DBParticipante.BIO_EN+" text;");

            db.execSQL("ALTER TABLE "+DBObra.TABLE_NAME+" ADD COLUMN "+DBObra.TECNICA_EN+" text not null default '';");
            db.execSQL("ALTER TABLE "+DBObra.TABLE_NAME+" ADD COLUMN "+DBObra.DESCRIPCION_EN+" text not null default '';");
            db.execSQL("ALTER TABLE "+DBObra.TABLE_NAME+" ADD COLUMN "+DBObra.PREMIO+" text;");

            db.execSQL("ALTER TABLE "+DBInformacion.TABLE_NAME+" ADD COLUMN "+DBInformacion.TITULO_EN+" text NOT NULL DEFAULT ''");
            db.execSQL("ALTER TABLE "+DBInformacion.TABLE_NAME+" ADD COLUMN "+DBInformacion.CONTENIDO1_EN+" text NOT NULL DEFAULT '';");
            db.execSQL("ALTER TABLE "+DBInformacion.TABLE_NAME+" ADD COLUMN "+DBInformacion.CONTENIDO2_EN+" text;");
            db.execSQL("ALTER TABLE "+DBInformacion.TABLE_NAME+" ADD COLUMN "+DBInformacion.CONTENIDO3_EN+" text;");
            db.execSQL("ALTER TABLE "+DBInformacion.TABLE_NAME+" ADD COLUMN "+DBInformacion.CONTENIDO4_EN+" text;");
            db.execSQL("ALTER TABLE "+DBInformacion.TABLE_NAME+" ADD COLUMN "+DBInformacion.CONTENIDO5_EN+" text;");*/
        }
    }
}
