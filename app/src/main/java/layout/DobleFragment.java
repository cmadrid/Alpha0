package layout;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import adapters.DataPassDoble;
import adapters.DobleAdapter;
import database.DBObra;
import salonmachala.org.salonmachala.Global;
import salonmachala.org.salonmachala.MainActivity;
import salonmachala.org.salonmachala.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DobleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DobleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DobleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int nColumnas = 2;
    RecyclerView recyclerView;
    boolean seguir = true;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DobleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PremiosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DobleFragment newInstance(String param1, String param2) {
        DobleFragment fragment = new DobleFragment();
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
        View view = inflater.inflate(R.layout.fragment_doble, container, false);
        view = init(view);
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

    private View init(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_doble);
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
        //recyclerView.addItemDecoration(new MarginDecoration(this));
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        new GetDataDoble().execute();

        if(this.getParentFragment()==null)
            Global.asignarFotoTitle("cdg_doble_"+mParam1.toLowerCase());

        return view;
    }
    public void detener(){
        seguir = false;
    }


    private class GetDataDoble extends AsyncTask<String,String,String> {

        HashMap<String, DataPassDoble> dpp = new HashMap<>();
        ArrayList<DataPassDoble> myDataset = new ArrayList<>();
        DobleAdapter adapter = new DobleAdapter(myDataset,nColumnas);

        public GetDataDoble(){
            seguir=true;
        }

        @Override
        protected String doInBackground(String... params) {

            DBObra db_obras = null;
            try {

                db_obras = new DBObra(getActivity());
                Cursor c = null;

                switch (mParam1)
                {
                    case "PR":
                        c = db_obras.consultarObrasPremios();
                        break;
                    case "EM":
                        c = db_obras.consultarExposicionMulti();
                        break;
                }

                if(c.moveToFirst()) {
                    do {
                        myDataset.add(new DataPassDoble(c.getInt(0),c.getInt(1), c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6)));
                        publishProgress();
                    } while (c.moveToNext() && seguir);
                }

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(db_obras!=null)
                    db_obras.close();
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
            //recyclerView.setAdapter(adapter);
            MainActivity.progressWheel.setVisibility(View.GONE);
            super.onPostExecute(s);
        }
    }

}
