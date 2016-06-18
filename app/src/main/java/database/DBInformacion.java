package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import gson.Informacion;
import gson.Obra;


public class DBInformacion {




    public static final String TABLE_NAME = "informacion";
    public static final String ID = "_id"+"_"+TABLE_NAME;
    public static final String CODIGO = "codigo"+"_"+TABLE_NAME;
    public static final String TITULO = "titulo"+"_"+TABLE_NAME;
    public static final String FOTO_URL1 = "foto_url1"+"_"+TABLE_NAME;
    public static final String FOTO1 = "foto1"+"_"+TABLE_NAME;
    public static final String CONTENIDO1 = "contenido1"+"_"+TABLE_NAME;
    public static final String FOTO_URL2 = "foto_url2"+"_"+TABLE_NAME;
    public static final String FOTO2 = "foto2"+"_"+TABLE_NAME;
    public static final String CONTENIDO2 = "contenido2"+"_"+TABLE_NAME;
    public static final String FOTO_URL3 = "foto_url3"+"_"+TABLE_NAME;
    public static final String FOTO3 = "foto3"+"_"+TABLE_NAME;
    public static final String CONTENIDO3 = "contenido3"+"_"+TABLE_NAME;
    public static final String FOTO_URL4 = "foto_url4"+"_"+TABLE_NAME;
    public static final String FOTO4 = "foto4"+"_"+TABLE_NAME;
    public static final String CONTENIDO4 = "contenido4"+"_"+TABLE_NAME;
    public static final String FOTO_URL5 = "foto_url5"+"_"+TABLE_NAME;
    public static final String FOTO5 = "foto5"+"_"+TABLE_NAME;
    public static final String CONTENIDO5 = "contenido5"+"_"+TABLE_NAME;
    public static final String ACTUALIZACION = "actualizacion"+"_"+TABLE_NAME;
    public static final String ACTUALIZACION_FOTO1 = "actualizacion_foto1"+"_"+TABLE_NAME;
    public static final String ACTUALIZACION_FOTO2 = "actualizacion_foto2"+"_"+TABLE_NAME;
    public static final String ACTUALIZACION_FOTO3 = "actualizacion_foto3"+"_"+TABLE_NAME;
    public static final String ACTUALIZACION_FOTO4 = "actualizacion_foto4"+"_"+TABLE_NAME;
    public static final String ACTUALIZACION_FOTO5 = "actualizacion_foto5"+"_"+TABLE_NAME;
    public Context ctx;


    private DbHelper helper;
    private SQLiteDatabase db;

    public static final String CREATE_TABLE = "create table "+ TABLE_NAME +" ("
            + ID + " integer primary key,"
            + CODIGO + " text not null,"
            + TITULO + " text not null,"
            + FOTO_URL1 + " text,"
            + FOTO1 + " blob,"
            + CONTENIDO1 + " text not null,"
            + FOTO_URL2 + " text,"
            + FOTO2 + " blob,"
            + CONTENIDO2 + " text,"
            + FOTO_URL3 + " text,"
            + FOTO3 + " blob,"
            + CONTENIDO3 + " text,"
            + FOTO_URL4 + " text,"
            + FOTO4 + " blob,"
            + CONTENIDO4 + " text,"
            + FOTO_URL5 + " text,"
            + FOTO5 + " blob,"
            + CONTENIDO5 + " text,"
            + ACTUALIZACION + " timestamp,"
            + ACTUALIZACION_FOTO1 + " timestamp,"
            + ACTUALIZACION_FOTO2 + " timestamp,"
            + ACTUALIZACION_FOTO3 + " timestamp,"
            + ACTUALIZACION_FOTO4 + " timestamp,"
            + ACTUALIZACION_FOTO5 + " timestamp" +
            ");";

    public DBInformacion(Context contexto) {
        this.ctx=contexto;
        helper = new DbHelper(contexto);
        db = helper.getWritableDatabase();
    }

