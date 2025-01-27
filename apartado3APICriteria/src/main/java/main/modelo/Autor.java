package main.modelo;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="autor")
public class Autor {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_autor")
    private Long idAutor;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "nacionalidad", nullable = false)
    private String nacionalidad;
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;
    @OneToMany(
            mappedBy = "autor",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Libro> libros;

    // Necesatio para JPA
    public Autor(){}

    public Autor(String nombre, String apellido, String nacionalidad, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
        libros = new ArrayList<>();
    }

    public Autor(String nombre, String apellido, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
    }

    public Long getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Long idAutor) {
        this.idAutor = idAutor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void addLibro(Libro nuevoLibro) {
       this.libros.add(nuevoLibro);
    }

    public List<Libro> getLibros() {
        return this.libros;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatoFechaSalida = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("Nombre completo: %s %s" +
                "%nNacionalidad: %s" +
                "%nFecha de nacimiento: %s", nombre, apellido, nacionalidad,
                fechaNacimiento.format(formatoFechaSalida));
    }
}
