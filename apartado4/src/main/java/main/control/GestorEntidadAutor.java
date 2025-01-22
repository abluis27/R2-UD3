package main.control;


import jakarta.persistence.*;
import main.modelo.Autor;
import main.repository.AutorRepository;

import java.util.List;

public class GestorEntidadAutor implements AutorRepository {
   private EntityManager gestorEntidades;
   private final String UNIDAD = "UnidadAutor";

   public GestorEntidadAutor() {
       EntityManagerFactory entityManagerFactory =
               Persistence.createEntityManagerFactory(UNIDAD);
      this.gestorEntidades = entityManagerFactory.createEntityManager();
   }

   @Override
    public void registrarAutor(Autor nuevoAutor) {
       EntityTransaction transaction = gestorEntidades.getTransaction();
       try {
           transaction.begin();
           gestorEntidades.persist(nuevoAutor);
           transaction.commit();
       } catch (RuntimeException e) {
           if (transaction.isActive()) {
              transaction.rollback();
           }
       }
    }

    @Override
    public List<Autor> obtenerAutores() {
       return gestorEntidades.createQuery(
               " SELECT autor FROM Autor autor", Autor.class).getResultList();
    }

    @Override
   public Autor buscarAutorPorNombreCompleto(String nombre, String apellidos)
           throws NoResultException {
       String sql = "SELECT autor FROM Autor autor" +
               " WHERE autor.nombre =:nombre AND autor.apellido = :apellidos";
       TypedQuery<Autor> query = gestorEntidades.createQuery(sql, Autor.class);
       query.setParameter("nombre", nombre);
       query.setParameter("apellidos", apellidos);
       return query.getSingleResult();
   }

   @Override
   public void actualizarAutor(Autor autorModificado) {
        EntityTransaction transaction = gestorEntidades.getTransaction();
        try {
            transaction.begin();
            gestorEntidades.merge(autorModificado);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void eliminarAutor(Autor autor) {
        EntityTransaction transaction = gestorEntidades.getTransaction();
        try {
            transaction.begin();
            gestorEntidades.remove(autor);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }
}
