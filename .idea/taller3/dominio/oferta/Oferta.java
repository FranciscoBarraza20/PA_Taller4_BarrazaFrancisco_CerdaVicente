package cl.ucn.disc.pa.taller3.dominio.oferta;

import java.util.Calendar;
import java.util.Date;

public abstract class Oferta {
    protected int id;
    protected String titulo;
    protected String descripcion;
    protected Date fechaPublicacion;
    protected Date fechaCierre;
    protected String nombreUnidad;

    public Oferta(int id, String titulo, String descripcion,String nombreUnidad, int duracionEnDias) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.nombreUnidad = nombreUnidad;
        this.asignarFechas(duracionEnDias);
    }

    public void asignarFechas(int duracion) {
        this.fechaPublicacion = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.fechaPublicacion);
        calendar.add(Calendar.DAY_OF_MONTH, duracion);

        this.fechaCierre = calendar.getTime();
    }
    //metodo get
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public String getNombreUnidad() {
        return nombreUnidad;
    }

    //metodo set
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }

    @Override
    public String toString() {
        return "ID: " + this.id + "\n" +
                "titulo: " + this.titulo + "\n" +
                "descripcion: " + this.descripcion + "\n" +
                "unidad: " + this.nombreUnidad + "\n" +
                "fecha publicacion: " + this.fechaPublicacion + "\n" +
                "fecha cierre: " + this.fechaCierre + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (obj.getClass() != getClass()) {
            return false;
        }

        Oferta oferta = (Oferta) obj;
        return oferta.id == this.id || oferta.getTitulo().equalsIgnoreCase(this.titulo);
    }
}
