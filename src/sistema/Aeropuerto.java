package sistema;

import java.util.ArrayList;

public class Aeropuerto {

    //ATRIBUTOS
    private ArrayList<Ruta> rutas;
    private String ciudad;
    private String pais;
    private String nombre;
    
    //Variable para recorrido
    private int estado;

    public Aeropuerto(String nombre, String ciudad, String pais){
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.pais = pais;
        this.rutas = new ArrayList<Ruta>();
    }
    
    public ArrayList<Ruta> getRutas(){
    	ArrayList<Ruta> aux = new ArrayList<Ruta>(rutas);
    	return aux;
    }
    public ArrayList<Ruta> getRutasInternacionales(){
    	ArrayList<Ruta> aux = new ArrayList<Ruta>();
    	for (int i = 0; i < rutas.size(); i++) {
    		if (rutas.get(i).esInternacional()) {
    			aux.add(rutas.get(i));
    		}
    	}
    	return aux;
    }
    public String getCiudad(){
        return ciudad;
    }
    
    public int getEstado() {
    	return estado;
    }
    
    public void setEstado(int estado) {
    	this.estado = estado;
    }
    
    public boolean equals(Object o) {
    	Aeropuerto a = (Aeropuerto) o;
    	return this.nombre.equals(a.getNombre());
    }
    public void setCiudad(String ciudad){
        this.ciudad = ciudad;
    }

    public String getPais(){
        return pais;
    }

    public void setPais(String pais){
        this.pais = pais;
    }

    public String getNombre(){
        return nombre;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public String toString() {
    	return nombre + ", " + ciudad + ", " + pais;
    }

    //Devuelve la ruta para poder trabajarla afuera y agregarla al sistema (prueba no es definitivo)
    public void addRuta(Ruta ruta){
        //añadimos la ruta al aeropuerto
        rutas.add(ruta);
    }
    /*
      Preguntar si es necesario devolver las rutas, o solamente conocer si son directos o no
      IMPORTANTE: Si se devuelven las rutas solamente se ahorra el recorrido, sino
       hay que recorrer 
     */
    public ArrayList<Aeropuerto> getVuelosDirectos(){
    	ArrayList<Aeropuerto> listAdyacentes = new ArrayList<Aeropuerto>();
    	
    	for(int i = 0; i < rutas.size(); i++) {
    		
    		listAdyacentes.add(rutas.get(i).getDestino());
    	}
    	
    	return listAdyacentes;
    }
}
