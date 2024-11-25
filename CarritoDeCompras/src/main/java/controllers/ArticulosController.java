package controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Articulo;
import repositorios.ArticulosRepoSingleton;
import repositorios.interfaces.ArticuloRepo;

/**
 * Servlet implementation class ArticulosController
 */
@WebServlet("/articulos")
public class ArticulosController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ArticulosController() {
    }

    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accion = request.getParameter("accion");
		accion = Optional.ofNullable(accion).orElse("index");
		
		switch(accion) {
		case "index" -> getIndex(request,response);
		case "listado" -> getListado(request,response);
		case "editar" -> getEditar(request,response);
		case "crear" -> getCrear(request,response);
		default -> response.sendError(404);		
		}
	}

	private void getCrear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("crearArticulo.jsp").forward(request, response);
	}

	private void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String stringId = request.getParameter("id");
		int id = Integer.parseInt(stringId);
		
		ArticuloRepo repo = ArticulosRepoSingleton.getInstance();
		
		Articulo art = repo.findById(id);
		
		request.setAttribute("articulo", art);
		request.getRequestDispatcher("editarArticulo.jsp").forward(request, response);
	}

	private void getListado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArticuloRepo repo = ArticulosRepoSingleton.getInstance();
		
		List<Articulo> listaArticulos = repo.getAll();
		
		request.setAttribute("listado", listaArticulos);
		
		request.getRequestDispatcher("listadoArticulos.jsp").forward(request, response);


	}

	private void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("inicioSesion.jsp").forward(request, response);

	}

	
	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArticuloRepo repo = ArticulosRepoSingleton.getInstance();

		
		String scodigo = request.getParameter("codigo");
		int codigo = Integer.parseInt(scodigo);

		String nombre = request.getParameter("nombre");

		String descripcion = request.getParameter("descripcion");

		String sPrecio = request.getParameter("precio");
		int precio = Integer.parseInt(sPrecio);
		
		String sStock = request.getParameter("stock");
		int stock = Integer.parseInt(sStock);
		
		
		
		Articulo art = new Articulo(codigo, nombre, descripcion, precio, stock);
		
		repo.insert(art);
		
		response.sendRedirect("articulos");
	}

}
