package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String id;
    private String nombre;
    private List<Libro> librosPrestados;
    private List<Prestamo> historialPrestamos;
    private LocalDate fechaFinSancion;

    public Usuario(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.librosPrestados = new ArrayList<>();
        this.historialPrestamos = new ArrayList<>();
    }

    // Getters y Setters
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

    public void setFechaFinSancion(LocalDate fecha) {
        this.fechaFinSancion = fecha;
    }

    @Override
    public String toString() {
        return nombre + " (ID: " + id + ")";
    }
}