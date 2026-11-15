package empresa.exceptions;

/**
 * Excepción no comprobada que lanza la capa de servicio cuando se incumple
 * una regla de negocio (por ejemplo, dar de alta un DNI que ya existe o
 * modificar un empleado que no existe).
 *
 * <p>El {@code FrontController} la captura en un único punto y muestra su
 * mensaje al usuario en la vista de error.</p>
 *
 * @author Adrián Acevedo Reinoso
 */
public class ReglaNegocioException extends RuntimeException {

    /**
     * Crea la excepción con un mensaje pensado para el usuario.
     *
     * @param message explicación de la regla que no se cumple
     */
    public ReglaNegocioException(String message) {
        super(message);
    }
}
