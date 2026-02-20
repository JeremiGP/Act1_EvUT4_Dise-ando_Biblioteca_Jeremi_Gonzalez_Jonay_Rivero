package app;

// Importacion Java.
import java.time.LocalDate;

// Importaciones de los paquetes del proyecto.
import controller.GestorBiblioteca;
import model.*;
import model.enums.*;
import exceptions.*;
import view.Consola;

public class Main {

    public static void main(String[] args) {

        GestorBiblioteca gestor = new GestorBiblioteca();
        Consola vista = new Consola();

        cargarDatosIniciales(gestor);

        demostrarExcepciones(gestor);

        vista.mostrarMenu(gestor);
    }

    private static void cargarDatosIniciales(GestorBiblioteca gestor) {
        // Creamos libros
        Libro l1 = new Libro("111", "Don Quijote", "Cervantes", "Planeta", GeneroLibro.CIENCIA_FICCION, 1605, 1);
        Libro l2 = new Libro("112", "Cien Años de Soledad", "García Márquez", "Sudamericana", GeneroLibro.FICCION, 1967,
                1);
        Libro l3 = new Libro("113", "1984", "George Orwell", "Secker & Warburg", GeneroLibro.CIENCIA_FICCION, 1949, 1);
        Libro l4 = new Libro("114", "El Hobbit", "Tolkien", "Minotauro", GeneroLibro.FANTASIA, 1937, 1);

        // Creamos usuarios
        Usuario u1 = new Usuario("001", "Juan Perez");
        Usuario u2 = new Usuario("002", "Maria Lopez");
        Usuario uSancionado = new Usuario("003", "Pedro Sancionado");

        // Preparamos al usuario sancionado para las pruebas
        uSancionado.setFechaFinSancion(LocalDate.now().plusDays(5)); // Castigado por 5 días más

        // Damos de alta todo en el gestor
        gestor.altaLibro(l1);
        gestor.altaLibro(l2);
        gestor.altaLibro(l3);
        gestor.altaLibro(l4);

        gestor.altaUsuario(u1);
        gestor.altaUsuario(u2);
        gestor.altaUsuario(uSancionado);
    }

    // Pruebas para comprobar que las excepciones funcionan.

    /**
     * private static void demostrarExcepciones(GestorBiblioteca gestor) {
     * System.out.println("\n--- INICIANDO BATERÍA DE PRUEBAS DE EXCEPCIONES ---");
     * 
     * // PRUEBA 1: Intentar que un usuario sancionado pida un libro
     * System.out.println("\nPrueba 1: Préstamo a usuario sancionado");
     * try {
     * // Intentamos prestar el libro "111" al usuario "003" (que sancionamos
     * arriba)
     * gestor.realizarPrestamo("003", "111");
     * } catch (SancionActivaException e) {
     * // Si salta el error, lo capturamos aquí y no dejamos que el programa explote
     * System.out.println(" CORRECTO - Error capturado: " + e.getMessage());
     * } catch (Exception e) {
     * System.out.println(" FALLO - Saltó otra excepción: " + e.getMessage());
     * }
     * 
     * // PRUEBA 2: Intentar pedir un libro que no está disponible (ya está
     * prestado)
     * System.out.println("\nPrueba 2: Préstamo de libro no disponible");
     * try {
     * // Primero, le prestamos el libro "111" a Juan ("001") legítimamente
     * gestor.realizarPrestamo("001", "111");
     * // Ahora, intentamos que María ("002") pida el mismo libro que solo tiene 1
     * copia
     * gestor.realizarPrestamo("002", "111");
     * } catch (LibroNoDisponibleException e) {
     * System.out.println(" CORRECTO - Error capturado: " + e.getMessage());
     * } catch (Exception e) {
     * System.out.println(" FALLO - Saltó otra excepción: " + e.getMessage());
     * }
     * 
     * // PRUEBA 3: Exceder el límite de 3 libros
     * System.out.println("\nPrueba 3: Límite de préstamos excedido");
     * try {
     * // Juan ("001") ya tiene el libro "111". Le damos 2 más para llegar al límite
     * (3).
     * gestor.realizarPrestamo("001", "112");
     * gestor.realizarPrestamo("001", "113");
     * 
     * // Intentamos darle un cuarto libro ("114")
     * gestor.realizarPrestamo("001", "114");
     * } catch (LimitePrestamosExcedidoException e) {
     * System.out.println(" CORRECTO - Error capturado: " + e.getMessage());
     * } catch (Exception e) {
     * System.out.println(" FALLO - Saltó otra excepción: " + e.getMessage());
     * }
     * 
     * System.out.println("\n--- FIN DE LA BATERÍA DE PRUEBAS ---");
     * }
     */
}