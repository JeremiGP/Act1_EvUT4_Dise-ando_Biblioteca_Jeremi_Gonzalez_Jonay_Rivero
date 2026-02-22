package exceptions;

// Excepción que salta cuando un usuario intenta superar su límite máximo de libros prestados
public class LimitePrestamosExcedidoException extends BibliotecaException {
    // Constructor que envía el mensaje de error personalizado a la clase padre
    public LimitePrestamosExcedidoException(String mensaje) {
        super(mensaje);
    }
}
