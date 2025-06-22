package cl.ucn.disc.pa.taller3.servicio.oferta;

public interface SistemaOferta {
    /**
     * Se busca publicar una oferta en el sistema considerndo todos sus parametros, esto segun el tipo de oferta.
     *
     * Prerrequisitos:
     * - el codigo de la oferta corresponda con alguno de los tipos que se trabajan (A,C,P).
     * - el id debe ser unico.
     * - el titulo y la descripcion debe tener un minimo de 8 y un maximo de 255 caracteres.
     * - la duracion tiene que ser un numero positivo mayor a 1.
     * Si es Oferta de Ayudantia:
     * - el nombre de asignatura y el rol del ayudante deben tener un minimo de 8 caracteres y un maximo de 255.
     * - la cantidad de horas semanales mensuales debe ser un numero positivo mayor a 2.
     * - el promedio debe ser un numero flotante positivo mayor a 4,0 y maximo 7,0.
     * Si es Oferta de alguna Actividad:
     * - la fecha de inicio debe ser al menos el dia de mañana.
     * - el nombre de la empresa y del guia debe tener un minimo de 8 y un maximo de 255 caracteres.
     * Si es Oferta de Actividad de Practica:
     * - el indicador de si existe remuneracion debe ser: 1, true, si para indicar el true booleano, si es 0, false o
     * no, se debe indicar un falso en booleano. Cualquier valor distinto, debe retornar una respuesta indicando el
     * error.
     * Si es Oferta de Actividad de Capstone:
     * - el tipo y las carreras necesarios debe tener un minimo de 8 y un maximo de 255 caracteres.
     * - el tipo debe ser: capstone, investigacion y/o memoria.
     * - la duracion en meses debe ser uno de los siguientes numeros: 6, 12, 18.
     * - la cantidad minima de estudiantes debe ser un numero entre 2 y 5, ambos inclusive.
     *
     * Postcondiciones:
     * - se agrega una nueva oferta al sistema.
     */
    String publicarOferta(String[] datosOferta);

    /**
     * busca una oferta por su id y muestra sus datos.
     *
     * prerequisitos:
     * - debe exister al menos 1 oferta en el sistema.
     * - el id debe ser un numero mayor a 0.
     */
    String verOferta(String id);

    /**
     * se busca mostrar todas las ofertas.
     * prerequisitos:
     * - debe exister al menos 1 oferta en el sistema.
     */
    String verTodasLasOfertas();

    /**
     * Se busca publicar una oferta en el sistema considerndo todos sus parametros, esto segun el tipo de oferta.
     *
     * Prerrequisitos:
     * - debe exister al menos 1 oferta en el sistema.
     * - el id debe ser un numero mayor a 0.
     * - el codigo de la oferta corresponda con alguno de los tipos que se trabajan (A,C,P).
     * - el id debe ser unico.
     * - el titulo y la descripcion debe tener un minimo de 8 y un maximo de 255 caracteres.
     * - la duracion tiene que ser un numero positivo mayor a 1.
     * Si es Oferta de Ayudantia:
     * - el nombre de asignatura y el rol del ayudante deben tener un minimo de 8 caracteres y un maximo de 255.
     * - la cantidad de horas semanales mensuales debe ser un numero positivo mayor a 2.
     * - el promedio debe ser un numero flotante positivo mayor a 4,0 y maximo 7,0.
     * Si es Oferta de alguna Actividad:
     * - la fecha de inicio debe ser al menos el dia de mañana.
     * - el nombre de la empresa y del guia debe tener un minimo de 8 y un maximo de 255 caracteres.
     * Si es Oferta de Actividad de Practica:
     * - el indicador de si existe remuneracion debe ser: 1, true, si para indicar el true booleano, si es 0, false o
     * no, se debe indicar un falso en booleano. Cualquier valor distinto, debe retornar una respuesta indicando el
     * error.
     * Si es Oferta de Actividad de Capstone:
     * - el tipo y las carreras necesarios debe tener un minimo de 8 y un maximo de 255 caracteres.
     * - el tipo debe ser: capstone, investigacion y/o memoria.
     * - la duracion en meses debe ser uno de los siguientes numeros: 6, 12, 18.
     * - la cantidad minima de estudiantes debe ser un numero entre 2 y 5, ambos inclusive.
     *
     * Postcondiciones:
     * - se actualiza la informacion de una oferta del sistema.
     */
    String editarOferta(String id, String[] datosOferta);

    /**
     * busca una oferta por su id y se elimina.
     *
     * prerequisitos:
     * - debe exister al menos 1 oferta en el sistema.
     * - el id debe ser un numero mayor a 0.
     *
     * postcondiciones:
     * - se elimina la oferta del sistema.
     */
    String eliminarOferta(String id);

    /**
     * busca actualizar el archivo de texto: OfertasAcademicas.txt
     */
    void persistenciaOfertas();
}
