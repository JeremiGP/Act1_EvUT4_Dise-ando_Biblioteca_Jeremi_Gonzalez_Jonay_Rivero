package exceptions;

// Excepción lanzada cuando un usuario intenta realizar una operación teniendo una sanción vigente
public class SancionActivaException extends BibliotecaException {
    // Constructor que envía el mensaje de error personalizado a la clase padre
    public SancionActivaException(String mensaje) {
        super(mensaje);
    }
}
