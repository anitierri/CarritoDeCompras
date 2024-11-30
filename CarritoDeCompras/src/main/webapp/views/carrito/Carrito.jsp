<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Carrito de Compras</title>
<link rel="stylesheet" href="css/styles.css">
<style>
.cantidad-container {
	display: flex;
	align-items: center;
	justify-content: center;
}

.cantidad-btn {
	width: 30px;
	height: 30px;
	margin: 0 10px;
}
.no-articles {
    background-color: #f8f9fa;
    border: 1px solid #e9ecef;
    color: #6c757d;
    padding: 20px;
    text-align: center;
    border-radius: 4px;
    margin: 20px 0;
}
</style>
</head>
<body>
	<h1>Carrito de Compras</h1>
	<c:if test="${not empty carrito}">
		<h4>Precio Total: $${carrito.precio}</h4>
		<table border="1">
			<thead>
				<tr>
					<th>Articulo</th>
					<th>Precio</th>
					<th>Cantidad</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="articuloCantidad" items="${carrito.articulos}">
					<tr>
						<td>${articuloCantidad.articulo.nombre}</td>
						<td>$${articuloCantidad.articulo.precio}</td>
						<td>
							<div class="cantidad-container">
								<form action="carrito" method="post" style="display: inline;">
									<input type="hidden" name="accion" value="decrementar">
									<input type="hidden" name="idArticulo"
										value="${articuloCantidad.articulo.id}"> <input
										type="submit" value="-" class="cantidad-btn"
										${articuloCantidad.cantidad <= 1 ? 'disabled' : ''}>
								</form>

								${articuloCantidad.cantidad}

								<form action="carrito" method="post" style="display: inline;">
									<input type="hidden" name="accion" value="incrementar">
									<input type="hidden" name="idArticulo"
										value="${articuloCantidad.articulo.id}"> <input
										type="submit" value="+" class="cantidad-btn">
								</form>
							</div>
						</td>
						<td>
							<form action="carrito" method="post">
								<input type="hidden" name="accion" value="eliminar"> <input
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
				type="submit" value="Finalizar Compra">
		</form>
	</c:if>
	<c:if test="${empty carrito}">
		<h5>El carrito está vacío</h5>
	</c:if>
	<br>

	<a href="articulos?accion=listado">Volver al listado de articulos</a>
	<a href="saldo?accion=historial">Ir al módulo de saldo</a>
</body>
</html>