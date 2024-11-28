package repositorios;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import models.Usuario;
import repositorios.interfaces.UsuarioRepo;
import utils.ContrasenaHash;

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
		try {
			Usuario user1 = new Usuario();
			user1.setNombre("pepe manolo");
			user1.setContrasena(ContrasenaHash.encriptar("soypepem4nolo"));
			user1.setCategoria("EMPLEADO");

			Usuario user2 = new Usuario();
			user2.setNombre("alberto rodriguez");
			user2.setContrasena(ContrasenaHash.encriptar("1234"));
			user2.setCategoria("CLIENTE");

			this.insert(user1);
			this.insert(user2);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Usuario findbyNombre(String nombre) {
		return this.listaUsuarios.stream().filter(user -> user.getNombre().equals(nombre.toLowerCase())).findAny().orElse(null);
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

}
