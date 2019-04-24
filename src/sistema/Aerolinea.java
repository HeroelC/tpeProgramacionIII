package sistema;

public class Aerolinea {

    //ATRIBUTOS
    String nombre;
    int capacidad;
    int reservas;

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
    
    public String toString() {
    	return nombre + "Capacidad: " + capacidad + " Reservas: " + reservas +
    			" Disponibles: " + getDisponibles();
    }
    

}
