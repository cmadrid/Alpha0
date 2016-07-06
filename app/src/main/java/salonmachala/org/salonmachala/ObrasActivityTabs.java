package salonmachala.org.salonmachala;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.view.View;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import database.DBObra;
import database.DBParticipante;
import layout.DescripcionFragment;
import layout.InformacionArtistaFragment;
import layout.InformacionObraFragment;

public class ObrasActivityTabs extends MyBaseActivity implements
        DescripcionFragment.OnFragmentInteractionListener,
        InformacionObraFragment.OnFragmentInteractionListener{

    private SectionsPagerAdapter mSectionsPagerAdapter;
    InformacionObraFragment iof;
    DescripcionFragment df;

    Integer id;
    Integer numPages = 2;
    TabLayout tabLayout;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(super.retornar)return;

        setContentView(R.layout.activity_tabs);


        Bundle b = getIntent().getExtras();

        //numPages = 2;
        if(b != null) {
            if(b.containsKey("id"))
                id = b.getInt("id");
        }


        llenaInformacion();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);



        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


    }


    @Override
    public void onFragmentInteraction(Uri uri) {

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



    public void llenaInformacion(){



        DBObra db_obra = null;
        try {
            db_obra = new DBObra(getApplicationContext());
            Cursor c = null;
            if(id!=null) {
                if(Global.estaEspaniol())
                    c = db_obra.consultar(id);
                else
                    c = db_obra.consultar_en(id);
            }

            if(c==null)
                return;

            if(c.moveToFirst()) {
                iof = InformacionObraFragment.newInstance(c.getString(1),
                        c.getString(3),
                        c.getString(11),
                        c.getString(4),
                        c.getString(8),
                        c.getString(12),
                        c.getInt(13));

                String descripcion = c.getString(2);
                if(descripcion.equalsIgnoreCase(""))
                    numPages=1;
                df = DescripcionFragment.newInstance(descripcion);

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db_obra.close();
        }

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    return iof;
                case 1:
                    return df;
            }
            return null;
        }

        @Override
        public int getCount() {
            return numPages;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.informacion);
                case 1:
                    return getString(R.string.descripcion_str);
            }
            return null;
        }
    }
}
