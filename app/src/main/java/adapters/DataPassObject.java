package adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;

import java.io.ByteArrayOutputStream;

/**
 * Created by ces_m on 6/6/2016.
 */
public class DataPassObject {
    String Nombre;
    String foto_url;
    Bitmap foto;
    int id;
    int tipo; //1 participante //2 obra
    public static int PARTICIPANTE = 1;
    public static int OBRA = 2;

    public DataPassObject(int id, String nombre, Bitmap foto, int tipo) {
        Nombre = nombre;
        this.foto = foto;
        this.id = id;
        this.tipo = tipo;
    }
    public DataPassObject(int id, String nombre, String foto_url, int tipo) {
        Nombre = nombre;
        this.foto_url = foto_url;
        this.id = id;
        this.tipo = tipo;
    }

    public String getFoto_url() {
        return foto_url;
    }

    public void setFoto_url(String foto_url) {
        this.foto_url = foto_url;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Bitmap getThumbnail(){

        final int THUMBNAIL_SIZE = 64;
        Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(foto, THUMBNAIL_SIZE, THUMBNAIL_SIZE);
        return ThumbImage;
    }
}
