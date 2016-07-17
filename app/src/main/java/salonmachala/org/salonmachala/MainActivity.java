package salonmachala.org.salonmachala;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import database.DBParticipante;
import layout.ArtistasFragment;
import layout.InformationFragment;
import layout.InicioFragment;
import layout.ObrasFragment;
import layout.DobleFragment;
import layout.TabFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        InicioFragment.OnFragmentInteractionListener,
        ObrasFragment.OnFragmentInteractionListener,
        ArtistasFragment.OnFragmentInteractionListener,
        DobleFragment.OnFragmentInteractionListener,
        InformationFragment.OnFragmentInteractionListener,View.OnClickListener{

    final int MY_PERMISSIONS_REQUEST_CAMERA = 159;
    public static MainActivity mainActivity;
    public static ProgressBar progressWheel;
    Fragment fragment = null;

    public CollapsingToolbarLayout collapsingToolbar;
    public AppBarLayout appBarLayout;
    public ImageView header;
    public Toolbar toolbar;
    public boolean doubleBackToExitPressedOnce = false;


    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);


        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        header = (ImageView) findViewById(R.id.header);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);

        collapsingToolbar.setContentScrim(getResources().getDrawable( R.drawable.textura_morada ));

//        header.setOnClickListener();



        Global.activity = this;
        mainActivity = this;
        progressWheel = (ProgressBar) findViewById(R.id.progressWheel);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        ((TextView)navigationView.getHeaderView(0).findViewById(R.id.urlHeader)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView)navigationView.getHeaderView(0).findViewById(R.id.urlHeader)).setLinkTextColor(Color.WHITE);

        navigationView.getMenu().getItem(0).setChecked(true);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content,InicioFragment.newInstance(null,null))
                .commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //new gson.LoadInformation(this).execute();

        if(Global.qr_code!=null){
            startActivity(new Intent(mainActivity,SimpleScannerActivity.class));
        }

    }

    @Override
    protected void onResume() {
        if(!Global.inicio)
            startActivity(new Intent(this,Splash.class));
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if(!navigationView.getMenu().getItem(0).isChecked()){


            if(fragment!=null && fragment instanceof ObrasFragment)
                ((ObrasFragment)fragment).detener();
            if(fragment!=null && fragment instanceof ArtistasFragment)
                ((ArtistasFragment)fragment).detener();
            if(fragment!=null && fragment instanceof DobleFragment)
                ((DobleFragment)fragment).detener();

            navigationView.getMenu().getItem(0).setChecked(true);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content,InicioFragment.newInstance(null,null))
                    .commit();

            reestableceToolBar();
            collapsingToolbar.setTitle(getResources().getString(R.string.app_name));
            //getSupportActionBar().setTitle(R.string.app_name);
        }
        else {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                finish();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this,R.string.presiones_2_veces_salir, Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }






    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        boolean fragmentTransaction = false;

        reestableceToolBar();

        if(fragment!=null && fragment instanceof ObrasFragment)
            ((ObrasFragment)fragment).detener();
        if(fragment!=null && fragment instanceof ArtistasFragment)
            ((ArtistasFragment)fragment).detener();
        if(fragment!=null && fragment instanceof DobleFragment)
            ((DobleFragment)fragment).detener();

        if (id == R.id.nav_inicio) {
            Global.permiso_escritura();
            fragment = InicioFragment.newInstance(null,null);
            fragmentTransaction = true;
        }else
        {
            header.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        if (id == R.id.nav_antecedentes) {

            Global.permiso_escritura();
            fragment = InformationFragment.newInstance("cdg_antecedentes",null);
            fragmentTransaction = true;

        } else if (id == R.id.nav_proyeccion) {

            Global.permiso_escritura();
            fragment = InformationFragment.newInstance("cdg_proyeccion",null);
            fragmentTransaction = true;

        } else if (id == R.id.nav_creditos) {

            Global.permiso_escritura();
            fragment = InformationFragment.newInstance("cdg_creditos",null);
            fragmentTransaction = true;

        }else if (id == R.id.nav_curaduria) {

            Global.permiso_escritura();
            fragment = InformationFragment.newInstance("cdg_curaduria",null);
            fragmentTransaction = true;

        }else if (id == R.id.nav_introduccion) {

            Global.permiso_escritura();
            fragment = InformationFragment.newInstance("cdg_introduccion",null);
            fragmentTransaction = true;

        } else if (id == R.id.nav_premiados) {

            Global.permiso_escritura();
            fragment = DobleFragment.newInstance("PR",null);
            fragmentTransaction = true;

        } else if (id == R.id.nav_artista) {

            Global.permiso_escritura();
            fragment = ArtistasFragment.newInstance("'PA'",null);
            fragmentTransaction = true;

        }else if (id == R.id.nav_exposicion_multi) {
/*
            Global.permiso_escritura();
            fragment = ArtistasFragment.newInstance("'IN'",null);
            fragmentTransaction = true;
*/

            Global.permiso_escritura();
            //fragment = InformationFragment.newInstance("cdg_exposicion_m",null);
            fragment = TabFragment.newInstance(null,null);
            fragmentTransaction = true;

        }else if (id == R.id.nav_jurado) {

            Global.permiso_escritura();
            //fragment = TabFragment.newInstance(null,null);
            fragment = ArtistasFragment.newInstance("'JU'",null);
            fragmentTransaction = true;

        }else if (id == R.id.nav_invitado_especial) {

            Intent intent = new Intent(this,ArtistaActivityTabs.class);
            Bundle b = new Bundle();

            b.putInt("id", DBParticipante.consultarEspecial(this));
            intent.putExtras(b);
            startActivity(intent);

        } else if (id == R.id.nav_obras) {

            Global.permiso_escritura();
            fragment = ObrasFragment.newInstance(null,null);
            fragmentTransaction = true;

        } else if (id == R.id.nav_qr) {
            abrir_qr();
        } else if (id == R.id.nav_catalogo) {
            Global.showMessageOKCancel(R.string.catalogo_nav, R.string.abrir_catalogo, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.web_catalogo)));
                    startActivity(browserIntent);
                }
            },null);
        } else if (id == R.id.nav_idioma) {
            if(Global.estaEspaniol())
                preguntarIdioma("en");
            else
                preguntarIdioma("es");
        }

        if(fragmentTransaction){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content,fragment)
                    .commit();
            item.setChecked(true);
            if(item.getTitle().toString().equalsIgnoreCase(getResources().getString(R.string.inicio_nav)))
                collapsingToolbar.setTitle(getResources().getString(R.string.app_name));//getSupportActionBar().setTitle(R.string.app_name);
            else
                collapsingToolbar.setTitle(item.getTitle());//getSupportActionBar().setTitle(item.getTitle());
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }






    private void abrir_qr(){
        if (android.os.Build.VERSION.SDK_INT>=23 && ContextCompat.checkSelfPermission(mainActivity,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(mainActivity,new String[] {Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);

            return;

        }

        startActivity(new Intent(mainActivity,SimpleScannerActivity.class));
    }




    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    startActivity(new Intent(mainActivity, SimpleScannerActivity.class));

                } else {

                    if (!ActivityCompat.shouldShowRequestPermissionRationale(mainActivity, Manifest.permission.CAMERA)) {
                        Global.showMessageOKCancel(null,R.string.permiso_camara,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                                        intent.setData(uri);
                                        startActivityForResult(intent, 122);

                                    }
                                },null);
                        return;
                    }

                }
                return;
            }case Global.MY_PERMISSIONS_REQUEST_WRITE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(Global.activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Global.premiso_write_denegado(Global.activity);
                        return;
                    }
                }
                return;
            }
        }

    }




    @Override
    public void onFragmentInteraction(Uri uri) {

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                abrir_qr();
                break;
        }
    }


    public void reestableceToolBar(){
        header.setBackgroundColor(android.R.color.transparent);
        header.setImageDrawable(null);
        header.setOnClickListener(null);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainActivity=null;
        Global.inicio=false;

    }


    public void preguntarIdioma(final String lang){
        new AlertDialog.Builder(mainActivity)
                .setMessage(R.string.cambiar_idioma2)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setLocaleChange(lang);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .create()
                .show();
    }


    public void setLocaleChange(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = mainActivity.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

        SharedPreferences sharedpreferences = Global.activity.getSharedPreferences(SalonMachala.Pref.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        if(lang.equalsIgnoreCase("en"))
            editor.putString(SalonMachala.Pref.Idioma,"en").apply();
        else
            editor.putString(SalonMachala.Pref.Idioma,"es").apply();

        Intent refresh = new Intent(mainActivity, Splash.class);
        mainActivity.finish();
        //mainActivity.startActivity(refresh);
    }


}
