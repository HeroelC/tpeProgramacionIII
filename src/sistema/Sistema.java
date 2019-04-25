package sistema;

import java.util.ArrayList;

public class Sistema {

    //ATRIBUTOS
    private ArrayList<Aeropuerto> aeropuertos;
    
    public Sistema(){
    	aeropuertos = new ArrayList<Aeropuerto>();
    }

    /*
    Por el momento faltaría que después se agreguen las Aerolineas
    Por el momento agrego las rutas al sistema también, después decidir si se van a utilizar o no
    */
    public void addRuta(Ruta ruta){
 
        //Buscamos la posición del origen y destino y las guardamos para evitar recorridos innecesarios
    	String origen = ruta.getOrigen().getNombre();
    	String destino = ruta.getDestino().getNombre();
    	
        int posicionOrigen = buscarAeropuerto(origen);
        int posicionDestino = buscarAeropuerto(destino);
        
        //creamos la ruta invertida
        Ruta rutaInvertida = new Ruta(ruta.getDestino(), ruta.getOrigen(), ruta.getDistancia(), ruta.esInternacional());
        //guardamos los aeropuertos para no pisarlos
        
        //Agregamos las rutas al sistema y a su vez los aeropuertos crean sus rutas
        aeropuertos.get(posicionOrigen).addRuta(ruta);
        aeropuertos.get(posicionDestino).addRuta(rutaInvertida);

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
    //buscar aeropuerto por nombre
    public Aeropuerto devolverAeropuerto(String nombre) {
    	for (int i = 0; i < aeropuertos.size(); i++) {
    		if (nombre.equals(aeropuertos.get(i).getNombre())) {
    			return aeropuertos.get(i);
    		}
    	}
    	return null; //si no lo encuentro devuelvo null, no se si esto est� bien
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
  
    /*Servicio 1: Verificar si existe un vuelo directo(Sin escala) entre un aeropuerto de
     * origen y uno de destino para una Aerolinea en particular. De existir, se desea conocer
     * los kilometros que requiere el viaje y la cantida de asientos que se encuentran
     * disponible*/
    //Esta función no está agregada en el diagrama de clases
    public Ruta buscarVueloDirecto(int origen, int destino) {
    	//itero las rutas del aeropuerto origen
    	ArrayList<Ruta> aux = new ArrayList<>(aeropuertos.get(origen-1).getRutas());
    	for (int i = 0 ; i < aux.size(); i++) {
    		//pregunto si el destino de cada ruta es igual al destino solicitado
    		if(aux.get(i).getDestino().getNombre().equals(aeropuertos.get(destino-1).getNombre())){
    			//en caso de encontrar, retorno la ruta
    			return aux.get(i);
    		}
    	}
    	//en caso de no encontrar, retorno null, no se si esto esta bien
    	return null;
    }
}
