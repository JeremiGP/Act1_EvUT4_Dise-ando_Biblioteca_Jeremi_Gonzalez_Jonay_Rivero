package model;

import model.enums.EstadoLibro;
import model.enums.GeneroLibro;

public class Libro {
    private String isbn;
    private String titulo;
    private String autor;
    private String editorial;
    private GeneroLibro genero;
    private EstadoLibro estado;
    private int anioPublicacion;
    private int copiasTotales;
    private int copiasDisponibles;

    public Libro(String isbn, String titulo, String autor, String editorial,
            GeneroLibro genero, int anioPublicacion, int copiasTotales) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.genero = genero;
        this.anioPublicacion = anioPublicacion;
        this.copiasTotales = copiasTotales;
        this.copiasDisponibles = copiasTotales;
        this.estado = EstadoLibro.DISPONIBLE;
    }

    public void setIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("El ISBN no puede estar vacío.");
        }
        this.isbn = isbn;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public GeneroLibro getGenero() {
        return genero;
    }

    public EstadoLibro getEstado() {
        return estado;
    }

    public int getCopiasDisponibles() {
        return copiasDisponibles;
    }

    public void setEstado(EstadoLibro estado) {
        this.estado = estado;
    }

    public void reducirCopia() {
        this.copiasDisponibles--;
    }

    public void aumentarCopia() {
        this.copiasDisponibles++;
    }

    @Override
    public String toString() {
        return String.format("ISBN: %s | %s | %s | Copias: %d/%d | Estado: %s | Autor: %s | Editorial: %s | Año: %d",
                isbn, titulo, genero, copiasDisponibles, copiasTotales, estado, autor, editorial, anioPublicacion);
    }
}