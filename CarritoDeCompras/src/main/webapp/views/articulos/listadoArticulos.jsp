<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Listado de Artículos</title>
</head>
<body>
	<c:if test="${sessionScope.usuario != null && sessionScope.usuario.categoria != 'CLIENTE'}">
		<a href="articulos?accion=crear">Agregar Articulo</a>
	</c:if>
		<a href="carrito?accion=ver">Ir al Carrito</a>
		<h1>Listado de Artículos</h1>
		<table border="1">
			<thead>
				<tr>
					<th>ID</th>
					<th>Código</th>
					<th>Nombre</th>
					<th>Descripción</th>
					<th>Precio</th>
					<th>Stock</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="articulo" items="${listado}">
					<tr>
						<td><c:out value="${articulo.id}" /></td>
						<td><c:out value="${articulo.codigo}" /></td>
						<td><c:out value="${articulo.nombre}" /></td>
						<td><c:out value="${articulo.descripcion}" /></td>
						<td><c:out value="${articulo.precio}" /></td>
						<td><c:out value="${articulo.stock}" /></td>
						<td><c:if
								test="${sessionScope.usuario != null && sessionScope.usuario.categoria != 'CLIENTE'}">
								<a href="articulos?accion=editar&id=${articulo.id}">Editar</a>
								<form action="articulos" method="post" style="display: inline;">
									<input type="hidden" name="id" value="${articulo.id}">
									<input type="hidden" name="accion" value="eliminar"> <input
										type="submit" value="Eliminar">
								</form>
							</c:if>
							<form action="carrito" method="post" style="display: inline;">
								<input type="hidden" name="idCarrito" value="${carrito.id}">
								<input type="hidden" name="idArticulo" value="${articulo.id}">
								<input type="hidden" name="accion" value="agregar"> <input
									type="submit" value="Agregar al Carrito">
							</form></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</body>
</html>
