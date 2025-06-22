package cl.ucn.disc.pa.taller3.dominio.colecciones;

import cl.ucn.disc.pa.taller3.dominio.oferta.Oferta;

public class NodoDoble {

    private Oferta oferta;
    private NodoDoble siguiente;
    private NodoDoble anterior;

    public NodoDoble(Oferta oferta){

        this.oferta = oferta;
        this.siguiente = null;
        this.anterior = null;

    }

    public Oferta getOferta() {
        return oferta;
    }

    public NodoDoble getSiguiente() {
        return siguiente;
    }

    public NodoDoble getAnterior() {
        return anterior;
    }

    public void setSiguiente(NodoDoble siguiente) {
        this.siguiente = siguiente;
    }

    public void setAnterior(NodoDoble anterior) {
        this.anterior = anterior;
    }
}
