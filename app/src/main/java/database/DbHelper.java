package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "data";
    private static final int DB_SCHEME_VERSION = 2;

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

        if (oldVersion==1 && newVersion>1) {
            db.execSQL("ALTER TABLE "+DBParticipante.NOMBRE_TABLA+" ADD COLUMN "+DBParticipante.TIPO+" text default 'PA'");
        }
    }
}
