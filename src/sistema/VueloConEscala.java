package sistema;

public class VueloConEscala {

	private String origen;
	private String destino;
	private double kmTotales;
	private int cantidadEscalas;
	
	public VueloConEscala(String origen, String destino,  int cantidadEscalas, double kmTotales) { 
		this.origen = origen;
		this.destino = destino;
		this.cantidadEscalas = cantidadEscalas;
		this.kmTotales = kmTotales;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public double getKm() {
		return kmTotales;
	}

	public void setKm(double km) {
		this.kmTotales = km;
	}

	public int getCantidadEscalas() {
		return cantidadEscalas;
	}

	public void setCantidadEscalas(int cantidadEscalas) {
		this.cantidadEscalas = cantidadEscalas;
	}

	@Override
	public String toString() {
		return "Información de vuelo su origen es: " + origen + ", el destino: " + destino + ", km totales: " + kmTotales + ", cantidad escalas: "
				+ cantidadEscalas;
	}
	
}
