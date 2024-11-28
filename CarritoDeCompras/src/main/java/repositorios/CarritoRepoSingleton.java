package repositorios;

import models.Carrito;
import models.Articulo;
import java.util.ArrayList;
import java.util.List;

public class CarritoRepoSingleton {
	private static CarritoRepoSingleton singleton;
	private List<Carrito> listaCarritos;
	private static int contadorId = 1;

	private CarritoRepoSingleton() {
		this.listaCarritos = new ArrayList<>();
	}

	public static CarritoRepoSingleton getInstance() {
		if (singleton == null) {
			singleton = new CarritoRepoSingleton();
		}
		return singleton;
	}

	public Carrito crearCarrito(int idUsuario) {
		Carrito carrito = new Carrito(idUsuario, 0.0, "pendiente");
		carrito.setId(contadorId++);
		listaCarritos.add(carrito);
		return carrito;
	}

	public Carrito findById(int id) {
		return this.listaCarritos.stream().filter(carrito -> carrito.getId() == id).findAny().orElse(null);
	}

	public void agregarArticulo(int idCarrito, Articulo articulo) {
		Carrito carrito = findById(idCarrito);
		if (carrito != null) {
			carrito.agregarArticulo(articulo);
		}
	}

	public void eliminarArticulo(int idCarrito, Articulo articulo) {
		Carrito carrito = findById(idCarrito);
		if (carrito != null) {
			carrito.eliminarArticulo(articulo);
		}
	}

	public List<Carrito> getAll() {
		return new ArrayList<>(this.listaCarritos);
	}

	public void finalizarCompra(int idCarrito) {
		Carrito carrito = findById(idCarrito);
		if (carrito != null) {
			carrito.setEstado("finalizado");
		}
	}

	public void actualizarCarrito(Carrito carrito) {
		Carrito carritoExistente = findById(carrito.getId());
		if (carritoExistente != null) {
			carritoExistente.setEstado(carrito.getEstado());
			carritoExistente.setPrecio(carrito.calcularTotal());
		}
	}
}