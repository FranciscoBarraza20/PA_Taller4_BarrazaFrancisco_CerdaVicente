package cl.ucn.disc.pa.taller3.dominio.oferta;

import java.time.LocalDate;

public abstract class OfertaActividad extends Oferta {
    protected LocalDate fechaInicio;
    protected String empresaSolicitante;
    protected String guiaEmpresa;

    public OfertaActividad(int id, String titulo, String descripcion, String nombreUnidad, int duracionEnDias, LocalDate fechaInicio, String empresaSolicitante, String guiaEmpresa) {

        super(id, titulo, descripcion, nombreUnidad, duracionEnDias);
        this.fechaInicio = fechaInicio;
        this.empresaSolicitante = empresaSolicitante;
        this.guiaEmpresa = guiaEmpresa;
    }
    //metodo get
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public String getEmpresaSolicitante() {
        return empresaSolicitante;
    }

    public String getGuiaEmpresa() {
        return guiaEmpresa;
    }


    //metodo set
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setEmpresaSolicitante(String empresaSolicitante) {
        this.empresaSolicitante = empresaSolicitante;
    }

    public void setGuiaEmpresa(String guiaEmpresa) {
        this.guiaEmpresa = guiaEmpresa;
    }

    @Override
    public String toString() {
        return super.toString() +
                "Inicio de Actividad: " + this.fechaInicio + "\n" +
                "Empresa Solicitante: " + this.empresaSolicitante + "\n" +
                "Guia Empresa: " + this.guiaEmpresa + "\n";
    }
}
