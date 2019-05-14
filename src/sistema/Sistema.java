package sistema;

import java.util.ArrayList;
import java.util.LinkedList;

public class Sistema {

	// CONSTANTES
	private final int NO_VISITADO = 0;
	private final int VISITADO = 1;

	// ATRIBUTOS
	private ArrayList<Aeropuerto> aeropuertos;
	private ArrayList<Reserva> reservas;
	private ArrayList<LinkedList<Ruta>> vuelos;

	// ATRIBUTOS de SERVICIO
	private boolean listaDePaises;
	private ArrayList<String> paises;
	private boolean listaDeAerolineas;
	private ArrayList<String> aerolineas;
	
	public Sistema() {
		aeropuertos = new ArrayList<Aeropuerto>();
		reservas = new ArrayList<Reserva>();
		vuelos = new ArrayList<LinkedList<Ruta>>();

		// SERVICIO 3
		listaDePaises = false;
		paises = new ArrayList<String>();
		
		listaDeAerolineas = false;
		aerolineas = new ArrayList<String>();
	}

	public ArrayList<String> listarPaises() {

		if (!listaDePaises) {
			for (int i = 0; i < aeropuertos.size(); i++) {
				if (!paises.contains(aeropuertos.get(i).getPais())) {
					paises.add(aeropuertos.get(i).getPais());
				}
				listaDePaises = true;
			}
			return paises;
		} else {
			return paises;
		}
	}
	public ArrayList<String> listarAerolineas(){
		ArrayList<Ruta> rutas = getRutas();
		if (!listaDeAerolineas) {
			for (int i = 0; i < rutas.size(); i++) {
				ArrayList<Aerolinea> aerolineasRuta = rutas.get(i).getAerolineas();
				for (int j = 0 ; j < aerolineasRuta.size(); j++) {
					if (!aerolineas.contains(aerolineasRuta.get(j).getNombre())) {
						aerolineas.add(aerolineasRuta.get(j).getNombre());
					}
					listaDeAerolineas = true;
				}
			}
			return aerolineas;
		} else {
			return aerolineas;
		}
	}
	public ArrayList<Vuelo> obtenerVueloEntrePaises(String origen, String destino) {
		// Guarda las rutas del país origen que llegan directamente al país destino
		ArrayList<Vuelo> rutaDirecta = new ArrayList<>();

		// Guardamos todas las rutas de los aeropuertos del país de origen
		ArrayList<Ruta> rutasOrigen = new ArrayList<>();

		// Guardamos los aeropuertos del país origen
		ArrayList<Aeropuerto> aeropuertoOrigen = new ArrayList<>();

		// Esta funciona testeada
		for (int i = 0; i < aeropuertos.size(); i++) {

			if ((aeropuertos.get(i).getPais().equals(origen)) && (aeropuertos.get(i).getPais().equals(origen))) {
				aeropuertoOrigen.add(aeropuertos.get(i));
			}
		}

		for (int i = 0; i < aeropuertoOrigen.size(); i++) {
			rutasOrigen.addAll(aeropuertoOrigen.get(i).getRutas());

		}

		for (int i = 0; i < rutasOrigen.size(); i++) {
			if (rutasOrigen.get(i).getDestino().getPais().equals(destino)) {
					ArrayList<Aerolinea> aerolineas = rutasOrigen.get(i).getAerolineas();
					for(int j = 0; j < aerolineas.size(); j++) {
						if (aerolineas.get(j).getDisponible()) {
							Vuelo informacion = new Vuelo(rutasOrigen.get(i).getOrigen().getNombre(),
									rutasOrigen.get(i).getDestino().getNombre(), rutasOrigen.get(i).getDistancia(),
									aerolineas.get(j).getDisponibles(), aerolineas.get(j).getNombre());
								rutaDirecta.add(informacion);
						}
					}
			}
		}

		return rutaDirecta;
	}

	public void addRuta(Ruta ruta) {
		// Buscamos la posición del origen y destino y las guardamos para evitar
		// recorridos innecesarios
		String origen = ruta.getOrigen().getNombre();
		String destino = ruta.getDestino().getNombre();

		int posicionOrigen = buscarAeropuerto(origen);
		int posicionDestino = buscarAeropuerto(destino);

		// creamos la ruta invertida
		Ruta rutaInvertida = new Ruta(ruta.getDestino(), ruta.getOrigen(), ruta.getDistancia(), ruta.esInternacional());
		// guardamos los aeropuertos para no pisarlos

		// Agregamos las rutas al sistema y a su vez los aeropuertos crean sus rutas
		aeropuertos.get(posicionOrigen).addRuta(ruta);
		aeropuertos.get(posicionDestino).addRuta(rutaInvertida);

	}

	// Buscar en que posición del ArrayList se encuentra el aeropuerto
	private int buscarAeropuerto(String nombre) {

		boolean seEncontro = false;
		int i = 0;
		int posicion = -1;

		while ((!seEncontro) && (i < aeropuertos.size())) {

			if (nombre.equals(aeropuertos.get(i).getNombre())) {
				seEncontro = true;
				posicion = i;
			}
			i++;
		}
		return posicion;
	}

	// buscar aeropuerto por nombre
	public Aeropuerto devolverAeropuerto(String nombre) {
		for (int i = 0; i < aeropuertos.size(); i++) {
			if (nombre.equals(aeropuertos.get(i).getNombre())) {
				return aeropuertos.get(i);
			}
		}
		return null; // si no lo encuentro devuelvo null, no se si esto est� bien
	}

