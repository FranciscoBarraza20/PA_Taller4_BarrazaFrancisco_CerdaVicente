package cl.ucn.disc.pa.taller3.dominio.usuario;

import java.util.Objects;

public class Usuario {
    private String nombre;
    private final String rut;
    private String correo;
    private final String contrasenia;

    public Usuario(
            String nombre,
            String rut,
            String correo,
            String contrasenia
    ) {
        this.nombre = nombre;
        this.rut = rut;
        this.correo = correo;
        this.contrasenia = contrasenia;
    }
    public String getNombre(){
        return nombre;
    }

    public String getRut() {
        return rut;
    }
    public String getCorreo(){
        return correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return  "Nombre:" + nombre + "\n" +
                "Rut:" + rut + "\n" +
                "Correo:" + correo + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return usuario.getRut().equalsIgnoreCase(this.rut);
    }
}
