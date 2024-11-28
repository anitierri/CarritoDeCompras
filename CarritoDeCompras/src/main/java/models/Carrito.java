package models;

import java.util.ArrayList;
import java.util.List;

public class Carrito {
	private int id;
	private int idUsuario;
	private double precio;
	private String estado;
	private List<ArticuloCantidad> articulos;

	public Carrito() {
		super();
		this.articulos = new ArrayList<>();
		this.precio = 0.0;
		this.estado = "pendiente";
	}

	public Carrito(int idUsuario, double precio, String estado) {
		this.idUsuario = idUsuario;
		this.precio = precio;
		this.estado = estado;
		this.articulos = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<ArticuloCantidad> getArticulos() {
		return articulos;
	}

	public double calcularTotal() {
		return articulos.stream()
				.mapToDouble(ArticuloCantidad::getSubtotal)
				.sum();
	}

	public void agregarArticulo(Articulo articulo) {
		for (ArticuloCantidad articuloCantidad : articulos) {
			if (articuloCantidad.getArticulo().getId() == articulo.getId()) {
				articuloCantidad.setCantidad(articuloCantidad.getCantidad() + 1);
				this.precio = calcularTotal();
				return;
			}
		}

		ArticuloCantidad nuevoArticuloCantidad = new ArticuloCantidad(articulo);
		this.articulos.add(nuevoArticuloCantidad);
		this.precio = calcularTotal();
	}

	public void eliminarArticulo(Articulo articulo) {
		articulos.removeIf(a -> a.getArticulo().getId() == articulo.getId());
		this.precio = calcularTotal();
	}

	@Override
	public String toString() {
		return "Carrito [id=" + id + ", idUsuario=" + idUsuario + ", precio=" + precio + ", estado=" + estado
				+ ", articulos=" + articulos + "]";
	}
}