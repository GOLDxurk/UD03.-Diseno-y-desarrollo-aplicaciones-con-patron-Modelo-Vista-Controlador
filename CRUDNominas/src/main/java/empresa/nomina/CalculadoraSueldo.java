package empresa.nomina;

import empresa.model.Empleado;

/**
 * Patrón <b>Strategy</b> (de comportamiento): define la "familia" de algoritmos
 * para calcular el sueldo de un empleado.
 *
 * <p>Quien necesita un sueldo (por ejemplo {@code NominaDAOJdbc}) depende solo
 * de esta interfaz y no de una fórmula concreta (principio de inversión de
 * dependencias). Si la empresa cambia la forma de calcular las nóminas, basta
 * con crear otra clase que implemente la interfaz y cambiar la línea donde se
 * crea en {@code FrontController}; el resto del código no se toca.</p>
 *
 * @author Adrián Acevedo Reinoso
 */
public interface CalculadoraSueldo {

    /**
     * Calcula el sueldo a partir de la categoría y la antigüedad.
     *
     * @param categoria categoría laboral del empleado
     * @param anyos     años de antigüedad del empleado
     * @return el sueldo total en euros
     */
    double sueldo(int categoria, int anyos);

    /**
     * Calcula el sueldo de un empleado a partir de su categoría y sus años.
     *
     * @param empleado empleado del que se calcula el sueldo
     * @return el sueldo total en euros
     */
    default double sueldo(Empleado empleado) {
        return sueldo(empleado.getCategoria(), empleado.getAnyos());
    }
}
