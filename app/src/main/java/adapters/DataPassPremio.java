package adapters;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;

/**
 * Created by ces_m on 6/6/2016.
 */
public class DataPassPremio {
    String nombre_autor;
    String nombre_obra;
    String foto_autor;
    String foto_obra;
    int id_autor;
    int id_obra;
    String tipo_premio;

    public DataPassPremio(int id_autor, int id_obra, String nombre_autor, String nombre_obra, String foto_autor, String foto_obra, String premio) {
        this.nombre_autor = nombre_autor;
        this.nombre_obra = nombre_obra;
        this.foto_autor = foto_autor;
        this.foto_obra = foto_obra;
        this.id_autor = id_autor;
        this.id_obra = id_obra;
        this.tipo_premio = premio;
    }

    public String getNombre_autor() {
        return nombre_autor;
    }

    public void setNombre_autor(String nombre_autor) {
        this.nombre_autor = nombre_autor;
    }

    public String getNombre_obra() {
        return nombre_obra;
    }

    public void setNombre_obra(String nombre_obra) {
        this.nombre_obra = nombre_obra;
    }

    public String getFoto_autor() {
        return foto_autor;
    }

    public void setFoto_autor(String foto_autor) {
        this.foto_autor = foto_autor;
    }

    public String getFoto_obra() {
        return foto_obra;
    }

    public void setFoto_obra(String foto_obra) {
        this.foto_obra = foto_obra;
    }

    public int getId_autor() {
        return id_autor;
    }

    public void setId_autor(int id_autor) {
        this.id_autor = id_autor;
    }

    public int getId_obra() {
        return id_obra;
    }

    public void setId_obra(int id_obra) {
        this.id_obra = id_obra;
    }

    public String getTipo_premio() {
        return tipo_premio;
    }

    public void setTipo_premio(String tipo_premio) {
        this.tipo_premio = tipo_premio;
    }
}
