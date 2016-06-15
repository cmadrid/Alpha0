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
import salonmachala.org.salonmachala.ArtistaActivity;
import salonmachala.org.salonmachala.MainActivity;
import salonmachala.org.salonmachala.ObraActivity;
import salonmachala.org.salonmachala.R;

/**
 * Created by ces_m on 5/27/2016.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<DataPassObject> mDataset;
    private int nColumnas;

    public ImageLoader imageLoader;
    // Allows to remember the last item shown on screen
    private int lastPosition = -1;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView nombre;
        public ImageView foto;
        public FrameLayout linear;
        public int id;
        public int tipo;

        public ViewHolder(View v) {
            super(v);
            nombre = (TextView) v.findViewById(R.id.tv_autor);
            foto = (ImageView) v.findViewById(R.id.iv_foto_autor);
            linear = (FrameLayout) v.findViewById(R.id.linear_autor);
        }
    }

/*
    public void remove(String item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }
*/
    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<DataPassObject> myDataset, int nColumnas) {
        mDataset = myDataset;
        this.nColumnas = nColumnas;
        imageLoader = new ImageLoader(MainActivity.mainActivity.getApplicationContext(),false);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final DataPassObject data = mDataset.get(position);

        holder.nombre.setText(data.getNombre());
        if(data.getFoto()!=null)
            holder.foto.setImageBitmap(data.getFoto());
        else
            imageLoader.DisplayImage(data.getFoto_url(),holder.foto);

        final int tipo = data.getTipo();
        final int id = data.getId();
        //final finalHolder
        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.mainActivity,
                        (tipo==DataPassObject.PARTICIPANTE)? ArtistaActivity.class:
                        (tipo==DataPassObject.OBRA)?ObraActivity.class:null);
                Bundle b = new Bundle();

                b.putInt("id",id);
                intent.putExtras(b);
                MainActivity.mainActivity.startActivity(intent);
            }
        });

        Display display = MainActivity.mainActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        //int height = size.y;

        //System.out.println(width);
        //System.out.println(height);
        ViewGroup.LayoutParams params = holder.linear.getLayoutParams();


// Changes the height and width to the specified *pixels*
        params.height = (width/(nColumnas+1))+(width/5);
        params.width = (width/nColumnas);
        //holder.linear.setLayoutParams(params);
        //System.out.println("cambia de esto "+width +" a esto "+135 );


        android.view.ViewGroup.LayoutParams layoutParams = holder.foto.getLayoutParams();
        //layoutParams.width = (width/nColumnas)-20;
        layoutParams.height = width/(nColumnas+1);
        //holder.foto.setLayoutParams(layoutParams);



        //holder.foto.setMaxHeight(width/3);
        //holder.foto.setMinimumHeight(width/3);
        //holder.foto.setMaxHeight(holder.foto.getWidth());


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
}