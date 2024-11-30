package models;

import java.time.LocalDateTime;

public class Saldo {
	private int id;
	private int idUsuario;
	private double monto;
	private String tipo;
	private LocalDateTime fecha;
	private int usuarioOrigen;
	private int usuarioDestino;

	public Saldo() {
		this.fecha = LocalDateTime.now();
	}

	public Saldo(int idUsuario, double monto, String tipo) {
		this.idUsuario = idUsuario;
		this.monto = monto;
		this.tipo = tipo;
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

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public int getUsuarioOrigen() {
		return usuarioOrigen;
	}

	public void setUsuarioOrigen(int usuarioOrigen) {
		this.usuarioOrigen = usuarioOrigen;
	}

	public int getUsuarioDestino() {
		return usuarioDestino;
	}

	public void setUsuarioDestino(int usuarioDestino) {
		this.usuarioDestino = usuarioDestino;
	}

	@Override
	public String toString() {
		return "Saldo{" + "id=" + id + ", idUsuario=" + idUsuario + ", monto=" + monto + ", tipo='" + tipo + '\''
				+ ", fecha=" + fecha + '}';
	}
}