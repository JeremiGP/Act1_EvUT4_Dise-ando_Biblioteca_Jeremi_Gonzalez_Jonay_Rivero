package exceptions;

// Excepción lanzada cuando se intenta prestar o reservar un libro que no tiene stock o no está disponible
public class LibroNoDisponibleException extends BibliotecaException {
    // Constructor que pasa el mensaje de error específico a la clase padre
    // (BibliotecaException)
    public LibroNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}
