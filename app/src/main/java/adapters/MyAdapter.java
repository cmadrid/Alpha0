package adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import salonmachala.org.salonmachala.MainActivity;
import salonmachala.org.salonmachala.ObraActivity;
import salonmachala.org.salonmachala.R;

/**
 * Created by ces_m on 5/27/2016.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<Pair<String,Bitmap>> mDataset;
    private int nColumnas;

    String nombre_obra;
    String creada_obra;
    String descripcion_obra;
    String tecnica_obra;
    String dimensiones_obra;
    int foto_obra;

    String nombre_artista;
    String bibliografia_artista;
    String edad_artista;
    String nacionalidad_artista;
    int foto_artista;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView nombre;
        public ImageView foto;
        public FrameLayout linear;

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
    public MyAdapter(ArrayList<Pair<String,Bitmap>> myDataset, int nColumnas) {
        mDataset = myDataset;
        this.nColumnas = nColumnas;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_artista, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Pair<String,Bitmap> data = mDataset.get(position);

        holder.nombre.setText(data.first);
        holder.foto.setImageBitmap(data.second);

        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llenarGioconda();

                Intent intent = new Intent(MainActivity.mainActivity, ObraActivity.class);
                Bundle b = new Bundle();

                b.putString("nombre_artista", nombre_artista);
                b.putString("nombre_obra", nombre_obra);
                b.putString("creada_obra", creada_obra);
                b.putString("descripcion_obra", descripcion_obra);
                b.putString("tecnica_obra", tecnica_obra);
                b.putString("dimensiones_obra", dimensiones_obra);
                b.putInt("foto_obra", foto_obra);

                intent.putExtras(b);
                MainActivity.mainActivity.startActivity(intent);
            }
        });

        Display display = MainActivity.mainActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        System.out.println(width);
        System.out.println(height);
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




    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public void llenarGioconda(){
        nombre_obra =("La Gioconda.");
        creada_obra =("1503-1519");
        foto_obra =(R.drawable.gioconda);
        tecnica_obra ="óleo sobre tabla";
        dimensiones_obra ="116 cm × 191 cm";
        descripcion_obra =("La Gioconda (La Joconde en francés), también conocida como La Mona Lisa, es una obra pictórica del pintor renacentista italiano Leonardo da Vinci. Fue adquirida por el rey Francisco I de Francia a principios del siglo XVI y desde entonces es propiedad del Estado Francés. Se exhibe en el Museo del Louvre de París.\n" +
                "\n" +
                "Su nombre, La Gioconda (la alegre, en castellano), deriva de la tesis más aceptada acerca de la identidad de la modelo: la esposa de Francesco Bartolomeo de Giocondo, que realmente se llamaba Lisa Gherardini, de donde viene su otro nombre: Mona (señora, del italiano antiguo) Lisa.\n" +
                "\n" +
                "Es un óleo sobre tabla de álamo de 77 × 53 cm, pintado entre 1503 y 1519, y retocado varias veces por el nacionalidad_artista. Se considera el ejemplo más logrado de sfumato, técnica muy característica de Leonardo, si bien actualmente su colorido original es menos perceptible por el oscurecimiento de los barnices. El cuadro está protegido por múltiples sistemas de seguridad y ambientado a temperatura estable para su preservación óptima. Es revisado constantemente para verificar y prevenir su deterioro.\n" +
                "\n" +
                "Por medio de estudios históricos se ha determinado que la modelo podría ser una vecina de Leonardo, que podrían conocerse sus descendientes y que la modelo podría haber estado embarazada. Pese a todas las suposiciones, las respuestas en firme a los varios interrogantes en torno a la obra de arte resultan francamente insuficientes, lo cual genera más curiosidad entre los admiradores del cuadro.\n" +
                "\n" +
                "La fama de esta pintura no se basa únicamente en la técnica empleada o en su belleza, sino también en los misterios que la rodean. Además, el robo que sufrió en 1911, las reproducciones realizadas, las múltiples obras de arte que se han inspirado en el cuadro y las parodias existentes contribuyen a convertir a La Gioconda en el cuadro más famoso del mundo, visitado por millones de personas anualmente.");

        nombre_artista =("Leonardo da Vinci.");
        foto_artista =R.drawable.da_vinci;
        edad_artista = "564";
        nacionalidad_artista="República de Florencia";
        bibliografia_artista="Leonardo <a href=\"http://www.w3schools.com/html/\">da</a> Vinci (Leonardo di ser Piero da Vinci Loudspeaker.svg escuchar) (nacido el 15 de abril de 1452 en Vinci2 y fallecido el 2 de mayo de 1519 en Amboise) fue un polímata florentino del Renacimiento italiano. Fue a la vez pintor, anatomista, arquitecto, paleontólogo,3 artista, botánico, científico, escritor, escultor, filósofo, ingeniero, inventor, músico, poeta y urbanista. Murió acompañado de su fiel Francesco Melzi, a quien legó sus proyectos, diseños y pinturas.2 Tras pasar su infancia en su ciudad natal, Leonardo estudió con el célebre pintor florentino Andrea de Verrocchio. Sus primeros trabajos de importancia fueron creados en Milán al servicio del duque Ludovico Sforza. Trabajó a continuación en Roma, Bolonia y Venecia, y pasó los últimos años de su vida en Francia, por invitación del rey Francisco I.\n" +
                "\n" +
                "Frecuentemente descrito como un arquetipo y símbolo del hombre del Renacimiento, genio universal, además de filósofo humanista cuya curiosidad infinita solo puede ser equiparable a su capacidad inventiva,4 Leonardo da Vinci es considerado como uno de los más grandes pintores de todos los tiempos y, probablemente, es la persona con el mayor número de talentos en múltiples disciplinas que jamás ha existido.5 Como ingeniero e inventor, Leonardo desarrolló ideas muy adelantadas a su tiempo, tales como el helicóptero, el carro de combate, el submarino y el automóvil. Muy pocos de sus proyectos llegaron a construirse (entre ellos la máquina para medir el límite elástico de un cable),Nota 2 puesto que la mayoría no eran realizables durante esa época.Nota 3 Como científico, Leonardo da Vinci hizo progresar mucho el conocimiento en las áreas de anatomía, la ingeniería civil, la óptica y la hidrodinámica.\n" +
                "\n" +
                "Su asociación histórica más famosa es la pintura, siendo dos de sus obras más célebres, La Gioconda y La Última Cena, copiadas y parodiadas en varias ocasiones, al igual que su dibujo del Hombre de Vitruvio, que llegaría a ser retomado en numerosos trabajos derivados. No obstante, únicamente se conocen alrededor de 20 obras suyas, debido principalmente a sus constantes (y a veces desastrosos) experimentos con nuevas técnicas y a su inconstancia crónica.Nota 4 Este reducido número de creaciones, junto con sus cuadernos que contienen dibujos, diagramas científicos y reflexiones sobre la naturaleza de la pintura, constituyen un legado para las sucesivas generaciones de artistas, llegando a ser igualado únicamente por Miguel Ángel.";
    }

}