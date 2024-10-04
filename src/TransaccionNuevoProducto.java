import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TransaccionNuevoProducto extends Transaccion {

    public TransaccionNuevoProducto(Almacen almacen) {
        super(almacen);
    }

    public void iniciarTransaccion(Scanner sc) {
        System.out.println("Transaccion iniciada");
        String nombre;
        String cantidadString;
        int cantidad;
        String respuesta;
        String opcion;

        while (true) {
            System.out.println("\nQue desea hacer?");
            System.out.println("1. Agregar productos");
            System.out.println("2. Quitar producto");
            System.out.println("3. Imprimir productos");
            System.out.println("4. Guardar productos y salir");
            System.out.println("3. Salir sin guardar productos");
            opcion = sc.nextLine();

            if (opcion.equals("1")) {
                while (true) {
                    System.out.println("Ingrese nombre del producto");
                    nombre = sc.nextLine();
                    if (!nombre.equals("")) {
                        System.out.println("Ingrese cantidad");
                        cantidadString = sc.nextLine();
                        try {
                            cantidad = Integer.valueOf(cantidadString);
                            addProducto(new Producto(nombre, cantidad, 0));
                        } catch (NumberFormatException e) {
                            System.out.println("Error al ingresar cantidad");
                        }
                    }
                    System.out.println("Desea agregar mas productos? (S/N)");
                    respuesta = sc.nextLine();
                    if (respuesta.equals("N") | respuesta.equals("n")) {
                        break;
                    }
                }
            } else if (opcion.equals("2")) {
                System.out.println("Ingrese nombre del producto");
                nombre = sc.nextLine();
                cantidadString = sc.nextLine();
                try {
                    cantidad = Integer.valueOf(cantidadString);
                    removeProducto(nombre, cantidad);
                } catch (NumberFormatException e) {
                    System.out.println("Error al ingresar cantidad");
                }
            } else if (opcion.equals("3")) {
                printProductos();
            } else if (opcion.equals("4")) {
                break;
            } else if (opcion.equals("5")) {
                return;
            } else {
                System.out.println("Opcion no valida");
            }

            Almacen almacen = getAlmacen();
            ArrayList<Producto> productos = getProductos();
            almacen.addProductos(productos);
            try {
                almacen.saveInDB();
            } catch (IOException e) {
                System.out.println("Error al guardar recibo");
            }
        }
    }
}
