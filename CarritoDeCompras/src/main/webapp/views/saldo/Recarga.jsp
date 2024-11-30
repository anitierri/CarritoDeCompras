<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<title>Recargar Saldo</title>
<link rel="stylesheet" href="css/styles.css">

</head>
<body>
	<h1>Recargar Saldo</h1>

	<p>
		Saldo actual : $
		<fmt:formatNumber type="number" groupingUsed="true"
			value="${saldoActual}" />
	</p>
	<c:if test="${not empty error}">
		<p style="color: red;">${error}</p>
	</c:if>

	<form action="saldo" method="post">
		<input type="hidden" name="accion" value="recarga"> <label
			for="monto">Monto a recargar:</label> <input type="number" id="monto"
			name="monto" step="0.01" min="0.01" required> <input
			type="submit" value="Recargar">
	</form>
	<br>

	<a href="saldo?accion=historial">Volver al Módulo de Saldo</a>
	<a href="carrito?accion=ver">Volver al Carrito de Compras</a>
	<a href="articulos?accion=listado">Volver al listado de articulos</a>

</body>
</html>