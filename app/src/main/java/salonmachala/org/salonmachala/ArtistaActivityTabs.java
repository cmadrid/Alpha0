package salonmachala.org.salonmachala;

import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import database.DBParticipante;
import layout.DescripcionFragment;
import layout.InformacionArtistaFragment;

public class ArtistaActivityTabs extends MyBaseActivity implements
        DescripcionFragment.OnFragmentInteractionListener,
        InformacionArtistaFragment.OnFragmentInteractionListener{

    private SectionsPagerAdapter mSectionsPagerAdapter;
    InformacionArtistaFragment iaf;
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
                iaf = InformacionArtistaFragment.newInstance(c.getString(1),
                            c.getString(4),
                            calculateAge(Timestamp.valueOf(c.getString(3)))+" "+getResources().getString(R.string.anios),
                            c.getString(9));
                String biografia = c.getString(5);
                if(biografia.equalsIgnoreCase(""))
                    numPages=1;
                df = DescripcionFragment.newInstance(biografia);
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

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    return iaf;
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
                    return getString(R.string.biografia_str);
            }
            return null;
        }
    }
}
