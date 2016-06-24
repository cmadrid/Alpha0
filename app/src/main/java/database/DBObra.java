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
import gson.Obra;
import gson.Participante;


public class DBObra {



    public static final  String TABLA_FK =DBParticipante.NOMBRE_TABLA;
    public static final String FK_ID = DBParticipante.ID;

    public static final String TABLE_NAME = "obra";
    public static final String ID = "_id"+"_"+TABLE_NAME;
    public static final String TITULO = "titulo"+"_"+TABLE_NAME;
    public static final String DESCRIPCION = "descripcion"+"_"+TABLE_NAME;
    public static final String DESCRIPCION_EN = "descripcion_en"+"_"+TABLE_NAME;
    public static final String FECHA = "fecha"+"_"+TABLE_NAME;
    public static final String TECNICA = "tecnica"+"_"+TABLE_NAME;
    public static final String TECNICA_EN = "tecnica_en"+"_"+TABLE_NAME;
    public static final String FOTO_URL = "foto_url"+"_"+TABLE_NAME;
    public static final String FOTO = "foto"+"_"+TABLE_NAME;
    public static final String PARTICIPANTE = "id"+"_"+ TABLA_FK;
    public static final String QR = "qr_text"+"_"+TABLE_NAME;
    public static final String DIMENSIONES = "dimensiones"+"_"+TABLE_NAME;
    public static final String PREMIO = "premio"+"_"+TABLE_NAME;
    public static final String ACTUALIZACION = "actualizacion"+"_"+TABLE_NAME;
    public static final String ACTUALIZACION_FOTO = "actualizacion_foto"+"_"+TABLE_NAME;
    public Context ctx;


    private DbHelper helper;
    private SQLiteDatabase db;

    public static final String CREATE_TABLE = "create table "+ TABLE_NAME +" ("
            + ID + " integer primary key,"
            + TITULO + " text not null,"
            + DESCRIPCION + " text not null,"
            + DESCRIPCION_EN + " text not null,"
            + FECHA + " text not null,"
            + TECNICA + " text not null,"
            + TECNICA_EN + " text not null,"
            + FOTO_URL + " text,"
            + FOTO + " blob,"
            + PARTICIPANTE + " integer not null,"
            + QR + " text,"
            + DIMENSIONES + " text,"
            + PREMIO + " text,"
            + ACTUALIZACION + " timestamp,"
            + ACTUALIZACION_FOTO + " timestamp,"
            + " FOREIGN KEY("+PARTICIPANTE+") REFERENCES "+TABLA_FK+"("+FK_ID+"));";

    public DBObra(Context contexto) {
        this.ctx=contexto;
        helper = new DbHelper(contexto);
        db = helper.getWritableDatabase();
    }

    public ContentValues generarContentValues(Integer id,String titulo,String descripcion,String descripcion_en,String fecha,String tecnica,String tecnica_en,String foto_url,byte[] foto,Integer participante,String qr,String dimensiones,String premio,Date actualizacion,Date actualizacion_foto){
        ContentValues valores = new ContentValues();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

        if(id!=null)
            valores.put(ID,id);
        if(titulo!=null)
            valores.put(TITULO,titulo);
        if(descripcion!=null)
            valores.put(DESCRIPCION,descripcion);
        if(descripcion_en!=null)
            valores.put(DESCRIPCION_EN,descripcion_en);
        if(fecha!=null)
            valores.put(FECHA, fecha);
        if(tecnica!=null)
            valores.put(TECNICA,tecnica);
        if(tecnica_en!=null)
            valores.put(TECNICA_EN,tecnica_en);
        if(foto_url!=null)
            valores.put(FOTO_URL,foto_url);
        if(foto!=null)
            valores.put(FOTO,foto);
        if(participante!=null)
            valores.put(PARTICIPANTE, participante);
        if(qr!=null)
            valores.put(QR,qr);
        if(dimensiones!=null)
            valores.put(DIMENSIONES,dimensiones);
        //if(premio!=null)
            valores.put(PREMIO,premio);
        if(actualizacion!=null)
            valores.put(ACTUALIZACION, dateFormat.format(actualizacion));
        if(actualizacion_foto!=null)
            valores.put(ACTUALIZACION_FOTO, dateFormat.format(actualizacion_foto));
        return valores;
    }
/*
    public void insertar(Integer id,String titulo,String descripcion,String fecha,String tecnica,String foto_url,Bitmap foto,Integer participante,String qr,String dimensiones,Date actualizacion,Date actualizacion_foto)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        foto.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] image = stream.toByteArray();
        db.insert(TABLE_NAME, null, generarContentValues(id, titulo, descripcion, fecha, tecnica,foto_url, image, participante, qr, dimensiones, actualizacion, actualizacion_foto));
    }


    public void insertar(Obra obra)
    {
        insertar(obra.getIdo(), obra.getTio(), obra.getDeo(), obra.getFeo(), obra.getTeo(),obra.getFoo(), obra.getBitmap(), obra.getIdp(), obra.getQro(), obra.getDio(), obra.getAco(), obra.getAfo());
    }

    public void insertar(ArrayList<Obra> obras)
    {
        for(Obra obra:obras)
            insertar(obra);
    }
*/
    public boolean insertaroActualizar(Integer id,String titulo,String descripcion,String descripcion_en,String fecha,String tecnica,String tecnica_en,String foto,Integer participante,String qr,String dimensiones,String premio,Date actualizacion,Date actualizacion_foto)
    {
        byte[] image = null;
        Cursor c = consultar(id);
        if(c.moveToFirst())
        {

            if(!actualizacion.after(Timestamp.valueOf(c.getString(9))))
                return false;

            if(actualizacion_foto.after(Timestamp.valueOf(c.getString(10)))) {
                //image = LoadInformation.getImage(foto);
            }
            String[] args = new String[] {id+""};
            db.update(TABLE_NAME, generarContentValues(id, titulo, descripcion, descripcion_en, fecha, tecnica, tecnica_en,foto, image, participante, qr, dimensiones, premio, actualizacion, actualizacion_foto), ID + "=?", args);
            return true;
        }else {
            //image = LoadInformation.getImage(foto);

            db.insert(TABLE_NAME, null, generarContentValues(id, titulo, descripcion, descripcion_en, fecha, tecnica, tecnica_en,foto, image, participante, qr, dimensiones, premio, actualizacion, actualizacion_foto));
        }
        return false;
    }
/*

    public boolean insertaroActualizar(Obra obra)
    {
        return insertaroActualizar(obra.getIdo(), obra.getTio(), obra.getDeo(), obra.getFeo(), obra.getTeo(), obra.getFoo(), obra.getIdp(), obra.getQro(), obra.getDio(), obra.getAco(), obra.getAfo());
    }
*/

