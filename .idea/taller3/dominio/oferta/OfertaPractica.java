package cl.ucn.disc.pa.taller3.dominio.oferta;

import java.time.LocalDate;

public class OfertaPractica extends OfertaActividad {
    private final int DURACION_EN_MESES = 2;
    private boolean tieneRemuneracion;

    public OfertaPractica(int id, String titulo, String descripcion, String nombreUnidad, int duracionEnDias, LocalDate fechaInicio, String empresaSolicitante, String guiaEmpresa, boolean tieneRemuneracion) {
        super(id, titulo, descripcion, nombreUnidad, duracionEnDias, fechaInicio, empresaSolicitante, guiaEmpresa);
        this.tieneRemuneracion = tieneRemuneracion;
    }
    //metodo get


    public int getDURACION_EN_MESES() {
        return DURACION_EN_MESES;
    }

    public boolean isTieneRemuneracion() {
        return tieneRemuneracion;
    }

    public void setTieneRemuneracion(boolean tieneRemuneracion) {
        this.tieneRemuneracion = tieneRemuneracion;
    }

    @Override
    public String toString() {
        String remuneracion = "";

        if (this.tieneRemuneracion) {
            remuneracion = "Si";
        } else {
            remuneracion = "No";
        }

        return super.toString() +
                "Duracion en meses: " + this.DURACION_EN_MESES + "\n" +
                "Remuneracion: " + remuneracion + "\n" + "\n";
    }
}
