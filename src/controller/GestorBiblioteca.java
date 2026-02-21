// Paquete controller
package controller;

// Importaciones de los paquetes model y exceptions del proyecto.
import model.*;
import model.enums.*;
import exceptions.*;

// Importaciones de java.
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

// Clase GestorBiblioteca
public class GestorBiblioteca {

    // Listas vacias para guardar los datos de libros , usuarios y prestamos
    // (activos).
    private List<Libro> catalogo = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Prestamo> prestamosActivos = new ArrayList<>();

    // --- ALTAS DE NUEVOS ELEMENTOS ---

    // Añadir nuevos libros al catalogo.
    public void altaLibro(Libro libro) {
        if (libro == null) {
            throw new NullPointerException("ERROR:No se puede dar de alta un libro nulo.");
        }
        catalogo.add(libro);
    }

    // Añadir nuevos usuarios al registro.
    public void altaUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new NullPointerException("ERROR: No se puede dar de alta un usuario nulo.");
        }
        usuarios.add(usuario);
    }

    // --- MÉTODOS DE BÚSQUEDA ---

    // Buscar libros que contengan un titulo.
    public List<Libro> buscarLibroPorTitulo(String titulo) {
        if (titulo == null) {
            throw new IllegalArgumentException("ERROR:El título no puede ser nulo.");
        }

        // Creamos un Array temporal para guardar libros coincidentes.
        List<Libro> librosEncontrados = new ArrayList<>();

        // Recorremos el catalogo uno a uno.
        for (Libro libro : catalogo) {
            if (libro.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                librosEncontrados.add(libro);
            }
        }
        // Devlvemos los resultados de todos los libros encontrados.
        return librosEncontrados;
    }

    // Buscar libros por ISBN.
    public Libro buscarLibroPorIsbn(String isbn) {
        return buscarLibro(isbn);
    }

    // Buscar libros por genero.
    public List<Libro> buscarLibroPorGenero(GeneroLibro genero) {
        if (genero == null) {
            throw new IllegalArgumentException("ERROR:El genero no puede ser nulo.");
        }

        // Creamos lista temporañ (Array) para guardar los resultados.
        List<Libro> librosEncontrados = new ArrayList<>();

        // Recorremos el catalogo uno a uno, y si encuentra coincidencia se añade a la
        // lista.
        for (Libro libro : catalogo) {
            if (libro.getGenero() == genero) {
                librosEncontrados.add(libro);
            }
        }

        // Devolvemos la lista de resultados encontrados.
        return librosEncontrados;
    }

    // Buscar que usuario tiene un libro.
    public Usuario quienTieneElLibro(String isbn) {

        // Recorremos toda la lista de usuarios, recorremos la lista de libros prestados
        // y si el ISBN coincide con el que buscamos,
        // buscamos la fecha en la que tiene que devolverlo al igual que el usuario.
        for (Usuario usuario : usuarios) {
            for (Libro libro : usuario.getLibrosPrestados()) {
                if (libro.getIsbn().equals(isbn)) {
                    String fechaVencimiento = buscarFechaVencimiento(usuario.getId(), isbn);
                    System.out.println("El usuario " + usuario.getNombre() +
                            " tiene el libro " + isbn +
                            " (Vence: " + fechaVencimiento + ")");
                    return usuario;
                }
            }
        }
        // revisamos todo si no encontramos nada devolveos null.
        return null;
    }

    // --- GESTIÓN DE TIEMPO Y SANCIONES ---

    // Verificamos si hay libros vencidos y sancionamos al usuario.
    public void verificarVencimientosYSancionar(String idUsuario) {
        // Buscamos el usuario.
        Usuario usuario = buscarUsuario(idUsuario);

        if (usuario == null) {
            return;
        }

        LocalDate hoy = LocalDate.now();
        long totalDiasSancion = 0;

        // Recorremos toda la lista de prestamos activos, si el prestamo es del usuario
        // y si la fecha es anterior a la actual,
        // calculamos los dias de retraso y los dias de sancion.
        for (Prestamo prestamo : prestamosActivos) {
            if (prestamo.getUsuario().getId().equals(idUsuario)) {
                if (hoy.isAfter(prestamo.getFechaVencimiento())) {
                    long diasRetraso = ChronoUnit.DAYS.between(prestamo.getFechaVencimiento(), hoy);
                    totalDiasSancion += (7 + diasRetraso);
                }
            }
        }
        // Si hay sanción, la aplicamos.
        if (totalDiasSancion > 0) {
            usuario.setFechaFinSancion(hoy.plusDays(totalDiasSancion));
        }
    }

    // --- OPERACIONES PRINCIPALES ---

    // Realizamos el prestamo.
    public Prestamo realizarPrestamo(String idUsuario, String isbnLibro)
            throws LibroNoDisponibleException, LimitePrestamosExcedidoException, SancionActivaException {

        // Buscamos el usuario y el libro.
        Usuario usuario = buscarUsuario(idUsuario);
        Libro libro = buscarLibro(isbnLibro);

        // Si no se encuentra el usuario o el libro, lanzamos una excepción.
        if (usuario == null || libro == null) {
            throw new IllegalArgumentException("ERROR: Usuario o Libro no encontrados.");
        }

        // Si el usuario tiene una sanción activa, no se puede realizar el prestamo.
        if (usuario.getFechaFinSancion() != null && usuario.getFechaFinSancion().isAfter(LocalDate.now())) {
            throw new SancionActivaException(
                    "ADVERTENCIA: El usuario está sancionado hasta: " + usuario.getFechaFinSancion());
        }

        // Si el usuario tiene 3 libros prestados, no se puede realizar el prestamo.
        if (usuario.getLibrosPrestados().size() >= 3) {
            throw new LimitePrestamosExcedidoException(
                    "ERROR: El usuario ya tiene 3 libros prestados, no puede pedir más.");
        }

        // Si el libro está disponible o reservado, se puede realizar el prestamo.
        if (libro.getEstado() != EstadoLibro.DISPONIBLE && libro.getEstado() != EstadoLibro.RESERVADO) {
            throw new LibroNoDisponibleException("ERROR: El libro " + libro.getTitulo() + " no está disponible.");
        }

        // Verificacion de si quedan copias disponibles
        if (libro.getCopiasDisponibles() <= 0) {
            throw new LibroNoDisponibleException("ERROR: No quedan copias disponibles de " + libro.getTitulo()
                    + " (Estado actual: " + libro.getEstado() + ")");
        }

        // Verifica si tiene libros vencidos actualmente
        for (Prestamo prestamo : prestamosActivos) {
            if (prestamo.getUsuario().getId().equals(idUsuario)
                    && LocalDate.now().isAfter(prestamo.getFechaVencimiento())) {
                throw new SancionActivaException(
                        "ADVERTENCIA: Tienes libros vencidos. Devuélvelos primero.");
            }
        }

        // Verifica el bloqueo de 7 días del historial
        for (Prestamo prestamo : usuario.getHistorialPrestamos()) {
            if (prestamo.getLibro().getIsbn().equals(isbnLibro)) {
                if (prestamo.getFechaDevolucionReal() != null
                        && LocalDate.now().isBefore(prestamo.getFechaDevolucionReal().plusDays(7))) {
                    throw new SancionActivaException(
                            "ADVERTENCIA: (Bloqueo de 7 días) No puedes volver a pedir este libro hasta el "
                                    + prestamo.getFechaDevolucionReal().plusDays(7));
                }
            }
        }

        // Realizamos el prestamo.
        Prestamo nuevoPrestamo = new Prestamo(libro, usuario, LocalDate.now(), LocalDate.now().plusDays(30));

        prestamosActivos.add(nuevoPrestamo);

        usuario.getLibrosPrestados().add(libro);
        libro.reducirCopia();

        // Cambiamos el estado global a PRESTADO si ya no quedan más copias en absoluto
        if (libro.getCopiasDisponibles() == 0) {
            libro.setEstado(EstadoLibro.PRESTADO);
        } else {
            // Si quedan copias despues de prestar, el estado es DISPONIBLE
            libro.setEstado(EstadoLibro.DISPONIBLE);
        }

        return nuevoPrestamo;
    }

    // Devolver el libro.
    public boolean devolverLibro(String idUsuario, String isbnLibro) {

        // Buscamos el usuario.
        Usuario usuario = buscarUsuario(idUsuario);

        // Si no se encuentra el usuario, lanzamos una excepción.
        if (usuario == null) {
            throw new IllegalArgumentException("ERROR: No se encontró ningún usuario con el ID proporcionado.");
        }

        Prestamo prestamoEncontrado = null;

        // Buscamos el prestamo activo.
        for (Prestamo prestamo : prestamosActivos) {
            if (prestamo.getUsuario().getId().equals(idUsuario) && prestamo.getLibro().getIsbn().equals(isbnLibro)) {
                prestamoEncontrado = prestamo;
                break;
            }
        }

        // Cláusula de guarda: Si no hay préstamo, cortamos la ejecución y lanzamos
        // error
        if (prestamoEncontrado == null) {
            throw new IllegalArgumentException(
                    "ERROR: No se encontró un préstamo activo de ese libro para ese usuario.");
        }

        // Variables de apoyo
        boolean huboSancion = false;
        Libro libro = prestamoEncontrado.getLibro();

        // Registramos la devolución.
        prestamoEncontrado.registrarDevolucion();

        // Evaluamos si hay sanción por entrega tardía.
        if (prestamoEncontrado.getFechaDevolucionReal().isAfter(prestamoEncontrado.getFechaVencimiento())) {
            usuario.setFechaFinSancion(LocalDate.now().plusDays(7));
            huboSancion = true;
        }

        // Actualizamos los datos del usuario
        usuario.getLibrosPrestados().remove(libro);
        usuario.getHistorialPrestamos().add(prestamoEncontrado);

        // Actualizamos los datos del libro.
        libro.aumentarCopia();
        if (libro.getCopiasDisponibles() > 0) {
            libro.setEstado(EstadoLibro.DISPONIBLE);
        }

        // Cerramos el préstamo activo.
        prestamosActivos.remove(prestamoEncontrado);

        // Devolvemos si hubo sanción para que la interfaz pueda avisar al usuario.
        return huboSancion;
    }

    // Reservamos el libro.
    public void reservarLibro(String isbn) throws LibroNoDisponibleException {

        // Buscamos el libro.
        Libro libro = buscarLibro(isbn);

        // Si no se encuentra el libro, lanzamos una excepción.
        if (libro == null) {
            throw new NullPointerException("ERROR: El libro no existe.");
        }

        // Si no quedan copias disponibles, lanzamos una excepción.
        if (libro.getCopiasDisponibles() <= 0) {
            throw new LibroNoDisponibleException("ERROR: No quedan copias disponibles de este libro para reservar.");
        }

        // Reducimos la copia.
        libro.reducirCopia();

        // Forzamos el estado a RESERVADO para que deje de figurar como DISPONIBLE.
        libro.setEstado(EstadoLibro.RESERVADO);
    }

    // --- RESÚMENES ---

    // Muestra catálogo completo.
    public void resumenLibros() {
        System.out.println("\n--- CATÁLOGO COMPLETO ---");
        for (Libro libro : catalogo) {
            System.out.println(libro.toString());
        }
    }

    // Muestra usuarios y préstamos activos.
    public void resumenUsuarios() {
        System.out.println("\n--- USUARIOS Y PRÉSTAMOS ACTIVOS ---");

        // For para recorrer los usuarios.
        for (Usuario usuario : usuarios) {
            verificarVencimientosYSancionar(usuario.getId());

            // Creamos un StringBuilder para construir el texto de los libros prestados
            // (String Builder = Cadena de texto que se puede modificar/renombrar).
            StringBuilder textoLibros = new StringBuilder();
            List<Libro> librosDelUsuario = usuario.getLibrosPrestados();

            // Si no tiene libros prestados, mostramos "Ninguno",
            // sino recorremos la lista y mostrar cuando vence cada uno.
            if (librosDelUsuario.isEmpty()) {
                textoLibros.append("Ninguno");
            } else {
                for (int i = 0; i < librosDelUsuario.size(); i++) {
                    Libro libro = librosDelUsuario.get(i);
                    textoLibros.append(libro.getTitulo())
                            .append(" (Vence: ")
                            .append(buscarFechaVencimiento(usuario.getId(), libro.getIsbn()))
                            .append(")");

                    if (i < librosDelUsuario.size() - 1) {
                        textoLibros.append(", ");
                    }
                }
            }

            System.out.println("Usuario: " + usuario.getNombre() + " | Libros: [" + textoLibros.toString() + "]");

            // Si tiene sanción, mostramos hasta cuando.
            if (usuario.getFechaFinSancion() != null && usuario.getFechaFinSancion().isAfter(LocalDate.now())) {
                System.out.println("   (!) ALERTA: Sancionado hasta " + usuario.getFechaFinSancion());
            }
        }
    }

    // --- MÉTODOS AUXILIARES (PRIVADOS) ---

    // Busca la fecha de vencimiento de un prestamo.
    private String buscarFechaVencimiento(String idUsuario, String isbn) {
        for (Prestamo prestamo : prestamosActivos) {
            if (prestamo.getUsuario().getId().equals(idUsuario) && prestamo.getLibro().getIsbn().equals(isbn)) {
                return prestamo.getFechaVencimiento().toString();
            }
        }
        return "N/A";
    }

    // Busca un usuario por su ID.
    private Usuario buscarUsuario(String id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        return null;
    }

    // Busca un libro por su ISBN.
    private Libro buscarLibro(String isbn) {
        for (Libro libro : catalogo) {
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }

    // --- GETTERS ---

    public List<Usuario> getUsuarios() {
        return usuarios;
    }
}