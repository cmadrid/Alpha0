package layout;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.Timestamp;

import database.DBInformacion;
import database.DBParticipante;
import lazyLoad.ImageLoader;
import salonmachala.org.salonmachala.Global;
import salonmachala.org.salonmachala.MainActivity;
import salonmachala.org.salonmachala.NestedWebView;
import salonmachala.org.salonmachala.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AntecedentesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AntecedentesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AntecedentesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    NestedWebView wv_antecedentes;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AntecedentesFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AntecedentesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AntecedentesFragment newInstance(String param1, String param2) {
        AntecedentesFragment fragment = new AntecedentesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.fragment_antecedentes, container, false);
        initComponents(view);
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


    private void initComponents(View view){
        wv_antecedentes = (NestedWebView) view.findViewById(R.id.wv_antecedentes);
        wv_antecedentes.setTextColor(Color.BLACK);
        wv_antecedentes.setTextSize(17);

        Global.llenaInformacion("cdg_antecedentes",wv_antecedentes);
        /*wv_antecedentes.setText("<strong>Antecedentes</strong>\n" +
                "\n" +
                "Dentro del proceso de crecimiento y renovación de nuestro Salón Internacional de Artes, cuyo nombre inicial, Salón de Junio Machala, surgió hace algunos años con la idea de resaltar el mes en que se celebran las fiestas patronales de Machala, pasamos a celebrar nuestra ciudad llamando a este importante evento SALÓN MACHALA, que desde esta séptima edición 2016, acorta su nombre para ganar en solidez, eficacia y con el anhelo de que sea un espacio ganado para las artes no solo en sus fiestas de cantonización, si no a lo largo de todo el año; para que sea un generoso continuum de arte llamado por su nombre propio: Salón Machala, un encuentro internacional de artes visuales de alta calidad que desde el extremo sur del Ecuador, abre sus ventanas  al mundo para recibir la brisa fresca que nos traen las artes de las diversas culturas del planeta y para visibilizar lo que somos y cómo decimos el arte desde esta diversa tierra fértil, de abundantes orillas salitrosas y montañas preñadas de oro. El Salón Machala es una nueva manera de nombrar el arte en el sur del Ecuador.\n" +
                "\n" +
                "La edición 2015 del salón trajo consigo a nuestra ciudad nociones de alta calidad artística dentro de un contexto verdaderamente internacional en el arte de nuestros días. La comunidad de artistas del país y el público en general siguieron con interés y admiración todos los eventos que conformaron nuestra propuesta del Salón Machala, y saludaron con entusiasmo la presencia en la capital bananera del mundo, de importantes artistas y personajes de la cultura artística de diversas latitudes. \n" +
                "\n" +
                "La rigurosidad de la selección y premiación de los artistas participantes, procurada por un equipo profesional de reconocida trayectoria internacional, en combinación con una curaduría de primera línea y una puesta en escena limpia y funcional, nos colocaron, sin ninguna duda, a la par de importantes eventos culturales organizados en diversas partes del mundo.");
                */
    }


}
