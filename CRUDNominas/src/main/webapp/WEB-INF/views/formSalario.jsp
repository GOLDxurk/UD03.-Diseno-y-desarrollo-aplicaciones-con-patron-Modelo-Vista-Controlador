<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%--
  VISTA (MVC): formulario para consultar el salario de un empleado por DNI.
  Se muestra con: controlador?opcion=formSalario
  Envía por GET al Front Controller: controlador?opcion=salario&dni=XXXXXXXXX
--%>
<jsp:include page="/WEB-INF/jspf/cabecera.jsp"><jsp:param name="titulo" value="Consultar salario"/></jsp:include>
<p class="sub">Introduce el DNI del empleado cuyo salario quieres consultar.</p>

<form action="${pageContext.request.contextPath}/controlador" method="get">
    <input type="hidden" name="opcion" value="salario">
    <div class="campo">
        <label for="dni">DNI</label>
        <input type="text" id="dni" name="dni" required minlength="9" maxlength="9"
               placeholder="12345678A" autofocus>
        <span class="ayuda">9 caracteres (8 números y la letra).</span>
    </div>
    <button type="submit">Consultar salario</button>
</form>

<jsp:include page="/WEB-INF/jspf/pie.jsp"/>
