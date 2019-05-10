package sistema;

import java.util.ArrayList;

public class Sistema {

	// CONSTANTES
	private final int NO_VISITADO = 0;
	private final int VISITADO = 1;

	// ATRIBUTOS
	private ArrayList<Aeropuerto> aeropuertos;
	private ArrayList<Reserva> reservas;
	private ArrayList<ArrayList<Ruta>> vuelos; 

	public Sistema() {
		aeropuertos = new ArrayList<Aeropuerto>();
		reservas = new ArrayList<Reserva>();
		vuelos = new ArrayList<ArrayList<Ruta>>();
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
	public Ruta buscarVueloDirecto(int origen, int destino) {
		// itero las rutas del aeropuerto origen
		ArrayList<Ruta> aux = new ArrayList<>(aeropuertos.get(origen - 1).getRutas());
		for (int i = 0; i < aux.size(); i++) {
			// pregunto si el destino de cada ruta es igual al destino solicitado
			if (aux.get(i).getDestino().getNombre().equals(aeropuertos.get(destino - 1).getNombre())) {
				// en caso de encontrar, retorno la ruta
				return aux.get(i);
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

	public ArrayList<ArrayList<Ruta>> obtenerVuelosDisponibles(int origen, int destino) {

		vuelos = new ArrayList<ArrayList<Ruta>>();
		ArrayList<Ruta> rutasOrigen = aeropuertos.get(origen - 1).getRutas();
		ArrayList<Ruta> caminoActual = new ArrayList<>();

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

	private void dfs_visit(Ruta ruta, Aeropuerto destino, ArrayList<Ruta> caminoActual) {

		ruta.getOrigen().setEstado(VISITADO);

		if (ruta.getDestino().equals(destino)) {
			caminoActual.add(ruta); //Agregamos a ruta que es el destino que buscamos.
			vuelos.add(caminoActual); //Devolvemos el camino de como llegamos hasta ahi. 
		} else {
			ArrayList<Ruta> rutas = ruta.getDestino().getRutas();
			for (int i = 0; i < rutas.size(); i++) {
				if (rutas.get(i).getDestino().getEstado() == NO_VISITADO) {
					caminoActual.add(rutas.get(i));
					dfs_visit(rutas.get(i), destino, caminoActual);
					caminoActual.remove(caminoActual.size() - 1);
				}
			}
			ruta.getOrigen().setEstado(NO_VISITADO);
		}
	}

}
