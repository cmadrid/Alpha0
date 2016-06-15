package layout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import salonmachala.org.salonmachala.AbrirImagen;
import salonmachala.org.salonmachala.MainActivity;
import salonmachala.org.salonmachala.R;
import widget.JustifiedTextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProyeccionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProyeccionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProyeccionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView tv_proyeccion;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProyeccionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProyeccionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProyeccionFragment newInstance(String param1, String param2) {
        ProyeccionFragment fragment = new ProyeccionFragment();
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
        View view = inflater.inflate(R.layout.fragment_proyeccion, container, false);
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
        tv_proyeccion = (TextView) view.findViewById(R.id.tv_proyeccion);
        tv_proyeccion.setMovementMethod(new ScrollingMovementMethod());
        tv_proyeccion.setText("El Salón Machala cumple con los parámetros internacionales de calidad, protocolo e intercambio cultural. Esta propuesta cultural está diseñada para trascender a nivel internacional, más aun ahora, que la reformulación de todo el evento nos ha permitido integrar alrededor del concurso de artes visuales sobre soporte bidimensional, otras opciones de lenguajes artísticos que enriquecen el evento y las referencias de la cultura visual en nuestra comunidad.\n" +
                "\n" +
                "El Salón Machala ha sido concebido como una propuesta cultural que abarca una serie de actividades dentro de la cultura artística y apunta a persuadir al espectador a sumergirse en una experiencia cultural que va desde conferencias magistrales, laboratorios de creación comunitarios, pasando por un complejo programa de curaduría pedagógica que creará puentes entre el público, los artistas y la producción artística expuesta y fortalecerá los vínculos entre la comunidad y el salón.\n" +
                "\n" +
                "El firme apoyo que nuestro evento recibe desde la Alcaldía de Machala, nos permite mantener y nutrir la programación anterior e insertar un novedoso programa de curaduría pedagógica y mediación artística, que es una adenda complementaria necesaria, por no decir urgente, en nuestro contexto cultural. Esta sección responde a una necesidad de estrategias educativas alrededor del salón que indudablemente tendrá un gran impacto en la comunidad en la manera de ver y abordar las relaciones entre las instituciones culturales y el consumidor cultural. La acción de la curaduría pedagógica expande el salón hacia la comunidad y le confiere más sentido a este importante hecho cultural, pues uno de los objetivos es proveer a la comunidad un abanico de posibilidades interactivas y de educación, que propicien acercamientos que fortalezcan un sentido de pertenencia y pertinencia con relación a este gran evento en la ciudad de Machala.\n" +
                "\n" +
                "La propuesta pondría al SALÓN MACHALA, a la altura de las corrientes más contemporáneas del pensamiento relacionadas con el rol del museo y las instituciones culturales dentro de la comunidad. Es compleja, ambiciosa y apunta a crear para el salón un brazo que extienda su actividad más allá de su espacio arquitectónico, para ampliar su radio de acción a los sectores más populares, los barrios y espacios urbanos cargados de significación histórica o de uso cotidiano en la ciudad de Machala.\n" +
                "\n" +
                "El programa de curaduría pedagógica fue diseñado y estará coordinado por Sara Madrid Arcos, B.A. and Teather Arts, Teaching Artist Program University of California, Los Ángeles (UCLA), quien ha conformado un equipo de trabajo caracterizado por la excelencia académica y vasta experiencia en el área, integrado por la artista y educadora Marcela Ormaza, MAT (Master in Art Education) del Massachusetts College of Art, Boston, MA.; Paola Viteri, Maestría en Estudios Culturales por la Universidad Andina Simón Bolívar, además de convenios de asesoramiento y colaboración con organizaciones especializadas en educación artística, con la coordinación local a cargo de Lilia García Carrión, artista emergente machaleña, egresada de la Escuela de Artes Visuales de la Universidad Técnica de Machala, UTMACH, involucrada en procesos de desarrollo de arte comunitario en nuestra ciudad.\n" +
                "\n" +
                "Mantenemos para esta nueva edición la categoría del Personaje Invitado Especial. En esta ocasión, hemos invitado al artista pintor y escritor norteamericano Virgil Elliott, un maestro de los procesos técnicos en la pintura, siguiendo el espíritu pedagógico de esta edición y lo reforzamos con el taller de dibujo de figura humana dictada por Eduardo Villacís, excelente artista figurativo ecuatoriano, profesor de la Escuela de Artes Visuales de la Universidad San Francisco de Quito.\n" +
                "\n" +
                "Para nutrir aún más este proceso de inmersión cultural, Marcio Tavares, curador brasileño y en esta edición Miembro del Jurado de los Premios Musa, nos ofrecerá una conferencia magistral sobre curadurías y exhibiciones como plataformas para producir conocimiento. Además, James Clover, artista norteamericano, Miembro del Jurado de los Premios Musa Paradisiaca, ofrecerá una conferencia, conversatorio y slide show para los artistas locales.\n" +
                "\n");

        //MainActivity.mainActivity.header.setImageResource(R.drawable.enrique_stefany);
        /*MainActivity.mainActivity.header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MainActivity.mainActivity.header.buildDrawingCache();
                Bitmap image= MainActivity.mainActivity.header.getDrawingCache();

                Intent intent = new Intent(MainActivity.mainActivity,AbrirImagen.class);
                Bundle extras = new Bundle();
                //extras.putParcelable("imagebitmap", image);
                extras.putInt("imageResource", R.drawable.enrique_stefany);
                extras.putString("title", "");
                intent.putExtras(extras);
                startActivity(intent);

            }
        });*/
    }
}
