package salonmachala.org.salonmachala;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import database.DBObra;
import lazyLoad.ImageLoader;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by ces_m on 5/15/2016.
 */
public class SimpleScannerActivity extends MyBaseActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    final int MY_PERMISSIONS_REQUEST_CAMERA = 12345;
    Activity simple = this;

    int id_obra;
    String nombre_obra;
    String foto_obra;

    int id_artista;
    String nombre_artista;
    String foto_artista;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        if(super.retornar)return;


        final Intent intent = getIntent();
        final String action = intent.getAction();

        if(Global.qr_code!=null){
            String tmp = Global.qr_code;
            Global.qr_code=null;
            if(!getData(tmp)) {
                finish();
                return;
            }
            openDialog();
            finish();
            return;
        }

        if (Intent.ACTION_VIEW.equals(action)) {
            String qr_code = intent.getData().getQueryParameter("qr_code");
            if(qr_code==null)qr_code="no";
            qr_code = qr_code.toLowerCase();
            if(MainActivity.mainActivity==null) {
                Global.qr_code = qr_code;
                startActivity(new Intent(this, Splash.class));
            }
            else {
                startActivity(new Intent(this,MainActivity.class));
                if(!getData(qr_code)) {
                    finish();
                    return;
                }
                openDialog();
            }
            finish();
            return;
        }

        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view

        List<BarcodeFormat> list = new ArrayList<>();
        list.add(BarcodeFormat.QR_CODE);

        mScannerView.setFormats(list);

    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        //Log.v(TAG, rawResult.getText()); // Prints scan results
        //Log.v(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
        ////System.out.println("raw: "+rawResult.getText());
        ////System.out.println("bc: "+rawResult.getBarcodeFormat().toString());
        Uri u = Uri.parse(rawResult.getText());
        String qr_code = u.getQueryParameter("qr_code");
        if(qr_code==null)qr_code="no";
        if(!getData(qr_code.toLowerCase())) {
            finish();
            return;
        }
        openDialog();
        finish();

    }

    private boolean getData(String qr){

        DBObra db_obra = null;

        try {
            db_obra = new DBObra(getApplicationContext());
            Cursor c = db_obra.consultarQr(qr);

            if(c!=null && c.moveToFirst()){
                id_obra = c.getInt(0);
                nombre_obra = c.getString(1);
                id_artista = c.getInt(3);
                nombre_artista = c.getString(4);

                foto_obra = c.getString(2);
                foto_artista = c.getString(5);

            }else{
                Toast.makeText(MainActivity.mainActivity,R.string.no_qr,Toast.LENGTH_SHORT).show();
                finish();
                return false;
            }

        }catch (Exception e){

        }finally {
            db_obra.close();
        }

        return true;
    }

    private void openDialog(){
        LayoutInflater inflater = MainActivity.mainActivity.getLayoutInflater();
        //View view = inflater.inflate(R.layout.dialog_autor_obra, null);
        View view = inflater.inflate(R.layout.dialog_autor_obra, null);
        ImageView ib_autor = (ImageView) view.findViewById(R.id.ib_autor);
        ImageView ib_obra = (ImageView) view.findViewById(R.id.ib_obra);
        TextView tv_autor = (TextView) view.findViewById(R.id.tv_nombre_autor);
        TextView tv_obra = (TextView) view.findViewById(R.id.tv_nombre_obra);
        TextView tv_titulo = (TextView) view.findViewById(R.id.titulo_dialogo);


        //tv_obra.setText(nombre_obra);
        tv_obra.setText(R.string.argumento);
        tv_titulo.setText(nombre_obra);

        tv_autor.setText(nombre_artista);

        try {
            if(foto_artista!=null && !foto_artista.equalsIgnoreCase(""))
                new ImageLoader(this, true).DisplayImage(foto_artista,ib_autor);
        }catch (Exception e){}
        try {
            if(foto_obra!=null && !foto_obra.equalsIgnoreCase(""))
                new ImageLoader(this, true).DisplayImage(foto_obra,ib_obra);
        }catch (Exception e){}

        ib_autor.setBackgroundColor(Color.YELLOW);
        ib_obra.setBackgroundColor(Color.YELLOW);
        ib_autor.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ib_obra.setScaleType(ImageView.ScaleType.CENTER_CROP);


        ib_autor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(simple, ArtistaActivity.class);
                Bundle b = new Bundle();
                b.putInt("id", id_artista);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        ib_obra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(simple, ObraActivity.class);
                Bundle b = new Bundle();

                b.putInt("id", id_obra);

                intent.putExtras(b);
                startActivity(intent);
            }
        });

        new AlertDialog.Builder(MainActivity.mainActivity).setView(view)
                .setCancelable(true)
                .show();
    }



}
