package cl.ucn.disc.pa.taller3.dominio.colecciones;

import cl.ucn.disc.pa.taller3.dominio.usuario.Usuario;

public class ContenedorNexoSimple {

    private Nodo cabecera;
    private int tamanio;

    public ContenedorNexoSimple(){

        this.cabecera = null;
        this.tamanio = 0;

    }

    public Nodo getCabecera() {
        return cabecera;
    }

    public int getTamanio() {
        return tamanio;
    }

    public void setCabecera(Nodo cabecera) {
        this.cabecera = cabecera;
    }

   public void agregarUsuario(Usuario usuario){

        Nodo nuevoNodo;

        try {
            nuevoNodo = new Nodo(usuario);
        }catch (IllegalArgumentException ex){
            throw new IllegalArgumentException("nose ha podido crear el nodo");

        }
        if(this.cabecera == null){
            this.cabecera = nuevoNodo;
        }
        else{

            nuevoNodo.setSiguiente(this.cabecera);//el nuevo nodo pasa ser la cabecera
            this.cabecera = nuevoNodo;//se crea el nodo

        }
        nuevoNodo.setSiguiente(null);//asegura que el ultimo nodo apunte a nulo para no crear un bucle


   }
   public int obtenerPosicion(String rut){

        int posicionUsuario = 0;//posicion exacta

        Nodo aux = this.cabecera;//llamar al primer nodo

        while (aux != null){//valida si el nodo es distinti a nulo
            if(aux.getUsuario().getRut().equals(rut)){//si encuentra usuario
                return posicionUsuario;//retorna posicion
            }
            posicionUsuario++;//aumenta posicion porque usuario nose encuentra en posicion
            aux = aux.getSiguiente();//avanza al siguiente

        }
      return -1;//si no encuentra posicion
   }
   public Usuario obtenerUsuario(int posicion){

        int posicionUsuarios = 0;

        Nodo aux = this.cabecera;

        while (aux != null){
            if(posicion == posicionUsuarios){//para en el nodo que contiene a la persona
                return aux.getUsuario();
            }
            aux = aux.getSiguiente();
            posicionUsuarios++;
        }
        return null;
   }
}
