package layout;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import adapters.DataPassObject;
import adapters.MyAdapter;
import database.DBParticipante;
import salonmachala.org.salonmachala.Global;
import salonmachala.org.salonmachala.MainActivity;
import salonmachala.org.salonmachala.R;
import widget.JustifiedTextView;

/**
 * Created by ces_m on 7/4/2016.
 */
public class DescripcionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    JustifiedTextView wv_descripcion;

    // TODO: Rename and change types of parameters
    private String mParam1;

    private OnFragmentInteractionListener mListener;

    public DescripcionFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DescripcionFragment newInstance(String param1) {
        DescripcionFragment fragment = new DescripcionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_descripcion, container, false);
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void init(View view){
        wv_descripcion = (JustifiedTextView) view.findViewById(R.id.wv_descripcion);

        wv_descripcion.setTextColor(Color.BLACK);
        wv_descripcion.setPadding(10,0,10,0);


        wv_descripcion.setTextSize(Global.getSizeWv());
        MainActivity.mainActivity.appBarLayout.setExpanded(false);
        setInfo(mParam1);

    }
    public void setInfo(String descripcion){
        wv_descripcion.setText(descripcion);
    }

}
