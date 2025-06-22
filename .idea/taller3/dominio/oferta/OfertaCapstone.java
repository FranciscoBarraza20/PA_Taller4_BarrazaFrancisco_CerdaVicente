package cl.ucn.disc.pa.taller3.dominio.oferta;

import java.time.LocalDate;

public class OfertaCapstone extends OfertaActividad {
    private String tipoCapstone;
    private int duracionEnMeses;
    private String[] carrerasNecesarias;
    private int cantidadMinimaEstudiantes;

    public OfertaCapstone(int id, String titulo, String descripcion, String nombreUnidad, int duracionEnDias, LocalDate fechaInicio, String empresaSolicitante, String guiaEmpresa, String tipoCapstone, int duracionEnMeses, String[] carrerasNecesarias, int cantidadMinimaEstudiantes) {
        super(id, titulo, descripcion, nombreUnidad, duracionEnDias, fechaInicio, empresaSolicitante,guiaEmpresa);

        this.tipoCapstone = tipoCapstone;
        this.duracionEnMeses = duracionEnMeses;
        this.carrerasNecesarias = carrerasNecesarias;
        this.cantidadMinimaEstudiantes = cantidadMinimaEstudiantes;
    }
    //metodo get
    public String getTipoCapstone() {
        return tipoCapstone;
    }
    public int getDuracionEnMeses() {
        return duracionEnMeses;
    }
    public String[] getCarrerasNecesarias() {
        return carrerasNecesarias;
    }
    public int getCantidadMinimaEstudiantes() {
        return cantidadMinimaEstudiantes;
    }

    //metodo set
    public void setTipoCapstone(String tipoCapstone) {
        this.tipoCapstone = tipoCapstone;
    }
    public void setDuracionEnMeses(int duracionEnMeses) {
        this.duracionEnMeses = duracionEnMeses;
    }
    public void setCarrerasNecesarias(String[] carrerasNecesarias) {
        this.carrerasNecesarias = carrerasNecesarias;
    }
    public void setCantidadMinimaEstudiantes(int cantidadMinimaEstudiantes) {
        this.cantidadMinimaEstudiantes = cantidadMinimaEstudiantes;
    }

    @Override
    public String toString() {
        String carrerasNecesarias = "";

        for (int i = 0; i < this.carrerasNecesarias.length; i++) {
            carrerasNecesarias += this.carrerasNecesarias[i];

            if (i != this.carrerasNecesarias.length - 1) {
                carrerasNecesarias += ", ";
            }
        }

        return super.toString() +
                "tipo de capstone: " + this.tipoCapstone + "\n" +
                "duracion en meses: " + this.duracionEnMeses + "\n" +
                "carreras necesarias: " + carrerasNecesarias + "\n" +
                "cantidad minima de estudiantes: " + this.cantidadMinimaEstudiantes + "\n" + "\n";
    }
}
