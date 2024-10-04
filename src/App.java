import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) throws InterruptedException {
        String opcion;
        System.out.println("Bienvenido al Sistema de Ventas de la Bodega San Judas");

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
                System.out.println("3. Salir");
                opcion = sc.nextLine();
                TimeUnit.MILLISECONDS.sleep(300);

                if (opcion.equals("1")) {
                    Venta(sc, almacen);
                    TimeUnit.SECONDS.sleep(1);
                } else if (opcion.equals("2")) {
                    //NuevoProducto(sc);
                    TimeUnit.SECONDS.sleep(1);
                } else if (opcion.equals("3")) {
                    System.out.println("Muchas Gracias");
                    System.exit(0);
                } else {
                    System.out.println("Número ingresado no es válido");
                }
            }
        }
    }

    public static void Venta(Scanner sc, Almacen tienda) {
        TransaccionVenta transaccion = new TransaccionVenta(tienda);
        transaccion.iniciarTransaccion(sc);
    }
}
