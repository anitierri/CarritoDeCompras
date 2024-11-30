package repositorios.interfaces;

import java.util.List;

import models.Articulo;

public interface ArticuloRepo {

	// trae todos
	public List<Articulo> getAll();

	// trae uno solo
	public Articulo findById(int id);

	// alta articulo
	public void insert(Articulo articulo);

	// modificar articulo
	public void update(Articulo articulo);

	// borrar artuculo
	public void delete(int id);

	// para actualizar el stock cuando se confirma una copmra
	public static void actualizarArticulo(Articulo articulo) {
		// TODO Auto-generated method stub

	}

}
