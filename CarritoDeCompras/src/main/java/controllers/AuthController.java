package controllers;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Usuario;
import repositorios.UsuarioRepoSingleton;
import repositorios.interfaces.UsuarioRepo;

/**
 * Servlet implementation class AuthController
 */
@WebServlet("/auth")
public class AuthController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UsuarioRepo usuariosrepo;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AuthController() {
		this.usuariosrepo = UsuarioRepoSingleton.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String accion = request.getParameter("accion");
		accion = Optional.ofNullable(accion).orElse("index");

		switch (accion) {
		case "login" -> getLogin(request, response);
		case "crearCuenta" -> getCrear(request, response);
		case "logout" -> getLogout(request, response);
		default -> response.sendError(404);
		}
	}

	private void getLogout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().invalidate();

		response.sendRedirect("auth?accion=login");
	}

	private void getCrear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("views/auth/crearCuenta.jsp").forward(request, response);
	}

	private void getLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("views/auth/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String accion = request.getParameter("accion");

		if (accion == null) {
			response.sendError(400, "No se brindó una acción.");
			return;
		}

		switch (accion) {
		case "login" -> postLogin(request, response);
		case "crearCuenta" -> postCrearCuenta(request, response);
		default -> response.sendError(404, "Acción no disponible: " + accion);
		}

	}

	private void postCrearCuenta(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String nombre = request.getParameter("nombre").toLowerCase();
		String contrasena = request.getParameter("contrasena");
		int dni = Integer.parseInt(request.getParameter("dni"));

		try {
			Usuario nuevoUsuario = new Usuario(nombre, contrasena, dni, "CLIENTE");
			usuariosrepo.insert(nuevoUsuario);

			response.sendRedirect("auth?accion=login");
		} catch (NumberFormatException e) {
			request.setAttribute("error", "DNI inválido");
			request.getRequestDispatcher("views/auth/crearCuenta.jsp").forward(request, response);
		}
	}

	private void postLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int dni = Integer.parseInt(request.getParameter("dni"));
		String contrasena = request.getParameter("contrasena");

		Usuario usuario = usuariosrepo.findByDni(dni);

		if (usuario != null && usuario.getContrasena().equals(contrasena)) {
			request.getSession().removeAttribute("idCarrito");
			request.getSession().setAttribute("usuario", usuario);
			response.sendRedirect("articulos?accion=listado");
		} else {
			request.setAttribute("error", "Usuario o contraseña incorrectos");
			request.getRequestDispatcher("views/auth/login.jsp").forward(request, response);
		}
	}
}