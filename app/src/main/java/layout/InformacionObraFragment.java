package layout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import lazyLoad.ImageLoader;
import salonmachala.org.salonmachala.ArtistaActivity;
import salonmachala.org.salonmachala.ArtistaActivityTabs;
import salonmachala.org.salonmachala.Global;
import salonmachala.org.salonmachala.MainActivity;
import salonmachala.org.salonmachala.R;
import widget.JustifiedTextView;

/**
 * Created by ces_m on 7/4/2016.
 */
public class InformacionObraFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "param6";
    private static final String ARG_PARAM7 = "param7";
    private int nColumnas = 2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    private String mParam5;
    private String mParam6;
    private Integer mParam7;


    TextView tv_titulo;
    TextView tv_fecha;
    TextView tv_autor;
    TextView tv_tecnica;
    TextView tv_dimensiones;
    ImageView iv_foto;

    public ImageLoader imageLoader;

    private OnFragmentInteractionListener mListener;

    public InformacionObraFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArtistasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InformacionObraFragment newInstance(String param1, String param2, String param3, String param4, String param5, String param6, Integer param7) {
        InformacionObraFragment fragment = new InformacionObraFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        args.putString(ARG_PARAM5, param5);
        args.putString(ARG_PARAM6, param6);
        args.putInt(ARG_PARAM7, param7);
        fragment.setArguments(args);
        //fragment.tipo = param1;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
            mParam5 = getArguments().getString(ARG_PARAM5);
            mParam6 = getArguments().getString(ARG_PARAM6);
            mParam7 = getArguments().getInt(ARG_PARAM7);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_informacion_obra, container, false);
        init(view);
        setInfo(mParam1,mParam2,mParam3,mParam4,mParam5,mParam6,mParam7);
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void init(View view){
        imageLoader = new ImageLoader(getActivity().getApplicationContext(),true);

        tv_titulo = (TextView) view.findViewById(R.id.titulo);
        tv_fecha = (TextView) view.findViewById(R.id.fecha);
        tv_autor = (TextView) view.findViewById(R.id.autor);
        tv_tecnica = (TextView) view.findViewById(R.id.tecnica);
        tv_dimensiones = (TextView) view.findViewById(R.id.dimensiones);
        iv_foto = (ImageView) view.findViewById(R.id.iv_foto);

    }

    public void setInfo(String titulo,String fecha,String autor, String tecnica, String dimensiones, String foto,final Integer id_autor){
        SpannableString content = new SpannableString(autor);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tv_autor.setTextColor(Color.BLUE);
        tv_autor.setText(content);
        tv_autor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ArtistaActivityTabs.class);
                Bundle b = new Bundle();

                b.putInt("id", id_autor);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        tv_titulo.setText(titulo);
        tv_fecha.setText(fecha);
        tv_tecnica.setText(tecnica);
        tv_dimensiones.setText(dimensiones);

        imageLoader.DisplayImage(foto,iv_foto);
        Global.openImageView(iv_foto,tv_titulo,foto,null);

    }

}
