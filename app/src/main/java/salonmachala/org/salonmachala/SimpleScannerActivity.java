package salonmachala.org.salonmachala;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import database.DBObra;
import database.DBParticipante;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by ces_m on 5/15/2016.
 */
public class SimpleScannerActivity extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    final int MY_PERMISSIONS_REQUEST_CAMERA = 12345;
    Activity simple = this;

    int id_obra;
    String nombre_obra;
    Bitmap foto_obra;

    int id_artista;
    String nombre_artista;
    Bitmap foto_artista;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
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
        System.out.println("raw: "+rawResult.getText());
        System.out.println("bc: "+rawResult.getBarcodeFormat().toString());

        DBObra db_obra = new DBObra(getApplicationContext());
        Cursor c = db_obra.consultarQr(rawResult.getText().toLowerCase());

        if(c.moveToFirst()){
            id_obra = c.getInt(0);
            nombre_obra = c.getString(1);
            id_artista = c.getInt(3);
            nombre_artista = c.getString(4);

            byte[] byteArray = c.getBlob(2);
            foto_obra = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

            byteArray = c.getBlob(5);
            foto_artista = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);


        }


        openDialog();
        finish();

    }

    private void openDialog(){
        LayoutInflater inflater = MainActivity.mainActivity.getLayoutInflater();
        //View view = inflater.inflate(R.layout.dialog_autor_obra, null);
        View view = inflater.inflate(R.layout.dialog_autor_obra2, null);
        ImageButton ib_autor = (ImageButton) view.findViewById(R.id.ib_autor);
        ImageButton ib_obra = (ImageButton) view.findViewById(R.id.ib_obra);
        TextView tv_autor = (TextView) view.findViewById(R.id.tv_nombre_autor);
        TextView tv_obra = (TextView) view.findViewById(R.id.tv_nombre_obra);
        TextView tv_titulo = (TextView) view.findViewById(R.id.titulo_dialogo);

        //tv_obra.setText(nombre_obra);
        tv_obra.setText(R.string.argumento);
        tv_titulo.setText(nombre_obra);

        tv_autor.setText(nombre_artista);
        ib_autor.setImageBitmap(foto_artista);
        ib_obra.setImageBitmap(foto_obra);


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
