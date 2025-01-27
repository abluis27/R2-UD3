package main.control;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.*;
import main.modelo.Autor;
import main.modelo.Libro;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class ConsultasAutores {
    private CriteriaBuilder criteriaBuilder;
    private CriteriaQuery<Autor> criteriaQuery;
    private Root<Autor> root;
    private Session session;

    public ConsultasAutores() {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("UnidadAutor");
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);

        this.session = sessionFactory.openSession();
        this.criteriaBuilder = session.getCriteriaBuilder();
        this.criteriaQuery = criteriaBuilder.createQuery((Autor.class));
        this.root = criteriaQuery.from(Autor.class);
    }

    public List<Autor> obtenerAutoresConNombreYFechaNac() {
        this.criteriaQuery.multiselect(
                this.root.get("nombre"),
                this.root.get("apellido"),
                this.root.get("fechaNacimiento"));
        Query<Autor> query = this.session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public List<Autor> obtenerAutoresPorNacionalidad(String nacionalidad) {
        Predicate filtroNacionalidad = this.criteriaBuilder
                .equal(root.get("nacionalidad"), nacionalidad);
        criteriaQuery.select(root).where(filtroNacionalidad);
        Query<Autor> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public List<Autor> obtenerAutoresOrdenadosPorApellido() {
        Order ordenDescencente = this.criteriaBuilder
                .desc(root.get("apellido"));
        this.criteriaQuery.select(root).orderBy(ordenDescencente);
        Query<Autor> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public List<Autor> obtenerAutoresConLibros() {
        root.join("libros", JoinType.LEFT);
        Query<Autor> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

}
