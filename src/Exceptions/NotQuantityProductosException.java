package Exceptions;

public class NotQuantityProductosException extends Exception {
    public NotQuantityProductosException(String nombre) {
        super("No existe suficiente cantidad del producto " + nombre);
    }
}
