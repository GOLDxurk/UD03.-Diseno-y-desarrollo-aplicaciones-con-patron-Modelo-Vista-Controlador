package empresa.controller;

import empresa.dao.EmpleadoDAO;
import empresa.dao.EmpleadoDAOJdbc;
import empresa.dao.NominaDAOJdbc;
import empresa.exceptions.DatosNoCorrectosException;
import empresa.exceptions.ReglaNegocioException;
import empresa.nomina.CalculadoraSueldo;
import empresa.nomina.SueldoPorCategoriaYAntiguedad;
import empresa.service.EmpleadoService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Patrón <b>Front Controller</b> dentro de la arquitectura <b>MVC</b>.
 *
 * <p>Es el único servlet de la aplicación y recibe todas las peticiones
 * (URL: {@code /controlador?opcion=...}). Los JSP están en {@code /WEB-INF/views},
 * así que el navegador no puede pedirlos directamente: la única entrada es este
 * servlet.</p>
 *
 * <p>Reparto de responsabilidades (separación de intereses, SoC):</p>
 * <ul>
 *   <li><b>Modelo</b>: {@code model} (Empleado) y {@code service} (lógica de negocio y DAO).</li>
 *   <li><b>Vista</b>: los JSP de {@code /WEB-INF/views}.</li>
 *   <li><b>Controlador</b>: este servlet, que solo hace cuatro cosas: pide el
 *       {@link Comando} a la {@link ComandoFactory}, lo ejecuta, trata los errores
 *       en un único sitio y reenvía a la vista.</li>
 * </ul>
 *
 * <p>Además es la "raíz de composición" de la aplicación: en {@link #init()} se
 * crean todos los objetos y se conectan entre sí (inyección de dependencias).
 * Es el único lugar que conoce las clases concretas.</p>
 *
 * @author Adrián Acevedo Reinoso
 */
@WebServlet(urlPatterns = "/controlador", loadOnStartup = 1)
public class FrontController extends HttpServlet {

    /** Carpeta (no accesible desde fuera) donde están los JSP de las vistas. */
    private static final String VISTAS = "/WEB-INF/views/";

    /** Opción que se usa cuando la petición no trae el parámetro "opcion". */
    private static final String OPCION_POR_DEFECTO = "menu";

    /** Crea los comandos que atienden cada opción. */
    private ComandoFactory factory;

    /**
     * Monta las dependencias de la aplicación, de dentro hacia fuera:
     * estrategia de sueldo -> DAO -> servicio (fachada) -> factoría de comandos.
     */
    @Override
    public void init() {
        CalculadoraSueldo calculadora = new SueldoPorCategoriaYAntiguedad();
        NominaDAOJdbc nominaDAO = new NominaDAOJdbc(calculadora);
        EmpleadoDAO empleadoDAO = new EmpleadoDAOJdbc(nominaDAO);
        EmpleadoService servicio = new EmpleadoService(empleadoDAO, nominaDAO);
        factory = new ComandoFactory(servicio);
    }

    /**
     * Atiende las peticiones GET (consultas y formularios).
     *
     * @param req  petición HTTP; parámetro principal: opcion
     * @param resp respuesta HTTP
     * @throws ServletException si falla el reenvío a la vista
     * @throws IOException      si ocurre un error de entrada/salida
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        procesar("GET", req, resp);
    }

    /**
     * Atiende las peticiones POST (operaciones que modifican datos).
     *
     * @param req  petición HTTP; parámetro principal: opcion
     * @param resp respuesta HTTP
     * @throws ServletException si falla el reenvío a la vista
     * @throws IOException      si ocurre un error de entrada/salida
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        procesar("POST", req, resp);
    }

    /**
     * Flujo común a todas las peticiones: obtiene el comando, lo ejecuta y
     * reenvía a la vista resultante. Todos los errores se tratan aquí, de modo
     * que los comandos no repiten código de gestión de errores (DRY).
     *
     * @param metodo método HTTP de la petición ("GET" o "POST")
     * @param req    petición HTTP
     * @param resp   respuesta HTTP
     * @throws ServletException si falla el reenvío a la vista
     * @throws IOException      si ocurre un error de entrada/salida
     */
    private void procesar(String metodo, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8"); // para tildes y ñ en los datos del formulario

        String opcion = req.getParameter("opcion");
        if (opcion == null || opcion.isEmpty()) {
            opcion = OPCION_POR_DEFECTO;
        }

        Comando comando = factory.crear(metodo, opcion);
        if (comando == null) {
            mostrarError(req, resp, "Opción no válida: " + opcion);
            return;
        }

        try {
            reenviar(req, resp, comando.ejecutar(req));
        } catch (ReglaNegocioException e) {
            mostrarError(req, resp, e.getMessage());
        } catch (DatosNoCorrectosException e) {
            mostrarError(req, resp, "Datos no correctos: revisa el DNI (9 caracteres), el sexo (M/F), "
                    + "la categoría (1-10) y los años (0 o más).");
        } catch (NumberFormatException e) {
            mostrarError(req, resp, "Faltan datos o hay un campo numérico con un valor no válido.");
        } catch (SQLException e) {
            mostrarError(req, resp, "Error al acceder a la base de datos: " + e.getMessage());
        }
    }

    /**
     * Reenvía la petición a una vista (JSP) de la carpeta de vistas.
     *
     * @param req   petición HTTP, con los atributos que usará la vista
     * @param resp  respuesta HTTP
     * @param vista nombre de la vista, sin extensión (por ejemplo "listado")
     * @throws ServletException si falla el reenvío
     * @throws IOException      si ocurre un error de entrada/salida
     */
    private void reenviar(HttpServletRequest req, HttpServletResponse resp, String vista)
            throws ServletException, IOException {
        req.getRequestDispatcher(VISTAS + vista + ".jsp").forward(req, resp);
    }

    /**
     * Guarda un mensaje de error en la petición (atributo "error")
     * y reenvía a la vista de error.
     *
     * @param req     petición HTTP
     * @param resp    respuesta HTTP
     * @param mensaje texto del error que verá el usuario
     * @throws ServletException si falla el reenvío
     * @throws IOException      si ocurre un error de entrada/salida
     */
    private void mostrarError(HttpServletRequest req, HttpServletResponse resp, String mensaje)
            throws ServletException, IOException {
        req.setAttribute("error", mensaje);
        reenviar(req, resp, "error");
    }
}
