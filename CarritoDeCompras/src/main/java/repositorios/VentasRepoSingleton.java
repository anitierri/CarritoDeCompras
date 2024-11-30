package repositorios;

import models.Venta;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VentasRepoSingleton {
	private static VentasRepoSingleton singleton;
	private List<Venta> listaVentas;
	private static int idCont = 1;

	private VentasRepoSingleton() {
		this.listaVentas = new ArrayList<>();
	}

	public static VentasRepoSingleton getInstance() {
		if (singleton == null) {
			singleton = new VentasRepoSingleton();
		}
		return singleton;
	}

	public void registrarVenta(Venta venta) {
		venta.setId(idCont++);
		listaVentas.add(venta);
	}

	public List<Venta> getAll() {
		return new ArrayList<>(listaVentas);
	}

	public List<Venta> obtenerVentasPorUsuario(int idUsuario) {
		return listaVentas.stream()
				.filter(venta -> venta.getIdUsuario() == idUsuario)
				.collect(Collectors.toList());
	}
}