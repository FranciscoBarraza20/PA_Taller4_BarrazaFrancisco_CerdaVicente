package cl.ucn.disc.pa.taller3.utilidades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class ValidadorFecha {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu")
                                                            .withResolverStyle(ResolverStyle.STRICT);

    public static boolean esFechaValida(String fechaStr) {
        try {
            LocalDate fecha = LocalDate.parse(fechaStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static LocalDate convertirFecha(String fechaStr) {
        return LocalDate.parse(fechaStr, formatter);
    }
}
