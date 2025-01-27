package main.modelo;

import jakarta.persistence.*;

@Entity
@Table(name="libro")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro")
    private long idLibro;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String genero;

    @Column(name="anio_publicacion", nullable = false)
    private int anioPublicacion;

    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false)
    private Autor autor;

    public Libro(){}

    public Libro(String titulo, String genero, int anioPublicacion) {
        this.titulo = titulo;
        this.genero = genero;
        this.anioPublicacion = anioPublicacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return String.format("Titulo: %s%nAutor: %s %s%nGenero: %s%nAño de publicacion: %d",
                titulo, autor.getNombre(), autor.getApellido(), genero, anioPublicacion);
    }
}
