package gson;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by ces_m on 6/5/2016.
 */
public class Participante{

    int idp;
    String nop;
    String psp;
    String fnp;
    String nap;
    String bcp;
    String fop;
    Date acp;
    Date afp;
    Bitmap bitmap;

    public Participante() {

    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getBcp() {
        return bcp;
    }

    public void setBcp(String bcp) {
        this.bcp = bcp;
    }

    public String getFnp() {
        return fnp;
    }

    public void setFnp(String fnp) {
        this.fnp = fnp;
    }

    public String getFop() {
        return fop;
    }

    public void setFop(String fop) {
        this.fop = fop;
    }


    public int getIdp() {
        return idp;
    }

    public void setIdp(int idp) {
        this.idp = idp;
    }

    public String getNap() {
        return nap;
    }

    public void setNap(String nap) {
        this.nap = nap;
    }

    public String getNop() {
        return nop;
    }

    public void setNop(String nop) {
        this.nop = nop;
    }

    public String getPsp() {
        return psp;
    }

    public void setPsp(String psp) {
        this.psp = psp;
    }
    public Date getAcp() {
        return acp;
    }

    public void setAcp(Date acp) {
        this.acp = acp;
    }

    public Date getAfp() {
        return afp;
    }

    public void setAfp(Date afp) {
        this.afp = afp;
    }
}
