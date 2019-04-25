package sistema;

import java.util.ArrayList;

public class Ruta {

	// ATRIBUTOS
	private Aeropuerto origen;
	private Aeropuerto destino;
	private ArrayList<Aerolinea> aerolineas;
	private double distancia;
	private boolean internacional;

	public Ruta(Aeropuerto origen, Aeropuerto destino, double distancia,
			boolean internacional) {

		this.origen = origen;
		this.destino = destino;
		this.distancia = distancia;
		this.internacional = internacional;
		this.aerolineas = new ArrayList<Aerolinea>();
	}

	// Averiguar esto, no me convence el retornar el objeto completo.
	public Aeropuerto getOrigen() {
		return origen;
	}

	public void setOrigen(Aeropuerto origen) {
		this.origen = origen;
	}

	// Lo mismo que el caso del getOrigen()
	public Aeropuerto getDestino() {
		return destino;
	}

	public void setDestino(Aeropuerto destino) {
		this.destino = destino;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}
	public boolean esInternacional() {
		return internacional;
	}

	public void setInternacional(boolean internacional) {
		this.internacional = internacional;
	}
	public ArrayList<Aerolinea> getAerolineas(){
		ArrayList<Aerolinea> aux = new ArrayList<Aerolinea>(aerolineas);
		return aux;
	}
	public String toString() {
		return "De: " + origen.getNombre() + " A: " + destino.getNombre() + " Son: " + distancia + "km. "
				+ "Es internacional: " + internacional;
	}
}
