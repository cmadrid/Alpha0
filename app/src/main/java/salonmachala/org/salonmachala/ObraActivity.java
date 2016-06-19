package salonmachala.org.salonmachala;

import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import database.DBObra;
import gson.LoadInformation;
import lazyLoad.ImageLoader;
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

    public ImageLoader imageLoader;
    Integer id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obra);

        Bundle b = getIntent().getExtras();

        if(b != null) {
            if(b.containsKey("id"))
                id = b.getInt("id");
        }


        imageLoader = new ImageLoader(MainActivity.mainActivity.getApplicationContext(),true);
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


        Global.openImageView(iv_foto,tv_titulo,null);

    }


    public void llenaInformacion(){
        DBObra db_obra = null;
        try {
            db_obra = new DBObra(getApplicationContext());
            Cursor c = null;
            if(id!=null)
                c = db_obra.consultar(id);


            if(c==null)
                return;

            if(c.moveToFirst()) {
                tv_titulo.setText(c.getString(1));
                tv_autor.setText(c.getString(11));
                tv_fecha.setText(c.getString(3));

                tv_tecnica.setText(c.getString(4));
                tv_dimensiones.setText(c.getString(8));

                imageLoader.DisplayImage(c.getString(12),iv_foto);

                wv_descripcion.setText(c.getString(2));
                wv_descripcion.setTextColor(Color.BLACK);

                int[] attrs = new int[] { android.R.attr.textSize };
                TypedArray ta = obtainStyledAttributes(R.style.size_wv_bio_des, attrs);
                int size = ta.getDimensionPixelSize(0,17);
                ta.recycle();

                wv_descripcion.setTextSize(size);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db_obra.close();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("destroy");
    }
}
