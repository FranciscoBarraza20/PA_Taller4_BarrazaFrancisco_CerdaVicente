package cl.ucn.disc.pa.taller3.dominio.oferta;

public class OfertaAyudantia extends Oferta {
    private String nombreAsignatura;
    private String rolAyudante;
    private int cantidadHorasSemanalMensual;
    private float promedioMinimo;

    public OfertaAyudantia(int id, String titulo, String descripcion, String nombreUnidad, int duracionEnDias, String nombreAsignatura, String rolAyudante, int cantidadHorasSemanalMensual, float promedioMinimo) {
        super(id, titulo, descripcion, nombreUnidad, duracionEnDias);
        this.nombreAsignatura = nombreAsignatura;
        this.rolAyudante = rolAyudante;
        this.cantidadHorasSemanalMensual = cantidadHorasSemanalMensual;
        this.promedioMinimo = promedioMinimo;
    }
    //metodo get


    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public String getRolAyudante() {
        return rolAyudante;
    }

    public int getCantidadHorasSemanalMensual() {
        return cantidadHorasSemanalMensual;
    }

    public float getPromedioMinimo() {
        return promedioMinimo;
    }

    //metodo set


    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }

    public void setRolAyudante(String rolAyudante) {
        this.rolAyudante = rolAyudante;
    }

    public void setCantidadHorasSemanalMensual(int cantidadHorasSemanalMensual) {
        this.cantidadHorasSemanalMensual = cantidadHorasSemanalMensual;
    }

    public void setPromedioMinimo(float promedioMinimo) {
        this.promedioMinimo = promedioMinimo;
    }

    @Override
    public String toString() {
        return super.toString() +
                "Asignatura: " + this.nombreAsignatura + "\n" +
                "Rol: " + this.rolAyudante + "\n" +
                "Cantidad de Horas: " + this.cantidadHorasSemanalMensual + "\n" +
                "Promedio Minimo: " + this.promedioMinimo + "\n" + "\n";
    }
}
