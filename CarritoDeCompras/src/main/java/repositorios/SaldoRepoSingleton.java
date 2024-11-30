package repositorios;

import models.Saldo;
import models.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SaldoRepoSingleton {
	private static SaldoRepoSingleton singleton;
	private List<Saldo> listaSaldos;
	private static int idCont = 1;

	private SaldoRepoSingleton() {
		this.listaSaldos = new ArrayList<>();
	}

	public static SaldoRepoSingleton getInstance() {
		if (singleton == null) {
			singleton = new SaldoRepoSingleton();
		}
		return singleton;
	}

	public Saldo registrarRecarga(int idUsuario, double monto) {
		Saldo saldo = new Saldo(idUsuario, monto, "recarga");
		saldo.setId(idCont++);
		listaSaldos.add(saldo);
		return saldo;
	}

	public Saldo registrarTransferencia(int idOrigen, int idDestino, double monto) {
		Saldo saldoOrigen = new Saldo(idOrigen, -monto, "transferencia");
		saldoOrigen.setId(idCont++);
		saldoOrigen.setUsuarioDestino(idDestino);
		listaSaldos.add(saldoOrigen);

		Saldo saldoDestino = new Saldo(idDestino, monto, "transferencia");
		saldoDestino.setId(idCont++);
		saldoDestino.setUsuarioOrigen(idOrigen);
		listaSaldos.add(saldoDestino);

		return saldoOrigen;
	}

	public Saldo registrarCompra(int idUsuario, double monto) {
		Saldo saldo = new Saldo(idUsuario, -monto, "compra");
		saldo.setId(idCont++);
		listaSaldos.add(saldo);
		return saldo;
	}

	public double obtenerSaldoActual(int idUsuario) {
		return listaSaldos.stream()
				.filter(saldo -> saldo.getIdUsuario() == idUsuario)
				.mapToDouble(Saldo::getMonto)
				.sum();
	}

	public List<Saldo> obtenerHistorialSaldo(int idUsuario) {
		return listaSaldos.stream()
				.filter(saldo -> saldo.getIdUsuario() == idUsuario)
				.collect(Collectors.toList());
	}

	public List<Saldo> obtenerTodasLasTransferencias(int idUsuario) {
		return listaSaldos.stream()
				.filter(saldo -> (saldo.getTipo().equals("transferencia") && (saldo.getIdUsuario() == idUsuario
						|| saldo.getUsuarioOrigen() == idUsuario || saldo.getUsuarioDestino() == idUsuario)))
				.collect(Collectors.toList());
	}

	public List<Saldo> getAll() {
		return new ArrayList<>(this.listaSaldos);
	}
}