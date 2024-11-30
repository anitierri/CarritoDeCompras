package models;

public class Usuario {

	int id;
	String nombre;
	String contrasena;
	int dni;
	String categoria;

	public Usuario() {
		super();
	}

	public Usuario(String nombre, String contrasena, int dni, String categoria) {
		super();
		this.nombre = nombre;
		this.contrasena = contrasena;
		this.dni = dni;
		this.categoria = categoria;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", contrasena=" + contrasena + ", dni=" + dni
				+ ", categoria=" + categoria + "]";
	}

}
