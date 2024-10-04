package Exceptions;

public class NotFoundProductoException extends Exception {
    public NotFoundProductoException(String nombre) {
        super("No existe el producto " + nombre);
    }
}
