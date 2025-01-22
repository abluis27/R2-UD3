package main.repository;

import jakarta.persistence.NoResultException;
import main.modelo.Autor;

import java.util.List;

public interface AutorRepository {
    void registrarAutor(Autor nuevoAutor);
    List<Autor> obtenerAutores();
    Autor buscarAutorPorNombreCompleto(String nombre, String apellidos)
            throws NoResultException;
    void actualizarAutor(Autor autorModificado);
    void eliminarAutor(Autor autor);
}
