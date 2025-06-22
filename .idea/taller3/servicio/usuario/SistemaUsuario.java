package cl.ucn.disc.pa.taller3.servicio.usuario;

public interface SistemaUsuario {
    /**
     * El usuario busca registrarse en el sistemas con los parametros : nombre, rut, correo, contrasenia.
     *
     * Prerequisitos:
     * El rut debe estar validado.
     * No puede existir un usuario registrado con el rut entrante.
     * El nombre tiene que tener al menos 3 caracteres y un maximo de 255.
     * La contrasenia tiene que tener al menos 6 caracteres y un maximo de 255.
     *
     * Postcondiciones:
     * Se registra un nuevo usuario con los parametros entrantes.
     *
     * Excepciones:
     * Se captura IllegalArgumenException si es que el arreglo esta completo, o si se ingresa un usuario con un rut que
     * ya esta registrado en el sistema.
     */
    String registrarse(String nombre, String rut, String correo, String contrasenia);

    /**
     * El usuario busca ingresar al sistema con las siguientes credenciales: rut y contrasenia.
     *
     * Prerequisitos:
     * El rut debe estar validado.
     *
     * Postcondiciones:
     * Se ingresa al sistema pasando a ser el usuario conectado.
     *
     * Excepciones:
     * Se captura IllegalArgumentException si es que no existe ningun usuario con el rut indicado.
     *
     * Retorno:
     * true si existe un usuario que coincida las crendeciales, false si la contrasenia es incorrecta.
     */
    boolean iniciarSesion(String rut, String contrasenia);

    /**
     * El usuario conectado quiere cerrar su sesion.
     *
     * Prerequisitos:
     * Debe haber un usuario conectado.
     *
     * Postcondiciones:
     * El usuario conectado pasa a ser nulo.
     *
     * Excepciones:
     * Se lanza una excepcion () dado que no se puede ejecutar esta funcion si no hay usuario conectado.
     */
    String cerrarSesion();

    /**
     * El usuario busca ver su informacion.
     *
     * Prerequisitos:
     * Debe haber un usuario conectado.
     *
     * Excepciones:
     * Se lanza una excepcion () dado que no se puede ejecutar esta funcion si no hay usuario conectado.
     */
    String verPerfil();

    /**
     * El usuario busca ver su informacion.
     *
     * Prerequisitos:
     * Debe haber un usuario conectado.
     *
     * Postcondicion:
     * El nombre y correo del usuario son modificados dentro del sistema.
     *
     * Excepciones:
     * Se lanza una excepcion () dado que no se puede ejecutar esta funcion si no hay usuario conectado.
     */
    String editarPerfil(String nombre, String correo);

    /**
     * busca actualizar el archivo de texto: Usuarios.txt
     */
    void persistenciaUsuarios();
}
