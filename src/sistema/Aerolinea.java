package sistema;

public class Aerolinea {

    //ATRIBUTOS
    private String nombre;
    private int capacidad;
    private int reservas;

    public Aerolinea(String nombre){
        this.nombre = nombre;
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
    
    public String toString() {
    	return nombre + "Capacidad: " + capacidad + " Reservas: " + reservas +
    			" Disponibles: " + getDisponibles();
    }
    

}
