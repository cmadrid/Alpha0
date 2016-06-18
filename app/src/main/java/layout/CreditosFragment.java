package layout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lazyLoad.ImageLoader;
import salonmachala.org.salonmachala.AbrirImagen;
import salonmachala.org.salonmachala.Global;
import salonmachala.org.salonmachala.MainActivity;
import salonmachala.org.salonmachala.NestedWebView;
import salonmachala.org.salonmachala.R;
import widget.JustifiedTextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreditosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreditosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreditosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    NestedWebView wv_creditos;

    private OnFragmentInteractionListener mListener;

    public CreditosFragment() {
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
    public static CreditosFragment newInstance(String param1, String param2) {
        CreditosFragment fragment = new CreditosFragment();
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
        View view = inflater.inflate(R.layout.fragment_creditos, container, false);
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
        wv_creditos = (NestedWebView) view.findViewById(R.id.wv_creditos);
        wv_creditos.setTextColor(Color.BLACK);
        wv_creditos.setTextSize(17);

        Global.llenaInformacion("cdg_creditos",wv_creditos);
/*
        wv_creditos.setText("<strong>Director</strong>\n" +
                "\n" +
                "Enrique Madrid (Machala - Ecuador, 1964)\n" +
                "\n" +
                "Enrique Madrid estudió en la Escuela de Artes de la Universidad Central del Ecuador (Quito), y fue alumno de la crítica y curadora cubana Guadalupe Álvarez, en La Habana. Del 2000 al 2009 vivió en Berkeley (California), donde realizó estudios de figura humana, acuarela y grabado. En 2005 hizo un viaje de investigación artística por algunos países de Europa. Fue miembro fundador y coordinador de proyectos del CEAC (Centro Ecuatoriano de Arte Contemporáneo). Entre 1995 y 2000 fue representado por La Galería de Quito. Como parte de delegaciones ecuatorianas ha expuesto en la Casa de las Américas (La Habana), en el Instituto Ítalo-Latinoamericano de Cultura (Roma), en la exposición Latin-American Contemporary Art (Providence, Rhode Island), entre otras. Ha obtenido el premio “Coloma Silva” a la mejor obra en la exposición de los egresados de la Escuela de Artes; fue premiado en el Primer Salón de Pintores Nóveles en Quito (con un jurado integrado por Kingman y Guayasamín); tuvo una mención especial y el primer premio en dos convocatorias del Salón Nacional de Acuarela “Pedro León”; en 1994 ganó el Salón Nacional de Pintura “Luis A. Martínez” y la primera mención en el Salón “Mariano Aguilera”. En 2014 la Casa de la Cultura Ecuatoriana “Benjamín Carrión” Núcleo de El Oro lo distinguió como “Pintor del Año” e inauguró en Machala y en la Casa de la Cultura Ecuatoriana “Benjamín Carrión” Núcleo del Azuay la muestra panorámica de su obra Tropicalismos. Actualmente reside en Machala, donde alterna su trabajo de taller con la Dirección del Salón Machala, junto a otras actividades de gestión cultural como la creación del CDAV (Centro para el Desarrollo de las Artes Visuales) y la Escuela de Artes Visuales E. Madrid. En 2012 fue seleccionado por el Banco Promerica para su ciclo de catálogos anuales, con la publicación del libro La Erosión del Sentido. En el 2015 representa al Ecuador en la residencia internacional de artistas Zaruma- Portovelo que aborda temas sobre el paisaje, el agua y el impacto de la minería en el medio.  Además, en el mismo  año fue seleccionado para asistir al segundo encuentro Internacional de Educación Artística y Buenas Prácticas, organizado por la Organización de Estados Iberoamericanos, ArtEducarte y  Fundación Tinkuy, Quito.\n" +
                "\n" +
                "<strong>Asistente</strong>\n" +
                "\n" +
                "Stefany Guzmán Morales (Machala - Ecuador, 1991) \n" +
                "\n" +
                "Se destacó a nivel académico mientras realizaba sus estudios superiores en Artes Visuales, etapa en la que incursionó en el teatro, la gestión cultural y la labor social, organizando y coordinando festivales y exposiciones independientes de arte que convocaron a artistas del país y de Suramérica. Tomó varios cursos de dibujo, pintura y grabado y en el 2012 recibió un reconocimiento por dictar cursos gratuitos a adolescentes con capacidades especiales. \n" +
                "\n" +
                "Perteneció al taller de Creatividad Literaria dirigido por el reconocido poeta Roy Sigüenza y participó en el I Encuentro Nacional de Poesía en homenaje al poeta Lauro Dávila Echeverría. Fue invitada a intervenir con un performance en la premiación del Festival de Poesía Poetics, en el que debutó como performer con Ablación, 2013 y al año siguiente realizó el performance In-hive a partir de la obra del artista Enrique Madrid en la Casa de la Cultura Benjamín Carrión Núcleo del Azuay - Cuenca y Núcleo de El Oro - Machala. En el 2015 fue seleccionada para asistir al segundo encuentro Internacional de Educación Artística y Buenas Prácticas, organizado por la Organización de Estados Iberoamericanos, ArtEducarte y  Fundación Tinkuy, Quito.\n" +
                "\n" +
                "Fue jurado calificador en varios concursos locales de dibujo y pintura organizados por instituciones públicas y educativas. \n" +
                "\n" +
                "En 2015, estuvo vinculada en un proyecto internacional de residencia artística, sobre el impacto de la minería en Portovelo y Zaruma.\n");


        //ImageLoader imageLoader = new ImageLoader(MainActivity.mainActivity,true);
        //imageLoader.DisplayImage("http://puntoec.org/json/recursos/creditos/enrique_stefany.jpg",MainActivity.mainActivity.header);


        MainActivity.mainActivity.header.setImageResource(R.drawable.enrique_stefany);
        Global.openImageView(MainActivity.mainActivity.header,null,R.drawable.enrique_stefany);*/
    }
}
