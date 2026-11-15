<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%--
  VISTA (MVC): página de error. Se puede usar de dos formas:
   1) Desde el Front Controller: request.setAttribute("error", "texto"); forward a esta vista
      (así se muestran los errores de validación, de negocio y de base de datos).
   2) Como error-page de web.xml (404 / excepciones no controladas).
--%>
<jsp:include page="/WEB-INF/jspf/cabecera.jsp"><jsp:param name="titulo" value="Se ha producido un error"/></jsp:include>

<div class="msg error">
    <c:choose>
        <c:when test="${not empty error}"><c:out value="${error}"/></c:when>
        <c:when test="${pageContext.errorData.statusCode == 404}">La página que buscas no existe.</c:when>
        <c:otherwise>Ha ocurrido un error inesperado. Inténtalo de nuevo más tarde.</c:otherwise>
    </c:choose>
</div>

<jsp:include page="/WEB-INF/jspf/pie.jsp"/>
