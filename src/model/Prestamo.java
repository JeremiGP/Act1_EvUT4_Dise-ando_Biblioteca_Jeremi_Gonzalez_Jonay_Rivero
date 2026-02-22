package model;

// Importacion de LocalDate
import java.time.LocalDate;

// Clase que representa un prestamo
public class Prestamo {
    // Atributos privados de la clase
    private Libro libro;
    private Usuario usuario;
    private LocalDate fechaPrestamo;
    private LocalDate fechaVencimiento;
    private LocalDate fechaDevolucionReal;

    // Constructor de la clase
    public Prestamo(Libro libro, Usuario usuario, LocalDate fechaPrestamo, LocalDate fechaVencimiento) {
        this.libro = libro;
        this.usuario = usuario;
        this.fechaPrestamo = LocalDate.now();
        this.fechaVencimiento = fechaPrestamo.plusDays(30); // 30 dias de prestamo
    }

    // --- GETTERS ---
    public Libro getLibro() {
        return libro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public LocalDate getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }

    // --- SETTERS ---
    public void setLibro(Libro libro) {
        if (libro == null) {
            throw new IllegalArgumentException("ERROR: El libro no puede estar vacío.");
        }
        this.libro = libro;
    }

    public void setUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("ERROR: El usuario no puede estar vacío.");
        }
        this.usuario = usuario;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        if (fechaPrestamo == null) {
            throw new IllegalArgumentException("ERROR: La fecha de prestamo no puede estar vacía.");
        }
        this.fechaPrestamo = fechaPrestamo;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        if (fechaVencimiento == null) {
            throw new IllegalArgumentException("ERROR: La fecha de vencimiento no puede estar vacía.");
        }
        this.fechaVencimiento = fechaVencimiento;
    }

    public void setFechaDevolucionReal(LocalDate fechaDevolucionReal) {
        if (fechaDevolucionReal == null) {
            throw new IllegalArgumentException("ERROR: La fecha de devolucion real no puede estar vacía.");
        }
        this.fechaDevolucionReal = fechaDevolucionReal;
    }

    // --- METODOS ---
    public void registrarDevolucion() {
        this.fechaDevolucionReal = LocalDate.now();
    }

    // --- METODO toString ---
    @Override
    public String toString() {
        return "Prestamo [libro=" + libro + ", usuario=" + usuario + ", fechaPrestamo=" + fechaPrestamo
                + ", fechaVencimiento=" + fechaVencimiento + ", fechaDevolucionReal=" + fechaDevolucionReal + "]";
    }
}
