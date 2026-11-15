<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%--
  VISTA (MVC): listado de todos los empleados.
  Recibe de ListarCommand (a través del Front Controller), en la petición:
    "empleados" -> List<Empleado>
--%>
<jsp:include page="/WEB-INF/jspf/cabecera.jsp"><jsp:param name="titulo" value="Listado de empleados"/></jsp:include>

<c:choose>
    <c:when test="${empty empleados}">
        <div class="msg vacio">No hay empleados registrados en la base de datos.</div>
    </c:when>
    <c:otherwise>
        <p class="sub">${empleados.size()} empleado(s), ordenados por DNI.</p>
        <table>
            <thead>
            <tr>
                <th>Nombre</th><th>DNI</th><th>Sexo</th>
                <th class="num">Categoría</th><th class="num">Años trabajados</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="e" items="${empleados}">
                <tr>
                    <td><c:out value="${e.nombre}"/></td>
                    <td><c:out value="${e.dni}"/></td>
                    <td><c:out value="${e.sexo}"/></td>
                    <td class="num"><c:out value="${e.categoria}"/></td>
                    <td class="num"><c:out value="${e.anyos}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/jspf/pie.jsp"/>
