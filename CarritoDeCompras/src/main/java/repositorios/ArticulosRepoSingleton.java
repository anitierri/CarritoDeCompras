package repositorios;

import java.util.ArrayList;
import java.util.List;

import models.Articulo;
import repositorios.interfaces.ArticuloRepo;

public class ArticulosRepoSingleton implements ArticuloRepo {

	private static ArticulosRepoSingleton singleton;

	public static ArticulosRepoSingleton getInstance() {
		if (singleton == null) {
			singleton = new ArticulosRepoSingleton();
		}
		return singleton;
	}

	private List<Articulo> listaArticulos;

	private ArticulosRepoSingleton() {
		this.listaArticulos = new ArrayList<Articulo>();

		Articulo art1 = new Articulo(1234567, "Remera", "lacoste - azul - talle m", 4999.99, 21);
		Articulo art2 = new Articulo(5678904, "pantalon", "marca pirulo - blanco - talle l", 19000.99, 5);
		Articulo art3 = new Articulo(7654321, "vestido", "prada - floreado - talle s", 30999.99, 4);

		this.insert(art1);
		this.insert(art2);
		this.insert(art3);
	}

	// trae los objetos de AtriculoRepo
	@Override
	public List<Articulo> getAll() {
		return new ArrayList<Articulo>(this.listaArticulos);
	}

	// para buscar por un solo prodicto (buscar por id)
	@Override
	public Articulo findById(int id) {

		return this.listaArticulos.stream().filter((art) -> art.getId() == id).findAny().orElse(null);
	}

	// alta producto, y se crea la id
	@Override
	public void insert(Articulo articulo) {
		int ultId = this.listaArticulos.stream().map(Articulo::getId).max(Integer::compare).orElse(0);

		articulo.setId(ultId + 1);

		this.listaArticulos.add(articulo);
	}

	// modificar
	@Override
	public void update(Articulo articulo) {
		// esto es em caso de querer trabajar con un aarchivo o una base de datos

	}

	// borrar articulo
	@Override
	public void delete(int id) {
		this.listaArticulos.removeIf((art) -> art.getId() == id);
	}

	public void actualizarArticulo(Articulo articulo) {
		for (int i = 0; i < listaArticulos.size(); i++) {
			if (listaArticulos.get(i).getId() == articulo.getId()) {
				listaArticulos.set(i, articulo);
				break;
			}
		}
	}

}
