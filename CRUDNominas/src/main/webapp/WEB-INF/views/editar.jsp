<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%--
  VISTA (MVC): formulario de modificación de un empleado.
  Recibe del comando EmpleadoConSueldoCommand (a través del Front Controller), en la petición:
    "empleado" -> Empleado a modificar
    "sueldo"   -> Double (sueldo actual, solo se muestra; no es editable)
  Envía por POST al Front Controller: controlador (opcion=actualizar)
  El DNI es la clave, por eso va en solo lectura.
--%>
<fmt:setLocale value="es_ES"/>
<jsp:include page="/WEB-INF/jspf/cabecera.jsp"><jsp:param name="titulo" value="Modificar empleado"/></jsp:include>

<c:choose>
    <c:when test="${empty empleado}">
        <div class="msg error">No se ha encontrado el empleado a modificar.</div>
    </c:when>
    <c:otherwise>
        <form action="${pageContext.request.contextPath}/controlador" method="post">
            <input type="hidden" name="opcion" value="actualizar">
            <div class="campo">
                <label for="dni">DNI</label>
                <input type="text" id="dni" name="dni" value="<c:out value='${empleado.dni}'/>" readonly>
            </div>
            <div class="campo">
                <label for="nombre">Nombre</label>
                <input type="text" id="nombre" name="nombre" value="<c:out value='${empleado.nombre}'/>" required>
            </div>
            <div class="campo">
                <label for="sexo">Sexo</label>
                <select id="sexo" name="sexo" required>
                    <option value="M" ${empleado.sexo.toString() == 'M' ? 'selected' : ''}>M</option>
                    <option value="F" ${empleado.sexo.toString() == 'F' ? 'selected' : ''}>F</option>
                </select>
            </div>
            <div class="campo">
                <label for="categoria">Categoría (1-10)</label>
                <input type="number" id="categoria" name="categoria" min="1" max="10"
                       value="${empleado.categoria}" required>
            </div>
            <div class="campo">
                <label for="anyos">Años trabajados</label>
                <input type="number" id="anyos" name="anyos" min="0" value="${empleado.anyos}" required>
            </div>
            <div class="campo">
                <label for="sueldo">Sueldo (calculado automáticamente)</label>
                <input type="text" id="sueldo" readonly
                       value="<c:if test='${not empty sueldo}'><fmt:formatNumber value='${sueldo}' type='currency' currencySymbol='€'/></c:if>">
                <span class="ayuda">Se recalcula al cambiar la categoría o los años.</span>
            </div>
            <button type="submit">Guardar cambios</button>
        </form>
    </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/jspf/pie.jsp"/>
