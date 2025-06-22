package cl.ucn.disc.pa.taller3.dominio.colecciones;

import cl.ucn.disc.pa.taller3.dominio.oferta.Oferta;

public class ContenedorNexoDoble {

    private NodoDoble cabecera;
    private NodoDoble cola;
    private int tamanio;

    public ContenedorNexoDoble(){

        this.cabecera = null;
        this.cola = null;
        this.tamanio = 0;
    }

    public int getTamanio() {
        return tamanio;
    }

    public NodoDoble getCabecera() {
        return cabecera;
    }

    public NodoDoble getCola() {
        return cola;
    }

    public void setCabecera(NodoDoble cabecera) {
        this.cabecera = cabecera;
    }

    public void setCola(NodoDoble cola) {
        this.cola = cola;
    }

    public void agregarOferta(Oferta oferta){

        NodoDoble nuevo;

        try {
            nuevo = new NodoDoble(oferta);
        }catch (IllegalArgumentException ex){
            throw ex;

        }

        if(this.cabecera == null){

            this.cabecera = nuevo;
            this.cola = nuevo;

        }else{
            nuevo.setSiguiente(this.cabecera);
            this.cabecera.setAnterior(nuevo);
            this.cabecera = nuevo;
        }
        this.tamanio++;
    }

    public boolean eliminar(Oferta oferta){

        //si la cabecera esta vacia
        if(this.cabecera == null){
            return false;

        }//por si tiene 1 solo elemento
        if(this.cabecera == this.cola && this.cabecera.getOferta().equals(oferta)){//

            this.cabecera = null;//elimina nodo
            this.cola = null;//elimina nodo
            this.tamanio--;
        }
        if(this.cabecera.getOferta().equals(oferta)){

            this.cabecera = this.cabecera.getSiguiente();

            this.cabecera.setAnterior(null);

            this.tamanio--;
        }
        if(this.cola.getOferta().equals(oferta)){

            this.cola = this.cola.getAnterior();

            this.cola.setSiguiente(null);

            this.tamanio--;
        }

        for (NodoDoble aux = this.cabecera.getSiguiente(); aux != null; aux = aux.getSiguiente()){
            if(aux.getOferta().equals(oferta)){
                this.eliminarNodo(aux);
                this.tamanio--;
                return true;
            }
        }
        return false;

    }
    public void eliminarNodo(NodoDoble nodoEliminar){

        NodoDoble anterior = nodoEliminar.getAnterior();
        NodoDoble siguiente = nodoEliminar.getSiguiente();

        anterior.setSiguiente(siguiente);
        siguiente.setAnterior(anterior);

    }
}
