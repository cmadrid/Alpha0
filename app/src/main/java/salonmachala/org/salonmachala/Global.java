package salonmachala.org.salonmachala;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import database.DBInformacion;
import lazyLoad.ImageLoader;

/**
 * Created by ces_m on 6/14/2016.
 */
public class Global {
    public static Activity activity= null;
    public final static int MY_PERMISSIONS_REQUEST_WRITE=753;
    public static String qr_code = null;

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

    public static void openImageView(final ImageView imageView, final TextView titulo, final Integer resource){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap bitmap=null;
                if(resource==null) {
                    imageView.buildDrawingCache();
                    bitmap = imageView.getDrawingCache();
                }

                Intent intent = new Intent(MainActivity.mainActivity,AbrirImagen.class);
                Bundle extras = new Bundle();
                if(bitmap!=null)
                    extras.putParcelable("imagebitmap", bitmap);
                if(resource!=null)
                    extras.putInt("imageResource", resource);
                if(titulo!=null)
                    extras.putString("title", titulo.getText().toString());
                intent.putExtras(extras);
                Global.activity.startActivity(intent);

            }
        });
    }


    public static void llenaInformacion(String cod, NestedWebView wv){

        DBInformacion db_informacion = null;
        try {
            db_informacion = new DBInformacion(MainActivity.mainActivity);
            Cursor c = null;
            if(cod!=null)
                c = db_informacion.consultar(cod);

            if(c==null)
                return;

            if(c.moveToFirst()) {

                //wv.setText(new String(new char[400]).replace("\0", c.getString(5)));
                wv.setText(c.getString(5));
                if(c.getString(3)!=null && !c.getString(3).equalsIgnoreCase("")) {
                    MainActivity.mainActivity.appBarLayout.setExpanded(true);
                    new ImageLoader(MainActivity.mainActivity, true).DisplayImage(c.getString(3), MainActivity.mainActivity.header);
                    //Global.openImageView(MainActivity.mainActivity.header,null,null);
                }else
                    MainActivity.mainActivity.appBarLayout.setExpanded(false);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db_informacion.close();
        }

    }

    public static int getSizeWv(){
        int[] attrs = new int[] { android.R.attr.textSize };
        TypedArray ta = activity.obtainStyledAttributes(R.style.size_wv_bio_des, attrs);
        //int size = (int) ta.getDimension(0,(float)17.0);
        //System.out.println(ta.getString(0));
        int size_ = new Integer(ta.getString(0).split("\\.")[0]);
        ta.recycle();
        //System.out.println("tamaño de wv letra = "+size_);
        return size_;
    }


}
