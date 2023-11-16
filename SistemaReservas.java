import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Usuario {
    String nombre;
    String contrasena;
    String plan;

    public Usuario(String nombre, String contrasena, String plan) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.plan = plan;
    }
}

class Reserva {
    String fechaViaje;
    boolean esIdaVuelta;
    int cantidadBoletos;
    String aerolinea;
    String nombreUsuario;

    public Reserva(String fechaViaje, boolean esIdaVuelta, int cantidadBoletos, String aerolinea, String nombreUsuario) {
        this.fechaViaje = fechaViaje;
        this.esIdaVuelta = esIdaVuelta;
        this.cantidadBoletos = cantidadBoletos;
        this.aerolinea = aerolinea;
        this.nombreUsuario = nombreUsuario;
    }
}

class Confirmacion {
    String numeroTarjeta;
    String claseVuelo;
    int cantidadMaletas;
    String nombreUsuario;

    public Confirmacion(String numeroTarjeta, String claseVuelo, int cantidadMaletas, String nombreUsuario) {
        this.numeroTarjeta = numeroTarjeta;
        this.claseVuelo = claseVuelo;
        this.cantidadMaletas = cantidadMaletas;
        this.nombreUsuario = nombreUsuario;
    }
}

public class SistemaReservas {

    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Reserva> reservas = new ArrayList<>();
    private static List<Confirmacion> confirmaciones = new ArrayList<>();
    private static Usuario usuarioActual = null;

    public static void main(String[] args) {
        cargarUsuariosDesdeCSV("usuarios.csv");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Modo registro");
            System.out.println("2. Ingresar/Salir");
            System.out.println("3. Modo reservas");
            System.out.println("4. Modo confirmación");
            System.out.println("5. Modo perfil");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            

            switch (opcion) {
                case 1:
                    modoRegistro(scanner);
                    break;
                case 2:
                    modoIngresarSalir(scanner);
                    break;
                case 3:
                    modoReservas(scanner);
                    break;
                case 4:
                    modoConfirmacion(scanner);
                    break;
                case 5:
                    modoPerfil(scanner);
                    break;
                case 6:
                    guardarUsuariosEnCSV("usuarios.csv");
                    guardarConfirmacionesEnCSV("confirmaciones.csv");
                    System.out.println("Saliendo del sistema. ¡Hasta luego!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }
        }
    }

    private static void modoRegistro(Scanner scanner) {
        scanner.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese un nombre de usuario: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Ingrese una contraseña: ");
        String contrasena = scanner.nextLine();
        
        System.out.print("Seleccione un plan (base o premium): ");
        String plan = scanner.nextLine().toLowerCase(); // Convertir a minúsculas para evitar problemas de comparación
        
        // Verificar si el plan ingresado es válido
        if (!plan.equals("base") && !plan.equals("premium")) {
            System.out.println("El plan seleccionado no es válido. Volviendo al menú principal.");
            return;
        }
    
        // Crear un nuevo usuario y agregarlo a la lista de usuarios
        Usuario nuevoUsuario = new Usuario(nombre, contrasena, plan);
        usuarios.add(nuevoUsuario);
    
        // Guardar el nuevo usuario en el archivo CSV
        guardarUsuariosEnCSV("usuarios.csv");
    
        System.out.println("Usuario creado exitosamente.");
    }
    
    private static Usuario buscarUsuario(String nombreUsuario, String contrasena) {
        for (Usuario usuario : usuarios) {
            if (usuario.nombre.equals(nombreUsuario) && usuario.contrasena.equals(contrasena)) {
                return usuario;
            }
        }
        return null; // Usuario no encontrado
    }

    private static void modoIngresarSalir(Scanner scanner) {
        // Si ya hay un usuario autenticado, mostrar opciones para salir
        if (usuarioActual != null) {
            System.out.println("1. Salir del sistema");
            System.out.println("2. Cambiar de usuario");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
    
            switch (opcion) {
                case 1:
                    System.out.println("Saliendo del sistema. ¡Hasta luego!");
                    guardarUsuariosEnCSV("usuarios.csv");
                    guardarConfirmacionesEnCSV("confirmaciones.csv");
                    System.exit(0);
                    break;
                case 2:
                    usuarioActual = null;
                    System.out.println("Cambiando de usuario. Volviendo al menú principal.");
                    break;
                default:
                    System.out.println("Opción no válida. Volviendo al menú principal.");
            }
        } else { // Si no hay un usuario autenticado, permitir ingresar
            scanner.nextLine(); // Limpiar el buffer
            System.out.print("Ingrese su nombre de usuario: ");
            String nombreUsuario = scanner.nextLine();
            System.out.print("Ingrese su contraseña: ");
            String contrasena = scanner.nextLine();
    
            // Buscar el usuario en la lista
            Usuario usuario = buscarUsuario(nombreUsuario, contrasena);
    
            if (usuario != null) {
                if (usuario.plan.equals("base")) {
                    System.out.println("Lo siento, por el momento no tenemos funcionalidades para la clase base.");
                    System.out.print("¿Quieres cambiarte a la clase premium? (Si/No): ");
                    String opcionCambio = scanner.nextLine().toLowerCase();
                    if (opcionCambio.equals("si")) {
                        // Redirigir al usuario para cambiar a clase premium (lógica adicional si es necesario)
                        System.out.println("Cambiando a la clase premium... (simulación)");
                    } else {
                        System.out.println("Volviendo al menú principal.");
                    }
                } else {
                    usuarioActual = usuario;
                    System.out.println("¡Bienvenido, " + usuarioActual.nombre + "!");
                }
            } else {
                System.out.println("Usuario o contraseña incorrectos. Volviendo al menú principal.");
            }
        }
    }
    

    private static void modoReservas(Scanner scanner) {
        // Verificar si hay un usuario autenticado
        if (usuarioActual == null) {
            System.out.println("Debe ingresar al sistema primero. Volviendo al menú principal.");
            return;
        }
    
        // Lógica para realizar reservas
        scanner.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese la fecha de viaje (DD/MM/YYYY): ");
        String fechaViaje = scanner.nextLine();
    
        System.out.print("¿Es una reserva de ida y vuelta? (true/false): ");
        boolean esIdaVuelta = scanner.nextBoolean();
    
        System.out.print("Ingrese la cantidad de boletos: ");
        int cantidadBoletos = scanner.nextInt();
    
        scanner.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese la aerolínea: ");
        String aerolinea = scanner.nextLine();
    
        // Crear una nueva reserva y agregarla a la lista
        Reserva nuevaReserva = new Reserva(fechaViaje, esIdaVuelta, cantidadBoletos, aerolinea, usuarioActual.nombre);
        reservas.add(nuevaReserva);
    
        System.out.println("Reserva realizada exitosamente.");
    }
    

    private static void modoConfirmacion(Scanner scanner) {
        // Verificar si hay un usuario autenticado
        if (usuarioActual == null) {
            System.out.println("Debe ingresar al sistema primero. Volviendo al menú principal.");
            return;
        }
    
        // Verificar si el usuario tiene reservas para confirmar
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas para confirmar. Volviendo al menú principal.");
            return;
        }
    
        // Mostrar las reservas disponibles para confirmar
        System.out.println("Reservas disponibles para confirmar:");
        for (int i = 0; i < reservas.size(); i++) {
            Reserva reserva = reservas.get(i);
            System.out.println(i + 1 + ". Fecha: " + reserva.fechaViaje + ", Aerolínea: " + reserva.aerolinea);
        }
    
        System.out.print("Seleccione el número de reserva que desea confirmar: ");
        int numeroReserva = scanner.nextInt();
    
        // Verificar si el número de reserva seleccionado es válido
        if (numeroReserva < 1 || numeroReserva > reservas.size()) {
            System.out.println("Número de reserva no válido. Volviendo al menú principal.");
            return;
        }
    
        // Obtener la reserva seleccionada
        Reserva reservaSeleccionada = reservas.get(numeroReserva - 1);
    
        // Lógica para confirmar la reserva
        scanner.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese el número de tarjeta: ");
        String numeroTarjeta = scanner.nextLine();
    
        // En el caso de usuarios premium, la clase de vuelo es siempre "Primera Clase"
        String claseVuelo = usuarioActual.plan.equals("premium") ? "Primera Clase" : "Economica";
    
        // En el caso de usuarios premium, preguntar por la cantidad de maletas
        int cantidadMaletas = 0;
        if (usuarioActual.plan.equals("premium")) {
            System.out.print("Ingrese la cantidad de maletas: ");
            cantidadMaletas = scanner.nextInt();
        }
    
        // Crear una nueva confirmación y agregarla a la lista
        Confirmacion nuevaConfirmacion = new Confirmacion(numeroTarjeta, claseVuelo, cantidadMaletas, usuarioActual.nombre);
        confirmaciones.add(nuevaConfirmacion);
    
        // Eliminar la reserva confirmada de la lista de reservas
        reservas.remove(reservaSeleccionada);
    
        // Guardar la confirmación en el archivo CSV
        guardarConfirmacionesEnCSV("confirmaciones.csv");
    
        System.out.println("Confirmación realizada exitosamente.");
    }
    
