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
     
	
	//instanciar el repositorio para no repetir tanto codigo en los metodos
	private ArticuloRepo articulosrepo;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ArticulosController() {
    	this.articulosrepo = ArticulosRepoSingleton.getInstance();
    	
    }

    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accion = request.getParameter("accion");
		accion = Optional.ofNullable(accion).orElse("index");
		
		switch(accion) {
		case "listado" -> getListado(request,response);
		case "editar" -> getEditar(request,response);
		case "crear" -> getCrear(request,response);
		default -> response.sendError(404);		
		}
	}

	
	
	
	
	private void getCrear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("views/articulos/crearArticulo.jsp").forward(request, response);
	}

	
	
	
	private void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String stringId = request.getParameter("id");
		int id = Integer.parseInt(stringId);
				
	    Articulo art = articulosrepo.findById(id);
		
		request.setAttribute("articulo", art);
		request.getRequestDispatcher("views/articulos/editarArticulo.jsp").forward(request, response);

	}

	
	
	private void getListado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Articulo> listaArticulos = articulosrepo.getAll();
		request.setAttribute("listado", listaArticulos);

		request.getRequestDispatcher("views/articulos/listadoArticulos.jsp").forward(request, response);
	}

	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accion = request.getParameter("accion");
		if(accion == null) {
			response.sendError(400, "No se brind� una acci�n.");
			return;
			
			
		}
		
		switch(accion) {
		case "insert" -> postInsertar(request, response);
		case "actualizar" -> postActualizar(request, response);
		case "eliminar" -> postEliminar(request, response);
		default -> response.sendError(404, "Accion no disponible: " + accion);	
		}
	}
	
	
	
	
	private void postEliminar(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		String sId = request.getParameter("id");
		int id = Integer.parseInt(sId);	
		
		articulosrepo.delete(id);
		response.sendRedirect("articulos?accion=listado");

		
	}



	private void postActualizar(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		
		String sId = request.getParameter("id");
		int id = Integer.parseInt(sId);		
				
		String scodigo = request.getParameter("codigo");
		int codigo = Integer.parseInt(scodigo);
		
		String nombre = request.getParameter("nombre");
		
		String descripcion = request.getParameter("descripcion");
		
		String sPrecio = request.getParameter("precio");
		Double precio = Double.parseDouble(sPrecio);
		
		String sStock = request.getParameter("stock");
		int stock = Integer.parseInt(sStock);
		
		
		Articulo art = articulosrepo.findById(id);

		art.setCodigo(codigo);
		art.setNombre(nombre);
		art.setDescripcion(descripcion);
		art.setPrecio(precio);
		art.setStock(stock);
		
		//no aplica para este proyecto, pero es para guardarel articulo en una base de datos
		articulosrepo.update(art);
		
		
		response.sendRedirect("articulos?accion=listado");
	}



	protected void postInsertar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		String scodigo = request.getParameter("codigo");
		int codigo = Integer.parseInt(scodigo);
		
		String nombre = request.getParameter("nombre");
		
		String descripcion = request.getParameter("descripcion");
		
		String sPrecio = request.getParameter("precio");
		Double precio = Double.parseDouble(sPrecio);
		
		String sStock = request.getParameter("stock");
		int stock = Integer.parseInt(sStock);
		
		
		Articulo art = new Articulo(codigo, nombre, descripcion, precio, stock);
		
		articulosrepo.insert(art);
		
		response.sendRedirect("articulos?accion=listado");
	
	}
}
