package cl.ucn.disc.pa.taller3.vista;

import cl.ucn.disc.pa.taller3.servicio.oferta.SistemaOferta;
import cl.ucn.disc.pa.taller3.servicio.usuario.SistemaUsuario;
import cl.ucn.disc.pa.taller3.utilidades.ValidadorEntrada;
import ucn.StdIn;
import ucn.StdOut;

import java.util.InputMismatchException;

public class VistaConsola {
    private SistemaUsuario sistemaUsuario;
    private SistemaOferta sistemaOferta;

    public VistaConsola(SistemaUsuario sistemaUsuario, SistemaOferta sistemaOferta) {
        this.sistemaUsuario = sistemaUsuario;
        this.sistemaOferta = sistemaOferta;
    }

    public void iniciar() {
        StdOut.println(":::Bienvenido al Sistema de Ofertas Academicas:::");
        this.menuInicio();
        StdOut.println("Hasta Pronto!");
    }

    private void menuInicio() {
        int opcion;

        while (true) {
            StdOut.println(":::Menu de Inicio:::");
            StdOut.println("1. Iniciar Sesion");
            StdOut.println("2. Registrarse");
            StdOut.println("3. Salir");

            opcion = ValidadorEntrada.validarEntero("Ingrese una opcion: ");

            if (opcion == 1) {
                this.iniciarSesion();
                continue;
            }

            if (opcion == 2) {
                this.registrarse();
                continue;
            }

            if (opcion == 3) {
                try {
                    this.sistemaUsuario.persistenciaUsuarios();
                    this.sistemaOferta.persistenciaOfertas();
                } catch (RuntimeException e) {
                    StdOut.println(e.getMessage());
                }
                break;
            }
        }
    }

    private void iniciarSesion() {
        String rut, contrasenia;

        StdOut.println(":::Iniciar Sesion:::");
        StdOut.println("Ingreses sus Credenciales");
        StdOut.println("Ingreses su rut:");
        rut = StdIn.readLine();
        StdOut.println("Ingreses su contrasenia:");
        contrasenia = StdIn.readLine();

        try {
            if (this.sistemaUsuario.iniciarSesion(rut, contrasenia)) {
                this.menuPrincipal();
            } else {
                StdOut.println("La contrasenia es invalida");
            }
        } catch (IllegalArgumentException e) {
            StdOut.println(e.getMessage());
        }

        StdOut.println();
    }

    public void menuPrincipal() {
        int opcion;

        while (true) {
            StdOut.println(":::Menu Principal:::");
            StdOut.println("1. Ingresar Oferta");
            StdOut.println("2. Buscar Oferta");
            StdOut.println("3. Ver Ofertas");
            StdOut.println("4. Editar Oferta");
            StdOut.println("5. Eliminar Oferta");
            StdOut.println("6. Ver Perfil");
            StdOut.println("7. Editar Perfil");
            StdOut.println("8. Cerrar Sesion");

            opcion = ValidadorEntrada.validarEntero("Ingrese una opcion: ");

            if (opcion == 1) {
                this.ingresarOferta();
                continue;
            }

            if (opcion == 2) {
                this.buscarOferta();
                continue;
            }

            if (opcion == 3) {
                this.verOfertas();
                continue;
            }

            if (opcion == 4) {
                this.editarOferta();
                continue;
            }

            if (opcion == 5) {
                this.eliminarOferta();
                continue;
            }

            if (opcion == 6) {
                this.verPerfil();
                continue;
            }

            if (opcion == 7) {
                this.editarPerfil();
                continue;
            }

            if (opcion == 8) {
                StdOut.println(this.sistemaUsuario.cerrarSesion());
                break;
            }else {
                StdOut.println("Opcion invalida");
            }
        }
    }

