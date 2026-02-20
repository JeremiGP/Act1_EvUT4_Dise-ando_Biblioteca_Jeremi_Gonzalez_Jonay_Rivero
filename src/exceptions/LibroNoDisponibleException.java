package exceptions;

public class LibroNoDisponibleException extends BibliotecaException {
    public LibroNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}
