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
	private ArrayList<VueloConEscala> vuelos;
	private Recorrido recorrido;

	// ATRIBUTOS de SERVICIO
	private boolean listaDePaises;
	private ArrayList<String> paises;
	private boolean listaDeAerolineas;
	private ArrayList<String> aerolineas;
	
	public Sistema() {
		aeropuertos = new ArrayList<>();
		reservas = new ArrayList<>();
		vuelos = new ArrayList<>();
		recorrido = new Recorrido();

		// SERVICIO 3
		listaDePaises = false;
		paises = new ArrayList<>();
		
		listaDeAerolineas = false;
		aerolineas = new ArrayList<>();
	}

	public ArrayList<String> listarPaises() {

		if (!listaDePaises) {
			for (Aeropuerto aeropuerto : aeropuertos) {
				if (!paises.contains(aeropuerto.getPais())) {
					paises.add(aeropuerto.getPais());
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
			for (Ruta ruta : rutas) {
				ArrayList<Aerolinea> aerolineasRuta = ruta.getAerolineas();
				for (Aerolinea aerolinea : aerolineasRuta) {
					if (!aerolineas.contains(aerolinea.getNombre())) {
						aerolineas.add(aerolinea.getNombre());
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
		for (Aeropuerto value : aeropuertos) {

			if ((value.getPais().equals(origen)) && (value.getPais().equals(origen))) {
				aeropuertoOrigen.add(value);
			}
		}

		for (Aeropuerto aeropuerto : aeropuertoOrigen) {
			rutasOrigen.addAll(aeropuerto.getRutasInternacionales());
		}

		for (Ruta ruta : rutasOrigen) {
			if (ruta.getDestino().getPais().equals(destino)) {
				ArrayList<Aerolinea> aerolineas = ruta.getAerolineas();
				for (Aerolinea aerolinea : aerolineas) {
					if (aerolinea.getDisponible()) {
						Vuelo informacion = new Vuelo(ruta.getOrigen().getNombre(),
								ruta.getDestino().getNombre(), ruta.getDistancia(),
								aerolinea.getDisponibles(), aerolinea.getNombre());
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
		ArrayList <Aerolinea> aux = new ArrayList<>(ruta.getAerolineas());
		Ruta rutaInvertida = new Ruta(ruta.getDestino(), ruta.getOrigen(), ruta.getDistancia(), ruta.esInternacional());
		rutaInvertida.addAerolineas(aux);
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
		for (Aeropuerto aeropuerto : aeropuertos) {
			if (nombre.equals(aeropuerto.getNombre())) {
				return aeropuerto;
			}
		}
		return null; // si no lo encuentro devuelvo null, no se si esto est� bien
	}

	/*
	 * Añadir aeropuertos al sistema, se cambio a que reciba solamente el aeropuerto
	 * y no todos los parametros
	 */
	public void addAeropuerto(Aeropuerto aeropuerto) {
		aeropuertos.add(aeropuerto);
	}

	// ¿Se deben devolver en un ArrayList? ¿Hay algun inconveniente?
	public ArrayList<Aeropuerto> listarAeropuertos() {

		return new ArrayList<>(aeropuertos);
	}

	/*
	 * Servicio 1: Verificar si existe un vuelo directo(Sin escala) entre un
	 * aeropuerto de origen y uno de destino para una Aerolinea en particular. De
	 * existir, se desea conocer los kilometros que requiere el viaje y la cantida
	 * de asientos que se encuentran disponible
	 */
	// Esta función no está agregada en el diagrama de clases
	public ArrayList<Vuelo> buscarVueloDirecto(int origen, int destino, String aerolinea) {
		// itero las rutas del aeropuerto origen
		ArrayList<Ruta> aux = new ArrayList<>(aeropuertos.get(origen - 1).getRutas());
		for (Ruta ruta : aux) {
			// pregunto si el destino de cada ruta es igual al destino solicitado
			if (ruta.getDestino().getNombre().equals(aeropuertos.get(destino - 1).getNombre())) {
				ArrayList<Aerolinea> aerolineas = ruta.getAerolineas();
				for (Aerolinea value : aerolineas) {
					if (value.getNombre().equals(aerolinea)) {
						Vuelo vuelo = new Vuelo(aeropuertos.get(origen - 1).getNombre(),
								aeropuertos.get(destino - 1).getNombre(), ruta.getDistancia(),
								value.getDisponibles(), value.getNombre());
						ArrayList<Vuelo> retorno = new ArrayList<>();
						retorno.add(vuelo);
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
	private ArrayList<Ruta> getRutas() {
		ArrayList<Ruta> aux = new ArrayList<>();
		for (Aeropuerto aeropuerto : aeropuertos) {
			aux.addAll(aeropuerto.getRutas());
		}
		return aux;
	}

	// Funcion para listar las reservas [Item 2 del TP]
	public ArrayList<Reserva> getReservas() {
		return new ArrayList<>(reservas);
	}

	public void addReserva(Reserva reserva) {

		Aeropuerto aOrigen = devolverAeropuerto(reserva.getOrigen());
		Aeropuerto aDestino = devolverAeropuerto(reserva.getDestino());

		reservas.add(reserva); // Agregamos la reserva al ArrayList de reservas del sistema

		ArrayList<Ruta> aux = new ArrayList<>(getRutas());
		for (Ruta ruta : aux) {
			if ((ruta.getOrigen().equals(aOrigen) && (ruta.getDestino().equals(aDestino)))) {
				for (int j = 0; j < ruta.getAerolineas().size(); j++) {
					if (ruta.getAerolineas().get(j).getNombre().equals(reserva.getAerolinea())) {
						ruta.getAerolineas().get(j).setReservas(reserva);
					}
				}
			}
		}
	}

	public ArrayList<VueloConEscala> obtenerVuelosDisponibles(int origen, int destino, String aerolinea) {
		vuelos.clear();
		ArrayList<Ruta> rutasOrigen = aeropuertos.get(origen - 1).getRutas();
		LinkedList<Ruta> caminoActual = new LinkedList<>();
		for (Aeropuerto aeropuerto : aeropuertos) {
			aeropuerto.setEstado(NO_VISITADO);
		}
		for (Ruta ruta : rutasOrigen) {
			if (ruta.getOrigen().getEstado() == NO_VISITADO) {
				dfs_visit(ruta, aeropuertos.get(destino - 1), caminoActual,
						aeropuertos.get(origen - 1).getNombre(), aerolinea);
			}
		}
		return vuelos;
	}
	private double seCumple (LinkedList<Ruta> camino, String aerolinea) {
		double kmTotales = 0;
		for (Ruta ruta : camino) {
			ArrayList<Aerolinea> aerolineas = ruta.getAerolineas();
			int contAerolineas = 0;
			for (Aerolinea value : aerolineas) {
				if (!value.getNombre().equals(aerolinea)) {
					if (value.getDisponible()) {
						contAerolineas++;
					}
				}
			}
			if (contAerolineas > 0) {
				kmTotales += ruta.getDistancia();
			} else {
				return 0;
			}
		}
		return kmTotales;
	}
	public Recorrido supervisarFuncionamiento(int origen) {
		recorrido = new Recorrido();
		for (Aeropuerto aeropuerto : aeropuertos) {
			aeropuerto.setEstado(NO_VISITADO);
		}
		double km = 0.0 ;
		System.out.println(recorrido.getKmTotales());
		Aeropuerto or = aeropuertos.get(origen-1);
		LinkedList<Aeropuerto> caminoActual = new LinkedList<>();
		realizarRecorrido(or, caminoActual, or, km);
		return recorrido;
	}
	private void realizarRecorrido(Aeropuerto origen, LinkedList<Aeropuerto> caminoActual,
								   Aeropuerto principio, double km) {
		origen.setEstado(VISITADO);
		caminoActual.add(origen);
		ArrayList<Ruta> rutas = origen.getRutas();
		for (Ruta ruta : rutas) {
			km += ruta.getDistancia();
			if (ruta.getDestino().equals(principio)) {
				if ((caminoActual.size() == aeropuertos.size())) {
					if (km < recorrido.getKmTotales() || recorrido.getKmTotales() == 0.0)
					caminoActual.add(principio);
					LinkedList<Aeropuerto> caminoFinal = new LinkedList<>(caminoActual);
					recorrido.setCamino(caminoFinal);
					recorrido.setKmTotales(km);
				}

			} else {
					if (ruta.getDestino().getEstado() == NO_VISITADO) {
						realizarRecorrido(ruta.getDestino(), caminoActual, principio, km);
					}
				}
			km -= ruta.getDistancia();
			}
		origen.setEstado(NO_VISITADO);
		caminoActual.remove(origen);
	}

	private void dfs_visit(Ruta ruta, Aeropuerto destino, LinkedList<Ruta> caminoActual, String origen, String aerolinea) {
		// Marco como visitado el aeropuerto de origen
		ruta.getOrigen().setEstado(VISITADO);
		if (ruta.getDestino().equals(destino)) {
			// Agregamos la ruta final al camino actual, que es el destino que buscamos.
			caminoActual.addLast(ruta);
			
			double kmTotales = seCumple(caminoActual, aerolinea); 
			if(kmTotales > 0) {
				LinkedList<Ruta> aux = new LinkedList<>(caminoActual);
				VueloConEscala auxVuelo = new VueloConEscala(origen, destino.getNombre(), aux.size(), kmTotales);
				vuelos.add(auxVuelo);
			}
			// Agregamos el camino actual que encontró a la estructura general.
		} else {
			/* le pido los adyacentes al destino */
			ArrayList<Ruta> rutas = ruta.getDestino().getRutas();
			caminoActual.addLast(ruta);
			// iteros sobre ellos
			for (Ruta value : rutas) {
				// pregunto si no lo visité para evitar ciclos
				if (value.getDestino().getEstado() == NO_VISITADO) {
					// marco la primera ruta adyacente
					// exploro
					dfs_visit(value, destino, caminoActual, origen, aerolinea);
					// desmarco la primera ruta adyacente
				}
			}
			// marco como no visitado el aeropuerto de origen al salir de la recursion
		}
		ruta.getOrigen().setEstado(NO_VISITADO);
		caminoActual.removeLast();
	}
}
