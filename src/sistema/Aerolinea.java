package sistema;

public class Aerolinea {

    //ATRIBUTOS
    private String nombre;
    private int capacidad;
    private Reserva reserva;

    public Aerolinea(String nombre, int capacidad){
        this.nombre = nombre;
        this.capacidad = capacidad;
    }

    public String getNombre(){
        return nombre;
    }

    public int getCapacidad() {
    	return capacidad;
    }
    
    public void setCapacidad(int capacidad) {
    	this.capacidad = capacidad;
    }
    
    public Reserva getReservas() {
    	return reserva;
    }
    
    public void setReservas(Reserva reserva) {
    	this.reserva = reserva;
    }
   
    public int getDisponibles() {
    	return capacidad - reserva.getCantidad();
    }

//	fixear
//	public String toString() {
//		return "\n" + nombre + ", capacidad: " + capacidad + ", reservas: " + reservas;
//	}

	public boolean equals(Object obj) {
		Aerolinea a = (Aerolinea) obj;
		return this.nombre.equals(a.getNombre());
	}
    
	
    

}
