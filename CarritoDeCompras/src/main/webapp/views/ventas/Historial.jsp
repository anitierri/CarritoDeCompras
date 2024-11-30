<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<title>${titulo}</title>
<link rel="stylesheet" href="css/styles.css">

<style>
table {
	border-collapse: collapse;
	width: 100%;
}

th, td {
	border: 1px solid #ddd;
	padding: 8px;
	text-align: left;
}

.articulos-subtable {
	width: 100%;
	margin-top: 5px;
}

.articulos-subTable th, .articulos-subTable td {
	border: 1px solid #eee;
	padding: 4px;
}
</style>
</head>
<body>
	<h1>${titulo}</h1>
	<table>
		<thead>
			<tr>
				<th>Fecha</th>
				<th>Total</th>
				<c:if test="${usuario.categoria == 'EMPLEADO'}">
					<th>Usuario</th>
				</c:if>
				<th>Artículos Comprados</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="venta" items="${ventas}">
				<tr>
					<td>${venta.fecha.dayOfMonth < 10 ? '0' : ''}${venta.fecha.dayOfMonth}-
						${venta.fecha.monthValue < 10 ? '0' : ''}${venta.fecha.monthValue}-
						${venta.fecha.year} ${venta.fecha.hour < 10 ? '0' : ''}${venta.fecha.hour}:
						${venta.fecha.minute < 10 ? '0' : ''}${venta.fecha.minute}</td>
					<td>$<fmt:formatNumber type="number" groupingUsed="true"
							value="${venta.total}" /></td>
					<c:if test="${usuario.categoria == 'EMPLEADO'}">
						<td>${usuarioRepo.findById(venta.idUsuario).nombre}</td>
					</c:if>
					<td>
						<table class="articulos-subTable">
							<thead>
								<tr>
									<th>Artículo</th>
									<th>Cantidad</th>
									<th>Precio</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="articuloCantidad" items="${venta.articulos}">
									<tr>
										<td>${articuloCantidad.articulo.nombre}</td>
										<td>${articuloCantidad.cantidad}</td>
										<td>$<fmt:formatNumber type="number" groupingUsed="true"
												value="${articuloCantidad.articulo.precio}" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br>

	<a href="articulos?accion=listado">Volver al listado de articulos</a>
	<a href="saldo?accion=historial">Ir al módulo de saldo </a>

</body>
</html>