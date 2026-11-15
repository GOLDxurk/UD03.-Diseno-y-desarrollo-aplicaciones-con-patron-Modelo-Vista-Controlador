<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%--
  VISTA (MVC): formulario de búsqueda de empleados por cualquiera de sus datos.
  Se muestra con: controlador?opcion=formBuscar
  Todos los campos son opcionales: se combinan con AND los que se rellenen.
  Envía por GET al Front Controller: controlador?opcion=buscar&nombre=..&dni=..&sexo=..&categoria=..&anyos=..
--%>
<jsp:include page="/WEB-INF/jspf/cabecera.jsp"><jsp:param name="titulo" value="Buscar empleados"/></jsp:include>
<p class="sub">Rellena uno o varios campos. Los que dejes vacíos no se tendrán en cuenta.</p>

<form action="${pageContext.request.contextPath}/controlador" method="get">
    <input type="hidden" name="opcion" value="buscar">
    <div class="campo">
        <label for="nombre">Nombre</label>
        <input type="text" id="nombre" name="nombre" placeholder="Parte del nombre">
    </div>
    <div class="campo">
        <label for="dni">DNI</label>
        <input type="text" id="dni" name="dni" maxlength="9" placeholder="Parte del DNI">
    </div>
    <div class="campo">
        <label for="sexo">Sexo</label>
        <select id="sexo" name="sexo">
            <option value="">-- Cualquiera --</option>
            <option value="M">M</option>
            <option value="F">F</option>
        </select>
    </div>
    <div class="campo">
        <label for="categoria">Categoría</label>
        <select id="categoria" name="categoria">
            <option value="">-- Cualquiera --</option>
            <% for (int i = 1; i <= 10; i++) { %>
            <option value="<%= i %>"><%= i %></option>
            <% } %>
        </select>
    </div>
    <div class="campo">
        <label for="anyos">Años trabajados</label>
        <input type="number" id="anyos" name="anyos" min="0" placeholder="Número exacto de años">
    </div>
    <button type="submit">Buscar</button>
</form>

<jsp:include page="/WEB-INF/jspf/pie.jsp"/>
