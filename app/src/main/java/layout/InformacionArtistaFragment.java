package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import lazyLoad.ImageLoader;
import salonmachala.org.salonmachala.Global;
import salonmachala.org.salonmachala.MainActivity;
import salonmachala.org.salonmachala.R;
import widget.JustifiedTextView;

/**
 * Created by ces_m on 7/4/2016.
 */
public class InformacionArtistaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private int nColumnas = 2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;


    TextView tv_nombre;
    TextView tv_edad;
    TextView tv_nacionalidad;
    ImageView iv_foto;

    public ImageLoader imageLoader;

    private OnFragmentInteractionListener mListener;

    public InformacionArtistaFragment() {
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
    public static InformacionArtistaFragment newInstance(String param1, String param2, String param3, String param4) {
        InformacionArtistaFragment fragment = new InformacionArtistaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
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
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_informacion_artista, container, false);
        init(view);
        setInfo(mParam1,mParam2,mParam3,mParam4);
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
        imageLoader = new ImageLoader(getActivity().getApplicationContext(),true);
        tv_nombre = (TextView) view.findViewById(R.id.tv_nombre);
        tv_edad = (TextView) view.findViewById(R.id.tv_edad);
        tv_nacionalidad = (TextView) view.findViewById(R.id.tv_nacionalidad);
        iv_foto = (ImageView) view.findViewById(R.id.iv_foto);

    }

    public void setInfo(String nombre,String nacionalidad,String edad, String foto){
        System.out.println("test info");
        tv_nombre.setText(nombre);
        tv_nacionalidad.setText(nacionalidad);

        tv_edad.setText(edad);
        imageLoader.DisplayImage(foto,iv_foto);
        Global.openImageView(iv_foto,tv_nombre,foto,null);

    }

}
