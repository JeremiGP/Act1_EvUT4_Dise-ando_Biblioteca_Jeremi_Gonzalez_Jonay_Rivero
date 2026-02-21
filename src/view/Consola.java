package view;

import controller.GestorBiblioteca;
import exceptions.*;
import model.Libro;
import model.Prestamo;
import model.enums.GeneroLibro;
import model.Usuario;
import java.util.Scanner;
import java.util.List;

/**
 * Clase Consola: Encargada de la interacción con el usuario.
 * Aplica estructuras de control de la UT2 y gestión de colecciones de la UT3.
 */
public class Consola {
    private final Scanner sc = new Scanner(System.in);

    public void mostrarMenu(GestorBiblioteca gestor) {
        int opcion = -1;

        // Bucle principal: se mantiene activo hasta que la opción sea 0
        while (opcion != 0) {
            System.out.println("\n======= Menú Principal =======");
            System.out.println("1. Resumen Catálogo");
            System.out.println("2. Resumen Usuarios");
            System.out.println("3. Buscar (Título/ISBN/Género)");
            System.out.println("4. Prestar Libro");
            System.out.println("5. Devolver Libro");
            System.out.println("6. Reservar Libro");
            System.out.println("7. Quien tiene el libro");
            System.out.println("0. Salir");
            System.out.println("==============================");
            System.out.print("Seleccione una opción: ");

            try {
                String entrada = sc.nextLine();
                opcion = Integer.parseInt(entrada);

                switch (opcion) {
                    case 1:
                        gestor.resumenLibros();
                        break;
                    case 2:
                        imprimirResumen(gestor);
                        break;
                    case 3:
                        menuBusqueda(gestor);
                        break;
                    case 4:
                        ejecutarFlujoPrestamo(gestor);
                        break;
                    case 5:
                        ejecutarFlujoDevolucion(gestor);
                        break;
                    case 6:
                        System.out.print("ISBN a reservar: ");
                        gestor.reservarLibro(sc.nextLine());
                        System.out.println("Solicitud de reserva procesada.");
                        break;
                    case 7:
                        System.out.println("Introduzca ISBN del libro: ");
                        String isbn = sc.nextLine();
                        Usuario usuario = gestor.quienTieneElLibro(isbn);
                        if (usuario != null) {
                        } else {
                            System.out.println("El libro no está prestado.");
                        }
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                        break;
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, introduzca un número entero válido.");
            } catch (Exception e) {
                System.err.println("Ha ocurrido un error inesperado: " + e.getMessage());
            }
        }
    }

    // --- MÉTODOS AUXILIARES PARA LIMPIAR EL SWITCH (MODULARIZACIÓN) ---

    private void ejecutarFlujoPrestamo(GestorBiblioteca gestor) {
        System.out.print("Indique ID Usuario: ");
        String idU = sc.nextLine();
        System.out.print("Indique el ISBN del libro: ");
        String isbnL = sc.nextLine();

        try {
            Prestamo p = gestor.realizarPrestamo(idU, isbnL);
            System.out.println("¡Éxito! Préstamo registrado.");
            System.out.println("   Libro: " + p.getLibro().getTitulo());
            System.out.println("   Vence el: " + p.getFechaVencimiento());
        } catch (Exception e) {
            System.out.println("Error al prestar: " + e.getMessage());
        }
    }

    private void ejecutarFlujoDevolucion(GestorBiblioteca gestor) {
        System.out.print("Indique ID Usuario: ");
        String idD = sc.nextLine();
        System.out.print("Indique el ISBN a devolver: ");
        String isbnD = sc.nextLine();

        try {
            boolean sancionado = gestor.devolverLibro(idD, isbnD);
            System.out.println("Libro devuelto correctamente.");
            if (sancionado) {
                System.out.println("ATENCIÓN: Entrega fuera de plazo. Usuario sancionado por 7 días.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Dato inválido: " + e.getMessage());
        }
    }

    private void imprimirResumen(GestorBiblioteca gestor) {
        System.out.println("\n=== LISTADO DE USUARIOS Y SANCIONES ===");
        for (Usuario usuario : gestor.getUsuarios()) {
            gestor.verificarVencimientosYSancionar(usuario.getId());

            String infoSancion = (usuario.getFechaFinSancion() != null)
                    ? " | SANCIONADO hasta: " + usuario.getFechaFinSancion()
                    : " | Activo (Sin sanciones)";

            System.out.println("> " + usuario.getNombre() + " (ID: " + usuario.getId() + ")" + infoSancion);

            if (usuario.getLibrosPrestados().isEmpty()) {
                System.out.println("  - Sin libros prestados.");
            } else {
                System.out.println("  - En posesión:");
                usuario.getLibrosPrestados().forEach(
                        libro -> System.out.println("    * " + libro.getTitulo() + " [ISBN: " + libro.getIsbn() + "]"));
            }
            System.out.println("---------------------------------------");
        }
    }

    private void menuBusqueda(GestorBiblioteca gestor) {
        System.out.println("\nBuscar por: 1. Título | 2. ISBN | 3. Género");
        try {
            int opcion2 = Integer.parseInt(sc.nextLine());
            switch (opcion2) {
                case 1:
                    System.out.print("Ingrese título: ");
                    imprimirResultados(gestor.buscarLibroPorTitulo(sc.nextLine()));
                    break;
                case 2:
                    System.out.print("Ingrese ISBN: ");
                    Libro libro = gestor.buscarLibroPorIsbn(sc.nextLine());
                    if (libro != null)
                        System.out.println(libro);
                    else
                        System.out.println("Libro no encontrado.");
                    break;
                case 3:
                    System.out.println("Géneros: FICCION, NO_FICCION, TERROR, CIENCIA, HISTORIA");
                    System.out.print("Seleccione Género: ");
                    GeneroLibro gl = GeneroLibro.valueOf(sc.nextLine().toUpperCase());
                    imprimirResultados(gestor.buscarLibroPorGenero(gl));
                    break;
                default:
                    System.out.println("Opción de búsqueda no válida.");
                    break;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: El género introducido no existe.");
        } catch (Exception e) {
            System.out.println("ERROR en la búsqueda: " + e.getMessage());
        }
    }

    private void imprimirResultados(List<Libro> libros) {
        if (libros.isEmpty()) {
            System.out.println("No se encontraron resultados.");
        } else {
            libros.forEach(System.out::println);
        }
    }

    /**
     * Método para simular fallos y verificar que las excepciones
     */
    public void simularPruebasDeExcepciones(GestorBiblioteca gestor) {
        System.out.println("\n--- INICIANDO TEST DE EXCEPCIONES ---");

        // PRUEBA 1: Intentar prestar un libro que no existe (Debe lanzar Exception)
        try {
            System.out.println("[Test 1] Intentando prestar ISBN inexistente '999'...");
            gestor.realizarPrestamo("001", "999");
        } catch (Exception e) {
            System.out.println("CAZADA: Se evitó el error: " + e.getMessage());
        }

        // PRUEBA 2: Devolución con datos nulos o vacíos
        try {
            System.out.println("\n[Test 2] Intentando devolver con ID vacío...");
            gestor.devolverLibro("", "111");
        } catch (IllegalArgumentException e) {
            // La UT4 recomienda usar IllegalArgumentException para argumentos no válidos
            System.out.println("CAZADA (Error de argumento): " + e.getMessage());
        }

        // PRUEBA 3: Simulación de sanción (Lógica de negocio)
        try {
            System.out.println("\n[Test 3] Verificando sanciones para usuario 001...");
            // Aquí rescatamos la lógica de tu método original
            gestor.verificarVencimientosYSancionar("001");
            System.out.println("Verificación completada sin errores críticos.");
        } catch (Exception e) {
            System.err.println("ERROR CRÍTICO en el sistema: " + e.getMessage());
        }

        // PRUEBA 4: Préstamo a usuario sancionado (SancionActivaException)
        try {
            System.out.println("\n[Test 4] Intentando prestar al usuario sancionado '003'...");
            // En tu Main preparaste a Pedro ("003") con una sanción activa.
            gestor.realizarPrestamo("003", "111");
        } catch (SancionActivaException e) {
            System.out.println("CAPTURADA (Sanción Activa): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }

        // PRUEBA 5: Límite de 3 libros superado (LimitePrestamosExcedidoException)
        try {
            System.out.println("\n[Test 5] Intentando superar el límite de 3 préstamos para Maria ('002')...");
            gestor.realizarPrestamo("002", "111");
            gestor.realizarPrestamo("002", "112");
            gestor.realizarPrestamo("002", "113");
            System.out.println("Maria ya tiene 3 libros. Intentando sacar el cuarto...");
            gestor.realizarPrestamo("002", "114"); // Aquí debe explotar
        } catch (LimitePrestamosExcedidoException e) {
            System.out.println("CAPTURADA (Límite Excedido): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }

        // PRUEBA 6: Bloqueo de 7 días al volver a pedir el mismo libro
        // (SancionActivaException)
        try {
            System.out.println("\n[Test 6] Intentando pedir un libro recién devuelto (Bloqueo 7 días)...");
            // Usamos a Juan ("001") que está limpio.
            gestor.realizarPrestamo("001", "114"); // Lo pide
            gestor.devolverLibro("001", "114"); // Lo devuelve al instante
            System.out.println("Libro devuelto. Intentando pedir el MISMO libro hoy mismo...");
            gestor.realizarPrestamo("001", "114"); // Debe saltar regla del historial
        } catch (SancionActivaException e) {
            System.out.println("CAPTURADA (Bloqueo Historial): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }

        System.out.println("\n--- FIN DEL TEST DE EXCEPCIONES ---");
    }
}