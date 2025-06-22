package cl.ucn.disc.pa.taller3.servicio.usuario;
import cl.ucn.disc.pa.taller3.dominio.colecciones.ContenedorNexoSimple;
import cl.ucn.disc.pa.taller3.dominio.colecciones.Nodo;
import cl.ucn.disc.pa.taller3.dominio.usuario.Usuario;
import cl.ucn.disc.pa.taller3.utilidades.ValidadorRUT;
import ucn.ArchivoEntrada;
import ucn.ArchivoSalida;
import ucn.Registro;
import java.io.IOException;


public class SistemaUsuarioArreglo implements SistemaUsuario {

    private ContenedorNexoSimple contenedorNexoSimple;
    private Usuario usuarioConectado;

    public SistemaUsuarioArreglo() {
        contenedorNexoSimple = new ContenedorNexoSimple();

        try {
            this.lecturaArchivo();
        } catch (IOException e) {
            throw new RuntimeException("Ha fallado la configuracion inicial dentro del sistema de usuarios: " + e.getMessage());
        }
    }

    // ::: METODOS IMPLEMENTADOS :::

    @Override
    public String registrarse(String nombre, String rut, String correo, String contrasenia) {
        if (!ValidadorRUT.verificarRUT(rut)) {
            return "El rut" + rut + " no es valido";
        }
        //implememtar con contenedor nexo simple
        if (this.contenedorNexoSimple.obtenerPosicion(rut) != -1) {
            return "El usuario con el rut " + rut + " ya existe";
        }

        if (nombre.length() < 3 || nombre.length() > 255) {
            return "El nombre no puede tener menos de 3 caracteres o mas de 255.";
        }

        if (contrasenia.length() < 6 || contrasenia.length() > 255) {
            return "La contrasenia no puede tener menos de 6 caracteres o mas de 255.";
        }

        try {
            this.contenedorNexoSimple.agregarUsuario(new Usuario(nombre,rut,correo,contrasenia));
            return "El usuario con el rut " + rut + " ha sido registrado con exito";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public boolean iniciarSesion(String rut, String contrasenia) {

        if (!ValidadorRUT.verificarRUT(rut)) {
            throw new IllegalArgumentException("El rut" + rut + " no es valido");
        }
        int posicion = this.contenedorNexoSimple.obtenerPosicion(rut);

        if(posicion == -1){
            throw new IllegalArgumentException("No se encuentra el usuario con rut "+rut);
        }
        if(this.contenedorNexoSimple.obtenerUsuario(posicion) == null){
            throw new IllegalArgumentException("Usuario con el rut "+rut+" no existe");
        }
        if(this.contenedorNexoSimple.obtenerUsuario(posicion).getRut().equals(rut) && this.contenedorNexoSimple.obtenerUsuario(posicion).getContrasenia().equals(contrasenia)){
            return true;
        }
        Usuario usuario = this.contenedorNexoSimple.obtenerUsuario(posicion);


        if(usuario.getContrasenia().equals(contrasenia)){
            this.usuarioConectado = usuario;
            return true;
        }

        return false;

    }

    @Override
    public String cerrarSesion() {
        if (this.usuarioConectado == null) {
            return "No hay usuario conectado";
        }
        this.usuarioConectado = null;

        return "Se ha cerrado sesion con exito!";
    }

    @Override
    public String verPerfil() {

        Nodo aux = this.contenedorNexoSimple.getCabecera();

        if(aux == null){
            return "el usuario no puede ser nulo";
        }
        this.usuarioConectado = aux.getUsuario();

        if(this.usuarioConectado == null){
            return "no hay personas en sistema";
        }
        return this.usuarioConectado.toString();
    }

    @Override
    public String editarPerfil(String nombre, String correo) {


        if (nombre.length() < 3 || nombre.length() > 255) {
            return "El nombre no puede tener menos de 3 caracteres o mas de 255.";
        }

        if (this.usuarioConectado == null) {
            return "No hay usuario conectado";
        }

        Nodo aux = this.contenedorNexoSimple.getCabecera();

        if(aux == null){
            return "El ususario no puede ser nulo";
        }

        while (aux != null){

            if(aux.getUsuario() == this.usuarioConectado){//compara el objeto directamente

                this.usuarioConectado.setCorreo(correo);
                this.usuarioConectado.setNombre(nombre);
                return "se ha editado el perfil con exito";
            }
            aux = aux.getSiguiente();
        }
        return "Usuario no encontrado en la lista";
    }

    @Override
    public void persistenciaUsuarios() {

        try {
            this.escribirArchivo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void lecturaArchivo() throws IOException {
        Registro linea;
        ArchivoEntrada archivo = new ArchivoEntrada("Usuarios.txt");

        while (!archivo.isEndFile())
        {
            linea = archivo.getRegistro();

            String nombre = linea.getString();
            String rut = linea.getString();
            String correo = linea.getString();
            String contrasenia = linea.getString();

            this.contenedorNexoSimple.agregarUsuario(new Usuario(nombre,rut,correo,contrasenia));
        }
        archivo.close();
    }

    private void escribirArchivo() throws IOException {

        ArchivoSalida archivoSalida = new ArchivoSalida("Usuarios.txt");

        Nodo aux = this.contenedorNexoSimple.getCabecera();

        while (aux != null){

            Usuario usuario = aux.getUsuario();
            Registro regSal = new Registro(4);

            regSal.agregarCampo(usuario.getNombre());
            regSal.agregarCampo(usuario.getRut());
            regSal.agregarCampo(usuario.getCorreo());
             regSal.agregarCampo(usuario.getContrasenia());
            
            archivoSalida.writeRegistro(regSal);
            aux = aux.getSiguiente();
        }
        archivoSalida.close();
    }
}
