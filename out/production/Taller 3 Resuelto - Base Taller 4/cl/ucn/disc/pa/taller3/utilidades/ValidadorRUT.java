package cl.ucn.disc.pa.taller3.utilidades;

public class ValidadorRUT {
    public static boolean verificarRUT(String rutCompleto) {
        rutCompleto = rutCompleto.replace(".", "").replace("-", "").toUpperCase();

        if (!rutCompleto.matches("\\d+[0-9K]")) return false;

        String rut = rutCompleto.substring(0, rutCompleto.length() - 1);
        char dv = rutCompleto.charAt(rutCompleto.length() - 1);

        return dv == calcularDV(rut);
    }

    private static char calcularDV(String rut) {
        int suma = 0;
        int multiplicador = 2;

        for (int i = rut.length() - 1; i >= 0; i--) {
            suma += Character.getNumericValue(rut.charAt(i)) * multiplicador;
            multiplicador = (multiplicador == 7) ? 2 : multiplicador + 1;
        }

        int resto = 11 - (suma % 11);
        if (resto == 11) return '0';
        if (resto == 10) return 'K';
        return (char) (resto + '0');
    }
}
