package view;

import java.util.List;
import java.util.Scanner;
import model.*;
import controller.GestorBiblioteca;

public class Consola {
    Scanner sc = new Scanner(System.in);

    public int mostrarMenu() {
        int opcion = -1;
        System.out.println("\n=======Menú Principal=======");
        System.out.println("1. Listar Catálogo");
        System.out.println("2. Buscar Título");
        System.out.println("3. Prestar Libro");
        System.out.println("4. Devolver Libro");
        System.out.println("5. Reservar Libro");
        System.out.println("0. Salir");
        System.out.println("==============================");
        System.out.print("Seleccione una opción: ");

        try {
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:
                    gestor.getCatalogo();
                    break;
                case 2:
                    System.out.println("Indique el titulo del libro que quiere buscar: ");
                    String titulo = sc.nextLine();
                    List<Libro> listaLibros = gestor.buscarPorTitulo(titulo);
                    if (listaLibros.isEmpty()) {
                        System.out.println("No se han encontrado libros con ese titulo.");
                    } else {
                        for (Libro p : listaLibros) {
                            System.out.println(p);
                        }
                    }
                    break;
                case 3:
                    System.out.println("Indique que ID Usuario tiene: ");
                    String id = sc.nextLine();
                    System.out.println("Indique el ISBN del libro que quiere pedir prestado: ");
                    String isbn = sc.nextLine();
                    // Llamamos al metodo de la clase biblioteca
                    System.out.println("Se ha realizado el prestamo correctamente.");
                    break;
                case 4:
                    System.out.println("Indique que ID Usuario tiene: ");
                    String id2 = sc.nextLine();
                    System.out.println("Indique el ISBN del libro que quiere devolver: ");
                    String isbn2 = sc.nextLine();
                    // Llamamos al metodo de la clase biblioteca
                    System.out.println("Se ha realizado la devolucion correctamente.");
                    break;
                case 5:
                    System.out.println("Indique que ID Usuario tiene: ");
                    String id3 = sc.nextLine();
                    System.out.println("Indique el ISBN del libro que quiere reservar: ");
                    String isbn3 = sc.nextLine();
                    // Llamamos al metodo de la clase biblioteca
                    System.out.println("Se ha realizado la reserva correctamente.");
                    break;

                default:
                    break;
            }
        } catch(BibliotecaException e){
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }


    }

}
