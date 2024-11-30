<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Iniciar Sesión</title>
<link rel="stylesheet" href="css/styles.css">

</head>
<body>
	<div class="login-container">
		<h2>Iniciar Sesión</h2>

		<form action="auth" method="post">
			<input type="hidden" name="accion" value="login">

			<div class="form-group">
				<label for="dni">DNI:</label> <input type="number" id="dni"
					name="dni" required>
			</div>

			<div class="form-group">
				<label for="contrasena">Contraseña:</label> <input type="password"
					id="contrasena" name="contrasena" required>
			</div>

			<div class="form-group">
				<button type="submit">Iniciar Sesión</button>
			</div>

			<c:if test="${not empty error}">
				<div class="error-message">${error}</div>
			</c:if>
		</form>

		<p>
			No tenes una cuenta? <a href="auth?accion=crearCuenta">Crear una
				cuenta</a>
		</p>
	</div>
</body>
</html>