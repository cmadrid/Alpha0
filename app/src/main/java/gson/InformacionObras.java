package gson;

import java.util.ArrayList;

/**
 * Created by ces_m on 6/5/2016.
 */
public class InformacionObras {

    int code;
    String tipo_request;
    String message;
    String actualizacion;
    ArrayList<Obra> data;

    public int getCode() {
        return code;
    }

    public ArrayList<Obra> getData() {
        return data;
    }


    public String getActualizacion() {
        return actualizacion;
    }
}