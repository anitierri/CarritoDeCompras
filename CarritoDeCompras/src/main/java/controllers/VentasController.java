package controllers;

import models.Usuario;
import models.Venta;
import repositorios.VentasRepoSingleton;
import repositorios.UsuarioRepoSingleton;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ventas")
public class VentasController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private VentasRepoSingleton ventasRepo;
	private UsuarioRepoSingleton usuarioRepo;

	public VentasController() {
		this.ventasRepo = VentasRepoSingleton.getInstance();
		this.usuarioRepo = UsuarioRepoSingleton.getInstance();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuario");
		if (usuarioActual == null) {
			response.sendRedirect("auth?accion=login");
			return;
		}
		List<Venta> ventas;
		if ("EMPLEADO".equals(usuarioActual.getCategoria())) {
			ventas = ventasRepo.getAll();
			request.setAttribute("titulo", "Historial de Ventas");
		} else {
			ventas = ventasRepo.obtenerVentasPorUsuario(usuarioActual.getId());
			request.setAttribute("titulo", "Mis Compras");
		}
		request.setAttribute("ventas", ventas);
		request.setAttribute("usuarioRepo", usuarioRepo);
		request.getRequestDispatcher("views/ventas/Historial.jsp").forward(request, response);
	}
}