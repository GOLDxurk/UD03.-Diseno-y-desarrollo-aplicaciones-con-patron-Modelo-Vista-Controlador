<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%--
  VISTA (MVC): muestra el salario de un empleado.
  Recibe de EmpleadoConSueldoCommand (a través del Front Controller), en la petición:
    "empleado" -> Empleado  (el empleado encontrado, o null si no existe)
    "sueldo"   -> Double    (su sueldo en la tabla Nominas)
--%>
<fmt:setLocale value="es_ES"/>
<jsp:include page="/WEB-INF/jspf/cabecera.jsp"><jsp:param name="titulo" value="Salario del empleado"/></jsp:include>

<c:choose>
    <c:when test="${empty empleado}">
        <div class="msg error">No se ha encontrado ningún empleado con ese DNI.</div>
    </c:when>
    <c:otherwise>
        <table>
            <thead>
            <tr><th>Nombre</th><th>DNI</th><th class="num">Categoría</th><th class="num">Años</th></tr>
            </thead>
            <tbody>
            <tr>
                <td><c:out value="${empleado.nombre}"/></td>
                <td><c:out value="${empleado.dni}"/></td>
                <td class="num"><c:out value="${empleado.categoria}"/></td>
                <td class="num"><c:out value="${empleado.anyos}"/></td>
            </tr>
            </tbody>
        </table>

        <c:choose>
            <c:when test="${empty sueldo}">
                <div class="msg vacio">Este empleado todavía no tiene nómina registrada.</div>
            </c:when>
            <c:otherwise>
                <p>Salario:</p>
                <p class="destacado"><fmt:formatNumber value="${sueldo}" type="currency" currencySymbol="€"/></p>
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/jspf/pie.jsp"/>
