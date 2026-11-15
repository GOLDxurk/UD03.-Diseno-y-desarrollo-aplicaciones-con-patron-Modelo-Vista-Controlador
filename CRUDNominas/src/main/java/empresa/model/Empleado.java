package empresa.model;
import empresa.exceptions.DatosNoCorrectosException;

/**
 * Clase Empleado, hereda de Persona.
 * Añade la categoría laboral (1-10) y los años de antiguedad.
 *
 * @author Adrián Acevedo Reinoso
 */
public class Empleado extends Persona {
    /** Categoría laboral del empleado (1-10). Vale 0 si no se ha asignado. */
    private int categoria;

    /** Años de antigüedad del empleado (0 o más). */
    public int anyos;


    /**
     * Constructor completo: crea un empleado con categoría y años.
     * Válida:
     * <ul>
     *   <li>categoria entre 1 y 10</li>
     *   <li>anyos &gt;= 0</li>
     *   <li>dni con longitud exactamente 9</li>
     *   <li>sexo únicamente 'M' o 'F'</li>
     * </ul>
     *
     * @param nombre    nombre del empleado
     * @param dni       DNI del empleado (9 caracteres)
     * @param sexo      sexo del empleado ('M' o 'F')
     * @param categoria categoría laboral (de 1 a 10)
     * @param anyos     años de antigüedad (0 o más)
     * @throws DatosNoCorrectosException si algún dato no es válido
     */
    public Empleado(String nombre, String dni, char sexo, int categoria, int anyos) {

        super(nombre, dni, sexo);
        if (categoria < 1 || categoria > 10 || anyos < 0 || dni.length() < 9 || dni.length() > 9 || (sexo != 'M' && sexo != 'F')) {
            throw new DatosNoCorrectosException("Datos no correctos.");
        }
        this.categoria = categoria;
        this.anyos = anyos;
    }

    /**
     * Constructor reducido: crea un empleado sin categoría ni años iniciales
     * (quedan a los valores por defecto: categoria = 0, anyos = 0).
     * Válida el DNI y el sexo ('M' o 'F').
     *
     * @param nombre nombre del empleado
     * @param dni    DNI del empleado (9 caracteres)
     * @param sexo   sexo del empleado ('M' o 'F')
     * @throws DatosNoCorrectosException si el DNI no tiene 9 caracteres
     *                                   o el sexo no es 'M' ni 'F'
     */
    public Empleado(String nombre, String dni, char sexo) {
        super(nombre, dni, sexo);
        if (dni.length() < 9 || dni.length() > 9 || (sexo != 'M' && sexo != 'F')) {
            throw new DatosNoCorrectosException("Datos no correctos.");
        }

    }

    /**
     * Cambia la categoría, solo si está dentro del rango permitido (1-10).
     * Si el valor está fuera de rango, no se modifica nada.
     *
     * @param nuevaCategoria nueva categoría laboral (de 1 a 10)
     */
    public void setCategoria(int nuevaCategoria) {
        if (nuevaCategoria >= 1 && nuevaCategoria <= 10) {
            categoria = nuevaCategoria;
        }
    }

    /**
     * Devuelve la categoría laboral del empleado.
     *
     * @return la categoría (1-10), o 0 si no se ha asignado
     */
    public int getCategoria() {
        return categoria;
    }

    /**
     * Devuelve los años de antigüedad del empleado.
     *
     * @return los años de antigüedad
     */
    public int getAnyos() {
        return anyos;
    }

    /**
     * Incrementa en uno los años de antigüedad (por ejemplo, cada aniversario).
     */
    public void incrAnyo() {
        anyos++;
    }

    /**
     * Imprime por pantalla los datos heredados de Persona
     * y además la categoría y los años de antigüedad.
     */
    public void imprime() {
        super.imprime();
        System.out.println("Categoria: " + categoria + "\nanyos: " + anyos);
    }
}
