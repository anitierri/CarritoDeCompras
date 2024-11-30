package repositorios.interfaces;

import java.util.List;

import models.Usuario;

public interface UsuarioRepo {

	// busca por nombre (al final lo cambiamos por dni, pero dejamos este metodo por
	// las dudas.
	public Usuario findbyNombre(String nombre);

	// busdca por id
	public Usuario findById(int id);

	// busca por dni
	public Usuario findByDni(int dni);

	// alta usuario
	public void insert(Usuario usuario);

	// este se usa unicamente para traer los usuarios par a ver las transacciones
	public List<Usuario> getAll();

}
