package model;

// Importaciones LocalDate y ArrayList
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Clase Usuario
public class Usuario {
    // Atributos privados de la clase
    private String id;
    private String nombre;
    private List<Libro> librosPrestados;
    private List<Prestamo> historialPrestamos;
    private LocalDate fechaFinSancion;

    // Constructor de la clase
    public Usuario(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.librosPrestados = new ArrayList<>();
        this.historialPrestamos = new ArrayList<>();
    }

    // --- GETTERS ---
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Libro> getLibrosPrestados() {
        return librosPrestados;
    }

    public List<Prestamo> getHistorialPrestamos() {
        return historialPrestamos;
    }

    public LocalDate getFechaFinSancion() {
        return fechaFinSancion;
    }

    // --- SETTERS ---
    public void setFechaFinSancion(LocalDate fecha) {
        this.fechaFinSancion = fecha;
    }

    // --- METODO toString ---
    @Override
    public String toString() {
        return nombre + " (ID: " + id + ")";
    }
}