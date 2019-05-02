package sistema;

public class Aerolinea {

    //ATRIBUTOS
    private String nombre;
    private int capacidad;
    private int reservas;

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
    
    public int getReservas() {
    	return reservas;
    }
    
    public void setReservas(int reservas) {
    	this.reservas = reservas;
    }
    
    public int getDisponibles() {
    	return capacidad - reservas;
    }

	@Override
	public String toString() {
		return "Aerolinea [nombre=" + nombre + ", capacidad=" + capacidad + ", reservas=" + reservas + "]";
	}

	@Override
	public boolean equals(Object obj) {
		Aerolinea a = (Aerolinea) obj;
		return this.nombre.equals(a.getNombre());
	}
    
	
    

}
