package app;

// Importacion Java.
import java.time.LocalDate;

// Importaciones de los paquetes del proyecto.
import controller.GestorBiblioteca;
import view.Consola;
import model.*;
import model.enums.*;

public class Main {

    public static void main(String[] args) {

        GestorBiblioteca gestor = new GestorBiblioteca();
        Consola vista = new Consola();

        // Creamos libros
        Libro l1 = new Libro("111", "Don Quijote", "Cervantes", "Planeta", GeneroLibro.CIENCIA_FICCION, 1605, 5);
        Libro l2 = new Libro("112", "Cien Años de Soledad", "García Márquez", "Sudamericana", GeneroLibro.FICCION, 1967,
                5);
        Libro l3 = new Libro("113", "1984", "George Orwell", "Secker & Warburg", GeneroLibro.CIENCIA_FICCION, 1949, 5);
        Libro l4 = new Libro("114", "El Hobbit", "Tolkien", "Minotauro", GeneroLibro.FANTASIA, 1937, 5);

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

        // Simulamos test de excepciones básicos de validación
        vista.simularPruebasDeExcepciones(gestor);

        // Lanzamos el menú interactivo para continuar probando la aplicación
        vista.mostrarMenu(gestor);
    }
}