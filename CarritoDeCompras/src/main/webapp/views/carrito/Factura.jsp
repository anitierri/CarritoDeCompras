<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Factura de Compra</title>
</head>
<body>
	<h1>Factura de Compra</h1>
	<h2>Carrito ID: ${carrito.id}</h2>
	<h3>Estado: ${carrito.estado}</h3>

	<table border="1">
		<thead>
			<tr>
				<th>Artículo</th>
				<th>Precio Unitario</th>
				<th>Cantidad</th>
				<th>Subtotal</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="articuloCantidad" items="${carrito.articulos}">
				<tr>
					<td>${articuloCantidad.articulo.nombre}</td>
					<td>$${articuloCantidad.articulo.precio}</td>
					<td>${articuloCantidad.cantidad}</td>
					<td>$${articuloCantidad.subtotal}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<h3>Total: $${total}</h3>

	<form action="carrito" method="post">
		<input type="hidden" name="accion" value="pagar"> <input
			type="submit" value="Confirmar Pago">
	</form>

	<a href="carrito?accion=ver">Volver al Carrito</a>
</body>
</html>