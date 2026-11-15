<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%--
  VISTA (MVC): formulario para dar de alta un nuevo empleado.
  Se muestra con: controlador?opcion=nuevo (GET).
  Envía por POST al Front Controller: controlador (opcion=crear). El sueldo se calcula solo.
--%>
<jsp:include page="/WEB-INF/jspf/cabecera.jsp"><jsp:param name="titulo" value="Nuevo empleado"/></jsp:include>

<form action="${pageContext.request.contextPath}/controlador" method="post">
    <input type="hidden" name="opcion" value="crear">
    <div class="campo">
        <label for="nombre">Nombre</label>
        <input type="text" id="nombre" name="nombre" required autofocus>
    </div>
    <div class="campo">
        <label for="dni">DNI</label>
        <input type="text" id="dni" name="dni" required minlength="9" maxlength="9" placeholder="12345678A">
    </div>
    <div class="campo">
        <label for="sexo">Sexo</label>
        <select id="sexo" name="sexo" required>
            <option value="M">M</option>
            <option value="F">F</option>
        </select>
    </div>
    <div class="campo">
        <label for="categoria">Categoría (1-10)</label>
        <input type="number" id="categoria" name="categoria" min="1" max="10" required>
    </div>
    <div class="campo">
        <label for="anyos">Años trabajados</label>
        <input type="number" id="anyos" name="anyos" min="0" value="0" required>
    </div>
    <button type="submit">Crear empleado</button>
</form>

<jsp:include page="/WEB-INF/jspf/pie.jsp"/>
