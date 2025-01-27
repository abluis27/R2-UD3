package main.control;

import main.modelo.Autor;
import main.modelo.Libro;

import java.util.List;

public class Main {
    private static ConsultasAutores consultasAutores = new ConsultasAutores();
    private static ConsultasLibros consultasLibros = new ConsultasLibros();

    public static void main(String[] args) {
        try {
            // consultaNombreAutoresYFechaNac();
            // consultaAutoresPorNacionalidad("Argentina");
            // consultaAutoresOrdenados();
            // consultaLibrosPublicadosEntre(1940, 1960);
            // consultaNumeroLibrosConElGenero("Ficción literaria");
            consultaJoinAutoresLibros();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void consultaNombreAutoresYFechaNac() {
        List<Autor> resultados = consultasAutores.obtenerAutoresConNombreYFechaNac();
        System.out.println("------------------------------------------------------");
        for (Autor autor: resultados) {
            System.out.println(String.format("Nombre completo: %s %s%nFecha de nacimiento: %s",
                    autor.getNombre(), autor.getApellido(), autor.getFechaNacimiento()));
            System.out.println("------------------------------------------------------");
        }
    }

    public static void consultaAutoresPorNacionalidad(String nacionalidad) {
       List<Autor> autores = consultasAutores.obtenerAutoresPorNacionalidad(nacionalidad);
       Main.recorrerListaAutores(autores);
    }

    public static void consultaAutoresOrdenados() {
        List<Autor> autores = consultasAutores.obtenerAutoresOrdenadosPorApellido();
        Main.recorrerListaAutores(autores);
    }

    private static void consultaLibrosPublicadosEn(int anio) {
        List<Libro> libros = consultasLibros.obtenerLibrosPublicadosEn(anio);
        recorrerListaLibros(libros);
    }

    private static void consultaLibrosPublicadosEntre(int anioDesde, int anioHasta) {
        List<Libro> libros = consultasLibros.obtenerLibrosPublicadosEntre(anioDesde, anioHasta);
        recorrerListaLibros(libros);
    }

    private static void consultaNumeroLibrosConElGenero(String generoBuscado) {
        double media = consultasLibros.obtenerMediaDeAnioDeLibrosConElGenero(generoBuscado);
        System.out.println(String.format("La media del año de publicacion los libros con el genero '%s' es de: %f.2",
                generoBuscado, media));
    }

    private static void consultaJoinAutoresLibros() {
        List<Autor> autoresConLibros = consultasAutores.obtenerAutoresConLibros();
        System.out.println("-----------------------------------");
        for (Autor autor : autoresConLibros) {
            System.out.println(autor);
            System.out.println("--- LIBROS PUBLICADOS ---");
            mostrarLibrosDeAutor(autor.getLibros());
            System.out.println("-----------------------------------");
        }
    }

    private static void mostrarLibrosDeAutor(List<Libro> libros) {
        for(Libro libro : libros) {
            System.out.println(String.format("- %s (%d), Genero: %s",
                    libro.getTitulo(), libro.getAnioPublicacion(), libro.getGenero()));
        }
    }

    private static void recorrerListaAutores(List<Autor> autores) {
        System.out.println("-----------------------------------");
        for (Autor autor : autores) {
            System.out.println(autor);
            System.out.println("-----------------------------------");
        }
    }

    private static void recorrerListaLibros(List<Libro> libros) {
        System.out.println("-----------------------------------");
        for (Libro libro : libros) {
            System.out.println(libro);
            System.out.println("-----------------------------------");
        }
    }
}

