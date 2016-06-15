package salonmachala.org.salonmachala;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

/**
 * Created by ces_m on 6/14/2016.
 */
public class Global {
    public static Activity activity= null;
    public final static int MY_PERMISSIONS_REQUEST_WRITE=753;

    public static void showMessageOKCancel(Integer title, int message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener noListener) {
        new AlertDialog.Builder(Global.activity)
                .setTitle(title==null?"": Global.activity.getResources().getString(title))
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, okListener)
                .setNegativeButton(android.R.string.no, noListener)
                .setCancelable(false)
                .create()
                .show();
    }

    public static boolean permiso_escritura(){

        if (android.os.Build.VERSION.SDK_INT>=23 && ContextCompat.checkSelfPermission(Global.activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(Global.activity,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                Global.MY_PERMISSIONS_REQUEST_WRITE);
            return false;
        }
        return true;

    }

    public static void premiso_write_denegado(final Activity activity){
        Global.showMessageOKCancel(null,R.string.permiso_write,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", Global.activity.getPackageName(), null);
                        intent.setData(uri);
                        Global.activity.startActivityForResult(intent, 122);

                        activity.finish();
                    }
                },new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                    }
                });
    }

}
