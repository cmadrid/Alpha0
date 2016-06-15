package salonmachala.org.salonmachala;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoViewAttacher;

public class AbrirImagen extends AppCompatActivity {

    PhotoViewAttacher mAttacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abrir_imagen);

        Bundle extras = getIntent().getExtras();
        Bitmap bmp = extras.getParcelable("imagebitmap");
        int resource = extras.getInt("imageResource",0);
        String title = extras.getString("title");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(title);

        ImageView image = (ImageView) findViewById(R.id.foto_full);
        if(bmp!=null)
            image.setImageBitmap(bmp );
        else if(resource!=0)
            image.setImageResource(resource);

        mAttacher = new PhotoViewAttacher(image);
        mAttacher.update();
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
}
