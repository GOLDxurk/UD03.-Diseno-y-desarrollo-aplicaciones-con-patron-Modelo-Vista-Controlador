<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%--
  VISTA (MVC): menú principal. Se muestra con: controlador?opcion=menu
  Todos los enlaces apuntan al Front Controller (/controlador); ya no se enlaza a
  ningún JSP directamente porque las vistas están en /WEB-INF/views.
--%>
<c:set var="base" value="${pageContext.request.contextPath}/controlador"/>
<jsp:include page="/WEB-INF/jspf/cabecera.jsp"><jsp:param name="titulo" value="Menú de opciones"/></jsp:include>
<p class="sub">Gestión de empleados y nóminas</p>
<ul class="menu">
    <li><a href="${base}?opcion=listar">Mostrar todos los empleados
        <small>Nombre, DNI, sexo, categoría y años trabajados</small></a></li>
    <li><a href="${base}?opcion=formSalario">Mostrar el salario de un empleado
        <small>Búsqueda por DNI</small></a></li>
    <li><a href="${base}?opcion=formBuscar">Buscar / modificar empleados
        <small>Búsqueda por cualquiera de sus datos</small></a></li>
    <li><a href="${base}?opcion=nuevo">Crear nuevo empleado
        <small>El sueldo se calcula automáticamente</small></a></li>
</ul>
</div>
</body>
</html>
