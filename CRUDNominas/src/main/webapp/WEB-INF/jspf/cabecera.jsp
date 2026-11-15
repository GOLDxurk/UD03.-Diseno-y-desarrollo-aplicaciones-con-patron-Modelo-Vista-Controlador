<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%--
  Cabecera común. Uso:
  <jsp:include page="/WEB-INF/jspf/cabecera.jsp"><jsp:param name="titulo" value="Mi título"/></jsp:include>
--%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${param.titulo} - Empresa</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<div class="contenedor">
    <h1>${param.titulo}</h1>
