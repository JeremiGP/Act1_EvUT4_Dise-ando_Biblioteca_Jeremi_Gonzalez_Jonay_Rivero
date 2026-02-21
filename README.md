## Sistema de Gestión de Biblioteca
Realizado por Jeremi González y Jonay Rivero.

* **Ejecución:** Para poder acceder a la aplicación, se debe ejecutar el archivo 'Main.java' ubicado en el paquete 'app'.

* **Gestión de Catálogo:** Creación de libros detallados con ISBN, título, autor, editorial, género, año de publicación y control de copias totales/disponibles.
* **Control de Préstamos y Reservas:** * Registro de préstamos con fecha de vencimiento automática a 30 días.
    * Lógica de estados del libro dinámica (Disponible, Prestado, Reservado).
    * Restricción de flujo: Límite máximo de 3 libros prestados simultáneamente por usuario.
* **Sistema de Sanciones Automático:** * Aplicación de sanciones por entregas fuera de plazo (7 días + los días de retraso).
    * Bloqueo del historial que impide volver a sacar un mismo libro antes de que pasen 7 días desde su devolución.
* **Manejo de Excepciones y Resúmenes:**
    * Búsqueda ágil de libros por título, ISBN o género.
    * Batería de pruebas automatizada para comprobar el manejo de errores (límite de libros, sanciones, copias agotadas, etc.).

* **Reparto de Tareas:**
    * Jeremi González: 
        * Creacion del repositorio.
        * Estructura del proyecto.
        * Logica completa de GestionBiblioteca.java & creacion de excepciones.
        * Creacion de Main.java
    * Jonay Rivero: 
        * Creacion de Consola.java
        * Creacion de Libro.java
        * Creacion de Prestamo.java
        * Creacion de Usuario.java
        * Creacion de enums
        * Creacion de README.md 
        * Correccion de errores del flujo de prestamos.

    * Ambos: 
        * Pruebas opcionales para la comprobacion de excepciones.
        * Revision del codigo para asegurar que cumple con los requisitos del enunciado.
        * Correccion de errores del proyecto entero.
        * Creacion de mejoras en su totalidad. 


* **Organización:**
|-- src
|   |-- app
|   |   |-- Main.java
|   |-- controller
|   |   |-- GestorBiblioteca.java
|   |-- exceptions
|   |   |-- BibliotecaException.java
|   |   |-- LibroNoDisponibleException.java
|   |   |-- LimitePrestamosExcedidoException.java
|   |   |-- SancionActivaException.java
|   |-- model
|   |   |-- Libro.java
|   |   |-- Prestamo.java
|   |   |-- Usuario.java
|   |   |-- enums
|   |   |   |-- EstadoLibro.java
|   |   |   |-- GeneroLibro.java
|   |-- view
|   |   |-- Consola.java
|
|-- README.md