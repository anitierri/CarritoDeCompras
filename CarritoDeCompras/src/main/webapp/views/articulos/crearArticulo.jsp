<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>crear articulo</title>
</head>
<body>
	<h1>cargar un articulo</h1>
	<h3>completá el siguiente formulario:</h3>

	<form action="articulos" method="post">
	<input type ="hidden" value="insert" name="accion"></input>
	
		<p>
			codigo: <input value="" name="codigo" />
		</p>
		<p>
			nombre: <input value="" name="nombre" />
		</p>
		<p>
			descripcion: <input value="" name="descripcion" />
		</p>
		<p>
			precio: <input value="" name="precio" />
		</p>
		<p>
			stock: <input value="" name="stock" />
		</p>
		<input type="submit" value="Guardar"/>

	</form>
</body>
</html>