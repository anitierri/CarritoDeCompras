package models;

public class ArticuloCantidad {
	private Articulo articulo;
	private int cantidad;

	public ArticuloCantidad(Articulo articulo) {
		this.articulo = articulo;
		this.cantidad = 1;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getSubtotal() {
		return articulo.getPrecio() * cantidad;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ArticuloCantidad that = (ArticuloCantidad) o;
		return articulo.getId() == that.articulo.getId();
	}

	@Override
	public int hashCode() {
		return articulo.getId();
	}

	@Override
	public String toString() {
		return "ArticuloCantidad{" + "articulo=" + articulo + ", cantidad=" + cantidad + '}';
	}
}