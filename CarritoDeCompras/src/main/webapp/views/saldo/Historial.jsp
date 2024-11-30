<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<title>Historial de Saldo</title>
<link rel="stylesheet" href="css/styles.css">

</head>
<body>
	<h1>Historial de Saldo</h1>

	<p>
		Saldo actual : $
		<fmt:formatNumber type="number" groupingUsed="true"
			value="${saldoActual}" />
	</p>
	<br>

	<a href="saldo?accion=recarga">Recargar Saldo</a>
	<a href="saldo?accion=transferencia">Transferir Saldo</a>
	<br>
	<br>
	


	<table border="1">
		<thead>
			<tr>
				<th>Fecha</th>
				<th>Tipo</th>
				<th>Monto</th>
				<th>Detalles</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="movimiento" items="${historial}">
				<tr>
					<td>${movimiento.fecha.dayOfMonth < 10 ? '0' : ''}${movimiento.fecha.dayOfMonth}-
						${movimiento.fecha.monthValue < 10 ? '0' : ''}${movimiento.fecha.monthValue}-
						${movimiento.fecha.year} ${venta.fecha.hour < 10 ? '0' : ''}${movimiento.fecha.hour}:
						${movimiento.fecha.minute < 10 ? '0' : ''}${movimiento.fecha.minute}</td>

					<td>${movimiento.tipo}</td>
					<td style="color: ${movimiento.monto >= 0 ? 'green' : 'red'}">
						$<fmt:formatNumber type="number" groupingUsed="true"
							value="${movimiento.monto}" />
					<td><c:choose>
							<c:when test="${movimiento.tipo == 'transferencia'}">
								<c:if test="${movimiento.monto < 0}">
									<c:set var="usuarioDestino"
										value="${usuarioRepo.findById(movimiento.usuarioDestino)}" />
                                    Transferencia enviada a: ${usuarioDestino.nombre} (DNI: ${usuarioDestino.dni})
                                </c:if>
								<c:if test="${movimiento.monto > 0}">
									<c:set var="usuarioOrigen"
										value="${usuarioRepo.findById(movimiento.usuarioOrigen)}" />
                                    Transferencia recibida de: ${usuarioOrigen.nombre} (DNI: ${usuarioOrigen.dni})
                                </c:if>
							</c:when>
							<c:when test="${movimiento.tipo == 'recarga'}">
                                Recarga de saldo
                            </c:when>
							<c:when test="${movimiento.tipo == 'compra'}">
                                Compra realizada
                            </c:when>
						</c:choose></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<br>
	<a href="articulos?accion=listado">Volver al listado de articulos</a>
	<a href="carrito?accion=ver">Volver al Carrito</a>
</body>
</html>