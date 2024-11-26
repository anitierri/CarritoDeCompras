<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>editar</title>
</head>
<body>
	<h1>editar un articulo</h1>
	<h3>completá el siguiente formulario:</h3>

	<form action="articulos" method="post">
	<input type="hidden" value="actualizar" name="accion">
		<p>
			ID: <input value="${articulo.id }" name="id" />
		</p>
		<p>
			codigo: <input value="${articulo.codigo}" name="codigo" />
		</p>
		<p>
			nombre: <input value="${articulo.nombre}" name="nombre" />
		</p>
		<p>
			descripcion: <input value="${articulo.descripcion}" name="descripcion" />
		</p>
		<p>
			precio: <input value="${articulo.precio}" name="precio" />
		</p>
		<p>
			stock: <input value="${articulo.stock}" name="stock" />
		</p>
		<input type="submit" value="Guardar"/>

	</form>

</body>
</html>