    private void ingresarOferta() {
        // se sobredimensiona aqui, pero se sobreescribira luego
        String[] datosOferta = new String[99];

        StdOut.println(":::Ingresar Oferta:::");

        StdOut.println("Elija el tipo de oferta:");
        StdOut.println("1. Ayudantia");
        StdOut.println("2. Capstone");
        StdOut.println("3. Practica Pre-Profesional");
        int opcion = ValidadorEntrada.validarEntero("Ingrese una opcion: ");

        if (opcion == 1) {
            datosOferta = new String[9];
            datosOferta[0] = "A";
            StdOut.println(":::Ingresar Ayudantia:::");
        }

        if (opcion == 2) {
            datosOferta = new String[12];
            datosOferta[0] = "C";
            StdOut.println(":::Ingresar Capstone:::");
        }

        if (opcion == 3) {
            datosOferta = new String[9];
            datosOferta[0] = "P";
            StdOut.println(":::Ingresar Practica:::");
        }

        if (opcion < 1 || opcion > 3) {
            StdOut.println("opcion invalida. volviendo al menu.");
            return;
        }

        this.ingresarDatosGenericos(datosOferta);

        try {
            if (opcion == 1) {
                this.ingresarDatosAyudantia(datosOferta);
            } else {
                this.ingresarDatosActividad(datosOferta);

                if (opcion == 2) {
                    this.ingresarDatosCapstone(datosOferta);
                } else {
                    this.ingresarDatosPractica(datosOferta);
                }
            }
        } catch (InputMismatchException e) {
            StdOut.println("opcion invalida. volviendo al menu.");
            return;
        }

        StdOut.println(this.sistemaOferta.publicarOferta(datosOferta));
    }

    private void ingresarDatosGenericos(String[] datosOferta) {
        // ingresar datos genericos
        StdOut.println("Ingrese el titulo de la oferta");
        datosOferta[1] = StdIn.readLine();

        StdOut.println("Ingrese el descripcion de la oferta");
        datosOferta[2] = StdIn.readLine();

        StdOut.println("Ingrese la unidad de la oferta");
        datosOferta[3] = StdIn.readLine();

        StdOut.println("Ingrese la duracion en dias de la oferta");
        datosOferta[4] = StdIn.readLine();
    }

    private void ingresarDatosAyudantia(String[] datosOferta) {
        // ingresar datos de una ayudantia
        StdOut.println("Ingrese el nombre de la asignatura");
        datosOferta[5] = StdIn.readLine();

        StdOut.println("Ingrese el rol del ayudante");
        datosOferta[6] = StdIn.readLine();

        StdOut.println("Ingrese la cantidad de horas semanal mensual");
        datosOferta[7] = StdIn.readLine();

        StdOut.println("Ingrese el promedio minimo solicitado");
        datosOferta[8] = StdIn.readLine();

    }

    private void ingresarDatosActividad(String[] datosOferta) {
        // ingresar datos de un capstone
        String dia, mes, anio;

        StdOut.println("Ingrese el dia de inicio de la actividad");
        dia = StdIn.readLine();

        StdOut.println("Ingrese el mes de inicio de la actividad");
        mes = StdIn.readLine();

        StdOut.println("Ingrese el anio de inicio de la actividad");
        anio = StdIn.readLine();

        datosOferta[5] = dia + "-" + mes + "-" + anio;

        StdOut.println("Ingrese el nombre de la empresa solicitante");
        datosOferta[6] = StdIn.readLine();

        StdOut.println("Ingrese el nombre del guia de la empresa");
        datosOferta[7] = StdIn.readLine();

    }

    private void ingresarDatosCapstone(String[] datosOferta) {
        StdOut.println("Elija el tipo de capstone:");
        StdOut.println("1. Capstone");
        StdOut.println("2. Investigacion");
        StdOut.println("3. Memoria");

        int opcion = ValidadorEntrada.validarEntero("Ingrese una opcion: ");

        if (opcion == 1) {
            datosOferta[8] = "Capstone";
        }

        if (opcion == 2) {
            datosOferta[8] = "Investigacion";
        }

        if (opcion == 3) {
            datosOferta[8] = "Memoria";
        }

        StdOut.println();

        if (opcion < 1 || opcion > 3) {
            throw new InputMismatchException();
        }

        StdOut.println("Ingrese la duracion en meses del proyecto de capstone");
        datosOferta[9] = StdIn.readLine();
        StdOut.println();

        int cantidadCarreras = ValidadorEntrada.validarEntero("Ingrese la cantidad de carreras");

        datosOferta[10] = "";

        for (int i = 0; i < cantidadCarreras; i++) {
            StdOut.println("Ingrese el nombre de la carrera N°" + (i+1));
            datosOferta[10] += StdIn.readLine();
            if (i != cantidadCarreras - 1) {
                datosOferta[10] += ",";
            }
        }

        StdOut.println("Ingrese la cantidad de estudiantes requeridos para el proyecto de capstone");
        datosOferta[11] = StdIn.readLine();

    }

