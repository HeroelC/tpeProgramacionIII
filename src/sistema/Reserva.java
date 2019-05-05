package sistema;

public class Reserva {
	
	private String origen;
	private String destino;
	private String aerolinea;
	private int cantidad;
	
	public Reserva(String origen, String destino, String aerolinea, int cantidad) {
		this.origen = origen;
		this.destino = destino;
		this.aerolinea = aerolinea;
		this.cantidad = cantidad;
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
	public String getAerolinea() {
		return aerolinea;
	}
	public void setAerolinea(String aerolinea) {
		this.aerolinea = aerolinea;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public String toString() {
		return "Reserva [origen=" + origen + ", destino=" + destino + ", aerolinea=" + aerolinea + ", cantidad="
				+ cantidad + "]";
	}

}
