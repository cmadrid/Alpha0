package gson;

import java.util.ArrayList;

/**
 * Created by ces_m on 6/18/2016.
 */
public class InformacionActivos {

    int code;
    String tipo_request;
    String message;
    String actualizacion;
    ArrayList<Activo> data;

    public int getCode() {
        return code;
    }

    public ArrayList<Activo> getData() {
        return data;
    }


    public String getActualizacion() {
        return actualizacion;
    }

    public class Activo {
        String ids;
        String tipo;

        public String getIds() {
            return ids;
        }

        public void setIds(String ids) {
            this.ids = ids;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }
    }
}