    private static void modoPerfil(Scanner scanner) {
        // Verificar si hay un usuario autenticado
        if (usuarioActual == null) {
            System.out.println("Debe ingresar al sistema primero. Volviendo al menú principal.");
            return;
        }
    
        // Mostrar opciones de gestión de perfil
        System.out.println("1. Cambiar contraseña");
        System.out.println("2. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
        int opcion = scanner.nextInt();
    
        switch (opcion) {
            case 1:
                cambiarContrasena(scanner);
                break;
            case 2:
                // Volver al menú principal
                break;
            default:
                System.out.println("Opción no válida. Volviendo al menú principal.");
        }
    }
    
    private static void cambiarContrasena(Scanner scanner) {
        scanner.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese la nueva contraseña: ");
        String nuevaContrasena = scanner.nextLine();
    
        // Cambiar la contraseña del usuario actual
        usuarioActual.contrasena = nuevaContrasena;
    
        // Guardar la información actualizada en el archivo CSV
        guardarUsuariosEnCSV("usuarios.csv");
    
        System.out.println("Contraseña cambiada exitosamente.");
    }
    

    private static void cargarUsuariosDesdeCSV(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] partes = line.split(",");
                usuarios.add(new Usuario(partes[0], partes[1], partes[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void guardarUsuariosEnCSV(String archivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (Usuario usuario : usuarios) {
                bw.write(usuario.nombre + "," + usuario.contrasena + "," + usuario.plan);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void guardarConfirmacionesEnCSV(String archivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (Confirmacion confirmacion : confirmaciones) {
                bw.write(confirmacion.numeroTarjeta + "," + confirmacion.claseVuelo + "," +
                        confirmacion.cantidadMaletas + "," + confirmacion.nombreUsuario);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
