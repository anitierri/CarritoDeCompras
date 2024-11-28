package models;

public class Saldo {
	int idSaldo;
	int idUsuario;
	int monto;
	
	
	public Saldo() {
		super();
	}
	
	public Saldo(int idUsuario, int monto) {
		super();
		this.idUsuario = idUsuario;
		this.monto = monto;
	}
	
	
	
	public int getIdSaldo() {
		return idSaldo;
	}
	public void setIdSaldo(int idSaldo) {
		this.idSaldo = idSaldo;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getMonto() {
		return monto;
	}
	public void setMonto(int monto) {
		this.monto = monto;
	}
	
	
	
	
	@Override
	public String toString() {
		return "Saldo [idSaldo=" + idSaldo + ", idUsuario=" + idUsuario + ", monto=" + monto + "]";
	}
	
	
	
	
}
