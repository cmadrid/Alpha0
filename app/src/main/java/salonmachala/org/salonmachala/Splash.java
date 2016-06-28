package salonmachala.org.salonmachala;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import database.DBFake;


public class Splash extends Activity {

    TextView tv_cargando;
    int count = 0;
    String qr_code = null;
    static SharedPreferences sharedpreferences;
    boolean ejecutado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        Global.inicio = true;
        Global.activity = this;
        new DBFake(this).close();

        tv_cargando = (TextView) findViewById(R.id.tv_cargando);

        sharedpreferences = getSharedPreferences(SalonMachala.Pref.MyPREFERENCES, Context.MODE_PRIVATE);
        ejecutado = sharedpreferences.getBoolean(SalonMachala.Pref.Ejecutado, false);

        Global.setLocale(sharedpreferences.getString(SalonMachala.Pref.Idioma,"es"));

        if(Global.permiso_escritura()){
            descargar();
        }


    }

    public boolean conectado(){

        ConnectivityManager conMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        if (i == null)
            return false;
        if (!i.isConnected())
            return false;
        if (!i.isAvailable())
            return false;
        return true;
    }

    public void toMain(){
        //Toast.makeText(this,"Contador final: "+count+" segundos",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void setTv_cargando_text(String txt){
        tv_cargando.setText(getString(R.string.cargando_splash)+": "+txt);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Global.MY_PERMISSIONS_REQUEST_WRITE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    descargar();

                } else {

                    if (!ActivityCompat.shouldShowRequestPermissionRationale(Global.activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Global.premiso_write_denegado(Global.activity);
                        return;
                    }
                    Global.showMessageOKCancel(null,R.string.permiso_requerido,null,null);
                    finish();

                }
                return;
            }
        }

    }

    public void descargar(){
        if(!conectado()){
            if(!ejecutado)
                Global.showMessageOKCancel(R.string.conexion,R.string.conexion_requiere,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                },null);

            else {
                toMain();
            }
        }
        else
            new gson.LoadInformation(this).execute();
    }




}
