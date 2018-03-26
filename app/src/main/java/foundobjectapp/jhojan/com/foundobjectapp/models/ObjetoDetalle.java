package foundobjectapp.jhojan.com.foundobjectapp.models;

/**
 * Created by Jhojan on 5/12/2017.
 */

public class ObjetoDetalle {

    String Objeto;

    String Estado;

    String Fecha;

    String Lugar;

    String Descripción;

    String Imagen;

    public ObjetoDetalle() {
    }

    public ObjetoDetalle(String objeto, String estado, String fecha, String lugar, String descripción, String imagen) {
        Objeto = objeto;
        Estado = estado;
        Fecha = fecha;
        Lugar = lugar;
        Descripción = descripción;
        Imagen = imagen;
    }

    public String getObjeto() {
        return Objeto;
    }

    public void setObjeto(String objeto) {
        Objeto = objeto;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getLugar() {
        return Lugar;
    }

    public void setLugar(String lugar) {
        Lugar = lugar;
    }

    public String getDescripción() {
        return Descripción;
    }

    public void setDescripción(String descripción) {
        Descripción = descripción;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    @Override
    public String toString() {
        return "ObjetoDetalle{" +
                "Objeto='" + Objeto + '\'' +
                ", Estado='" + Estado + '\'' +
                ", Fecha='" + Fecha + '\'' +
                ", Lugar='" + Lugar + '\'' +
                ", Descripción='" + Descripción + '\'' +
                ", Imagen='" + Imagen + '\'' +
                '}';
    }
}
