package salonmachala.org.salonmachala;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ces_m on 6/25/2016.
 */



public class MyBaseActivity extends AppCompatActivity {

    public boolean retornar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!Global.inicio) {
            System.out.println("dentro : "+retornar);
            retornar = true;
            startActivity(new Intent(this, Splash.class));
            finish();
        }
    }
}
