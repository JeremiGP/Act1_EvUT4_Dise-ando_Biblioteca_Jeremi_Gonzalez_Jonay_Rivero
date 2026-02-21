package exceptions;

public class SancionActivaException extends RuntimeException {
    public SancionActivaException(String mensaje) {
        super(mensaje);
    }
}
