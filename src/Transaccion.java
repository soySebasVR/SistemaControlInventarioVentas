import java.util.ArrayList;

public class Transaccion {
    private ArrayList<Producto> productos;
    private Almacen almacen;

    public Transaccion(Almacen almacen) {
        productos = new ArrayList<Producto>();
        this.almacen = almacen;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void addProducto(Producto p) {
        for (Producto producto : productos) {
            if (producto.getNombre().equals(p.getNombre())) {
                producto.setCantidad(producto.getCantidad() + p.getCantidad());
                return;
            }
        }
        productos.add(p);
    }

    public void removeProducto(String nombre, int cantidad) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getNombre().equals(nombre)) {
                if (productos.get(i).getCantidad() <= cantidad) {
                    productos.remove(i);
                } else {
                    productos.get(i).setCantidad(productos.get(i).getCantidad() - cantidad);
                }
                break;
            }
        }
    }

    public Producto getProducto(String nombre) {
        for (Producto p : productos) {
            if (p.getNombre().equals(nombre)) {
                return p;
            }
        }
        System.out.println("Producto no encontrado");
        return null;
    }

    public void printProductos() {
        for (Producto p : productos) {
            System.out.println(p.getNombre() + " | " + p.getCantidad() + " | " + p.getPrecio());
        }
    }
}
