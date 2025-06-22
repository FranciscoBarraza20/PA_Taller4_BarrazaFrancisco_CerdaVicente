package cl.ucn.disc.pa.taller3.utilidades;

import ucn.StdIn;
import ucn.StdOut;

public class ValidadorEntrada {
    public static int validarEntero(String frase) {
        String valor = "";
        while (true) {
            StdOut.print(frase);
            valor = StdIn.readLine();
            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException e) {
                StdOut.println(valor + " no es un numero");
            }
        }
    }
}
