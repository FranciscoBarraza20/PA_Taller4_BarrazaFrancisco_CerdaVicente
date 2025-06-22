package cl.ucn.disc.pa.taller3;

import cl.ucn.disc.pa.taller3.servicio.oferta.SistemaOferta;
import cl.ucn.disc.pa.taller3.servicio.oferta.SistemaOfertaArreglo;
import cl.ucn.disc.pa.taller3.servicio.usuario.SistemaUsuario;
import cl.ucn.disc.pa.taller3.servicio.usuario.SistemaUsuarioArreglo;
import cl.ucn.disc.pa.taller3.vista.VistaConsola;
import ucn.StdOut;

public class Main {
    public static void main(String[] args) {
        // configuracion de la aplicacion
        try {
            // se instancian ambos sistemas
            SistemaUsuario sistemaUsuario = new SistemaUsuarioArreglo();
            SistemaOferta sistemaOferta = new SistemaOfertaArreglo();

            // se genera la instancia de la vista utilizando com entrada los sistemas generados
            VistaConsola vista = new VistaConsola(sistemaUsuario, sistemaOferta);

            // se inicia la aplicacion
            vista.iniciar();
        } catch (Exception e) {
            StdOut.println(e.getMessage());
        }
    }
}