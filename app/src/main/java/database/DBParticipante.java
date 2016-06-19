package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import gson.LoadInformation;
import gson.Participante;


public class DBParticipante {

    public static final String NOMBRE_TABLA = "participante";
    public static final String ID = "_id"+"_"+ NOMBRE_TABLA;
    public static final String NOMBRE = "nombre"+"_"+ NOMBRE_TABLA;
    public static final String PSEUDONIMO = "pseudonimo"+"_"+ NOMBRE_TABLA;
    public static final String NACIMIENTO = "fecha_nacimiento"+"_"+ NOMBRE_TABLA;
    public static final String NACIONALIDAD = "nacionalidad"+"_"+ NOMBRE_TABLA;
    public static final String BIBLIO = "biblio"+"_"+ NOMBRE_TABLA;
    public static final String FOTO_URL = "foto_url"+"_"+NOMBRE_TABLA;
    public static final String FOTO = "foto"+"_"+"participante";
    public static final String ACTUALIZACION = "actualizacion"+"_"+ NOMBRE_TABLA;
    public static final String ACTUALIZACION_FOTO = "actualizacion_foto"+"_"+ NOMBRE_TABLA;
    public static final String TIPO = "tipo"+"_"+ NOMBRE_TABLA;
    public Context ctx;


    private DbHelper helper;
    private SQLiteDatabase db;

    public static final String CREATE_TABLE = "create table "+ NOMBRE_TABLA +" ("
            + ID + " integer primary key,"
            + NOMBRE + " text not null,"
            + PSEUDONIMO + " text,"
            + NACIMIENTO + " timestamp not null,"
            + NACIONALIDAD + " text not null,"
            + BIBLIO + " text,"
            + FOTO_URL + " text,"
            + FOTO + " blob,"
            + TIPO + " text,"
            + ACTUALIZACION + " TIMESTAMP,"
            + ACTUALIZACION_FOTO + " TIMESTAMP"
            +");";

    public DBParticipante(Context contexto) {
        this.ctx=contexto;
        helper = new DbHelper(contexto);
        db = helper.getWritableDatabase();
    }

    public ContentValues generarContentValues(Integer id,String nombre,String pseudonimo,Date nacimiento,String nacionalidad,String biblio,String foto_url,byte[] foto,Date actualizacion,Date actualizacion_foto,String tipo){
        ContentValues valores = new ContentValues();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

        if(id!=null)
            valores.put(ID,id);
        if(nombre!=null)
            valores.put(NOMBRE,nombre);
        if(pseudonimo!=null)
            valores.put(PSEUDONIMO,pseudonimo);
        if(nacimiento!=null)
            valores.put(NACIMIENTO, dateFormat.format(nacimiento));
        if(nacionalidad!=null)
            valores.put(NACIONALIDAD,nacionalidad);
        if(biblio!=null)
            valores.put(BIBLIO,biblio);
        if(foto_url!=null)
            valores.put(FOTO_URL, foto_url);
        if(foto!=null)
            valores.put(FOTO, foto);
        if(tipo!=null)
            valores.put(TIPO, tipo);
        if(actualizacion!=null)
            valores.put(ACTUALIZACION,dateFormat.format(actualizacion));
        if(actualizacion_foto!=null)
            valores.put(ACTUALIZACION_FOTO,dateFormat.format(actualizacion_foto));
        return valores;
    }
/*
    public void insertar(Integer id,String nombre,String pseudonimo,Date nacimiento,String nacionalidad,String biblio,String foto_url,Bitmap foto,Date actualizacion,Date actualizacion_foto)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        foto.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] image = stream.toByteArray();
        db.insert(NOMBRE_TABLA, null, generarContentValues(id, nombre, pseudonimo, nacimiento, nacionalidad,foto_url, biblio, image, actualizacion, actualizacion_foto));
    }

    public void insertar(Participante participante)
    {
        insertar(participante.getIdp(), participante.getNop(), participante.getPsp(), participante.getFnp(), participante.getNap(), participante.getBcp(),participante.getFop(), participante.getBitmap(), participante.getAcp(), participante.getAfp());
    }

    public void insertar(ArrayList<Participante> participantes)
    {
        for (Participante participante:participantes)
            insertar(participante);
    }
*/

    public boolean insertaroActualizar(Integer id,String nombre,String pseudonimo,Date nacimiento,String nacionalidad,String biblio,String foto,Date actualizacion,Date actualizacion_foto,String tipo)
    {


        byte[] image = null;

        Cursor c = consultar(id);
        if(c.moveToFirst())
        {

            if(!actualizacion.after(Timestamp.valueOf(c.getString(7))))
                return false;
            //

            if(actualizacion_foto.after(Timestamp.valueOf(c.getString(8)))) {
                //image = LoadInformation.getImage(foto);
            }

            String[] args = new String[] {id+""};
            db.update(NOMBRE_TABLA, generarContentValues(id, nombre, pseudonimo, nacimiento,nacionalidad,biblio,foto,image,actualizacion,actualizacion_foto,tipo), ID + "=?", args);
            return true;
        }else {
            //image = LoadInformation.getImage(foto);

            db.insert(NOMBRE_TABLA, null, generarContentValues(id, nombre, pseudonimo, nacimiento, nacionalidad, biblio,foto, image, actualizacion, actualizacion_foto,tipo));
        }
        return false;
    }
/*
    public boolean insertaroActualizar(Participante participante)
    {
        return insertaroActualizar(participante.getIdp(), participante.getNop(), participante.getPsp(), participante.getFnp(), participante.getNap(), participante.getBcp(), participante.getFop(), participante.getAcp(), participante.getAfp());
    }
*/
    public void insertaroActualizar(ArrayList<Participante> participantes)
    {
        for(Participante participante:participantes)
            insertaroActualizar(participante.getIdp(), participante.getNop(), participante.getPsp(), participante.getFnp(), participante.getNap(), participante.getBcp(), participante.getFop(), participante.getAcp(), participante.getAfp(),participante.getTip());
    }



    public Cursor consultar(Integer id){

        String[] campos = new String[] {ID,NOMBRE,PSEUDONIMO,NACIMIENTO,NACIONALIDAD,BIBLIO,FOTO,ACTUALIZACION,ACTUALIZACION_FOTO,FOTO_URL};
        //Cursor c = db.query(NOMBRE_TABLA, campos, "usuario=?(where)", args(para el where), group by, having, order by, num);

        String[] args = new String[] {id+""};

        if(id==null)return db.query(NOMBRE_TABLA, campos, null, null, null, null,null);
        return db.query(NOMBRE_TABLA, campos, ID+"=?", args, null, null, null);
    }


    public Cursor consultarArtistas(Integer id,String tipo){

        String[] campos = new String[] {ID,NOMBRE,PSEUDONIMO,FOTO,FOTO_URL};
        //Cursor c = db.query(NOMBRE_TABLA, campos, "usuario=?(where)", args(para el where), group by, having, order by, num);

        String[] args = new String[] {id+"",tipo};

        if(id==null)return db.query(NOMBRE_TABLA, campos, TIPO+"=?", new String[] {tipo}, null, null,null);
        return db.query(NOMBRE_TABLA, campos, ID+"=? and "+TIPO+"=?", args, null, null, null);
    }

    public int borrar(String ids){
        return db.delete(NOMBRE_TABLA,ID+" not in ("+ids+")", null);
    }

    public void vaciar(){
        db.delete(NOMBRE_TABLA,null,null);
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