	/*
	 * Añadir aeropuertos al sistema, se cambio a que reciba solamente el aeropuerto
	 * y no todo los parametros
	 */
	public void addAeropuerto(Aeropuerto aeropuerto) {
		aeropuertos.add(aeropuerto);
	}

	// ¿Se deben devolver en un ArrayList? ¿Hay algun inconveniente?
	public ArrayList<Aeropuerto> listarAeropuertos() {
		ArrayList<Aeropuerto> listaAeropuertos = new ArrayList<Aeropuerto>(aeropuertos);

		return listaAeropuertos;
	}

	/*
	 * Servicio 1: Verificar si existe un vuelo directo(Sin escala) entre un
	 * aeropuerto de origen y uno de destino para una Aerolinea en particular. De
	 * existir, se desea conocer los kilometros que requiere el viaje y la cantida
	 * de asientos que se encuentran disponible
	 */
	// Esta función no está agregada en el diagrama de clases
	public Vuelo buscarVueloDirecto(int origen, int destino, String aerolinea) {
		// itero las rutas del aeropuerto origen
		ArrayList<Ruta> aux = new ArrayList<>(aeropuertos.get(origen - 1).getRutas());
		for (int i = 0; i < aux.size(); i++) {
			// pregunto si el destino de cada ruta es igual al destino solicitado
			if (aux.get(i).getDestino().getNombre().equals(aeropuertos.get(destino - 1).getNombre())) {
				ArrayList<Aerolinea> aerolineas = aux.get(i).getAerolineas();
				for (int j = 0; j < aerolineas.size(); j++) {
					if (aerolineas.get(j).getNombre().equals(aerolinea)) {
						Vuelo retorno = new Vuelo(aeropuertos.get(origen - 1).getNombre(),
								aeropuertos.get(destino - 1).getNombre(), aux.get(i).getDistancia(),
								aerolineas.get(j).getDisponibles(), aerolineas.get(j).getNombre());
						return retorno;
					}
				}
			}
		}
		// en caso de no encontrar, retorno null, no se si esto esta bien
		return null;
	}

	// devuevlvo todas las rutas iterando los aerpouertos.
	// Esta funcion es para poder listar todas las reservas
	public ArrayList<Ruta> getRutas() {
		ArrayList<Ruta> aux = new ArrayList<Ruta>();
		for (int i = 0; i < aeropuertos.size(); i++) {
			aux.addAll(aeropuertos.get(i).getRutas());
		}
		return aux;
	}

	// Funcion para listar las reservas [Item 2 del TP]
	public ArrayList<Reserva> getReservas() {
		return new ArrayList<Reserva>(reservas);
	}

	public void addReserva(Reserva reserva) {

		Aeropuerto aOrigen = devolverAeropuerto(reserva.getOrigen());
		Aeropuerto aDestino = devolverAeropuerto(reserva.getDestino());

		reservas.add(reserva); // Agregamos la reserva al ArrayList de reservas del sistema

		ArrayList<Ruta> aux = new ArrayList<Ruta>(getRutas());
		for (int i = 0; i < aux.size(); i++) {
			if ((aux.get(i).getOrigen().equals(aOrigen) && (aux.get(i).getDestino().equals(aDestino)))) {
				for (int j = 0; j < aux.get(i).getAerolineas().size(); j++) {
					if (aux.get(i).getAerolineas().get(j).getNombre().equals(reserva.getAerolinea())) {
						aux.get(i).getAerolineas().get(j).setReservas(reserva);
					}
				}
			}
		}
	}

	public ArrayList<LinkedList<Ruta>> obtenerVuelosDisponibles(int origen, int destino) {

		vuelos.clear();
		ArrayList<Ruta> rutasOrigen = aeropuertos.get(origen - 1).getRutas();
		LinkedList<Ruta> caminoActual = new LinkedList<>();

		for (int i = 0; i < aeropuertos.size(); i++) {
			aeropuertos.get(i).setEstado(NO_VISITADO);
		}

		for (int i = 0; i < rutasOrigen.size(); i++) {
			if (rutasOrigen.get(i).getOrigen().getEstado() == NO_VISITADO) {
				dfs_visit(rutasOrigen.get(i), aeropuertos.get(destino - 1), caminoActual);
			}
		}

		return vuelos;
	}

	private void dfs_visit(Ruta ruta, Aeropuerto destino, LinkedList<Ruta> caminoActual) {
		// Marco como visitado el aeropuerto de origen
		ruta.getOrigen().setEstado(VISITADO);
		if (ruta.getDestino().equals(destino)) {
			// Agregamos la ruta final al camino actual, que es el destino que buscamos.
			caminoActual.addLast(ruta);
			LinkedList<Ruta> aux = new LinkedList<Ruta>(caminoActual);
			// Agregamos el camino actual que encontró a la estructura general.
			vuelos.add(aux);
		} else {
			// le pido los adyacentes al destino
			ArrayList<Ruta> rutas = ruta.getDestino().getRutas();
			caminoActual.addLast(ruta);
			// iteros sobre ellos
			for (int i = 0; i < rutas.size(); i++) {
				// pregunto si no lo visité para evitar ciclos
				if (rutas.get(i).getDestino().getEstado() == NO_VISITADO) {
					// marco la primera ruta adyacente
					// exploro
					dfs_visit(rutas.get(i), destino, caminoActual);
					// desmarco la primera ruta adyacente
				}
			}
			// marco como no visitado el aeropuerto de origen al salir de la recursion
		}
		ruta.getOrigen().setEstado(NO_VISITADO);
		caminoActual.removeLast();
	}

}
