package gson;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import database.DBObra;
import database.DBParticipante;
import lazyLoad.FileCache;
import salonmachala.org.salonmachala.SalonMachala;
import salonmachala.org.salonmachala.Splash;

/**
 * Created by ces_m on 6/4/2016.
 */
public class LoadInformation extends AsyncTask<String, String, String> {


    Context c;
    static SharedPreferences sharedpreferences;
    String actualizacion_participantes;
    String actualizacion_obras;

    FileCache fileCache;

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    public LoadInformation(Context c) {
        this.c = c;
        sharedpreferences = c.getSharedPreferences(SalonMachala.Pref.MyPREFERENCES, Context.MODE_PRIVATE);
        fileCache = new FileCache(c);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        System.out.println("inicio");

        actualizacion_participantes = sharedpreferences.getString(SalonMachala.Pref.Actualizacion_Participantes, null);
        actualizacion_obras = sharedpreferences.getString(SalonMachala.Pref.Actualizacion_Obras, null);


        String jsonp = RequestJsonHttp.executePost("informacion_participantes",new Param<String, String>("actualizacion", actualizacion_participantes));
        InformacionParticipantes ip = gson.fromJson(jsonp, InformacionParticipantes.class);


        String jsono = RequestJsonHttp.executePost("informacion_obras",new Param<String, String>("actualizacion", actualizacion_obras));
        InformacionObras io = gson.fromJson(jsono, InformacionObras.class);


        System.out.println("actualizacion participantes("+actualizacion_participantes+"): "+ip.getActualizacion());
        System.out.println("actualizacion obras("+actualizacion_obras+"): "+io.getActualizacion());

        SharedPreferences.Editor editor = sharedpreferences.edit();

        System.out.println("fin");

        ArrayList<Participante> participantes = ip.getData();
        ArrayList<Obra> obras = io.getData();


        if(actualizacion_participantes==null || actualizacion_obras==null)
            fileCache.clear();
        //getImagesParticipantes(participantes);
        //getImagesObras(obras);

        System.out.println(jsonp);
        System.out.println("--------------------------------------*-*-*-*-*-*--------------------------------------------");
        System.out.println(jsono);

        DBParticipante db_participante = null;
        try {

            db_participante=new DBParticipante(c);

            db_participante.insertaroActualizar(participantes);

            System.out.println("correcto P");

            editor.putString(SalonMachala.Pref.Actualizacion_Participantes, ip.getActualizacion()).apply();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db_participante.close();
        }


        DBObra db_obra = null;
        try {

            db_obra=new DBObra(c);

            db_obra.insertaroActualizar(obras);

            System.out.println("correcto O");

            editor.putString(SalonMachala.Pref.Actualizacion_Obras, io.getActualizacion()).apply();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db_participante.close();
        }

        editor.putBoolean(SalonMachala.Pref.Ejecutado,true).apply();

        return null;
    }

    public static byte[] getImage(String foto){
        byte[] array = null;
        if(foto!=null && !foto.equalsIgnoreCase("")) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            getBitmapFromURL(foto).compress(Bitmap.CompressFormat.PNG, 0, stream);
            array = stream.toByteArray();
        }
        return array;
    }

    private void getImagesObras(ArrayList<Obra> datos){
        for(Obra obra : datos) {
            String foto = obra.getFoo();
            if(foto!=null && !foto.equalsIgnoreCase(""))
                obra.setBitmap(getBitmapFromURL(foto));
        }
    }
    private void getImagesParticipantes(ArrayList<Participante> datos){
        for(Participante participante:datos) {
            String foto = participante.getFop();
            if(foto!=null && !foto.equalsIgnoreCase(""))
                participante.setBitmap(getBitmapFromURL(foto));
        }
    }
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            // Log exception
            return null;
        }


    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(c instanceof Splash)
            ((Splash) c).toMain();
    }
}
