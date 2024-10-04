import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Exceptions.NotFoundProductoException;

public class Almacen {
    private ArrayList<Producto> productos;

    public Almacen() {
        this.productos = new ArrayList<Producto>();
    }

    public Producto getProducto(String nombre) throws NotFoundProductoException {
        for (Producto p : productos) {
            if (p.getNombre().equals(nombre)) {
                return p;
            }
        }
        throw new NotFoundProductoException(nombre);
    }

    public void updateProductoPrecio(Producto p) {
        for (Producto producto : productos) {
            if (producto.getNombre().equals(p.getNombre())) {
                producto.setPrecio(p.getPrecio());
                return;
            }
        }
    }

    public static Almacen getFromDB() throws FileNotFoundException {
        Almacen tienda = new Almacen();
        File dbFile = new File("db/db.csv");

        try (Scanner myReader = new Scanner(dbFile)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String regex = ",";
                String[] myArray = data.split(regex);
                tienda.productos.add(new Producto(myArray[0], Integer.valueOf(myArray[1]), Double.valueOf(myArray[2])));
            }
            myReader.close();
        }

        return tienda;
    }

    public void saveInDB() throws IOException {
        File dbFile = new File("db/db.csv");
        try (FileWriter fw = new FileWriter(dbFile)) {
            for (Producto p : productos) {
                fw.write(p.getNombre() + "," + p.getCantidad() + "," + p.getPrecio() + "\n");
            }
            fw.flush();
            fw.close();
            System.out.println("Guardado en BD");
        } catch (IOException e) {
            System.out.println("Error al guardar en BD");
            e.printStackTrace();
        }
    }

    public void removeProductos(ArrayList<Producto> prodsRemove) {
        for (Producto pr : prodsRemove) {
            for (Producto p : productos) {
                if (p.getNombre().equals(pr.getNombre())) {
                    p.setCantidad(p.getCantidad() - pr.getCantidad());
                    break;
                }
            }
        }
    }

    public void addProductos(ArrayList<Producto> prodsAdd) {
        boolean addFlag = false;

        for (Producto pa : prodsAdd) {
            addFlag = false;
            for (Producto p : productos) {
                if (p.getNombre().equals(pa.getNombre())) {
                    p.setCantidad(p.getCantidad() + pa.getCantidad());
                    addFlag = true;
                    break;
                }
            }
            if (!addFlag) {
                productos.add(pa);
            }
        }
    }

    public void printProductos() {
        for (Producto p : productos) {
            System.out.println(p.getNombre() + " | " + p.getCantidad() + " | " + p.getPrecio());
        }
    }
}
