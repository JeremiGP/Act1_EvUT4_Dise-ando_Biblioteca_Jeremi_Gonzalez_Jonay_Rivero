package view;

import controller.GestorBiblioteca;
import model.Libro;
import model.Prestamo;
import model.enums.GeneroLibro;
import model.Usuario;
import java.util.Scanner;
import java.util.List;

public class Consola {
    private final Scanner sc = new Scanner(System.in);

    public void mostrarMenu(GestorBiblioteca gestor) {
        int opcion = -1;
        
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
                    case 1: gestor.resumenLibros(); 
                    break;
                    case 2: imprimirResumen(gestor); 
                    break;
                    case 3: menuBusqueda(gestor); 
                    break;
                    case 4: {
                        System.out.print("Indique ID Usuario: ");
                        String idU = sc.nextLine();
                        System.out.print("Indique el ISBN del libro: ");
                        String isbnL = sc.nextLine();
                        
                        // Capturamos el posible error específico del préstamo
                        try {
                            Prestamo p = gestor.realizarPrestamo(idU, isbnL);
                            System.out.println("¡Éxito! Préstamo registrado.");
                            System.out.println("   Libro: " + p.getLibro().getTitulo());
                            System.out.println("   Vence el: " + p.getFechaVencimiento());
                        } catch (Exception e) {
                            System.out.println("Error al prestar: " + e.getMessage());
                        }
                    }
                    case 5: {
                        System.out.print("Indique ID Usuario: ");
                        String idD = sc.nextLine();
                        System.out.print("Indique el ISBN a devolver: ");
                        String isbnD = sc.nextLine();
                        
                        try {
                            // Capturamos el posible error específico del préstamo
                            boolean sancionado = gestor.devolverLibro(idD, isbnD);
                            System.out.println("Libro devuelto correctamente.");
                            if (sancionado) {
                                System.out.println("ATENCIÓN: Entrega fuera de plazo.");
                                System.out.println("Usuario sancionado por 7 días.");
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("Dato inválido: " + e.getMessage());
                        }
                    }
                    case 6: {
                        System.out.print("ISBN a reservar: ");
                        gestor.reservarLibro(sc.nextLine());
                        System.out.println("Solicitud de reserva procesada.");
                    }
                    case 7: {
                        System.out.println("Introduzca ISBN del libro: ");
                        String isbn = sc.nextLine();
                        Usuario usuario = gestor.quienTieneElLibro(isbn);
                        if (usuario != null) {
                            System.out.println("El libro está prestado a: " + usuario.getNombre());
                        } else {
                            System.out.println("El libro no está prestado.");
                        }
                    }
                    case 0: System.out.println("Saliendo del sistema...");
                    default: System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, introduzca un número válido.");
            } catch (Exception e) {
                System.err.println("Ha ocurrido un error inesperado: " + e.getMessage());
            }
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
                usuario.getLibrosPrestados().forEach(libro -> 
                    System.out.println("    * " + libro.getTitulo() + " [ISBN: " + libro.getIsbn() + "]"));
            }
            System.out.println("---------------------------------------");
        }
    }

    private void menuBusqueda(GestorBiblioteca gestor) {
        System.out.println("\nBuscar por: 1. Título | 2. ISBN | 3. Género");
        try {
            int opcion2 = Integer.parseInt(sc.nextLine());
            switch (opcion2) {
                case 1: {
                    System.out.print("Ingrese título: ");
                    imprimirResultados(gestor.buscarLibroPorTitulo(sc.nextLine()));
                }
                case 2: {
                    System.out.print("Ingrese ISBN: ");
                    Libro libro = gestor.buscarLibroPorIsbn(sc.nextLine());
                    if (libro != null) System.out.println(libro);
                    else System.out.println("Libro no encontrado.");
                }
                case 3: {
                    System.out.println("Géneros: FICCION, NO_FICCION, TERROR, CIENCIA, HISTORIA");
                    System.out.print("Seleccione Género: ");
                    GeneroLibro gl = GeneroLibro.valueOf(sc.nextLine().toUpperCase());
                    imprimirResultados(gestor.buscarLibroPorGenero(gl));
                }
                default: System.out.println("Opción de búsqueda no válida.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: El género introducido no existe.");
        } catch (Exception e) {
            System.out.println("Error en la búsqueda: " + e.getMessage());
        }
    }

    private void imprimirResultados(List<Libro> libros) {
        if (libros.isEmpty()) {
            System.out.println("No se encontraron resultados.");
        } else {
            libros.forEach(System.out::println);
        }
    }
}