package empresa.exceptions;

/**
 * Excepción no comprobada que se lanza cuando los datos de un empleado
 * no son válidos (DNI que no tiene 9 caracteres, sexo distinto de 'M'/'F',
 * categoría fuera del rango 1-10 o años negativos).
 *
 * @author Adrián Acevedo Reinoso
 */
public class DatosNoCorrectosException extends RuntimeException {

    /**
     * Crea la excepción con un mensaje descriptivo.
     *
     * @param message mensaje que explica qué dato no es correcto
     */
    public DatosNoCorrectosException(String message) {
        super(message);
    }
}
