package sistema;

import java.util.ArrayList;

public class Sistema {

    //ATRIBUTOS
    ArrayList<Aeropuerto> aeropuertos;
    
    public Sistema(){
    	aeropuertos = new ArrayList<Aeropuerto>();
    }

    /*
    Por el momento faltaría que después se agreguen las Aerolineas
    Por el momento agrego las rutas al sistema también, después decidir si se van a utilizar o no
    */
    public void addRuta(Ruta ruta){
    	//String origen, String destino, double distancia, boolean internacional
        //Buscamos la posición del origen y destino y las guardamos para evitar recorridos innecesarios
    	String origen = ruta.getOrigen().getNombre();
    	String destino = ruta.getDestino().getNombre();
    	
        int posicionOrigen = buscarAeropuerto(origen);
        int posicionDestino = buscarAeropuerto(destino);
        
        //creamos la ruta invertida
        Ruta rutaInvertida = ruta;
        
        rutaInvertida.setOrigen(ruta.getDestino());
        rutaInvertida.setDestino(ruta.getOrigen());
        
        //Agregamos las rutas al sistema y a su vez los aeropuertos crean sus rutas
        
        aeropuertos.get(posicionOrigen).addRuta(ruta);
        
//        rutas.add(aeropuertos.get(posicionDestino).addRuta();
    }

    //Buscar en que posición del ArrayList se encuentra el aeropuerto
    private int buscarAeropuerto(String nombre){

        boolean seEncontro = false;
        int i = 0;
        int posicion = -1;

        while((!seEncontro)&&(i < aeropuertos.size())){

            if(nombre.equals(aeropuertos.get(i).getNombre())){
                seEncontro = true;
                posicion = i;
            }
            i++;
        }
        return posicion;
    }
    
    //Añadir aeropuertos al sistema 
    public void addAeropuerto(String nombre, String ciudad, String pais) {
    	aeropuertos.add(new Aeropuerto(nombre, ciudad, pais));
    }
    
    //¿Se deben devolver en un ArrayList? ¿Hay algun inconveniente?
    public ArrayList<Aeropuerto> listarAeropuertos(){
    	ArrayList<Aeropuerto> listaAeropuertos = new ArrayList<Aeropuerto>(aeropuertos);
    	
    	return listaAeropuertos;
    }
    public ArrayList<Ruta> listarRutas(){
    	ArrayList<Ruta> listaRuta = new ArrayList<Ruta>(rutas);
    	
    	return listaRuta;
    }
    
    /*Servicio 1: Verificar si existe un vuelo directo(Sin escala) entre un aeropuerto de
     * origen y uno de destino para una Aerolinea en particular. De existir, se desea conocer
     * los kilometros que requiere el viaje y la cantida de asientos que se encuentran
     * disponible*/
    //Esta función no está agregada en el diagrama de clases
    public Ruta buscarVueloDirecto(int origen, int destino) {
    	for (int i = 0; i < rutas.size(); i++) {
    		if ((rutas.get(i).getOrigen().equals(aeropuertos.get(origen-1)) &&
    				(rutas.get(i).getDestino().equals(aeropuertos.get(destino-1))))){
    			String mensaje = rutas.get(i).toString();
    			ArrayList<Aerolinea> aux = rutas.get(i).getAerolineas();
    			for (int j = 0; j < aux.size(); j++) {
    				mensaje += "\n" + aux.get(i).toString();
    			}
 
    		}
    	}
 
    }
}
