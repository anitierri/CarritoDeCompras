package controllers;

import models.Usuario;
import repositorios.SaldoRepoSingleton;
import repositorios.UsuarioRepoSingleton;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/saldo")
public class SaldoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SaldoRepoSingleton saldoRepo;
	private UsuarioRepoSingleton usuarioRepo;

	public SaldoController() {
		this.saldoRepo = SaldoRepoSingleton.getInstance();
		this.usuarioRepo = UsuarioRepoSingleton.getInstance();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String accion = request.getParameter("accion");
		accion = (accion != null) ? accion : "index";

		Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuario");

		if (usuarioActual == null) {
			response.sendRedirect("auth?accion=login");
			return;
		}
		if ("EMPLEADO".equals(usuarioActual.getCategoria())) {
			response.sendError(403, "No tenes permisos para realizar esta acción");
			return;
		}

		switch (accion) {
		case "recarga":
			getRecarga(request, response);
			break;
		case "transferencia":
			getTransferencia(request, response);
			break;
		case "historial":
			getHistorial(request, response);
			break;
		default:
			response.sendError(404);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String accion = request.getParameter("accion");
		if (accion == null) {
			response.sendError(400, "No se brindó una acción.");
			return;
		}

		Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuario");
		if (usuarioActual == null) {
			response.sendError(401, "Usuario no autenticado");
			return;
		}

		switch (accion) {
		case "recarga":
			postRecarga(request, response);
			break;
		case "transferencia":
			postTransferencia(request, response);
			break;
		default:
			response.sendError(404, "Acción no disponible: " + accion);
		}
	}

	private void getRecarga(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuario");
		double saldoActual = saldoRepo.obtenerSaldoActual(usuarioActual.getId());
		request.setAttribute("saldoActual", saldoActual);
		request.getRequestDispatcher("views/saldo/Recarga.jsp").forward(request, response);
	}

	private void postRecarga(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuario");

		try {
			double monto = Double.parseDouble(request.getParameter("monto"));

			if (monto <= 0) {
				request.setAttribute("error", "El monto debe ser mayor a cero");
				getRecarga(request, response);
				return;
			}

			saldoRepo.registrarRecarga(usuarioActual.getId(), monto);
			response.sendRedirect("saldo?accion=historial");
		} catch (NumberFormatException e) {
			request.setAttribute("error", "Monto inválido");
			getRecarga(request, response);
		}
	}

	private void getHistorial(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuario");
		double saldoActual = saldoRepo.obtenerSaldoActual(usuarioActual.getId());
		request.setAttribute("saldoActual", saldoActual);
		request.setAttribute("historial", saldoRepo.obtenerHistorialSaldo(usuarioActual.getId()));
		request.setAttribute("usuarioRepo", usuarioRepo);
		request.getRequestDispatcher("views/saldo/Historial.jsp").forward(request, response);
	}

	private void getTransferencia(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuario");
		double saldoActual = saldoRepo.obtenerSaldoActual(usuarioActual.getId());
		request.setAttribute("saldoActual", saldoActual);
		request.setAttribute("usuarios", usuarioRepo.getAll());
		request.getRequestDispatcher("views/saldo/Transferencia.jsp").forward(request, response);
	}

	private void postTransferencia(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuario");

		try {
			int dniDestino = Integer.parseInt(request.getParameter("dniDestino"));
			double monto = Double.parseDouble(request.getParameter("monto"));

			Usuario usuarioDestino = usuarioRepo.findByDni(dniDestino);

			if (usuarioDestino == null) {
				request.setAttribute("error", "Usuario de destino no encontrado");
				getTransferencia(request, response);
				return;
			}

			double saldoActual = saldoRepo.obtenerSaldoActual(usuarioActual.getId());

			// ya agregamos una validacion en la vista ademas de esta
			if (monto <= 0 || monto > saldoActual) {
				request.setAttribute("error", "Monto inválido o saldo insuficiente");
				getTransferencia(request, response);
				return;
			}

			saldoRepo.registrarTransferencia(usuarioActual.getId(), usuarioDestino.getId(), monto);
			response.sendRedirect("saldo?accion=historial");
		} catch (NumberFormatException e) {
			request.setAttribute("error", "Datos inválidos");
			getTransferencia(request, response);
		}
	}

}