package salonmachala.org.salonmachala;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import widget.JustifiedTextView;

public class ArtistaActivity extends AppCompatActivity {

    ArtistaActivity artista = this;

    JustifiedTextView wv_bibliografia;
    //DocumentView tv_bibliografia;
    TextView tv_nombre;
    TextView tv_edad;
    TextView tv_nacionalidad;
    ImageView iv_foto;

    String nombre_artista;
    String nacionalidad_artista;
    String edad_artista;
    String bibliografia_artista;
    int foto_artista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artista);

        Bundle b = getIntent().getExtras();

        if(b != null) {
            nombre_artista = b.getString("nombre_artista");
            nacionalidad_artista = b.getString("nacionalidad_artista");
            edad_artista = b.getString("edad_artista");
            bibliografia_artista = b.getString("bibliografia_artista");
            foto_artista = b.getInt("foto_artista");
        }


        initComponents();
        llenaInformacion();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void initComponents(){


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        wv_bibliografia = (JustifiedTextView) findViewById(R.id.wv_bibliografia);
        //tv_bibliografia = (DocumentView) findViewById(R.id.tv_bibliografia);
        tv_nombre = (TextView) findViewById(R.id.tv_nombre);
        tv_edad = (TextView) findViewById(R.id.tv_edad);
        tv_nacionalidad = (TextView) findViewById(R.id.tv_nacionalidad);
        iv_foto = (ImageView) findViewById(R.id.iv_foto);


        iv_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iv_foto.buildDrawingCache();
                Bitmap image= iv_foto.getDrawingCache();

                Intent intent = new Intent(artista,AbrirImagen.class);
                Bundle extras = new Bundle();
                extras.putParcelable("imagebitmap", image);
                extras.putString("title", nombre_artista);
                intent.putExtras(extras);
                startActivity(intent);

            }
        });

    }


    public void llenaInformacion(){
        tv_nombre.setText(nombre_artista);
        tv_nacionalidad.setText(nacionalidad_artista);
        tv_edad.setText(edad_artista);
        iv_foto.setImageResource(foto_artista);

        wv_bibliografia.setText(bibliografia_artista);
        wv_bibliografia.setTextColor(Color.BLACK);
        wv_bibliografia.setTextSize(17);

        //tv_bibliografia.getDocumentLayoutParams().setTextAlignment(TextAlignment.JUSTIFIED);
        //tv_bibliografia.setText(bibliografia_artista);
    }

}
