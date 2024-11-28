package repositorios.interfaces;

import models.Usuario;

public interface UsuarioRepo {
	
	//busca por nombre
	public Usuario findbyNombre(String nombre);
	
	//busdca por id
	public Usuario findById(int id);
	
	//alta usuario
	public void insert(Usuario usuario);

}
