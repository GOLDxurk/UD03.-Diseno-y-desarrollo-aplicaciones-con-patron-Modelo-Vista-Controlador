package empresa.controller.comandos;

import empresa.exceptions.DatosNoCorrectosException;
import empresa.model.Empleado;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Clase de utilidades para leer parámetros de la petición. Agrupa el código
 * que usan varios comandos para no repetirlo (principio DRY).
 *
 * @author Adrián Acevedo Reinoso
 */
final class Peticion {

    /** Clase de utilidades: no se instancia. */
    private Peticion() {
    }

    /**
     * Convierte un parámetro de texto a Integer.
     *
     * @param valor texto recibido en el parámetro (puede ser {@code null} o vacío)
     * @return el número, o {@code null} si el texto es {@code null} o está vacío
     * @throws NumberFormatException si el texto no es un número válido
     */
    static Integer aEntero(String valor) {
        return (valor == null || valor.isEmpty()) ? null : Integer.valueOf(valor.trim());
    }

    /**
     * Construye un {@link Empleado} con los parámetros del formulario
     * (nombre, dni, sexo, categoria y anyos). Al crearlo se validan los datos.
     *
     * @param req petición con los datos del formulario
     * @return el empleado con los datos recibidos
     * @throws DatosNoCorrectosException si falta algún dato o no es válido
     * @throws NumberFormatException     si categoría o años no son números
     */
    static Empleado empleadoDe(HttpServletRequest req) {
        String nombre = req.getParameter("nombre");
        String dni = req.getParameter("dni");
        String sexo = req.getParameter("sexo");
        if (nombre == null || dni == null || sexo == null || sexo.isEmpty()) {
            throw new DatosNoCorrectosException("Faltan datos.");
        }
        return new Empleado(nombre, dni, sexo.charAt(0),
                Integer.parseInt(req.getParameter("categoria")),
                Integer.parseInt(req.getParameter("anyos")));
    }
}
