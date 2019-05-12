package sistema;

public class Vuelo {
	private String origen;
	private String destino;
	private double km;
	private int cantidad;
	private String aerolinea;
	public Vuelo(String origen, String destino, double km, int cantidad, String aerolinea) {
		this.origen = origen;
		this.destino = destino;
		this.km = km;
		this.cantidad = cantidad;
		this.aerolinea = aerolinea;
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
		return km;
	}
	public void setKm(double km) {
		this.km = km;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	public String getAerolinea() {
		return aerolinea;
	}
	public void setAerolinea(String aerolinea) {
		this.aerolinea = aerolinea;
	}
	@Override
	public String toString() {
		return "Vuelo [origen=" + origen + ", destino=" + destino + ", km=" + km + ", cantidad=" + cantidad
				+ ", aerolinea=" + aerolinea + "]";
	}



	

}
