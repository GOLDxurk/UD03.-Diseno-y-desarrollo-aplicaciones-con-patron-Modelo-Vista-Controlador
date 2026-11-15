package empresa.nomina;

/**
 * Estrategia concreta de {@link CalculadoraSueldo}: es la fórmula que ya se
 * usaba en la unidad 2 (antes, clase {@code Nomina}). El sueldo es el sueldo
 * base de la categoría más 5000 euros por cada año de antigüedad.
 *
 * @author Adrián Acevedo Reinoso
 */
public class SueldoPorCategoriaYAntiguedad implements CalculadoraSueldo {

    /** Sueldo base anual según la categoría (índice 0 -> categoría 1, etc.). */
    private static final int[] SUELDO_BASE = {50000, 70000, 90000, 110000, 130000,
            150000, 170000, 190000, 210000, 230000};

    /** Euros que se suman por cada año de antigüedad. */
    private static final int PLUS_POR_ANYO = 5000;

    /**
     * Calcula el sueldo total como el sueldo base de la categoría
     * más 5000 euros por cada año de antigüedad.
     * (Una categoría 0, sin asignar, se trata como la categoría 1.)
     *
     * @param categoria categoría laboral del empleado (1-10; 0 se trata como 1)
     * @param anyos     años de antigüedad del empleado
     * @return el sueldo total en euros
     * @throws ArrayIndexOutOfBoundsException si la categoría no está entre 0 y 10
     */
    @Override
    public double sueldo(int categoria, int anyos) {
        int posicion = (categoria == 0) ? 0 : categoria - 1;
        return SUELDO_BASE[posicion] + PLUS_POR_ANYO * anyos;
    }
}
