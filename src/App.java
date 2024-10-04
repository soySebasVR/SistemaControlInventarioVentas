import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import Exceptions.NotFoundProductoException;

public class App {
    public static void main(String[] args) throws InterruptedException {
        String opcion;
        System.out.println("Bienvenido(a) al Sistema de Ventas");

        Almacen almacen;
        try {
            almacen = Almacen.getFromDB();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
            return;
        }

        System.out.println("Ingrese opcion");
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n¿Qué desea hacer?");
                System.out.println("1. Ingresar venta");
                System.out.println("2. Ingresar nuevo producto");
                System.out.println("3. Obtener producto");
                System.out.println("4. Actualizar precio");
                System.out.println("5. Salir");
                opcion = sc.nextLine();
                TimeUnit.MILLISECONDS.sleep(300);

                if (opcion.equals("1")) {
                    Venta(sc, almacen);
                    TimeUnit.SECONDS.sleep(1);
                } else if (opcion.equals("2")) {
                    NuevoProducto(sc, almacen);
                    TimeUnit.SECONDS.sleep(1);
                } else if (opcion.equals("3")) {
                    ObtenerProducto(sc, almacen);
                    TimeUnit.SECONDS.sleep(1);
                } else if (opcion.equals("4")) {
                    ActualizarPrecio(almacen, sc);
                    TimeUnit.SECONDS.sleep(1);
                } else if (opcion.equals("5")) {
                    System.out.println("Muchas Gracias");
                    break;
                } else {
                    System.out.println("Número ingresado no es válido");
                }
            }
        }
    }

    public static void Venta(Scanner sc, Almacen almacen) {
        TransaccionVenta transaccion = new TransaccionVenta(almacen);
        transaccion.iniciarTransaccion(sc);
    }

    public static void NuevoProducto(Scanner sc, Almacen almacen) {
        TransaccionNuevoProducto transaccion = new TransaccionNuevoProducto(almacen);
        transaccion.iniciarTransaccion(sc);
    }

    public static void ObtenerProducto(Scanner sc, Almacen almacen) {
        String opcion;
        System.out.println("Qué desea hacer?");
        System.out.println("1. Listar productos");
        System.out.println("2. Obtener producto");
        opcion = sc.nextLine();
        if (opcion.equals("1")) {
            almacen.printProductos();
        } else if (opcion.equals("2")) {
            System.out.println("Ingrese nombre del producto");
            String nombre = sc.nextLine();
            Producto p;
            try {
                p = almacen.getProducto(nombre);
                System.out.println(p.getNombre() + " | " + p.getCantidad() + " | " + p.getPrecio());
            } catch (NotFoundProductoException e) {
                System.out.println("Producto no encontrado");
            }
        }
    }

    public static void ActualizarPrecio(Almacen almacen, Scanner sc) {
        String precioString;
        double precio;
        System.out.println("Ingrese nombre del producto");
        String nombre = sc.nextLine();
        if (nombre.equals("")) {
            return;
        }
        Producto p;
        try {
            p = almacen.getProducto(nombre);
            System.out.println("Ingrese nuevo precio");
            precioString = sc.nextLine();
            precio = Double.valueOf(precioString);
            p.setPrecio(precio);
            almacen.updateProductoPrecio(p);
            System.out.println("Producto actualizado");
        } catch (NotFoundProductoException e) {
            System.out.println("Producto no encontrado");
        } catch (NumberFormatException e) {
            System.out.println("Error al ingresar precio");
        }
    }
}
