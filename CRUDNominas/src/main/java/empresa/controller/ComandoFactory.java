package empresa.controller;

import empresa.controller.comandos.ActualizarCommand;
import empresa.controller.comandos.BuscarCommand;
import empresa.controller.comandos.CrearCommand;
import empresa.controller.comandos.EmpleadoConSueldoCommand;
import empresa.controller.comandos.ListarCommand;
import empresa.controller.comandos.VistaCommand;
import empresa.service.EmpleadoService;

import java.util.HashMap;
import java.util.Map;

/**
 * Patrón <b>Factory</b> (creacional, versión "simple factory"): centraliza la
 * creación de los {@link Comando} y decide cuál corresponde a cada petición.
 *
 * <p>Es el único sitio donde se relacionan los nombres de las acciones
 * (parámetro {@code opcion}) con las clases que las atienden. La clave
 * combina el método HTTP y la opción ("GET listar", "POST crear"), de modo que
 * las operaciones que modifican datos solo se pueden lanzar por POST.</p>
 *
 * <p>Los comandos se crean una sola vez y se reutilizan: no guardan estado
 * entre peticiones, solo reciben el {@link EmpleadoService} por el
 * constructor (inyección de dependencias).</p>
 *
 * @author Adrián Acevedo Reinoso
 */
public class ComandoFactory {

    /** Comandos registrados, indexados por "MÉTODO opcion". */
    private final Map<String, Comando> comandos = new HashMap<>();

    /**
     * Crea la factoría y registra todos los comandos de la aplicación.
     *
     * @param servicio fachada con las operaciones de negocio, que se inyecta
     *                 en los comandos que la necesitan
     */
    public ComandoFactory(EmpleadoService servicio) {
        // Pantallas que solo muestran un formulario o el menú (no consultan nada)
        registrar("GET", "menu", new VistaCommand("menu"));
        registrar("GET", "formSalario", new VistaCommand("formSalario"));
        registrar("GET", "formBuscar", new VistaCommand("formBuscar"));
        registrar("GET", "nuevo", new VistaCommand("crear"));

        // Consultas (GET)
        registrar("GET", "listar", new ListarCommand(servicio));
        registrar("GET", "buscar", new BuscarCommand(servicio));
        registrar("GET", "salario", new EmpleadoConSueldoCommand(servicio, "salario"));
        registrar("GET", "editar", new EmpleadoConSueldoCommand(servicio, "editar"));

        // Operaciones que modifican datos (POST)
        registrar("POST", "crear", new CrearCommand(servicio));
        registrar("POST", "actualizar", new ActualizarCommand(servicio));
    }

    /**
     * Devuelve el comando que atiende una petición.
     *
     * @param metodo método HTTP ("GET" o "POST")
     * @param opcion valor del parámetro {@code opcion}
     * @return el comando correspondiente, o {@code null} si no hay ninguno
     */
    public Comando crear(String metodo, String opcion) {
        return comandos.get(metodo + " " + opcion);
    }

    /**
     * Registra un comando con su método HTTP y su opción.
     *
     * @param metodo  método HTTP que lo activa
     * @param opcion  valor del parámetro {@code opcion} que lo activa
     * @param comando comando que se ejecutará
     */
    private void registrar(String metodo, String opcion, Comando comando) {
        comandos.put(metodo + " " + opcion, comando);
    }
}
