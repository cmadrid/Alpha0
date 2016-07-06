package layout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.ProgressBar;

import database.DBInformacion;
import lazyLoad.ImageLoader;
import salonmachala.org.salonmachala.AbrirImagen;
import salonmachala.org.salonmachala.Global;
import salonmachala.org.salonmachala.MainActivity;
import salonmachala.org.salonmachala.NestedWebView;
import salonmachala.org.salonmachala.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InformationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InformationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    NestedWebView wv_information1;
    NestedWebView wv_information2;
    ProgressBar progressCreditos;
    String tipo_info;

    private OnFragmentInteractionListener mListener;

    public InformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Creditos.
     */
    // TODO: Rename and change types and number of parameters
    public static InformationFragment newInstance(String param1, String param2) {
        InformationFragment fragment = new InformationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        fragment.tipo_info = param1;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        init(view);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void init(View view){
        wv_information1 = (NestedWebView) view.findViewById(R.id.wv_information1);
        ViewCompat.setNestedScrollingEnabled(wv_information1, false);
        //progressCreditos = (ProgressBar) view.findViewById(R.id.progressCreditos);


        wv_information1.setTextColor(Color.BLACK);
        wv_information1.setTextSize(Global.getSizeWv());

        //Global.llenaInformacion(tipo_info,wv_information1);
        wv_information1.getSettings().setJavaScriptEnabled(true);

        wv_information1.addJavascriptInterface(new Object()
        {
            @JavascriptInterface
            public void performClick(String url, String titulo)
            {
                Intent intent = new Intent(MainActivity.mainActivity,AbrirImagen.class);
                Bundle extras = new Bundle();

                extras.putString("path", url);
                extras.putString("title",titulo );
                intent.putExtras(extras);
                MainActivity.mainActivity.startActivity(intent);
            }
        }, "ok");

        DBInformacion db_informacion = null;
        try {
            db_informacion = new DBInformacion(MainActivity.mainActivity);
            Cursor c = null;
            if(tipo_info!=null) {
                if (Global.estaEspaniol())
                    c = db_informacion.consultar(tipo_info);
                else
                    c = db_informacion.consultar_en(tipo_info);
            }
            if(c==null)
                return;

            if(c.moveToFirst()) {
                String text = "";
                //wv.setText(new String(new char[400]).replace("\0", c.getString(5)));
                text = c.getString(5);
                if(c.getString(3)!=null && !c.getString(3).equalsIgnoreCase("")) {
                    MainActivity.mainActivity.appBarLayout.setExpanded(true);
                    new ImageLoader(MainActivity.mainActivity, true).DisplayImage(c.getString(3), MainActivity.mainActivity.header);

                    //Global.openImageView(MainActivity.mainActivity.header,null,null);
                }else
                    MainActivity.mainActivity.appBarLayout.setExpanded(false);
                if(c.getString(6)!=null && !c.getString(6).equalsIgnoreCase("")){
                    text = text+insertImage(c.getString(6),c.getString(2));
                }
                if(c.getString(8)!=null && !c.getString(8).equalsIgnoreCase("")){
                    text = text+c.getString(8);
                }
                if(c.getString(9)!=null && !c.getString(9).equalsIgnoreCase("")){
                    text = text+insertImage(c.getString(9),c.getString(2));
                }
                if(c.getString(11)!=null && !c.getString(11).equalsIgnoreCase("")){
                    text = text+c.getString(11);
                }
                if(c.getString(12)!=null && !c.getString(12).equalsIgnoreCase("")){
                    text = text+insertImage(c.getString(12),c.getString(2));
                }
                if(c.getString(14)!=null && !c.getString(14).equalsIgnoreCase("")){
                    text = text+c.getString(14);
                }

                if(c.getString(15)!=null && !c.getString(15).equalsIgnoreCase("")){
                    text = text+insertImage(c.getString(15),c.getString(2));
                }
                if(c.getString(17)!=null && !c.getString(17).equalsIgnoreCase("")){
                    text = text+c.getString(17);
                }
                wv_information1.setText(text+"\n\n\n\n\n\n\n\n\n");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(db_informacion!=null)
                db_informacion.close();
        }



        //wv_information1.setWebViewProgress(progressCreditos);

        //ImageLoader imageLoader = new ImageLoader(MainActivity.mainActivity,true);
        //imageLoader.DisplayImage("http://puntoec.org/json/recursos/creditos/enrique_stefany.jpg",MainActivity.mainActivity.header);

/*
        MainActivity.mainActivity.header.setImageResource(R.drawable.enrique_stefany);
        Global.openImageView(MainActivity.mainActivity.header,null,R.drawable.enrique_stefany);*/
    }

    public String insertImage(String url,String titulo){
        return "  <center><table><tr><td>\n" +
                "        <a onclick=\"ok.performClick('"+url+"','"+titulo+"');\"><img src='"+url+"' width='100%' alt='Hello'></a>\n" +
                " </td></tr></table></center>";
    }

}
