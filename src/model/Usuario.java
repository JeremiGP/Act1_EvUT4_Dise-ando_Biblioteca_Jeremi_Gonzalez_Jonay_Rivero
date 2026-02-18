package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String id;
    private String nombre;
    private List<Libro> librosPrestados;
    private List<Libro> historialPrestamos;
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

    public List<Libro> getHistorialPrestamos() {
        return historialPrestamos;
    }

    public LocalDate getFechaFinSancion() {
        return fechaFinSancion;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID no puede estar vacío.");
        }
        this.id = id;
    }

    public void setFechaFinSancion(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha de fin de sanción no puede estar vacía.");
        }
        this.fechaFinSancion = fecha;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre + " (ID: " + id + ")";
    }
}
