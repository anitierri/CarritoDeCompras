package models;

import java.time.LocalDateTime;
import java.util.List;

public class Venta {
	private int id;
	private int idUsuario;
	private double total;
	private LocalDateTime fecha;
	private List<ArticuloCantidad> articulos;
	private String estado;

	public Venta() {
		this.fecha = LocalDateTime.now();
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

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public List<ArticuloCantidad> getArticulos() {
		return articulos;
	}

	public void setArticulos(List<ArticuloCantidad> articulos) {
		this.articulos = articulos;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}