    public ContentValues generarContentValues(Integer id,String codigo, String titulo,
                                              String foto_url1,byte[] foto1,String contenido1,
                                              String foto_url2,byte[] foto2,String contenido2,
                                              String foto_url3,byte[] foto3,String contenido3,
                                              String foto_url4,byte[] foto4,String contenido4,
                                              String foto_url5,byte[] foto5,String contenido5,
                                              Date actualizacion,Date actualizacion_foto1,Date actualizacion_foto2,Date actualizacion_foto3,Date actualizacion_foto4,Date actualizacion_foto5){
        ContentValues valores = new ContentValues();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

        if(id!=null)
            valores.put(ID,id);
        if(codigo!=null)
            valores.put(CODIGO,codigo);
        if(titulo!=null)
            valores.put(TITULO,titulo);
        if(foto_url1!=null)
            valores.put(FOTO_URL1,foto_url1);
        if(foto1!=null)
            valores.put(FOTO1, foto1);
        if(contenido1!=null)
            valores.put(CONTENIDO1,contenido1);
        if(foto_url2!=null)
            valores.put(FOTO_URL2,foto_url2);
        if(foto2!=null)
            valores.put(FOTO2, foto2);
        if(contenido2!=null)
            valores.put(CONTENIDO2,contenido2);
        if(foto_url3!=null)
            valores.put(FOTO_URL3,foto_url3);
        if(foto3!=null)
            valores.put(FOTO3, foto3);
        if(contenido3!=null)
            valores.put(CONTENIDO3,contenido3);
        if(foto_url4!=null)
            valores.put(FOTO_URL4,foto_url4);
        if(foto4!=null)
            valores.put(FOTO4, foto4);
        if(contenido4!=null)
            valores.put(CONTENIDO4,contenido4);
        if(foto_url5!=null)
            valores.put(FOTO_URL5,foto_url5);
        if(foto5!=null)
            valores.put(FOTO5, foto5);
        if(contenido5!=null)
            valores.put(CONTENIDO5,contenido5);

        if(actualizacion!=null)
            valores.put(ACTUALIZACION, dateFormat.format(actualizacion));
        if(actualizacion_foto1!=null)
            valores.put(ACTUALIZACION_FOTO1, dateFormat.format(actualizacion_foto1));
        if(actualizacion_foto2!=null)
            valores.put(ACTUALIZACION_FOTO2, dateFormat.format(actualizacion_foto2));
        if(actualizacion_foto3!=null)
            valores.put(ACTUALIZACION_FOTO3, dateFormat.format(actualizacion_foto3));
        if(actualizacion_foto4!=null)
            valores.put(ACTUALIZACION_FOTO4, dateFormat.format(actualizacion_foto4));
        if(actualizacion_foto5!=null)
            valores.put(ACTUALIZACION_FOTO5, dateFormat.format(actualizacion_foto5));
        return valores;
    }
    public boolean insertaroActualizar(Integer id, String codigo, String titulo,
                                       String foto_url1,String contenido1,
                                       String foto_url2,String contenido2,
                                       String foto_url3,String contenido3,
                                       String foto_url4,String contenido4,
                                       String foto_url5,String contenido5,
                                       Date actualizacion,Date actualizacion_foto1,Date actualizacion_foto2,Date actualizacion_foto3,Date actualizacion_foto4,Date actualizacion_foto5)
    {
        Cursor c = consultar(id);
        if(c.moveToFirst())
        {

            if(!actualizacion.after(Timestamp.valueOf(c.getString(18))))
                return false;

            String[] args = new String[] {id+""};
            db.update(TABLE_NAME, generarContentValues(id, codigo, titulo, foto_url1, null, contenido1, foto_url2, null, contenido2, foto_url3, null, contenido3, foto_url4, null, contenido4, foto_url5, null, contenido5,
                    actualizacion, actualizacion_foto1, actualizacion_foto2, actualizacion_foto3, actualizacion_foto4, actualizacion_foto5), ID + "=?", args);
            return true;
        }else {
            //image = LoadInformation.getImage(foto);

            db.insert(TABLE_NAME, null, generarContentValues(id, codigo, titulo, foto_url1, null, contenido1, foto_url2, null, contenido2, foto_url3, null, contenido3, foto_url4, null, contenido4, foto_url5, null, contenido5,
                    actualizacion, actualizacion_foto1, actualizacion_foto2, actualizacion_foto3, actualizacion_foto4, actualizacion_foto5));
        }
        return false;
    }

    public void insertaroActualizar(ArrayList<Informacion> informaciones)
    {
        for(Informacion inf:informaciones)
            insertaroActualizar(inf.getIdi(), inf.getCoi(), inf.getTii(), inf.getF1i(), inf.getC1i(), inf.getF2i(), inf.getC2i(), inf.getF3i(), inf.getC3i(), inf.getF4i(), inf.getC4i(), inf.getF5i(), inf.getC5i(), inf.getAci(),
                    inf.getAf1(), inf.getAf2(), inf.getAf3(), inf.getAf4(), inf.getAf5());
    }


    public Cursor consultar(Integer id){


        String[] campos = new String[] {ID,CODIGO,TITULO,FOTO_URL1,FOTO1,CONTENIDO1,FOTO_URL2,FOTO2,CONTENIDO2,FOTO_URL3,FOTO3,CONTENIDO3,FOTO_URL4,FOTO4,CONTENIDO4,FOTO_URL5,FOTO5,CONTENIDO5,
                ACTUALIZACION,ACTUALIZACION_FOTO1,ACTUALIZACION_FOTO2,ACTUALIZACION_FOTO3,ACTUALIZACION_FOTO4,ACTUALIZACION_FOTO5};

        String[] args = new String[] {id+""};

        if(id==null)return db.query(TABLE_NAME, campos, null, null, null, null,null);
        return db.query(TABLE_NAME, campos, ID+"=?", args, null, null, null);
    }


    public Cursor consultar(String cod){


        String[] campos = new String[] {ID,CODIGO,TITULO,FOTO_URL1,FOTO1,CONTENIDO1,FOTO_URL2,FOTO2,CONTENIDO2,FOTO_URL3,FOTO3,CONTENIDO3,FOTO_URL4,FOTO4,CONTENIDO4,FOTO_URL5,FOTO5,CONTENIDO5,
                ACTUALIZACION,ACTUALIZACION_FOTO1,ACTUALIZACION_FOTO2,ACTUALIZACION_FOTO3,ACTUALIZACION_FOTO4,ACTUALIZACION_FOTO5};

        String[] args = new String[] {cod+""};

        if(cod==null)return db.query(TABLE_NAME, campos, null, null, null, null,null);
        return db.query(TABLE_NAME, campos, CODIGO+"=?", args, null, null, null);
    }


    public int borrar(String ids){
        return db.delete(TABLE_NAME,ID+" not in ("+ids+")",null);
    }


    public void vaciar(){
        db.delete(TABLE_NAME,null,null);
    }

    public void close(){
        try {
            if(helper!=null){
                helper.close();
                helper=null;
            }

            if(db!=null){
                db.close();
                db=null;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
