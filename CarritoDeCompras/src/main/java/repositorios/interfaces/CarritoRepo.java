package repositorios.interfaces;

import models.Carrito;
import models.Articulo;

import java.util.List;

public interface CarritoRepo {

	public List<Carrito> getAll();

	public Carrito findById(int id);

	public void insert(Carrito carrito);

	public void delete(int id);

	public void agregarArticulo(int carritoId, Articulo articulo);

	public void eliminarArticulo(int carritoId, Articulo articulo);

}