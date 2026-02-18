package model;

import java.time.LocalDate;

public class Prestamo {
    private Libro libro;
    private Usuario usuario;
    private LocalDate fechaPrestamo;
    private LocalDate fechaVencimiento;
    private LocalDate fechaDevolucionReal;

    public Prestamo(Libro libro, Usuario usuario, LocalDate fechaPrestamo, LocalDate fechaVencimiento) {
        this.libro = libro;
        this.usuario = usuario;
        this.fechaPrestamo = LocalDate.now();
        this.fechaVencimiento = fechaPrestamo.plusDays(30); // 30 dias de prestamo
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        if (libro == null) {
            throw new IllegalArgumentException("El libro no puede estar vacío.");
        }
        this.libro = libro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede estar vacío.");
        }
        this.usuario = usuario;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        if (fechaPrestamo == null) {
            throw new IllegalArgumentException("La fecha de prestamo no puede estar vacía.");
        }
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        if (fechaVencimiento == null) {
            throw new IllegalArgumentException("La fecha de vencimiento no puede estar vacía.");
        }
        this.fechaVencimiento = fechaVencimiento;
    }

    public LocalDate getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }

    public void setFechaDevolucionReal(LocalDate fechaDevolucionReal) {
        if (fechaDevolucionReal == null) {
            throw new IllegalArgumentException("La fecha de devolucion real no puede estar vacía.");
        }
        this.fechaDevolucionReal = fechaDevolucionReal;
    }

    public void registrarDevolucion() {
        this.fechaDevolucionReal = LocalDate.now();
    }

    @Override
    public String toString() {
        return "Prestamo [libro=" + libro + ", usuario=" + usuario + ", fechaPrestamo=" + fechaPrestamo
                + ", fechaVencimiento=" + fechaVencimiento + ", fechaDevolucionReal=" + fechaDevolucionReal + "]";
    }
}
