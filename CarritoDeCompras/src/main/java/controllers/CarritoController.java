package controllers;

import models.Carrito;
import models.Usuario;
import models.Articulo;
import repositorios.ArticulosRepoSingleton;
import repositorios.CarritoRepoSingleton;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/carrito")
public class CarritoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CarritoRepoSingleton carritosRepo;

	public CarritoController() {
		this.carritosRepo = CarritoRepoSingleton.getInstance();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String accion = request.getParameter("accion");
		accion = Optional.ofNullable(accion).orElse("index");

		switch (accion) {
		case "listado":
			getListado(request, response);
			break;
		case "ver":
			getVer(request, response);
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

		switch (accion) {
		case "agregar":
			postAgregarArticulo(request, response);
			break;
		case "eliminar":
			postEliminarArticulo(request, response);
			break;
		case "finalizar":
			postFinalizarCompra(request, response);
			break;
		case "pagar":
			postPagar(request, response);
			break;
		default:
			response.sendError(404, "Acción no disponible: " + accion);
		}
	}

	private void getListado(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Carrito> listaCarritos = carritosRepo.getAll();
		request.setAttribute("listado", listaCarritos);
		request.getRequestDispatcher("views/carrito/Carrito.jsp").forward(request, response);
	}

	private void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

		if (usuario == null) {
			response.sendError(401, "Usuario no autenticado");
			return;
		}

		Integer idCarrito = (Integer) request.getSession().getAttribute("idCarrito");

		if (idCarrito != null) {
			Carrito carrito = carritosRepo.findById(idCarrito);

			if (carrito != null && carrito.getIdUsuario() == usuario.getId()) {
				request.setAttribute("carrito", carrito);
				request.getRequestDispatcher("views/carrito/Carrito.jsp").forward(request, response);
			} else {
				response.sendError(404, "Carrito no encontrado o no autorizado");
			}
		} else {
			response.sendError(404, "Carrito no disponible");
		}
	}

	private void postAgregarArticulo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

		if (usuario == null) {
			response.sendError(401, "Usuario no autenticado");
			return;
		}

		Integer idCarrito = (Integer) request.getSession().getAttribute("idCarrito");

		if (idCarrito == null) {
			Carrito carrito = carritosRepo.crearCarrito(usuario.getId());
			idCarrito = carrito.getId();
			request.getSession().setAttribute("idCarrito", idCarrito);
		}

		String sIdArticulo = request.getParameter("idArticulo");
		int idArticulo = -1;

		if (sIdArticulo != null && !sIdArticulo.isEmpty()) {
			try {
				idArticulo = Integer.parseInt(sIdArticulo);
			} catch (NumberFormatException e) {
				response.sendError(400, "ID de artículo no válido");
				return;
			}
		}

		Articulo articulo = ArticulosRepoSingleton.getInstance().findById(idArticulo);

		if (articulo != null) {
			carritosRepo.agregarArticulo(idCarrito, articulo);
			response.sendRedirect("carrito?accion=ver&id=" + idCarrito);
		} else {
			response.sendError(404, "Artículo no encontrado");
		}
	}

	private void postEliminarArticulo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer idCarrito = (Integer) request.getSession().getAttribute("idCarrito");

		if (idCarrito == null) {
			response.sendError(404, "Carrito no encontrado");
			return;
		}

		String sIdArticulo = request.getParameter("idArticulo");
		int idArticulo = Integer.parseInt(sIdArticulo);

		Articulo articulo = ArticulosRepoSingleton.getInstance().findById(idArticulo);

		if (articulo != null) {
			carritosRepo.eliminarArticulo(idCarrito, articulo);
			response.sendRedirect("carrito?accion=ver&id=" + idCarrito);
		} else {
			response.sendError(404, "Artículo no encontrado");
		}
	}

	private void postFinalizarCompra(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer idCarrito = (Integer) request.getSession().getAttribute("idCarrito");

		if (idCarrito == null) {
			response.sendError(404, "Carrito no encontrado");
			return;
		}

		Carrito carrito = carritosRepo.findById(idCarrito);

		if (carrito != null) {
			carrito.setEstado("pendiente");
			carritosRepo.actualizarCarrito(carrito);

			double total = carrito.calcularTotal();
			request.setAttribute("carrito", carrito);
			request.setAttribute("total", total);

			request.getRequestDispatcher("views/carrito/Factura.jsp").forward(request, response);
		} else {
			response.sendError(404, "Carrito no encontrado");
		}
	}

	private void postPagar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer idCarrito = (Integer) request.getSession().getAttribute("idCarrito");

		if (idCarrito == null) {
			response.sendError(404, "Carrito no encontrado");
			return;
		}

		Carrito carrito = carritosRepo.findById(idCarrito);

		if (carrito != null) {
			carrito.setEstado("finalizado");
			carritosRepo.actualizarCarrito(carrito);

			response.sendRedirect("articulos?accion=listado");
		} else {
			response.sendError(404, "Carrito no encontrado");
		}
	}
}