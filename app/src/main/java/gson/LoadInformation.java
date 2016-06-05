package gson;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
