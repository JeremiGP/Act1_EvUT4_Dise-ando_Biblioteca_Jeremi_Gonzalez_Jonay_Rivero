package exceptions;

// Clase base para todas las excepciones personalizadas de la biblioteca
public class BibliotecaException extends Exception {
    // Constructor que recibe el mensaje detallado del error
    public BibliotecaException(String mensaje) {
        super(mensaje);
    }
}