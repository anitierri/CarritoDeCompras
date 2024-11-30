package repositorios;

import java.util.ArrayList;
import java.util.List;

import models.Usuario;
import repositorios.interfaces.UsuarioRepo;

public class UsuarioRepoSingleton implements UsuarioRepo {

	private static UsuarioRepoSingleton singleton;

	public static UsuarioRepoSingleton getInstance() {
		if (singleton == null) {
			singleton = new UsuarioRepoSingleton();
		}
		return singleton;
	}

	private List<Usuario> listaUsuarios;

	private UsuarioRepoSingleton() {
		this.listaUsuarios = new ArrayList<Usuario>();
		Usuario usuario1 = new Usuario();
		usuario1.setNombre("pepe manolo");
		usuario1.setContrasena("soypepem4nolo");
		usuario1.setDni(12345678);
		usuario1.setCategoria("EMPLEADO");

		Usuario usuario2 = new Usuario();
		usuario2.setNombre("alberto rodriguez");
		usuario2.setContrasena("1234");
		usuario2.setDni(87654321);
		usuario2.setCategoria("CLIENTE");

		Usuario usuario3 = new Usuario();
		usuario3.setNombre("macarena macarenita");
		usuario3.setContrasena("4321");
		usuario3.setDni(23456789);
		usuario3.setCategoria("CLIENTE");

		this.insert(usuario1);
		this.insert(usuario2);
		this.insert(usuario3);

	}

	@Override
	public Usuario findbyNombre(String nombre) {
		return this.listaUsuarios.stream().filter(user -> user.getNombre().equals(nombre.toLowerCase())).findAny()
				.orElse(null);
	}

	@Override
	public Usuario findByDni(int dni) {
		return this.listaUsuarios.stream().filter(usuario -> usuario.getDni() == dni).findFirst().orElse(null);
	}

	@Override
	public Usuario findById(int id) {
		return this.listaUsuarios.stream().filter(user -> user.getId() == id).findAny().orElse(null);
	}

	@Override
	public void insert(Usuario usuario) {
		int ultId = this.listaUsuarios.stream().map(Usuario::getId).max(Integer::compare).orElse(0);

		usuario.setId(ultId + 1);

		this.listaUsuarios.add(usuario);
	}

	@Override
	public List<Usuario> getAll() {
		return new ArrayList<>(this.listaUsuarios);
	}

}