    private void ingresarDatosPractica(String[] datosOferta) {
        StdOut.println("1. Si");
        StdOut.println("2. No");
        int opcion = ValidadorEntrada.validarEntero("Tiene remuneracion la actividad de practica?");

        if (opcion == 1) {
            datosOferta[8] = "Si";
        } else if (opcion == 2) {
            datosOferta[8] = "No";
        } else {
            throw new InputMismatchException();
        }
    }

    private void buscarOferta() {
        String idTitulo;
        StdOut.println(":::Buscar Oferta:::");
        StdOut.println("Ingrese la id o titulo de la oferta");
        idTitulo = StdIn.readString();

        StdOut.println(this.sistemaOferta.verOferta(idTitulo));

    }

    private void verOfertas() {
        StdOut.println(":::Listado de Ofertas:::");
        StdOut.println(this.sistemaOferta.verTodasLasOfertas());

    }

    private void editarOferta() {
        // se sobredimensiona aqui, pero se sobreescribira luego
        String[] datosOferta = new String[99];

        StdOut.println(":::Editar Oferta:::");

        StdOut.println("Ingrese el id de la oferta");
        String id = StdIn.readLine();

        StdOut.println("Elija el tipo de oferta:");
        StdOut.println("1. Ayudantia");
        StdOut.println("2. Capstone");
        StdOut.println("3. Practica Pre-Profesional");
        int opcion = ValidadorEntrada.validarEntero("Ingrese una opcion: ");

        if (opcion == 1) {
            datosOferta = new String[9];
            datosOferta[0] = "A";
            StdOut.println(":::Ingresar Ayudantia:::");
        }

        if (opcion == 2) {
            datosOferta = new String[12];
            datosOferta[0] = "C";
            StdOut.println(":::Ingresar Capstone:::");
        }

        if (opcion == 3) {
            datosOferta = new String[9];
            datosOferta[0] = "P";
            StdOut.println(":::Ingresar Practica:::");
        }

        if (opcion < 1 || opcion > 3) {
            StdOut.println("opcion invalida. volviendo al menu.");
            return;
        }

        this.ingresarDatosGenericos(datosOferta);

        try {
            if (opcion == 1) {
                this.ingresarDatosAyudantia(datosOferta);
            } else {
                this.ingresarDatosActividad(datosOferta);

                if (opcion == 2) {
                    this.ingresarDatosCapstone(datosOferta);
                } else {
                    this.ingresarDatosPractica(datosOferta);
                }
            }
        } catch (InputMismatchException e) {
            StdOut.println("opcion invalida. volviendo al menu.");
            return;
        }

        StdOut.println(this.sistemaOferta.editarOferta(id, datosOferta));

    }

    private void eliminarOferta() {
        StdOut.println(":::Eliminar Oferta:::");

        StdOut.println("Ingrese el id de la oferta");
        String id = StdIn.readLine();

        StdOut.println("Esta seguro de eliminar la oferta con la id: " + id);
        StdOut.println("1. Si");
        StdOut.println("2. No");
        int opcion = ValidadorEntrada.validarEntero("Ingrese una opcion: ");

        if (opcion == 1) {
            StdOut.println(this.sistemaOferta.eliminarOferta(id));
        } else {
            StdOut.println("Volviendo al menu.");
        }
    }

    private void verPerfil() {
        StdOut.println(":::Perfil:::");
        StdOut.println(this.sistemaUsuario.verPerfil());

    }

    private void editarPerfil() {
        String nombre, correo;

        StdOut.println(":::Editar Perfil:::");
        StdOut.println("Ingrese su nombre:");
        nombre = StdIn.readLine();
        StdOut.println("Ingrese su correo:");
        correo = StdIn.readLine();

        StdOut.println(this.sistemaUsuario.editarPerfil(nombre, correo));

    }

    private void registrarse() {
        String nombreUsuario, rut, correo, contrasenia;

        StdOut.println(":::Registro:::");
        StdOut.println("Ingrese el nombre del usuario:");
        nombreUsuario = StdIn.readLine();
        StdOut.println("Ingrese el rut:");
        rut = StdIn.readLine();
        StdOut.println("Ingrese el correo:");
        correo = StdIn.readLine();
        StdOut.println("Ingrese la contrasenia:");
        contrasenia = StdIn.readLine();

        StdOut.println(this.sistemaUsuario.registrarse(nombreUsuario,rut,correo,contrasenia));

    }
}
