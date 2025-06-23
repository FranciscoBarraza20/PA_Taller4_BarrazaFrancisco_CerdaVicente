package cl.ucn.disc.pa.taller3.servicio.oferta;

import cl.ucn.disc.pa.taller3.dominio.colecciones.ContenedorNexoDoble;
import cl.ucn.disc.pa.taller3.dominio.colecciones.NodoDoble;
import cl.ucn.disc.pa.taller3.dominio.oferta.*;
import cl.ucn.disc.pa.taller3.utilidades.ValidadorFecha;
import ucn.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


public class SistemaOfertaArreglo implements SistemaOferta{

    private ContenedorNexoDoble contenedorNexoDoble;
    private Oferta ultimaOferta;
    private final String[] CODIGOS_TIPO_OFERTA = {"A","C","P"};

    public SistemaOfertaArreglo() {
        contenedorNexoDoble = new ContenedorNexoDoble();

        try {
            this.lecturaArchivo();
        } catch (IOException e) {
            throw new RuntimeException("Ha fallado la configuracion inicial dentro del sistema de ofertas: " + e.getMessage());
        }
    }

    @Override
    public String publicarOferta(String[] datosOferta) {
        try {
            this.validarOferta(datosOferta);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
        // caso: ayudantia
        if (datosOferta[0].equalsIgnoreCase(this.CODIGOS_TIPO_OFERTA[0])) {
            return this.publicarOfertaAyudantia(datosOferta);
        }
        // caso: capstone
        if (datosOferta[0].equalsIgnoreCase(this.CODIGOS_TIPO_OFERTA[1])) {
            return this.publicarOfertaCapstone(datosOferta);
        }
        // caso: practica
        return this.publicarOfertaPractica(datosOferta);
    }

    @Override
    public String verOferta(String idTitulo) {//funcionando

        if (this.contenedorNexoDoble.getTamanio() == 0) {
            return "La lista esta vacia";
        }
        int id = -1;
        try {
            id = Integer.parseInt(idTitulo);
        } catch (NumberFormatException _) {
        }

        if (this.ultimaOferta != null && (this.ultimaOferta.getId() == id || this.ultimaOferta.getTitulo().equalsIgnoreCase(idTitulo))) {
            return this.ultimaOfertaInfo();
        }
        //obtiene la cabecera del nodo
        NodoDoble aux = this.contenedorNexoDoble.getCabecera();
        //validasi el nodo es nulo
        if(aux == null){
            return "La oferta no puede ser nula";
        }
        //while para recorrer lista
        while (aux != null){

            Oferta oferta = aux.getOferta();
            this.ultimaOferta = oferta;

            if(ultimaOferta.getId() == id || ultimaOferta.getTitulo().equals(idTitulo)){
                return this.ultimaOfertaInfo();
            }
            aux = aux.getSiguiente();
        }
        return "La oferta con el id: " + id + ", no existe.";
    }

    private String ultimaOfertaInfo() {

        return ":::OFERTA ENCONTRADA:::" + "\n\n" +
                this.ultimaOferta + "\n\n";
    }

    @Override
    public String verTodasLasOfertas() {

        if (this.contenedorNexoDoble.getTamanio() == 0) {
            return "La lista esta vacia";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(":::OFERTAS DISPONIBLE:::").append("\n\n");

        NodoDoble aux = this.contenedorNexoDoble.getCabecera();

        while (aux != null){

            Oferta oferta = aux.getOferta();
            sb.append(oferta.toString());

            aux = aux.getSiguiente();
        }
        return sb.toString();
    }

    @Override
    public String editarOferta(String id, String[] datosOferta) {
        int id_validada;

        try {
            id_validada = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return "El id no corresponde a un numero.";
        }
        Oferta ofertaQueSeEdita = buscarOferta(id_validada);

        try {
            this.validarOferta(datosOferta);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
        // caso: ayudantia
        if (datosOferta[0].equalsIgnoreCase(this.CODIGOS_TIPO_OFERTA[0]) && ofertaQueSeEdita instanceof OfertaAyudantia) {
            return this.editarOfertaAyudantia(datosOferta, ofertaQueSeEdita);
        }
        // caso: capstone
        if (datosOferta[0].equalsIgnoreCase(this.CODIGOS_TIPO_OFERTA[1]) && ofertaQueSeEdita instanceof OfertaCapstone) {
            return this.editarOfertaCapstone(datosOferta, ofertaQueSeEdita);
        }
        // caso: practica
        if (ofertaQueSeEdita instanceof OfertaPractica) {
            return this.editarOfertaPractica(datosOferta, ofertaQueSeEdita);
        }
        return "La oferta con la id " + id + ", no corresponde con el tipo de oferta declarado";
    }

    @Override
    public String eliminarOferta(String id) {

        int idOferta;
        try {
            idOferta = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return "El id no corresponde a un numero.";
        }
        NodoDoble aux = this.contenedorNexoDoble.getCabecera();

        while (aux != null){

            Oferta oferta = aux.getOferta();

            if(oferta.getId() == idOferta){//si la id asociada a la oferta es igual a la id dada por parametro
                this.contenedorNexoDoble.eliminar(oferta);//se elimina la oferta

                if(this.ultimaOferta != null && this.ultimaOferta == oferta){
                    this.ultimaOferta = null;//la oferta asociada se hace nula porque la oferta dejara de existir
                }
                return "Se ha eliminado la oferta con la id "+id+ "con exito";
            }
            aux = aux.getSiguiente();//pasa al siguinte
        }
        return "La oferta con el id: " + id + ", no existe.";
    }

    @Override
    public void persistenciaOfertas() {

        try {
            this.escribirArchivo();
        } catch (IOException e) {
            StdOut.println(e.getMessage());
        }
    }
    // ::: METODOS PRIVADOS :::

    private void lecturaArchivo() throws IOException {
        Registro linea;
        ArchivoEntrada archivo = new ArchivoEntrada("OfertasAcademicas.txt");
        Oferta oferta = null;

        // saltar la primera linea
        archivo.getRegistro();

        while (!archivo.isEndFile())
        {
            linea = archivo.getRegistro();

            char tipoOferta = linea.getChar();

            int id = Integer.parseInt(linea.getString());
            String titulo = linea.getString();
            String descripcion = linea.getString();
            String nombreUnidad = linea.getString();
            int duracion = linea.getInt();

            if (tipoOferta == 'A') {
                String nombreAsignatura = linea.getString();
                String rolAyudante = linea.getString();
                int cantidadHorasSemanalMensual = linea.getInt();
                float promedioMinimo = (float) linea.getDouble();

                oferta = new OfertaAyudantia(id,titulo,descripcion,nombreUnidad,duracion,nombreAsignatura,rolAyudante,cantidadHorasSemanalMensual,promedioMinimo);
            }

            else if (tipoOferta == 'C' || tipoOferta == 'P') {
                LocalDate fechaInicio = ValidadorFecha.convertirFecha(linea.getString());
                String empresaSolicitante = linea.getString();
                String guiaEmpresa = linea.getString();

                if (tipoOferta == 'C') {
                    String tipoCapstone = linea.getString();
                    int duracionEnMeses = linea.getInt();
                    String[] carrerasNecesarias = this.procesarOcupaciones(linea.getString());
                    int cantidadMinimaEstudiantes = linea.getInt();

                    oferta = new OfertaCapstone(id, titulo, descripcion, nombreUnidad, duracion, fechaInicio, empresaSolicitante, guiaEmpresa, tipoCapstone, duracionEnMeses, carrerasNecesarias, cantidadMinimaEstudiantes);
                }
                else {
                    String tieneRemuneracion = linea.getString();
                    boolean remuneracion = tieneRemuneracion.equalsIgnoreCase("Si");

                    oferta = new OfertaPractica(id, titulo, descripcion, nombreUnidad, duracion, fechaInicio, empresaSolicitante, guiaEmpresa, remuneracion);
                }
            }
            if (oferta != null) {
                this.contenedorNexoDoble.agregarOferta(oferta);
                oferta = null;
            }
        }
        archivo.close();
    }

    private String[] procesarOcupaciones(String texto) {
        String[] partes = texto.split("\\|");

        for (int i = 0; i < partes.length; i++) {
            partes[i] = partes[i].trim();
        }
        return partes;
    }

    private String publicarOfertaAyudantia(String[] datosOferta) {
        try {
            this.validarAyudantia(datosOferta);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

        int ultimaID = this.contenedorNexoDoble.getTamanio()+1;//generador de id
        // se crea la oferta de ayudantia
        Oferta nueva = new OfertaAyudantia(ultimaID, datosOferta[1], datosOferta[2], datosOferta[3], Integer.parseInt(datosOferta[4]), datosOferta[5], datosOferta[6], Integer.parseInt(datosOferta[7]), Float.parseFloat(datosOferta[8]));
        // se agrega al listado
        this.contenedorNexoDoble.agregarOferta(nueva);
        // respuesta afirmativa
        return "Se ha agregado la oferta de ayudantia con exito, y la id es "+(this.contenedorNexoDoble.getTamanio());
    }

    private String publicarOfertaCapstone(String[] datosOferta) {
        try {
            this.validarCapstone(datosOferta);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
        int ultimaID = this.contenedorNexoDoble.getTamanio()+1;
        // se crea la oferta de capstone
        Oferta nueva = new OfertaCapstone(ultimaID, datosOferta[1], datosOferta[2], datosOferta[3], Integer.parseInt(datosOferta[4]), ValidadorFecha.convertirFecha(datosOferta[5]), datosOferta[6],
                datosOferta[7], datosOferta[8], Integer.parseInt(datosOferta[9]), this.obtenerCarreras(datosOferta), Integer.parseInt(datosOferta[11]));
        // se agrega la oferta a la lista
        this.contenedorNexoDoble.agregarOferta(nueva);

        return "La oferta de Capstone Project fue agregada con exito , y la id es "+(this.contenedorNexoDoble.getTamanio());
    }

    private String publicarOfertaPractica(String[] datosOferta) {
        try {
            this.validarPractica(datosOferta);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
        Oferta nueva = getOferta(datosOferta);
        // se agrega la oferta
        this.contenedorNexoDoble.agregarOferta(nueva);

        return "Se ha agregado la oferta con exito , y la id es "+(this.contenedorNexoDoble.getTamanio());
    }

    private Oferta getOferta(String[] datosOferta) {
        int ultimaID = this.contenedorNexoDoble.getTamanio()+1;

        //int id, String titulo, String descripcion, String nombreUnidad, int duracionEnDias, LocalDate fechaInicio, String empresaSolicitante, String guiaEmpresa, boolean tieneRemuneracion

        Oferta nueva = new OfertaPractica(ultimaID, datosOferta[1], datosOferta[2], datosOferta[3], Integer.parseInt(datosOferta[4]),ValidadorFecha.convertirFecha(datosOferta[5]),
                datosOferta[6], datosOferta[7], datosOferta[8].equalsIgnoreCase("si"));
        return nueva;
    }

    private String[] obtenerCarreras(String[] datosOferta) {
        // se separan por comas
        String[] carreras = datosOferta[10].split(",");

        // se recorre cada una para eliminar posibles espacios al inicio y al final
        for (int i = 0; i < carreras.length; i++) {
            carreras[i] = carreras[i].trim();
        }
        return carreras;
    }

    private Oferta buscarOferta(int id) {

        NodoDoble aux = this.contenedorNexoDoble.getCabecera();

        while (aux != null){

            if(aux.getOferta().getId() == id){
                return aux.getOferta();//retorno la oferta con la id asociada
            }
            aux = aux.getSiguiente();//pasa al siguiente
        }
        throw new IllegalArgumentException("La oferta con la id " + id + " no existe.");
    }

    private String editarOfertaAyudantia(String[] datosOferta, Oferta oferta) {
        try {
            this.validarAyudantia(datosOferta);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
        // se edita la oferta
        this.editarSeccion(datosOferta, oferta);
        this.editarSeccion(datosOferta, (OfertaAyudantia) oferta);

        return "Se ha editado con exito la oferta de ayudantia con la id " + oferta.getId();
    }

    private String editarOfertaCapstone(String[] datosOferta, Oferta oferta) {
        try {
            this.validarCapstone(datosOferta);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

        // se edita la oferta
        this.editarSeccion(datosOferta, oferta);
        this.editarSeccion(datosOferta, (OfertaCapstone) oferta);

        return "Se ha editado la oferta de capstone con la id " + oferta.getId();
    }

    private String editarOfertaPractica(String[] datosOferta, Oferta oferta) {
        try {
            this.validarPractica(datosOferta);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

        // se edita la oferta
        this.editarSeccion(datosOferta, oferta);
        this.editarSeccion(datosOferta, (OfertaPractica) oferta);

        return "Se ha editado la oferta de practica profesional con la id " + oferta.getId();
    }

    // editores
    private void editarSeccion(String[] datosOferta, Oferta oferta) {
        // se edita la seccion que corresponde a la clase Oferta
        oferta.setTitulo(datosOferta[1]);
        oferta.setDescripcion(datosOferta[2]);
        oferta.setNombreUnidad(datosOferta[3]);
        oferta.asignarFechas(Integer.parseInt(datosOferta[4]));
    }

    private void editarSeccion(String[] datosOferta, OfertaAyudantia oferta) {
        // se edita la seccion que corresponde a la clase OfertaAyudantia
        oferta.setNombreAsignatura(datosOferta[5]);
        oferta.setRolAyudante(datosOferta[6]);
        oferta.setCantidadHorasSemanalMensual(Integer.parseInt(datosOferta[7]));
        oferta.setPromedioMinimo(Float.parseFloat(datosOferta[8]));
    }

    private void editarSeccion(String[] datosOferta, OfertaCapstone oferta) {
        // se edita la seccion que corresponde a la clase OfertaCapstone
        oferta.setFechaInicio(ValidadorFecha.convertirFecha(datosOferta[5]));
        oferta.setEmpresaSolicitante(datosOferta[6]);
        oferta.setGuiaEmpresa(datosOferta[7]);
        oferta.setTipoCapstone(datosOferta[8]);
        oferta.setDuracionEnMeses(Integer.parseInt(datosOferta[9]));
        oferta.setCarrerasNecesarias(this.obtenerCarreras(datosOferta));
        oferta.setCantidadMinimaEstudiantes(Integer.parseInt(datosOferta[11]));
    }

    private void editarSeccion(String[] datosOferta, OfertaPractica oferta) {
        // se edita la seccion que corresponde a la clase OfertaPractica
        oferta.setFechaInicio(ValidadorFecha.convertirFecha(datosOferta[5]));
        oferta.setEmpresaSolicitante(datosOferta[6]);
        oferta.setGuiaEmpresa(datosOferta[7]);
        oferta.setTieneRemuneracion(datosOferta[8].equalsIgnoreCase("Si"));
    }

    // validadores
    private void validarOferta(String[] datosOferta) {
        // se evalua si el tipo corresponde
        boolean tipoValido = false;

        for (String letra : this.CODIGOS_TIPO_OFERTA) {
            if (datosOferta[0].equalsIgnoreCase(letra)) {
                tipoValido = true;
                break;
            }
        }

        if (!tipoValido) {
            throw new IllegalArgumentException("El tipo de oferta indicado no corresponde con los considerados en este sistema.");
        }

        // se valida el titulo
        if (!(7 < datosOferta[1].length() && datosOferta[1].length() < 256)) {
            throw new IllegalArgumentException("El titulo debe tener entre 8 y 255 caracteres");
        }

        // se valida la descripcion
        if (!(7 < datosOferta[2].length() && datosOferta[2].length() < 256)) {
            throw new IllegalArgumentException("La descripcion debe tener entre 8 y 255 caracteres");
        }

        // se valida la unidad
        if (datosOferta[3].isEmpty()) {
            throw new IllegalArgumentException("El nombre de la unidad debe tener al menos 1 caracter");
        }

        // se valida la duracion
        try {
            int duracion = Integer.parseInt(datosOferta[4]);

            if (duracion < 1) {
                throw new IllegalArgumentException("La duracion debe ser al menos 1") ;
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La duracion debe ser un valor numerico");
        }
    }

    private void validarAyudantia(String[] datosOferta) {
        // se valida el nombre de la asignatura
        if (!(5 < datosOferta[5].length() && datosOferta[5].length() < 256)) {
            throw new IllegalArgumentException("El nombre de la asignatura debe tener entre 6 y 255 caracteres");
        }

        // se valida el rol del ayudante
        if (!(5 < datosOferta[6].length() && datosOferta[6].length() < 256)) {
            throw new IllegalArgumentException("El rol del ayudante debe tener entre 6 y 255 caracteres");
        }

        // se valida las horas semanales
        try {
            int horas = Integer.parseInt(datosOferta[7]);

            if (horas <= 2) {
                throw new IllegalArgumentException("La cantidad de horas semanales debe ser mayor a 2");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La cantidad de horas semanales debe ser un valor numerico");
        }

        // se valida el promedio
        try {
            float promedio = Float.parseFloat(datosOferta[8]);

            if (!(4.0 <= promedio && promedio <= 7.0)) {
                throw new IllegalArgumentException("El promedio debe estar entre 4.0 y 7.0") ;
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El promedio debe ser un valor de punto flotante");
        }
    }

    private void validarCapstone(String[] datosOferta) {
        // se valida la fecha de inicio de la actividad
        if (!(ValidadorFecha.esFechaValida(datosOferta[5]))) {
            throw new IllegalArgumentException("La fecha ingresada no es valida. Debe repetar el formato dd/mm/yyy");
        }

        // se valida el texto de la compania y del docente guia (no pueden estar vacios)
        // validacion implicita
        if (datosOferta[6].isEmpty() || datosOferta[7].isEmpty()) {
            throw new IllegalArgumentException("La compania o el docente guia no pueden estar vacios.") ;
        }

        // se valida el tipo de proyecto
        // validacion implicita
        boolean tipoCorrecto = false;

        for (String tipo : new String[]{"Capstone", "Investigacion", "Memoria"}) {
            if (datosOferta[8].equalsIgnoreCase(tipo)) {
                tipoCorrecto = true;
                break;
            }
        }

        if (!tipoCorrecto) {
            throw new IllegalArgumentException("El tipo de proyecto de capstone no corresponde con los trabajados por el sistema.") ;
        }

        // se valida la duracion en meses indicada
        int meses;

        try {
            meses = Integer.parseInt(datosOferta[9]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La duracion en meses del proyecto debe ser un valor numerico.") ;
        }

        boolean cantidadDeMesesCorrecta = false;

        for (int cantidad : new int[]{6, 12, 18}) {
            if (meses == cantidad) {
                cantidadDeMesesCorrecta = true;
                break;
            }
        }

        if (!cantidadDeMesesCorrecta) {
            throw new IllegalArgumentException("La cantidad de meses no corresponde a los trabajados por el sistema.");
        }

        // se validan las carreras
        if (datosOferta[10].isEmpty()) {
            throw new IllegalArgumentException("Deben existir carreras asociadas") ;
        }

        // se valida la cantidad de estudiantes requerida
        int cantidadEstudiantes;

        try {
            cantidadEstudiantes = Integer.parseInt(datosOferta[11]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La cantidad de estudiantes debe ser un valor numerico");
        }

        if (cantidadEstudiantes < 2 || cantidadEstudiantes > 5) {
            throw new IllegalArgumentException("La cantidad de estudiantes debe ser un valor entre 2 y 5.") ;
        }
    }

    private void validarPractica(String[] datosOferta) {
        // se valida la fecha de inicio de la actividad
        if (!(ValidadorFecha.esFechaValida(datosOferta[5]))) {
            throw new IllegalArgumentException("La fecha ingresada no es valida. Debe repetar el formato dd/mm/yyy");
        }

        // se valida el texto de la compania y del docente guia (no pueden estar vacios)
        // validacion implicita
        if (datosOferta[6] == null || datosOferta[7] == null) {
            throw new IllegalArgumentException("La compania o el docente guia no pueden estar vacios.");
        }

        // se valida la remuneracion
        if (datosOferta[8] == null) {
            throw new IllegalArgumentException("Se debe indicar si hay remuneracion o no");
        }

        if (!(datosOferta[8].equalsIgnoreCase("Si") || datosOferta[8].equalsIgnoreCase("No"))) {
            throw new IllegalArgumentException("No se ha indicado correctamente si tiene remuneracion o no la actividad");
        }
    }
    private void escribirArchivo() throws IOException {

        ArchivoSalida archivoSalida = new ArchivoSalida("Resultados_academicos.txt");

        NodoDoble aux = this.contenedorNexoDoble.getCabecera();
        //formato de fecha basado en dia, mes y anio
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        while (aux != null) {

            Oferta oferta = aux.getOferta();

            if (oferta instanceof OfertaAyudantia) {
                Registro regSalAyudantia = new Registro(10);
                String tipoOferta = "A";

                regSalAyudantia.agregarCampo(tipoOferta);
                regSalAyudantia.agregarCampo(oferta.getId());
                regSalAyudantia.agregarCampo(oferta.getTitulo());
                regSalAyudantia.agregarCampo(oferta.getDescripcion());
                regSalAyudantia.agregarCampo(oferta.getNombreUnidad());

                // --- Corrección: Manejo de fecha de publicación nula para OfertaAyudantia ---
                Date fechaPublicacionAyudantia = oferta.getFechaPublicacion();
                if (fechaPublicacionAyudantia != null) {
                    regSalAyudantia.agregarCampo(dateFormat.format(fechaPublicacionAyudantia));
                } else {
                    regSalAyudantia.agregarCampo(""); // Agrega cadena vacía si la fecha es nula
                }

                regSalAyudantia.agregarCampo(((OfertaAyudantia)oferta).getNombreAsignatura());
                regSalAyudantia.agregarCampo(((OfertaAyudantia)oferta).getRolAyudante());
                regSalAyudantia.agregarCampo(((OfertaAyudantia)oferta).getCantidadHorasSemanalMensual());
                regSalAyudantia.agregarCampo(((OfertaAyudantia)oferta).getPromedioMinimo());

                archivoSalida.writeRegistro(regSalAyudantia);

            } else if (oferta instanceof OfertaCapstone) {

                Registro regSalCapstone = new Registro(13);
                String tipoOferta = "C";

                regSalCapstone.agregarCampo(tipoOferta);
                regSalCapstone.agregarCampo(oferta.getId());
                regSalCapstone.agregarCampo(oferta.getTitulo());
                regSalCapstone.agregarCampo(oferta.getDescripcion());
                regSalCapstone.agregarCampo(oferta.getNombreUnidad());
                //valida la fecha de publicacion antes de escribirla al archivo
                Date fechaPublicacion = oferta.getFechaPublicacion();
                if(fechaPublicacion != null){
                    regSalCapstone.agregarCampo(dateFormat.format(fechaPublicacion));
                }else{
                    regSalCapstone.agregarCampo("");
                }
                //valida la fecha de publicacion antes de escribirla al archivo
                LocalDate fechaInicio = ((OfertaCapstone) oferta).getFechaInicio();
                if(fechaInicio != null){
                    Date fechaInicio_date = Date.from(fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    regSalCapstone.agregarCampo(dateFormat.format(fechaInicio_date));
                }else{
                    regSalCapstone.agregarCampo("");
                }
                regSalCapstone.agregarCampo(((OfertaCapstone) oferta).getEmpresaSolicitante());
                regSalCapstone.agregarCampo(((OfertaCapstone) oferta).getGuiaEmpresa());
                regSalCapstone.agregarCampo(((OfertaCapstone) oferta).getTipoCapstone());
                regSalCapstone.agregarCampo(((OfertaCapstone) oferta).getDuracionEnMeses());

                String[] carreras = ((OfertaCapstone) oferta).getCarrerasNecesarias();
                if(carreras != null){
                    String carrerasNecesarias = String.join("|",carreras);
                    regSalCapstone.agregarCampo(carrerasNecesarias);
                }else{
                    regSalCapstone.agregarCampo("");//cadena vacia
                }
                regSalCapstone.agregarCampo(((OfertaCapstone) oferta).getCantidadMinimaEstudiantes());
                archivoSalida.writeRegistro(regSalCapstone);

            } else if (oferta instanceof OfertaPractica) {
                Registro regSalPractica = new Registro(10);

                String tipoOferta = "P";
                regSalPractica.agregarCampo(tipoOferta);
                regSalPractica.agregarCampo(oferta.getId());
                regSalPractica.agregarCampo(oferta.getTitulo());
                regSalPractica.agregarCampo(oferta.getDescripcion());
                regSalPractica.agregarCampo(oferta.getNombreUnidad());

                //validacion de fecha de publicacion
                Date fechaPublicacion = oferta.getFechaPublicacion();
                if (fechaPublicacion != null) {
                    regSalPractica.agregarCampo(dateFormat.format(fechaPublicacion));
                } else {
                    regSalPractica.agregarCampo(""); // Agrega cadena vacía si la fecha es nula
                }

                //Validacion de fecha de inicio
                LocalDate fechaInicio = ((OfertaPractica) oferta).getFechaInicio();
                if(fechaInicio != null){
                    Date fechaInicioDate = Date.from(fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    regSalPractica.agregarCampo(dateFormat.format(fechaInicioDate));
                }else{
                    regSalPractica.agregarCampo("");
                }
                regSalPractica.agregarCampo(((OfertaPractica) oferta).getEmpresaSolicitante());
                regSalPractica.agregarCampo(((OfertaPractica) oferta).getGuiaEmpresa());

                String respuesta = "";

                if(((OfertaPractica) oferta).isTieneRemuneracion()){
                    respuesta = "Si";
                }else{
                    respuesta = "No";
                }
                regSalPractica.agregarCampo(respuesta);

                archivoSalida.writeRegistro(regSalPractica);

            }
            aux = aux.getSiguiente();

        }
        archivoSalida.close();
    }
}
