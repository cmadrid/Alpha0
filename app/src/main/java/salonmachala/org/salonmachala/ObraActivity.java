package salonmachala.org.salonmachala;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import widget.JustifiedTextView;

public class ObraActivity extends AppCompatActivity {

    ObraActivity obra = this;

    JustifiedTextView wv_descripcion;
    //TextView tv_descripcion;
    TextView tv_titulo;
    TextView tv_fecha;
    TextView tv_autor;
    TextView tv_tecnica;
    TextView tv_dimensiones;
    ImageView iv_foto;

    String nombre_obra;
    String nombre_autor;
    String tecnica;
    String dimensiones;
    String fecha;
    String descripcion;
    int foto_obra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obra);

        Bundle b = getIntent().getExtras();

        if(b != null) {
            nombre_obra = b.getString("nombre_obra");
            nombre_autor = b.getString("nombre_artista");
            fecha = b.getString("creada_obra");
            descripcion = b.getString("descripcion_obra");
            tecnica = b.getString("tecnica_obra");
            dimensiones = b.getString("dimensiones_obra");
            foto_obra = b.getInt("foto_obra");
        }


        initComponents();
        llenaInformacion();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

        //tv_descripcion = (TextView) findViewById(R.id.descripcion);
        wv_descripcion = (JustifiedTextView) findViewById(R.id.wv_descripcion);
        tv_titulo = (TextView) findViewById(R.id.titulo);
        tv_fecha = (TextView) findViewById(R.id.fecha);
        tv_autor = (TextView) findViewById(R.id.autor);
        tv_tecnica = (TextView) findViewById(R.id.tecnica);
        tv_dimensiones = (TextView) findViewById(R.id.dimensiones);
        iv_foto = (ImageView) findViewById(R.id.foto);

        iv_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iv_foto.buildDrawingCache();
                Bitmap image= iv_foto.getDrawingCache();

                Intent intent = new Intent(obra,AbrirImagen.class);
                Bundle extras = new Bundle();
                extras.putParcelable("imagebitmap", image);
                extras.putString("title", nombre_obra);
                intent.putExtras(extras);
                startActivity(intent);

            }
        });

    }


    public void llenaInformacion(){
        tv_titulo.setText(nombre_obra);
        tv_autor.setText(nombre_autor);
        tv_fecha.setText(fecha);

        tv_tecnica.setText(tecnica);
        tv_dimensiones.setText(dimensiones);

        iv_foto.setImageResource(foto_obra);

        wv_descripcion.setText(descripcion);
        wv_descripcion.setTextColor(Color.BLACK);
        wv_descripcion.setTextSize(17);
        //tv_descripcion.setText(descripcion);
    }

}
