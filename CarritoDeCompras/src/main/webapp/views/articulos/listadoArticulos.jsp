<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>




<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Listado de articulos</title>
</head>
<body>
	<a href="articulos?accion=crear">Agregar Articulo</a>
	<h1>Listado de articulos</h1>
	<table border="1">
		<thead>
			<tr>
				<th>ID</th>
				<th>Código</th>
				<th>Nombre</th>
				<th>Descripcion</th>
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
					<td><a href="articulos?accion=editar&id=${articulo.id }">editar</a>
						<form action="articulos" method="post" style="display:inline;">
							<input type="hidden" name="id" value="${articulo.id }"> 
							<input type="hidden" name="accion" value="eliminar"> 
							<input type="submit" value="Eliminar">
						</form></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>