package main.control;

import java.util.Scanner;

public class Main {
   private static Scanner entrada = new Scanner(System.in);
   private static int opcion = 0;

    public static void main(String[] args) {
        boolean salir = false;
        while(!salir) {
            mostrarMenu();
            opcion = elegirOpcion();
            salir = operar();
        }
    }

    private static boolean operar() {
        System.out.println();
        switch (opcion) {
            case 1:
                GestorAutor.listarAutores();
                break;
            case 2:
                GestorAutor.buscarAutorPorNombre();
                break;
            case 3:
                GestorAutor.registrarAutor();
                break;
            case 4:
                GestorAutor.eliminarAutor();
                break;
            case 5:
                GestorAutor.modificarAutor();
                break;
            case 6:
                System.out.println("Fin del programa");
                return true;
            default:
                System.out.println("Opcion invalida. Vuelva a intentarlo");
                break;
        }
        return false;
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

    public static void mostrarMenu() {
       System.out.println("""
               +---------------------------------------------+
               |           GESTOR AUTORES Y LIBROS           |
               +---------------------------------------------+
               1) Listar todos los autores.
               2) Buscar autor por nombre completo.
               3) Registrar autor.
               4) Eliminar autor.
               5) Modificar autor.
               6) Salir.""");
   }
}
