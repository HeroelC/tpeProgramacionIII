package sistema;

import java.util.ArrayList;

public class Aeropuerto {

    //ATRIBUTOS
    ArrayList<Ruta> rutas;
    String ciudad;
    String pais;
    String nombre;

    public Aeropuerto(String nombre, String ciudad, String pais){

        this.nombre = nombre;
        this.ciudad = ciudad;
        this.pais = pais;
        this.rutas = new ArrayList<Ruta>();
    }

    public String getCiudad(){
        return ciudad;
    }
    public boolean equals(Object o) {
    	Aeropuerto a = (Aeropuerto) o;
    	return this.nombre == a.getNombre();
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
    
    //¿Necesario?
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
