package controllers;

import models.Carrito;
import models.Usuario;
import models.Venta;
import models.Articulo;
import models.ArticuloCantidad;
import repositorios.ArticulosRepoSingleton;
import repositorios.CarritoRepoSingleton;
import repositorios.SaldoRepoSingleton;
import repositorios.VentasRepoSingleton;
import repositorios.interfaces.ArticuloRepo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

		case "ver":
			getVer(request, response);
			break;
		default:
			response.sendError(404);
		}
	}

	private void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String accion = request.getParameter("accion");
		if (accion == null) {
			response.sendError(400, "No se brindó una acción.");
			return;
		}
		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("usuario") == null) {
			response.sendRedirect("auth?accion=login");
			return;
		}

		Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuario");

		if ("EMPLEADO".equals(usuarioActual.getCategoria())) {
			response.sendError(403, "No tenes permisos para realizar esta acción");
			return;
		}

		switch (accion) {
		case "agregar":
			postAgregarArticulo(request, response);
			break;
		case "eliminar":
			postEliminarArticulo(request, response);
			break;
		case "incrementar":
			postIncrementarArticulo(request, response);
			break;
		case "decrementar":
			postDecrementarArticulo(request, response);
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

	private void postIncrementarArticulo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer idCarrito = (Integer) request.getSession().getAttribute("idCarrito");

		String sIdArticulo = request.getParameter("idArticulo");
		int idArticulo = Integer.parseInt(sIdArticulo);

		Articulo articulo = ArticulosRepoSingleton.getInstance().findById(idArticulo);

		if (articulo != null) {
			Carrito carrito = carritosRepo.findById(idCarrito);
			int cantidadEnCarrito = carrito.getArticulos().stream()
					.filter(ac -> ac.getArticulo().getId() == articulo.getId()).mapToInt(ArticuloCantidad::getCantidad)
					.sum();

			if (cantidadEnCarrito + 1 <= articulo.getStock()) {
				carritosRepo.incrementarArticulo(idCarrito, articulo);
				response.sendRedirect("carrito?accion=ver");
			} else {
				request.getSession().setAttribute("errorStock", "No hay suficiente stock disponible");
				response.sendRedirect("carrito?accion=ver&error=stock");
			}
		} else {
			response.sendError(404, "Artículo no encontrado");
		}
	}

	private void postDecrementarArticulo(HttpServletRequest request, HttpServletResponse response)
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
			carritosRepo.decrementarArticulo(idCarrito, articulo);
			response.sendRedirect("carrito?accion=ver");
		} else {
			response.sendError(404, "Artículo no encontrado");
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
			Carrito carrito = carritosRepo.findById(idCarrito);
			int cantidadEnCarrito = carrito.getArticulos().stream()
					.filter(ac -> ac.getArticulo().getId() == articulo.getId()).mapToInt(ArticuloCantidad::getCantidad)
					.sum();

			if (cantidadEnCarrito + 1 <= articulo.getStock()) {
				carritosRepo.agregarArticulo(idCarrito, articulo);
				response.sendRedirect("carrito?accion=ver&id=" + idCarrito);
			} else {
				request.getSession().setAttribute("errorStock", "No hay suficiente stock disponible");
				response.sendRedirect("catalogo?error=stock");
			}
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
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
		Integer idCarrito = (Integer) request.getSession().getAttribute("idCarrito");

		if (usuario == null) {
			response.sendError(401, "Usuario no autenticado");
			return;
		}

		if (idCarrito == null) {
			response.sendError(404, "Carrito no encontrado");
			return;
		}

		Carrito carrito = carritosRepo.findById(idCarrito);
		double total = carrito.calcularTotal();
		double saldoActual = SaldoRepoSingleton.getInstance().obtenerSaldoActual(usuario.getId());

		if (total > saldoActual) {
			response.sendRedirect("saldo?accion=recarga");
			return;
		}

		for (ArticuloCantidad articuloCantidad : carrito.getArticulos()) {
			Articulo articulo = articuloCantidad.getArticulo();
			int cantidad = articuloCantidad.getCantidad();

			articulo.setStock(articulo.getStock() - cantidad);
			ArticuloRepo.actualizarArticulo(articulo);
		}

		Venta venta = new Venta();
		venta.setIdUsuario(usuario.getId());
		venta.setTotal(total);
		venta.setArticulos(carrito.getArticulos());
		venta.setEstado("finalizado");

		VentasRepoSingleton.getInstance().registrarVenta(venta);

		carrito.setEstado("finalizado");
		carritosRepo.actualizarCarrito(carrito);

		request.getSession().removeAttribute("idCarrito");

		response.sendRedirect("ventas?doGet");
	}
}