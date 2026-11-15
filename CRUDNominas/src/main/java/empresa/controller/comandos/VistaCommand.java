package empresa.controller.comandos;

import empresa.controller.Comando;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Comando para las pantallas que solo muestran una vista (el menú y los
 * formularios) y no necesitan consultar datos. Una misma clase sirve para
 * todas, indicando la vista en el constructor.
 *
 * @author Adrián Acevedo Reinoso
 */
public class VistaCommand implements Comando {

    /** Vista que se muestra. */
    private final String vista;

    /**
     * Crea el comando.
     *
     * @param vista nombre de la vista a mostrar, sin extensión (por ejemplo "menu")
     */
    public VistaCommand(String vista) {
        this.vista = vista;
    }

    /** {@inheritDoc} */
    @Override
    public String ejecutar(HttpServletRequest req) {
        return vista;
    }
}
