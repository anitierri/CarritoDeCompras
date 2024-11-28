<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="es">
<head>
<meta charset="UTF-8">
<title>Carrito de Compras</title>
</head>
<body>
	<h1>Carrito de Compras</h1>

	<c:if test="${not empty carrito}">

<!--borrar h2 y h3 despuess-->
		<h2 type="hidden">Carrito ID: ${carrito.id}</h2>
		<h3 type="hidden">Estado: ${carrito.estado}</h3>
		<h4>Precio Total: ${carrito.precio}</h4>

		<table border="1">
			<thead>
				<tr>
					<th>Articulo</th>
					<th>Precio</th>
					<th>Stock</th>
					<th>Eliminar</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="articuloCantidad" items="${carrito.articulos}">
					<tr>
						<td>${articuloCantidad.articulo.nombre}</td>
						<td>$${articuloCantidad.articulo.precio}</td>
						<td>${articuloCantidad.cantidad}</td>
						<td>
							<form action="carrito" method="post">
								<input type="hidden" name="accion" value="eliminar"> <input
									type="hidden" name="idCarrito" value="${carrito.id}"> <input
									type="hidden" name="idArticulo"
									value="${articuloCantidad.articulo.id}"> <input
									type="submit" value="Eliminar">
						</form>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<br>
		<form action="carrito" method="post">
			<input type="hidden" name="accion" value="finalizar"> <input
				type="hidden" name="idCarrito" value="${carrito.id}"> <input
				type="submit" value="Finalizar Compra">
		</form>
	</c:if>
	<c:if test="${empty carrito}">
	<h5>El carrito está vacío</h5>
	</c:if>
	<a href="articulos?accion=listado">Volver al listado de articulos</a>
</body>
</html>
