package salonmachala.org.salonmachala;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class Splash extends Activity {

    TextView tv_cargando;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        tv_cargando = (TextView) findViewById(R.id.tv_cargando);


        Timer T=new Timer();
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        setTv_cargando_text(count+"");
                        count++;
                    }
                });
            }
        }, 1000, 1000);

        new gson.LoadInformation(this).execute();
    }

    public void toMain(){
        Toast.makeText(this,"Contador final: "+count+" segundos",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void setTv_cargando_text(String txt){
        tv_cargando.setText(getString(R.string.cargando_splash)+": "+txt);
    }
}
