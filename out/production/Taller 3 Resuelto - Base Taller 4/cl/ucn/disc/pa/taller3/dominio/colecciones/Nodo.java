package cl.ucn.disc.pa.taller3.dominio.colecciones;

import cl.ucn.disc.pa.taller3.dominio.usuario.Usuario;

public class Nodo {

    private Nodo siguiente;
    private Usuario usuario;

    public Nodo(Usuario usuario){

        this.usuario = usuario;
        this.siguiente = null;

    }
    public Usuario getUsuario() {
        return usuario;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }
    public void setSiguiente(Nodo siguiente){
        this.siguiente = siguiente;
    }

}
