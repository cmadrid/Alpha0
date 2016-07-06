package adapters;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import lazyLoad.ImageLoader;
import salonmachala.org.salonmachala.ArtistaActivityTabs;
import salonmachala.org.salonmachala.MainActivity;
import salonmachala.org.salonmachala.ObraActivity;
import salonmachala.org.salonmachala.ObrasActivityTabs;
import salonmachala.org.salonmachala.R;

/**
 * Created by ces_m on 5/27/2016.
 */

public class PremioAdapter extends RecyclerView.Adapter<PremioAdapter.ViewHolder> {
    private ArrayList<DataPassPremio> mDataset;
    private int nColumnas;

    public ImageLoader imageLoader;
    // Allows to remember the last item shown on screen
    private int lastPosition = -1;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tipo_premio;
        public TextView nombre_autor;
        public TextView nombre_obra;
        public ImageView foto_autor;
        public ImageView foto_obra;
        public FrameLayout linear;

        public ViewHolder(View v) {
            super(v);
            tipo_premio = (TextView) v.findViewById(R.id.tv_tipo_premio);
            nombre_autor = (TextView) v.findViewById(R.id.tv_autor_premio);
            nombre_obra = (TextView) v.findViewById(R.id.tv_obra_premio);
            foto_autor = (ImageView) v.findViewById(R.id.iv_autor_premio);
            foto_obra = (ImageView) v.findViewById(R.id.iv_obra_premio);

            linear = (FrameLayout) v.findViewById(R.id.linear_autor);
        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public PremioAdapter(ArrayList<DataPassPremio> myDataset, int nColumnas) {
        mDataset = myDataset;
        this.nColumnas = nColumnas;
        imageLoader = new ImageLoader(MainActivity.mainActivity.getApplicationContext(),false);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PremioAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_premio, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final DataPassPremio data = mDataset.get(position);

        holder.tipo_premio.setText(tipo_premio(data.getTipo_premio()));

        holder.nombre_autor.setText(data.getNombre_autor());
        holder.nombre_obra.setText(data.getNombre_obra());

        imageLoader.DisplayImage(data.getFoto_autor(),holder.foto_autor);
        imageLoader.DisplayImage(data.getFoto_obra(),holder.foto_obra);

        final int id_autor = data.getId_autor();
        final int id_obra = data.getId_obra();
        //final finalHolder
        holder.foto_autor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.mainActivity,ArtistaActivityTabs.class);
                Bundle b = new Bundle();

                b.putInt("id",id_autor);
                intent.putExtras(b);
                MainActivity.mainActivity.startActivity(intent);
            }
        });
        holder.foto_obra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.mainActivity,ObrasActivityTabs.class);
                Bundle b = new Bundle();

                b.putInt("id",id_obra);
                intent.putExtras(b);
                MainActivity.mainActivity.startActivity(intent);
            }
        });

        Display display = MainActivity.mainActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        //int height = size.y;

        ////System.out.println(width);
        ////System.out.println(height);
        ViewGroup.LayoutParams params = holder.linear.getLayoutParams();


// Changes the height and width to the specified *pixels*
        params.height = (width/(nColumnas+1))+(width/4);
        //params.width = (width/nColumnas);

        ViewGroup.LayoutParams layoutParams = holder.foto_autor.getLayoutParams();
        //layoutParams.width = (width/nColumnas)-20;
        layoutParams.height = width/(nColumnas+1);
        //holder.foto.setLayoutParams(layoutParams);


        ViewGroup.LayoutParams layoutParams2 = holder.foto_obra.getLayoutParams();
        //layoutParams.width = (width/nColumnas)-20;
        layoutParams2.height = width/(nColumnas+1);
        //holder.foto.setLayoutParams(layoutParams);


        //setAnimation(holder.linear, position);


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(MainActivity.mainActivity, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public String tipo_premio(String tipo_premio){
        String result = null;
        switch(tipo_premio){
            case "1_1P": result = MainActivity.mainActivity.getResources().getString(R.string.primer_puesto);
                break;
            case "2_2P": result = MainActivity.mainActivity.getResources().getString(R.string.segundo_puesto);
                break;
            case "3_3P": result = MainActivity.mainActivity.getResources().getString(R.string.tercer_puesto);
                break;
            case "4_AO": result = MainActivity.mainActivity.getResources().getString(R.string.artista_orense);
                break;
            case "5_1M": result = MainActivity.mainActivity.getResources().getString(R.string.primera_mencion);
                break;
            case "6_2M": result = MainActivity.mainActivity.getResources().getString(R.string.segunda_mencion);
                break;
            case "7_3M": result = MainActivity.mainActivity.getResources().getString(R.string.tercera_mencion);
                break;
            case "8_4M": result = MainActivity.mainActivity.getResources().getString(R.string.cuarta_mencion);
                break;
        }
        return result;
    }
}