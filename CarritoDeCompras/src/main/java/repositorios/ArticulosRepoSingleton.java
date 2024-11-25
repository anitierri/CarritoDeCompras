package repositorios;

import java.util.ArrayList;
import java.util.List;

import models.Articulo;
import repositorios.interfaces.ArticuloRepo;

public class ArticulosRepoSingleton implements ArticuloRepo {

	private static ArticulosRepoSingleton singleton;
	
	public static ArticulosRepoSingleton getInstance() {
		if(singleton == null) {
			singleton = new ArticulosRepoSingleton();
		}
		return singleton;
	}
	
	private List<Articulo> listaArticulos;
	
	private ArticulosRepoSingleton() {
		//para probar
		this.listaArticulos = new ArrayList<Articulo>();
		Articulo articulo1 = new Articulo(1234567, "Horno Electrico", "atma", 12000, 2);
		Articulo articulo2 = new Articulo(5678904, "Horno no Electrico", "no atma", 10000, 23);
		Articulo articulo3 = new Articulo(7654321, "Horno para helados", "heladorno", 100000, 100);


		this.insert(articulo1);
		this.insert(articulo2);
		this.insert(articulo3);

	}
	
	//trae los objetos de AtriculoRepo
	@Override
	public List<Articulo> getAll() {
		return new ArrayList<Articulo>(this.listaArticulos);
	}

	//para buscar por un solo prodicto (buscar por id)
	@Override
	public Articulo findById(int id) {
		
		return this.listaArticulos.stream()
			.filter( (art)-> art.getId() == id )
			.findAny()
			.orElse(null);
	}

	//alta producto, y se crea la id
	@Override
	public void insert(Articulo articulo) {
		int ultId = this.listaArticulos.stream()
				.map(Articulo::getId)
				.max(Integer::compare)
				.orElse(0);
		
		articulo.setId(ultId + 1);
		
		this.listaArticulos.add(articulo);
	}

	//modificar
	@Override
	public void update(Articulo articulo) {
		// esto es em caso de querer trabajar con un aarchivo o una base de datos
		
	}
	
	
	
	//borrar articulo
	@Override
	public void delete(int id) {
		this.listaArticulos.removeIf( (art) -> art.getId() == id);
	}

}
