package salonmachala.org.salonmachala;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by ces_m on 5/15/2016.
 */
public class SimpleScannerActivity extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    final int MY_PERMISSIONS_REQUEST_CAMERA = 12345;
    Activity simple = this;

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

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view

        List<BarcodeFormat> list = new ArrayList<>();
        list.add(BarcodeFormat.QR_CODE);

        mScannerView.setFormats(list);

    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        //Log.v(TAG, rawResult.getText()); // Prints scan results
        //Log.v(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
        System.out.println("raw: "+rawResult.getText());
        System.out.println("bc: "+rawResult.getBarcodeFormat().toString());

/*
        Intent intent = new Intent(simple, ObraActivity.class);
        Bundle b = new Bundle();
        b.putString("key", rawResult.getText()); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
*/


        switch (rawResult.getText().toLowerCase()) {
            case "gioconda":
                llenarGioconda();break;
            case "ultimacena":
                llenaUltimaCena();break;
            case "comepatatas":
                llenaComePatatas();break;
            default:
                Toast.makeText(SimpleScannerActivity.this, "No se encontro información de obra en ese código.", Toast.LENGTH_SHORT).show();
                finish();
                return;
        }


        openDialog();
        finish();

    }

    private void openDialog(){
        LayoutInflater inflater = MainActivity.mainActivity.getLayoutInflater();
        //View view = inflater.inflate(R.layout.dialog_autor_obra, null);
        View view = inflater.inflate(R.layout.dialog_autor_obra2, null);
        ImageButton ib_autor = (ImageButton) view.findViewById(R.id.ib_autor);
        ImageButton ib_obra = (ImageButton) view.findViewById(R.id.ib_obra);
        TextView tv_autor = (TextView) view.findViewById(R.id.tv_nombre_autor);
        TextView tv_obra = (TextView) view.findViewById(R.id.tv_nombre_obra);
        TextView tv_titulo = (TextView) view.findViewById(R.id.titulo_dialogo);

        //tv_obra.setText(nombre_obra);
        tv_obra.setText(R.string.argumento);
        tv_titulo.setText(nombre_obra);

        tv_autor.setText(nombre_artista);
        ib_autor.setImageResource(foto_artista);
        ib_obra.setImageResource(foto_obra);


        ib_autor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(simple, ArtistaActivity.class);
                Bundle b = new Bundle();
                b.putString("nacionalidad_artista", nacionalidad_artista);
                b.putString("nombre_artista", nombre_artista);
                b.putString("edad_artista", edad_artista);
                b.putString("bibliografia_artista", bibliografia_artista);
                b.putInt("foto_artista", foto_artista);

                intent.putExtras(b);
                startActivity(intent);
            }
        });
        ib_obra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(simple, ObraActivity.class);
                Bundle b = new Bundle();

                b.putString("nombre_artista", nombre_artista);
                b.putString("nombre_obra", nombre_obra);
                b.putString("creada_obra", creada_obra);
                b.putString("descripcion_obra", descripcion_obra);
                b.putString("tecnica_obra", tecnica_obra);
                b.putString("dimensiones_obra", dimensiones_obra);
                b.putInt("foto_obra", foto_obra);

                intent.putExtras(b);
                startActivity(intent);
            }
        });

        new AlertDialog.Builder(MainActivity.mainActivity).setView(view)
                .setCancelable(true)
                .show();
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

    public void llenaUltimaCena(){
        nombre_obra =("La última cena.");
        creada_obra =("1495-1497");
        foto_obra =(R.drawable.ultima_cena);
        tecnica_obra ="óleo sobre tabla";
        dimensiones_obra ="77 cm × 53 cm";
        descripcion_obra =("La última cena (en italiano: Il cenacolo o L’ultima cena) es una pintura mural original de Leonardo da Vinci ejecutada entre 1495 y 1497, se encuentra en la pared sobre la que se pintó originariamente, en el refectorio del convento dominico de Santa Maria delle Grazie en Milán (Italia), declarado Patrimonio de la Humanidad por la Unesco en 1980. La pintura fue elaborada, para su patrón, el duque Ludovico Sforza de Milán. No es un fresco tradicional, sino un mural ejecutado al temple y óleo sobre dos capas de preparación de yeso extendidas sobre enlucido. Mide 460 cm de alto por 880 cm de ancho. Muchos expertos e historiadores del arte, consideran a La última cena como una de las mejores obras pictóricas del mundo.");

        nombre_artista =("Leonardo da Vinci.");
        foto_artista =R.drawable.da_vinci;
        edad_artista = "564";
        nacionalidad_artista="República de Florencia";
        bibliografia_artista="Leonardo da Vinci (Leonardo di ser Piero da Vinci Loudspeaker.svg escuchar) (nacido el 15 de abril de 1452 en Vinci2 y fallecido el 2 de mayo de 1519 en Amboise) fue un polímata florentino del Renacimiento italiano. Fue a la vez pintor, anatomista, arquitecto, paleontólogo,3 artista, botánico, científico, escritor, escultor, filósofo, ingeniero, inventor, músico, poeta y urbanista. Murió acompañado de su fiel Francesco Melzi, a quien legó sus proyectos, diseños y pinturas.2 Tras pasar su infancia en su ciudad natal, Leonardo estudió con el célebre pintor florentino Andrea de Verrocchio. Sus primeros trabajos de importancia fueron creados en Milán al servicio del duque Ludovico Sforza. Trabajó a continuación en Roma, Bolonia y Venecia, y pasó los últimos años de su vida en Francia, por invitación del rey Francisco I.\n" +
                "\n" +
                "Frecuentemente descrito como un arquetipo y símbolo del hombre del Renacimiento, genio universal, además de filósofo humanista cuya curiosidad infinita solo puede ser equiparable a su capacidad inventiva,4 Leonardo da Vinci es considerado como uno de los más grandes pintores de todos los tiempos y, probablemente, es la persona con el mayor número de talentos en múltiples disciplinas que jamás ha existido.5 Como ingeniero e inventor, Leonardo desarrolló ideas muy adelantadas a su tiempo, tales como el helicóptero, el carro de combate, el submarino y el automóvil. Muy pocos de sus proyectos llegaron a construirse (entre ellos la máquina para medir el límite elástico de un cable),Nota 2 puesto que la mayoría no eran realizables durante esa época.Nota 3 Como científico, Leonardo da Vinci hizo progresar mucho el conocimiento en las áreas de anatomía, la ingeniería civil, la óptica y la hidrodinámica.\n" +
                "\n" +
                "Su asociación histórica más famosa es la pintura, siendo dos de sus obras más célebres, La Gioconda y La Última Cena, copiadas y parodiadas en varias ocasiones, al igual que su dibujo del Hombre de Vitruvio, que llegaría a ser retomado en numerosos trabajos derivados. No obstante, únicamente se conocen alrededor de 20 obras suyas, debido principalmente a sus constantes (y a veces desastrosos) experimentos con nuevas técnicas y a su inconstancia crónica.Nota 4 Este reducido número de creaciones, junto con sus cuadernos que contienen dibujos, diagramas científicos y reflexiones sobre la naturaleza de la pintura, constituyen un legado para las sucesivas generaciones de artistas, llegando a ser igualado únicamente por Miguel Ángel.";
    }

    public void llenaComePatatas(){
        nombre_obra =("Los comedores de patatas.");
        creada_obra =("1885");
        foto_obra =(R.drawable.come_patata);
        tecnica_obra ="pintura al óleo";
        dimensiones_obra ="82 cm × 114 cm";
        descripcion_obra =("Los comedores de patatas, Los comedores de papa o Los campesinos comiendo patatas (Aardappeleters en flamenco) es un cuadro del pintor Vincent van Gogh, que creó en abril de 1885 mientras residía en Nuenen, Países Bajos. Se encuentra en el Museo Van Gogh de Ámsterdam. La versión que se encuentra en el Museo Kröller-Müller de la ciudad de Otterlo es un bosquejo preliminar en óleo.Los comedores de patatas, Los comedores de papa o Los campesinos comiendo patatas (Aardappeleters en flamenco) es un cuadro del pintor Vincent van Gogh, que creó en abril de 1885 mientras residía en Nuenen, Países Bajos. Se encuentra en el Museo Van Gogh de Ámsterdam. La versión que se encuentra en el Museo Kröller-Müller de la ciudad de Otterlo es un bosquejo preliminar en óleo.");

        nombre_artista =("Vincent van Gogh.");
        foto_artista =R.drawable.van_gogh;
        edad_artista = "163";
        nacionalidad_artista="Países Bajos";
        bibliografia_artista="Vincent Willem van Gogh (en neerlandés Acerca de este sonido Vincent van Gogh (?·i) [ˈvɪnsɛnt fɑn'xɔx]) (Zundert, Países Bajos, 30 de marzo de 1853-Auvers-sur-Oise, Francia, 29 de julio de 1890) fue un pintor neerlandés, uno de los principales exponentes del postimpresionismo.\n" +
                "\n" +
                "Pintó unos 900 cuadros (entre ellos 27 autorretratos y 148 acuarelas) y realizó más de 1600 dibujos. Una figura central en su vida fue su hermano menor Theo, marchante de arte en París, quien le prestó apoyo financiero de manera continua y desinteresada. La gran amistad entre ellos está documentada en las numerosas cartas que se intercambiaron desde agosto de 1872. De las 800 cartas que se conservan del pintor, unas 650 eran para Theo, las otras son correspondencia con amigos y familiares.2\n" +
                "\n" +
                "A pesar que desde muy joven tuvo inclinación hacia el dibujo, su primer trabajo fue en una galería de arte. Más tarde se convirtió en pastor protestante y en 1879, a la edad_artista de 26 años, se marchó como misionero a una región minera de Bélgica, donde comenzó a dibujar a la gente de la comunidad local. En 1885 pintó su primera gran obra Los comedores de patatas. En ese momento su paleta se componía principalmente de tonos sombríos terrosos. La luz de colores vivos por la que es conocido surgió en obras posteriores, cuando se trasladó al sur de Francia, consiguiendo su plenitud durante su estancia en Arlés en 1888. La calidad de su obra sólo fue reconocida después de su muerte, en una exposición retrospectiva en 1890, considerándose en la actualidad uno de los grandes maestros de la pintura. Tuvo una gran influencia en el arte del siglo XX, especialmente entre los expresionistas alemanes y los fauvistas como Derain, Vlaminck y Kees Van Dongen.3 4 Falleció a los 37 años por una herida de bala de pistola; aún no se sabe con seguridad si fue un suicidio o un asesinato accidental. A pesar de que existe una tendencia general a especular que su enfermedad mental influyese en su pintura, el crítico de arte Robert Hughes cree que las obras del artista están ejecutadas bajo un completo control,5 de hecho, el pintor jamás trabajó en los periodos en los que estaba enfermo.";
    }

}
