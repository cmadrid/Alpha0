package layout;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;

import java.util.ArrayList;

import adapters.DataPassObject;
import adapters.MyAdapter;
import database.DBParticipante;
import salonmachala.org.salonmachala.MainActivity;
import salonmachala.org.salonmachala.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ArtistasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ArtistasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArtistasFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int nColumnas = 2;
    RecyclerView recyclerView;
    boolean seguir = true;
    String tipo = "PA";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ArtistasFragment() {
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
    public static ArtistasFragment newInstance(String param1, String param2) {
        ArtistasFragment fragment = new ArtistasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        fragment.tipo = param1;
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

        View view = inflater.inflate(R.layout.fragment_artistas, container, false);
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
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_artistas);
        //recyclerView.addItemDecoration(new MarginDecoration(this));
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), nColumnas));
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        new GetDataParticipantes().execute();

    }

    public void detener(){
        seguir = false;
    }

    private class GetDataParticipantes extends AsyncTask<String,String,String>{

        ArrayList<DataPassObject> myDataset = new ArrayList<>();
        MyAdapter adapter = new MyAdapter(myDataset,nColumnas);
        @Override
        protected String doInBackground(String... params) {
            DBParticipante db_participantes = null;
            try {

                db_participantes = new DBParticipante(getActivity());
                Cursor c = db_participantes.consultarArtistas(null,tipo);
                if(c.moveToFirst()) {
                    do {
                        myDataset.add(new DataPassObject(c.getInt(0),c.getString(1), c.getString(4), DataPassObject.PARTICIPANTE));

                        publishProgress();
                    } while (c.moveToNext() && seguir);
                }

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(db_participantes!=null)
                    db_participantes.close();
            }

            //recyclerView.setAdapter(new NumberedAdapter(30));

            return null;
        }

        @Override
        protected void onPreExecute() {
            MainActivity.progressWheel.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(adapter);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            adapter.notifyDataSetChanged();
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            MainActivity.progressWheel.setVisibility(View.GONE);
            MainActivity.mainActivity.appBarLayout.setExpanded(false);
            //recyclerView.setAdapter(adapter);
            super.onPostExecute(s);
        }
    }
}
