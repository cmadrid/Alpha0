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
    Date fnp;
    String nap;
    String nap_en;
    String bcp;
    String bcp_en;
    String fop;
    Date acp;
    Date afp;
    Bitmap bitmap;
    String tip;

    public Participante() {

    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
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

    public Date getFnp() {
        return fnp;
    }

    public void setFnp(Date fnp) {
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

    public String getNap_en() {
        return nap_en;
    }

    public void setNap_en(String nap_en) {
        this.nap_en = nap_en;
    }

    public String getBcp_en() {
        return bcp_en;
    }

    public void setBcp_en(String bcp_en) {
        this.bcp_en = bcp_en;
    }
}
