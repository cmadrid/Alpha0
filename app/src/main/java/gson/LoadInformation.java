package gson;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.MainThread;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import database.DBObra;
import database.DBParticipante;
import salonmachala.org.salonmachala.MainActivity;
import salonmachala.org.salonmachala.R;

/**
 * Created by ces_m on 6/4/2016.
 */
public class LoadInformation extends AsyncTask<String, String, String> {


    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    public LoadInformation() {

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        System.out.println("inicio");
        /*
        String json = RequestJsonHttp.executePost("informacion_qr", new Param<String, String>("qr", "gioconda"));
        InformacionQr iq = gson.fromJson(json, InformacionQr.class);

        System.out.println("fin");
        if (iq.getCode() == 0) {
            System.out.println(iq.getData().getTeo());
            System.out.println(iq.getData().getFoo());
            System.out.println(iq.getData().getDio());
            System.out.println(iq.getData().getAco());
        }
        System.out.println(json);

        DBParticipante db_participante = null;
        try {

            db_participante=new DBParticipante(MainActivity.mainActivity);
            Bitmap icon = BitmapFactory.decodeResource(MainActivity.mainActivity.getResources(),
                    R.drawable.da_vinci);
            db_participante.insertar(0,"name","pse","nac","nacio","biblio",icon,new Date(),new Date());
            System.out.println("correcto");

        }catch (Exception e){}
        finally {
            db_participante.close();
        }
        */

        String jsonp = RequestJsonHttp.executePost("informacion_participantes");
        InformacionParticipantes ip = gson.fromJson(jsonp, InformacionParticipantes.class);


        String jsono = RequestJsonHttp.executePost("informacion_obras");
        InformacionObras io = gson.fromJson(jsono, InformacionObras.class);

        System.out.println("fin");

        ArrayList<Participante> participantes = ip.getData();
        ArrayList<Obra> obras = io.getData();

        getImagesParticipantes(participantes);
        getImagesObras(obras);

        System.out.println(jsonp);
        System.out.println("--------------------------------------*-*-*-*-*-*--------------------------------------------");
        System.out.println(jsono);

        DBParticipante db_participante = null;
        try {

            db_participante=new DBParticipante(MainActivity.mainActivity);

            db_participante.insertaroActualizar(participantes);

            System.out.println("correcto P");

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db_participante.close();
        }


        DBObra db_obra = null;
        try {

            db_obra=new DBObra(MainActivity.mainActivity);

            db_obra.insertaroActualizar(obras);

            System.out.println("correcto O");

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db_participante.close();
        }

        return null;
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
            URL url = new URL(src.replace("localhost",RequestJsonHttp.host));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
