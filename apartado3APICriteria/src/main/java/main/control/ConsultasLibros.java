package main.control;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.*;
import main.modelo.Libro;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class ConsultasLibros {
    private CriteriaBuilder criteriaBuilder;
    private CriteriaQuery<Libro> criteriaQuery;
    private Root<Libro> root;
    private Session session;

    public ConsultasLibros() {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("UnidadAutor");
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);

        this.session = sessionFactory.openSession();
        this.criteriaBuilder = session.getCriteriaBuilder();
        this.criteriaQuery = criteriaBuilder.createQuery((Libro.class));
        this.root = criteriaQuery.from(Libro.class);
    }

    public List<Libro> obtenerLibrosPublicadosEn(int anio) {
        Predicate filtroAnio = criteriaBuilder.equal(root.get("anioPublicacion"), anio);
        criteriaQuery.select(root).where(filtroAnio);
        Query<Libro> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public List<Libro> obtenerLibrosPublicadosEntre(int anioDesde, int anioHasta) {
        Predicate filtroAnio = criteriaBuilder
                .between(root.get("anioPublicacion"), anioDesde, anioHasta);
        criteriaQuery.select(root).where(filtroAnio);
        Query<Libro> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public double obtenerMediaDeAnioDeLibrosConElGenero(String generoBuscado) {
        CriteriaQuery<Double> criteriaQueryDouble = criteriaBuilder.createQuery(Double.class);
        Root<Libro> root = criteriaQueryDouble.from(Libro.class);
        Predicate filtroGenero = criteriaBuilder.equal(root.get("genero"), generoBuscado);

        Expression<Double> promedioAnio = criteriaBuilder.avg(root.get("anioPublicacion"));
        criteriaQueryDouble.select(promedioAnio).where(filtroGenero);
        Query<Double> query = session.createQuery(criteriaQueryDouble);
        return query.getSingleResult();
    }
}