    public void insertaroActualizar(ArrayList<Obra> obras)
    {
        for(Obra obra:obras)
            insertaroActualizar(obra.getIdo(), obra.getTio(), obra.getDeo(), obra.getDeo_en(), obra.getFeo(), obra.getTeo(), obra.getTeo_en(), obra.getFoo(), obra.getIdp(), obra.getQro(), obra.getDio(), obra.getPro(), obra.getAco(), obra.getAfo());
    }


    public Cursor consultar(Integer id){

        String QB= TABLE_NAME +
                " JOIN " + DBParticipante.NOMBRE_TABLA + " ON " +
                PARTICIPANTE + " = " + DBParticipante.ID;

        String[] campos = new String[] {ID,TITULO,DESCRIPCION,FECHA,TECNICA,FOTO,PARTICIPANTE,QR,DIMENSIONES,ACTUALIZACION,ACTUALIZACION_FOTO,DBParticipante.NOMBRE,FOTO_URL,DBParticipante.ID};
        //Cursor c = db.query(NOMBRE_TABLA, campos, "usuario=?(where)", args(para el where), group by, having, order by, num);

        String[] args = new String[] {id+""};

        if(id==null)return db.query(QB, campos, null, null, null, null,null);
        return db.query(QB, campos, ID+"=?", args, null, null, null);
    }

    public Cursor consultar_en(Integer id){

        String QB= TABLE_NAME +
                " JOIN " + DBParticipante.NOMBRE_TABLA + " ON " +
                PARTICIPANTE + " = " + DBParticipante.ID;

        String[] campos = new String[] {ID,TITULO,DESCRIPCION_EN,FECHA,TECNICA_EN,FOTO,PARTICIPANTE,QR,DIMENSIONES,ACTUALIZACION,ACTUALIZACION_FOTO,DBParticipante.NOMBRE,FOTO_URL,DBParticipante.ID};
        //Cursor c = db.query(NOMBRE_TABLA, campos, "usuario=?(where)", args(para el where), group by, having, order by, num);

        String[] args = new String[] {id+""};

        if(id==null)return db.query(QB, campos, null, null, null, null,null);
        return db.query(QB, campos, ID+"=?", args, null, null, null);
    }

    public Cursor consultarQr(String qr){

        if(qr==null)return null;

        String QB= TABLE_NAME +
                " JOIN " + DBParticipante.NOMBRE_TABLA + " ON " +
                PARTICIPANTE + " = " + DBParticipante.ID;

        String[] campos = new String[] {ID,TITULO,FOTO_URL,PARTICIPANTE,DBParticipante.NOMBRE,DBParticipante.FOTO_URL};
        String[] args = new String[] {qr+""};

        return db.query(QB, campos, QR+"=?", args, null, null, null);
    }

    public Cursor consultarObras(Integer id){

        String[] campos = new String[] {ID,TITULO,FOTO,FOTO_URL};
        //Cursor c = db.query(NOMBRE_TABLA, campos, "usuario=?(where)", args(para el where), group by, having, order by, num);

        String[] args = new String[] {id+""};

        if(id==null)return db.query(TABLE_NAME, campos, null, null, null, null,null);
        return db.query(TABLE_NAME, campos, ID+"=?", args, null, null, null);
    }

    public Cursor consultarObrasPremios(){

        String QB= TABLE_NAME +
                " JOIN " + DBParticipante.NOMBRE_TABLA + " ON " +
                PARTICIPANTE + " = " + DBParticipante.ID;

        String[] campos = new String[] {DBParticipante.ID, ID, DBParticipante.NOMBRE, TITULO, DBParticipante.FOTO_URL, FOTO_URL, PREMIO};

        return db.query(QB, campos, PREMIO +" is not null ", null, null, null,PREMIO);
    }

    public int borrar(String ids){
        return db.delete(TABLE_NAME,ID+" not in ("+ids+")", null);
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
