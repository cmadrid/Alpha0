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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import database.DBParticipante;
import lazyLoad.ImageLoader;
import widget.JustifiedTextView;

public class ArtistaActivity extends MyBaseActivity {

    ArtistaActivity artista = this;

    JustifiedTextView wv_bibliografia;
    //DocumentView tv_bibliografia;
    TextView tv_nombre;
    TextView tv_edad;
    TextView tv_nacionalidad;
    ImageView iv_foto;

    public ImageLoader imageLoader;

    Integer id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(super.retornar)return;

        setContentView(R.layout.activity_artista);

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


    }


    public void llenaInformacion(){
        DBParticipante db_participante = null;
        try {
            db_participante = new DBParticipante(getApplicationContext());
            Cursor c = null;
            if(id!=null) {
                if(Global.estaEspaniol())
                    c = db_participante.consultar(id);
                else
                    c = db_participante.consultar_en(id);
            }

            if(c==null)
                return;

            if(c.moveToFirst()) {


                tv_nombre.setText(c.getString(1));
                tv_nacionalidad.setText(c.getString(4));

                tv_edad.setText(calculateAge(Timestamp.valueOf(c.getString(3)))+" "+getResources().getString(R.string.anios));
                imageLoader.DisplayImage(c.getString(9),iv_foto);
                Global.openImageView(iv_foto,tv_nombre,c.getString(9),null);

                wv_bibliografia.setText(c.getString(5));
                wv_bibliografia.setTextColor(Color.BLACK);


                wv_bibliografia.setTextSize(Global.getSizeWv());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db_participante.close();
        }


    }

    public int calculateAge(Date nacimiento){
        Calendar dob = Calendar.getInstance();
        dob.setTime(nacimiento);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) <= dob.get(Calendar.DAY_OF_YEAR))
            age--;
        return age;
    }

}
