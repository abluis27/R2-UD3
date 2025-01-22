package main.control;

import jakarta.persistence.NoResultException;
import main.modelo.Autor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class GestorAutor {
    private static GestorEntidadAutor gestorBBDD = new GestorEntidadAutor();
    private static Scanner entrada = new Scanner(System.in);

    public static void listarAutores() {
        List<Autor> autores = gestorBBDD.obtenerAutores();
        System.out.println("----- AUTORES REGISTRADOS -----");
        for (Autor autor : autores) {
            System.out.println(autor);
            System.out.println("----------");
        }
    }

    private static Autor buscarAutor() throws IllegalArgumentException, NoResultException {
        String nombre = pedirString("Introduzca el nombre del autor: ");
        String apellidos = pedirString("Introduzca el/los apellido/s del autor: ");
        return gestorBBDD.buscarAutorPorNombreCompleto(nombre, apellidos);
    }

    public static void buscarAutorPorNombre() {
        System.out.println("----- BUSCAR AUTOR -----");
        try {
            Autor autorBuscado = buscarAutor();
            System.out.println(autorBuscado);
        } catch (NoResultException e) {
            System.out.println("Autor con el nombre especificado no existe");
        } catch (IllegalArgumentException e) {
            System.out.println("Los campos no pueden estar en blanco");
        }

    }

    public static void registrarAutor() {
        try {
            System.out.println("----- REGISTRANDO AUTOR -----");
            Autor nuevoAutor = crearAutor();
            gestorBBDD.registrarAutor(nuevoAutor);
            System.out.println("AUTOR REGISTRADO");
        } catch (IllegalArgumentException e) {
            System.out.println("Los campos no pueden estar vacios.");
        } catch (DateTimeParseException e) {
            System.out.println("Fecha de nacimiento invalida, tiene que seguir el siguiente formato: dd/mm/yyyy");
        }
    }

    private static Autor crearAutor() throws IllegalArgumentException, DateTimeParseException {
        String nombre = pedirString("Introduzca el nombre del autor: ");
        String apellido = pedirString("Introduzca el/los apellido/s del autor: ");
        String nacionalidad = pedirString("Introduzca la nacionalidad del autor: ");
        LocalDate fechaNacimiento = pedirFechaNacimiento();
        return new Autor(nombre, apellido, nacionalidad, fechaNacimiento);
    }

    private static String pedirString(String mensaje) {
        System.out.print(mensaje);
        String valor = entrada.nextLine();
        if(valor.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return valor;
    }

    private static LocalDate pedirFechaNacimiento() throws DateTimeParseException {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.print("Introduzca la fecha de nacimiento (dd/mm/yyyy): ");
        String fecha = entrada.nextLine();
        return LocalDate.parse(fecha, formatoFecha);
    }

    public static void eliminarAutor()  {
        System.out.println("----- ELIMINANDO AUTOR -----");
        try {
            Autor autorBuscado = buscarAutor();
            gestorBBDD.eliminarAutor(autorBuscado);
            System.out.println("El autor especificado fue eliminado");
        } catch (NoResultException e) {
            System.out.println("Autor con el nombre especificado no existe");
        } catch (IllegalArgumentException e) {
            System.out.println("Los campos no pueden estar en blanco");
        }
    }

    public static void modificarAutor() {
        System.out.println("---- MODIFICANDO AUTOR -----");
        try {
            Autor autorParaModificar = buscarAutor();
            mostrarMenuAtributos();
            int opcion = elegirOpcion();
            modificar(autorParaModificar, opcion);
            System.out.println("Autor modificado");
        } catch (NoResultException e) {
            System.out.println("Autor con el nombre especificado no existe");
        } catch (IllegalArgumentException e) {
            System.out.println("Los campos no pueden estar en blanco");
        } catch (DateTimeParseException e) {
            System.out.println("Fecha de nacimiento invalida, tiene que seguir el siguiente formato: dd/mm/yyyy");
        }
    }

    private static int elegirOpcion() {
        try {
            System.out.print("Elija una opcion: ");
            String opcion = entrada.nextLine();
            return Integer.parseInt(opcion);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void modificar(Autor autor, int opcion) {
        switch (opcion) {
            case 1:
                String nombre = pedirString("Introduzca el nuevo nombre: ");
                autor.setNombre(nombre);
                break;
            case 2:
                String apellidos = pedirString("Introduzca el/los nuevo/s apellido/s: ");
                autor.setApellido(apellidos);
                break;
            case 3:
                String nacionalidad = pedirString("Introduzca la nueva nacionalidad: ");
                autor.setNacionalidad(nacionalidad);
                break;
            case 4:
                LocalDate fechaNaciomiento = pedirFechaNacimiento();
                autor.setFechaNacimiento(fechaNaciomiento);
                break;
            default:
                System.out.println("Opcion no valida.");
                break;
        }
        if (opcion >= 1 && opcion <= 4) {
            gestorBBDD.actualizarAutor(autor);
        }
    }

    private static void mostrarMenuAtributos() {
        System.out.println("""
                1) Nombre
                2) Apellidos
                3) Nacionalidad
                4) Fecha de nacimiento""");
    }
}
