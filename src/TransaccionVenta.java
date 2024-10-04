import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class TransaccionVenta extends Transaccion {
    private String id;
    private LocalDateTime fechaHora;

    public TransaccionVenta(Almacen almacen) {
        super(almacen);
        id = String.valueOf(Math.random() * 10000 + 1000);
        fechaHora = LocalDateTime.now();
    }

    public int getTotal() {
        int total = 0;
        for (Producto p : getProductos()) {
            total += p.getCantidad() * p.getPrecio();
        }
        return total;
    }

    public void iniciarTransaccion(Scanner sc) {
        System.out.println("Transaccion iniciada");
        String opcion;
        String nombre;
        String cantidadString;
        int cantidad;
        String respuesta;

        while (true) {
            System.out.println("\nQue desea hacer?");
            System.out.println("1. Agregar productos");
            System.out.println("2. Quitar producto");
            System.out.println("3. Salir");
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
                            addProductoVenta(nombre, cantidad);
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
                break;
            } else {
                System.out.println("Opcion no valida");
            }
        }

        Almacen almacen = getAlmacen();
        ArrayList<Producto> productos = getProductos();
        almacen.removeProductos(productos);
        try {
            almacen.saveInDB();
            saveRecibo();
        } catch (IOException e) {
            System.out.println("Error al guardar recibo");
        }
    }

    private void saveRecibo() throws IOException {
        String recibo = "Recibo " + id + "\nFecha y Hora: " + fechaHora + "\n\n";
        for (Producto p : getProductos()) {
            recibo += p.getNombre() + " | " + p.getCantidad() + " | " + p.getPrecio() + "\n";
        }
        recibo += "\nTotal: " + getTotal();

        File reciboFile = new File("db/recibos/" + fechaHora + "-" + id + ".txt");
        try (FileWriter fw = new FileWriter(reciboFile)) {
            fw.write(recibo);
        }
    }

    private void addProductoVenta(String nombre, int cantidad) {
        int cantidadTransaccionTotal = 0;
        Producto prodAlmacen = getAlmacen().getProducto(nombre);
        if (prodAlmacen.getCantidad() == 0) {
            System.out.println("Producto no encontrado");
            return;
        }
        Producto prodTransaccion = getProducto(nombre);

        if (prodTransaccion == null) {
            cantidadTransaccionTotal = cantidad;
        } else {
            cantidadTransaccionTotal = prodTransaccion.getCantidad() + cantidad;
        }

        if (cantidadTransaccionTotal > cantidad) {
            System.out.println("No hay suficiente cantidad en almacen");
            return;
        }
        addProducto(new Producto(nombre, cantidad, prodAlmacen.getPrecio()));
    }
}
